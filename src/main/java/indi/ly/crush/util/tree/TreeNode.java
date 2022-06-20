package indi.ly.crush.util.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <h2><span style="color: red;">树节点实体</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@MappedSuperclass

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     当前节点的唯一标识符.
	 * </p>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	/**
	 * <p>
	 *     当前节点的唯一父标识符(<em>父节点的唯一标识符</em>).
	 * </p>
	 */
	private Long pid;
	/**
	 * <p>
	 *     当前节点的所有子节点.
	 * </p>
	 */
	@Transient
	private List<TreeNode> childNode;
	/**
	 * <p>
	 *     当前节点的描述.
	 * </p>
	 */
	private String description;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		TreeNode treeNode = (TreeNode) o;
		return id != null && Objects.equals(id, treeNode.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
