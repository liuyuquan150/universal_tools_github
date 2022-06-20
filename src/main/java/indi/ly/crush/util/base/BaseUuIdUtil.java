package indi.ly.crush.util.base;

import indi.ly.crush.enumeration.SymbolEnum;

import java.util.UUID;

/**
 * <h2><span style="color: red;">UUID 工具类</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseUuIdUtil {
	
	public static String randomUuId() {
		return randomUuId(true);
	}

	public static String randomUuId(Boolean delete) {
		var uuid = UUID.randomUUID().toString();
		return delete ? uuid.replace(SymbolEnum.HYPHEN.getValue(), "") : uuid;
	}
}
