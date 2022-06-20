package indi.ly.crush.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * <h2><span style="color: red;">Kaptcha 验证码配置</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 * @formatter:off
 */
@Configuration
@PropertySource(value = {"classpath:kaptcha.properties"}, encoding = "UTF-8")
public class KaptchaCaptchaConfig {
	
	@Value(value = "${kaptcha.border}")
	private String border;
	
	@Value(value = "${kaptcha.border.color}")
	private String borderColor;
	
	@Value(value = "${kaptcha.image.width}")
	private String imageWidth;
	
	@Value(value = "${kaptcha.image.height}")
	private String imageHeight;
	
	@Value(value = "${kaptcha.session.key}")
	private String sessionKey;
	
	@Value(value = "${kaptcha.textproducer.font.color}")
	private String fontColor;
	
	@Value(value = "${kaptcha.textproducer.font.size}")
	private String fontSize;
	
	@Value(value = "${kaptcha.textproducer.char.length}")
	private String charLength;
	
	@Value(value = "${kaptcha.textproducer.font.names}")
	private String fontNames;
	
	@Bean(name = {"CaptchaProducer", "DefaultKaptcha"})
	public Producer createDefaultKaptchaBean() {
		var defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(new Config(this.settingCaptchaProperties()));
		return defaultKaptcha;
	}
	
	/**
	 * <p>
	 *     设置验证码属性并返回配置对象.
	 * </p>
	 *
	 * @return 验证码属性配置对象.
	 */
	private Properties settingCaptchaProperties() {
		Properties captchaProperties = new Properties();
		captchaProperties.setProperty("kaptcha.border", this.border);
		captchaProperties.setProperty("kaptcha.border.color", this.borderColor);
		captchaProperties.setProperty("kaptcha.image.width", this.imageWidth);
		captchaProperties.setProperty("kaptcha.image.height", this.imageHeight);
		captchaProperties.setProperty("kaptcha.session.key", this.sessionKey);
		captchaProperties.setProperty("kaptcha.textproducer.font.color", this.fontColor);
		captchaProperties.setProperty("kaptcha.textproducer.font.size", this.fontSize);
		captchaProperties.setProperty("kaptcha.textproducer.char.length", this.charLength);
		captchaProperties.setProperty("kaptcha.textproducer.font.names", this.fontNames);
		return captchaProperties;
	}
}
