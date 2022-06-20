package indi.ly.crush.model.entity;

import indi.ly.crush.util.tree.TreeNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serial;
import java.util.Objects;

/**
 * <h2><span style="color: red;">菜单实体</span></h2>
 * <p>
 *     {@link Table}、{@link Column} 都采用默认设置(默认表名、默认列名).
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Table
@Entity

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Menu
		extends TreeNode {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	private String name;
	@Transient
	private String remark;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		Menu menu = (Menu) o;
		return Objects.equals(getName(), menu.getName()) && Objects.equals(getRemark(), menu.getRemark());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getName(), getRemark());
	}
}