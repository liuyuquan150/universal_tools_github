package indi.ly.crush.util.enumeration;

/**
 * <h2><span style="color: red;">枚举接口</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public interface IEnum<K, V> {
	
	/**
	 * <p>
	 *     获取 {@link IEnum} 实现类设置的 Key.
	 * </p>
	 *
	 * @return {@link IEnum} 实现类设置的 Key.
	 */
	default K getKey() {
		return null;
	}
	
	/**
	 * <p>
	 *     获取 {@link IEnum} 实现类设置的 Value.
	 * </p>
	 *
	 * @return {@link IEnum} 实现类设置的 Value.
	 */
	default V getValue() {
		return null;
	}
}
