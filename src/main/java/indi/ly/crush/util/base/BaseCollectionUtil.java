package indi.ly.crush.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">集合工具</span></h2>
 * <p>
 *     继承于 {@link CollectionUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseCollectionUtil
		extends CollectionUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCollectionUtil.class);
	
	//---------------------------------------------------------------------
	// List
	//---------------------------------------------------------------------
	
	public static <E, T extends List<E>, K> VarianceDataAnalysisResult<E> obtainDifferenceData(
			Supplier<T> listOne, Supplier<T> listTwo,
			Function<E, K> differenceRule
	) {
		return obtainDifferenceData(listOne, listTwo, differenceRule, true);
	}
	
	/**
	 * <p>
	 *     以对象的形式获取两个集合的差异数据.
	 * </p>
	 *
	 * @param listOne          进行差异对比的列表数据源.
	 * @param listTwo          进行差异对比的列表数据源.
	 * @param differenceRule   数据进行差异比对时的规则函数.
	 * @param flag             {@code true}: “A 端” 作为 “本端”; {@code false}: “B 端”作为 “本端”;
	 * @param <E>              进行差异对比的源对象的数据类型、返回列表中差异数据元素的数据类型, 也是 {@code differenceRule}、{@code differenceResult} 函数输入对象的数据类型.
	 * @param <T>              {@link Supplier} 提供结果(<em>对象</em>)的数据类型.
	 * @param <K>              {@code differenceResult} 函数结果对象的数据类型.
	 * @see VarianceDataAnalysisResult
	 * @return 差异数据对象.
	 */
	public static <E, T extends List<E>, K> VarianceDataAnalysisResult<E> obtainDifferenceData(
			Supplier<T> listOne, Supplier<T> listTwo,
			Function<E, K> differenceRule, Boolean flag
	) {
		var listSourceOne = listOne.get();
		var listSourceTwo = listTwo.get();
		var listSourceOneSize = listSourceOne.size();
		var listSourceTwoSize = listSourceTwo.size();
		
		if (listSourceOneSize == 0 && listSourceTwoSize == 0) {
			return new VarianceDataAnalysisResult<>();
		}
		if (listSourceOneSize == 0) {
			if (flag) {
				return new VarianceDataAnalysisResult<>(null, new ArrayList<>(listSourceTwo),null);
			} else {
				return new VarianceDataAnalysisResult<>(new ArrayList<>(listSourceTwo), null,  null);
			}
		}
		if (listSourceTwoSize == 0) {
			if (flag) {
				return new VarianceDataAnalysisResult<>(new ArrayList<>(listSourceOne), null, null);
			} else {
				return new VarianceDataAnalysisResult<>(null, new ArrayList<>(listSourceOne), null);
			}
		}
		
		var mapSourceOne = listToMap(listSourceOne, differenceRule, Function.identity());
		var mapSourceTwo = listToMap(listSourceTwo, differenceRule, Function.identity());
		
		List<E> aEndDifferenceData;
		List<E> bEndDifferenceData;
		List<E> allSameData;
		if (listSourceOneSize > listSourceTwoSize){
			aEndDifferenceData =  new ArrayList<>(listSourceOneSize);
			bEndDifferenceData = new ArrayList<>(listSourceTwoSize);
			allSameData = findDifferenceData(mapSourceOne, mapSourceTwo, aEndDifferenceData, bEndDifferenceData);
		} else {
			aEndDifferenceData =  new ArrayList<>(listSourceTwoSize);
			bEndDifferenceData = new ArrayList<>(listSourceOneSize);
			allSameData = findDifferenceData(mapSourceTwo, mapSourceOne, aEndDifferenceData, bEndDifferenceData);
		}
		System.err.println(allSameData);
		return flag
				? new VarianceDataAnalysisResult<>(aEndDifferenceData, bEndDifferenceData, allSameData)
				: new VarianceDataAnalysisResult<>(bEndDifferenceData, aEndDifferenceData,allSameData)
				;
	}
	
	/**
	 * <p>
	 *     查找差异数据.
	 * </p>
	 *
	 * @param aEndSourceData              地图形式的 A 端源数据.
	 * @param bEndSourceData              地图形式的 B 端源数据.
	 * @param aEndDifferenceDataContainer 存储 A 端差异数据的容器.
	 * @param <K>                         地图形式的 A、B 两端中 KEY 的数据类型.
	 * @param <V>                         地图形式的 A、B 两端中 VALUE 的数据类型, 也是差异数据的数据类型.
	 * @return B 端差异数据.
	 */
	private static <K, V> List<V> findDifferenceData(
			Map<K, V> aEndSourceData, Map<K, V> bEndSourceData,
			List<? super V> aEndDifferenceDataContainer,
			List<? super V> bEndDifferenceDataContainer
	) {
		var allSameData = new LinkedList<V>();
		aEndSourceData.forEach((k, e) -> {
			var isDifferenceData =
					Optional
							.ofNullable(bEndSourceData.remove(k))
							.isEmpty();
			if (isDifferenceData) {
				aEndDifferenceDataContainer.add(e);
			} else {
				allSameData.add(e);
			}
		});

		bEndDifferenceDataContainer.addAll(new ArrayList<>(bEndSourceData.values()));
		return allSameData;
	}
	
	public static class VarianceDataAnalysisResult<E> {
		/**
		 * <p>
		 *     本端差异数据: 本端数据存在, 对端数据不存在.
		 * </p>
		 */
		private List<E> localDifferenceData;
		/**
		 * <p>
		 *     其它渠道(<em>又称对端</em>)差异数据: 对端数据存在, 本端数据不存在.
		 * </p>
		 */
		private List<E> otherChannelDifferenceData;
		/**
		 * <p>
		 *     所有差异数据: {@link #localDifferenceData} + {@link #otherChannelDifferenceData}.
		 * </p>
		 */
		private List<E> allDifferenceData;
		/**
		 * <p>
		 *     所有相同数据: 在源数据的基础上剔除 {@link #allDifferenceData}.
		 * </p>
		 */
		private List<E> allSameData;
		
		public VarianceDataAnalysisResult() {
			this(null, null,null);
		}
		
		public VarianceDataAnalysisResult(List<E> localDifferenceData, List<E> otherChannelDifferenceData, List<E> allSameData) {
			this.localDifferenceData = Optional.ofNullable(localDifferenceData).orElseGet(ArrayList :: new);
			this.otherChannelDifferenceData = Optional.ofNullable(otherChannelDifferenceData).orElseGet(ArrayList :: new);
			this.allDifferenceData = new ArrayList<>(this.localDifferenceData);
			this.allDifferenceData.addAll(this.otherChannelDifferenceData);
			this.allSameData = Optional.ofNullable(allSameData).orElseGet(ArrayList :: new);
		}
		
		public List<E> getLocalDifferenceData() {
			return localDifferenceData;
		}
		
		public void setLocalDifferenceData(List<E> localDifferenceData) {
			this.localDifferenceData = localDifferenceData;
		}
		
		public List<E> getOtherChannelDifferenceData() {
			return otherChannelDifferenceData;
		}
		
		public void setOtherChannelDifferenceData(List<E> otherChannelDifferenceData) {
			this.otherChannelDifferenceData = otherChannelDifferenceData;
		}
		
		public List<E> getAllDifferenceData() {
			return allDifferenceData;
		}
		
		public void setAllDifferenceData(List<E> allDifferenceData) {
			this.allDifferenceData = allDifferenceData;
		}
		
		public List<E> getAllSameData() {
			return allSameData;
		}
		
		public void setAllSameData(List<E> allSameData) {
			this.allSameData = allSameData;
		}
	}
	
	public static Boolean isNotEmpty(List<?> list) {
		return !list.isEmpty();
	}
	
	public static <T> List<T> createArrayListContainingOnlySpecifiedElement(T t, Predicate<T> tPredicate) {
		if (tPredicate.test(t)) {
			var ts = new ArrayList<T>(1);
			ts.add(0, t);
			return ts;
		}
		return new ArrayList<>(0);
	}
	
	public static <T, K>  Map<K, T> listToMap(List<T> sourceList, Function<T, K> keyMapper) {
		// Function.identity() == t -> t
		return listToMap(sourceList, keyMapper, Function.identity());
	}
	
	public static <T, K>  Map<K, T> listToMap(List<T> sourceList, Function<T, K> keyMapper, Boolean sequentialInsertion) {
		return listToMap(sourceList, keyMapper, Function.identity(), (value1, value2) -> value1,sequentialInsertion);
	}
	
	public static <T, K, V>  Map<K, V> listToMap(List<T> sourceList, Function<T, K> keyMapper, Function<T, V> valueMapper) {
		return listToMap(sourceList, keyMapper, valueMapper, (value1, value2) -> value1);
	}
	
	public static <T, K, V>  Map<K, V> listToMap(List<T> sourceList, Function<T, K> keyMapper, Function<T, V> valueMapper, Boolean sequentialInsertion) {
		return listToMap(sourceList, keyMapper, valueMapper, (value1, value2) -> value1,sequentialInsertion);
	}
	
	public static <T, K, V>  Map<K, V> listToMap(List<T> sourceList, Function<T, K> keyMapper, Function<T, V> valueMapper, BinaryOperator<V> mergeFunction) {
		return listToMap(sourceList, keyMapper, valueMapper, mergeFunction, false);
	}
	
	public static <T, K, V>  Map<K, V> listToMap(List<T> sourceList, Function<T, K> keyMapper, Function<T, V> valueMapper, BinaryOperator<V> mergeFunction, Boolean sequentialInsertion) {
		BaseObjectUtil.isNull(sourceList, keyMapper, valueMapper);
		if (sourceList.isEmpty()) {
			return new HashMap<>(0);
		}
		
		var size = sourceList.size();
		Supplier<? extends Map<K, V>> supplier = sequentialInsertion ? () -> new LinkedHashMap<>(size) : () -> new HashMap<>(size);
		return sourceList
				.stream()
				.collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, supplier));
	}
	
	
	
	//---------------------------------------------------------------------
	// Set
	//---------------------------------------------------------------------
	
	public static <T> Boolean notContains(Set<?> set, T t) {
		return !set.contains(t);
	}
	
	public static <E> Set<E> union(Set<? extends E> setOne, Set<? extends E> setTwo) {
		var unionSet = new HashSet<E>(Objects.requireNonNull(setOne));
		unionSet.addAll(Objects.requireNonNull(setTwo));
		return unionSet;
	}
}