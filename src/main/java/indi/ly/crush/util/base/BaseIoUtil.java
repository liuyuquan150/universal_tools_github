package indi.ly.crush.util.base;

import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * <h2><span style="color: red;">IO工具</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseIoUtil {
	
	public static void close(Closeable... closeableIoStreams) {
		var optionalCloseables = Optional
												.of(closeableIoStreams)
												.get();
		
		Arrays
				.stream(optionalCloseables)
				.map(Optional :: of)
				.forEach(optionalCloseable -> optionalCloseable.ifPresent(BaseIoUtil :: close));
	}
	
	@SneakyThrows(value = IOException.class)
	public static void close(Closeable closeableIoStream) {
		if (Objects.requireNonNull(closeableIoStream) instanceof Writer) {
			((Writer) closeableIoStream).flush();
		}
		closeableIoStream.close();
	}
}
