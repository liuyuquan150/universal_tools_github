package indi.ly.crush.api.function;

import indi.ly.crush.util.base.BaseObjectUtil;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * <h2><span style="color: red;">可抛出异常的 {@link BiConsumer}</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@SuppressWarnings(value = "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
@FunctionalInterface
public interface ThrowableBiConsumer<T, U>
		extends Serializable {
	
	void accept(T t, U u)throws ClassNotFoundException;
	
	static <T, U> BiConsumer<T, U> acceptWrapper(ThrowableBiConsumer<T, U> tbi) {
		BaseObjectUtil.isNull(tbi);
		return (t, u) -> {
			try {
				tbi.accept(t,u);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	static <T, U> @NotNull BiConsumer<T, U> applyWrapper(ThrowableBiConsumer<T, U> tbi, BiConsumer<Throwable, ?> bi) {
		BaseObjectUtil.isNull(tbi, bi);
		return (t, u) -> {
			try {
				tbi.accept(t,u);
			} catch (Throwable e) {
				bi.accept(e, null);
			}
		};
	}
}
