package com.spring.yogiyo.wwwcontroller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.SHA256;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.wwwservice.InterWwwService;

// === #30. 컨트롤러 선언 ===
@Controller
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
	그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
public class WwwController {
	
	//=== #34. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private InterWwwService service;
	
	
	// === #40. 로그인 폼 페이지 요청 ===
	@RequestMapping(value="/login.yo", method= {RequestMethod.GET})
	public ModelAndView login(ModelAndView mv) {
		
		mv.setViewName("login/loginform.tiles1");
		return mv;
	}
	
	// === #41. 로그인 처리하기 ===
	@RequestMapping(value="/loginEnd.yo", method= {RequestMethod.POST})
	public ModelAndView loginEnd(HttpServletRequest request, ModelAndView mv) {
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("email", email);
		paraMap.put("pwd", pwd);
		
		MemberVO loginuser = service.getLoginMember(paraMap);
		//////////////////////////////////////////////////////////
		
		HttpSession session = request.getSession();
		
		if(loginuser == null) {
			String msg = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";
			
			mv.addObject("msg", msg);
			mv.addObject("loc", loc);
			
			mv.setViewName("msg");
			// /Board/src/main/webapp/WEB-INF/views/msg.jsp 파일을 생성한다.
		}
		else {
			if(loginuser.isIdleStatus() == true) { // boolean값은 is로 씀
				// 로그인을 한지 1년 지나서 휴면상태로 빠진 경우
				String msg = "접속한지 1년이 지나 휴면상태로 빠졌습니다. ";
				
			// 로그인을 한지 1년이 지났으면 로그인을 하지 못하도록 막아버리게 뒤로만 가는 것 // 
			//	String loc = "javascript:history.back()";
				
				//// 로그인을 한지 1년이 지났지만 정상적으로 로그인 처리를 해주는 것 ///
				String loc = "/yogiyo/index.yo";
				session.setAttribute("loginuser", loginuser);
				
				mv.addObject("msg", msg);
				mv.addObject("loc", loc);
				
				mv.setViewName("msg"); 
			}
			else {
				
				if(loginuser.isRequirePwdChange() == true) {
					// 암호를 최근 3개월 동안 변경하지 않은 경우
					session.setAttribute("loginuser", loginuser);
					
					String msg = "암호를 3개월동안 변경하지 않았습니다.";
					String loc = request.getContextPath()+"/myinfo.yo";
					
					mv.addObject("msg", msg);
					mv.addObject("loc", loc);
					
					mv.setViewName("msg");
				}
				else {
					// 아무런 이상없이 로그인 하는 경우
					session.setAttribute("loginuser", loginuser);
					
					mv.setViewName("login/loginEnd.tiles1");
					// /Board/src/main/webapp/WEB-INF/views/tiles1/login/loginEnd.jsp 파일을 생성한다.
				}
			}
		}
		
		return mv;
		
	}
	@RequestMapping(value="/myinfo.yo", method= {RequestMethod.GET})
	public String myinfo() {
		return "login/myinfo.tiles1";
		// /Board/src/main/webapp/WEB-INF/views/tiles1/login/myinfo.jsp 파일을 생성한다.
	}
	
	// === #50. 로그아웃 처리하기 ===
	@RequestMapping(value="/logout.yo", method= {RequestMethod.GET})
	public ModelAndView logout(ModelAndView mv, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		String msg = "로그아웃 되었습니다.";
		String loc = request.getContextPath()+"/index.yo";
		
		mv.addObject("msg", msg);
		mv.addObject("loc", loc);
		
		mv.setViewName("msg");
		return mv;
	}
	
	
}
