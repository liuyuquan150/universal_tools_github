package indi.ly.crush.util.tree.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.ly.crush.repository.api.IMenuRepository;
import indi.ly.crush.util.support.BaseSpringBeanUtil;
import indi.ly.crush.util.tree.TreeNode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class SimpleTreeUtilTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IMenuRepository iMenuRepositoryImpl;
	
	
	@SneakyThrows
	@Test
	void build() {
		var menus = iMenuRepositoryImpl.findAll();
		log.info("{}", menus);
		var nodes = BaseSpringBeanUtil.copyList(menus, TreeNode.class, Map.of("name", "description"));
		log.info("{}", nodes);
		var treeNodes = BaseSimpleTreeUtil.build(nodes, 0L);
		log.info("{}", treeNodes);
		var treeNodesJSON = objectMapper.writeValueAsString(treeNodes);
		log.info("{}", treeNodesJSON);
	}
}