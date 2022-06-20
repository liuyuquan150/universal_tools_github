package indi.ly.crush.excel.easy.model;

import com.alibaba.excel.annotation.ExcelProperty;
import indi.ly.crush.excel.easy.init.ExcelEntityInfoContainer;
import indi.ly.crush.util.base.BaseObjectUtil;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">Excel 列检查结果</span></h2>
 *
 * @see ExcelRowCheckResult
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public class ExcelColumnCheckResult
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     Excel 文件当前行的某列是检查通过的.
	 * </p>
	 */
	private Boolean isCheckPassed;
	/**
	 * <p>
	 *     Excel 文件当前行的某列检查不通过, 它的索引值 ———— Excel 实体某属性上的 {@link ExcelProperty#index()}.
	 * </p>
	 */
	private Integer errorColumnIndex;
	/**
	 * <p>
	 *     Excel 文件当前行的某列(<em>实体对象某属性</em>)检查不通过, 要设置的提示消息.
	 * </p>
	 */
	private String promptMessage;
	
	public static <T> ExcelColumnCheckResult check(
			Class<?> clazz, T filedValue, String filedName, Supplier<String> promptMessageSupplier, Predicate<T> checkBehaviorPredicate
	) {
		BaseObjectUtil.isNull(filedName, promptMessageSupplier, checkBehaviorPredicate);
		Assert.hasLength(filedName,() -> "fileName not has length");
		
		var excelColumnCheckResult = new ExcelColumnCheckResult();
		var checkResult = checkBehaviorPredicate.test(filedValue);
		excelColumnCheckResult.isCheckPassed = checkResult;
		if (checkResult) {
			var entityFieldInfo = ExcelEntityInfoContainer.entityInfo.get(clazz);
			excelColumnCheckResult.errorColumnIndex =  entityFieldInfo.get(filedName);
			excelColumnCheckResult.promptMessage = promptMessageSupplier.get();
		}
		
		return excelColumnCheckResult;
	}
	
	public Boolean checkPassed() {
		return Objects.requireNonNull(isCheckPassed);
	}
	
	public Integer errorColumnIndex() {
		return Objects.requireNonNull(errorColumnIndex);
	}
	
	public String promptMessage() {
		return Objects.requireNonNull(promptMessage);
	}
	
	@Override
	public String toString() {
		return "ExcelColumnCheckResult{" + "isCheckPassed=" + isCheckPassed + ", errorColumnIndex=" + errorColumnIndex
				+ ", promptMessage='" + promptMessage + '\'' + '}';
	}
}
