package indi.ly.crush.util.tree.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.ly.crush.repository.api.IMenuRepository;
import indi.ly.crush.util.support.BaseSpringBeanUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class FunctionTreeUtilTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IMenuRepository iMenuRepositoryImpl;
	
	@SneakyThrows
	@Test
	void build() {
		var menus = BaseSpringBeanUtil.copyList(iMenuRepositoryImpl.findAll(), Menu.class);
		log.info("{}", menus);
		var treeNodes = BaseFunctionTreeUtil.build(
				menus,
				menu -> menu.getPid() == 0,
				Menu :: getId,
				Menu :: getPid,
				Menu :: getChildNode,
				Menu :: setChildNode
		);
		log.info("{}", treeNodes);
		var treeNodesJSON = objectMapper.writeValueAsString(treeNodes);
		log.info("{}", treeNodesJSON);
	}
	
	
	@Data
	public static class Menu
			implements Serializable {
		@Serial
		private static final long serialVersionUID = -6849794470754667710L;
		private Long id;
		private Long pid;
		private List<Menu> childNode;
		private String name;
		private String remark;
	}
}