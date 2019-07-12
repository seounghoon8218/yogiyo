package com.spring.yogiyo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.yogiyo.service.InterYogiyoService;

// #30
@Controller
public class YogiyoController {

	// === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired
	private InterYogiyoService serviec;

	// === #36. 메인 페이지 요청 ====
	@RequestMapping(value="index.yo", method= {RequestMethod.GET})
	public ModelAndView index(ModelAndView mv) {
		
		mv.setViewName("main/index.tiles1");
		
		return mv;
		// /Yogiyo/src/main/webapp/WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
	}

}
