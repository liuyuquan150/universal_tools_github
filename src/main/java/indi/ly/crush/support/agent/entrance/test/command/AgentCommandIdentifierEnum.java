package indi.ly.crush.support.agent.entrance.test.command;

/**
 * <h2><span style="color: red;">agent 指令标识符枚举</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public enum AgentCommandIdentifierEnum {
	/**
	 * <p>
	 *     类部分 Agent 指令的标识符.
	 * </p>
	 */
	CLASS_AGENT_COMMAND_IDENTIFIER("@C-"),
	/**
	 * <p>
	 *     方法部分 Agent 指令的标识符.
	 * </p>
	 */
	METHOD_AGENT_COMMAND_IDENTIFIER("@M-"),
	/**
	 * <p>
	 *     包部分 Agent 指令的标识符.
	 * </p>
	 */
	PACKAGE_AGENT_COMMAND_IDENTIFIER("@P-");
	
	/**
	 * <p>
	 *     Agent 指令标识符的具体值.
	 * </p>
	 */
	private final String value;
	
	AgentCommandIdentifierEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
