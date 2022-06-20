package indi.ly.crush.api.function;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * <h2><span style="color: red;">可序列化的 {@link Predicate Predicate}</span></h2>
 * 
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@FunctionalInterface
public interface SerializablePredicate<T>
		extends Predicate<T>, Serializable {}
