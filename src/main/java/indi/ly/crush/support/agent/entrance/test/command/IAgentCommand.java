package indi.ly.crush.support.agent.entrance.test.command;

/**
 * <h2><span style="color: red;">agent 指令</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface IAgentCommand {
	/**
	 * <p>
	 *     是否执行命令.
	 * </p>
	 *
	 * @param agentCommandComposition agent 指令构成对象.
	 * @return {@code true}: 去执行命令; {@code false}: 不去执行命令.
	 */
	default Boolean whetherToExecuteCommand(AgentCommandComposition agentCommandComposition){return false;}
	
	/**
	 * <p>
	 *     打印消息.
	 * </p>
	 */
	default void printMessage(){}
}
