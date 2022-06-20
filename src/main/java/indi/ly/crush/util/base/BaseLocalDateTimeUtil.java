package indi.ly.crush.util.base;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2><span style="color: red;">本地日期时间工具类</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseLocalDateTimeUtil {
	
	public static String format() {
		return format("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String format(String pattern) {
		Assert.isTrue(BaseStringUtil.hasLength(pattern), () -> "pattern not has length.");
		var dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.now().format(dateTimeFormatter);
	}
	
	public static LocalDateTime parse(String text, String format) {
		BaseObjectUtil.isNull(text);
		return format == null ? LocalDateTime.parse(text) : LocalDateTime.parse(text,DateTimeFormatter.ofPattern(format));
	}
}
