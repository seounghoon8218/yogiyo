package com.spring.yogiyo.kkkcontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
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
import com.spring.common.MyUtil;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.kkkmodel.BoardVO;
import com.spring.yogiyo.kkkmodel.CommentVO;
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
	 * @RequestMapping(value="/gps.yo" , method= {RequestMethod.GET}
	 * ,produces="text/plain;charset=UTF-8")
	 * 
	 * @ResponseBody public String gps(HttpServletRequest request) { String latitude
	 * = request.getParameter("latitude"); String longitude =
	 * request.getParameter("longitude");
	 * 
	 * Gson gson = new Gson();
	 * 
	 * JsonObject jsonObj = new JsonObject();
	 * 
	 * jsonObj.addProperty("latitude", latitude); jsonObj.addProperty("longitude",
	 * longitude);
	 * 
	 * 
	 * return gson.toJson(jsonObj);
	 * 
	 * }
	 */

	// 음식점들 보여주는 화면
	@RequestMapping(value = "/categryList.yo", method = { RequestMethod.GET })
	public ModelAndView test(ModelAndView mv, HttpServletRequest request) {
		String shopcategorycode = request.getParameter("shopcategorycode");

		mv.addObject("shopcategorycode", shopcategorycode);

		mv.setViewName("restaurant/categryList.tiles2");
		return mv;
	}

	@RequestMapping(value = "/getShopList.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getShopList(HttpServletRequest request) {

		String shopcategorycode = request.getParameter("shopcategorycode");
		String cnt = request.getParameter("cnt");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		Gson gson = new Gson();

		// 매장들 가져오기
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("shopcategorycode", shopcategorycode);
		paraMap.put("cnt", cnt);
		paraMap.put("latitude", latitude);
		paraMap.put("longitude", longitude);

		List<oooVO> shopList = service.getShopList(paraMap);

		JsonArray jsonArr = new JsonArray();

		for (oooVO shopvo : shopList) {
			JsonObject jsonObj = new JsonObject();

			jsonObj.addProperty("masterno", shopvo.getMasterno());

			/* 총 별점 평균 보여주기 */
			double starpointAvg_double = service.getStarpointAvg(shopvo.getMasterno());
			String starpointAvg = String.valueOf(starpointAvg_double);
			/* 총 리뷰 갯수 보여주기 */
			int reviewCount = service.getReviewCount(shopvo.getMasterno());

			HashMap<String, String> MasterStarReviewMap = new HashMap<String, String>();
			MasterStarReviewMap.put("masterno", String.valueOf(shopvo.getMasterno()));
			MasterStarReviewMap.put("starpointAvg", starpointAvg);
			MasterStarReviewMap.put("reviewCount", String.valueOf(reviewCount));

			// 별점이랑 리뷰갯수 업데이트 해주기
			int sprc = service.updateStarpAndReviewc(MasterStarReviewMap);

			jsonObj.addProperty("starpointAvg", starpointAvg);
			jsonObj.addProperty("reviewCount", reviewCount);

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

	@RequestMapping(value = "/restaurantView.yo", method = { RequestMethod.GET })
	public String restaurantView(HttpServletRequest request) {


	      String masterno = request.getParameter("masterno");
	      System.out.println("==============>>/restaurantView.yo <<============== masterno==>" + masterno);
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

	      // 리뷰 보여주기 //
	      
	      List<HashMap<String, String>> reviewList = service.getReviewList(masterno);
	      
	      if (reviewList != null) {
	         //System.out.println("~~~~~~ 널이 아니군요~~~~");
	         request.setAttribute("reviewList", reviewList);
	      } else {
	         //System.out.println("~~~~~~ 널이군요~~~~");

	      }
	      
	      request.setAttribute("masterno", masterno);
	      request.setAttribute("starpointAvg", starpointAvg);
	      request.setAttribute("reviewCount", reviewCount);

	      /////////////////////////////////////////////////////////////

	      request.setAttribute("shop", shop);
	      return "restaurant/restaurantView.tiles2";	
	      }

	@RequestMapping(value = "/kkk/menucategoryList.yo", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String menucategoryList(HttpServletRequest request) {

		Gson gson = new Gson();

		// 메뉴카테고리 가져오기
		List<HashMap<String, String>> menucategoryList = service.getMenucategoryList();

		JsonArray jsonArr = new JsonArray();

		for (HashMap<String, String> map : menucategoryList) {
			JsonObject jsonObj = new JsonObject();

			jsonObj.addProperty("menuspeccode", map.get("menuspeccode"));
			jsonObj.addProperty("menuspecname", map.get("menuspecname"));

			jsonArr.add(jsonObj);

		}

		return gson.toJson(jsonArr);
	}

	@RequestMapping(value = "/kkk/menuList.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String menuList(HttpServletRequest request) {

		Gson gson = new Gson();
		String masterno = request.getParameter("masterno");
		String code = request.getParameter("code");

		HashMap<String, String> paramap = new HashMap<String, String>();
		paramap.put("masterno", masterno);
		paramap.put("code", code);

		// 리스트별 메뉴 가져오기
		List<HashMap<String, String>> menuList = service.getMenuList(paramap);

		JsonArray jsonArr = new JsonArray();

		for (HashMap<String, String> map : menuList) {
			JsonObject jsonObj = new JsonObject();

			jsonObj.addProperty("menucode", map.get("menucode"));
			jsonObj.addProperty("masterno", map.get("masterno"));
			jsonObj.addProperty("menuname", map.get("menuname"));
			jsonObj.addProperty("menuprice", map.get("menuprice"));
			jsonObj.addProperty("filename", map.get("filename"));
			jsonObj.addProperty("pop_menuspeccode", map.get("pop_menuspeccode"));

			jsonArr.add(jsonObj);

		}

		return gson.toJson(jsonArr);
	}

	// 주문표에 추가하기
	@RequestMapping(value = "/kkk/orderAdd.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String orderAdd(HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();
		int n, m;
		JsonObject jsonObj = new JsonObject();

		String menuname = request.getParameter("menuname");
		String menucode = request.getParameter("menucode");
		String masterno = request.getParameter("masterno");
		String menuprice = request.getParameter("menuprice");
		String filename = request.getParameter("filename");
		String menuqty = request.getParameter("menuqty");

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		if (loginuser == null) {
			jsonObj.addProperty("n", 3);
			return gson.toJson(jsonObj);
		}

		// 타음식점 추가 불가 기능
		String msno = service.getCartMasterno(loginuser.getEmail());

		if (msno != null) {
			if (!msno.equals(masterno)) {
				jsonObj.addProperty("n", 4);
				return gson.toJson(jsonObj);
			}
		}

		HashMap<String, String> paramap = new HashMap<String, String>();
		paramap.put("menuname", menuname);
		paramap.put("menucode", menucode);
		paramap.put("masterno", masterno);
		paramap.put("menuprice", menuprice);
		paramap.put("filename", filename);
		paramap.put("menuqty", menuqty);
		paramap.put("email", loginuser.getEmail());

		// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
		m = service.cartSelect(paramap);

		if (m == 0) { // 장바구니에 존재하지 않을때

			// 장바구니에 insert 해주기
			n = service.cartInsert(paramap);

			jsonObj.addProperty("n", n);

		} else { // 장바구니에 이미 존재할때
			jsonObj.addProperty("n", 2);
		}

		return gson.toJson(jsonObj);
	}

	// 주문표 보여주기
	@RequestMapping(value = "/kkk/orderMenuList.yo", method = { RequestMethod.GET })
	public String login_orderMenuList(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		String orderno = request.getParameter("orderno");

		if (orderno != null || "".equals(orderno)) {
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

      System.out.println("================= masterno : " + masterno);

      if (masterno != null) {

         List<HashMap<String, String>> reviewList = service.getReviewList(masterno);

         if (reviewList != null) {
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
         }
      }

      return gson.toJson(jsonArr);
      ///////////////////////////////////////////////////
   }
// 리뷰등록하기
   @RequestMapping(value = "/kkk/addReview.yo", method = { RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
   public String addReview(HttpServletRequest request, oooReviewVO reviewvo, MultipartHttpServletRequest mrequest) {

      HttpSession session1 = request.getSession();
      MemberVO loginuser = (MemberVO) session1.getAttribute("loginuser");

      if (loginuser == null) {
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
         String path = root + "resources" + File.separator + "images";
         String newFileName = "";
         byte[] bytes = null;
         long fileSize = 0;
         try {

            bytes = attach.getBytes();

            newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);

            reviewvo.setFileName(newFileName);
            reviewvo.setOrgFilename(attach.getOriginalFilename());

            fileSize = attach.getSize();
            reviewvo.setFileSize(String.valueOf(fileSize));
         } catch (Exception e) {
            e.printStackTrace();
         }

      } else {
         reviewvo.setFileName("");
         reviewvo.setFileSize("");
         reviewvo.setOrgFilename("");
      }
      
      String masterno = request.getParameter("masterno");
      String starpoint = request.getParameter("starpoint");
      String comments = request.getParameter("comments");
      String image = reviewvo.getOrgFilename();
      String menuname = request.getParameter("menuname");

      System.out.println(masterno + "," + starpoint + "," + comments + "," + menuname + "," + image);

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

      System.out.println("===============================checkemail 전 ===============================" + masterno);

      paramap.put("masterno", masterno);
      paramap.put("loginuserEmail", loginuserEmail);
      List<HashMap<String, String>> checkEmail = service.checkOrderStatus(paramap);

      if (checkEmail != null) {
         int n = service.addReview(paramap);

         if (n == 1) {

            String msg = "리뷰 등록 성공!";
            String loc = "javascript:history.go(-1)";
            request.setAttribute("msg", msg);
            request.setAttribute("loc", loc);
         } else {
            String msg = "리뷰 등록 실패ㅠ";
            String loc = "javascript:history.back()";
            request.setAttribute("msg", msg);
            request.setAttribute("loc", loc);

         }
      } else {
         String msg = "주문하신 상품만 리뷰를 남기실 수 있습니다!";
         String loc = "javascript:history.back()";
         request.setAttribute("msg", msg);
         request.setAttribute("loc", loc);
      }
      return "msg";
   }

   
   /*---------------------------손혜현 게시판 시작---------------------------------------*/      
   // 게시판 목록보여주기
    @RequestMapping(value="/kkkBoardList.yo", method= {RequestMethod.GET})
    public ModelAndView kkkBoardList(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
                
       List<BoardVO> boardList = null;
             
       String str_currentShowPageNo = request.getParameter("currentShowPageNo");
       
       int totalCount = 0;         // 총게시물 건수
       int sizePerPage = 3;      // 한 페이지당 보여줄 게시물 수
       int currentShowPageNo = 0;  // 현재 보여준느 페이지 번호로서, 초기치로는 1페이지로 설정함.
       int totalPage = 0;           // 총 페이지수 (웹브라우저상에 보여줄 총 페이지 수, 페이지바)
       
       int startRno = 0;         // 시작 행번호
       int endRno    = 0;         // 끝 행번호
       
       
       String searchType = request.getParameter("searchType");
       
       String searchWord = request.getParameter("searchWord");
       if(searchWord == null || searchWord.trim().isEmpty()) {
          searchWord = "";
       }
       
       HashMap<String, String> paraMap = new HashMap<String, String>();
       paraMap.put("searchType", searchType);
       paraMap.put("searchWord", searchWord.trim());
       
       // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
       // 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을 때로 나뉘어진다.
       
       // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
       if("".equals(searchWord)) {
          totalCount = service.getKKKTotalCountWithNOsearch();
       }
       // 검색조건이 있을 경우의 총 게시물 건수(totalCount)
       else {
          totalCount = service.getKKKTotalCountWithSearch(paraMap); // paraMap에 검색어가 있다.
       }
       
       totalPage =(int) Math.ceil((double)totalCount/sizePerPage); // 소수값을 정수로 올림을 해준다.
       
       if(str_currentShowPageNo == null) { // 첫화면
          // 게시판에 보여지는 초기화면
          currentShowPageNo = 1;   // 1페이지를 본다.
          // 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다.
       }
       else {
          
          try {         
             currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
             
             if(currentShowPageNo < 1  || currentShowPageNo > totalPage) {
                currentShowPageNo = 1;
             }
             
          } catch (NumberFormatException e) {
             currentShowPageNo = 1;
          }
       }
                
       startRno = ((currentShowPageNo - 1 )* sizePerPage + 1 ) ;
       
       endRno = startRno + sizePerPage - 1;
       
       paraMap.put("startRno", String.valueOf(startRno));
       paraMap.put("endRno", String.valueOf(endRno));
       
       boardList = service.KKKBoardListWithPaging(paraMap);
       // 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 다 포함한 것)
       
       if(!"".equals(searchWord)) {
          mv.addObject("paraMap", paraMap);
       }
       
       // === #119.페이지바 만들기 ===
       String pagebar = "<ul>";
       
       String url = "kkkBoardList.yo?";
       int blockSize = 10;      
       
       pagebar += MyUtil.makePageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, searchType, searchWord);
       
       pagebar += "</ul>";
       
       mv.addObject("pagebar", pagebar);
       
       
       ////////////////////////////////////////////////////////////////////////////
       
       HttpSession session = request.getSession();
       session.setAttribute("readCountPermission", "yes");
       String goBackURL = MyUtil.getCurrentURL(request);
       mv.addObject("goBackURL", goBackURL);
       
       ///////////////////////////////////////////////////
       
       // 날짜 더하기 시작-----------------------------------------------
          
          String today = null;
          Date date = new Date(); 
          
          SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
           
          Calendar cal = Calendar.getInstance();
           
          cal.setTime(date); 
           
          cal.add(Calendar.MONTH, 3);
           
          today = sdformat.format(cal.getTime());
           
          mv.addObject("endDate", today);
          
          // 날짜 더하기 끝-----------------------------------------
       
       
       mv.addObject("boardList", boardList);
       
       mv.setViewName("kkkBoard/listKKK.tiles3");
       
       return mv;
    }
    
    
    

    // === #51. 게시판 글쓰기 폼페이지 요청 ===
    @RequestMapping(value="/addKKKBoard.yo", method= {RequestMethod.GET})
    public ModelAndView requireLogin_addKKKBoard(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
       
       // === #124. 답변글쓰기가 추가된 경우 ===
       String fk_seq = request.getParameter("fk_seq");
       String groupno = request.getParameter("groupno");
       String depthno = request.getParameter("depthno");
       
       mv.addObject("fk_seq", fk_seq);
       mv.addObject("groupno", groupno);
       mv.addObject("depthno", depthno);
       
       mv.setViewName("kkkBoard/addKKKBoard.tiles3");
       
       return mv;
    }
    
    
    // === #53. 게시판 글쓰기 완료 요청 ===
    @RequestMapping(value="/addKKKEnd.yo", method= {RequestMethod.POST})       
    public String addKKKEnd(BoardVO boardvo, MultipartHttpServletRequest mrequest) { 
       
       
       // ========= !!첨부파일이 있는지 없는지 알아오기 시작!! =========      
       MultipartFile attach = boardvo.getAttach();
       if(!attach.isEmpty()) {
          // attach 가 비어있지 않다면(즉, 첨부파일이 있는 경우라면)
          
          
             // WAS 의 webapp 의 절대경로를 알아와야 한다.
             HttpSession session = mrequest.getSession();
             String root = session.getServletContext().getRealPath("/");
             String path = root + "resources" + File.separator+"files";
             
             System.out.println("확인 >>" + path);
             // 확인 >>C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\
             
          //   2. 파일 첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일 올리기
             String newFileName = "";
             // WAS(톰캣)에 디스크에 저장 될 파일명
             
             byte[] bytes = null;
             // 첨부파일을 WAS(톰캣)의 디스크에 저장 할 때 사용되는 용도
             
             long fileSize = 0;
             // 파일 크기를 읽어오기 위한 용도
             
             try {
                
                bytes= attach.getBytes();
                // 파일이 깨진 것일 경우 바꾸지 못하기 때문에 Exception이 뜬다. 그래서 try를 써준다.
                // getBytes() method는 첨부된 파일을 byte 단위로 파일을 다 읽어오는 것이다. 
                // 예를 들어, 첨부한 파일이 "강아지.png"라면 
                // 이 파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.
                
                
                newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
                // 이제 파일을 올린다.
                // attach.getOriginalFilename() 은 첨부된 파일의 파밀명(강아지.png) 이다.
                
                System.out.println(">>>>"+newFileName);
                
                // 3. BoardVO boardvo 에 filename 값과 orgFilename 값과 fileSize 값을 넣어주기
                boardvo.setFileName(newFileName);
                boardvo.setOrgFilename(attach.getOriginalFilename());
                
                fileSize = attach.getSize();
                boardvo.setFileSize(String.valueOf(fileSize));
                // 게시판 페이지에서 첨부한 파일의 크기를 보여줄 때 String 타입으로 변경해준다.
                
             } catch (Exception e) {
                e.printStackTrace();
             }
             
       }
    //   ========= !!첨부파일이 있는지 없는지 알아오기 끝!! ========= 
        
       String content = boardvo.getContent();
       
       // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
       boardvo.setContent(MyUtil.replaceParameter(content));        
       
       content = boardvo.getContent().replaceAll("\r\n", "<br/>");
       boardvo.setContent(content);         
       
       
             
    // === #138.파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출하기 ===
    //       먼저 위의 int n = service.add(boardvo) 부분을 주석처리하고 아래처럼 한다.
       
       int n = 0;
       if(attach.isEmpty()) {
          // 첨부파일이 없는 경우
          n = service.KKKadd(boardvo);
       }
       else {
          // 첨부파일이 있는 경우
          n = service.KKKadd_withFile(boardvo);
       }
       
       mrequest.setAttribute("n", n);
       
       return "kkkBoard/addKKKEnd.tiles3";
    }
    
    
    
    // === #61. 글1개를 보여주는 페이지 요청 ===
    @RequestMapping(value="/viewKKK.yo", method= {RequestMethod.GET})   
    public ModelAndView viewKKK(HttpServletRequest request, ModelAndView mv) {
       
       // 조회하고자 하는 글번호 받아오기
       String seq = request.getParameter("seq");
       
       String goBackURL = request.getParameter("goBackURL");
       mv.addObject("goBackURL", goBackURL);
       
       HttpSession session = request.getSession();
       MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
       
       String userid = null; 
       
       if(loginuser != null) {
          userid = loginuser.getEmail(); 
          // 로그인 되어진 사용자의 userid 
       }
       
       BoardVO boardvo = null;
       
       // 위의 글목록보기 에서 session.setAttribute("readCountPermission", "yes"); 해두었다.
       if("yes".equals(session.getAttribute("readCountPermission")) ) {
          // 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
       
          boardvo = service.getKKKView(seq, userid);
          // 글조회수 증가와 함께 글1개 조회를 해주는 것
          
          session.removeAttribute("readCountPermission");
          // 중요함!! session 에 저장된 readCountPermission을 삭제한다.
       }
       else {
         boardvo = service.getKKKViewWithNoAddCount(seq);
         // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것
         
       }
       // === #92. 댓글쓰기가 있는 게시판 === //
       List<CommentVO> commentList = service.getKKKCommentList(seq);
       // 원게시물에 딸린 댓글들을 조회해오는 것
       mv.addObject("commentList", commentList);
       ////////////////////////////////////////////////
       
       
      // 날짜 더하기 시작-----------------------------------------------
       
       String today = null;
       Date date = new Date(); 
       
       SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        
       Calendar cal = Calendar.getInstance();
        
       cal.setTime(date); 
        
       cal.add(Calendar.MONTH, 3);
        
       today = sdformat.format(cal.getTime());
        
       mv.addObject("endDate", today);
       
       // 날짜 더하기 끝-----------------------------------------
     
       mv.addObject("boardvo", boardvo);
       
       mv.setViewName("kkkBoard/viewKKK.tiles3");
       
       return mv;
    }
    
    
    // === #70. 글수정 페이지 요청 ===
    @RequestMapping(value="/editKKK.yo", method= {RequestMethod.GET})
    public ModelAndView requireLogin_editKKK(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
       
       // 글 수정해야할 글번호 가져오기
       String seq = request.getParameter("seq");
       
       // 글 수정해야할 글1개 내용 가져오기
       BoardVO boardvo = service.getKKKViewWithNoAddCount(seq);
       // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
       
       HttpSession session = request.getSession();
       MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
       
       if( !loginuser.getEmail().equals(boardvo.getFk_userid()) ) { 
          String msg = "다른 사용자의 글은 수정이 불가합니다.";
          String loc = "javascript:history.back()";
          
          mv.addObject("msg", msg);
          mv.addObject("loc", loc);
          mv.setViewName("msg");
       }
       else {
          // 자신의 글을 수정할 경우 
          // 가져온 1개글을 글수정할 폼이 있는 view 단으로 보내준다.
          mv.addObject("boardvo", boardvo);
          
          mv.setViewName("kkkBoard/editKKK.tiles3");
       }
       
       return mv;
    }
    

    // === #71. 글수정 페이지 완료하기 ===
    @RequestMapping(value="/editKKKEnd.yo", method= {RequestMethod.POST})
    public ModelAndView requireLogin_editKKKEnd(HttpServletRequest request, HttpServletResponse response, BoardVO boardvo, ModelAndView mv) {
       
       String content = boardvo.getContent();
       
       // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
      content = MyUtil.replaceParameter(content);
      content = content.replaceAll("\r\n", "<br/>");
       
       boardvo.setContent(content);
       
       
       int n = service.editKKK(boardvo);
       
       if(n == 0) {
          mv.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");
       }
       else {
          mv.addObject("msg", "글수정 성공!!");
       }
       
       mv.addObject("loc", request.getContextPath()+"/viewKKK.yo?seq="+boardvo.getSeq());
       mv.setViewName("msg");
       
       return mv;
    }
    
    
    // === #77. 글삭제 페이지 요청 ===
    @RequestMapping(value="/delKKK.yo", method= {RequestMethod.GET})
    public ModelAndView requireLogin_delKKK(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
    
       // 삭제해야할 글번호를 받아온다.
       String seq = request.getParameter("seq");
       
       // 삭제해야할 글1개 내용 가져오기
       BoardVO boardvo = service.getKKKViewWithNoAddCount(seq);
       // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
       
       HttpSession session = request.getSession();
       MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
       
       if( !loginuser.getEmail().equals(boardvo.getFk_userid()) ) { 
          String msg = "다른 사용자의 글은 삭제가 불가합니다.";
          String loc = "javascript:history.back()";
          
          mv.addObject("msg", msg);
          mv.addObject("loc", loc);
          mv.setViewName("msg");
       }
       else {
          // 자신의 글을 삭제할 경우
          // 글삭제시 입력한 암호가 글작성시 입력해준 암호와 일치하는지
          // 알아오도록 del.jsp 페이지로 넘긴다.
          mv.addObject("seq", seq);
          mv.setViewName("kkkBoard/delKKK.tiles3");
       }
       
       return mv;
    }
    
    
    // === #78. 글삭제 페이지 완료하기 ===
    @RequestMapping(value="/delKKKEnd.yo", method= {RequestMethod.POST})
    public ModelAndView delKKKEnd(HttpServletRequest request, ModelAndView mv) {
       
       try {
          // 삭제해야할 글번호 및 사용자가 입력한 암호를 받아온다.
          String seq = request.getParameter("seq");
          String pw = request.getParameter("pw");
          
          BoardVO boardvo = new BoardVO();
          boardvo.setSeq(seq);
          boardvo.setPw(pw);
          
          int n;
       
          n = service.delKKK(boardvo);
       
          
          if(n == 0) {
             mv.addObject("msg", "암호가 일치하지 않아 글 삭제가 불가합니다.");
          }
          else {
             mv.addObject("msg", "글삭제 성공!!");
          }
          
          mv.addObject("loc", request.getContextPath()+"/kkkBoardList.yo");
          mv.setViewName("msg");
       } catch (Throwable e) {
          e.printStackTrace();
       }
       return mv;
    }
    
    
    // === #85. 댓글쓰기 ===
    @RequestMapping(value="/addKKKComment.yo", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")      
    @ResponseBody
    public String addKKKComment(CommentVO commentvo) {
       
       String jsonStr = ""; 
       
       // 댓글쓰기(*** Ajax 로 처리 ***)
       try {
           int n = service.addKKKComment(commentvo);
           
           if(n==1) {
                // 댓글쓰기(insert) 및 
                // 원게시물(tblBoard 테이블)에 댓글의 갯수(1씩 증가) 증가(update)가 성공했다라면
                
              List<CommentVO> commentList = service.getKKKCommentList(commentvo.getParentSeq());
              // 원게시물에 딸린 댓글들을 조회해오는 것
              
              JSONArray jsonArr = new JSONArray();
              
              for(CommentVO cmtvo : commentList) {
                 JSONObject jsonObj = new JSONObject();
                 jsonObj.put("name", cmtvo.getName());
                 jsonObj.put("content", cmtvo.getContent());
                 jsonObj.put("regDate", cmtvo.getRegDate());
                 
                 jsonArr.put(jsonObj);
              }
              
              jsonStr = jsonArr.toString();
              
          }
       } catch (Throwable e) {
          e.printStackTrace();
       } 
       
        return jsonStr;
    }
    
    
    
    
    
 // ===== #147. 첨부파일 다운로드 받기 =====
    @RequestMapping(value="/KKKdownload.yo", method={RequestMethod.GET}) 
    public void requireLogin_KKKdownload(HttpServletRequest req, HttpServletResponse res) {
       
       String seq = req.getParameter("seq"); 
       // 첨부파일이 있는 글번호
       
       // 첨부파일이 있는 글번호에서 
       // 201907250930481985323774614.jpg 처럼
       // 이러한 fileName 값을 DB에서 가져와야 한다. 
       // 또한 orgFileName 값도 DB에서 가져와야 한다.
       
       BoardVO vo = service.getKKKViewWithNoAddCount(seq);
       // 조회수 증가 없이 1개 글 가져오기
       // 먼저 board.xml 에 가서 id가 getView 인것에서
       // select 절에 fileName, orgFilename, fileSize 컬럼을
       // 추가해주어야 한다.
       
       String fileName = vo.getFileName(); 
       // 201907250930481985323774614.jpg 와 같은 것이다.
       // 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
       
       String orgFilename = vo.getOrgFilename(); 
       // Desert.jpg 처럼 다운받을 사용자에게 보여줄 파일명.
       
       
       // 첨부파일이 저장되어 있는 
       // WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
       // 이 경로는 우리가 파일첨부를 위해서
       //    /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
       // WAS 의 webapp 의 절대경로를 알아와야 한다. 
       HttpSession session = req.getSession();
       
       String root = session.getServletContext().getRealPath("/"); 
       String path = root + "resources"+File.separator+"files";
       // path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
       
       // **** 다운로드 하기 **** //
       // 다운로드가 실패할 경우 메시지를 띄워주기 위해서
       // boolean 타입 변수 flag 를 선언한다.
       boolean flag = false;
       
       flag = fileManager.doFileDownload(fileName, orgFilename, path, res);
       // 다운로드가 성공이면 true 를 반납해주고,
       // 다운로드가 실패이면 false 를 반납해준다.
       
       if(!flag) {
          // 다운로드가 실패할 경우 메시지를 띄워준다.
          
          res.setContentType("text/html; charset=UTF-8"); 
          PrintWriter writer = null;
          
          try {
             writer = res.getWriter();
             // 웹브라우저상에 메시지를 쓰기 위한 객체생성.
          } catch (IOException e) {
             
          }
          
          writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
          
       }
        
    } // end of void download(HttpServletRequest req, HttpServletResponse res, HttpSession session)---------
    
    
    
    
    /*// ==== #스마트에디터3. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
       @RequestMapping(value="/image/multiplePhotoUpload.yo", method={RequestMethod.POST})
       public void kkkMultiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
           
                   
       // WAS 의 webapp 의 절대경로를 알아와야 한다. 
       HttpSession session = req.getSession();
       String root = session.getServletContext().getRealPath("/"); 
       String path = root + "resources"+File.separator+"photo_upload";
       // path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
          
       // System.out.println(">>>> 확인용 path ==> " + path); 
       // >>>> 확인용 path ==> C:\springworkspace_daumeditor\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload  
          
       File dir = new File(path);
       if(!dir.exists())
           dir.mkdirs();
          
       String strURL = "";
          
       try {
          if(!"OPTIONS".equals(req.getMethod().toUpperCase())) {
              String filename = req.getHeader("file-name"); //파일명을 받는다 - 일반 원본파일명
                 
                 
                 InputStream is = req.getInputStream();
              
                 String newFilename = fileManager.doFileUpload(is, filename, path);
              
             int width = fileManager.getImageWidth(path+File.separator+newFilename);
             
             if(width > 600)
                width = 600;
                
          // System.out.println(">>>> 확인용 width ==> " + width);
          // >>>> 확인용 width ==> 600
          // >>>> 확인용 width ==> 121
              
             String CP = req.getContextPath(); // board
             
             strURL += "&bNewLine=true&sFileName="; 
                      strURL += newFilename;
                      strURL += "&sWidth="+width;
                      strURL += "&sFileURL="+CP+"/resources/photo_upload/"+newFilename;
              }
          
              /// 웹브라우저상에 사진 이미지를 쓰기 ///
             PrintWriter out = res.getWriter();
             out.print(strURL);
       } catch(Exception e){
             e.printStackTrace();
       }
       
       }
*/
       
    // === 검색어 입력시 자동 글 완성하기  ===
       @RequestMapping(value="/KKKwordSearchShow.yo", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")      
       @ResponseBody
       public String KKKwordSearchShow(HttpServletRequest request) {
          
          String searchType = request.getParameter("searchType");
          String searchWord = request.getParameter("searchWord");
          
          HashMap<String, String> paraMap = new HashMap<String, String>();
          paraMap.put("searchType", searchType);
          paraMap.put("searchWord", searchWord);
          
          List<String> wordList = service.KKKwordSearchShow(paraMap);
          
          JSONArray jsonArr = new JSONArray();
          
          if(wordList != null) {
             for(String word :wordList) {
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("word", word);
               
               jsonArr.put(jsonObj);
             }
          }
          
          String result = jsonArr.toString();
          
          return result;
       }
    
   
}
