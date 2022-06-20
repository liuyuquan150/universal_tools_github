package indi.ly.crush.support.agent.entrance.test.command;

import indi.ly.crush.support.agent.entrance.test.command.impl.ClassAgentCommand;
import indi.ly.crush.support.agent.entrance.test.command.impl.MethodAgentCommand;
import indi.ly.crush.support.agent.entrance.test.command.impl.PackageAgentCommand;
import indi.ly.crush.util.base.BaseObjectUtil;
import indi.ly.crush.util.base.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">agent 指令构建者</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class AgentCommandBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentCommandBuilder.class);
	/**
	 * <p>
	 *     agent 指令分隔符. <br /> <br />
	 *
	 *     对于一个 agent 指令字符串来说, 它可能是多个 agent 指令的组成.
	 * </p>
	 */
	private static final String AGENT_COMMAND_SEPARATOR = "\\|\\|";
	
	public static IAgentCommand buildAgentCommand(String agentCommandParameter) {
		if (BaseStringUtil.notHasLength(agentCommandParameter)) {
			return null;
		}
		
		var correctAgentCommandStrings =
				Arrays
						.stream(agentCommandParameter.split(AGENT_COMMAND_SEPARATOR))
						.collect((Supplier<List<String>>) ArrayList :: new, List :: add, List :: addAll);
		
		var wrongAgentCommandStrings = new ArrayList<>(correctAgentCommandStrings.size());
		
		var correctAgentCommand =
				correctAgentCommandStrings
						.stream()
						.map(String :: trim)
						.map(agentCommand ->
							Optional
									.<IAgentCommand>empty()
									.or(ClassAgentCommand.createClassAgentCommand(agentCommand))
									.or(MethodAgentCommand.createMethodAgentCommand(agentCommand))
									.or(PackageAgentCommand.createPackageAgentCommand(agentCommand))
									.orElseGet(() -> {
										wrongAgentCommandStrings.add(agentCommand);
										return null;
									}))
						.filter(BaseObjectUtil :: isNotEmpty)
						.toList();
		
		if (wrongAgentCommandStrings.size() > 0) {
			LOGGER.debug("错误的 Agent 指令: {}", wrongAgentCommandStrings);
		}
		
		LOGGER.debug("正确的 Agent 指令: {}", correctAgentCommand);
		return new AgentCommandExecutor(correctAgentCommand);
	}
}
