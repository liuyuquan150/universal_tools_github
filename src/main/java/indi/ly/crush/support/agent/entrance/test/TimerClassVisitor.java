package indi.ly.crush.support.agent.entrance.test;

import indi.ly.crush.enumeration.SymbolEnum;
import indi.ly.crush.support.agent.entrance.test.command.AgentCommandComposition;
import indi.ly.crush.support.agent.entrance.test.command.IAgentCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2><span style="color: red;">计时器访问者</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class TimerClassVisitor
		extends ClassVisitor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimerClassVisitor.class);
	private static final String INIT_METHOD_NAME = "<init>";
	private static final String CLINIT_METHOD_NAME = "<clinit>";
	
	/**
	 * <p>
	 *     类内部名称.
	 * </p>
	 */
	private String classInternalName;
	/**
	 * <p>
	 *     类上注解的描述符.
	 * </p>
	 */
	private String classAnnotationDescription;
	private IAgentCommand agentCommand;
	
	public TimerClassVisitor(ClassVisitor classVisitor, String classInternalName, IAgentCommand agentCommand) {
		this(classVisitor,  classInternalName, null, agentCommand);
	}
	
	public TimerClassVisitor(ClassVisitor classVisitor, String classInternalName, String classAnnotationDescription, IAgentCommand agentCommand) {
		super(Opcodes.ASM9, classVisitor);
		this.classInternalName = classInternalName;
		this.classAnnotationDescription = classAnnotationDescription;
		this.agentCommand = agentCommand;
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		this.classAnnotationDescription = descriptor;
		return super.visitAnnotation(descriptor, visible);
	}
	
	@Override
	public MethodVisitor visitMethod(
			int methodAccess, String methodName, String methodDescriptor, String methodSignature, String[] methodExceptionNames
	) {
		var methodVisitor = cv.visitMethod(methodAccess, methodName, methodDescriptor, methodSignature, methodExceptionNames);
		if (methodVisitor == null || INIT_METHOD_NAME.equals(methodName) || CLINIT_METHOD_NAME.equals(methodName) || this.classInternalName == null) {
			return methodVisitor;
		}
		
		var className = this.classInternalName.replace(SymbolEnum.POSITIVE_SLASH_SYMBOL.getValue(), SymbolEnum.DOT_SYMBOL.getValue());
		var agentCommandComposition = new AgentCommandComposition(className, this.classAnnotationDescription, methodName, "");
		if (this.agentCommand.whetherToExecuteCommand(agentCommandComposition)) {
			final var stackConstantValue = this.classInternalName + methodName + methodDescriptor;
			LOGGER.debug("要加载到堆栈上的常量: {}", stackConstantValue);
			return new TimerMethodAdviceAdapter(stackConstantValue, methodVisitor, this.classInternalName, methodAccess,methodName, methodDescriptor);
		} else {
			return methodVisitor;
		}
	}
}
