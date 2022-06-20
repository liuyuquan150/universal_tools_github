package indi.ly.crush.util.tree.custom;

import indi.ly.crush.util.base.BaseReflectionUtil;
import indi.ly.crush.util.tree.TreeNode;
import indi.ly.crush.util.tree.mapper.BaseMappableTreeUtil;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <h2><span style="color: red;">可自定义树工具</span></h2>
 *
 * @author 云上的云
 * @see BaseMappableTreeUtil
 * @since 3.0
 * @formatter:off
 */
public abstract class BaseCustomTreeUtil {
	@FunctionalInterface
	public interface Mapper<S, T extends TreeNode> {
		/**
		 * <p>
		 *     将源对象属性值映射到目标对象属性上的定义, 须外界自行实现其定义.
		 * </p>
		 *
		 * @param source 提供映射内容(<em>属性</em>)的源对象的数据类型.
		 * @param target 源对象映射至的目标对象的数据类型.
		 */
		void attributeMapping(S source, T target);
		private static <S, T extends TreeNode> List<T> performMapping(List<S> nodeSourceData, Mapper<S, T> mapper, Class<T> targetClass) {
			return nodeSourceData
					.stream()
					.map(source -> {
						// 由外界提供的 Class 决定要构建的树的数据类型
						var target = BaseReflectionUtil.newInstance(targetClass);
						mapper.attributeMapping(source, target);
						return target;
					})
					.toList();
		}
	}
	
	public static <S, T extends TreeNode> List<TreeNode> build(List<S> nodeSourceData, Long rootNodeId, Class<T> targetClass, Mapper<S, T> mapper) {
		Assert.notNull(nodeSourceData, () -> "请提供一个有效的节点源数据列表");
		Assert.notNull(rootNodeId, () -> "请提供一个有效的数值来表示为构建树的起始位置");
		
		var nodeTree = new ArrayList<TreeNode>(nodeSourceData.size() - 1);
		var mappingTree = Mapper.performMapping(nodeSourceData, mapper, targetClass);
		
		mappingTree
				.stream()
				.filter(node -> Objects.equals(node.getPid(), rootNodeId))
				.forEach(node -> {
					nodeTree.add(node);
					buildTree(mappingTree, node);
				});
		return nodeTree;
	}
	
	private static void buildTree(List<? extends TreeNode> nodeSourceData, TreeNode parentNode) {
		nodeSourceData
				.stream()
				.filter(note -> Objects.equals(note.getPid(), parentNode.getId()))
				.forEachOrdered(note -> {
					var childNode = Optional
													.ofNullable(parentNode.getChildNode())
													.orElseGet(ArrayList :: new);
					parentNode.setChildNode(childNode);
					
					childNode.add(note);
					buildTree(nodeSourceData, note);
				});
	}
}
