package indi.ly.crush.util.enumeration;

import indi.ly.crush.util.base.BaseObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">枚举工具类</span></h2>
 * <p>
 *     本工具类中的静态方法(<em>枚举类一般需要实现的功能</em>)依赖于 {@link IEnum} 接口.<br />
 *     对于使用者而言,
 *     它必须实现 {@link IEnum} 接口中定义的两个默认方法(<em>{@link IEnum#getKey()} && {@link IEnum#getValue()}</em>).
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseEnumUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEnumUtil.class);
	
	//---------------------------------------------------------------------
	// getEnumConstantByKey、getEnumConstantListByKey
	//---------------------------------------------------------------------
	
	public static <ENUM extends IEnum<K, V>, K, V, T> ENUM getEnumConstantByKey(T key, Class<ENUM> enumClassObj) {
		return getEnumConstantByKey(key, enumClassObj, null);
	}
	
	public static <ENUM extends IEnum<K, V>, K, V, T> ENUM getEnumConstantByKey(T key, Class<ENUM> enumClassObj, Supplier<String> matchFailureMessageSupplier) {
		return getEnumConstantListByKey(key, enumClassObj, matchFailureMessageSupplier).get(0);
	}
	
	public static <ENUM extends IEnum<K, V>, K, V, T> List<ENUM> getEnumConstantListByKey(T key, Class<ENUM> enumClassObj) {
		return getEnumConstantListByKey(key, enumClassObj, null);
	}
	
	public static <ENUM extends IEnum<K, V>, K, V, T> List<ENUM> getEnumConstantListByKey(T key, Class<ENUM> enumClassObj, Supplier<String> matchFailureMessageSupplier) {
		BaseObjectUtil.isNull(key);
		
		var targetEnum = Arrays
										.stream(getAllEnumConstantsByClassObjectOrElseThrow(enumClassObj))
										.filter(e -> e.getKey() != null)
										.filter(e -> key.equals(e.getKey()) || e.getKey() == key)
										.toList();
		
		if (targetEnum.size() == 0) {
			var supplier = Optional
											.ofNullable(matchFailureMessageSupplier)
											.orElse(() -> MessageFormat.format("根据唯一标识 {0} 匹配不到类型为 {1} 的枚举对象.", key, enumClassObj));
			throw new IllegalArgumentException(supplier.get());
		}
		
		return targetEnum;
	}
	
	//---------------------------------------------------------------------
	// getValueByKey、getValueListByKey
	//---------------------------------------------------------------------
	
	public static <T, K, V> V getValueByKey(T key, Class<? extends IEnum<K, V>> enumClassObj) {
		return getValueByKey(key, enumClassObj, null);
	}
	
	public static <T, K, V> V getValueByKey(T key, Class<? extends IEnum<K, V>> enumClassObj, Supplier<String> matchFailureMessageSupplier) {
		return getValueListByKey(key, enumClassObj, matchFailureMessageSupplier).get(0);
	}
	
	public static <T, K, V> List<V> getValueListByKey(T key, Class<? extends IEnum<K, V>> enumClassObj) {
		return getValueListByKey(key, enumClassObj, null);
	}
	
	public static <T, K, V> List<V> getValueListByKey(T key, Class<? extends IEnum<K, V>> enumClassObj, Supplier<String> matchFailureMessageSupplier) {
		return getEnumConstantListByKey(key, enumClassObj, matchFailureMessageSupplier)
				.stream()
				.map(IEnum :: getValue)
				.toList();
	}
	
	//---------------------------------------------------------------------
	// convertKeysAndValuesToMap
	//---------------------------------------------------------------------
	
	@SuppressWarnings(value = "MapOrSetKeyShouldOverrideHashCodeEquals")
	public static <K, V> Map<Key<K>, V> convertKeysAndValuesToMap(Class<? extends IEnum<K, V>> enumClassObj) {
		/*
			处理 KEY 相同覆盖的场景.
		 */
		
		var enums = getAllEnumConstantsByClassObjectOrElseThrow(enumClassObj);
		return Arrays
				.stream(enums)
				.collect(() -> new HashMap<>(enums.length << 1), (HashMap<Key<K>, V> m, IEnum<K, V> e) -> {
					var key = e.getKey();
					var keyType = Optional
												.ofNullable(key)
												.map(k -> k.getClass())
												.orElse(null);
					m.put(new Key<>(keyType, key), e.getValue());
				}, HashMap :: putAll);
	}

	
	//---------------------------------------------------------------------
	// 私有方法
	//---------------------------------------------------------------------
	
	private static <ENUM extends IEnum<K, V>, K, V> ENUM[] getAllEnumConstantsByClassObjectOrElseThrow(Class<ENUM> enumClassObj) {
		return Optional
				.ofNullable(Objects.requireNonNull(enumClassObj).getEnumConstants())
				.orElseThrow(() -> new IllegalArgumentException("此 Class 对象不是一个枚举类: " + enumClassObj));
	}
	
	@Setter
	@Getter
	@ToString
	private static class Key<K> {
		private Class<?> type;
		private K value;
		
		public Key(Class<?> type, K value) {
			this.type = type;
			this.value = value;
		}
	}
}
