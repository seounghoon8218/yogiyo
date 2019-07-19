package com.spring.yogiyo.ooocontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.MyUtil;
import com.spring.yogiyo.ooomodel.oooVO;
import com.spring.yogiyo.oooservice.Interoooservice;

@Controller
public class oooController {

	// === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
		@Autowired
		private Interoooservice service;
		
	
	// === #36. 매장 등록 페이지 요청 ====
	@RequestMapping(value="/shopregister.yo", method= {RequestMethod.GET})
	public ModelAndView shopregister(ModelAndView mv) {
		
		mv.setViewName("register/shopregister.tiles3");
		
		return mv;
	} // end shopregister
	
	
	@RequestMapping(value="/shopregisterEnd.yo", method= {RequestMethod.POST})
	public ModelAndView shopregisterEnd(oooVO ovo, ModelAndView mv) {
		
		String wonsanji = ovo.getWonsanji();
		
		wonsanji = MyUtil.replaceParameter(wonsanji);
		wonsanji = ovo.getWonsanji().replaceAll("\r\n", "<br>");		
		ovo.setWonsanji(wonsanji);
		
		int n = service.addshop(ovo);
		System.out.println("n===>" + n);
		
		
		mv.addObject("n", n);
		mv.setViewName("register/shopregisterEnd.tiles3");
		
		return mv;
	} // end shopregisterEnd
	
}
