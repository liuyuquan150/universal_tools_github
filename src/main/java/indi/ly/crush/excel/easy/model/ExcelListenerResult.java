package indi.ly.crush.excel.easy.model;

import indi.ly.crush.http.response.ResponseResult;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <h2><span style="color: red;">Excel 监听器结果</span></h2>
 *
 * @see ExcelRowListenerResult
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class ExcelListenerResult
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     Excel 文件导入成功行数.
	 * </p>
	 */
	private final Integer successRowCount;
	/**
	 * <p>
	 *     Excel 文件导入错误行数 / 失败行数.
	 * </p>
	 */
	private final Integer errorRowCount;
	/**
	 * <p>
	 *     Excel 文件导入, 所有的 "错误行数对象集" / "失败行数对象集".
	 * </p>
	 */
	private final List<ExcelRowListenerResult> excelRowListenerResults;
	/**
	 * <p>
	 *     导出文件路径.
	 * </p>
	 */
	private final String exportFilePath;
	
	public ExcelListenerResult(Integer successRowCount, List<ExcelRowListenerResult> excelRowListenerResults, String exportFilePath) {
		this.successRowCount = successRowCount;
		this.excelRowListenerResults = excelRowListenerResults;
		this.errorRowCount = this.excelRowListenerResults.size();
		this.exportFilePath = exportFilePath;
	}
	
	public Integer getSuccessRowCount() {
		return successRowCount;
	}
	
	public Integer getErrorRowCount() {
		return errorRowCount;
	}
	
	public List<ExcelRowListenerResult> getExcelRowListenerResults() {
		return excelRowListenerResults;
	}
	
	public String getExportFilePath() {
		return exportFilePath;
	}
	

	/**
	 * <p>
	 *     分析监听器结果, 将 Excel 文件的读取情况信息以统一响应的形式返回给前端.
	 * </p>
	 *
	 * @param listenerResult 监听器的监听结果.
	 * @return 一个含 Excel 文件读取情况的统一响应对象.
	 */
	public static ResponseResult<List<ExcelRowListenerResult>> analyzeListenerResult(ExcelListenerResult listenerResult) {
		int successRowCount = listenerResult.getSuccessRowCount();
		int errorRowCount = listenerResult.getErrorRowCount();
		var excelRowListenerResults = listenerResult.getExcelRowListenerResults();
		var exportFilePath = listenerResult.getExportFilePath();
		
		if (successRowCount == 0) {
			return ResponseResult.failure("导入全部失败, 错误信息文件生成位置: " + exportFilePath, excelRowListenerResults);
		}
		else if (errorRowCount == 0) {
			return ResponseResult.ok("导入全部成功");
		}
		var message = String.format("导入部分成功! 成功 [%d] 条, 失败 [%d] 条, 错误信息文件生成位置: %s", successRowCount, errorRowCount, exportFilePath);
		return ResponseResult.ok(message, excelRowListenerResults);
	}
	
	
	@Override
	public String toString() {
		return "ExcelListenerResult{" + "successRowCount=" + successRowCount + ", errorRowCount=" + errorRowCount
				+ ", excelRowListenerResults=" + excelRowListenerResults + ", exportFilePath='" + exportFilePath + '\''
				+ '}';
	}
}
