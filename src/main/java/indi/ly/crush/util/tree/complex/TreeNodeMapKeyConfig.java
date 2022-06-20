package indi.ly.crush.util.tree.complex;

import indi.ly.crush.util.base.BaseObjectUtil;

import java.util.Optional;

/**
 * <h2><span style="color: red;">树节点地图 KEY 配置</span></h2>
 * <p>
 *     用于配置 {@link TreeNodeMap} 中的 KEY.
 * </p>
 *
 * @author 云上的云
 * @since 4.0
 * @formatter:off
 */
public class TreeNodeMapKeyConfig {
	private static volatile TreeNodeMapKeyConfig defaultTreeNodeMapKeyConfig;
	
	//---------------------------------------------------------------------
	// 树节点属性默认 KEY, 这里是作为 TreeNodeMap 的 KEY 存在.
	//---------------------------------------------------------------------
	
	private static final String TREE_NODE_ID_PROPERTY_DEFAULT_KEY = "id";
	private static final String TREE_NODE_PID_PROPERTY_DEFAULT_KEY = "pid";
	private static final String TREE_NODE_NAME_PROPERTY_DEFAULT_KEY = "name";
	private static final String TREE_NODE_CODE_PROPERTY_DEFAULT_KEY = "code";
	private static final String TREE_NODE_CHILD_NODE_PROPERTY_DEFAULT_KEY = "childNode";
	
	//---------------------------------------------------------------------
	// 树节点属性 KEY, 这里是作为 TreeNodeMap 的 KEY 存在.
	// 由外界传入决定, 会代替 ”树节点属性默认 KEY“.
	//---------------------------------------------------------------------
	
	private String treeNodeIdPropertyKey;
	private String treeNodePidPropertyKey;
	private String treeNodeNamePropertyKey;
	private String treeNodeCodePropertyKey;
	private String treeNodeChildNodePropertyKey;
	
	/**
	 * <p>
	 *     {@link #createDefaultTreeNodeMapKeyConfig()} 方法代替此无参构造器, 每次构建的 {@link TreeNodeMapKeyConfig} 对象是{@link #defaultTreeNodeMapKeyConfig 单例}的.
	 * </p>
	 */
	private TreeNodeMapKeyConfig() {}
	
	public TreeNodeMapKeyConfig(String treeNodeIdPropertyKey) {
		this(treeNodeIdPropertyKey, null, null, null, null);
	}
	
	public TreeNodeMapKeyConfig(String treeNodeIdPropertyKey, String treeNodePidPropertyKey) {
		this(treeNodeIdPropertyKey, treeNodePidPropertyKey, null, null, null);
	}
	
	public TreeNodeMapKeyConfig(String treeNodeIdPropertyKey, String treeNodePidPropertyKey, String treeNodeNamePropertyKey) {
		this(treeNodeIdPropertyKey, treeNodePidPropertyKey, treeNodeNamePropertyKey, null, null);
	}
	
	public TreeNodeMapKeyConfig(String treeNodeIdPropertyKey, String treeNodePidPropertyKey, String treeNodeNamePropertyKey, String treeNodeCodePropertyKey) {
		this(treeNodeIdPropertyKey, treeNodePidPropertyKey, treeNodeNamePropertyKey, treeNodeCodePropertyKey, null);
	}
	
	public TreeNodeMapKeyConfig(String treeNodeIdPropertyKey, String treeNodePidPropertyKey, String treeNodeNamePropertyKey, String treeNodeCodePropertyKey, String treeNodeChildNodePropertyKey) {
		this.treeNodeIdPropertyKey = treeNodeIdPropertyKey;
		this.treeNodePidPropertyKey = treeNodePidPropertyKey;
		this.treeNodeNamePropertyKey = treeNodeNamePropertyKey;
		this.treeNodeCodePropertyKey = treeNodeCodePropertyKey;
		this.treeNodeChildNodePropertyKey = treeNodeChildNodePropertyKey;
	}
	
	/**
	 * <p>
	 *     获取树节点 id 属性 KEY.
	 * </p>
	 *
	 * @return 树节点 id 属性 KEY.
	 */
	public String getTreeNodeIdPropertyKey() {
		return this.getFinalTreeNodePropertyKey(this.treeNodeIdPropertyKey, TREE_NODE_ID_PROPERTY_DEFAULT_KEY);
	}
	
	/**
	 * <p>
	 *     设置树节点 id 属性 KEY.
	 * </p>
	 *
	 * @param treeNodeIdPropertyKey 树节点 id 属性 KEY.
	 * @return 当前 {@link TreeNodeMapKeyConfig} 对象.
	 */
	public TreeNodeMapKeyConfig setTreeNodeIdPropertyKey(String treeNodeIdPropertyKey) {
		this.treeNodeIdPropertyKey = treeNodeIdPropertyKey;
		return this;
	}
	
