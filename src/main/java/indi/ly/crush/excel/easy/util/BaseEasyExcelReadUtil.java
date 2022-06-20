package indi.ly.crush.excel.easy.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.read.listener.ReadListener;
import indi.ly.crush.util.base.BaseObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.IntStream;

/**
 * <h2><span style="color: red;">EasyExcel 读工具类</span></h2>
 * <p>
 *     提供了用于将 Excel 文件中的数据读取到内存的便捷方法.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseEasyExcelReadUtil
		extends BaseEasyExcelUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEasyExcelReadUtil.class);
	
	//---------------------------------------------------------------------
	// easyRead: 简单读
	//---------------------------------------------------------------------
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, ReadListener<?> listener
	) {
		easyRead(excelReader, modelClass, true,EMPTY_CONVERTERS, List.of(listener));
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, List<? extends ReadListener<?>> listeners
	) {
		easyRead(excelReader, modelClass, true, EMPTY_CONVERTERS, listeners);
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, List<? extends Converter<?>> converter, ReadListener<?> listener
	) {
		easyRead(excelReader, modelClass, true,converter, List.of(listener));
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, Converter<?> converter, List<? extends ReadListener<?>> listeners
	) {
		easyRead(excelReader, modelClass, true, List.of(converter), listeners);
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, Converter<?> converter, ReadListener<?> listener
	) {
		easyRead(excelReader, modelClass, true, List.of(converter), List.of(listener));
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, List<? extends Converter<?>> converters, List<? extends ReadListener<?>> listeners
	) {
		easyRead(excelReader, modelClass, true, converters, listeners);
	}
	
	public static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, Boolean autoTrim, Converter<?> converter, ReadListener<?> listener
	) {
		easyRead(excelReader, modelClass, autoTrim, List.of(converter), List.of(listener));
	}
	
	private static void easyRead(
			ExcelReader excelReader, Class<?> modelClass, Boolean autoTrim,
			List<? extends Converter<?>> converters, List<? extends ReadListener<?>> listeners
	) {
		BaseObjectUtil.isNull(excelReader, modelClass, autoTrim, listeners, converters);
		Assert.notEmpty(listeners, () -> "请至少保证一个监听器的传入");
		
		var sheetNumber = excelReader
									.excelExecutor()
									.sheetList()
									.size();
		LOGGER.debug("当前工作簿的工作表个数 ------> {}", sheetNumber);
		if (sheetNumber > 0) {
			try {
				IntStream
						.range(0, sheetNumber)
						.mapToObj(EasyExcel :: readSheet)
						.map(excelReaderSheetBuilder -> {
							excelReaderSheetBuilder
									.autoTrim(autoTrim)
									.head(modelClass)
									.headRowNumber(currentSheetHasSeveralRowHeaders(modelClass));
							listeners.forEach(excelReaderSheetBuilder :: registerReadListener);
							converters.forEach(excelReaderSheetBuilder :: registerConverter);
							return excelReaderSheetBuilder.build();
						})
						.peek(readSheet -> LOGGER.debug("ReadSheet ------> {}", readSheet.toString()))
						.forEachOrdered(excelReader :: read);
			} finally {
				excelReader.finish();
				LOGGER.debug("{}", "ExcelReader 已关闭 IO");
			}
		}
	}
}
