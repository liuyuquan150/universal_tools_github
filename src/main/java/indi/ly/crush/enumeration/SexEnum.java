package indi.ly.crush.enumeration;

import indi.ly.crush.util.enumeration.BaseEnumUtil;
import indi.ly.crush.util.enumeration.IEnum;

/**
 * <h2><span style="color: red;">性别枚举</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public enum SexEnum
		implements IEnum<Integer, String> {
	/**
	 * <p>
	 *     女.
	 * </p>
	 */
	Female("女", 0),
	/**
	 * <p>
	 *     男.
	 * </p>
	 */
	Male("男", 1),
	/**
	 * <p>
	 *     未知.
	 * </p>
	 */
	Unknown("未知", 2);
	
	/**
	 * <p>
	 *     展示在前端的性别字符串.
	 * </p>
	 */
	private final String sexValue;
	/**
	 * <p>
	 *     存储在数据库中的性别代码.
	 * </p>
	 */
	private final Integer sexCode;
	
	SexEnum(String sexValue, Integer sexCode) {
		this.sexValue = sexValue;
		this.sexCode = sexCode;
	}
	
	public String getSexValue() {
		return sexValue;
	}
	
	public Integer getSexCode() {
		return sexCode;
	}
	
	@Override
	public Integer getKey() {
		return this.sexCode;
	}
	
	@Override
	public String getValue() {
		return this.sexValue;
	}
	
	public static SexEnum getSexEnumBySexCode(Integer sexCode) {
		return BaseEnumUtil.getEnumConstantByKey(sexCode, SexEnum.class);
	}
}
