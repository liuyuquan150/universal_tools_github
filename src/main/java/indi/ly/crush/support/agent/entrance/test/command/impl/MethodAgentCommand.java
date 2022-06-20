package indi.ly.crush.support.agent.entrance.test.command.impl;

import indi.ly.crush.support.agent.entrance.test.command.AgentCommandIdentifierEnum;
import indi.ly.crush.support.agent.entrance.test.command.AgentCommandComposition;
import indi.ly.crush.support.agent.entrance.test.command.IAgentCommand;
import indi.ly.crush.util.base.BaseStringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <h2><span style="color: red;">方法部分 Agent 指令</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
public class MethodAgentCommand
		implements IAgentCommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodAgentCommand.class);
	private static final String DOLLAR_SIGN_REGULAR_EXPRESSION = "\\$";
	private static final String ENGLISH_COMMA = ",";
	private static final String METHOD_IDENTIFIER = DOLLAR_SIGN_REGULAR_EXPRESSION;
	private static final String METHOD_SEPARATOR = ENGLISH_COMMA;
	private static final Integer CLASS_NAME_INDEX = 0;
	private static final Integer CLASS_METHOD_NAMES_INDEX = 1;
	private static final Integer METHOD_AGENT_COMMAND_SEGMENTATION_SUCCESS_FLAG = 2;
	private String className;
	private Set<String> classMethodNames;
	
	private MethodAgentCommand(String className, Set<String> classMethodNames) {
		this.className = className;
		this.classMethodNames = classMethodNames;
		this.printMessage();
	}
	
	public static Supplier<Optional<IAgentCommand>> createMethodAgentCommand(String agentCommandParameter) {
		var methodAgentCommandIdentifierValue = AgentCommandIdentifierEnum.METHOD_AGENT_COMMAND_IDENTIFIER.getValue();
		var l = methodAgentCommandIdentifierValue.length();
		var classMethod = agentCommandParameter.substring(l);
		
		if (BaseStringUtil.notStartsWith(agentCommandParameter, methodAgentCommandIdentifierValue)) {
			return Optional :: empty;
		}
		
		var classMethodNames = classMethod.split(METHOD_IDENTIFIER);
		if (classMethodNames.length != METHOD_AGENT_COMMAND_SEGMENTATION_SUCCESS_FLAG) {
			return Optional :: empty;
		}
		
		var className = classMethodNames[CLASS_NAME_INDEX];
		var methodNames = classMethodNames[CLASS_METHOD_NAMES_INDEX].split(METHOD_SEPARATOR);
		var classMethodNameSet = new HashSet<>(Arrays.asList(methodNames));
		
		return () -> Optional.of(new MethodAgentCommand(className, classMethodNameSet));
	}
	
	@Override
	public Boolean whetherToExecuteCommand(AgentCommandComposition agentCommandComposition) {
		var className = agentCommandComposition.getClassName();
		var methodName = agentCommandComposition.getMethodName();
		return Objects.equals(this.className, className) && this.classMethodNames.contains(methodName);
	}
	
	@Override
	public void printMessage() {
		var methods = this.classMethodNames
									.stream()
									.collect(Collectors.joining(ENGLISH_COMMA, "", ""));
		LOGGER.debug("{} - {}", this.className, methods);
	}
}
