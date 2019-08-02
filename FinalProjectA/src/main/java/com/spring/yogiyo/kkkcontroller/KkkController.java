package com.spring.yogiyo.kkkcontroller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.common.FileManager;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.kkkservice.InterKkkService;
import com.spring.yogiyo.ooomodel.oooReviewVO;
import com.spring.yogiyo.ooomodel.oooVO;

@Controller
public class KkkController {

	@Autowired
	   private FileManager fileManager;
	
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
		String cnt = request.getParameter("cnt");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		Gson gson = new Gson();
		
		// 매장들 가져오기
		HashMap<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("shopcategorycode", shopcategorycode);
		paraMap.put("cnt", cnt);
		paraMap.put("latitude", latitude);
		paraMap.put("longitude", longitude);
		
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
		
		// 이지훈──────────────────────────────────────────────────────────────
	      // 매장정보
	      
	      List<HashMap<String, String>> shopInfo = service.getShopInfo(masterno);
	         
	      
	      request.setAttribute("shopInfo", shopInfo);
	      // 이지훈──────────────────────────────────────────────────────────────   
		
	      ///////////////////////////////////////////////////////////////
	      /* 총 별점 평균 보여주기 */
	      double starpointAvg_double = service.getStarpointAvg(Integer.parseInt(masterno));
	      String starpointAvg = String.valueOf(starpointAvg_double);
	      /* 총 리뷰 갯수 보여주기 */
	      int reviewCount = service.getReviewCount(Integer.parseInt(masterno));
	      
	      
	      
	      List<HashMap<String, String>> reviewList = service.getReviewList(masterno);
	      request.setAttribute("reviewList", reviewList);
	      
	      request.setAttribute("masterno", masterno);
	      request.setAttribute("starpointAvg", starpointAvg);
	      request.setAttribute("reviewCount", reviewCount);
	      
	      
	      
	      /////////////////////////////////////////////////////////////  
	      
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
	
