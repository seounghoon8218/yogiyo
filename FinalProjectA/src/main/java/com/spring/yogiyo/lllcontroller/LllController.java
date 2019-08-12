package com.spring.yogiyo.lllcontroller;

import java.io.File;
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
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.lllmodel.CommentVO;
import com.spring.yogiyo.lllmodel.MoonVO;
import com.spring.yogiyo.lllservice.InterLllService;

//=== #30. 컨트롤러 선언 ===
@Controller
public class LllController {
   
   // === #35. 의존객체 주입하기(DI: Dependency Injection) ===
      @Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
      private InterLllService service;
      
      // === #137. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI: Dependency Injection)
      @Autowired
      private FileManager fileManager;
      
      
      // === #51. 게시판 글쓰기 폼페이지 요청 ===
      @RequestMapping(value="/moonadd.yo", method= {RequestMethod.GET})
      public ModelAndView requireLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
         
         // === #124. 답변글쓰기가 추가된 경우 ===
         String fk_seq = request.getParameter("fk_seq");
         String groupno = request.getParameter("groupno");
         String depthno = request.getParameter("depthno");
         
         mv.addObject("fk_seq", fk_seq);
         mv.addObject("groupno", groupno);
         mv.addObject("depthno", depthno);
         //////////////////////////////////////////////////
         
         mv.setViewName("lllboard/moonadd.tiles3");
         
         return mv;
      }
      
      
      // === #53. 게시판 글쓰기 완료 요청 ===
      @RequestMapping(value="/moonaddEnd.yo", method= {RequestMethod.GET}) 
//      public ModelAndView addEnd(moonvo moonvo, ModelAndView mv) {
   /*
        === #133. 파일첨부가 된 글쓰기 이므로
                  먼저 위의  public ModelAndView addEnd(moonvo moonvo, ModelAndView mv) { 을 주석처리한 이후에 아래와 같이 한다.
           MultipartHttpServletRequest mrequest 를 사용하기 위해서는 
                  먼저  /Board/src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에서
           multipartResolver 를 bean 으로 등록해주어야 한다.!!!                        
    */
      public String addEnd(MoonVO moonvo, HttpServletRequest request) {   
       
         
         String content = moonvo.getContent();
         
         // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
         moonvo.setContent(MyUtil.replaceParameter(content));        
         
         content = moonvo.getContent().replaceAll("\r\n", "<br/>");
         moonvo.setContent(content);
               
      //   int n = service.add(moonvo);
         
      //   === #138. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단 호출 ===
      //      먼저 위의 int n = service.add(moonvo); 부분을 주석처리하고서 아래처럼 한다.
         
         int n = 0;
         
            n = service.moonadd(moonvo);
         
         
      //   mv.addObject("n", n);
         request.setAttribute("n", n);
      //   mv.setViewName("board/addEnd.tiles1");
         
         return "lllboard/moonaddEnd.tiles3";
      }
      
      // === #57. 글목록 보기 페이지 요청 ===
      @RequestMapping(value="/moonlist.yo", method= {RequestMethod.GET})
      public ModelAndView list(HttpServletRequest request, ModelAndView mv) {
      
         List<MoonVO> moonList = null;
         
         // 페이징 처리를 안한 검색어가 없는 전체 글목록 보여주기
      //   boardList = service.boardListNoSearch();
         
      // === #97. 페이징 처리를 안한 검색어가 있는 전체 글목록 보여주기 === //
      /*
         String searchType = request.getParameter("searchType");
         String searchWord = request.getParameter("searchWord");
         
         if(searchWord == null || searchWord.trim().isEmpty()) {
            searchWord = "";
         }
         
         HashMap<String,String> paraMap = new HashMap<String,String>();
         paraMap.put("searchType", searchType);
         paraMap.put("searchWord", searchWord);
         
         boardList = service.boardListSearch(paraMap);
         
         if(!"".equals(searchWord)) {
            mv.addObject("paraMap", paraMap);
         }
      */   
      //////////////////////////////////////////////////////
         
      // === #109. 페이징 처리를 한 검색어가 있는 전체 글목록 보여주기 === //      
      // 페이징 처리를 통한 글목록 보여주기는 예를 들어 3페이지의 내용을 보고자 한다라면
      //   /list.action?currentShowPageNo=3 와 같이 해주어야 한다.
         
      String str_currentShowPageNo = request.getParameter("currentShowPageNo"); 
      
      int totalCount = 0;         // 총게시물 건수
      int sizePerPage = 10;        // 한 페이지당 보여줄 게시물 수
      int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
      int totalPage = 0;          // 총 페이지 수(웹브라우저상에 보여줄 총 페이지 갯수, 페이지바)
      
      int startRno = 0;           // 시작 행번호
      int endRno   = 0;           // 끝 행 번호
      
      
      String searchType = request.getParameter("searchType");
      String searchWord = request.getParameter("searchWord");
      
      if(searchWord == null || searchWord.trim().isEmpty()) {
         searchWord = "";
      }
      
      HashMap<String,String> paraMap = new HashMap<String,String>();
      paraMap.put("searchType", searchType);
      paraMap.put("searchWord", searchWord);
      
      // 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
      // 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
      
      // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
      if("".equals(searchWord)) {
         totalCount = service.getTotalCountWithNOsearch();
      }
      
      // 검색조건이 있을 경우의 총 게시물 건수(totalCount)
      else {
         totalCount = service.getTotalCountWithSearch(paraMap);
      }
      
      totalPage = (int) Math.ceil( (double)totalCount/sizePerPage ); 
      
      
      if(str_currentShowPageNo == null) {
         // 게시판에 보여지는 초기화면
         
         currentShowPageNo = 1;
         // 즉, 초기화면은 /list.action?currentShowPageNo=1 로 한다는 말이다.
      }
      else {
         
         try {
            currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
            
            if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
               currentShowPageNo = 1;
            }
         } catch(NumberFormatException e) {
            currentShowPageNo = 1;
         }
      }
      
      // **** 가져올 게시글의 범위를 구한다.(공식임!!!) **** 
      /*
           currentShowPageNo      startRno     endRno
          --------------------------------------------
               1 page        ===>    1           5
               2 page        ===>    6           10
               3 page        ===>    11          15
               4 page        ===>    16          20
               ......                ...         ...
       */
      
      startRno = ((currentShowPageNo - 1 ) * sizePerPage) + 1;
      endRno = startRno + sizePerPage - 1; 
      
      paraMap.put("startRno",String.valueOf(startRno));
      paraMap.put("endRno",String.valueOf(endRno));
      
      moonList = service.boardListWithPaging(paraMap);
      // 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)
      
      if(!"".equals(searchWord)) {
         mv.addObject("paraMap", paraMap);
      }
      
      
      // === #119. 페이지바 만들기 === 
      String pagebar = "<ul>";
      
      String url = "moonlist.yo";
      int blockSize = 10;
         
      pagebar += MyUtil.makePageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, searchType, searchWord);
      pagebar += "</ul>";
      mv.addObject("pagebar", pagebar);
      
      String gobackURL = MyUtil.getCurrentURL(request);
      // 페이징 처리되어진 후 특정글제목을 클릭하여 상세내용을 본 이후
      // 사용자가 목록보기 버튼을 클릭했을때 돌아갈 페이지를 알려주기 위해
      // 현재 페이지 주소를 뷰단으로 넘겨준다.
      
