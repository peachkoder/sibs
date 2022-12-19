package com.peachkoder.config.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectConfig {

	private static final String POINTCUT = "within(com.peachkoder.service..*)"
			+ "|| within(com.peachkoder.model.repository..*) " + "|| within(com.peachkoder.controller..*)"
			+ "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)";

	private static final Logger log = LogManager.getLogger("com.peachkoder.logger");

	@Pointcut(POINTCUT)
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object invoke(ProceedingJoinPoint jp) throws Throwable {
		Object obj = jp.proceed();
		if (obj == null) {
			log.warn("Executed: {}.", constructLogMsg(jp));
		} else {
			log.warn("Executed: {}. Result: {}", constructLogMsg(jp), obj);
		}
		return obj;
	}

	@AfterThrowing(pointcut = "pointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception during: {} with ex: {}", constructLogMsg(joinPoint), e.toString());

	}

	private Map<String, Object> getParameters(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();

		HashMap<String, Object> map = new HashMap<>();

		String[] parameterNames = signature.getParameterNames();

		for (int i = 0; i < parameterNames.length; i++) {
			map.put(parameterNames[i], joinPoint.getArgs()[i]);
		}

		return map;
	}

	private String constructLogMsg(JoinPoint jp) {
		var args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
//		Method method = ((MethodSignature) jp.getSignature()).getMethod();
		var sb = new StringBuilder("@");
		sb.append(jp);
		sb.append("::");
//		sb.append(method.getName());
//		sb.append(":");
		sb.append(args);
		return sb.toString();
	}

}
