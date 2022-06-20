package indi.ly.crush.util.base.unittest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

/**
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@SuppressWarnings(value = "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface MockHttpServletRequestBuilderHandler {
	MockHttpServletRequestBuilder handle(MockHttpServletRequestType requestType, String url, Object body, MultiValueMap<String, String> params, MediaType contentType);
	
	default MediaType isOrElseMediaType(MediaType mediaType, MediaType defaultMediaType) {
		return Optional
					.ofNullable(mediaType)
					.orElse(defaultMediaType);
	}
}
