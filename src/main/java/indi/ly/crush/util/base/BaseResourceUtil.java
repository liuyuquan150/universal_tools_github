package indi.ly.crush.util.base;

import indi.ly.crush.api.function.ThrowableFunction;
import indi.ly.crush.excel.easy.init.ExcelEntityInfoContainer;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * <h2><span style="color: red;">资源工具类</span></h2>
 * <p>
 *     继承于 {@link ResourceUtils}, 并对一些常需要进行取反操作的方法进行了封装, 使其语义更为清晰明了.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class BaseResourceUtil
		extends ResourceUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseResourceUtil.class);
	
	public static Resource[] pathSearchResources(String packagePath) {
		return pathSearchResources(new PathMatchingResourcePatternResolver(),packagePath);
	}
	
	@SneakyThrows(value = IOException.class)
	public static Resource[] pathSearchResources(ResourcePatternResolver r, String packagePath) {
		BaseObjectUtil.isNull(r, packagePath);
		
		var resourceLocation= SystemPropertyUtils.resolvePlaceholders(packagePath);
		var resourcePath = BaseClassUtil.convertClassNameToResourcePath(resourceLocation);
		var packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resourcePath + "/**/*.class";
		
		return r.getResources(packageSearchPath);
	}
	
	public static Set<Class<?>> findAllSpecifiedClass(String packagePath, Predicate<ClassMetadata> p, BiConsumer<Set<Class<?>>, ClassMetadata> bi) {
		var r = new PathMatchingResourcePatternResolver();
		var c = new CachingMetadataReaderFactory(r);
		return findAllSpecifiedClass(r, c,packagePath, p,bi);
	}
	
	/**
	 * <p>
	 *     查找所有符合指定条件的 {@link Class}, 但是这个指定条件(<em>{@link Predicate}、{@link BiConsumer}</em>)是由使用者自己实现的.
	 * </p>
	 * <br />
	 *
	 * <h3>这是一种常见的使用方式: {@link ExcelEntityInfoContainer#register()}</h3>
	 * <p>
	 *     搜索 indi.ly.crush.model.entity 路径下的所有修饰了 Excel 注解的实体类, 将其字节码对象放入到容器中.
	 *
	 *     <pre>{@code
	 *                  var r = new PathMatchingResourcePatternResolver();
	 *                  var packagePath = "indi.ly.crush.model.entity";
	 *                  Predicate<ClassMetadata> p = ClassMetadata :: isConcrete;
	 *                  BiConsumer<Set<Class<?>>, ClassMetadata> bi = (classSet, classMetadata) -> {
	 *                      var className = classMetadata.getClassName();
	 *                      Class<?> entityClass = null;
	 *                      try {
	 *                          entityClass = Class.forName(className);
	 *                      } catch (ClassNotFoundException e) {
	 *                          e.printStackTrace();
	 *                      }
	 *                      if (entityClass != null && entityClass.getAnnotation(Excel.class) != null) {
	 *                          classSet.add(entityClass);
	 *                      }
	 *                  }
	 *
	 *                  var classSet = findAllSpecifiedClass(r, packagePath, p, bi);
	 *     }</pre>
	 * </p>
	 *
	 * @param r           用于将位置模式(<em>例如, Ant 样式的路径模式</em>) 解析为 {@link Resource} 对象的策略接口实现类.
	 * @param m           {@link MetadataReader} 实例的工厂接口实现类.
	 * @param packagePath 包路径, 例如: indi.ly.crush.model.entity.
	 * @param p           {@link ClassMetadata#isAbstract}、{@link ClassMetadata#isAnnotation}、{@link ClassMetadata#isConcrete}、
	 *                    {@link ClassMetadata#isFinal}、{@link ClassMetadata#isIndependent}、{@link ClassMetadata#isInterface}
	 * @param bi          {@link ClassMetadata#getClassName}、{@link ClassMetadata#getEnclosingClassName}、{@link ClassMetadata#getInterfaceNames}、
	 *                    {@link ClassMetadata#getMemberClassNames}、{@link ClassMetadata#getSuperClassName}
	 * @return 符合指定条件的所有字节码对象.
	 */
	public static Set<Class<?>> findAllSpecifiedClass(ResourcePatternResolver r, MetadataReaderFactory m, String packagePath, Predicate<ClassMetadata> p, BiConsumer<Set<Class<?>>, ClassMetadata> bi) {
		BaseObjectUtil.isNull(r, m, packagePath, p, bi);
		var resources = pathSearchResources(r, packagePath);
		
		return Arrays
				.stream(resources)
				.filter(Resource :: isReadable)
				.map(ThrowableFunction.applyWrapper(m :: getMetadataReader))
				.filter(Objects :: nonNull)
				.map(MetadataReader :: getClassMetadata)
				.filter(p)
				.collect(LinkedHashSet :: new, bi, Set :: addAll);
	}
}
