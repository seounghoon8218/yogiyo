package com.spring.yogiyo.kkkcontroller;

import java.util.ArrayList;
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
		public ModelAndView test(ModelAndView mv) {
			mv.setViewName("restaurant/categryList.tiles2");
			return mv;
		}
	
	@RequestMapping(value="/getShopList.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getShopList(HttpServletRequest request) {
		
		Gson gson = new Gson();
		
		// 매장 가져오기
		List<oooVO> shopList = service.getShopList();
		
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
		
		return "restaurant/restaurantView.tiles2";
	}
	
	
}
