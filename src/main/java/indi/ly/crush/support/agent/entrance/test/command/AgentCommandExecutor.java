package indi.ly.crush.support.agent.entrance.test.command;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <h2><span style="color: red;">agent 指令执行器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
public class AgentCommandExecutor
		implements IAgentCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentCommandExecutor.class);
	private List<IAgentCommand> agentCommands;
	
	public AgentCommandExecutor(List<IAgentCommand> agentCommands) {
		this.agentCommands = agentCommands;
		this.printMessage();
	}
	
	@Override
	public Boolean whetherToExecuteCommand(AgentCommandComposition agentCommandComposition) {
		return this.agentCommands
					.stream()
					.anyMatch(agentCommand -> agentCommand.whetherToExecuteCommand(agentCommandComposition));
	}
	
	@Override
	public void printMessage() {
		LOGGER.debug("{}", this.agentCommands.size());
	}
}
