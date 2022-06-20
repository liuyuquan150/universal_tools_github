package indi.ly.crush.aware;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <h2><span style="color: red;">Spring 应用程序上下文持有者</span></h2>
 * <p>
 *     实现 {@link ApplicationContextAware} 接口和 {@link ApplicationContextAware#setApplicationContext} 回调方法,
 *     使内部持有 {@link ApplicationContext} 静态属性完成赋值, 再通过定义静态方法来对外提供获取 Bean 的能力.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "SpringApplicationContextAware")
public class SpringApplicationContextHolder
		implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext)
			throws BeansException {
		SpringApplicationContextHolder.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name) {
		return SpringApplicationContextHolder.applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return SpringApplicationContextHolder.applicationContext.getBean(clazz);
	}
	
	public static <T> T getBean(String name, Class<T> clazz) {
		return SpringApplicationContextHolder.applicationContext.getBean(name, clazz);
	}
	
	public static <T> T getBean(Class<T> clazz, String args) {
		return SpringApplicationContextHolder.applicationContext.getBean(clazz,args);
	}
	
	public static Boolean containsBean(String name) {
		return SpringApplicationContextHolder.applicationContext.containsBean(name);
	}
	
	public static Boolean isSingleton(String name) {
		return SpringApplicationContextHolder.applicationContext.isSingleton(name);
	}
	
	public static Class<?> getType(String name) {
		return SpringApplicationContextHolder.applicationContext.getType(name);
	}
}
