package indi.ly.crush.util.base;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">反射工具</span></h2>
 * <p>
 *     继承于 {@link ReflectionUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseReflectionUtil
		extends ReflectionUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseReflectionUtil.class);
	
	/**
	 * <p>
	 *     通过反射创建对象的便捷方法, 允许携带实参列表.
	 * </p>
	 *
	 * @param tClass                要通过反射创建的目标对象的字节码对象.
	 * @param constructorParameters 要通过反射创建的目标对象的构造器实参列表.
	 * @param <T>                   要通过反射创建的目标对象的数据类型.
	 * @return 反射创建的目标对象.
	 * @apiNote
	 * <h3>{@link Class#getDeclaredConstructor( Class[])} 方法对于 {@code null} 的实参传入无法提供预期的构造器</h3>
	 * <p>
	 *     <pre>{@code
	 *                  package indi.ly.crush.model.entity.User;
	 *
	 *                  public class User {
	 *                      private String name;
	 *                      public User(String name) {
	 *                          this.name = name;
	 *                      }
	 *
	 *                      @SneakyThrows
	 *                      public static void main(String[] args) {
	 *                          var tClass = User.class;
	 *                          var name = null;
	 *                          var target = tClass
	 *                                          .getDeclaredConstructor(new Class<?>[]{null})
	 *                                          .newInstance(name);
	 *                      }
	 *                  }
	 *     }</pre>
	 *     上述代码, 会抛出 {@link NoSuchMethodException} 并提示消息: <span style="color: red;">indi.ly.crush.model.entity.User.<init>(null)</span>. <br />
	 *     这种现象并不好, 因为使用者存在误传入 {@code null} 作为实参, 是作为实参列表一部分存在的可能. <br />
	 *     抛出的 {@link NoSuchMethodException} 异常不是一个预期错误, 应当仍是 {@code null} 作为实参,
	 *     凭此实参列表反射创建对象后, 由对象内部自身行为所抛出的异常才是预期的.
	 * </p>
	 * <br /> <br />
	 *
	 * <h3>实现携带实参列表通过反射创建对象的思路</h3>
	 * <p>
	 *     由于 {@link Class#getDeclaredConstructor( Class[])} 方法对于 {@code null} 的实参传入无法提供预期的构造器,
	 *     因此我们需要另寻途径(<em>思路如下</em>): <br />
	 *     <ol>
	 *         <li>
	 *             要反射创建的目标对象的字节码对象是已知的.
	 *         </li>
	 *         <li>
	 *             要通过反射创建的目标对象的构造器实参列表是已知的.
	 *         </li>
	 *         <li>
	 *             由于目标对象的字节码对象已知, 因此可以拿到所有的构造器对象.
	 *         </li>
	 *         <li>
	 *             虽然 {@link Class#getDeclaredConstructor( Class[])} 方法对于 {@code null} 的实参传入无法提供预期的构造器,
	 *             但是目标对象的构造器实参列表是已知的, 实参的个数可以确定.
	 *             然后迭代所有构造器对象, 筛选出形参列表个数与实参列表个数一致的构造器对象.
	 *         </li>
	 *         <li>
	 *             仅仅只是个数一致, 不能保证通过反射创建的对象一定是使用者想要的. <br />
	 *             还存在着 “列表个数一致, 但类型互不相同” 的情况: <br />
	 *             <pre>{@code
	 *                          package indi.ly.crush.model.entity.User;
	 *                          public class User {
	 *                              private String name;
	 *                              private Integer age;
	 *                              private Character sex;
	 *
	 *                              public User(String name, Integer age, Character sex) {
	 *                                  this.name = name;
	 *                                  this.age = age;
	 *                                  this.sex = sex;
	 *                              }
	 *
	 *                              public User(String name, Character sex, Integer age) {
	 *                                  this.name = name;
	 *                                  this.sex = sex;
	 *                                  this.age = age;
	 *                              }
	 *                          }
	 *             }</pre>
	 *             像上述这种类结构, 就还需要进行参数类型的匹配来保证通过反射创建的对象一定是使用者想要的.
	 *         </li>
	 *     </ol>
	 * </p>
	 * <br />
	 *
	 * <h3>{@link Class#getDeclaredConstructors()} 方法的注意事项</h3>
	 * <p>
	 *     {@link Class#getDeclaredConstructors()} 方法虽然可以获取所有构造器对象, 但数组内的构造器对象并不是按照定义类的构造器结构顺序所存储的.
	 * </p>
	 */
	public static <T> T newInstance(Class<? extends T> tClass, Object... constructorParameters) {
		if (BaseObjectUtil.isEmpty(constructorParameters)) {
			LOGGER.debug("反射调用无参构造器, 字节码对象: {}、构造器实参列表: {}", tClass, Arrays.toString(constructorParameters));
			return newInstance(tClass);
		}
		
		Class<?>[] constructorParametersTypes = Arrays
												.stream(constructorParameters)
												.peek(constructorParameter -> LOGGER.debug("{}", constructorParameter))
												.map(constructorParameter ->BaseObjectUtil.isNotEmpty(constructorParameter) ? constructorParameter.getClass() : (Class<?>) null)
												.peek(constructorParametersType -> LOGGER.debug("{}", constructorParametersType))
												.toArray(Class<?>[] :: new);
		LOGGER.debug("构造器实参数组: {}", Arrays.toString(constructorParameters));
		LOGGER.debug("构造器实参字节码对象数组: {}", Arrays.toString(constructorParametersTypes));
		
		
	
		var declaredConstructors = tClass.getDeclaredConstructors();
		
		var allNumberConsistentParameterTypes = Arrays
																.stream(declaredConstructors)
																.map(Constructor :: getParameterTypes)
																.filter(parameterTypes -> parameterTypes.length == constructorParametersTypes.length)
																.collect(Collectors.toSet());
		LOGGER.debug("{}", allNumberConsistentParameterTypes);
		
		if (allNumberConsistentParameterTypes.size() == 0) {
			throw new IllegalArgumentException("所传入的实参列表, 并不能找到匹配数目的构造器. 实参列表: " + Arrays.toString(constructorParameters));
		}
		
		if (allNumberConsistentParameterTypes.size() == 1) {
			return newInstance(tClass,(Class<?>[]) allNumberConsistentParameterTypes.toArray()[0], constructorParameters);
		}
		
		Class<?>[] constructorParameterTypes = null;
		while (allNumberConsistentParameterTypes.size() > 0) {
			// 取出第一个形参字节码数组, 进行接下来的类型匹配
			var parameterTypes = allNumberConsistentParameterTypes
											.stream()
											.findFirst()
											.get();
			
			// 默认匹配成功
			var matchingSuccess = true;
			for (int i = 0, length = parameterTypes.length; i < length; i++) {
				// 对于这种情况(形参字节码数组只有一个形参, 并且传入的唯一实参是 null), 这说明当前形参字节码数组可以认为是预期的.
				if (length == 1 && constructorParametersTypes[i] == null) {
					constructorParameterTypes = parameterTypes;
				}
				
				// 为 null 的实参是需要进行排除的. 如果实参不为 null, 而字节码与形参字节码不匹配, 则认为当前形参字节码数组不是预期的.
				if (constructorParametersTypes[i] != null && constructorParametersTypes[i] != parameterTypes[i]) {
					// 删除, 是为了保证在下一次迭代中取出的形参字节码数组不会重复
					allNumberConsistentParameterTypes.remove(parameterTypes);
					matchingSuccess = false;
					break;
				}
			}
			
			if (matchingSuccess) {
				constructorParameterTypes = parameterTypes;
			}
			
			// 找到预期的形参字节码数组之后, 则可以不再进行查找, 避免重复多余的迭代操作.
			if (constructorParameterTypes != null) {
				break;
			}
		}
		
		if (constructorParameterTypes == null) {
			throw new IllegalArgumentException("没有对应的构造器");
		}
		
		return newInstance(tClass, constructorParameterTypes, constructorParameters);
	}
	
	@SneakyThrows(value = {
			InstantiationException.class, IllegalAccessException.class,
			InvocationTargetException.class, NoSuchMethodException.class
	})
	public static <T> T newInstance(Class<? extends T> tClass) {
		return tClass
				.getDeclaredConstructor()
				.newInstance();
	}
	
	@SneakyThrows(value = NoSuchFieldException.class)
	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		Assert.hasLength(fieldName, () -> "fieldName is null");
		var declaredField = clazz.getDeclaredField(fieldName);
		declaredField.setAccessible(true);
		return declaredField;
	}
	
	@SneakyThrows(value = {
			InstantiationException.class, IllegalAccessException.class,
			InvocationTargetException.class, NoSuchMethodException.class
	})
	private static <T> T newInstance(Class<? extends T> tClass, Class<?>[] constructorParameterTypes, Object[] constructorParameters) {
		return tClass
				.getDeclaredConstructor(constructorParameterTypes)
				.newInstance(constructorParameters);
	}
}