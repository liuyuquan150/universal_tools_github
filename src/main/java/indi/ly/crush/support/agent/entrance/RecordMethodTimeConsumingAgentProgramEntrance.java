package indi.ly.crush.support.agent.entrance;

import indi.ly.crush.support.agent.entrance.test.MethodExecuteTimingTransformer;
import indi.ly.crush.support.agent.entrance.test.command.AgentCommandBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.Optional;

/**
 * <h2><span style="color: red;">记录方法耗时代理程序入口</span></h2>
 * <p>
 *     Java Agent 直译为 Java 代理, 也常常被称为 Java 探针技术. <br /> <br />
 *
 *
 *     Java Agent 是一个遵循一组严格约定的常规 Java 类: <br />
 *     <ol>
 *         <li>
 *             必须定义一个 MANIFEST.MF 文件,
 *             且包含 Premain-Class、Agent-Class 参数,
 *             可选参数有 Manifest-Version、Can-Redefine-Classes、Can-Retransform-Classes 等.
 *         </li>
 *         <li>
 *             MANIFEST.MF 文件中 Premain-Class / Agent-Class 所指向的类,
 *             类中必须包含 premain 方法 / agentmain 方法, 方法逻辑由用户自己确定.
 *         </li>
 *     </ol>
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
public class RecordMethodTimeConsumingAgentProgramEntrance {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordMethodTimeConsumingAgentProgramEntrance.class);
	
	public static void premain(String agentArgs, Instrumentation instrumentation) {
		Optional
				.ofNullable(AgentCommandBuilder.buildAgentCommand(agentArgs))
				.ifPresent(agentCommand -> instrumentation.addTransformer(new MethodExecuteTimingTransformer(agentCommand)));
	}
	
	public static void premain(String agentArgs) {
	}
	
	public static void agentmain(String agentArgs, Instrumentation inst) {
	}
	
	public static void agentmain(String agentArgs) {
	}
}
