package indi.ly.crush.api.function;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * <h2><span style="color: red;">可序列化的 {@link BiConsumer BiConsumer}</span></h2>
 * 
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@FunctionalInterface
public interface SerializableBiConsumer<T, U>
		extends BiConsumer<T, U>, Serializable {}
