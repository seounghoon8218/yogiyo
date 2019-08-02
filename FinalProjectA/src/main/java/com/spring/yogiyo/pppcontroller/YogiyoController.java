package com.spring.yogiyo.pppcontroller;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.ooomodel.oooVO;
import com.spring.yogiyo.pppservice.InterYogiyoService;

// #30
@Controller
public class YogiyoController {

	// === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired
	private InterYogiyoService service;

	// === #36. 메인 페이지 요청 ====
	@RequestMapping(value = "/index.yo", method = { RequestMethod.GET })
	public ModelAndView index(ModelAndView mv) {

		mv.setViewName("main/index.tiles1");

		return mv;
		// /Yogiyo/src/main/webapp/WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
	}

	// tiles2 의 헤더부분 카테고리리스트
	@RequestMapping(value = "/categoryListAjax.yo", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String categoryListAjax(HttpServletRequest request) {

		Gson gson = new Gson();
		JsonArray jsonArr = new JsonArray();
		// 카테고리 리스트 불러오기
		List<HashMap<String, String>> categoryList = service.getShopCategory();

		for (HashMap<String, String> map : categoryList) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("shopcategorycode", map.get("shopcategorycode"));
			jsonObj.addProperty("shopcategoryname", map.get("shopcategoryname"));

			jsonArr.add(jsonObj);
		}
		return gson.toJson(jsonArr);

	}

	// 관리자 차트보는페이지
	@RequestMapping(value = "/adminChart.yo", method = { RequestMethod.GET })
	public ModelAndView adminChart(ModelAndView mv) {

		mv.setViewName("admin/chart.tiles3");

		return mv;
	}

	@RequestMapping(value = "/chartTest.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String chartTest(HttpServletRequest request) {

		List<HashMap<String, String>> chartList = service.chartList();

		Gson gson = new Gson();

		JsonArray jsonArr = new JsonArray();
		for (HashMap<String, String> map : chartList) {
			JsonObject jsonObj = new JsonObject();

			jsonObj.addProperty("shopcategoryname", map.get("shopcategoryname"));
			jsonObj.addProperty("cnt", map.get("cnt"));
			jsonObj.addProperty("percnt", map.get("percnt"));

			jsonArr.add(jsonObj);
		}

		return gson.toJson(jsonArr);
	}

	// 내정보 입력
	@RequestMapping(value = "/myinfowrite.yo", method = { RequestMethod.GET })
	public String login_myinfowrite(HttpServletRequest request , HttpServletResponse response) {

		String menuname = request.getParameter("menuname");
		String menucode = request.getParameter("menucode");
		String masterno = request.getParameter("masterno");
		String totalprice = request.getParameter("totalprice");

		request.setAttribute("menuname", menuname);
		request.setAttribute("menucode", menucode);
		request.setAttribute("masterno", masterno);
		request.setAttribute("totalprice", totalprice);

		return "pay/myinfowrite";
	}

	// 내정보입력 토대로 결제페이지 보여주기
	@RequestMapping(value = "/payment.yo", method = { RequestMethod.GET })
	public String login_payment(HttpServletRequest request , HttpServletResponse response) {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		String menuname = request.getParameter("menuname");
		String menucode = request.getParameter("menucode");
		String masterno = request.getParameter("masterno");
		String totalprice = request.getParameter("totalprice");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String tel = request.getParameter("tel");
		String yocheong = request.getParameter("yocheong");

		loginuser.setAddr1(addr1);
		loginuser.setAddr2(addr2);
		loginuser.setTel(tel);

		request.setAttribute("yocheong", yocheong);
		request.setAttribute("addr1", loginuser.getAddr1());
		request.setAttribute("addr2", loginuser.getAddr2());
		request.setAttribute("tel", loginuser.getTel());
		request.setAttribute("email", loginuser.getEmail());
		request.setAttribute("name", loginuser.getName()); // 로그인유저 이름
		request.setAttribute("menuname", menuname);
		request.setAttribute("menucode", menucode);
		request.setAttribute("masterno", masterno);
		request.setAttribute("totalprice", totalprice);
		
		return "pay/paymentGateway";
	}

	// 결제완료후 결제테이블 업데이트
	@RequestMapping(value = "/paymentEnd.yo", method = { RequestMethod.POST })
	public String login_paymentEnd(HttpServletRequest request , HttpServletResponse response) {
		String msg = "";
		String loc = "";
		
		String menuname = request.getParameter("menuname");
		String menucode = request.getParameter("menucode");
		String masterno = request.getParameter("masterno");
		String totalprice = request.getParameter("totalprice");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String tel = request.getParameter("tel");
		String yocheong = request.getParameter("yocheong");
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		HashMap<String, String> paraMap = new HashMap<String,String>();
		paraMap.put("paymenuname", menuname);
		paraMap.put("menucode", menucode);
		paraMap.put("masterno", masterno);
		paraMap.put("totalprice", totalprice);
		paraMap.put("addr1", addr1);
		paraMap.put("addr2", addr2);
		paraMap.put("tel", tel);
		paraMap.put("yocheong", yocheong);
		paraMap.put("email", loginuser.getEmail());
		
		// TBL_PAYMENT 테이블에 추가
		int m = service.insertPayment(paraMap);

		if (m == 1) {
			
				// 결제완료
				msg ="[" + loginuser.getName() + "]님 " + totalprice + "원 상품결제가 완료되었습니다.";
				loc = request.getContextPath() + "/index.yo";
				
				// 결제완료했으면 해당 아이디 장바구니 비우기
				int z = service.alldeleteCart(loginuser.getEmail());

				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);

				return "msg";

		} else {
			msg = "[" + loginuser.getName() + "]님의 코인액" + totalprice + "원 결제가 실패되었습니다.";
			loc = request.getContextPath() + "/index.yo";

			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);

			return "msg";
		}
	}

}
