package indi.ly.crush.util.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class BaseCollectionUtilTest {
	
	@Test
	void listToMap() {
		var liuyuquan = new BaseMapUtilTest.User(1L, "liuyuquan", 22);
		var liyang = new BaseMapUtilTest.User(2L, "liyang", 21);
		var sourceList = List.of(liuyuquan, liyang);
		var users = BaseCollectionUtil.listToMap(sourceList, BaseMapUtilTest.User :: getId, user -> user, true);
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