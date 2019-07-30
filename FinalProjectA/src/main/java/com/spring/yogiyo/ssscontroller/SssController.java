package com.spring.yogiyo.ssscontroller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.yogiyo.sssmodel.MenuVO;
import com.spring.yogiyo.sssservice.InterSssService;

@Controller
public class SssController {

	@Autowired
	private InterSssService service;

	@Autowired
	private FileManager fileManager;

	// 메뉴등록
	@RequestMapping(value = "/registerMenu.yo", method = { RequestMethod.GET })
	public ModelAndView registerMenu(MenuVO menuvo, ModelAndView mv) {

		mv.setViewName("menu/registerMenu.tiles3");

		return mv;
	}

	// 메뉴등록 완료요청
	@RequestMapping(value = "/registerMenuEnd.yo", method = { RequestMethod.POST })
	public String registerMenuEnd(MenuVO menuvo, MultipartHttpServletRequest mrequest) {

		// 스크립트공격
		String menucomment = menuvo.getMenucomment();
		menucomment = MyUtil.replaceParameter(menucomment);
		menucomment = menuvo.getMenucomment().replaceAll("\r\n", "<br>");
		menuvo.setMenucomment(menucomment);
		
		
		
		// 사진저장
		MultipartFile attach = menuvo.getAttach();
		if (!attach.isEmpty()) {
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "files";
			String newFileName = "";
			byte[] bytes = null;
			long fileSize = 0;

			try {
				bytes = attach.getBytes();
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				fileSize = attach.getSize();
				menuvo.setFileName(newFileName);
				menuvo.setOrgFilename(attach.getOriginalFilename());
				menuvo.setFileSize(String.valueOf(fileSize));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // end of if
		
		// 메뉴등록
		int n = 0;
		
		try {
			n = service.menuRegister(menuvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mrequest.setAttribute("n", n);
		
		return "menu/registerMenuEnd.tiles3";
	}
	
	
	
	
}
