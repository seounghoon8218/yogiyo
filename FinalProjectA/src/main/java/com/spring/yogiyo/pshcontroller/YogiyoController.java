package com.spring.yogiyo.pshcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.yogiyo.pshservice.InterYogiyoService;

// #30
@Controller
public class YogiyoController {

	// === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired
	private InterYogiyoService service;

	// === #36. 메인 페이지 요청 ====
	@RequestMapping(value="/index.yo", method= {RequestMethod.GET})
	public ModelAndView index(ModelAndView mv) {
		
		mv.setViewName("main/index.tiles1");
		
		return mv;
		// /Yogiyo/src/main/webapp/WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
	}	  
	
	// 회원가입 폼 보여주기
	@RequestMapping(value="/register.yo" , method= {RequestMethod.GET})
	public ModelAndView register(ModelAndView mv) {
		mv.setViewName("register/register.tiles3");
		return mv;
	}
	
	@RequestMapping(value="/test.yo" , method= {RequestMethod.GET})
	public ModelAndView test(ModelAndView mv) {
		mv.setViewName("test.tiles2");
		return mv;
	}
}
