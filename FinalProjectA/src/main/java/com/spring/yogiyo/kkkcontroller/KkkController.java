package com.spring.yogiyo.kkkcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.yogiyo.kkkservice.InterKkkService;
import com.spring.yogiyo.ooomodel.oooVO;

@Controller
public class KkkController {

	@Autowired
	private InterKkkService service;
		/*
	@RequestMapping(value="/gps.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String gps(HttpServletRequest request) {
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		Gson gson = new Gson();
		
		JsonObject jsonObj = new JsonObject();
				
		jsonObj.addProperty("latitude", latitude);
		jsonObj.addProperty("longitude", longitude);
		
		
		return gson.toJson(jsonObj);
		
	}
	*/
	
	// 음식점들 보여주는 화면
		@RequestMapping(value="/categryList.yo" , method= {RequestMethod.GET})
		public ModelAndView test(ModelAndView mv , HttpServletRequest request) {
			String shopcategorycode = request.getParameter("shopcategorycode");
			
			mv.addObject("shopcategorycode",shopcategorycode);
			
			mv.setViewName("restaurant/categryList.tiles2");
			return mv;
		}
	
	@RequestMapping(value="/getShopList.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getShopList(HttpServletRequest request) {

		String shopcategorycode = request.getParameter("shopcategorycode");
		
		Gson gson = new Gson();
		
		// 매장들 가져오기
		HashMap<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("shopcategorycode", shopcategorycode);
		
		List<oooVO> shopList = service.getShopList(paraMap);
		
		
		JsonArray jsonArr = new JsonArray();
		
		for(oooVO shopvo : shopList) {
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("masterno", shopvo.getMasterno());
			jsonObj.addProperty("shopname", shopvo.getShopname());
			jsonObj.addProperty("shopcategorycode", shopvo.getShopcategorycode());
			jsonObj.addProperty("addr", shopvo.getAddr());
			jsonObj.addProperty("addr2", shopvo.getAddr2());
			jsonObj.addProperty("shoptel", shopvo.getShoptel());
			jsonObj.addProperty("shopimage", shopvo.getShopimage());
			jsonObj.addProperty("shoptime", shopvo.getShoptime());
			jsonObj.addProperty("minprice", shopvo.getMinprice());
			jsonObj.addProperty("paymethod", shopvo.getPaymethod());
			jsonObj.addProperty("sanghoname", shopvo.getSanghoname());
			jsonObj.addProperty("wonsanji", shopvo.getWonsanji());
			
			jsonArr.add(jsonObj);
			
		}
				
		return gson.toJson(jsonArr);
		
	}
	
	@RequestMapping(value="/restaurantView.yo" , method= {RequestMethod.GET})
	public String restaurantView(HttpServletRequest request) {
		
		String masterno = request.getParameter("masterno");
		
		oooVO shop = service.getShopView(masterno); // 매장하나정보 가지고오기
		
		request.setAttribute("shop", shop);
		request.setAttribute("masterno", masterno);
		return "restaurant/restaurantView.tiles2";
	}
	
	@RequestMapping(value="/kkk/menucategoryList.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String menucategoryList(HttpServletRequest request) {

		Gson gson = new Gson();
		
		// 메뉴카테고리 가져오기
		List<HashMap<String, String>> menucategoryList = service.getMenucategoryList();
		
		
		JsonArray jsonArr = new JsonArray();
		
		for(HashMap<String, String> map : menucategoryList) {
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("menuspeccode",map.get("menuspeccode"));
			jsonObj.addProperty("menuspecname",map.get("menuspecname"));
			
			
			jsonArr.add(jsonObj);
			
		}
				
		return gson.toJson(jsonArr);
	}

	@RequestMapping(value="/kkk/menuList.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String menuList(HttpServletRequest request) {
		
		Gson gson = new Gson();
		String masterno = request.getParameter("masterno");
		String code = request.getParameter("code");
		
		HashMap<String, String> paramap = new HashMap<String,String>();
		paramap.put("masterno", masterno);
		paramap.put("code", code);
		
		// 리스트별 메뉴 가져오기
		List<HashMap<String, String>> menuList = service.getMenuList(paramap);
		
		
		JsonArray jsonArr = new JsonArray();
		
		for(HashMap<String, String> map : menuList) {
			JsonObject jsonObj = new JsonObject();
			
			jsonObj.addProperty("menucode",map.get("menucode"));
			jsonObj.addProperty("masterno",map.get("masterno"));
			jsonObj.addProperty("menuname",map.get("menuname"));
			jsonObj.addProperty("menuprice",map.get("menuprice"));
			jsonObj.addProperty("filename",map.get("filename"));
			jsonObj.addProperty("pop_menuspeccode",map.get("pop_menuspeccode"));
			
			jsonArr.add(jsonObj);
			
		}
		
		return gson.toJson(jsonArr);
	}
	
}
