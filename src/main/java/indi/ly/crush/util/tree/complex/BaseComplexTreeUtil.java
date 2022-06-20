package indi.ly.crush.util.tree.complex;

import indi.ly.crush.util.base.BaseReflectionUtil;
import indi.ly.crush.util.tree.TreeNode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">复杂树工具</span></h2>
 *
 * @author 云上的云
 * @since 4.0
 * @formatter:off
 */
public abstract class BaseComplexTreeUtil {
	@FunctionalInterface
	public interface Mapper<S extends TreeNode, T extends TreeNodeMap> {
		/**
		 * <p>
		 *     将{@link TreeNode 源对象}属性值映射到{@link TreeNodeMap 目标地图}同名键所对应值上的定义, 须外界自行实现其定义.
		 * </p>
		 *
		 * @param source 提供映射内容(<em>属性</em>)的源对象的数据类型.
		 * @param target 源对象映射至的目标地图的数据类型.
		 */
		void attributeMapping(S source, T target);

		private static <S extends TreeNode, T extends TreeNodeMap> List<T> performMapping(
				List<S> nodeSourceData, TreeNodeMapKeyConfig keyConfig,
				Mapper<S, T> mapper, Class<T> targetClass
		) {
			return nodeSourceData
					.stream()
					.map(source -> {
						var target = BaseReflectionUtil.newInstance(targetClass, keyConfig == null ? TreeNodeMapKeyConfig.createDefaultTreeNodeMapKeyConfig() : keyConfig);
						mapper.attributeMapping(source, target);
						return target;
					})
					.toList();
		}
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			List<S> nodeSourceData, Class<T> targetClass
	) {
		return build(nodeSourceData, 0L, null, defaultMapper(), targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			Supplier<List<S>> nodeSourceData, Class<T> targetClass
	) {
		return build(nodeSourceData.get(), 0L, null, defaultMapper(), targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			List<S> nodeSourceData, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData, 0L, null, mapper, targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			List<S> nodeSourceData, Long rootNodeId, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData, rootNodeId, null, mapper, targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			Supplier<List<S>> nodeSourceData, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData.get(), 0L, null, mapper, targetClass);
	}

	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			Supplier<List<S>> nodeSourceData, Long rootNodeId, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData.get(), rootNodeId, null, mapper, targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			Supplier<List<S>> nodeSourceData, TreeNodeMapKeyConfig keyConfig, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData.get(), 0L, keyConfig, mapper, targetClass);
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			Supplier<List<S>> nodeSourceData, Long rootNodeId, TreeNodeMapKeyConfig keyConfig, Mapper<S, T> mapper, Class<T> targetClass
	) {
		return build(nodeSourceData.get(), rootNodeId, keyConfig, mapper, targetClass);
	}
	
	private static <S extends TreeNode, T extends TreeNodeMap> Mapper<S, T> defaultMapper() {
		return (treeNode, treeNodeMap) -> 
											treeNodeMap
											.setId(treeNode.getId())
											.setPid(treeNode.getPid())
											.setName(treeNode.getDescription());
	}
	
	public static <S extends TreeNode, T extends TreeNodeMap> List<T> build(
			List<S> nodeSourceData, Long rootNodeId, TreeNodeMapKeyConfig keyConfig, Mapper<S, T> mapper, Class<T> targetClass
	) {
		Assert.notNull(nodeSourceData, () -> "请提供一个有效的节点源数据列表");
		Assert.notNull(rootNodeId, () -> "请提供一个有效的数值来表示为构建树的起始位置");
		
		var nodeTreeMap = new ArrayList<T>(nodeSourceData.size() - 1);
		var mappingTreeMap = Mapper.performMapping(nodeSourceData, keyConfig, mapper, targetClass);
		
		mappingTreeMap
				.stream()
				.filter(nodeMap -> Objects.equals(nodeMap.getPid(), rootNodeId))
				.forEach(nodeMap -> {
					nodeTreeMap.add(nodeMap);
					buildTree(mappingTreeMap, nodeMap);
				});
		return nodeTreeMap;
	}
	
	private static void buildTree(List<? extends TreeNodeMap> nodeSourceData, TreeNodeMap parentNodeMap) {
		nodeSourceData
				.stream()
				.filter(noteMap -> Objects.equals(noteMap.getPid(), parentNodeMap.getId()))
				.forEachOrdered(noteMap -> {
					var childNodeObj = Optional
												.ofNullable(parentNodeMap.getChildNode())
												.orElseGet(ArrayList :: new);
					// 这一步是将编译期的 childNodeObj 的类型转换为 List<TreeNodeMap>, 并且不会被 idea 发出警告
					var childNode = TreeNodeMap.vConversionToArrayList(childNodeObj, TreeNodeMap.class);
					parentNodeMap.setChildNode(childNode);
					
					childNode.add(noteMap);
					buildTree(nodeSourceData, noteMap);
				});
	}
}
