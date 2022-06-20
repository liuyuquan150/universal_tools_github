package indi.ly.crush.support.agent.entrance.test.command.impl;

import indi.ly.crush.support.agent.entrance.test.command.AgentCommandComposition;
import indi.ly.crush.support.agent.entrance.test.command.AgentCommandIdentifierEnum;
import indi.ly.crush.support.agent.entrance.test.command.IAgentCommand;
import indi.ly.crush.util.base.BaseStringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">类部分 Agent 指令</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
public class ClassAgentCommand
		implements IAgentCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassAgentCommand.class);
	private String className;
	
	private ClassAgentCommand(String className) {
		this.className = className;
		this.printMessage();
	}
	
	public static Supplier<Optional<IAgentCommand>> createClassAgentCommand(String agentCommandParameter) {
		var classAgentCommandIdentifierValue = AgentCommandIdentifierEnum.CLASS_AGENT_COMMAND_IDENTIFIER.getValue();
		var l = classAgentCommandIdentifierValue.length();
		var className = agentCommandParameter.substring( l);
		
		var classAgentCommand = BaseStringUtil.notStartsWith(agentCommandParameter, classAgentCommandIdentifierValue) ? null : new ClassAgentCommand(className);
		return () -> Optional.ofNullable(classAgentCommand);
	}
	
	@Override
	public Boolean whetherToExecuteCommand(AgentCommandComposition agentCommandComposition) {
		return Objects.equals(this.className, agentCommandComposition.getClassName());
	}
	
	@Override
	public void printMessage() {
		LOGGER.debug("{}", this.className);
	}
}
