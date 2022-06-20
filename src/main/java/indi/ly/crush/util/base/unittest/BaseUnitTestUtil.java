package indi.ly.crush.util.base.unittest;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;

/**
 * <h2><span style="color: red;">单元测试工具类</span></h2>
 * <p>
 *     提供了对 MockMvc API 的简单封装.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false, print = MockMvcPrint.NONE)
public abstract class BaseUnitTestUtil {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	private MockHttpServletRequestBuilderHandlerChain chain;
	
	//---------------------------------------------------------------------
	// Send Get Request
	//---------------------------------------------------------------------
	
	protected ResultActions sendGetRequest(String url) {
		return sendGetRequest( url, false, null, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendGetRequest(String url, Boolean enableHttps) {
		return sendGetRequest( url, enableHttps, null, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendGetRequestWithData(String url, MultiValueMap<String, String> params) {
		return sendGetRequest( url, false, params, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendGetRequestWithData(String url, Boolean enableHttps, MultiValueMap<String, String> params) {
		return sendGetRequest( url, enableHttps, params, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendGetRequestWithData(String url, MultiValueMap<String, String> params, MediaType contentType) {
		return sendGetRequest( url, false, params, contentType,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendGetRequestWithData(String url, Boolean enableHttps, MultiValueMap<String, String> params, MediaType contentType) {
		return sendGetRequest( url, enableHttps, params, contentType,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendGetRequestWithData(String url, MultiValueMap<String, String> params, HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies) {
		return sendGetRequest( url, false, params, null,httpHeaders, mockHttpSession, cookies);
	}
	
	protected ResultActions securelySendGetRequestWithData(String url, MultiValueMap<String, String> params, MediaType contentType, HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies) {
		return sendGetRequest( url, true, params, contentType,httpHeaders, mockHttpSession, cookies);
	}
	
	
	private ResultActions sendGetRequest(
			String url, Boolean enableHttps, MultiValueMap<String, String> params, MediaType contentType,
			HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies
	) {
		return perform(MockHttpServletRequestType.GET, url, enableHttps, null,params, contentType,httpHeaders, mockHttpSession, cookies);
	}
	
	
	
	//---------------------------------------------------------------------
	// Send Post Request
	//---------------------------------------------------------------------
	
	protected ResultActions sendPostRequest(
			String url
	) {
		return sendPostRequest( url, false, null, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendPostRequest(String url, Boolean enableHttps) {
		return sendPostRequest( url, enableHttps, null, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendPostRequestWithBody(String url, Object body) {
		return sendPostRequest( url, false, body, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendPostRequestWithBody(String url, Boolean enableHttps, Object body) {
		return sendPostRequest( url, enableHttps, body, null,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendPostRequestWithBody(String url, Object body, MediaType contentType) {
		return sendPostRequest( url, false, body, contentType,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	protected ResultActions securelySendPostRequestWithBody(String url, Boolean enableHttps, Object body, MediaType contentType) {
		return sendPostRequest( url, enableHttps, body, contentType,HttpHeaders.EMPTY, null, (Cookie[]) null);
	}
	
	
	protected ResultActions sendPostRequestWithBody(String url, Object body, HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies) {
		return sendPostRequest( url, false, body, null,httpHeaders, mockHttpSession, cookies);
	}
	
	protected ResultActions securelySendPostRequestWithBody(String url, Object body, MediaType contentType, HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies) {
		return sendPostRequest( url, true, body, contentType,httpHeaders, mockHttpSession, cookies);
	}
	
	
	private ResultActions sendPostRequest(
			String url, Boolean enableHttps, Object body, MediaType contentType,
			HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies
	) {
		return perform(MockHttpServletRequestType.POST, url, enableHttps, body, null,contentType,httpHeaders, mockHttpSession, cookies);
	}
	
	
	
	//---------------------------------------------------------------------
	// perform
	//---------------------------------------------------------------------
	
	@SneakyThrows(value = Exception.class)
	private ResultActions perform(
			MockHttpServletRequestType requestType, String url, Boolean enableHttps, Object body, MultiValueMap<String, String> params,
			MediaType contentType, HttpHeaders httpHeaders, MockHttpSession mockHttpSession, Cookie... cookies
	) {
		
		var requestBuilder = chain.handle(requestType, url, enableHttps, body, params,contentType,httpHeaders, mockHttpSession, cookies);
		// perform: 执行请求
		return mockMvc.perform(requestBuilder);
	}
}
