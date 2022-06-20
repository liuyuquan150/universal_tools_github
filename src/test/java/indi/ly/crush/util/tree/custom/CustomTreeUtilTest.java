package indi.ly.crush.util.tree.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.ly.crush.model.entity.Menu;
import indi.ly.crush.repository.api.IMenuRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class CustomTreeUtilTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IMenuRepository iMenuRepositoryImpl;
	
	@SneakyThrows
	@Test
	void build() {
		var menus = iMenuRepositoryImpl.findAll();
		log.info("{}", menus);
		var treeNodes = BaseCustomTreeUtil.build(menus, 0L, Menu.class, (menu, treeNode) -> {
			treeNode.setId(menu.getId());
			treeNode.setPid(menu.getPid());
			treeNode.setName(menu.getName());
			treeNode.setRemark("菜单备注信息");
		});
		log.info("{}", treeNodes);
		var treeNodesJSON = objectMapper.writeValueAsString(treeNodes);
		log.info("{}", treeNodesJSON);
	}
}