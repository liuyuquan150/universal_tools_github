package indi.ly.crush.api.function;

import indi.ly.crush.util.base.BaseObjectUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <h2><span style="color: red;">可抛出异常的 {@link Function}</span></h2>
 * 
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@FunctionalInterface
public interface ThrowableFunction<T, R>
		extends Serializable {
	
	R apply(T t) throws Throwable;
	
	static <T, R> Function<T, R> applyWrapper(ThrowableFunction<T, R> tf) {
		BaseObjectUtil.isNull(tf);
		return t -> {
			try {
				return tf.apply(t);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	static <T, R> Function<T, R> applyWrapper(ThrowableFunction<T, R> tf, Function<Throwable, R> hf) {
		BaseObjectUtil.isNull(tf, hf);
		return t -> {
			try {
				return tf.apply(t);
			} catch (Throwable e) {
				return hf.apply(e);
			}
		};
	}
}
