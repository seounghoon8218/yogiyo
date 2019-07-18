package com.spring.yogiyo.pppaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component // Aspect 는 Service나 Controller 와 다르게 Component를 꼭 같이 써줘야 bean으로 올라감
public class Aop {
	/*
	   XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면
	     해당 클래스(지금은 Cross)는 bean으로 자동 등록된다.
	     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명(지금은 Cross)이 된다.
	     지금은 bean의 이름은 cross 이 된다.
	
	ex)
	
	@Pointcut("execution(* com.test.aop.AOPController.member*(..))")
	public void member() {}
	
	@Before("member()")
	public void memberbefore(JoinPoint joinPoint) {
	
	 */
	
	
}
