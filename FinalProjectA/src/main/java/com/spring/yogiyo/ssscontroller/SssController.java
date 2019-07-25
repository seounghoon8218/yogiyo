package com.spring.yogiyo.ssscontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.MyUtil;
import com.spring.yogiyo.sssmodel.MenuVO;
import com.spring.yogiyo.sssservice.InterSssService;

@Controller
public class SssController {

	@Autowired
	private InterSssService service;
	
	// 메뉴등록	
	@RequestMapping(value="/registerMenu.yo", method = {RequestMethod.GET})
	public ModelAndView registerMenu( MenuVO menuvo, ModelAndView mv) {
		
		// int menuReg = service.menuRegister(menuvo); 
		// mv.addObject("menuReg", menuReg);		
		mv.setViewName("register/menuRegister.tiles3");		
		
		return mv;
	}
	
	// 메뉴등록 완료요청
	@RequestMapping(value="/registerMenuEnd.yo", method = {RequestMethod.GET})
	public String registerMenuEnd( MenuVO menuvo, HttpServletRequest request) {
		
		String menucomment = menuvo.getMenucomment();
		
		menucomment = MyUtil.replaceParameter(menucomment);
		menucomment = menuvo.getMenucomment().replaceAll("\r\n", "<br>");
		menuvo.setMenucomment(menucomment);
		
		int menuReg = service.menuRegister(menuvo); 
			
		request.setAttribute("menuReg",menuReg);		
		
		return "register/menuRegisterEnd.tiles3";
	}
	
}
