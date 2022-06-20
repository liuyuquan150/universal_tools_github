package indi.ly.crush.enumeration;


/**
 * <h2><span style="color: red;">Http 资源媒体类型枚举</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public enum HttpResourceMediaTypeEnum {
	/**
	 * <p>
	 *     Http 资源的消息主体(<em>entity-body</em>)是序列化的 JSON 字符串. <br />
	 *     除了低版本的 IE, 基本都支持.
	 * </p>
	 */
	APPLICATION_JSON_UTF8("application/json; charset=UTF-8"),
	/**
	 * <p>
	 *     XLS 媒体类型.
	 * </p>
	 */
	APPLICATION_XLS("application/vnd.ms-excel application/x-excel"),
	/**
	 * <p>
	 *     XLSX 媒体类型.
	 * </p>
	 */
	APPLICATION_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	
	/**
	 * <p>
	 *     媒体类型具体值.
	 * </p>
	 */
	private final String contentTypeValue;
	
	HttpResourceMediaTypeEnum(String contentTypeValue) {
		this.contentTypeValue = contentTypeValue;
	}
	
	public String getContentTypeValue() {
		return contentTypeValue;
	}
}
