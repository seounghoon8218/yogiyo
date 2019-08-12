package com.spring.yogiyo.ooocontroller;

import java.io.File;
import java.io.IOException;
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

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.ooomodel.InteroooDAO;
import com.spring.yogiyo.ooomodel.oooBoardVO;
import com.spring.yogiyo.ooomodel.oooCommentVO;
import com.spring.yogiyo.ooomodel.oooDAO;
import com.spring.yogiyo.ooomodel.oooVO;
import com.spring.yogiyo.oooservice.Interoooservice;

@Controller
public class oooController {

   // === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
   @Autowired
   private Interoooservice service;

   @Autowired
   private FileManager fileManager;
   
   // === #36. 매장 등록 페이지 요청 ====
   @RequestMapping(value = "/shopregister.yo", method = { RequestMethod.GET })
   public ModelAndView shopregister(ModelAndView mv, HttpServletRequest request) {

      HttpSession session = request.getSession();
      // MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");

      /*
       * if(loginuser ==null || (loginuser != null)) {
       * 
       * String msg= "관리자만 접근이 가능합니다"; String loc = "javascript:history.back()";
       * 
       * mv.addObject("msg", msg); mv.addObject("loc", loc);
       * 
       * mv.setViewName("msg");
       * 
       * }
       */
      // else {

      InteroooDAO odao = new oooDAO();

      int masterno = service.getMasterNo(); // 사업자 번호 가져오기
      List<HashMap<String, String>> shopCategoryList = service.selectShopCategory(); // 업종 카테고리 가져오기

      mv.addObject("masterno", masterno);
      mv.addObject("shopCategoryList", shopCategoryList);
      mv.setViewName("register/shopregister.tiles3");
      // }
      return mv;

   } // end shopregister

   @RequestMapping(value = "/shopregisterEnd.yo", method = { RequestMethod.POST })
   public ModelAndView shopregisterEnd(oooVO ovo, ModelAndView mv, HttpServletRequest request) {

      String wonsanji = ovo.getWonsanji();

      wonsanji = MyUtil.replaceParameter(wonsanji);
      wonsanji = ovo.getWonsanji().replaceAll("\r\n", "<br>");
      ovo.setWonsanji(wonsanji);
   
   

      int n = service.addshop(ovo); // 매장 등록 완료
   

      mv.addObject("n", n);
      mv.setViewName("register/shopregisterEnd.tiles3");

      return mv;
   } // end shopregisterEnd

