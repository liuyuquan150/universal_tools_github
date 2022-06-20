package indi.ly.crush.util.base;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">对象工具</span></h2>
 * <p>
 *     继承于 {@link ObjectUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseObjectUtil
		extends ObjectUtils {
	public static <T> Boolean isNotEmpty(T obj) {
		return !ObjectUtils.isEmpty(obj);
	}
	
	public static <T1, T2> Boolean notEquals(T1 objOne, T2 objTwo) {
		return !Objects.equals(objOne, objTwo);
	}
	
	//---------------------------------------------------------------------
	// isNull
	//---------------------------------------------------------------------
	
	public static <T> void isNull(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
	}
	
	@SafeVarargs
	public static <T> void isNull(T... t) {
		var ts = Optional.of(t).get();
		
		Arrays
				.stream(ts)
				.forEach(BaseObjectUtil :: isNull);
	}
	
	public static <T> T isThrowException(T t, Predicate<T> tPredicate, Supplier<? extends Throwable> exceptionSupplier)
			throws Throwable {
		if (tPredicate.test(t)) {
			throw exceptionSupplier.get();
		}
		return t;
	}
}
