package indi.ly.crush.util.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.Map;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class BaseMapUtilTest {
	
	@Test
	void mapToList() {
		var liuyuquan = new User(1L, "liuyuquan", 22);
		var liyang = new User(2L, "liyang", 21);
		var sourceMap = Map.of(liuyuquan.getId(), liuyuquan, liyang.getId(), liyang);
		var users = BaseMapUtil.mapToList(sourceMap, (id, user) -> user, Comparator.comparing(User :: getAge));
		log.info("{}", users);
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class User {
		private Long id;
		private String name;
		private Integer age;
	}
}