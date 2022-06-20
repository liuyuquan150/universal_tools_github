package indi.ly.crush.excel.easy.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.handler.WriteHandler;
import indi.ly.crush.enumeration.SymbolEnum;
import indi.ly.crush.util.base.BaseLocalDateTimeUtil;
import indi.ly.crush.util.base.BaseUuIdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <h2><span style="color: red;">EasyExcel 基础工具</span></h2>
 * 
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseEasyExcelUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEasyExcelUtil.class);
	
	protected static final Integer WRITE_MAX_ENTRIES_03_VERSION = 65535;
	protected static final Integer WRITE_MAX_ENTRIES_07_VERSION = 1048575;
	protected static final List<? extends Converter<?>> EMPTY_CONVERTERS = List.of();
	protected static final List<? extends WriteHandler> EMPTY_WRITE_HANDLERS = List.of();
	
	public static Integer currentSheetHasSeveralRowHeaders(Class<?> modelClass) {
		var maxHeadRows = 0;
		for (var declaredField : modelClass.getDeclaredFields()) {
			declaredField.setAccessible(true);
			if (declaredField.isAnnotationPresent(ExcelProperty.class)) {
				var excelPropertyAnnotation = declaredField.getAnnotation(ExcelProperty.class);
				var headRows = excelPropertyAnnotation.value().length;
				if (maxHeadRows < headRows) {
					maxHeadRows = headRows;
				}
			}
		}
		LOGGER.debug("{} 对应的工作表的列头行数是 [{}]", modelClass.getName(), maxHeadRows);
		return maxHeadRows;
	}
	
	protected static String defaultCreateSheetName() {
		var now = BaseLocalDateTimeUtil.format();
		var uuid=  BaseUuIdUtil.randomUuId();
		return String.join(SymbolEnum.HYPHEN.getValue(), now, uuid);
	}
}
