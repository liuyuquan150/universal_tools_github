package indi.ly.crush.support.agent.entrance.test;

import indi.ly.crush.enumeration.SymbolEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2><span style="color: red;">方法计时器</span></h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public class MethodTimer {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodTimer.class);
	private static final ThreadLocal<Map<String, Long>> TIMER = ThreadLocal.withInitial(HashMap :: new);
	private static final String METHOD_START_TIME_MARKER = "Start-";
	private static final String METHOD_END_TIME_MARKER = "End-";
	
	private MethodTimer() {}
	
	public static void startTimer(String stackConstantValue) {
		TIMER.get().put(METHOD_START_TIME_MARKER + stackConstantValue, System.currentTimeMillis());
	}
	
	public static void endTimer(String stackConstantValue) {
		TIMER.get().put(METHOD_END_TIME_MARKER + stackConstantValue, System.currentTimeMillis());
	}
	
	public static void calculationMethodTimeConsuming(String classInternalName, String methodName, String methodDescriptor) {
		var key = String.join("", classInternalName, methodName, methodDescriptor);
		var timerHashMap = TIMER.get();
		var methodExecutionStartTime = timerHashMap.get(METHOD_START_TIME_MARKER + key);
		var methodExecutionEndTime = timerHashMap.get(METHOD_END_TIME_MARKER + key);
		var methodExecutionTime = methodExecutionEndTime - methodExecutionStartTime;
		var className = classInternalName.replace(SymbolEnum.POSITIVE_SLASH_SYMBOL.getValue(), SymbolEnum.DOT_SYMBOL.getValue());
		LOGGER.debug("{}#{} 方法耗时: {}毫秒", className, methodName, methodExecutionTime);
	}
}