	// 주문표에 추가하기
	@RequestMapping(value="/kkk/orderAdd.yo" , method= {RequestMethod.GET} ,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String orderAdd(HttpServletRequest request, HttpServletResponse response) {
		
		Gson gson = new Gson();
		int n , m ;
		JsonObject jsonObj = new JsonObject();
		
		String menuname = request.getParameter("menuname");
		String menucode = request.getParameter("menucode");
		String masterno = request.getParameter("masterno");
		String menuprice = request.getParameter("menuprice");
		String filename = request.getParameter("filename");
		String menuqty = request.getParameter("menuqty");
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		
		if(loginuser == null) {
			jsonObj.addProperty("n", 3);
			return gson.toJson(jsonObj);
		}
		
		// 타음식점 추가 불가 기능
		String msno = service.getCartMasterno(loginuser.getEmail());
		
		
		if(msno != null) {		
			if(!msno.equals(masterno)) {
				jsonObj.addProperty("n", 4);
				return gson.toJson(jsonObj);
			}
		}
		
		HashMap<String, String> paramap = new HashMap<String,String>();
		paramap.put("menuname", menuname);
		paramap.put("menucode", menucode);
		paramap.put("masterno", masterno);
		paramap.put("menuprice", menuprice);
		paramap.put("filename", filename);
		paramap.put("menuqty", menuqty);
		paramap.put("email", loginuser.getEmail());			
		
		
		// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
		m = service.cartSelect(paramap);
		
		if( m == 0) { // 장바구니에 존재하지 않을때
		
			// 장바구니에 insert 해주기
			n = service.cartInsert(paramap);
		
			
			jsonObj.addProperty("n", n);
					
		}else { // 장바구니에 이미 존재할때
			jsonObj.addProperty("n", 2);
		}
		
		return gson.toJson(jsonObj);
	}
	
	
	// 주문표 보여주기
	@RequestMapping(value="/kkk/orderMenuList.yo" , method= {RequestMethod.GET})
	public String login_orderMenuList(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		
		String orderno = request.getParameter("orderno");
		
		if(orderno != null || "".equals(orderno)) {
			// 주문메뉴 삭제
			int n = service.delMenu(orderno);			
		}
		
		// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
		List<HashMap<String, String>> cartList = service.selectCartList(loginuser.getEmail());
		
		request.setAttribute("cartList", cartList);
		
		
		return "menuList/orderMenuList.tiles3";
	}
	
	
	// 리뷰 보여주기
   @RequestMapping(value = "/kkk/showReview.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
   @ResponseBody
   public String showReview(HttpServletRequest request, oooReviewVO reviewvo) {

      //////////////////////////////////////////////////
   
      String masterno = request.getParameter("masterno");
   
      
      Gson gson = new Gson();
      JsonArray jsonArr = new JsonArray();
   
      List<HashMap<String, String>> reviewList = service.getReviewList(masterno);
         
               for (HashMap<String, String> reviewMap : reviewList) {
                  JsonObject jsonObj = new JsonObject();
   
                  jsonObj.addProperty("email", reviewMap.get("email"));
                  jsonObj.addProperty("image", reviewMap.get("image"));
                  jsonObj.addProperty("menuname", reviewMap.get("menuname"));
                  jsonObj.addProperty("reviewRegDate", reviewMap.get("reviewRegDate"));
                  jsonObj.addProperty("comments", reviewMap.get("comments"));
                  jsonObj.addProperty("starpoint", reviewMap.get("starpoint"));
   
                  jsonArr.add(jsonObj);
               }
            request.setAttribute("reviewList", reviewList);
         
         
      
      return gson.toJson(jsonArr);
      ///////////////////////////////////////////////////
   }

	   // 리뷰등록하기
	   @RequestMapping(value = "/kkk/addReview.yo", method = { RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	   public String addReview(HttpServletRequest request, oooReviewVO reviewvo, MultipartHttpServletRequest mrequest ) {
	      
	      HttpSession session1 = request.getSession();
	      MemberVO loginuser = (MemberVO) session1.getAttribute("loginuser");
	   
	      if(loginuser==null) {
	         String msg = "로그인 먼저 해주세요";
	         String loc = "javascript:history.back()";
	         
	         request.setAttribute("msg", msg);
	         request.setAttribute("loc", loc);
	         
	         return "msg";
	      }
	      
	      
	      MultipartFile attach = reviewvo.getAttach();
	      if (!attach.isEmpty()) {
	         HttpSession session = mrequest.getSession();
	         String root = session.getServletContext().getRealPath("/");
	         String path = root + "resources" + File.separator+"images";
	         String newFileName = "";
	         byte[] bytes = null;
	         long fileSize = 0;
	         try {
	            
	            bytes= attach.getBytes();
	   
	            newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
	            
	            reviewvo.setFileName(newFileName);
	            reviewvo.setOrgFilename(attach.getOriginalFilename());
	            
	            fileSize = attach.getSize();
	            reviewvo.setFileSize(String.valueOf(fileSize));
	         } catch (Exception e) {
	            e.printStackTrace();
	         }

	      }
	      else {
	         reviewvo.setFileName("");
	         reviewvo.setFileSize("");
	         reviewvo.setOrgFilename("");
	      }
	      
	      String masterno = request.getParameter("masterno");
	      String starpoint = request.getParameter("starpoint");
	      String comments = request.getParameter("comments");
	      String image = reviewvo.getOrgFilename();
	      String menuname = request.getParameter("menuname");
	      
	      System.out.println(masterno+","+starpoint+","+comments+","+menuname+","+image);
	      
	      
	      String loginuserEmail = loginuser.getEmail();
	      
	      
	      HashMap<String, String> paramap = new HashMap<String, String>();
	      paramap.put("starpoint", starpoint);
	      paramap.put("image", image);
	      paramap.put("comments", comments);
	      paramap.put("fk_masterno", masterno);      
	      paramap.put("email", loginuserEmail);
	      paramap.put("menuname", menuname);
	      paramap.put("filename", reviewvo.getFileName());
	      paramap.put("orgfilename", reviewvo.getOrgFilename());
	      paramap.put("filesize", reviewvo.getFileSize());
	      
	      System.out.println("===============================checkemail 전 ===============================");

	      paramap.put("masterno", masterno);
	      paramap.put("loginuserEmail", loginuserEmail);
	      List<HashMap<String, String>> checkEmail = service.checkOrderStatus(paramap);
	      
	      
	      if(checkEmail != null) {
	         int n = service.addReview(paramap);
	         
	         if(n==1) {
	            
	            
	            String msg = "리뷰 등록 성공!";
	            String loc = "javascript:history.go(-1)";
	            request.setAttribute("msg", msg);
	            request.setAttribute("loc", loc);
	         }
	         else {
	            String msg = "리뷰 등록 실패ㅠ";
	            String loc = "javascript:history.back()";
	            request.setAttribute("msg", msg);
	            request.setAttribute("loc", loc);
	            
	         }
	      }
	      else {
	         String msg = "주문하신 상품만 리뷰를 남기실 수 있습니다!";
	         String loc = "javascript:history.back()";
	         request.setAttribute("msg", msg);
	         request.setAttribute("loc", loc);
	      }
	         return "msg";
	   }
	   
	
}
