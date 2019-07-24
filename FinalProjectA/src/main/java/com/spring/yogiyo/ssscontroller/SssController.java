package com.spring.yogiyo.ssscontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.yogiyo.sssmodel.MenuVO;
import com.spring.yogiyo.sssservice.InterSssService;

@Controller
public class SssController {

	@Autowired
	private InterSssService service;
	
	// 메뉴등록	
	@RequestMapping(value="/registerMenu.yo", method = {RequestMethod.GET})
	public ModelAndView registerMenu(ModelAndView mv, MenuVO menuvo) {
		
		int menuReg = service.menuRegister(menuvo); 

		mv.addObject("menuReg", menuReg);
		
		mv.setViewName("register/menuRegister.tiles3");
		
		return mv;
	}
	
}
