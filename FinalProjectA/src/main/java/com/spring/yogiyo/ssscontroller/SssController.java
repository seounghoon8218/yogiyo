package com.spring.yogiyo.ssscontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.yogiyo.sssservice.InterSssService;

@Controller
public class SssController {

	@Autowired
	private InterSssService service;
	
	@RequestMapping(value="/registerMenu.yo", method = {RequestMethod.GET})
	public ModelAndView registerMenu(ModelAndView mv) {
		
		return mv;
	}
	
}
