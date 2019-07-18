package com.spring.yogiyo.kkkcontroller;

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

@Controller
public class KkkController {

	@Autowired
	private InterKkkService service;
		
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
	
}
