package indi.ly.crush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * <h2><span style="color: red;">公共应用程序</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@SpringBootApplication(scanBasePackages = {"indi.ly.crush"}, exclude = {SecurityAutoConfiguration.class})
public class MyCommonApplication {
	public static void main(String[] args) {
		var crushCommonApplication = new SpringApplication(MyCommonApplication.class);
		crushCommonApplication.run(args);
	}
}
