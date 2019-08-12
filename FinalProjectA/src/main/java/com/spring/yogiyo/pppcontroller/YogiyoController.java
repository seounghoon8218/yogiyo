package com.spring.yogiyo.pppcontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import com.spring.yogiyo.ooomodel.oooVO;
import com.spring.yogiyo.pppmodel.BoardVO;
import com.spring.yogiyo.pppmodel.CommentVO;
import com.spring.yogiyo.pppservice.InterYogiyoService;

// #30
@Controller
public class YogiyoController {

   // === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
   @Autowired
   private InterYogiyoService service;

   
// === 파일업로드 및 다운로드를 해주는 FIleManager 클래스 의존객체 주입하기 ( DI : Dependency
   // Injection ) ====
   @Autowired
   private FileManager filemanager;
   
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

   // 관리자 차트보는페이지 , 회원관리(페이징처리)
   @RequestMapping(value = "/adminChart.yo", method = { RequestMethod.GET })
   public ModelAndView adminChart(ModelAndView mv ,HttpServletRequest request) {

      String searchType = "";
      String searchWord = "";
      String code="";
      
      // 1. 페이징 처리를 위해 한 페이지당 보여줄 장바구니에 들어간 제품의 갯수를 몇개로 할 것인가 정한다. 
            // 한페이지당 보여줄 제품의 갯수는 3개로 한다.
            int sizePerPage = 10;
            
            // 2. 페이징 처리한 데이터 조회 결과물 가져오기 - 전체 페이지 갯수 알아오기
            int totalPage = 0;
            
            searchWord = request.getParameter("searchWord"); 
            
            if(searchWord == null) {
               searchWord = "";
            }
            
            // 총 회원수 구하기
            int totalMemberCnt = service.getTotalMemberCnt(searchWord);

            totalPage = (int) Math.ceil((double)totalMemberCnt/sizePerPage); // Math.ceil(2.3333) => 3.0 , Math.ceil(4.7231) => 5.0
            
            // 3. 조회하고자 하는 페이지번호를 받아와야 한다.
            String str_currentShowPageNo =request.getParameter("currentShowPageNo");
            int currentShowPageNo = 0;
            try {
               if(str_currentShowPageNo == null) {
                  currentShowPageNo=1;
               }
               
               currentShowPageNo = Integer.parseInt(str_currentShowPageNo);               
               if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
                  currentShowPageNo = 1;
               }
               
            } catch (NumberFormatException e) {
               currentShowPageNo = 1;
            }
            
            int startno = (currentShowPageNo*sizePerPage)-(sizePerPage-1);
            int endno = currentShowPageNo*sizePerPage;
            
            HashMap<String, String> paraMap = new HashMap<String,String>();
            paraMap.put("searchWord", searchWord);
            paraMap.put("startno", String.valueOf(startno) );
            paraMap.put("endno", String.valueOf(endno) );
            
            // 4. 페이징 처리한 데이터 조회 결과물 가져오기
            List<MemberVO> MemberList = service.getPageMember(paraMap);

            // 5. 페이지바 만들기 ( 매번 만들기 싫으니 유틸에 만들자!! )
            String url = request.getContextPath()+"/adminChart.yo?";
            int blockSize = 10; // 1 2 3 4 5 6 7 8 9 10 [다음] , [이전] 11 12 13 14 15 16 17 18 19 20 [다음] --> 이런식인데 지음은 1 2 [다음] , [이전] 3 4 [다음]
            
            
            String pageBar = MyUtil.makePageBar(url,currentShowPageNo,sizePerPage,totalPage,blockSize, searchType, searchWord);
            
            
            request.setAttribute("MemberList", MemberList);
            request.setAttribute("pageBar", pageBar);
      
   
      
      //////
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
   
