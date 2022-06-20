package indi.ly.crush.api.function;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Log4j2
@SpringBootTest
class ThrowableFunctionTest {
	
	@SneakyThrows
	@Test
	void apply() {
		var firstPathStr = "D:\\code\\universal_tools_github";
		var fileSuffixName = ".java";
		var path = Paths.get(firstPathStr);
		var fileCount = Files
				.walk(path)
				.peek(file -> log.info("获得路径为 {} 的文件目录下的所有文件目录的名称 ————> {}", firstPathStr, file.getFileName()))
				.filter(file -> file.toString().endsWith(fileSuffixName))
				.peek(file -> log.info("搜索到的 {} 文件名称 ————> {}", fileSuffixName, file.getFileName()))
				.flatMap(ThrowableFunction.applyWrapper(Files :: lines, e -> Stream.empty()))
				.filter(StringUtils :: hasLength)
				.count();
		log.info("count: {}", fileCount);
	}
}