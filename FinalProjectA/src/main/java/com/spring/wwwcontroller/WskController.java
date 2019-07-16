package com.spring.wwwcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// #30
@Controller
public class WskController {

	@RequestMapping(value="//.yo", method = {RequestMethod.GET})
	public ModelAndView authmember(ModelAndView mv) {
		
		mv.setViewName("main/index.tiles1");
		
		return mv;
	}
	

}
