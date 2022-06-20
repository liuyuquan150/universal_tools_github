package indi.ly.crush.util.base;

import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">字节码工具</span></h2>
 * <p>
 *     继承于 {@link ClassUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseClassUtil
		extends ClassUtils {
	public static <T> Boolean isNotInstance(Class<?> clazz, T obj) {
		return !clazz.isInstance(obj);
	}
	
	
	/**
	 * <p>
	 *     将一个 {@link Object} 对象(<em>{@code conversionTargetObj}</em>)转换成一个{@link List 列表},
	 *     其{@link List 列表}中元素的数据类型为 {@code genericTypeClass},
	 *     并消除编译器发出的 ”转换未经检查“ 警告. <br /> <br />
	 *
	 *
	 *     这是一段更容易理解的早先代码: <br />
	 *
	 *     <pre>{@code
	 *                  var afterConversion = new ArrayList<T>();
	 *                  if (conversionTargetObj instanceof List<?>) {
	 *                      for (Object t : ((List<?>) conversionTargetObj)) {
	 *                          afterConversion.add(genericTypeClass.cast(t));
	 *                      }
	 *                      return afterConversion;
	 *                  }
	 *                  throw new IllegalArgumentException("转换目标对象实际也不是一个列表: " + conversionTargetObj.getClass());
	 *     }</pre>
	 * </p>
	 *
	 * @param conversionTargetObj 被转换的目标对象.
	 * @param genericTypeClass    转换目标对象被转换成{@link List 列表}后, 期望此列表元素的 {@link Class} 对象.
	 * @param <T>                 转换目标对象的数据类型.
	 * @param <C>                 转换目标对象被转换成{@link List 列表}后, 期望此列表元素的 {@link Class} 对象的泛型.
	 * @return 一个转换后的 {@link List 列表}.
	 * @apiNote 请保证被转换的目标对象在运行期间的类型是 {@link List}.
	 */
	public static <T, C> List<C> tConversionToList(T conversionTargetObj, Class<C> genericTypeClass) {
		if (conversionTargetObj instanceof List<?>) {
			return ((List<?>) conversionTargetObj)
					.stream()
					.map(genericTypeClass :: cast)
					.collect(Collectors.toList());
		}
		
		throw new IllegalArgumentException("转换目标对象实际也不是一个列表: " + conversionTargetObj.getClass());
	}
	
	public static <T> String getClassName(T t) {
		if (Objects.requireNonNull(t) instanceof Class<?>) {
			return ((Class<?>) t).getName();
		}
		return t.getClass().getName();
	}
	
	
	public static Boolean isNotEnum(Class<?> clazz) {
		return !clazz.isEnum();
	}
}