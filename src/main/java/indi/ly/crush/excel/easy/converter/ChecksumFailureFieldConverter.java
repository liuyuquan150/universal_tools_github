package indi.ly.crush.excel.easy.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import indi.ly.crush.enumeration.SymbolEnum;
import indi.ly.crush.excel.easy.model.ExcelRowListenerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <h2><span style="color: red;">{@link ExcelRowListenerResult#checksumFailureField} 属性转换器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "ChecksumFailureFieldConverter")
public class ChecksumFailureFieldConverter
		implements Converter<Map<Integer, String>>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChecksumFailureFieldConverter.class);
	
	@Override
	public Class<?> supportJavaTypeKey() {
		return Map.class;
	}
	
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}
	
	@Override
	public Map<Integer, String> convertToJavaData(ReadCellData<?> readCellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		// 对于 ExcelRowListenerResult 而言, 它只有导出没有导入的需求, 因此 “Excel 对象转 Java 对象” 可以不用实现.
		return null;
	}
	
	@Override
	public Map<Integer, String> convertToJavaData(ReadConverterContext<?> readConverterContext) throws Exception {
		return Converter.super.convertToJavaData(readConverterContext);
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(Map<Integer, String> javaObjectPropertiesValue, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		var cellContent = javaObjectPropertiesValue
										.entrySet()
										.stream()
										.collect(
												StringBuffer :: new,
												(cellContentStringBuffer, entry) ->
														cellContentStringBuffer
																.append(entry.getKey())
																.append(SymbolEnum.HYPHEN.getValue())
																.append(entry.getValue())
																.append("\n"),
												(s1, s2) -> {})
										.toString();
		return new WriteCellData<>(cellContent);
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(WriteConverterContext<Map<Integer, String>> writeConverterContext) throws Exception {
		return Converter.super.convertToExcelData(writeConverterContext);
	}
}
