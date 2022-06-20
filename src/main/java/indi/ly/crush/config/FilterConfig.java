package indi.ly.crush.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

/**
 * <h2><span style="color: red;">过滤器配置</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Configuration
public class FilterConfig {
	
	/**
	 * <p>
	 *     创建一个 {@link FilterRegistrationBean 过滤器注册组件}, 注册一个 {@link CharacterEncodingFilter 字符编码过滤器}(<em>它的配置如下</em>):
	 *     <ul>
	 *         <li>
	 *             ”{@link Ordered#HIGHEST_PRECEDENCE} + 1“ 优先级
	 *         </li>
	 *         <li>
	 *             ”UTF-8“ 字符编码
	 *         </li>
	 *         <li>
	 *             ”/*“ 过滤路径
	 *         </li>
	 *     </ul>
	 * </p>
	 *
	 * @return 一个 {@link FilterRegistrationBean 过滤器注册组件}, 它已注册定制好配置的 {@link CharacterEncodingFilter 字符编码过滤器}.
	 */
	@Bean(name = "CharacterEncodingFilter")
	public FilterRegistrationBean<? extends Filter> createCharacterEncodingFilterBean() {
		var characterEncodingFilter = new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true);
		var filterRegistrationBean = new FilterRegistrationBean<>(characterEncodingFilter);
		filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		filterRegistrationBean.addUrlPatterns();
		return filterRegistrationBean;
	}
}