	/**
	 * <p>
	 *     获取树节点 pid 属性 KEY.
	 * </p>
	 *
	 * @return 树节点 pid 属性 KEY.
	 */
	public String getTreeNodePidPropertyKey() {
		return this.getFinalTreeNodePropertyKey(this.treeNodePidPropertyKey, TREE_NODE_PID_PROPERTY_DEFAULT_KEY);
	}
	
	/**
	 * <p>
	 *     设置树节点 pid 属性 KEY.
	 * </p>
	 *
	 * @param treeNodePidPropertyKey 树节点 pid 属性 KEY.
	 * @return 当前 {@link TreeNodeMapKeyConfig} 对象.
	 */
	public TreeNodeMapKeyConfig setTreeNodePidPropertyKey(String treeNodePidPropertyKey) {
		this.treeNodePidPropertyKey = treeNodePidPropertyKey;
		return this;
	}
	
	/**
	 * <p>
	 *     获取树节点 name 属性 KEY.
	 * </p>
	 *
	 * @return 树节点 name 属性 KEY.
	 */
	public String getTreeNodeNamePropertyKey() {
		return this.getFinalTreeNodePropertyKey(this.treeNodeNamePropertyKey, TREE_NODE_NAME_PROPERTY_DEFAULT_KEY);
	}
	
	/**
	 * <p>
	 *     设置树节点 name 属性 KEY.
	 * </p>
	 *
	 * @param treeNodeNamePropertyKey 树节点 name 属性 KEY.
	 * @return 当前 {@link TreeNodeMapKeyConfig} 对象.
	 */
	public TreeNodeMapKeyConfig setTreeNodeNamePropertyKey(String treeNodeNamePropertyKey) {
		this.treeNodeNamePropertyKey = treeNodeNamePropertyKey;
		return this;
	}
	
	/**
	 * <p>
	 *     获取树节点 code 属性 KEY.
	 * </p>
	 *
	 * @return 树节点 code 属性 KEY.
	 */
	public String getTreeNodeCodePropertyKey() {
		return this.getFinalTreeNodePropertyKey(this.treeNodeCodePropertyKey, TREE_NODE_CODE_PROPERTY_DEFAULT_KEY);
	}
	
	/**
	 * <p>
	 *     设置树节点 code 属性 KEY.
	 * </p>
	 *
	 * @param treeNodeCodePropertyKey 树节点 code 属性 KEY.
	 * @return 当前 {@link TreeNodeMapKeyConfig} 对象.
	 */
	public TreeNodeMapKeyConfig setTreeNodeCodePropertyKey(String treeNodeCodePropertyKey) {
		this.treeNodeCodePropertyKey = treeNodeCodePropertyKey;
		return this;
	}
	
	/**
	 * <p>
	 *     获取树节点 children 属性 KEY.
	 * </p>
	 *
	 * @return 树节点 children 属性 KEY.
	 */
	public String getTreeNodeChildNodePropertyKey() {
		return this.getFinalTreeNodePropertyKey(this.treeNodeChildNodePropertyKey, TREE_NODE_CHILD_NODE_PROPERTY_DEFAULT_KEY);
	}
	
	/**
	 * <p>
	 *     设置树节点 children 属性 KEY.
	 * </p>
	 *
	 * @param treeNodeChildNodePropertyKey 树节点 children 属性 KEY.
	 * @return 当前 {@link TreeNodeMapKeyConfig} 对象.
	 */
	public TreeNodeMapKeyConfig setTreeNodeChildNodePropertyKey(String treeNodeChildNodePropertyKey) {
		this.treeNodeChildNodePropertyKey = treeNodeChildNodePropertyKey;
		return this;
	}
	
	public static TreeNodeMapKeyConfig createDefaultTreeNodeMapKeyConfig() {
		if (BaseObjectUtil.isEmpty(defaultTreeNodeMapKeyConfig)) {
			synchronized (TreeNodeMapKeyConfig.class) {
				if (BaseObjectUtil.isEmpty(defaultTreeNodeMapKeyConfig)) {
					defaultTreeNodeMapKeyConfig = new TreeNodeMapKeyConfig();
				}
			}
		}
		return defaultTreeNodeMapKeyConfig;
	}
	
	/**
	 * <p>
	 *     获取最终的树节点属性 KEY. <br /> <br />
	 *
	 *
	 *     如果指定了 {@code treeNodePropertyCustomKey}, 那么返回指定的 {@code treeNodePropertyCustomKey}.
	 *     如果未指定 {@code treeNodePropertyCustomKey}, 则返回默认的 {@code treeNodePropertyDefaultKey}.
	 *     这样做, 是为了保证能给树节点对应的属性提供一个 KEY.
	 * </p>
	 *
	 * @param treeNodePropertyCustomKey  树节点属性自定义 KEY.
	 * @param treeNodePropertyDefaultKey 树节点属性默认 KEY.
	 * @return 最终树节点属性 KEY.
	 */
	private String getFinalTreeNodePropertyKey(String treeNodePropertyCustomKey, String treeNodePropertyDefaultKey) {
		return Optional
				.ofNullable(treeNodePropertyCustomKey)
				.orElse(treeNodePropertyDefaultKey);
	}
}
