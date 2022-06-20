package indi.ly.crush.excel.easy.annotation;

import indi.ly.crush.excel.easy.init.ExcelEntityInfoContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h2><span style="color: red;">Excel 实体标记注解</span></h2>
 * <p>
 *     在程序刚开始启动期间, 凡是被此注解修饰的实体类的相关信息都会被注册到 {@link ExcelEntityInfoContainer} 中.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelEntity {

}
