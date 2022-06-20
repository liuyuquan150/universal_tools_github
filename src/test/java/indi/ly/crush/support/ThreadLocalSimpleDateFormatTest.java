package indi.ly.crush.support;

import indi.ly.crush.support.date.ThreadLocalSimpleDateFormat;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Objects;

/**
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class ThreadLocalSimpleDateFormatTest {
	
	@Test
	void test() {
		var threadLocalSimpleDateFormatRunnable = new ThreadLocalSimpleDateFormatRunnable();
		for (int i = 0; i < 10; i++) new Thread(threadLocalSimpleDateFormatRunnable).start();
	}
}

@Log4j2
class ThreadLocalSimpleDateFormatRunnable
		implements Runnable {
	@Override
	public void run() {
		try {
			ThreadLocalSimpleDateFormat.setSimpleDateFormat("HH:mm:ss");
			var formatDateStr = ThreadLocalSimpleDateFormat.format(new Date());
			log.info("{}",formatDateStr);
			var parseDate = ThreadLocalSimpleDateFormat.parse(formatDateStr);
			var currentThreadName = Thread.currentThread().getName();
			var isSecurity = Objects.equals(formatDateStr, ThreadLocalSimpleDateFormat.format(parseDate));
			log.info("{} 线程是否安全: {}", currentThreadName, isSecurity);
		} finally {
			ThreadLocalSimpleDateFormat.clearSimpleDateFormat();
		}
	}
}