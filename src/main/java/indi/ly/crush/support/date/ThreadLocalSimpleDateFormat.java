package indi.ly.crush.support.date;

import lombok.SneakyThrows;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h2><span style="color: red;">线程本地 {@link SimpleDateFormat}</span></h2>
 * <p>
 *     解决了 {@link SimpleDateFormat} 在多线程环境下会出现安全问题的问题. <br /> <br />
 *
 *
 *     在 {@literal Java} 的多线程并发执行过程中, 为了保证多个线程对变量的安全访问,
 *     可以将变量放到 {@link ThreadLocal} 类型的对象中, 使变量在每个线程中都有独立值,
 *     不会出现一个线程读取变量时被另一个线程修改的现象. <br /> <br />
 *
 *
 *     {@link ThreadLocal} 类通常被翻译为 “线程本地变类” 类或 “线程局部变量” 类.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
public abstract class ThreadLocalSimpleDateFormat {
	private static final String SIMPLE_DATE_FORMAT_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL =
			ThreadLocal.withInitial(() -> new SimpleDateFormat(SIMPLE_DATE_FORMAT_DEFAULT_PATTERN));
	
	public static String format(Date date) {
		Assert.notNull(date, () -> "Date Object is null");
		return SIMPLE_DATE_FORMAT_THREAD_LOCAL.get().format(date);
	}
	
	@SneakyThrows(value = ParseException.class)
	public static Date parse(String dateStr) {
		Assert.hasText(dateStr, () -> "Date String 不是一个有效的");
		return SIMPLE_DATE_FORMAT_THREAD_LOCAL.get().parse(dateStr);
	}
	
	public static void setSimpleDateFormat(String pattern) {
		Assert.hasText(pattern, () -> "pattern 不是一个有效的");
		SIMPLE_DATE_FORMAT_THREAD_LOCAL.set(new SimpleDateFormat(pattern));
	}
	
	public static void clearSimpleDateFormat() {
		SIMPLE_DATE_FORMAT_THREAD_LOCAL.remove();
	}
}
