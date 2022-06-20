package indi.ly.crush.util.base;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">地图工具</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseMapUtil {
	private static final Float DEFAULT_LOAD_FACTOR = 0.75F;
	
	
	public static Integer rationalInitial(Integer expectedSize) {
		return rationalInitial(expectedSize, DEFAULT_LOAD_FACTOR);
	}
	
	public static Integer rationalInitial(Integer expectedSize, Float loadFactor) {
		return (int) ((expectedSize / loadFactor) + 1.0F);
	}
	
	public static Boolean isNotEmpty(Map<?, ?> map) {
		return !map.isEmpty();
	}
	
	public static <K, V, T>  List<T> mapToList(Map<K, V> sourceMap, BiFunction<K, V, T> kvMapper) {
		return mapToList(sourceMap, kvMapper, null);
	}
	
	public static <K, V, T> List<T> mapToList(Map<K, V> sourceMap, BiFunction<K, V, T> kvMapper, Comparator<T> sort) {
		BaseObjectUtil.isNull(sourceMap, kvMapper);
		if (sourceMap.isEmpty()) {
			return new ArrayList<>(0);
		}
		
		var stream = sourceMap
									.entrySet()
									.stream()
									.map(entry -> kvMapper.apply(entry.getKey(), entry.getValue()));
		
		if (BaseObjectUtil.isEmpty(sort)) {
			return stream.collect(Collectors.toList());
		}
		return stream.sorted(sort).collect(Collectors.toList());
	}
}
