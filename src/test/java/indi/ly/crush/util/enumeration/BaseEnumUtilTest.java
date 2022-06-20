package indi.ly.crush.util.enumeration;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class BaseEnumUtilTest {

	@Test
	void getEnumConstantByKey() {
		log.info("{}", BaseEnumUtil.getEnumConstantByKey("", ResultEnum.class));
	}
	
	@Test
	void getEnumConstantListByKey() {
		log.info("{}", BaseEnumUtil.getEnumConstantListByKey("", ResultEnum.class));
	}
	
	@Test
	void getValueByKey() {
		log.info("{}", BaseEnumUtil.getValueByKey("", ResultEnum.class));
	}
	
	@Test
	void getValueListByKey() {
		log.info("{}", BaseEnumUtil.getValueListByKey("", ResultEnum.class));
	}
	
	@Test
	void convertKeysAndValuesToMap() {
		log.info("{}", BaseEnumUtil.convertKeysAndValuesToMap(ResultEnum.class));
	}
}

enum ResultEnum
		implements IEnum<Object, Object>{
	BAD_REQUEST(false, 400, ""){
		@Override
		public String getKey() {
			return this.getMessage();
		}
		@Override
		public Integer getValue() {
			return this.getCode();
		}
	},
	UNAUTHORIZED(false, 401, ""){
		@Override
		public String getKey() { return this.getMessage(); }
		@Override
		public Integer getValue() { return this.getCode(); }
	},
	NOT_FOUND(false, 404, "");

	private final Boolean success;
	private final Integer code;
	private final String message;

	ResultEnum(Boolean success, Integer code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Object getKey() {
		return this.code;
	}

	@Override
	public Object getValue() {
		return this.message;
	}
}