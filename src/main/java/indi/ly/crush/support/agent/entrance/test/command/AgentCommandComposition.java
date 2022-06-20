package indi.ly.crush.support.agent.entrance.test.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h2><span style="color: red;">agent 指令构成</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class AgentCommandComposition {
	/**
	 * <p>
	 *     类名称.
	 * </p>
	 */
	private String className;
	/**
	 * <p>
	 *     类上注解的描述符.
	 * </p>
	 */
	private String classAnnotationDescription;
	/**
	 * <p>
	 *     方法名称.
	 * </p>
	 */
	private String methodName;
	/**
	 * <p>
	 *     方法上注解的描述符.
	 * </p>
	 */
	private String methodAnnotationDescription;
}
