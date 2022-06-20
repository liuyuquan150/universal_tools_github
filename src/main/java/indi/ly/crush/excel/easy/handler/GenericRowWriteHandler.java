package indi.ly.crush.excel.easy.handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import indi.ly.crush.excel.easy.listener.GenericExcelListener;
import indi.ly.crush.excel.easy.model.ExcelRowListenerResult;
import indi.ly.crush.excel.easy.util.BaseEasyExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2><span style="color: red;">{@link GenericExcelListener} 行写入程序</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class GenericRowWriteHandler
		implements RowWriteHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericRowWriteHandler.class);

	@Override
	public void beforeRowCreate(RowWriteHandlerContext rowWriteHandlerContext) {
		RowWriteHandler.super.beforeRowCreate(rowWriteHandlerContext);
	}

	@Override
	public void beforeRowCreate(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			Integer rowIndex, Integer relativeRowIndex, Boolean isHead
	) {}
	
	@Override
	public void afterRowCreate(RowWriteHandlerContext rowWriteHandlerContext) {
		RowWriteHandler.super.afterRowCreate(rowWriteHandlerContext);
	}
	
	@Override
	public void afterRowCreate(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			Row row, Integer relativeRowIndex, Boolean isHead
	) {
		RowWriteHandler.super.afterRowCreate(writeSheetHolder, writeTableHolder, row, relativeRowIndex, isHead);
	}
	
	@Override
	public void afterRowDispose(RowWriteHandlerContext rowWriteHandlerContext) {
		RowWriteHandler.super.afterRowDispose(rowWriteHandlerContext);
	}
	
	/**
	 * <p>
	 *     在对行的所有操作完成后, 创建该行的批注.
	 * </p>
	 *
	 * @param writeSheetHolder 工作表持有者.
	 * @param writeTableHolder 工作表持有者, 不使用工作表 “写入” 时为 {@code null}.
	 * @param row              当前行.
	 * @param relativeRowIndex 相对行索引(距离多少行后开始, 也就是开头空几行), 在填充数据的情况下为 {@code null}.
	 * @param isHead           当前行是否是表头, 在填充数据的情况下始终为 {@code false}.
	 */
	@Override
	public void afterRowDispose(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			Row row, Integer relativeRowIndex, Boolean isHead
	) {
		var headRowNumber = BaseEasyExcelUtil.currentSheetHasSeveralRowHeaders(ExcelRowListenerResult.class);
		if (isHead && (row.getRowNum() + 1 == headRowNumber)) {
			var cellIndex = 4;
			var rowIndex = 1;
			var currentSheet = writeSheetHolder.getSheet();
			var drawing = currentSheet.createDrawingPatriarch();
			var xssfClientAnchor = new XSSFClientAnchor(0, 0, 0, 0, cellIndex, rowIndex, cellIndex + 1, rowIndex + 1);
	
			var microfilm = drawing.createCellComment(xssfClientAnchor);
			var xssfRichTextString = new XSSFRichTextString("格式:\n业务错误列的索引-业务错误列的中文描述");
			microfilm.setString(xssfRichTextString);
			var currentRow = currentSheet.getRow(rowIndex);
			var currentCell = currentRow.getCell(cellIndex);
			currentCell.setCellComment(microfilm);
		}
	}
}
