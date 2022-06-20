package indi.ly.crush.api.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * <h2><span style="color: red;">可序列化的 {@link Function Function}</span></h2>
 * 
 * @param <R> 语义继承自 {@link Function} 接口中定义的泛型 R.
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@FunctionalInterface
public interface SerializableFunction<T, R>
		extends Function<T, R>, Serializable {}