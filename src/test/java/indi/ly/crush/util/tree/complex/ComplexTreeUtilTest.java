package indi.ly.crush.util.tree.complex;

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
class ComplexTreeUtilTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IMenuRepository iMenuRepositoryImpl;
	
	@SneakyThrows
	@Test
	void build() {
		var treeNodes = BaseComplexTreeUtil.build(
				iMenuRepositoryImpl::findAll,
				(menu, treeNodeMap) ->
						treeNodeMap
								.setId(menu.getId())
								.setPid(menu.getPid())
								.setName(menu.getName())
								.conditionAddProperty("rootMenuRemake", "根菜单备注信息", pid -> (long) pid == 0L, menu::getPid)
								.conditionAddProperty("subMenuRemake","子菜单备注信息", pid -> (long) pid != 0L, menu::getPid)
								.conditionDeleteProperty("name", id -> (long) id == 19L, menu::getId),
				TreeNodeMap.class
		);
		log.info("{}", treeNodes);
		var treeNodesJSON = objectMapper.writeValueAsString(treeNodes);
		log.info("{}", treeNodesJSON);
	}
}