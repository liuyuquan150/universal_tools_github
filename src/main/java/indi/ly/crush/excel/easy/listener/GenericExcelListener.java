package indi.ly.crush.excel.easy.listener;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import indi.ly.crush.excel.easy.model.ExcelListenerResult;
import indi.ly.crush.excel.easy.model.ExcelRowCheckResult;
import indi.ly.crush.excel.easy.model.ExcelRowListenerResult;
import indi.ly.crush.excel.easy.util.BaseEasyExcelWriteUtil;
import indi.ly.crush.util.base.BaseFileUtil;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <h2><span style="color: red;">通用 Excel 监听器</span></h2>
 * <p>
 *     这是一个通用 Excel 文件读取监听器,
 *     它内部采用 {@link Function} 和 {@link Consumer} 让使用者(<em>通常是 {@literal Service}</em>)决定此监听器的 “校验行为” 和 “消费行为”.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
public class GenericExcelListener<T>
		extends AnalysisEventListener<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericExcelListener.class);
	private final Boolean open;
	private final SqlSession securitySqlSession;
	/**
	 * <p>
	 *     T 对象的校验行为, 由 {@link ExcelRowCheckResult} 提供校验不通过的消息提示动态化.
	 * </p>
	 */
	private final Function<T, ExcelRowCheckResult> f;
	/**
	 * <p>
	 *     T 对象的消费行为(<em>比如将读到的数据按批处理的形式新增到数据库中</em>), 由使用者传入.
	 * </p>
	 */
	private final Consumer<T> c;
	private final Integer sheetNumber;
	private static final Integer BATCH_EXECUTION_DEFAULT_BOUNDARIES = 300_000 - 1;
	private final Integer batchExecutionBoundaries;
	/**
	 * <p>
	 *    错误行数据: 存储 “导入错误/校验不通过” 的行数据. <br />
	 *
	 *    对于这种数据, 我们需要保存并返回给前端进行展示提醒, 方便用户感知导入结果.
	 * </p>
	 */
	private final List<ExcelRowListenerResult> errorRowData;
	private Integer successRowCount;
	private Integer sheetCount;
	
	
	@Override
	public void invoke(T data, AnalysisContext context) {
		LOGGER.debug("invoke 方法已监听到 Excel 文件中的一行数据: {}", data.toString());
		
		if (this.open) {
			var excelRowCheckResult = this.f.apply(data);
			if (excelRowCheckResult.errorColumnMap().isEmpty()) {
				this.c.accept(data);
				this.successRowCount++;
			} else {
				var excelRowListenerResult = ExcelRowListenerResult.createExcelRowListenerResultByAnalysisContext(context);
				excelRowListenerResult.setChecksumFailureField(excelRowCheckResult.errorColumnMap());
				this.errorRowData.add(excelRowListenerResult);
			}
		} else {
			this.c.accept(data);
			this.successRowCount++;
		}
		
		if (this.successRowCount % (this.batchExecutionBoundaries + 1) == 0) {
			this.securitySqlSession.flushStatements();
		}
	}
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		var readSheetHolder = context.readSheetHolder();
		var sheetName = readSheetHolder.getSheetName();
		LOGGER.debug("名为 '{}' 的工作表中的全部数据已监听完毕.", sheetName);
		
		// 解析工作簿最后一个工作表时的处理
		if (this.sheetNumber.equals(++this.sheetCount)) {
			if (this.successRowCount % (this.batchExecutionBoundaries + 1) != 0) {
				this.securitySqlSession.flushStatements();
			}
			if (this.errorRowData.size() > 0) {
				this.securitySqlSession.rollback(true);
				LOGGER.warn("当前含业务错误存在, 丢弃挂起的批处理语句并回滚数据库连接");
			} else {
				this.securitySqlSession.commit(true);
				LOGGER.warn("当前不含业务错误存在, 提交数据库连接");
			}
		}
	}
	
	@Override
	public void onException(Exception exception, AnalysisContext context) {
		if (exception instanceof ExcelDataConvertException excelDataConvertException) {
			var rowIndex = excelDataConvertException.getRowIndex() + 1;
			var columnIndex = excelDataConvertException.getColumnIndex() + 1;
			var message = excelDataConvertException.getMessage();
			LOGGER.error("解析第 {} 行第 {} 列时发生异常, 原因是: {}", rowIndex, columnIndex, message);
			
			if (this.open) {
				var excelFileListenerRowData = ExcelRowListenerResult.createExcelRowListenerResultByAnalysisContext(context);
				excelFileListenerRowData.setRowIndex(rowIndex);
				excelFileListenerRowData.setColumnIndex(columnIndex);
				excelFileListenerRowData.setErrorMessage(excelDataConvertException.getClass() + ": " + message);
				this.errorRowData.add(excelFileListenerRowData);
			}
			
			this.securitySqlSession.rollback(true);
		}
	}
	
	public ExcelListenerResult listenerResult(String exportFilePath) {
		if (!this.open) {
			throw new IllegalArgumentException("open is false");
		}
		LOGGER.debug("整个 Excel 文件成功读取的行数 ------> {}", this.successRowCount);
		return new ExcelListenerResult(this.successRowCount, this.errorRowData, exportFilePath);
	}
	
	
	public ExcelListenerResult exportListenerResult(
			String exportDirectoryPath, String originalName, String sheetName,
			Integer worksheetStorageEntry, List<? extends Converter<?>> converters,
			List<? extends WriteHandler> writeHandlers
	) {
		if (!this.open) {
			throw new IllegalArgumentException("open is false");
		}
		
		if (this.errorRowData.isEmpty()) {
			LOGGER.debug("{}", "导入无错误数据, 所以不对错误数据进行导出.");
			return listenerResult(null);
		}
		
		var exportFilePath = BaseFileUtil.createExportFilePath(exportDirectoryPath, originalName);
		var excelListenerResult = listenerResult(exportFilePath);
		var fileExtension = BaseFileUtil.getFileExtension(originalName);
		
		ExcelTypeEnum fileType;
		if (Objects.equals(fileExtension, ExcelTypeEnum.XLSX.getValue())) {
			fileType = ExcelTypeEnum.XLSX;
		}
		else if (Objects.equals(fileExtension, ExcelTypeEnum.XLS.getValue())) {
			fileType = ExcelTypeEnum.XLS;
		}
		else {
			fileType = ExcelTypeEnum.CSV;
		}
		
		BaseEasyExcelWriteUtil.flexibleWrite(excelListenerResult.getExcelRowListenerResults(), exportFilePath, ExcelRowCheckResult.class, sheetName, fileType, worksheetStorageEntry,converters, writeHandlers);
		return excelListenerResult;
	}
	
	
	
	//---------------------------------------------------------------------
	// 构造器
	//---------------------------------------------------------------------
	
	public GenericExcelListener(
			SqlSession securitySqlSession, Function<T, ExcelRowCheckResult> f, Consumer<T> c, ExcelReader excelReader
	) {
		this(false,securitySqlSession, f, c, excelReader.excelExecutor().sheetList().size(), null);
	}
	
	public GenericExcelListener(
			SqlSession securitySqlSession, Function<T, ExcelRowCheckResult> f, Consumer<T> c, Integer sheetNumber
	) {
		this(false,securitySqlSession, f, c, sheetNumber, null);
	}
	
	public GenericExcelListener(
			SqlSession securitySqlSession, Function<T, ExcelRowCheckResult> f, Consumer<T> c, ExcelReader excelReader, Integer batchExecutionBoundaries
	) {
		this(false,securitySqlSession, f, c, excelReader.excelExecutor().sheetList().size(), batchExecutionBoundaries);
	}
	
	public GenericExcelListener(
			Boolean open, SqlSession securitySqlSession, Function<T, ExcelRowCheckResult> f, Consumer<T> c, Integer sheetNumber, Integer batchExecutionBoundaries
	) {
		this.open = Optional.ofNullable(open).orElse(false);
		this.securitySqlSession = Objects.requireNonNull(securitySqlSession, "SqlSession must not be null");
		Assert.isTrue(this.securitySqlSession.getClass() != SqlSessionTemplate.class, "不能传入一个 SqlSessionTemplate 会话, 因为 SqlSessionTemplate 不允许外界手动控制会话的提交、回滚");
		this.f = Objects.requireNonNull(f);
		this.c = Objects.requireNonNull(c);
		this.sheetNumber = Objects.requireNonNull(sheetNumber);
		Assert.isTrue(this.sheetNumber >= 0, "sheetNumber 不能小于 0");
		this.batchExecutionBoundaries = Optional.ofNullable(batchExecutionBoundaries).orElse(BATCH_EXECUTION_DEFAULT_BOUNDARIES);
		Assert.isTrue(this.batchExecutionBoundaries > 0, "batchExecutionBoundaries 必须大于 0");
		this.errorRowData = new LinkedList<>();
		this.successRowCount = 0;
		this.sheetCount = 0;
	}
}
