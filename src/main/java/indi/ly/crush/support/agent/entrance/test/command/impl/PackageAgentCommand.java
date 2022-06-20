package indi.ly.crush.support.agent.entrance.test.command.impl;

import indi.ly.crush.support.agent.entrance.test.command.AgentCommandComposition;
import indi.ly.crush.support.agent.entrance.test.command.AgentCommandIdentifierEnum;
import indi.ly.crush.support.agent.entrance.test.command.IAgentCommand;
import indi.ly.crush.util.base.BaseStringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * <h2><span style="color: red;">包部分 Agent 指令</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
public class PackageAgentCommand
		implements IAgentCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(PackageAgentCommand.class);
	private String packageName;
	
	private PackageAgentCommand(String packageName) {
		this.packageName = packageName;
		this.printMessage();
	}
	
	public static Supplier<Optional<IAgentCommand>> createPackageAgentCommand(String agentCommandParameter) {
		var packageAgentCommandIdentifierValue = AgentCommandIdentifierEnum.PACKAGE_AGENT_COMMAND_IDENTIFIER.getValue();
		var l = packageAgentCommandIdentifierValue.length();
		var packageName = agentCommandParameter.substring(l);
		
		var packageAgentCommand =  (BaseStringUtil.notStartsWith(agentCommandParameter, packageAgentCommandIdentifierValue) ? null : new PackageAgentCommand(packageName));
		return () -> Optional.ofNullable(packageAgentCommand);
	}
	
	@Override
	public Boolean whetherToExecuteCommand(AgentCommandComposition agentCommandComposition) {
		return agentCommandComposition
				.getClassName()
				.startsWith(this.packageName);
	}
	
	@Override
	public void printMessage() {
		LOGGER.debug("{}", this.packageName);
	}
}
