package indi.ly.crush.excel.easy.model;

import indi.ly.crush.util.base.BaseMapUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <h2><span style="color: red;">Excel 行检查结果</span></h2>
 * <p>
 *     Excel 文件的每一行都是一个实体对象,
 *     对 Excel 文件行进行检查实际就是对某个代表 Excel 文件的实体对象进行属性的检查.
 * </p>
 *
 * @see ExcelColumnCheckResult
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class ExcelRowCheckResult
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     错误列地图: {@link ExcelColumnCheckResult#errorColumnIndex Key} - {@link ExcelColumnCheckResult#promptMessage Value}. <br />
	 *
	 *     若是存储的 K-V 个数为 0, 则说明 Excel 文件当前行是检查通过的(<em>实体对象所有属性都检查通过</em>).
	 * </p>
	 */
	private Map<Integer, String> errorColumnMap;

	private ExcelRowCheckResult() {}
	
	private ExcelRowCheckResult(Map<Integer, String> errorColumnMap) {
		this.errorColumnMap = errorColumnMap;
	}
	
	public static ExcelRowCheckResult check(List<ExcelColumnCheckResult> excelColumnCheckResults) {
		if (Objects.requireNonNull(excelColumnCheckResults).isEmpty()) {
			return new ExcelRowCheckResult(new LinkedHashMap<>(0));
		}
		
		var excelRowCheckResult = new ExcelRowCheckResult();
		var initialCapacity = BaseMapUtil.rationalInitial(excelColumnCheckResults.size());
		excelRowCheckResult.errorColumnMap = new LinkedHashMap<>(initialCapacity);
		excelColumnCheckResults
				.stream()
				.filter(excelColumnCheckResult -> !excelColumnCheckResult.checkPassed())
				.forEachOrdered(e -> excelRowCheckResult.errorColumnMap.put(e.errorColumnIndex(), e.promptMessage()));
		
		return excelRowCheckResult;
	}
	
	public Map<Integer, String> errorColumnMap() {
		return Objects.requireNonNull(errorColumnMap);
	}
	
	@Override
	public String toString() {
		return "ExcelRowCheckResult{" + "errorColumnMap=" + errorColumnMap + '}';
	}
}
