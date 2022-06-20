package indi.ly.crush.util.tree.complex;

import indi.ly.crush.util.base.BaseClassUtil;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">树节点地图</span></h2>
 *
 * @author 云上的云
 * @see TreeNodeMapKeyConfig
 * @since 4.0
 * @formatter:off
 */
public class TreeNodeMap
		extends HashMap<String, Object> {
	private final TreeNodeMapKeyConfig treeNodeMapKeyConfig;
	
	public TreeNodeMap() {
		this(TreeNodeMapKeyConfig.createDefaultTreeNodeMapKeyConfig());
	}
	
	public TreeNodeMap(TreeNodeMapKeyConfig treeNodeMapKeyConfig) {
		this.treeNodeMapKeyConfig = treeNodeMapKeyConfig;
	}
	
	public Object getId() {
		return super.get(this.treeNodeMapKeyConfig.getTreeNodeIdPropertyKey());
	}
	
	public TreeNodeMap setId(Object id) {
		super.put(this.treeNodeMapKeyConfig.getTreeNodeIdPropertyKey(), id);
		return this;
	}
	
	public Object getPid() {
		return super.get(this.treeNodeMapKeyConfig.getTreeNodePidPropertyKey());
	}
	
	public TreeNodeMap setPid(Object pid) {
		super.put(this.treeNodeMapKeyConfig.getTreeNodePidPropertyKey(), pid);
		return this;
	}
	
	public Object getName() {
		return super.get(this.treeNodeMapKeyConfig.getTreeNodeNamePropertyKey());
	}
	
	public TreeNodeMap setName(Object name) {
		super.put(this.treeNodeMapKeyConfig.getTreeNodeNamePropertyKey(), name);
		return this;
	}
	
	public Object getCode() {
		return super.get(this.treeNodeMapKeyConfig.getTreeNodeCodePropertyKey());
	}
	
	public TreeNodeMap setCode(Object code) {
		super.put(this.treeNodeMapKeyConfig.getTreeNodeCodePropertyKey(), code);
		return this;
	}
	
	public Object getChildNode() {
		return super.get(this.treeNodeMapKeyConfig.getTreeNodeChildNodePropertyKey());
	}
	
	public TreeNodeMap setChildNode(Object children) {
		super.put(this.treeNodeMapKeyConfig.getTreeNodeChildNodePropertyKey(), children);
		return this;
	}
	
	/**
	 * <p>
	 *     添加属性, 用于动态添加树节点可具备的属性条目.
	 * </p>
	 *
	 * @param propertyName  要添加属性的名称, 它以 KEY 的形式存储在当前 {@link TreeNodeMap} 地图中.
	 * @param propertyValue 要添加属性的具体值, 它以 VALUE 的形式存储在当前 {@link TreeNodeMap} 地图中.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap addProperty(String propertyName, Object propertyValue) {
		super.put(propertyName, propertyValue);
		return this;
	}
	
	/**
	 * <p>
	 *     修改属性, 根据属性名称修改树节点指定属性的实际值.
	 * </p>
	 *
	 * @param propertyName  被修改属性的名称.
	 * @param propertyValue 被修改属性的具体值.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap modifyProperty(String propertyName, Object propertyValue) {
		return this.addProperty(propertyName, propertyValue);
	}
	
	/**
	 * <p>
	 *     删除属性, 用于删除树节点的指定属性条目.
	 * </p>
	 *
	 * @param propertyName 被删除属性的名称.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap deleteProperty(String propertyName) {
		super.remove(propertyName);
		return this;
	}
	
	/**
	 * <p>
	 *     条件添加属性.
	 * </p>
	 *
	 * @param propertyName  要添加属性的名称, 它以 KEY 的形式存储在当前 {@link TreeNodeMap} 地图中.
	 * @param propertyValue 要添加属性的具体值, 它以 VALUE 的形式存储在当前 {@link TreeNodeMap} 地图中.
	 * @param addCondition  添加条件.
	 * @param condition     条件.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap conditionAddProperty(String propertyName, Object propertyValue, Predicate<Object> addCondition, Supplier<Object> condition) {
		if (addCondition.test(condition.get())) {
			this.addProperty(propertyName, propertyValue);
		}
		return this;
	}
	
	/**
	 * <p>
	 *     条件修改属性.
	 * </p>
	 *
	 * @param propertyName    被修改属性的名称.
	 * @param propertyValue   被修改属性的具体值.
	 * @param modifyCondition 修改条件.
	 * @param condition       条件.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap conditionModifyProperty(String propertyName, Object propertyValue, Predicate<Object> modifyCondition, Supplier<Object> condition) {
		return this.conditionAddProperty(propertyName, propertyValue, modifyCondition, condition);
	}

	/**
	 * <p>
	 *     条件删除属性.
	 * </p>
	 *
	 * @param propertyName    被删除属性的名称.
	 * @param deleteCondition 删除条件.
	 * @param condition       条件.
	 * @return 当前的 {@link TreeNodeMap} 地图, 即一个树节点.
	 */
	public TreeNodeMap conditionDeleteProperty(String propertyName, Predicate<Object> deleteCondition, Supplier<Object> condition) {
		if (deleteCondition.test(condition.get())) {
			this.deleteProperty(propertyName);
		}
		return this;
	}
	
	/**
	 * <p>
	 *     如果一个泛型对象 Children 本身是{@literal List<?>},
	 *     那么请使用此方法将其在编译期间转换成{@link List}(<em>保留列表泛型 —— ?</em>). <br />
	 *
	 *     被转换后的 {@link List}, 它元素的数据类型(<em>它的泛型类型</em>)由 {@code genericTypeClass} 所决定.
	 * </p>
	 *
	 * @param conversionTargetObj 被转换的目标对象.
	 * @param genericTypeClass    转换目标对象被转换成{@link List 列表}后, 期望此列表泛型的 {@link Class}.
	 * @param <Children>          转换目标对象的数据类型.
	 * @param <ChildrenClass>     转换目标对象被转换成{@link List 列表}后, 期望此列表元素的 {@link Class} 对象的泛型.
	 * @return 一个转换后的 {@link List 列表}.
	 * @apiNote 此方法是为了适配 {@link #getChildNode()} 方法而存在.
	 */
	public static <Children, ChildrenClass extends TreeNodeMap> List<ChildrenClass> vConversionToArrayList(Children conversionTargetObj, Class<ChildrenClass> genericTypeClass) {
		return BaseClassUtil.tConversionToList(conversionTargetObj, genericTypeClass);
	}
}
