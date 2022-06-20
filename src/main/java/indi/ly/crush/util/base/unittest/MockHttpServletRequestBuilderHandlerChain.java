package indi.ly.crush.util.base.unittest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "MockHttpServletRequestTypeHandlerChain")
@SuppressWarnings(value = {"ClassCanBeRecord", "AlibabaUndefineMagicConstant"})
public class MockHttpServletRequestBuilderHandlerChain {
	private static final Logger LOGGER = LoggerFactory.getLogger(MockHttpServletRequestBuilderHandlerChain.class);
	private final List<MockHttpServletRequestBuilderHandler> handlers;
	
	public MockHttpServletRequestBuilderHandlerChain(List<MockHttpServletRequestBuilderHandler> handlers) {
		this.handlers = handlers;
	}

	protected MockHttpServletRequestBuilder handle(
			MockHttpServletRequestType mockHttpServletRequestType, String url,
			Boolean enableHttps, Object body, MultiValueMap<String, String> params, MediaType contentType,
			HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies
	) {
		for (MockHttpServletRequestBuilderHandler handler : Objects.requireNonNull(handlers, "存储所有处理器的集合容器不存在")) {
			var builder = handler.handle(mockHttpServletRequestType, url, body, params,contentType);
			if (builder != null) {
				if (mockHttpSession != null) {
					builder.session(mockHttpSession);
				}
				if (cookies != null) {
					builder.cookie(cookies);
				}
				return builder
							.characterEncoding(StandardCharsets.UTF_8)
							.accept(MediaType.APPLICATION_JSON)
							.headers(httpHeaders)
							.secure(enableHttps)
							.locale(Locale.CHINESE);
			}
		}
		
		LOGGER.debug("已注册的所有处理器: {}", handlers);
		throw new IllegalArgumentException("没有找到对应的处理器: " + mockHttpServletRequestType.toString());
	}
}
