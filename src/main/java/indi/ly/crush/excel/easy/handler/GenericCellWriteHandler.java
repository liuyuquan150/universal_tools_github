package indi.ly.crush.excel.easy.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import indi.ly.crush.excel.easy.listener.GenericExcelListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <h2><span style="color: red;">{@link GenericExcelListener} 单元格写入程序</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class GenericCellWriteHandler
		implements CellWriteHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericCellWriteHandler.class);
	
	@Override
	public void beforeCellCreate(CellWriteHandlerContext cellWriteHandlerContext) {
		CellWriteHandler.super.beforeCellCreate(cellWriteHandlerContext);
	}
	
	@Override
	public void beforeCellCreate(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
			Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead
	) {}
	
	@Override
	public void afterCellCreate(CellWriteHandlerContext cellWriteHandlerContext) {
		CellWriteHandler.super.afterCellCreate(cellWriteHandlerContext);
	}
	
	@Override
	public void afterCellCreate(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
			Head head, Integer relativeRowIndex, Boolean isHead
	) {}
	
	@Override
	public void afterCellDataConverted(CellWriteHandlerContext cellWriteHandlerContext) {
		CellWriteHandler.super.afterCellDataConverted(cellWriteHandlerContext);
	}
	
	@Override
	public void afterCellDataConverted(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead
	) {}
	
	@Override
	public void afterCellDispose(CellWriteHandlerContext cellWriteHandlerContext) {
		CellWriteHandler.super.afterCellDispose(cellWriteHandlerContext);
	}
	
	/**
	 * <p>
	 *     在单元格上的所有操作都完成后, 设置除 ”头部单元格“ 以及 ”动态添加的单元格“ 外的单元格的文本内容水平居左、垂直居中、文本换行.
	 * </p>
	 *
	 * @param writeSheetHolder 工作表持有者.
	 * @param writeTableHolder 工作表持有者, 不使用工作表 “写入” 时为 {@code null}.
	 * @param cellDataList     Excel 的内部单元格数据集合. 在添加头的情况下, 它是 {@code null}.
	 * @param cell             当前列(单元格).
	 * @param head             头, 在填充数据且没有头部的情况下为 {@code null}.
	 * @param relativeRowIndex 相对行索引(距离多少行后开始, 也就是开头空几行), 在填充数据的情况下为 {@code null}.
	 * @param isHead           当前行是否是表头, 在填充数据的情况下始终为 {@code false}.
	 */
	@Override
	public void afterCellDispose(
			WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead
	) {
		if (isHead) {
			return;
		}
		
		var currentSheet = writeSheetHolder.getSheet();
		var currentWorkbook = currentSheet.getWorkbook();
		var cellStyle = currentWorkbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);
	}
}
