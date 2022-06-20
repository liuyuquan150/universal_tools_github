package indi.ly.crush.util.base.unittest;

import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

/**
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "MockHttpServletGetRequestBuilderHandler")
public class MockHttpServletGetRequestBuilderHandler
		implements MockHttpServletRequestBuilderHandler, Ordered {
	@Override
	public MockHttpServletRequestBuilder handle(
			MockHttpServletRequestType mockHttpServletRequestType, String url, Object body, MultiValueMap<String, String> params, MediaType contentType) {
		if (MockHttpServletRequestType.GET != mockHttpServletRequestType) {
			return null;
		}
	
		var mediaType = isOrElseMediaType(contentType, MediaType.APPLICATION_FORM_URLENCODED);
		
		var builder = MockMvcRequestBuilders.get(url);
		if (params != null) {
			builder.params(params);
		}
		return builder.contentType(mediaType);
	}
	
	@Override
	public int getOrder() {
		return 1000;
	}
}
