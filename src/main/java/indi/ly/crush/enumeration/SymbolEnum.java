package indi.ly.crush.enumeration;

/**
 * <h2><span style="color: red;">符号枚举</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public enum SymbolEnum {
	/**
	 * <p>
	 *     中文逗号
	 * </p>
	 */
	CHINESE_COMMA(","),
	/**
	 * <p>
	 *     点符号(<em>英文句号</em>).
	 * </p>
	 */
	DOT_SYMBOL("."),
	/**
	 * <p>
	 *     正斜杠符号.
	 * </p>
	 */
	POSITIVE_SLASH_SYMBOL("/"),
	/**
	 * <p>
	 *     连字字符.
	 * </p>
	 */
	HYPHEN("-");
	
	/**
	 * <p>
	 *     符号具体值.
	 * </p>
	 */
	private final String value;
	
	SymbolEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
