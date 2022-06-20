package indi.ly.crush.http.response;


import indi.ly.crush.enumeration.ResponseResultEnum;

/**
 * <h2><span style="color: red">响应结果</span></h2>
 * <p>
 *     约定: <br />
 *     <ul>
 *         <li>
 *             {@link ResponseResultEnum#OK} —————— {@link #ok}.
 *         </li>
 *         <li>
 *             {@link ResponseResultEnum#BAD_REQUEST} —————— {@link #failure}.
 *         </li>
 *         <li>
 *             {@link ResponseResultEnum#INTERNAL_SERVER_ERROR} —————— {@link #error}.
 *         </li>
 *         <li>
 *             其它 {@link ResponseResultEnum} 对象 —————— {@link #set(ResponseResultEnum)} or {@link #set(ResponseResultEnum, Object)}.
 *         </li>
 *     </ul>
 * </p>
 *
 * @param <T> 客户端请求所希望的数据体的数据类型.
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public final class ResponseResult<T> {
	/**
	 * <p>
	 *     反馈客户端此次请求是否成功, 作为响应的消息主体的一部分存在.
	 * </p>
	 */
	private Boolean success;
	/**
	 * <p>
	 *     反馈客户端此次请求的响应状态码, 作为响应的消息主体的一部分存在. <br /> <br />
	 *
	 *
	 *     请注意: <br />
	 *     Restful 架构推崇复用 HTTP 的状态码, 不要再冗余自定义一个 “code“, 直接响应有效数据更为优雅.
	 *     但是接口交互可能需要更多的编码表示不同的业务含义, 因此在这里又定义了一个除了 HTTP 状态码之外的 “code“.
	 *     若无其它业务含义, 此 “code“ 一般会与 HTTP 状态码统一. 在具体实践中, 此 “code“ 也可能是字符串格式.
	 * </p>
	 */
	private Integer code;
	/**
	 * <p>
	 *     反馈客户端此次请求的响应消息, 作为响应的消息主体的一部分存在.
	 * </p>
	 */
	private String message;
	/**
	 * <p>
	 *     反馈客户端此次请求所希望的数据体, 作为响应的消息主体的一部分存在.
	 * </p>
	 */
	private T data;
	
	private ResponseResult() {}
	
	private ResponseResult(Boolean success, Integer code, String message, T data) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	//---------------------------------------------------------------------
	// ok、failure、error: ()
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> ok() {
		return create(
				ResponseResultEnum.OK.getSuccess(),
				ResponseResultEnum.OK.getCode(),
				ResponseResultEnum.OK.getMessage(),
				null
		);
	}
	
	public static <T> ResponseResult<T> failure() {
		return create(
				ResponseResultEnum.BAD_REQUEST.getSuccess(),
				ResponseResultEnum.BAD_REQUEST.getCode(),
				ResponseResultEnum.BAD_REQUEST.getMessage(),
				null
		);
	}
	
	public static <T> ResponseResult<T> error() {
		return create(
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getSuccess(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getCode(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getMessage(),
				null
		);
	}
	
	//---------------------------------------------------------------------
	// ok、failure、error: (String message)
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> ok(String message) {
		return create(
				ResponseResultEnum.OK.getSuccess(),
				ResponseResultEnum.OK.getCode(),
				message,
				null
		);
	}
	
	public static <T> ResponseResult<T> failure(String message) {
		return create(
				ResponseResultEnum.BAD_REQUEST.getSuccess(),
				ResponseResultEnum.BAD_REQUEST.getCode(),
				message,
				null
		);
	}
	
	public static <T> ResponseResult<T> error(String message) {
		return create(
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getSuccess(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getCode(),
				message,
				null
		);
	}
	
	//---------------------------------------------------------------------
	// ok、failure、error: (T data)
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> ok(T data) {
		return create(
				ResponseResultEnum.OK.getSuccess(),
				ResponseResultEnum.OK.getCode(),
				ResponseResultEnum.OK.getMessage(),
				data
		);
	}
	
	public static <T> ResponseResult<T> failure(T data) {
		return create(
				ResponseResultEnum.BAD_REQUEST.getSuccess(),
				ResponseResultEnum.BAD_REQUEST.getCode(),
				ResponseResultEnum.BAD_REQUEST.getMessage(),
				data
		);
	}
	
	public static <T> ResponseResult<T> error(T data) {
		return create(
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getSuccess(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getCode(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getMessage(),
				data
		);
	}
	
	//---------------------------------------------------------------------
	// ok、failure、error: (String message, T data)
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> ok(String message, T data) {
		return create(
				ResponseResultEnum.OK.getSuccess(),
				ResponseResultEnum.OK.getCode(),
				message,
				data
		);
	}
	
	public static <T> ResponseResult<T> failure(String message, T data) {
		return create(
				ResponseResultEnum.BAD_REQUEST.getSuccess(),
				ResponseResultEnum.BAD_REQUEST.getCode(),
				message,
				data
		);
	}
	
	public static <T> ResponseResult<T> error(String message, T data) {
		return create(
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getSuccess(),
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getCode(),
				message,
				data
		);
	}
	
	//---------------------------------------------------------------------
	// ok、failure、error: (Integer code, String message, T data)
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> ok(Integer code, String message, T data) {
		return create(
				ResponseResultEnum.OK.getSuccess(),
				code,
				message,
				data
		);
	}
	
	public static <T> ResponseResult<T> failure(Integer code, String message, T data) {
		return create(
				ResponseResultEnum.BAD_REQUEST.getSuccess(),
				code,
				message,
				data
		);
	}
	
	public static <T> ResponseResult<T> error(Integer code, String message, T data) {
		return create(
				ResponseResultEnum.INTERNAL_SERVER_ERROR.getSuccess(),
				code,
				message,
				data
		);
	}
	
	//---------------------------------------------------------------------
	// 其它
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> set(ResponseResultEnum responseEnum) {
		return create(
				responseEnum.getSuccess(),
				responseEnum.getCode(),
				responseEnum.getMessage(),
				null
		);
	}
	
	public static <T> ResponseResult<T> set(ResponseResultEnum responseEnum, T data) {
		return create(
				responseEnum.getSuccess(),
				responseEnum.getCode(),
				responseEnum.getMessage(),
				data
		);
	}
	
	//---------------------------------------------------------------------
	// 链式
	//---------------------------------------------------------------------
	
	public static <T> ResponseResult<T> empty() {
		return new ResponseResult<>();
	}
	
	public ResponseResult<T> success(Boolean success) {
		this.setSuccess(success);
		return this;
	}
	
	public ResponseResult<T> code(Integer code) {
		this.setCode(code);
		return this;
	}
	
	public ResponseResult<T> message(String message) {
		this.setMessage(message);
		return this;
	}
	
	public ResponseResult<T> data(T data) {
		this.setData(data);
		return this;
	}
	
	//---------------------------------------------------------------------
	// getter、setter
	//---------------------------------------------------------------------
	
	public Boolean getSuccess() {
		return this.success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getData() {
		return this.data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "ResponseResult{" + "success=" + success + ", code=" + code + ", message='" + message + ", data=" + data + '}';
	}
	
	public static <T> ResponseResult.ResponseResultBuilder<T> builder() {
		return new ResponseResult.ResponseResultBuilder<>();
	}
	
	private static <T> ResponseResult<T> create(Boolean success, Integer code, String message, T data) {
		return ResponseResult
				.<T>builder()
				.success(success)
				.code(code).message(message)
				.data(data)
				.build();
	}
	
	//---------------------------------------------------------------------
	// ResponseResult.ResponseResultBuilder
	//---------------------------------------------------------------------
	
	/**
	 * <p>
	 *     {@link ResponseResult} 构建器.
	 * </p>
	 *
	 * @param <T> {@link #data} 的数据类型, 由 {@link ResponseResult} 的泛型参数决定.
	 */
	public static class ResponseResultBuilder<T> {
		/**
		 * <p>
		 *     语义与 {@link ResponseResult#success} 一致.
		 * </p>
		 */
		private Boolean success;
		/**
		 * <p>
		 *     语义与 {@link ResponseResult#code} 一致.
		 * </p>
		 */
		private Integer code;
		/**
		 * <p>
		 *     语义与 {@link ResponseResult#message} 一致.
		 * </p>
		 */
		private String message;
		/**
		 * <p>
		 *     语义与 {@link ResponseResult#data} 一致.
		 * </p>
		 */
		private T data;
		
		ResponseResultBuilder() {}
		
		public ResponseResult.ResponseResultBuilder<T> success(final Boolean success) {
			this.success = success;
			return this;
		}
		
		public ResponseResult.ResponseResultBuilder<T> code(final Integer code) {
			this.code = code;
			return this;
		}
		
		public ResponseResult.ResponseResultBuilder<T> message(final String message) {
			this.message = message;
			return this;
		}
		
		public ResponseResult.ResponseResultBuilder<T> data(final T data) {
			this.data = data;
			return this;
		}
		
		public ResponseResult<T> build() {
			return new ResponseResult<>(this.success, this.code, this.message, this.data);
		}
		
		@Override
		public String toString() {
			return "ResponseResult.ResponseResultBuilder(success=" + this.success + ", code=" + this.code + ", message=" + this.message + ", data=" + this.data + ")";
		}
	}
}
