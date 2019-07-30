package com.spring.yogiyo.sssaop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// * 공통관심사 클래스 객체로 등록한다. - 기존의 XML <aop:aspect> 역할을 한다.
@Aspect
@Component
public class LoginCheckAOP {

	// pointcut 을 생성한다.
	@Pointcut("execution(public * com.spring..*Controller.requireLogin_*(..))")
	public void requireLogin() {}
	
	
	
}
