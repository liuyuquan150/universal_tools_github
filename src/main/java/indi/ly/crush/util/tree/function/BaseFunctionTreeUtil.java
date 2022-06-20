package indi.ly.crush.util.tree.function;

import indi.ly.crush.util.base.BaseCollectionUtil;
import indi.ly.crush.util.base.BaseObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h2><span style="color: red;">函数树工具</span></h2>
 * <p>
 *     采用 {@link java.util.function 函数式接口}、{@link Stream} 等 Java 8 新特性. <br />
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseFunctionTreeUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionTreeUtil.class);
	
	public static <T> List<T> build(
			List<T> source,
			SerializableFunction<T, ?> getNodeId,
			SerializableFunction<T, ?> getParentNodeId,
			SerializableFunction<T, List<T>> getChildNodes,
			SerializableBiConsumer<T, List<T>> setChildNodes
	) {
		return build(source,null , getNodeId, getParentNodeId, getChildNodes, setChildNodes);
	}
	
	public static <T> List<T> build(
			List<T> source,
			SerializablePredicate<T> isRootNode,
			SerializableFunction<T, ?> getNodeId,
			SerializableFunction<T, ?> getParentNodeId,
			SerializableFunction<T, List<T>> getChildNodes,
			SerializableBiConsumer<T, List<T>> setChildNodes
	) {
		BaseObjectUtil.isNull(source, getNodeId, getParentNodeId, getChildNodes, setChildNodes);
		if (source.isEmpty()) {
			LOGGER.warn("{}", "源列表的元素个数为 0");
			return source;
		}
		if (BaseObjectUtil.isEmpty(isRootNode)) {
			LOGGER.warn("{}", "未自定义查找根节点的 SerializablePredicate");
		}
		LOGGER.debug("Lambda 方法名: {}", getLambdaImplMethodName(getNodeId, getParentNodeId, getChildNodes, setChildNodes));
		
		var treeArrayList = new ArrayList<T>(source.size());
		var treeHashMap = BaseCollectionUtil.listToMap(source, getNodeId);
		
		var rootNodes = source
									.stream()
									.filter(findRootNode(isRootNode, getParentNodeId))
									.toList();
		assert rootNodes.size() != 0 : "找不到根节点, 无法组装";
		if (rootNodes.size() > 1) {
			LOGGER.error("所有符合条件的根节点: {}", rootNodes);
			throw new IllegalArgumentException("可以为根节点的节点不止一个, 目标不明确");
		}
		var rootNode = rootNodes.get(0);
		LOGGER.info("根节点: {}", rootNode);
		treeArrayList.add(rootNode);
		var rootNodePid = getParentNodeId.apply(rootNode);
		
		source
				.stream()
				.filter(currentNode -> getParentNodeId.apply(currentNode) != rootNodePid)
				.forEach(currentNode -> {
					var currentNodePid = getParentNodeId.apply(currentNode);
					var currentNoteParentNode = treeHashMap.get(currentNodePid);
					var allChildNode = getChildNodes.apply(currentNoteParentNode);
					Optional
							.ofNullable(allChildNode)
							.orElseGet(() -> {
								var childNodes = new LinkedList<T>();
								setChildNodes.accept(currentNoteParentNode, childNodes);
								return childNodes;
							})
							.add(currentNode);
				});
		
		return treeArrayList;
	}
	
	private static <T> Predicate<T> findRootNode(SerializablePredicate<T> isRootNode, SerializableFunction<T, ?> getParentNodeId) {
		return node -> {
			if (isRootNode != null) {
				return isRootNode.test(node);
			}
			var pid = getParentNodeId.apply(node);
			return pid == null || (pid instanceof String ? "0".equals(pid) : (Long) pid == 0L);
		};
	}
	
	private static String getLambdaImplMethodName(GetLambdaMethodName... getLambdaMethodName) {
		return Arrays
					.stream(getLambdaMethodName)
					.map(GetLambdaMethodName :: getLambdaImplMethodName)
					.collect(Collectors.joining(", "));
	}
	
	public interface SerializableBiConsumer<T, U>
			extends indi.ly.crush.api.function.SerializableBiConsumer<T, U>, GetLambdaMethodName {}
	public interface SerializableFunction<T, R>
			extends indi.ly.crush.api.function.SerializableFunction<T, R>, GetLambdaMethodName {}
	public interface SerializablePredicate<T>
			extends indi.ly.crush.api.function.SerializablePredicate<T>, GetLambdaMethodName {}
	private interface GetLambdaMethodName {
		String WRITE_REPLACE_METHOD_NAME = "writeReplace";
		default String getLambdaImplMethodName() {
			var clazz = this.getClass();
			String implMethodName = "null";
			try {
				var writeReplaceMethod = clazz.getDeclaredMethod(WRITE_REPLACE_METHOD_NAME);
				writeReplaceMethod.setAccessible(true);
				var serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(this);
				implMethodName = serializedLambda.getImplMethodName();
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return implMethodName;
		}
	}
}
