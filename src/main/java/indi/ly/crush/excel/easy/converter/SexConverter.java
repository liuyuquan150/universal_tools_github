package indi.ly.crush.excel.easy.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import indi.ly.crush.enumeration.SexEnum;
import indi.ly.crush.util.base.BaseObjectUtil;
import indi.ly.crush.util.enumeration.BaseEnumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <h2><span style="color: red;">性别转换器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "SexConverter")
public class SexConverter
		implements Converter<Integer> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SexConverter.class);
	
	@Override
	public Class<?> supportJavaTypeKey() {
		return Integer.class;
	}
	
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}
	
	@Override
	public Integer convertToJavaData(ReadCellData<?> readCellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		var readCellDataStringValue = readCellData.getStringValue();
		var sexEnum = BaseEnumUtil.getEnumConstantByKey(readCellDataStringValue, SexEnum.class);
		if (BaseObjectUtil.notEquals(sexEnum.getSexValue(), readCellDataStringValue)) {
			throw new IllegalArgumentException("单元格内容 [" + readCellDataStringValue  + "] 是个不合法的性别描述.");
		}
		return sexEnum.getSexCode();
	}
	
	@Override
	public Integer convertToJavaData(ReadConverterContext<?> readConverterContext) throws Exception {
		return Converter.super.convertToJavaData(readConverterContext);
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(Integer javaObjectPropertiesValue, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) {
		return new WriteCellData<>(BaseEnumUtil.getValueByKey(javaObjectPropertiesValue, SexEnum.class));
	}
	
	@Override
	public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> writeConverterContext) throws Exception {
		return Converter.super.convertToExcelData(writeConverterContext);
	}
}
