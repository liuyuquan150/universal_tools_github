package indi.ly.crush.excel.easy.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import indi.ly.crush.util.base.BaseObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <h2><span style="color: red;">EasyExcel 写工具类</span></h2>
 * <p>
 *     提供了用于将数据写入到 Excel 文件中的便捷方法.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseEasyExcelWriteUtil
		extends BaseEasyExcelUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEasyExcelWriteUtil.class);
	
	//---------------------------------------------------------------------
	// easyWrite: 简单写
	//---------------------------------------------------------------------
	
	@Deprecated(since = "1.0")
	public static <T> void easyWrite03(
			List<T> dataSource, String pathName, Class<?> headClass
	) {
		easyWrite(dataSource, pathName, defaultCreateSheetName(), headClass, ExcelTypeEnum.XLS, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	public static <T> void easyWrite07(
			List<T> dataSource, String pathName, Class<?> headClass
	) {
		easyWrite(dataSource, pathName, defaultCreateSheetName(), headClass, ExcelTypeEnum.XLSX, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void easyWrite03(
			List<T> dataSource, String pathName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		easyWrite(dataSource, pathName, defaultCreateSheetName(), headClass, ExcelTypeEnum.XLS, converters, writeHandlers);
	}
	
	public static <T> void easyWrite07(
			List<T> dataSource, String pathName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		easyWrite(dataSource, pathName, defaultCreateSheetName(), headClass, ExcelTypeEnum.XLSX, converters, writeHandlers);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void easyWrite03(
			List<T> dataSource, String pathName, String sheetName, Class<?> headClass
	) {
		easyWrite(dataSource, pathName, sheetName, headClass, ExcelTypeEnum.XLS, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	public static <T> void easyWrite07(
			List<T> dataSource, String pathName, String sheetName, Class<?> headClass
	) {
		easyWrite(dataSource, pathName, sheetName, headClass, ExcelTypeEnum.XLSX, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void easyWrite03(
			List<T> dataSource, String pathName, String sheetName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		easyWrite(dataSource, pathName, sheetName, headClass, ExcelTypeEnum.XLS, converters, writeHandlers);
	}
	
	public static <T> void easyWrite07(
			List<T> dataSource, String pathName, String sheetName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		easyWrite(dataSource, pathName, sheetName, headClass, ExcelTypeEnum.XLSX, converters, writeHandlers);
	}
	
	private static <T> void easyWrite(
			List<T> dataSource, String pathName, String sheetName, Class<?> headClass, ExcelTypeEnum fileType,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		BaseObjectUtil.isNull(dataSource, pathName, sheetName, headClass);
		
		var builder = EasyExcel
											.write(pathName, headClass)
											.inMemory(Boolean.TRUE)
											.excelType(fileType)
											.sheet(0,sheetName);
		converters.forEach(builder :: registerConverter);
		writeHandlers.forEach(builder :: registerWriteHandler);
		
		builder.doWrite(dataSource);
		LOGGER.debug("{}", "ExcelWriter 已关闭 IO");
	}
	
	//---------------------------------------------------------------------
	// flexibleWrite: 灵活写
	//---------------------------------------------------------------------
	
	@Deprecated(since = "1.0")
	public static <T> void flexibleWrite03(
			List<T> dataSource, String pathName, Class<?> headClass
	) {
		flexibleWrite(dataSource, pathName, headClass, defaultCreateSheetName(), ExcelTypeEnum.XLS, WRITE_MAX_ENTRIES_03_VERSION, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	public static <T> void flexibleWrite07(
			List<T> dataSource, String pathName, Class<?> headClass
	) {
		flexibleWrite(dataSource, pathName, headClass, defaultCreateSheetName(), ExcelTypeEnum.XLSX, WRITE_MAX_ENTRIES_07_VERSION, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void flexibleWrite03(
			List<T> dataSource, String pathName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		flexibleWrite(dataSource, pathName, headClass, defaultCreateSheetName(), ExcelTypeEnum.XLS, WRITE_MAX_ENTRIES_03_VERSION, converters, writeHandlers);
	}
	
	public static <T> void flexibleWrite07(
			List<T> dataSource, String pathName, Class<?> headClass,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		flexibleWrite(dataSource, pathName, headClass, defaultCreateSheetName(), ExcelTypeEnum.XLSX, WRITE_MAX_ENTRIES_07_VERSION, converters, writeHandlers);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void flexibleWrite03(
			List<T> dataSource, String pathName, Class<?> headClass, String sheetName
	) {
		flexibleWrite(dataSource, pathName, headClass, sheetName, ExcelTypeEnum.XLS, WRITE_MAX_ENTRIES_03_VERSION, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	public static <T> void flexibleWrite07(
			List<T> dataSource, String pathName, Class<?> headClass, String sheetName
	) {
		flexibleWrite(dataSource, pathName, headClass, sheetName, ExcelTypeEnum.XLSX, WRITE_MAX_ENTRIES_07_VERSION, EMPTY_CONVERTERS, EMPTY_WRITE_HANDLERS);
	}
	
	@Deprecated(since = "1.0")
	public static <T> void flexibleWrite03(
			List<T> dataSource, String pathName, Class<?> headClass, String sheetName,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		flexibleWrite(dataSource, pathName, headClass, sheetName, ExcelTypeEnum.XLS, WRITE_MAX_ENTRIES_03_VERSION, converters, writeHandlers);
	}
	
	public static <T> void flexibleWrite07(
			List<T> dataSource, String pathName, Class<?> headClass, String sheetName,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		flexibleWrite(dataSource, pathName, headClass, sheetName, ExcelTypeEnum.XLSX, WRITE_MAX_ENTRIES_07_VERSION, converters, writeHandlers);
	}
	
	private static final Integer DEFAULT_WORKSHEET_STORAGE_ENTRY = 200_000;
	
	public static <T> void flexibleWrite(
			List<T> dataSource, String pathName, Class<?> headClass, String sheetName,
			ExcelTypeEnum fileType, Integer worksheetStorageEntry,
			List<? extends Converter<?>> converters, List<? extends WriteHandler> writeHandlers
	) {
		BaseObjectUtil.isNull(dataSource, pathName, sheetName, headClass, fileType, converters, writeHandlers);
		
		var cvsTemp = worksheetStorageEntry;
		worksheetStorageEntry = DEFAULT_WORKSHEET_STORAGE_ENTRY > worksheetStorageEntry ? DEFAULT_WORKSHEET_STORAGE_ENTRY : worksheetStorageEntry;

		if (Objects.equals(ExcelTypeEnum.XLSX,fileType) && pathName.endsWith(ExcelTypeEnum.XLSX.getValue())) {
			worksheetStorageEntry = worksheetStorageEntry > WRITE_MAX_ENTRIES_07_VERSION ? WRITE_MAX_ENTRIES_07_VERSION : worksheetStorageEntry;
		}
		else if (Objects.equals(ExcelTypeEnum.XLS,fileType) &&pathName.endsWith(ExcelTypeEnum.XLS.getValue())) {
			worksheetStorageEntry = worksheetStorageEntry > WRITE_MAX_ENTRIES_03_VERSION ? WRITE_MAX_ENTRIES_03_VERSION : worksheetStorageEntry;
		}
		else if (Objects.equals(ExcelTypeEnum.CSV,fileType) &&pathName.endsWith(ExcelTypeEnum.CSV.getValue())) {
			worksheetStorageEntry = cvsTemp;
		}
		else {
			throw new IllegalArgumentException(pathName + " 和 " + fileType + " 不对应, 或是" + pathName + "这种格式不被支持");
		}
		
		var builder = EasyExcel
											.write(pathName, headClass)
											.excelType(fileType);
		converters.forEach(builder :: registerConverter);
		writeHandlers.forEach(builder :: registerWriteHandler);
		
		splittingDataSourceIntoMultipleAndWritingToMultipleSheets(dataSource, sheetName, builder, worksheetStorageEntry);
	}
	
	private static <T> void splittingDataSourceIntoMultipleAndWritingToMultipleSheets(
			List<T> dataSource, String sheetName, ExcelWriterBuilder builder, Integer maxRow
	) {
		var excelWriter = builder.build();
		var dataSize = dataSource.size();
		var reasonableWriteCount = (dataSize / maxRow) + (dataSize % maxRow == 0 ? 0 : 1);
		
		try {
			if (reasonableWriteCount == 1) {
				var writeSheet = EasyExcel
												.writerSheet(0,sheetName)
												.build();
				excelWriter.write(dataSource, writeSheet);
			} else {
				List<T> splitData;
				var begin = 0;
				int end = maxRow;
				for(int i = 0; i < reasonableWriteCount; i++) {
					splitData = new ArrayList<>(maxRow - 1);
					if (maxRow > 1) {
						end--;
					}
					splitData.addAll(dataSource.subList(begin, Math.min(end, dataSize)));
					begin = end;
					end += maxRow;
					
					var writeSheet = EasyExcel
													.writerSheet(i, sheetName + "_" + (i + 1))
													.build();
					excelWriter.write(splitData, writeSheet);
				}
			}
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
				LOGGER.debug("{}", "ExcelWriter 已关闭 IO");
			}
		}
	}
}
