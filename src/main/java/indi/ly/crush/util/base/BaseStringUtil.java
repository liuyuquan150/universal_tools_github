package indi.ly.crush.util.base;

import org.springframework.util.StringUtils;

/**
 * <h2><span style="color: red;">字符串工具</span></h2>
 * <p>
 *     继承于 {@link StringUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseStringUtil
		extends StringUtils {
	public static Boolean notEquals(String objOne, String objTwo) {
		return BaseObjectUtil.notEquals(objOne, objTwo);
	}
	
	public static Boolean notStartsWith(String caller, String prefix) {
		return !caller.startsWith(prefix);
	}
	
	public static Boolean notContains(String str, String s) {
		return !str.contains(s);
	}
	
	public static Boolean notHasLength(String str) {
		return !hasLength(str);
	}
	
	public static Boolean notHasText(String str) {
		return !hasText(str);
	}
	
	/**
	 * <p>
	 *     删除指定的字符, 如果它存在于结尾处则将其删除, 不存在则返回原本的字符串. <br /> <br />
	 *
	 *
	 *     {@link StringUtils#delete(String, String)} 方法亦可实现奔方法的功能.
	 * </p>
	 *
	 * @param str    进行操作的字符串.
	 * @param delete 要删除的字符.
	 * @return 原本的字符串 || 删除指定字符后的新字符串.
	 */
	public static String deleteSpecifiedCharIfItExistsAtEnd(String str, Character delete) {
		BaseObjectUtil.isNull(str, delete);
		var l = str.length() - 1;
		return str.charAt(l) == delete ? str.substring(0, l) : str;
	}
	
	
	public static String lastIndexOfSubstring(String s, Character c) {
		return lastIndexOfSubstring(s,c + "");
	}
	
	/**
	 * <p>
	 *     根据指定子字符串, 搜索它在源字符串中的最后一个位置(<em>存在多个不同的位置</em>), 从当前位置开始截取子字符串(<em>包裹指定字符串</em>).
	 *     若无搜索不到子字符串, 则返回源字符串.
	 * </p>
	 *
	 * @param source 源字符串.
	 * @param sub    指定子字符串.
	 * @return 截取之后的子串.
	 */
	public static String lastIndexOfSubstring(String source, String sub) {
		BaseObjectUtil.isNull(source, sub);
		var lastIndex = source.lastIndexOf(sub);
		return lastIndex == -1 ? source : source.substring(lastIndex);
	}
}
