package indi.ly.crush.support.agent.entrance.test;

import indi.ly.crush.support.agent.entrance.test.command.IAgentCommand;
import lombok.Data;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * <h2><span style="color: red;">方法执行计时转换器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Data
public class MethodExecuteTimingTransformer
		implements ClassFileTransformer {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodExecuteTimingTransformer.class);
	private IAgentCommand agentCommand;
	
	public MethodExecuteTimingTransformer(IAgentCommand agentCommand) {
		super();
		this.agentCommand = agentCommand;
	}
	
	@Override
	public byte[] transform(
			ClassLoader loader, String classInternalName, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer
	) {
		var classReader = new ClassReader(classfileBuffer);
		var classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
		classReader.accept(new TimerClassVisitor(classWriter, classInternalName, this.agentCommand), ClassReader.EXPAND_FRAMES);
		return classWriter.toByteArray();
	}
}