   // 음식별 매장 판매 랭킹
   @RequestMapping(value = "/categoryRankShop.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
   @ResponseBody
   public String categoryRankShop(HttpServletRequest request) {
      
      // 음식별 매장 판매 랭킹
      List<HashMap<String, String>> chartList = service.categoryRankShop();
      
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
   @RequestMapping(value = "/categoryRankShopEnd.yo", method = { RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
   @ResponseBody
   public String categoryRankShopEnd(HttpServletRequest request) {
      
      String shopcategoryname = request.getParameter("shopcategoryname");
      
      // 음식별 매장 판매 랭킹
      List<HashMap<String, String>> chartList = service.categoryRankShopEnd(shopcategoryname);
      
      Gson gson = new Gson();
      
      JsonArray jsonArr = new JsonArray();
      for (HashMap<String, String> map : chartList) {
         JsonObject jsonObj = new JsonObject();
         
         jsonObj.addProperty("shopname", map.get("shopname"));
         jsonObj.addProperty("percnt", map.get("percnt"));
         jsonObj.addProperty("cnt", map.get("cnt"));
         
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

   //////////////
   
// 박성훈 게시판 페이지--
   @RequestMapping(value = "/pshComplimentBoard.yo", method = { RequestMethod.GET })
   public String pshComplimentBoard(HttpServletRequest request , HttpServletResponse response) {
      List<BoardVO> boardList = null;
      
      // 페이징처리 + 검색어 _ 전체글 목록 보여주기.
      String str_currentShowPageNo = request.getParameter("currentShowPageNo");
      
      int totalCount = 0; // 총 게시물 건수
      int sizeperPage = 10; // (selectBox 로 3, 5, 10) 한 페이지당 보여줄 게시물 수
      int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정해야 한다.
      int totalPage = 0; // 총 페이지 수( 웹브라우저 상에 보여줄 총 페이지 수, 페이지바 )

      int startRno = 0; // 시작 행번호
      int endRno = 0; // 끝 행 번호
      
      String searchType = request.getParameter("searchType");
      String searchWord = request.getParameter("searchWord");
      if (searchWord == null || searchWord.trim().isEmpty()) {
         searchWord = "";
      }
      
      HashMap<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("searchType", searchType);
      paraMap.put("searchWord", searchWord.trim());
      
      // 검색조건이 없을경우의 총 게시물 건수(totalCount)
      if ("".equals(searchWord)) {
         totalCount = service.getTotalCountWithNoSearch();
      }
      // 검색조건이 있을경우의 총 게시물 건수(totalCount)
      else {
         totalCount = service.getTotalCountWithSearch(paraMap);
      }      
      
      totalPage = (int) Math.ceil((double) totalCount / sizeperPage); // (double)16/5 ==> 3.2 => ceil(3.2) ==> 4.0 => // (int)4.0 => 4

      if (str_currentShowPageNo == null) {
         // 게시판에 보여지는 초기화면

         currentShowPageNo = 1;
         // 즉 초기화면은 list.action?currentShowPageNo=1 로 한다는 말이다.
      } else {

         try {
            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);

            if (currentShowPageNo < 1 || currentShowPageNo > totalPage) { // user 장난 방지
               currentShowPageNo = 1;
            }

         } catch (NumberFormatException e) { // user가 url에 장난질 칠경우 1페이지로 고정시키자
            currentShowPageNo = 1;
         }

      }
      
      // *** 가져올 게시글의 범위를 구한다. ( 공식!! ) ***
      startRno = ((currentShowPageNo - 1) * sizeperPage) + 1;
      endRno = startRno + sizeperPage - 1;

      paraMap.put("startRno", Integer.toString(startRno));
      paraMap.put("endRno", String.valueOf(endRno));

      // 페이징 처리한 글목록 가져오기 ( 검색이 있든지, 검색이 없든지 다 포함한 것 )
      boardList = service.boardListWithPaging(paraMap);
      
      if (!"".equals(searchWord)) {
         request.setAttribute("paraMap", paraMap);
      }
      
      // === # 122. 페이지바 만들기 ===
      String pagebar = "<ul>";

      String url = "list.action";
      int blockSize = 10;

      pagebar += MyUtil.makePageBar(url, currentShowPageNo, sizeperPage, totalPage, blockSize, searchType, searchWord);

      pagebar += "</ul>";

      request.setAttribute("pagebar", pagebar);
      
      // === 글조회수(readCount)증가 (DML문 update)는
      // 반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만
      // 증가되고, 웹브라우저에서 새로고침(F5)을 했을 경우에는
      // 증가가 되지않도록 해야한다.
      // 이것을 하기 위해서는 session을 사용하여 처리하면 된다.
      HttpSession session = request.getSession();
      session.setAttribute("readCountPermission", "yes");
      /*
      * session 에 "readCountPermission" 키값으로 저장된 value값은 "yes" 이다. session 에
      * "readCountPermission" 키값에 해당하는 "yes"를 얻어오기 위해서는 반드시 웹브라우저에서 주소창에
      * "/pshComplimentBoard.yo" 이라고 입력해야만 얻어올 수 있다.
      */

      // === 현재 페이지의 주소(URL) 알아내기 ===
      String url2 = MyUtil.getCurrentURL(request);
//      System.out.println("\n 주소 ==> "+url); // 
      session.setAttribute("gobacklist", url2); // 세션에 url 정보를 저장한다.

      request.setAttribute("boardList", boardList);
      
      return "pppBoard/pshComplimentBoard.tiles3";
   }
   
   // === 게시판 글쓰기 폼페이지 요청 ===
   @RequestMapping(value = "/pppadd.yo", method = { RequestMethod.GET })
   public ModelAndView login_pppadd(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

      // === #127. 답변글쓰기 추가된 경우 ===
      String fk_seq = request.getParameter("fk_seq");
      String groupno = request.getParameter("groupno");
      String depthno = request.getParameter("depthno");

      mv.addObject("fk_seq", fk_seq);
      mv.addObject("groupno", groupno);
      mv.addObject("depthno", depthno);

      mv.setViewName("pppBoard/add.tiles3");
      return mv;
   }
   
   // ===  게시판 글쓰기 완료 요청 ===
   /*
    * mrequest 을 사용하기 위해서는 먼저 servlet-context.xml 파일에서 multipartResolver 를 bean으로
    * 등록해주어야 한다.!!!
    */
   @RequestMapping(value = "/pppaddEnd.yo", method = { RequestMethod.POST })
   public String pppaddEnd(BoardVO boardvo, MultipartHttpServletRequest mrequest) {
      /*
       * 웹페이지에 요청form이 enctype="multipart/form-data" 으로 되어있어서 Multipart 요청(파일처리 요청)이
       * 들어올때 컨트롤러에서는 HttpServletRequest 대신 MultipartHttpServletRequest 인터페이스를 사용해야
       * 한다. MultipartHttpServletRequest 인터페이스는 HttpServletRequest 인터페이스와
       * MultipartRequest 인터페이스를 상속받고있다. 즉, 웹 요청 정보를 얻기 위한 getParameter()와 같은 메소드와
       * Multipart(파일처리) 관련 메소드를 모두 사용가능하다.
       * 
       */
      
      MultipartFile attach = boardvo.getAttach();
      if (!attach.isEmpty()) {
         // attach 가 비어있지 않다면 ( 즉 , 첨부파일이 있는 경우라면 )
         /*
          * 1. 사용자가 보낸 파일을 WAS(톰켓)의 특정 폴더에 저장해주어야 한다. >>> 파일이 업로드 되어질 특정경로(폴더) 지정해주기 우리는
          * WAS의 webapp/resources/files 라는 폴더로 지정해주겠다. (
          * C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\
          * wtpwebapps\Board\resources ) 이곳에 만들어주겠다는뜻!!
          */
         // WAS의 webapp의 절대경로를 알아와야 한다.
         HttpSession session = mrequest.getSession();
         String root = session.getServletContext().getRealPath("/");
         String path = root + "resources" + File.separator + "files";
         // File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자
         // 운영체제가 윈도우 이라면 File.separator 은 "\" 이고,
         // 운영체제가 유닉스,리눅스 라면 File.separator 은 "/" 이다.
      
         // path가 첨부파일을 저장할 WAS(톰캣)의 폴더가 되어진다.
         System.out.println("확인용 >>> " + path); // 확인용 >>>
                                                // C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files

         // 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일올리기
         String newFileName = "";
         // WAS(톰캣)에 디스크에 저장될 파일명

         byte[] bytes = null;
         // 첨부파일을 WAS(톰캣)에 디스크에 저장할때 사용되는 용도

         long fileSize = 0;
         // 파일크기를 읽어오기 위한 용도         
      
         try {
            bytes = attach.getBytes(); // 첨부된 파일을 byte 로 바꿀때 ( io익셉션 발생한다. 꺠진파일이며 오류발생하기위해 )
            // getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것
            // 예를 들어, 첨부한 파일이 "강아지.png" 이라면
            // 이 파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.

            newFileName = filemanager.doFileUpload(bytes, attach.getOriginalFilename(), path);
            // 이제 파일올리기를 한다.
            // attach.getOriginalFilename() 은 첨부된 파일의 파일명(강아지.png) 이다.

            System.out.println("확인용 >>> " + newFileName);

            // === 3. BoardVO boardvo 에 fileName과 orgFilename 값과 fileSize 값을 넣어주기
            boardvo.setFileName(newFileName);
            // WAS(톰캣)에 저장된 파일명 20190725124547178350354710300.PNG

            boardvo.setOrgFilename(attach.getOriginalFilename());
            // 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)를 보여줄때 및
            // 사용자가 파일을 다운로드 할때 사용되어지는 파일명

            fileSize = attach.getSize();
            boardvo.setFileSize(String.valueOf(fileSize));
            // 게시판에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함

         } catch (Exception e) {
            e.printStackTrace();
         }
      }// 첨부파일 존재여부확인 끝!---
      
      String content = boardvo.getContent();

      // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 **
       content = MyUtil.replaceParameter(content);

      content = content.replaceAll("\r\n", "<br>");
      boardvo.setContent(content);
      
//      int n = service.add(boardvo); // 글쓰기( 파일첨부가 없는 글쓰기 )

      // === #141. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출하기
      // 먼저 위의 int n = service.add(boardvo); 부분을 주석처리하고서 아래처럼 한다.

      int n = 0;
      if (attach.isEmpty()) {
         // 첨부파일이 없는 경우라면
         n = service.add(boardvo);
      } else {
         // 첨부파일이 있는 경우라면
         n = service.add_withFile(boardvo);
      }

      mrequest.setAttribute("n", n);
      
      return "pppBoard/addEnd.tiles3";
   }
      
   // 글1개를 보여주는 페이지 요청 ===
   @RequestMapping(value="/pppview.yo", method= {RequestMethod.GET})   
   public ModelAndView pppview(HttpServletRequest request, ModelAndView mv) {
      
       // 조회하고자 하는 글번호 받아오기
       String seq = request.getParameter("seq");
      
       String gobackURL = request.getParameter("gobackURL");
       mv.addObject("gobackURL", gobackURL);
      
       HttpSession session = request.getSession();
       MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
       
       String userid = null; 
         
       if(loginuser != null) {
          userid = loginuser.getEmail(); 
          // 로그인 되어진 사용자의 userid 
       }
       
       BoardVO boardvo = null;
       
      // 위의 글목록보기 #68. 에서 session.setAttribute("readCountPermission", "yes"); 해두었다.
      if("yes".equals(session.getAttribute("readCountPermission")) ) {
         // 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
         
         boardvo = service.getView(seq, userid);
         // 글조회수 증가와 함께 글1개 조회를 해주는 것
            
         session.removeAttribute("readCountPermission");
            // 중요함!! session 에 저장된 readCountPermission을 삭제한다.
      }
      else {
         boardvo = service.getViewWithNoAddCount(seq);
           // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것
      }
       
      // 댓글쓰기가 있는 게시판 === //
      List<CommentVO> commentList = service.getCommentList(seq); 
      // 원게시물에 딸린 댓글들을 조회해오는 것
      mv.addObject("commentList", commentList);
      ///////////////////////////////////////////
               
      mv.addObject("boardvo", boardvo);
      
      mv.setViewName("pppBoard/view.tiles3");   
      return mv;
   }
                  
   // 글수정 페이지 요청 ===
      @RequestMapping(value="/pppedit.yo", method= {RequestMethod.GET})
      public ModelAndView login_pppedit(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
         
         // 글 수정해야할 글번호 가져오기
         String seq = request.getParameter("seq");
         
         // 글 수정해야할 글1개 내용 가져오기
         BoardVO boardvo = service.getViewWithNoAddCount(seq);
         // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
         
         HttpSession session = request.getSession();
         MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
         
         if( !loginuser.getEmail().equals(boardvo.getFk_email()) ) { 
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
            
            mv.setViewName("pppBoard/edit.tiles3");
         }
         
         return mv;
      }   
   
      
      // 글수정 페이지 완료하기 ===
      @RequestMapping(value="/pppeditEnd.yo", method= {RequestMethod.POST})
      public ModelAndView login_pppeditEnd(HttpServletRequest request, HttpServletResponse response, BoardVO boardvo, ModelAndView mv) {
         
         String content = boardvo.getContent();
         
         // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
         content = MyUtil.replaceParameter(content);
         content = content.replaceAll("\r\n", "<br/>");
         
         boardvo.setContent(content);
         
         /* 글 수정을 하려면 원본글의 글암호와 수정시 입력해준 암호가 일치할때만
                 수정이 가능하도록 해야 한다. */
         int n = service.edit(boardvo);
         
         if(n == 0) {
            mv.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");
         }
         else {
            mv.addObject("msg", "글수정 성공!!");
         }
         
         mv.addObject("loc", request.getContextPath()+"/pppview.yo?seq="+boardvo.getSeq());
         mv.setViewName("msg");
         
         return mv;
      }
      
      // === #77. 글삭제 페이지 요청 ===
      @RequestMapping(value="/pppdel.yo", method= {RequestMethod.GET})
      public ModelAndView login_pppdel(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
      
         // 삭제해야할 글번호를 받아온다.
         String seq = request.getParameter("seq");
         
         // 삭제해야할 글1개 내용 가져오기
         BoardVO boardvo = service.getViewWithNoAddCount(seq);
         // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
         
         HttpSession session = request.getSession();
         MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
         
         if( !loginuser.getEmail().equals(boardvo.getFk_email()) ) { 
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
            mv.setViewName("pppBoard/del.tiles3");
            // /Board/src/main/webapp/WEB-INF/views/tiles1/board/del.jsp 파일을 생성한다.
         }
         
         return mv;
      }
      
      // === 글삭제 페이지 완료하기 ===
      @RequestMapping(value="/pppdelEnd.yo", method= {RequestMethod.POST})
      public ModelAndView pppdelEnd(HttpServletRequest request, ModelAndView mv) {
       
         try {
            // 삭제해야할 글번호 및 사용자가 입력한 암호를 받아온다.
            String seq = request.getParameter("seq");
            String pw = request.getParameter("pw");
            
            BoardVO boardvo = new BoardVO();
            boardvo.setSeq(seq);
            boardvo.setPw(pw);
            
            int n = service.del(boardvo);
            
            if(n == 0) {
               mv.addObject("msg", "암호가 일치하지 않아 글 삭제가 불가합니다.");
            }
            else {
               mv.addObject("msg", "글삭제 성공!!");
            }
            
            mv.addObject("loc", request.getContextPath()+"/pshComplimentBoard.yo");
            mv.setViewName("msg");
         
          } catch (Throwable e) {
            e.printStackTrace();
         }
         
          return mv;
      }
      
      
      // 댓글쓰기 ===
      @RequestMapping(value="/pppaddComment.yo", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")      
      @ResponseBody
      public String pppaddComment(CommentVO commentvo) {
         
         String jsonStr = ""; 
         
         // 댓글쓰기(*** Ajax 로 처리 ***)
         try {
             int n = service.addComment(commentvo);
             
             if(n==1) {
                  // 댓글쓰기(insert) 및 
                  // 원게시물(tblBoard 테이블)에 댓글의 갯수(1씩 증가) 증가(update)가 성공했다라면
                  
                List<CommentVO> commentList = service.getCommentList(commentvo.getParentSeq());
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
      
      // 검색어 입력시 자동글 완성하기 3 === 
      @RequestMapping(value="/pppwordSearchShow.yo", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8") 
      @ResponseBody
      public String pppwordSearchShow(HttpServletRequest request) {
         
         String searchType = request.getParameter("searchType");
         String searchWord = request.getParameter("searchWord");
         
         HashMap<String,String> paraMap = new HashMap<String,String>();
         paraMap.put("searchType", searchType);
         paraMap.put("searchWord", searchWord);
         
         List<String> wordList = service.wordSearchShow(paraMap);
         
         JSONArray jsonArr = new JSONArray();
         
         if(wordList != null) {
            for(String word : wordList) {
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("word", word);
               jsonArr.put(jsonObj);
            }
         }
         
         String result = jsonArr.toString();
         
         return result;
      }
      
    // 첨부파일 다운로드 받기 =====
      @RequestMapping(value="/pppdownload.yo", method={RequestMethod.GET}) 
      public void login_pppdownload(HttpServletRequest request, HttpServletResponse response) {
         
         String seq = request.getParameter("seq"); 
         // 첨부파일이 있는 글번호
         
         // 첨부파일이 있는 글번호에서 
         // 201907250930481985323774614.jpg 처럼
         // 이러한 fileName 값을 DB에서 가져와야 한다. 
         // 또한 orgFileName 값도 DB에서 가져와야 한다.
         
         BoardVO vo = service.getViewWithNoAddCount(seq);
         // 조회수 증가 없이 1개 글 가져오기
         // 먼저 board.xml 에 가서 id가 getView 인것에서
         // select 절에 fileName, orgFilename, fileSize 컬럼을
         // 추가해주어야 한다.
         
         String fileName = vo.getFileName(); 
         // 201907250930481985323774614.jpg 와 같은 것이다.
         // 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
         
         String orgFilename = vo.getOrgFilename(); 
         // 강아지.png 처럼 다운받을 사용자에게 보여줄 파일명.
         
         
         // 첨부파일이 저장되어 있는 
         // WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
         // 이 경로는 우리가 파일첨부를 위해서
         //    /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
         // WAS 의 webapp 의 절대경로를 알아와야 한다. 
         HttpSession session = request.getSession();
         
         String root = session.getServletContext().getRealPath("/"); 
         String path = root + "resources"+File.separator+"files";
         // path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
         
         // **** 다운로드 하기 **** //
         // 다운로드가 실패할 경우 메시지를 띄워주기 위해서
         // boolean 타입 변수 flag 를 선언한다.
         boolean flag = false;
         
         flag = filemanager.doFileDownload(fileName, orgFilename, path, response);
         // 다운로드가 성공이면 true 를 반납해주고,
         // 다운로드가 실패이면 false 를 반납해준다.
         
         if(!flag) {
            // 다운로드가 실패할 경우 메시지를 띄워준다.
            
            response.setContentType("text/html; charset=UTF-8"); 
            PrintWriter writer = null;
            
            try {
               writer = response.getWriter();
               // 웹브라우저상에 메시지를 쓰기 위한 객체생성.
            } catch (IOException e) {
               
            }
            
            writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
            
         }
          
      } // end of void download(HttpServletRequest req, HttpServletResponse res)---------
        
      
      // ==== #스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
         @RequestMapping(value="/image/pppmultiplePhotoUpload.yo", method={RequestMethod.POST})
         public void pppmultiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
             
         /*
            1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
            >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
                 우리는 WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
          */
            
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
                   
                 // System.out.println(">>>> 확인용 filename ==> " + filename); 
                 // >>>> 확인용 filename ==> berkelekle%ED%8A%B8%EB%9E%9C%EB%94%9405.jpg
                   
                   InputStream is = req.getInputStream();
                /*
                   요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때,
                   혹은 이름 없이 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 
                   이러한 형태의 값을 'payload body'라고 하는데 요청 바디에 직접 쓰여진다 하여 'request body post data'라고도 한다.

                          서블릿에서 payload body는 Request.getParameter()가 아니라 
                       Request.getInputStream() 혹은 Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다.    
                */
                   String newFilename = filemanager.doFileUpload(is, filename, path);
                
               int width = filemanager.getImageWidth(path+File.separator+newFilename);
               
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
   
}