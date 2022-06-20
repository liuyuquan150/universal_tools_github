package indi.ly.crush.support.agent.entrance.test;

import indi.ly.crush.enumeration.SymbolEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2><span style="color: red;">计时器方法建议适配器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class TimerMethodAdviceAdapter
		extends AdviceAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimerMethodAdviceAdapter.class);
	/**
	 * <p>
	 *     {@link MethodTimer} 的内部名称 —————— indi/ly/crush/support/agent/test/MethodTimer(<em>参见 {@link Type#getInternalName()}</em>)
	 * </p>
	 */
	private static final String METHOD_TIMER_INTERNAL_NAME = MethodTimer.class.getName().replace(SymbolEnum.DOT_SYMBOL.getValue(), SymbolEnum.POSITIVE_SLASH_SYMBOL.getValue());
	/**
	 * <p>
	 *     {@link MethodTimer#startTimer(String)} 方法的名称.
	 * </p>
	 */
	private static final String START_TIMER_METHOD_NAME = "startTimer";
	/**
	 * <p>
	 *     {@link MethodTimer#endTimer(String)} 方法的名称.
	 * </p>
	 */
	private static final String END_TIMER_METHOD_NAME = "endTimer";
	/**
	 * <p>
	 *     {@link MethodTimer#calculationMethodTimeConsuming(String, String, String)} 方法的名称.
	 * </p>
	 */
	private static final String CALCULATION_METHOD_TIME_CONSUMING_METHOD_NAME = "calculationMethodTimeConsuming";
	/**
	 * <p>
	 *     {@link MethodTimer#startTimer(String)} 方法形参描述符.
	 * </p>
	 */
	private static final String START_TIMER_METHOD_PARAMETER_DESCRIPTORS = "(Ljava/lang/String;)V";
	/**
	 * <p>
	 *     {@link MethodTimer#endTimer(String)} 方法形参描述符.
	 * </p>
	 */
	private static final String END_TIMER_METHOD_PARAMETER_DESCRIPTORS = START_TIMER_METHOD_PARAMETER_DESCRIPTORS;
	/**
	 * <p>
	 *     {@link MethodTimer#calculationMethodTimeConsuming(String, String, String)} 方法形参描述符.
	 * </p>
	 */
	private static final String CALCULATION_METHOD_TIME_CONSUMING_METHOD_PARAMETER_DESCRIPTORS = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V";
	/**
	 * <p>
	 *     要加载到堆栈上的常量(<em>参见 {@link MethodVisitor#visitLdcInsn(Object)}</em>).
	 * </p>
	 */
	private String stackConstantValue;
	/**
	 * <p>
	 *     要加载到堆栈上的类内部名称.
	 * </p>
	 */
	private String classInternalName;
	/**
	 * <p>
	 *     要加载到堆栈上的方法名称.
	 * </p>
	 */
	private String methodName;
	/**
	 * <p>
	 *     要加载到堆栈上的方法描述符.
	 * </p>
	 */
	private String methodDescriptor;
	
	public TimerMethodAdviceAdapter(String stackConstantValue, MethodVisitor methodVisitor, String classInternalName, int methodAccess, String methodName, String methodDescriptor) {
		super(Opcodes.ASM9, methodVisitor, methodAccess, methodName, methodDescriptor);
		this.stackConstantValue = stackConstantValue;
		this.classInternalName = classInternalName;
		this.methodName = methodName;
		this.methodDescriptor = methodDescriptor;
	}
	
	@Override
	protected void onMethodEnter() {
		this.visitLdcInsn(this.stackConstantValue);
		this.visitMethodInsn(Opcodes.INVOKESTATIC, METHOD_TIMER_INTERNAL_NAME, START_TIMER_METHOD_NAME, START_TIMER_METHOD_PARAMETER_DESCRIPTORS, false);
	}
	
	@Override
	protected void onMethodExit(int opcode) {
		this.visitLdcInsn(this.stackConstantValue);
		this.visitMethodInsn(Opcodes.INVOKESTATIC, METHOD_TIMER_INTERNAL_NAME, END_TIMER_METHOD_NAME, END_TIMER_METHOD_PARAMETER_DESCRIPTORS, false);
		this.visitLdcInsn(this.classInternalName);
		this.visitLdcInsn(this.methodName);
		this.visitLdcInsn(this.methodDescriptor);
		this.visitMethodInsn(Opcodes.INVOKESTATIC, METHOD_TIMER_INTERNAL_NAME, CALCULATION_METHOD_TIME_CONSUMING_METHOD_NAME, CALCULATION_METHOD_TIME_CONSUMING_METHOD_PARAMETER_DESCRIPTORS, false);
	}
}