   // 게시판 글 목록 보여주기
   @RequestMapping(value = "/oooBoardList.yo", method = { RequestMethod.GET })
   public ModelAndView oooBoardList(ModelAndView mv, HttpServletRequest request) {

      List<oooBoardVO> boardList = null;

      String str_currentShowPageNo = request.getParameter("currentShowPageNo");

      int totalCount = 0; // 총게시물 건수
      int sizePerPage = 10; // 한 페이지당 보여줄 게시물 수
      int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
      int totalPage = 0; // 총 페이지 수(웹브라우저상에 보여줄 총 페이지 갯수, 페이지바)

      int startRno = 0; // 시작 행번호
      int endRno = 0; // 끝 행 번호

      String searchType = request.getParameter("searchType");
      String searchWord = request.getParameter("searchWord");

      if (searchWord == null || searchWord.trim().isEmpty()) {
         searchWord = "";
      }

      HashMap<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("searchType", searchType);
      paraMap.put("searchWord", searchWord);

      if ("".equals(searchWord)) {
         totalCount = service.getTotalCountWithNOsearch();
      }

      else {
         totalCount = service.getTotalCountWithSearch(paraMap);
      }

      totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

      if (str_currentShowPageNo == null) {
         // 게시판에 보여지는 초기화면

         currentShowPageNo = 1;
         // 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
      } else {

         try {
            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);

            if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
               currentShowPageNo = 1;
            }
         } catch (NumberFormatException e) {
            currentShowPageNo = 1;
         }
      }

      // **** 가져올 게시글의 범위를 구한다.(공식임!!!) ****
      /*
       * currentShowPageNo startRno endRno
       * -------------------------------------------- 1 page ===> 1 5 2 page ===> 6 10
       * 3 page ===> 11 15 4 page ===> 16 20 ...... ... ...
       */

      startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
      endRno = startRno + sizePerPage - 1;

      paraMap.put("startRno", String.valueOf(startRno));
      paraMap.put("endRno", String.valueOf(endRno));

      boardList = service.boardListWithPaging(paraMap);
      // 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)

      if (!"".equals(searchWord)) {
         mv.addObject("paraMap", paraMap);
      }

      // === #119. 페이지바 만들기 === //
      String pagebar = "<ul>";

      String url = "oooBoardList.yo";
      int blockSize = 10;

      pagebar += MyUtil.makePageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, searchType,
            searchWord);

      pagebar += "</ul>";

      mv.addObject("pagebar", pagebar);

      String gobackURL = MyUtil.getCurrentURL(request);
      mv.addObject("gobackURL", gobackURL);
      /////////////////////////////////////////////////////

      // === #68. 글조회수(readCount)증가 (DML문 update)는
      // 반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만
      // 증가되고, 웹브라우저에서 새로고침(F5)을 했을 경우에는
      // 증가가 되지 않도록 해야 한다.
      // 이것을 하기 위해서는 session 을 사용하여 처리하면 된다.
      HttpSession session = request.getSession();
      session.setAttribute("readCountPermission", "yes");
      /*
       * session 에 "readCountPermission" 키값으로 저장된 value값은 "yes" 이다. session 에
       * "readCountPermission" 키값에 해당하는 value값 "yes"를 얻으려면 반드시 웹브라우저에서 주소창에
       * "/list.action" 이라고 입력해야만 얻어올 수 있다.
       */

      ///////////////////////////////////////////////////

      mv.addObject("boardList", boardList);
      mv.setViewName("oooBoard/oooBoardList.tiles3");

      return mv;
   }

   // 게시판 글 목록 보여주기
   @RequestMapping(value = "/oooBoardAdd.yo", method = { RequestMethod.GET })
   public ModelAndView login_oooBoardAdd(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

      // === #124. 답변글쓰기가 추가된 경우 === //
      String fk_seq = request.getParameter("fk_seq");
      String groupno = request.getParameter("groupno");
      String depthno = request.getParameter("depthno");

      mv.addObject("fk_seq", fk_seq);
      mv.addObject("groupno", groupno);
      mv.addObject("depthno", depthno);
      ////////////////////////////////////////////////////////////////////

      mv.setViewName("oooBoard/oooBoardAdd.tiles3");

      return mv;
   }
   
   @RequestMapping(value = "/oooBoardAddEnd.yo", method = { RequestMethod.POST })
   public String addEnd(oooBoardVO oooboardvo, MultipartHttpServletRequest mrequest) {   


      MultipartFile attach = oooboardvo.getAttach();   
      if(!attach.isEmpty()) {
      
      
         HttpSession session = mrequest.getSession();
         String root = session.getServletContext().getRealPath("/");
         String path = root + "resources" + File.separator + "files";
      
         System.out.println("확인용==>" + path);
      
      
         String newFileName = "";
      
      
         byte[] bytes = null;
      
         
         long fileSize = 0;
      
         
         try {
            bytes = attach.getBytes();
         
            
            newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
            
            System.out.println("확인용!!==>" + newFileName);
            
            
            oooboardvo.setFileName(newFileName);
            oooboardvo.setOrgFilename(attach.getOriginalFilename());
            
            
            fileSize = attach.getSize();
            oooboardvo.setFileSize(String.valueOf(fileSize));
            
            
            
         } catch (Exception e) {
            
            e.printStackTrace();
         }
      }
        // ========= !!첨부파일이 있는지 없는지 알아오기 끝!! ========= 
      
      String content = oooboardvo.getContent();

      content = MyUtil.replaceParameter(content);

      
      oooboardvo.setContent(MyUtil.replaceParameter(content));

      content = oooboardvo.getContent().replaceAll("\r\n", "<br>");
      oooboardvo.setContent(content);
      
      
      
      int n = 0;
      if(attach.isEmpty()) {
         // 첨부파일이 없는 경우이라면
         n = service.add(oooboardvo);
      
      }
      else {
         // 첨부파일이 있는 경우라면
         n = service.add_withFile(oooboardvo);
      
      }
      
      mrequest.setAttribute("n", n);
      
   
      return "oooBoard/oooBoardAddEnd.tiles3";
   }
   
   // 글 1개를 보여주는 페이지 요청
   @RequestMapping(value = "/oooBoardView.yo", method = { RequestMethod.GET })
   public ModelAndView oooBoardView(HttpServletRequest request, ModelAndView mv) {
         
      String seq = request.getParameter("seq");
      String gobackURL = request.getParameter("gobackURL");
      mv.addObject("gobackURL", gobackURL);
      
      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
      
      String email = null;
      
      if(loginuser != null) {
         email = loginuser.getEmail();
      }
      
      oooBoardVO oooboardvo = null;
      
      if("yes".equals(session.getAttribute("readCountPermission"))) {
         
         oooboardvo = service.getView(seq, email);
         session.removeAttribute("readCountPermission");
      
      }else {
         
         oooboardvo = service.getViewWithNoAddCount(seq);
         // 글 조회수 증가없이 단순히 글조회만을 해주는 것
      }
      
      List<oooCommentVO> commentList = service.getCommentList(seq);
      mv.addObject("commentList", commentList);
      
      
      mv.addObject("oooboardvo", oooboardvo);
      mv.setViewName("oooBoard/oooBoardView.tiles3");
      
      return mv;
   }

   // 글 수정 페이지 요청 
   @RequestMapping(value = "/oooBoardEdit.yo", method = { RequestMethod.GET })
   public ModelAndView oooBoardEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
      
      String seq = request.getParameter("seq");
      
      oooBoardVO oooboardvo = service.getViewWithNoAddCount(seq);
      
      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
      
      if(!loginuser.getEmail().equals(oooboardvo.getFk_userid())) {
         String msg = "다른 사용자의 글은 수정이 불가능합니다!";
         String loc = "javascript:history.back()";
         
         mv.addObject("msg", msg);
         mv.addObject("loc", loc);
         mv.setViewName("msg");
         
      }
      else {
         
         mv.addObject("oooboardvo", oooboardvo);
         mv.setViewName("oooBoard/oooBoardEdit.tiles3");
      }
      
      
      return mv;
   }
   
   // 글 수정 페이지 완료하기
   @RequestMapping(value = "/oooBoardEditEnd.yo", method = { RequestMethod.POST })
   public ModelAndView login_editEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, oooBoardVO oooboardvo) {
      
      String content = oooboardvo.getContent();

      // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드
      content = MyUtil.replaceParameter(content);
      content = oooboardvo.getContent().replaceAll("\r\n", "<br>");

      oooboardvo.setContent(content);

      
      int n = service.edit(oooboardvo);
      if (n == 0) {
         mv.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");

      } else {
         mv.addObject("msg", "글 수정 성공!!");

      }
      mv.addObject("loc", request.getContextPath() + "/oooBoardView.yo?seq=" + oooboardvo.getSeq());
      mv.setViewName("msg");
      
   
      return mv;
   }
   
   // 글삭제 페이지 요청하기 
   @RequestMapping(value = "/oooBoardDel.yo", method = { RequestMethod.GET })
   public ModelAndView login_oooBoardDel(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, oooBoardVO oooboardvo) {
      // 삭제해야할 글번호를 받아온다.
      String seq = request.getParameter("seq");

      // 삭제해야할 글 1개 내용 가져오기
      oooboardvo = service.getViewWithNoAddCount(seq);

      // 글조회수 (readCount) 증가 없이 그냥 글 한개만 가져오는 것 //
      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

      if (!loginuser.getEmail().equals(oooboardvo.getFk_userid())) {
         String msg = "다른 사용자의 글은 삭제가 불가능 합니다!";
         String loc = "javascript:history.back()";

         mv.addObject("msg", msg);
         mv.addObject("loc", loc);
         mv.setViewName("msg");
      } else {
         // 자신의 글을 삭제할 경우
         // 글 삭제 시 입력한 암호가 글작성 시 입력해준 암호와 일치하는지 알아오도록 del.jsp 페이지로 넘긴다.

         mv.addObject("seq", seq);
         mv.setViewName("oooBoard/oooBoardDel.tiles3");
      }
      return mv;
   }
   
   // 글삭제 페이지 완료하기 
   @RequestMapping(value = "/oooBoardDelEnd.yo", method = { RequestMethod.POST })
   public ModelAndView oooBoardDelEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

      try {
         // 삭제해야할 글번호 및 사용자가 입력한 암호를 받아온다.
         String seq = request.getParameter("seq");
         String pw = request.getParameter("pw");

         oooBoardVO oooboardvo = new oooBoardVO();
         oooboardvo.setSeq(seq);
         oooboardvo.setPw(pw);

         int n;

         n = service.del(oooboardvo);

         if (n == 0) {
            mv.addObject("msg", "암호가 일치하지 않아 글 삭제가 불가합니다.");

         } else {
            mv.addObject("msg", "글 삭제 성공!!");

         }
         mv.addObject("loc", request.getContextPath() + "/oooBoardList.yo");
         mv.setViewName("msg");

      } catch (Throwable e) {
         e.printStackTrace();
      }

      return mv;
   }
   
   // === #85. 댓글 작성하기 === //
   @RequestMapping(value = "/oooBoardAddComment.yo", method = {RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
   @ResponseBody
   public String addComment(oooCommentVO ooocommentvo) {

      String jsonStr = "";

      // 댓글쓰기 (*** Ajax 로 처리 *** )

      try {
         int n = service.addComment(ooocommentvo);

         if (n == 1) {
            // 댓글쓰기(insert) 및 원게시물(tblBoard 테이블)에 댓글의 갯수(1씩 증가 update)증가가 성공했다라면

            // 원게시물에 딸린 댓글들을 조회해오는 것
            List<oooCommentVO> commentList = service.getCommentList(ooocommentvo.getParentSeq());

            JSONArray jsonArr = new JSONArray();

            for (oooCommentVO cmtvo : commentList) {
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
   
   
   // 검색어 입력시 자동글 완성하기
   @RequestMapping(value = "/oooBoardwordSearchShow.yo", method = {RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
   @ResponseBody
   public String wordSearchShow(HttpServletRequest request) {
      String searchType= request.getParameter("searchType");
      String searchWord = request.getParameter("searchWord");

      HashMap<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("searchType", searchType);
      paraMap.put("searchWord", searchWord);

      List<String> wordList = service.wordSearchShow(paraMap);

      JSONArray jsonArr = new JSONArray();

      if (wordList != null) { // 검색어가 해당되지않는 것일수도있기때문

         for (String word : wordList) {
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("word", word);
            jsonArr.put(jsonObj);

         }
      }
      String result = jsonArr.toString();
      return result;
   }   
   
   
   // 첨부파일 다운로드 받기 
   @RequestMapping(value="/oooBoardDownload.yo", method={RequestMethod.GET}) 
   public void requireLogin_download(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
      
      String seq = request.getParameter("seq");
      
      oooBoardVO oooboardvo = service.getViewWithNoAddCount(seq);
      
      String fileName = oooboardvo.getFileName();
      String orgFilename = oooboardvo.getOrgFilename(); 
      
      HttpSession session2 = request.getSession();
      
      
      String root = session.getServletContext().getRealPath("/"); 
      String path = root + "resources"+File.separator+"files";
      
      boolean flag = false;
      
      flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
      
      if(!flag) {
            
         response.setContentType("text/html; charset=UTF-8"); 
         PrintWriter writer = null;
         
         try {
            writer = response.getWriter();
            
         } catch (IOException e) {
            
         }
         writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
      }
      
      
      
   }
}