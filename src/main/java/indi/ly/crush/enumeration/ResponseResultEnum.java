package indi.ly.crush.enumeration;

import javax.servlet.http.HttpServletResponse;

/**
 * <h2><span style="color: red">响应结果枚举</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public enum ResponseResultEnum {
	//---------------------------------------------------------------------
	// 状态码 2XX
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     Continue: 继续. <br /> <br />
	 *
	 *
	 *     客户端应当继续发送请求. <br />
	 *     服务器的这个临时响应是用来通知客户端它的部分请求已经被服务器接收, 且仍未被拒绝, 客户端应当继续发送请求的剩余部分. <br />
	 *     如果客户端的请求已经完成, 服务器则忽略这个响应. <br />
	 *     服务器必须在请求完成后向客户端发送一个最终响应.
	 * </p>
	 */
	CONTINUE(true, 100, null),
	/**
	 * <p>
	 *     Switching Protocols: 切换协议. <br /> <br />
	 *
	 *     服务器已经理解了客户端的请求, 并将通过 Upgrade 消息头通知客户端采用不同的协议来完成这个请求. <br />
	 *     在发送完这个响应最后的空行后, 服务器将会切换到在 Upgrade 消息头中定义的那些协议. <br />
	 *     只有在切换新的协议更有好处的时候才应该采取类似措施. <br />
	 *     例如, 切换到新的 HTTP 版本比旧版本更有优势. <br />
	 *     或者切换到一个实时且同步的协议以传送利用此类特性的资源.
	 * </p>
	 */
	SWITCHING_PROTOCOLS(true, 101, null),
	
	
	
	//---------------------------------------------------------------------
	// 状态码 2XX
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     OK: 成功. <br /> <br />
	 *
	 *
	 *     客户端请求已成功, 服务器会将请求所希望的响应头或数据体将随此响应返回.
	 * </p>
	 */
	OK(true, 200, "请求成功"),
	/**
	 * <p>
	 *     Created: 已创建. <br /> <br />
	 *
	 *
	 *     客户端请求已成功, 而且服务器根据依据请求的需要而建立一个新的资源, 且其 URI 已经随 Location 头信息返回. <br />
	 *     假如需要的资源无法及时建立的话, 应当返回 {@link #ACCEPTED "202 Accepted"}.
	 * </p>
	 */
	CREATED(true, 201, null),
	/**
	 * <p>
	 *     Accepted: 已接收. <br /> <br />
	 *
	 *
	 *     服务器已经接受了客户端的请求, 但未处理完成.
	 *     服务器已接受客户端请求, 但尚未处理. <br />
	 *     正如它可能被拒绝一样, 最终该请求可能会也可能不会被执行. <br />
	 *     在异步操作的场合下, 没有比发送这个状态码更方便的做法了. <br />
	 *     返回 202 状态码的响应的目的是允许服务器接受其它过程的请求(<em>例如某个每天只执行一次的基于批处理的操作</em>),
	 *     而不必让客户端一直保持与服务器的连接直到批处理操作全部完成. <br />
	 *     在接受请求处理并返回 202 状态码的响应, 服务器应当在返回的实体中包含一些指示处理当前状态的信息,
	 *     以及指向处理状态监视器或状态预测的指针, 以便前端人员能够估计操作是否已经完成.
	 * </p>
	 */
	ACCEPTED(true, 202, null),
	/**
	 * <p>
	 *     Non-Authoritative Information: 非授权信息. <br /> <br />
	 *
	 *
	 *     服务器已成功处理了客户端请求, 但返回的实体头部元信息不是在原始服务器上有效的确定集合, 而是来自本地或者第三方的拷贝. <br />
	 *     当前的信息可能是原始版本的子集或者超集. <br />
	 *     例如, 包含资源的元数据可能导致原始服务器知道元信息的超级. <br />
	 *     使用此状态码不是必须的, 而且只有在响应不使用此状态码便会返回 {@link #OK "200 OK"} 的情况下才是合适的.
	 * </p>
	 */
	NON_AUTHORITATIVE_INFORMATION(true, 203, null),
	/**
	 * <p>
	 *     No Content: 无内容. <br /> <br />
	 *
	 *
	 *     服务器成功处理了客户端请求, 但不需要返回任何实体内容, 并且希望返回更新了的元信息. <br />
	 *     响应可能通过实体头部的形式, 返回新的或更新后的元信息. <br />
	 *     如果存在这些头部信息, 则应当与所请求的变量相呼应. <br />
	 *     如果客户端是浏览器的话, 那么用户浏览器应保留发送了该请求的页面, 而不产生任何文档视图上的变化(<em>继续显示当前文档</em>). <br />
	 *     即使按照规范新的或更新后的元信息应当被应用到用户浏览器活动视图中的文档. <br />
	 *     由于 204 响应被禁止包含任何消息主体, 因此它始终以消息头后的第一个空行结尾.
	 * </p>
	 */
	NO_CONTENT(true, 204, null),
	/**
	 * <p>
	 *     Reset Content: 重置内容. <br /> <br />
	 *
	 *
	 *     服务器成功处理了客户端请求, 且没有返回任何内容. <br />
	 *     与 {@link #NO_CONTENT 204 响应} 不同, 返回此状态码的响应要求请求者重置文档视图. <br />
	 *     该响应主要是被用于接受用户输入后, 立即重置表单, 以便用户能够轻松地开始另一次输入. <br />
	 *     与 {@link #NO_CONTENT 204 响应} 一样, 该响应也被禁止包含任何消息主体, 且以消息头后的第一个空行结束.
	 * </p>
	 */
	RESET_CONTENT(true, 205, null),
	/**
	 * <p>
	 *     Partial Content: 部分内容.
	 * </p>
	 */
	PARTIAL_CONTENT(true, 206, null),
	
	
	
	//---------------------------------------------------------------------
	// 状态码 3XX
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     Multiple Choices: 多种选择.
	 * </p>
	 */
	MULTIPLE_CHOICES(true, 300, null),
	/**
	 * <p>
	 *     Moved Permanently: 永久移动.
	 * </p>
	 */
	MOVED_PERMANENTLY(true, 301, null),
	/**
	 * <p>
	 *     Move Temporarily: 临时移动.
	 * </p>
	 */
	MOVE_TEMPORARILY(true, 302, null),
	/**
	 * <p>
	 *     See Other: 查看其它地址.
	 * </p>
	 */
	SEE_OTHER(true, 303, null),
	/**
	 * <p>
	 *     Not Modified: 未修改.
	 * </p>
	 */
	NOT_MODIFIED(true, 304, null),
	/**
	 * <p>
	 *     Use Proxy: 使用代理.
	 * </p>
	 */
	USE_PROXY(true, 305, null),
	/**
	 * <p>
	 *     Temporary Redirect: 临时重定向.
	 * </p>
	 */
	TEMPORARY_REDIRECT(true, 307, null),
	
	
	
	//---------------------------------------------------------------------
	// 状态码 4XX
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     Bad Request: 错误请求.
	 * </p>
	 */
	BAD_REQUEST(false, 400, null),
	/**
	 * <p>
	 *     Unauthorized: 未授权.
	 * </p>
	 */
	UNAUTHORIZED(false, 401, null),
	/**
	 * <p>
	 *     Payment Required: 保留, 将来使用.
	 * </p>
	 */
	PAYMENT_REQUIRED(false, 402, null),
	/**
	 * <p>
	 *     Forbidden: 禁止.
	 * </p>
	 */
	FORBIDDEN(false, 403, null),
	/**
	 * <p>
	 *     Not Found: 未找到.
	 * </p>
	 */
	NOT_FOUND(false, 404, null),
	/**
	 * <p>
	 *     Method Not Allowed: 方法禁用.
	 * </p>
	 */
	METHOD_NOT_ALLOWED(false, 405, null),
	/**
	 * <p>
	 *     Not Acceptable: 不接受.
	 * </p>
	 */
	NOT_ACCEPTABLE(false, 406, null),
	/**
	 * <p>
	 *     Proxy Authentication Required: 需要代理授权.
	 * </p>
	 */
	PROXY_AUTHENTICATION_REQUIRED(false, 407, null),
	/**
	 * <p>
	 *     Request Timeout: 请求超时.
	 * </p>
	 */
	REQUEST_TIMEOUT(false, 408, null),
	/**
	 * <p>
	 *     Conflict: 冲突.
	 * </p>
	 */
	CONFLICT(false, 409, null),
	/**
	 * <p>
	 *     Gone: 已删除.
	 * </p>
	 */
	GONE(false, 410, null),
	/**
	 * <p>
	 *     Length Required: 需要有效长度.
	 * </p>
	 */
	LENGTH_REQUIRED(false, 411, null),
	/**
	 * <p>
	 *     Precondition Failed: 未满足前提条件.
	 * </p>
	 */
	PRECONDITION_FAILED(false, 412, null),
	/**
	 * <p>
	 *     Request Entity Too Large: 请求实体过大.
	 * </p>
	 */
	REQUEST_ENTITY_TOO_LARGE(false, 413, null),
	/**
	 * <p>
	 *     Request-URI Too Long: 请求的 URI 过长.
	 * </p>
	 */
	REQUEST_URL_TOO_LARGE(false, 414, null),
	/**
	 * <p>
	 *     Unsupported Media Type: 不支持的媒体类型.
	 * </p>
	 */
	UNSUPPORTED_MEDIA_TYPE(false, 415, null),
	/**
	 * <p>
	 *     Requested Range Not Satisfiable: 请求范围不符合要求.
	 * </p>
	 */
	REQUESTED_RANGE_NOT_SATISFIABLE(false, 416, null),
	/**
	 * <p>
	 *     Expectation Failed: 未满足期望值.
	 * </p>
	 */
	EXPECTATION_FAILED(false, 417, null),
	
	
	
	//---------------------------------------------------------------------
	// 状态码 5XX
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     Internal Server Error: 服务器内部错误.
	 * </p>
	 */
	INTERNAL_SERVER_ERROR(false, 500, null),
	/**
	 * <p>
	 *     Not Implemented: 尚未实施.
	 * </p>
	 */
	NOT_IMPLEMENTED(false, 501, null),
	/**
	 * <p>
	 *     Bad Gateway: 错误网关.
	 * </p>
	 */
	BAD_GATEWAY(false, 502, null),
	/**
	 * <p>
	 *     Service Unavailable: 服务不可用.
	 * </p>
	 */
	SERVICE_UNAVAILABLE(false, 503, null),
	/**
	 * <p>
	 *     Gateway Timeout: 网关超时.
	 * </p>
	 */
	GATEWAY_TIMEOUT(false, 504, null),
	/**
	 * <p>
	 *     HTTP Version Not Supported: HTTP 版本不受支持.
	 * </p>
	 */
	HTTP_VERSION_NOT_SUPPORTED(false, 505, null);
	
	/**
	 * <p>
	 *     服务端回传 {@link HttpServletResponse 响应} 消息主体(<em>entity-body</em>)中的部分内容: 反馈给客户端此次请求是否成功.
	 * </p>
	 */
	private final Boolean success;
	/**
	 * <p>
	 *     服务端回传 {@link HttpServletResponse 响应} 消息主体(<em>entity-body</em>)中的部分内容: 反馈给客户端此次请求的状态码.
	 * </p>
	 */
	private final Integer code;
	/**
	 * <p>
	 *     服务端回传 {@link HttpServletResponse 响应} 消息主体(<em>entity-body</em>)中的部分内容: 反馈给客户端此次请求的消息.
	 * </p>
	 */
	private final String message;
	
	ResponseResultEnum(Boolean success, Integer code, String message) {
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
}