//      System.out.println("~~~~~gobackURL : " + gobackURL);
      mv.addObject("gobackURL", gobackURL);
    /////////////////////////////////////////////////////   
         
         // === #68. 글조회수(readCount)증가 (DML문 update)는
         //          반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만
         //          증가되고, 웹브라우저에서 새로고침(F5)을 했을 경우에는
         //          증가가 되지 않도록 해야 한다.
         //          이것을 하기 위해서는 session 을 사용하여 처리하면 된다.
         HttpSession session = request.getSession();
         session.setAttribute("readCountPermission", "yes"); 
         /*
            session 에  "readCountPermission" 키값으로 저장된 value값은 "yes" 이다.
            session 에  "readCountPermission" 키값에 해당하는 value값 "yes"를 얻으려면 
               반드시 웹브라우저에서 주소창에 "/list.action" 이라고 입력해야만 얻어올 수 있다. 
          */
         
         ///////////////////////////////////////////////////
         
         mv.addObject("moonList", moonList);
         mv.setViewName("lllboard/moonList.tiles3");
         
         return mv;
      }
      
      
         
         
      // === #61. 글1개를 보여주는 페이지 요청 ===
         @RequestMapping(value="/moonview.yo", method= {RequestMethod.GET})   
         public ModelAndView view(HttpServletRequest request, ModelAndView mv) {
            
            // 조회하고자 하는 글번호 받아오기
            String seq = request.getParameter("seq");
            
            String gobackURL = request.getParameter("gobackURL");
            mv.addObject("gobackURL", gobackURL);
            
            HttpSession session = request.getSession();
            MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
            
            String email = null; 
            
            if(loginuser != null) {
               email = loginuser.getEmail(); 
               // 로그인 되어진 사용자의 userid 
            }
            
            // === #67. !!! 글1개를 보여주는 페이지 요청은 select 와 함께 
            //     DML문(지금은 글조회수 증가인 update문)이 포함되어져 있다.
            //     이럴경우 웹브라우저에서 페이지 새로고침(F5)을 했을때 DML문이 실행되어
            //     매번 글조회수 증가가 발생한다.
            //     그래서 우리는 웹브라우저에서 페이지 새로고침(F5)을 했을때는
            //     단순히 select만 해주고 DML문(지금은 글조회수 증가인 update문)은 
            //     실행하지 않도록 해주어야 한다. !!! === //
            
            MoonVO moonvo = null;
            
            // 위의 글목록보기 #68. 에서 session.setAttribute("readCountPermission", "yes"); 해두었다.
            if("yes".equals(session.getAttribute("readCountPermission")) ) {
               // 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
            
               moonvo = service.getView(seq, email);
               // 글조회수 증가와 함께 글1개 조회를 해주는 것
               
               session.removeAttribute("readCountPermission");
               // 중요함!! session 에 저장된 readCountPermission을 삭제한다.
            }
            else {
              moonvo = service.getViewWithNoAddCount(seq);
              // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것
            }
            
            // === #92. 댓글쓰기가 있는 게시판 === //
            List<CommentVO> commentList = service.getCommentList(seq); 
               // 원게시물에 딸린 댓글들을 조회해오는 것
            mv.addObject("commentList", commentList);
            ///////////////////////////////////////////
            
            mv.addObject("moonvo", moonvo);
            mv.setViewName("lllboard/moonview.tiles3");
            
            return mv;
         }     
   
         // === #70. 글수정 페이지 요청 ===
         @RequestMapping(value="/moonedit.yo", method= {RequestMethod.GET})
         public ModelAndView requireLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
            
            // 글 수정해야할 글번호 가져오기
            String seq = request.getParameter("seq");
            
            // 글 수정해야할 글1개 내용 가져오기
            MoonVO moonvo = service.getViewWithNoAddCount(seq);
            // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
            
            HttpSession session = request.getSession();
            MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
            
            if( !loginuser.getEmail().equals(moonvo.getFk_email()) ) { 
               String msg = "다른 사용자의 글은 수정이 불가합니다.";
               String loc = "javascript:history.back()";
               
               mv.addObject("msg", msg);
               mv.addObject("loc", loc);
               mv.setViewName("msg");
            }
            else {
               // 자신의 글을 수정할 경우 
               // 가져온 1개글을 글수정할 폼이 있는 view 단으로 보내준다.
               mv.addObject("moonvo", moonvo);
               
               mv.setViewName("lllboard/moonedit.tiles3");
            }
            
            return mv;
         }
         

         // === #71. 글수정 페이지 완료하기 ===
         @RequestMapping(value="/mooneditEnd.yo", method= {RequestMethod.POST})
         public ModelAndView requireLogin_editEnd(HttpServletRequest request, HttpServletResponse response, MoonVO moonvo, ModelAndView mv) {
            
            String content = moonvo.getContent();
            
            // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
            content = MyUtil.replaceParameter(content);
            content = content.replaceAll("\r\n", "<br/>");
            
            moonvo.setContent(content);
            
            /* 글 수정을 하려면 원본글의 글암호와 수정시 입력해준 암호가 일치할때만
                    수정이 가능하도록 해야 한다. */
            int n = service.edit(moonvo);
            
            if(n == 0) {
               mv.addObject("msg", "암호가 일치하지 않아 글 수정이 불가합니다.");
            }
            else {
               mv.addObject("msg", "글수정 성공!!");
            }
            
            mv.addObject("loc", request.getContextPath()+"/view.yo?seq="+moonvo.getSeq());
            mv.setViewName("msg");
            
            return mv;
         }
   
      // === #77. 글삭제 페이지 요청 ===
         @RequestMapping(value="/moondel.yo", method= {RequestMethod.GET})
         public ModelAndView requireLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
         
            // 삭제해야할 글번호를 받아온다.
            String seq = request.getParameter("seq");
            String groupno = request.getParameter("groupno");
            
            // 삭제해야할 글1개 내용 가져오기
            MoonVO moonvo = service.getViewWithNoAddCount(seq);
            // 글조회수(readCount) 증가 없이 그냥 글1개만 가져오는 것
            
            HttpSession session = request.getSession();
            MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
            
            if( !loginuser.getEmail().equals(moonvo.getFk_email()) ) { 
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
               mv.setViewName("lllboard/moondel.tiles3");
               // /Board/src/main/webapp/WEB-INF/views/tiles1/board/del.jsp 파일을 생성한다.
            }
            
            return mv;
         }
         
         
         // === #78. 글삭제 페이지 완료하기 ===
         @RequestMapping(value="/moondelEnd.yo", method= {RequestMethod.POST})
         public ModelAndView delEnd(HttpServletRequest request, ModelAndView mv) {
          
            try {
               // 삭제해야할 글번호 및 사용자가 입력한 암호를 받아온다.
               String seq = request.getParameter("seq");
               String pw = request.getParameter("pw");
               
               MoonVO moonvo = new MoonVO();
               moonvo.setSeq(seq);
               moonvo.setPw(pw);
               
               int n = service.del(moonvo);
               
               if(n == 0) {
                  mv.addObject("msg", "암호가 일치하지 않아 글 삭제가 불가합니다.");
               }
               else {
                  mv.addObject("msg", "글삭제 성공!!");
               }
               
               mv.addObject("loc", request.getContextPath()+"/moonlist.yo");
               mv.setViewName("msg");
            
             } catch (Throwable e) {
               e.printStackTrace();
            }
            
             return mv;
         }
   
      // === #85. 댓글쓰기 ===
         @RequestMapping(value="/addComment.yo", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")      
         @ResponseBody
         public String addComment(CommentVO commentvo) {
            
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
   
}