package indi.ly.crush.repository.api;

import indi.ly.crush.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h2><span style="color: red;">菜单资料库</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public interface IMenuRepository
		extends JpaRepository<Menu, Long> {}
