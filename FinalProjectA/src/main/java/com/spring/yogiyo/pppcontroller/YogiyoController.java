package com.spring.yogiyo.pppcontroller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.yogiyo.pppservice.InterYogiyoService;

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
	
	
	
	// tiles2 의 헤더부분 카테고리리스트
	@RequestMapping(value="/categoryListAjax.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String categoryListAjax(HttpServletRequest request) {
		
		Gson gson = new Gson();
		JsonArray jsonArr = new JsonArray();
		// 카테고리 리스트 불러오기
		List<HashMap<String, String>> categoryList = service.getShopCategory();
		
		for(HashMap<String, String> map : categoryList) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("shopcategorycode", map.get("shopcategorycode"));
			jsonObj.addProperty("shopcategoryname", map.get("shopcategoryname"));
			
			jsonArr.add(jsonObj);
		}
		return gson.toJson(jsonArr);
		
	}
	
}

