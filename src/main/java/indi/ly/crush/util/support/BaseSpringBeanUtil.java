package indi.ly.crush.util.support;

import indi.ly.crush.util.base.BaseClassUtil;
import indi.ly.crush.util.base.BaseCollectionUtil;
import indi.ly.crush.util.base.BaseMapUtil;
import indi.ly.crush.util.base.BaseObjectUtil;
import indi.ly.crush.util.base.BaseReflectionUtil;
import indi.ly.crush.util.base.BaseStringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">Spring Bean 工具</span></h2>
 * <p>
 *     继承于 {@link BeanUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseSpringBeanUtil
		extends BeanUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseSpringBeanUtil.class);
	
	
	public static <T> Map<String, Object> objectPropertyCopyToMap(T sourceObj) {
		return objectPropertyCopyToMap(sourceObj, false, false);
	}
	
	/**
	 * <p>
	 *     将对象属性拷贝到地图中. <br /> <br />
	 *
	 *
	 *     对象属性的名称作为地图的 ”K“, 对象属性的值作为地图的 ”V“. <br />
	 *     ”K“ 的数据类型默认是 {@link String}. <br />
	 *     ”V“ 的数据类型保留对象属性的原类型, 这一点不同于 {@link org.apache.commons.beanutils.BeanUtils#describe(Object)}.
	 * </p>
	 * <br />
	 *
	 * @param sourceObj      提供拷贝内容(<em>属性</em>)的源对象.
	 * @param retentionClass 是否保留源对象的包路径信息.
	 * @param excludeNull    是否不拷贝值为 {@code null} 的属性.
	 * @param <T>            提供拷贝内容(<em>属性</em>)的源对象的数据类型.
	 * @return 拷贝之后的地图.
	 * @apiNote
	 * <h3>源对象所属类结构必须是以 {@literal public} 修饰的</h3>
	 * <p>
	 *     本方法是依赖于 {@literal Spring} 中的 {@link BeanUtils} 和 {@link ReflectionUtils} 两个工具类提供的 {@literal api},
	 *     因此使用本方法必须要遵守 {@link BeanUtils} 和 {@link ReflectionUtils} 的要求. <br />
	 *
	 *     在 {@link ReflectionUtils#invokeMethod( Method, Object)} 中, 要求您提供的 Java Bean 所属类必须是一个 {@literal public} 级别的,
	 *     并且需要进行拷贝的属性最好提供了相应的 {@literal setter} 和 {@literal getter} 方法(<em>关于这一点, 参见下述</em>).
	 * </p>
	 * <br />
	 *
	 * <h3>属性必须存在对应的 {@literal getXxx} 方法</h3>
	 * <p>
	 *     如果源对象所属类结构中的属性不存在对应的 {@literal getXxx} 方法, 则该属性不会被进行拷贝至地图中: <br />
	 *     <pre>{@code
	 *                  package indi.ly.crush.model.entity.User;
	 *
	 *                  public class User {
	 *                      private String name;
	 *                      private Integer age;
	 *                      public User(String name, Integer age) {
	 *                          this.name = name;
	 *                          this.age = age;
	 *                      }
	 *                      public String getName() { return name; }
	 *                      public void setName(String name) { this.name = name; }
	 *                  }
	 *     }</pre>
	 *     像这种类结构的源对象, 由于不存在对应的 {@literal getAge} 方法, 因此 {@literal age} 属性不会被进行拷贝: <br />
	 *     <pre>{@code
	 *                  var user = new User("李洋", 25);
	 *                  var copyResult = BaseSpringBeanUtil.objectPropertyCopyToMap(user);
	 *                  // {name=李洋}
	 *                  System.out.println(copyResult);
	 *     }</pre>
	 * </p>
	 * <br />
	 *
	 * <h3>方法参数 {@literal retentionClass} 的解释</h3>
	 * <p>
	 *     以下解释, 采用上述 {@literal User} 类. <br /> <br />
	 *
	 *
	 *     当 {@literal retentionClass} 为 {@code true} 时: <br />
	 *     <pre>{@code
	 *                  var user = new User("李洋", 25);
	 *                  var copyResult = BaseSpringBeanUtil.objectPropertyCopyToMap(user, true);
	 *                  // {name=李洋, class=indi.ly.crush.model.entity.User}
	 *                  System.out.println(copyResult);
	 *     }</pre>
	 *
	 *     当 {@literal retentionClass} 为 {@code false} 时: <br />
	 *     <pre>{@code
	 *                  var user = new User("李洋", 25);
	 *                  var copyResult = BaseSpringBeanUtil.objectPropertyCopyToMap(user);
	 *                  // {name=李洋}
	 *                  System.out.println(copyResult);
	 *     }</pre>
	 * </p>
	 * <br />
	 *
	 * <h3>方法参数 {@literal excludeNull} 的解释</h3>
	 * <p>
	 *     以下解释, 采用上述 {@literal User} 类. <br /> <br />
	 *
	 *
	 *     当 {@literal excludeNull} 为 {@code true} 时: <br />
	 *     <pre>{@code
	 *                  var user = new User("李洋", null);
	 *                  var copyResult = BaseSpringBeanUtil.objectPropertyCopyToMap(user, true);
	 *                  // {name=李洋, class=indi.ly.crush.model.entity.User}
	 *                  System.out.println(copyResult);
	 *     }</pre>
	 *
	 *     当 {@literal excludeNull} 为 {@code false} 时: <br />
	 *     <pre>{@code
	 *                  var user = new User("李洋", null);
	 *                  var copyResult = BaseSpringBeanUtil.objectPropertyCopyToMap(user);
	 *                  // {name=李洋, age=null, class=indi.ly.crush.model.entity.User}
	 *                  System.out.println(copyResult);
	 *     }</pre>
	 * </p>
	 * <br />
	 *
	 * <h3>最初的代码实现</h3>
	 * <p>
	 *     <pre>{@code
	 *                  public static <T> Map<Object, Object> javaBeanConvertMap(T sourceJavaBean) {
	 *                      Assert.notNull(sourceJavaBean, () -> "无效: 传入的 Java Bean 是一个 null");
	 *                      var clazz = sourceJavaBean.getClass();
	 *                      var targetMap = new HashMap<>(clazz.getDeclaredFields().length - 1);
	 *                      var propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
	 *                      Arrays
	 *                              .stream(propertyDescriptors)
	 *                              .forEach(propertyDescriptor -> {
	 *                                  var propertyName = propertyDescriptor.getName();
	 *                                  LOG.trace("属性名称: {}", propertyName);
	 *                                  if (!"class".equals(propertyName)) {
	 *                                      var propertyMethod = propertyDescriptor.getReadMethod();
	 *                                      var propertyValue = ReflectionUtils.invokeMethod(propertyMethod, sourceJavaBean);
	 *                                      LOG.trace("属性值: {}", propertyName);
	 *                                      targetMap.put(propertyName, propertyValue);
	 *                                  }
	 *                              });
	 *                      return targetMap;
	 *                  }
	 *     }</pre>
	 * </p>
	 */
	public static <T> Map<String, Object> objectPropertyCopyToMap(T sourceObj, Boolean retentionClass, Boolean excludeNull) {
		Assert.notNull(sourceObj, () -> "请提供一个不为 null 的源对象, 否则无法对该对象进行属性拷贝至地图中的操作");
		var sourceObjClass = sourceObj.getClass();
		var classStr = "class";
		var resultMap =
				Arrays
				.stream(BeanUtils.getPropertyDescriptors(sourceObjClass))
				.peek(propertyDescriptor -> LOGGER.debug("{}", propertyDescriptor))
				.filter(propertyDescriptor -> BaseObjectUtil.isNotEmpty(propertyDescriptor.getReadMethod()))
				.peek(tempPropertyDescriptor -> LOGGER.debug("{}", tempPropertyDescriptor))
				.map(propertyDescriptor -> new TempPropertyDescriptor(propertyDescriptor, propertyDescriptor.getName()))
				.peek(tempPropertyDescriptor -> LOGGER.debug("{}", tempPropertyDescriptor))
				.filter(tempPropertyDescriptor -> retentionClass || BaseStringUtil.notEquals(classStr, tempPropertyDescriptor.getPropertyName()))
				.peek(tempPropertyDescriptor -> LOGGER.debug("{}", tempPropertyDescriptor))
				.collect(
						() -> new HashMap<String, Object>(BaseMapUtil.rationalInitial(sourceObjClass.getDeclaredFields().length - 1)),
						(resultContainer, tempPropertyDescriptor) -> {
							var propertyDescriptor = tempPropertyDescriptor.getPropertyDescriptor();
							var propertyMethod = propertyDescriptor.getReadMethod();
							var propertyValue = BaseReflectionUtil.invokeMethod(propertyMethod, sourceObj);
							if (excludeNull && BaseObjectUtil.isEmpty(propertyValue)) {
								return;
							}
							resultContainer.put(tempPropertyDescriptor.getPropertyName(), propertyValue);
						},
						(resultContainerOne, resultContainerTwo) -> {}
				);
			
		if (retentionClass) {
			var classValue = resultMap.get(classStr).toString();
			var sourceObjPackagePathStr = classValue.substring(classStr.length() + 1);
			resultMap.put(classStr, sourceObjPackagePathStr);
		}
		
		return resultMap;
	}
	
	@Data
	@AllArgsConstructor
	static class TempPropertyDescriptor {
		private PropertyDescriptor propertyDescriptor;
		private String propertyName;
	}
	
	//---------------------------------------------------------------------
	// 对象拷贝
	//---------------------------------------------------------------------
	
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass) {
		return copyObj(sourceObj, targetClass, (Class<?>) null,(String[]) null);
	}
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, Class<?> editable) {
		return copyObj(sourceObj, targetClass, editable,(String[]) null);
	}
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, String... ignoreProperties) {
		return copyObj(sourceObj, targetClass, (Class<?>) null,ignoreProperties);
	}
	
	private static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, Class<?> operation, String... ignoreProperties) {
		var targetObj = BaseReflectionUtil.newInstance(targetClass);
		Optional
				.ofNullable(operation)
				.ifPresentOrElse(
						editableClass -> copyProperties(sourceObj, targetObj, editableClass),
						() -> copyProperties(sourceObj, targetObj, ignoreProperties)
				);
		return targetObj;
	}
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, Map<String, String> propertiesCopyRule) {
		return copyObj(sourceObj, targetClass, propertiesCopyRule, null, (String[]) null);
	}
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, Map<String, String> propertiesCopyRule, Class<?> operation) {
		return copyObj(sourceObj, targetClass, propertiesCopyRule, operation, (String[]) null);
	}
	
	public static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass, Map<String, String> propertiesCopyRule, String... ignoreProperties) {
		return copyObj(sourceObj, targetClass, propertiesCopyRule, null, ignoreProperties);
	}
	
	private static <S, T> T copyObj(S sourceObj, Class<? extends T> targetClass,
			Map<String, String> propertiesCopyRule, Class<?> operation, String... ignoreProperties) {
		Assert.notNull(sourceObj, () -> "请提供一个有效的源对象, 以便进行拷贝");
		Assert.notNull(targetClass, () -> "请提供一个有效的目标 Class");
		
		var actualOperation = isActualOperationClass(sourceObj, operation);
		var targetObj = BaseReflectionUtil.newInstance(targetClass);
		var ignorePropertiesSet = filterConversionIgnoreProperties(ignoreProperties);
		
		for (var sourcePropertyDescriptor : BeanUtils.getPropertyDescriptors(actualOperation)) {
			var readMethod = sourcePropertyDescriptor.getReadMethod();
			if (BaseObjectUtil.isNotEmpty(readMethod)) {
				var targetPropertyDescriptor = getPropertyDescriptor(sourcePropertyDescriptor,targetClass, propertiesCopyRule, ignorePropertiesSet);
				if (BaseObjectUtil.isNotEmpty(targetPropertyDescriptor)) {
					var writeMethod = targetPropertyDescriptor.getWriteMethod();
					if (BaseObjectUtil.isNotEmpty(writeMethod)) {
						copyToTarget(sourceObj, targetObj, readMethod, writeMethod);
					}
				}
			}
		}
		
		return targetObj;
	}

	private static <S> Class<?> isActualOperationClass(S sourceObj, Class<?> operation) {
		var actualEditable  = sourceObj.getClass();
		if (BaseObjectUtil.isNotEmpty(operation)) {
			Assert.isTrue(operation.isInstance(sourceObj),
					() -> "Source class [%s] not assignable to Editable class [%s]"
							.formatted(sourceObj.getClass().getName(), operation.getName()));
			actualEditable = operation;
		}
		LOGGER.info("Editable Class: {}", actualEditable);
		return actualEditable;
	}
	
	private static <T> PropertyDescriptor getPropertyDescriptor(PropertyDescriptor sourcePropertyDescriptor,
			Class<? extends T> targetClass, Map<String, String> propertiesCopyRule, Set<String> ignorePropertiesSet) {
		PropertyDescriptor targetPropertyDescriptor = null;
		var sourcePropertiesName = sourcePropertyDescriptor.getName();
		if (BaseObjectUtil.isNotEmpty(propertiesCopyRule)) {
			var targetPropertiesName = propertiesCopyRule.get(sourcePropertiesName);
			if (BaseObjectUtil.isNotEmpty(targetPropertiesName)) {
				targetPropertyDescriptor = BeanUtils.getPropertyDescriptor(targetClass, targetPropertiesName);
			}
			// 有规则表无具体规则
			else {
				if (BaseCollectionUtil.notContains(ignorePropertiesSet, sourcePropertiesName)) {
					targetPropertyDescriptor = BeanUtils.getPropertyDescriptor(targetClass, sourcePropertiesName);
				}
			}
		}
		// 无规则表
		else {
			if (BaseCollectionUtil.notContains(ignorePropertiesSet, sourcePropertiesName)) {
				targetPropertyDescriptor = BeanUtils.getPropertyDescriptor(targetClass, sourcePropertiesName);
			}
		}
		return targetPropertyDescriptor;
	}
	
	@SneakyThrows(value = {IllegalAccessException.class, InvocationTargetException.class})
	private static <S, T> void copyToTarget( S sourceObj, T targetObj, Method readMethod, Method writeMethod) {
		var setterMethodParameterType = writeMethod.getParameterTypes()[0];
		var getterMethodReturnType = readMethod.getReturnType();
		if (BaseClassUtil.isAssignable(setterMethodParameterType, getterMethodReturnType)) {
			var getterMethodValue = readMethod.invoke(sourceObj);
			writeMethod.invoke(targetObj, getterMethodValue);
		}
	}
	
	private static Set<String> filterConversionIgnoreProperties(String[] ignoreProperties) {
		return Arrays
				.stream(Optional.ofNullable(ignoreProperties).orElseGet(() -> new String[]{}))
				.filter(BaseStringUtil :: hasText)
				.map(String :: trim)
				.collect(Collectors.toSet());
	}
	
	//---------------------------------------------------------------------
	// 列表拷贝
	//---------------------------------------------------------------------
	
	public static <S, T> List<T> copyList(List<? extends S> sourceList, Class<? extends T> targetClass) {
		return copyList(sourceList, targetClass, sourceObj -> copyObj(sourceObj, targetClass));
	}
	
	public static <S, T> List<T> copyList(List<? extends S> sourceList, Class<? extends T> targetClass, Class<?> editable) {
		return copyList(sourceList, targetClass, sourceObj -> copyObj(sourceObj, targetClass, editable));
	}
	
	public static <S, T> List<T> copyList(List<? extends S> sourceList, Class<? extends T> targetClass, String... ignoreProperties) {
		return copyList(sourceList, targetClass, sourceObj -> copyObj(sourceObj, targetClass, ignoreProperties));
	}
	
	private static <S, T> List<T> copyList(List<? extends S> sourceList, Class<? extends T> targetClass, Function<? super S, T> copyAction) {
		Assert.notNull(targetClass, () -> "请提供一个有效的目标 Class");
		return Optional
				.ofNullable(sourceList)
				.map(sourceObjList ->
						sourceObjList
								.stream()
								.map(copyAction)
								.collect(Collectors.toList()))
				.orElseGet(() -> new ArrayList<>(0));
	}
	
	public static <S, T> List<T> copyList(
			List<? extends S> sourceList, Class<? extends T> targetClass,
			Map<String, String> propertiesCopyRule
	) {
		return copyList(sourceList, targetClass, (sourceObj, m) -> copyObj(sourceObj, targetClass,  m), propertiesCopyRule);
	}
	
	public static <S, T> List<T> copyList(
			List<? extends S> sourceList, Class<? extends T> targetClass,
			Map<String, String> propertiesCopyRule, String... ignoreProperties
	) {
		return copyList(sourceList, targetClass, (sourceObj, m) -> copyObj(sourceObj, targetClass,  m, ignoreProperties), propertiesCopyRule);
	}
	
	public static <S, T> List<T> copyList(
			List<? extends S> sourceList, Class<? extends T> targetClass,
			Map<String, String> propertiesCopyRule, Class<?> operation
	) {
		return copyList(sourceList, targetClass, (sourceObj, m) -> copyObj(sourceObj, targetClass,  m, operation), propertiesCopyRule);
	}
	
	private static <S, M extends Map<String, String>, T> List<T> copyList(
			List<? extends S> sourceList, Class<? extends T> targetClass,
			BiFunction<? super S, ? super M, T> copyAction, M propertiesCopyRule
	) {
		Assert.notNull(targetClass, () -> "请提供一个有效的目标 Class");
		return Optional
				.ofNullable(sourceList)
				.map(sourceObjList ->
						sourceObjList
								.stream()
								.map(sourceObj -> copyAction.apply(sourceObj, propertiesCopyRule))
								.collect(Collectors.toList()))
				.orElseGet(() -> new ArrayList<>(0));
	}
}
