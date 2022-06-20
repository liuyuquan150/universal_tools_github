package indi.ly.crush.excel.easy.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import indi.ly.crush.excel.easy.annotation.ExcelEntity;
import indi.ly.crush.excel.easy.converter.ChecksumFailureFieldConverter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * <h2><span style="color: red;">Excel 行监听器结果</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@ExcelEntity
public class ExcelRowListenerResult
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     工作表名称.
	 * </p>
	 */
	@ExcelProperty(value = {"导入详情", "工作表名称"}, index = 0)
	private final String sheetName;
	/**
	 * <p>
	 *     行索引.
	 * </p>
	 */
	@ExcelProperty(value = {"导入详情", "行号"}, index = 1)
	private Integer rowIndex;
	/**
	 * <p>
	 *     错误行对象, 不作为 "Excel 导入错误信息文件导出" 的一部分(<em>只是为 debug 和 log 记录而服务</em>),
	 *     它本质就是 {@link ReadRowHolder#getCurrentRowAnalysisResult}.
	 * </p>
	 */
	@ExcelIgnore
	private final Object errorRowObj;
	/**
	 * <p>
	 *     错误列的索引(<em>程序错误所导致</em>), 由 {@link AnalysisEventListener#onException(Exception, AnalysisContext)} 提供.
	 * </p>
	 */
	@ExcelProperty(value = {"导入详情", "程序错误", "列号"}, index = 2)
	private Integer columnIndex;
	/**
	 * <p>
	 *     错误列的消息(<em>程序错误所导致</em>), 由 {@link AnalysisEventListener#onException(Exception, AnalysisContext)} 提供.
	 * </p>
	 */
	@ExcelProperty(value = {"导入详情", "程序错误", "列消息"}, index = 3)
	private String errorMessage;
	/**
	 * <p>
	 *     校验失败字段: 存储未通过业务自身定义校验的字段(<em>而非程序主动抛出异常所导致</em>), 它本质就是 {@link ExcelRowCheckResult#errorColumnMap}.
	 * </p>
	 */
	@ExcelProperty(value = {"导入详情", "业务错误"}, index = 4, converter = ChecksumFailureFieldConverter.class)
	private Map<Integer, String> checksumFailureField;
	
	private ExcelRowListenerResult(String sheetName, Integer rowIndex, Object errorRowObj, Integer columnIndex, String errorMessage) {
		this.sheetName = sheetName;
		this.rowIndex = rowIndex;
		this.errorRowObj = errorRowObj;
		this.columnIndex = columnIndex;
		this.errorMessage = errorMessage;
	}
	
	public static ExcelRowListenerResult createExcelRowListenerResultByAnalysisContext(AnalysisContext analysisContext) {
		var readSheetHolder = analysisContext.readSheetHolder();
		var sheetName = readSheetHolder.getSheetName();
		var readRowHolder = analysisContext.readRowHolder();
		var rowIndex = readRowHolder.getRowIndex() + 1;
		var currentRowAnalysisResult = readRowHolder.getCurrentRowAnalysisResult();
		return new ExcelRowListenerResult(sheetName, rowIndex, currentRowAnalysisResult, null, null);
	}
	
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void setChecksumFailureField(Map<Integer, String> checksumFailureField) {
		this.checksumFailureField = checksumFailureField;
	}
	
	@Override
	public String toString() {
		return "ExcelRowListenerResult{" + "sheetName='" + sheetName + '\'' + ", rowIndex=" + rowIndex
				+ ", errorRowObj=" + errorRowObj + ", columnIndex=" + columnIndex + ", errorMessage='" + errorMessage
				+ '\'' + ", checksumFailureField=" + checksumFailureField + '}';
	}
}
