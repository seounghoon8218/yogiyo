package com.spring.yogiyo.wwwaop;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.spring.common.MyUtil;

/* === 주업무(<예:글쓰기, 글수정, 댓글쓰기 등등>를 실행하기 앞서 
이러한 주업무들은 먼저 로그인을 해야만 사용가능한 작업이므로  
주업무에 대한 보조업무 객체로 로그인 여부를 체크하는 관심 클래스(객체)를 생성하여 
포인트컷과 더불어 어드바이스를 생성하여 동작하도록 만들겠다.       
*/

//===== #52. 공통관심사 클래스(Aspect 클래스)생성하기 =====
@Aspect
/* 공통관심사 클래스 객체로 등록한다.
- 기존의 XML <aop:aspect> 역할을 한다. */
@Component
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
public class LoginCheck {
	
	// == Pointcut 을 생성한다. ==
	@Pointcut("execution(public * com.spring..*Controller.requireLogin_*(..))") // ..(모든것)
	public void requireLogin() {}
	
	// == Before Advice 를 구현한다. ==
	@Before("requireLogin()") //pointcut
	public void before(JoinPoint joinPoint) {
		// JOinPoint joinPoint  는 포인트컷 되어진 주업무의 메소드이다.
		
		// 로그인 유무를 확인하기 위해서 request를 통해 session을 얻어온다.
		HttpServletRequest request = (HttpServletRequest)joinPoint.getArgs()[0];
		HttpServletResponse response = (HttpServletResponse)joinPoint.getArgs()[1];
		HttpSession session =  request.getSession();
		
		if( session.getAttribute("loginuser") == null ) {
			try {
				String msg = "먼저 로그인 하세요";
				String loc = request.getContextPath()+"/login.yo";
				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);
				
				// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 하기<<< //
				// === 현재 페이지의 주소(URL) 알아내기 ===
				String url = MyUtil.getCurrentURL(request);
				
			//	System.out.println("확인용"+url);
				
				session.setAttribute("gobackURL", url); // 세션에 url 정보를 저장한다.
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
