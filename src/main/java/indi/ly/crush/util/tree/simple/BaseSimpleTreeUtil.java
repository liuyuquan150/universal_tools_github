package indi.ly.crush.util.tree.simple;

import indi.ly.crush.util.tree.TreeNode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <h2><span style="color: red;">简单树工具</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseSimpleTreeUtil {
	
	public static List<TreeNode> build(List<TreeNode> nodeSourceData, Long rootNodeId) {
		Assert.notNull(nodeSourceData, () -> "请提供一个有效的节点源数据列表");
		Assert.notNull(rootNodeId, () -> "请提供一个有效的数值来表示为构建树的起始位置");
		
		var nodeTree = new ArrayList<TreeNode>(nodeSourceData.size() - 1);
		nodeSourceData
				.stream()
				.filter(node -> Objects.equals(node.getPid(),rootNodeId))
				.forEach(node -> {
					nodeTree.add(node);
					buildTree(nodeSourceData, node);
				});
		return nodeTree;
	}
	
	private static void buildTree(List<TreeNode> nodeSourceData, TreeNode parentNode) {
		nodeSourceData
				.stream()
				.filter(note -> Objects.equals(note.getPid(),parentNode.getId()))
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
