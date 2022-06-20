package indi.ly.crush.excel.easy.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2><span style="color: red;">{@link LocalDateTime} 属性转换器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "LocalDateTimePropertyConverter")
public class LocalDateTimePropertyConverter
		implements Converter<LocalDateTime> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimePropertyConverter.class);
	private final DateTimeFormatter dateTimeFormatter;
	
	public LocalDateTimePropertyConverter() {
		this("yyyy年MM月dd日 HH时mm分ss秒");
	}
	
	public LocalDateTimePropertyConverter(String pattern) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	}
	
	@Override
	public Class<?> supportJavaTypeKey() {
		return LocalDateTime.class;
	}
	
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}
	
	@Override
	public LocalDateTime convertToJavaData(ReadCellData<?> readCellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		var readCellDataStringValue = readCellData.getStringValue();
		return LocalDateTime.parse(readCellDataStringValue, this.dateTimeFormatter);
	}
	
	@Override
	public LocalDateTime convertToJavaData(ReadConverterContext<?> readConverterContext) throws Exception {
		return Converter.super.convertToJavaData(readConverterContext);
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(LocalDateTime javaObjectPropertiesValue, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		return new WriteCellData<>(javaObjectPropertiesValue.format(this.dateTimeFormatter));
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(WriteConverterContext<LocalDateTime> writeConverterContext) throws Exception {
		return Converter.super.convertToExcelData(writeConverterContext);
	}
}
