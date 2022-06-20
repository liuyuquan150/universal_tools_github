package indi.ly.crush.excel.easy.init;

import com.alibaba.excel.annotation.ExcelProperty;
import indi.ly.crush.api.function.ThrowableBiConsumer;
import indi.ly.crush.excel.easy.annotation.ExcelEntity;
import indi.ly.crush.util.base.BaseMapUtil;
import indi.ly.crush.util.base.BaseResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h2><span style="color: red;">Excel 实体信息容器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Component(value = "ExcelEntityInfoContainer")
public class ExcelEntityInfoContainer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelEntityInfoContainer.class);
	/**
	 * <p>
	 *     Map<实体类字节码对象, Map<实体类属性名称, ExcelProperty注解index方法的值>>.
	 * </p>
	 */
	public static Map<Class<?>, Map<String, Integer>> entityInfo;
	private final ResourcePatternResolver r;
	private final MetadataReaderFactory m;
	
	public ExcelEntityInfoContainer(ResourceLoader resourceLoader) {
		LOGGER.debug("ResourceLoader: {}", resourceLoader);
		this.r = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		this.m = new CachingMetadataReaderFactory(resourceLoader);
	}
	
	@PostConstruct
	public void register() {
		var clazzSet = BaseResourceUtil.findAllSpecifiedClass(r, m, "indi.ly.crush", ClassMetadata :: isConcrete, ThrowableBiConsumer.acceptWrapper(
													(classSet, classMetadata) -> {
															var className = classMetadata.getClassName();
															var entityClass = Class.forName(className);
															if (entityClass.getAnnotation(ExcelEntity.class) != null) {
																classSet.add(entityClass);
															}
														})
													);
		LOGGER.debug("ClassSet: {}", clazzSet);
		
		var size = clazzSet.size();
		int initialCapacity = BaseMapUtil.rationalInitial(size);
		entityInfo = new HashMap<>(initialCapacity);
		
		for (var clazz : clazzSet) {
			var declaredFields = clazz.getDeclaredFields();
			var l =  declaredFields.length;
			if (l > 0) {
				initialCapacity = BaseMapUtil.rationalInitial(l);
				var entityFieldInfo = new LinkedHashMap<String, Integer>(initialCapacity);
				
				Arrays
					.stream(declaredFields)
					.forEachOrdered(declaredField -> {
						var excelPropertyAnnotation = declaredField.getAnnotation(ExcelProperty.class);
						if (excelPropertyAnnotation != null) {
							var fieldName = declaredField.getName();
							var columnIndex = excelPropertyAnnotation.index() + 1;
							entityFieldInfo.put(fieldName, columnIndex);
						}
					});
				
				if (entityFieldInfo.isEmpty()) {
					break;
				}
				entityInfo.put(clazz, entityFieldInfo);
			}
		}
		LOGGER.debug("EntityInfo: {}", entityInfo);
	}
}
