package indi.ly.crush.util.tree.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class MappableTreeUtilTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IMenuRepository iMenuRepositoryImpl;
	
	@SneakyThrows
	@Test
	void build() {
		var menus = iMenuRepositoryImpl.findAll();
		log.info("{}", menus);
		var treeNodes = BaseMappableTreeUtil.build(menus, 0L, (menu, treeNode) -> {
			treeNode.setId(menu.getId());
			treeNode.setPid(menu.getPid());
			treeNode.setDescription(menu.getName());
		});
		log.info("{}", treeNodes);
		var treeNodesJSON = objectMapper.writeValueAsString(treeNodes);
		log.info("{}", treeNodesJSON);
	}
}