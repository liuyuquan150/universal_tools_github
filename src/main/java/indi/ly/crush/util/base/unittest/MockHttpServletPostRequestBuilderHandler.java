package indi.ly.crush.util.base.unittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
@Component(value = "MockHttpServletPostRequestBuilderHandler")
public class MockHttpServletPostRequestBuilderHandler
		implements MockHttpServletRequestBuilderHandler, Ordered {
	private final ObjectMapper objectMapper;
	
	public MockHttpServletPostRequestBuilderHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@SneakyThrows(value = JsonProcessingException.class)
	@Override
	public MockHttpServletRequestBuilder handle(
			MockHttpServletRequestType mockHttpServletRequestType, String url, Object body, MultiValueMap<String, String> params, MediaType contentType) {
		if (MockHttpServletRequestType.POST != mockHttpServletRequestType) {
			return null;
		}
		
		var mediaType = isOrElseMediaType(contentType, MediaType.APPLICATION_JSON);
		
		var builder = MockMvcRequestBuilders.post(url);
		if (body != null) {
			var json = objectMapper.writeValueAsString(body);
			builder.content(json);
		}
		return builder.contentType(mediaType);
	}
	
	@Override
	public int getOrder() {
		return 1001;
	}
}
