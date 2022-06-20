package indi.ly.crush.excel.easy.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import indi.ly.crush.excel.easy.init.ExcelEntityInfoContainer;
import indi.ly.crush.excel.easy.listener.GenericExcelListener;
import indi.ly.crush.excel.easy.model.ExcelRowListenerResult;
import indi.ly.crush.excel.easy.util.BaseEasyExcelUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">{@link GenericExcelListener} 工作表写入程序</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class GenericSheetWriteHandler
		implements SheetWriteHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericSheetWriteHandler.class);
	private final Supplier<String> message;
	
	public GenericSheetWriteHandler(Supplier<String> message) {
		this.message = Objects.requireNonNull(message);
	}
	
	@Override
	public void beforeSheetCreate(SheetWriteHandlerContext sheetWriteHandlerContext) {
		SheetWriteHandler.super.beforeSheetCreate(sheetWriteHandlerContext);
	}

	@Override
	public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder)
	{}
	
	@Override
	public void afterSheetCreate(SheetWriteHandlerContext sheetWriteHandlerContext) {
		SheetWriteHandler.super.afterSheetCreate(sheetWriteHandlerContext);
	}
	

	@Override
	public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
		var headRowNumber = BaseEasyExcelUtil.currentSheetHasSeveralRowHeaders(ExcelRowListenerResult.class);
		var currentSheet = writeSheetHolder.getSheet();
		var currentWorkbook = currentSheet.getWorkbook();
		
		/*
			设置创建的单元格的样式 — 文本居中、红色背景.
		 */
		var cellStyle = currentWorkbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		
		var newRow = currentSheet.createRow(headRowNumber);
		var newCell = newRow.createCell(0, CellType.STRING);
		newCell.setCellValue("整个导入结果:   " + this.message.get());
		newCell.setCellStyle(cellStyle);
		
		/*
			找最后一个单元格的索引.
		 */
		final int[] lastColIndex = {0};
		var v = ExcelEntityInfoContainer.entityInfo.get(ExcelRowListenerResult.class);
		v.forEach((filedName, index) -> {
			if (lastColIndex[0] <= index) {
				lastColIndex[0] = index;
			}
		});
		
		/*
			添加合并单元格区域
		 */
		var cellRangeAddress = new CellRangeAddress(headRowNumber, headRowNumber, 0, lastColIndex[0] - 1);
		currentSheet.addMergedRegionUnsafe(cellRangeAddress);
	}
}
