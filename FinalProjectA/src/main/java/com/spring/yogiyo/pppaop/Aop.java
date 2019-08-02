package com.spring.yogiyo.pppaop;

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
import com.spring.member.model.MemberVO;

@Aspect
@Component // Aspect 는 Service나 Controller 와 다르게 Component를 꼭 같이 써줘야 bean으로 올라감
public class Aop {
	
		// Pointcut 을 생성한다.
		@Pointcut("execution( public * com.spring..*Controller.login_*(..) )")
		public void requireLogin() {}
		
		
		@Before("requireLogin()")
		public void before(JoinPoint joinpoint) {
			// JoinPoint joinpoint 는 포인트컷 되어진 주업무의 메소드이다.
			
			// 로그인 유무를 확인하기 위해서 request를 통해 session을 얻어오겠다.
			HttpServletRequest request = (HttpServletRequest)joinpoint.getArgs()[0];
			HttpServletResponse response = (HttpServletResponse)joinpoint.getArgs()[1]; 
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			
			if(loginuser == null) {
				
				String msg = "먼저 로그인 하세요.";
				String loc = request.getContextPath()+"/login.yo";
				
				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);

				// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업하기 <<< //
				// === 현재 페이지의 주소(URL) 알아내기 ===
				String url = MyUtil.getCurrentURL(request);
//				System.out.println("\n 주소 ==> "+url); // 주소 ==> add.action?null
				session.setAttribute("gobackURL", url);	// 세션에 url 정보를 저장한다.
				
				try {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
					dispatcher.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} // end of if(loginuser == null)--------
			
		} // end of @Before("requireLogin()")----------------------
	
}
