package com.spring.yogiyo.wwwcontroller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spring.common.MyUtil;
import com.spring.common.SHA256;
import com.spring.common.FileManager;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.wwwmodel.BoardVO;
import com.spring.yogiyo.wwwmodel.CommentVO;
import com.spring.yogiyo.wwwservice.InterWwwService;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

// === #30. 컨트롤러 선언 ===
@Controller
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
   그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. */
public class WwwController {
   
   //=== #34. 의존객체 주입하기(DI: Dependency Injection) ===
   @Autowired // Type에 따라 알아서 Bean 을 주입해준다.
   private InterWwwService service;
   
   // === #137. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기
	@Autowired
	private FileManager fileManager;
   
   // === 비번암호화
   public SHA256 as = null;
   
   // === #40. 로그인 폼 페이지 요청 ===
   @RequestMapping(value="/login.yo", method= {RequestMethod.GET})
   public ModelAndView login(ModelAndView mv) {
      
      mv.setViewName("login/loginform.tiles3");
      return mv;
   }
   
   // === #41. 로그인 처리하기 ===
   @RequestMapping(value="/loginEnd.yo", method= {RequestMethod.POST})
   public ModelAndView loginEnd(HttpServletRequest request, ModelAndView mv) {
      
      String email = request.getParameter("email");
      String pwd = request.getParameter("pwd");
      
      // 로그인할때 입력한 비번 암호화해서 비교하기 위해
      pwd = as.encrypt(pwd);
      
      HashMap<String, String> paraMap = new HashMap<String, String>();
      paraMap.put("email", email);
      paraMap.put("pwd", pwd);
      
      MemberVO loginuser = service.getLoginMember(paraMap);
      //////////////////////////////////////////////////////////
      
      HttpSession session = request.getSession();
      
      if(loginuser == null) {
         String msg = "아이디 또는 암호가 틀립니다.";
         String loc = "javascript:history.back()";
         
         mv.addObject("msg", msg);
         mv.addObject("loc", loc);
         
         mv.setViewName("msg");
         // /Board/src/main/webapp/WEB-INF/views/msg.jsp 파일을 생성한다.
      }
      else {
         if(loginuser.isIdleStatus() == true) { // boolean값은 is로 씀
            // 로그인을 한지 1년 지나서 휴면상태로 빠진 경우
            String msg = "접속한지 1년이 지나 휴면상태로 빠졌습니다. ";
            
         // 로그인을 한지 1년이 지났으면 로그인을 하지 못하도록 막아버리게 뒤로만 가는 것 // 
         //   String loc = "javascript:history.back()";
            
            //// 로그인을 한지 1년이 지났지만 정상적으로 로그인 처리를 해주는 것 ///
            String loc = "/yogiyo/index.yo";
            session.setAttribute("loginuser", loginuser);
            
            mv.addObject("msg", msg);
            mv.addObject("loc", loc);
            
            mv.setViewName("msg"); 
         }
         else {
            
            if(loginuser.isRequirePwdChange() == true) {
               // 암호를 최근 3개월 동안 변경하지 않은 경우
               session.setAttribute("loginuser", loginuser);
               
               String msg = "암호를 3개월동안 변경하지 않았습니다.";
               String loc = request.getContextPath()+"/myinfo.yo";
               
               mv.addObject("msg", msg);
               mv.addObject("loc", loc);
               
               mv.setViewName("msg");
            }
            else {
               // 아무런 이상없이 로그인 하는 경우
               session.setAttribute("loginuser", loginuser);
               
               mv.setViewName("login/loginEnd.tiles3");
               // /Board/src/main/webapp/WEB-INF/views/tiles1/login/loginEnd.jsp 파일을 생성한다.
            }
         }
      }
      
      return mv;
      
   }
   @RequestMapping(value="/myinfo.yo", method= {RequestMethod.GET})
   public String myinfo() {
      return "login/myinfo.tiles3";
      // /Board/src/main/webapp/WEB-INF/views/tiles1/login/myinfo.jsp 파일을 생성한다.
   }
   
   // === #50. 로그아웃 처리하기 ===
   @RequestMapping(value="/logout.yo", method= {RequestMethod.GET})
   public ModelAndView logout(ModelAndView mv, HttpServletRequest request) {
      
      HttpSession session = request.getSession();
      session.invalidate();
      
      String msg = "로그아웃 되었습니다.";
      String loc = request.getContextPath()+"/index.yo";
      
      mv.addObject("msg", msg);
      mv.addObject("loc", loc);
      
      mv.setViewName("msg");
      return mv;
   }
   
   // 회원가입 폼 보여주기
   @RequestMapping(value="/register.yo" , method= {RequestMethod.GET})
   public ModelAndView register(ModelAndView mv) {
      mv.setViewName("register/register.tiles3");
      return mv;
   }
   
   // === #41. 회원가입 처리하기 ===
   @RequestMapping(value="/registerEnd.yo", method= {RequestMethod.POST})
   public String registerEnd(MemberVO membervo) {
      service.RegisterMember(membervo);
      return "register/registerEnd.tiles3";
   }
   
   // 회원탈퇴 폼 보여주기
   @RequestMapping(value="/memberDel.yo" , method= {RequestMethod.GET})
   public ModelAndView requireLogin_MemberDel(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, HttpSession session, MemberVO membervo) {
	   mv.setViewName("register/memberDel.tiles3");
	   return mv;
   }
   
   // 회원탈퇴 처리하기
   @RequestMapping(value="/memberDelEnd.yo" , method= {RequestMethod.POST})
   public ModelAndView memberDelEnd(ModelAndView mv, HttpSession session, MemberVO membervo, HttpServletRequest request) {
	   MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	   String pwd = request.getParameter("pwd");
	   pwd = as.encrypt(pwd);
	   if( !loginuser.getPwd().equals(as.encrypt(membervo.getPwd())) ) { 
		   String msg = "비밀번호가 틀렸습니다.";
		   String loc = "javascript:history.back()";
		   
		   mv.addObject("msg", msg);
		   mv.addObject("loc", loc);
		   mv.setViewName("msg");
		   return mv;
	   }
	   else {
		   service.memberDelEnd(membervo);
		   
		   String msg = "회원탈퇴 되었습니다.";
		   String loc = "/yogiyo/index.yo";
		   
		   session.removeAttribute("loginuser");
		   
		   mv.addObject("msg", msg);
		   mv.addObject("loc", loc);
		   mv.setViewName("msg");
		   return mv;
		   
	   }
   }
   
   // === 이메일 중복확인 ===
   @RequestMapping(value="/emailcheck.yo", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8")
   @ResponseBody
   public String emailcheck(HttpServletRequest request){
      
      Gson gson = new Gson();
      
      String email = request.getParameter("email");
      int n = service.selectUserID(email);
      
      JsonObject jsonobj = new JsonObject();
      
      jsonobj.addProperty("n", n);
      
      return gson.toJson(jsonobj);
   }
   
   // 내정보수정 폼 보여주기
   @RequestMapping(value="/editMember.yo" , method= {RequestMethod.GET})
   public ModelAndView requireLogin_editMember(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
      mv.setViewName("login/editMember.tiles3");
      return mv;
   }
   
   // === 내정보수정 처리하기 ===
   @RequestMapping(value="/editMember.yo", method= {RequestMethod.POST})
   public ModelAndView editMemberEnd(ModelAndView mv, MemberVO membervo, HttpServletRequest request) {
      String idx = request.getParameter("idx"); 
      if(idx == null) idx="";
      
      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

      if( !idx.equals(String.valueOf(loginuser.getIdx()) ) ) {
         // 로그인을 했지만 로그인한 자신의 정보를 수정하는 것이 아니라
         // 다른 사용자의 정보를 수정하려고 접근한 경우
         String msg = "다른 사용자의 정보 변경 불가!!";
         String loc = "javascript:history.back()";
         
         mv.addObject("msg", msg);
          mv.addObject("loc", loc);
            
          mv.setViewName("msg");
          return mv;
      }
      else {
         // 로그인을 해서 자신의 정보를 수정하려고 한 경우
         mv.addObject("membervo", membervo);
         
         mv.setViewName("login/editMember.tiles3");
      }
      return mv;
   }
   // === 수정 페이지 완료하기 ===
   @RequestMapping(value="/editMemberEnd.yo", method= {RequestMethod.POST})
   public ModelAndView editMemberEnd(MemberVO membervo, HttpServletRequest request, ModelAndView mv) {

      int result = service.editMember(membervo);
      
      if(result == 0) {
         mv.addObject("msg","회원수정이 불가합니다.");
      }
      else {
         mv.addObject("msg","내 정보수정 성공.");
      }
      mv.addObject("loc",request.getContextPath()+"/editMember.yo");
      mv.setViewName("msg");
      
      return mv;
   }
   
   // 인증번호 요청
   @ResponseBody
   @RequestMapping(value = "/sendSMS.yo", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
   public String sendSMS(String userPhoneNumber, HttpServletRequest request) throws Exception { // 휴대폰 문자보내기
      
      Gson gson = new Gson();
      
      String email2 = request.getParameter("email2");
      String tel2 = request.getParameter("tel2");
      
      System.out.println("1."+email2);
      System.out.println("2."+tel2);
      
      HashMap<String, String> map = new HashMap<String, String>(); 
      map.put("email", email2);
      map.put("tel", tel2);
      int pwdOK = service.pwdSearch(map);
      System.out.println("3."+pwdOK);
      
      if(pwdOK == 1) {
      
         String api_key = "NCS5EEAEWCJIHGYZ";
          String api_secret = "AMVBII8BXMUAVNPR2WEOT945LVQKIZM3";
          Message coolsms = new Message(api_key, api_secret);
   
          Random rnd = new Random();
         
         String certificationCode = "";
         //certificationCode ==> "vcfhn0983154";
         
         char randchar = ' ';
         for (int i = 0; i < 5; i++) {
            
                /*min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면
                int rndnum = rnd.nextInt(max - min +1) + min;
                영문 소문자 'a'부터 'z'까지 중 랜덤하게 1개를 만든다.*/
             
            
            randchar = (char) (rnd.nextInt('z' - 'a' +1) + 'a');
            certificationCode += randchar;
         }
         
         int randnum = 0;
         for (int i = 0; i < 7; i++) {
            randnum = rnd.nextInt(9-0+1);
            certificationCode += randnum;
         }
          
          // 4 params(to, from, type, text) are mandatory. must be filled
          HashMap<String, String> params = new HashMap<String, String>();
          params.put("to", tel2); // 수신번호
          params.put("from", "01039482031"); // 발신번호
          params.put("type", "SMS"); // Message type ( SMS, LMS, MMS, ATA )
          params.put("text", "인증번호 : " + certificationCode); // 문자내용    
          params.put("app_version", "JAVA SDK v2.2"); // application name and version
          
          
          HttpSession session = request.getSession();
          session.setAttribute("certificationCode", certificationCode);
          
          /*   Optional parameters for your own needs
          params.put("mode", "test"); // 'test' 모드. 실제로 발송되지 않으며 전송내역에 60 오류코드로 뜹니다. 차감된 캐쉬는 다음날 새벽에 충전 됩니다.
          params.put("image", "desert.jpg"); // image for MMS. type must be set as "MMS"
          params.put("image_encoding", "binary"); // image encoding binary(default), base64 
          params.put("delay", "10"); // 0~20사이의 값으로 전송지연 시간을 줄 수 있습니다.
          params.put("force_sms", "true"); // 푸시 및 알림톡 이용시에도 강제로 SMS로 발송되도록 할 수 있습니다.
          params.put("refname", ""); // Reference name
          params.put("country", "KR"); // Korea(KR) Japan(JP) America(USA) China(CN) Default is Korea
          params.put("sender_key", "5554025sa8e61072frrrd5d4cc2rrrr65e15bb64"); // 알림톡 사용을 위해 필요합니다. 신청방법 : http://www.coolsms.co.kr/AboutAlimTalk
          params.put("template_code", "C004"); // 알림톡 template code 입니다. 자세한 설명은 http://www.coolsms.co.kr/AboutAlimTalk을 참조해주세요. 
          params.put("datetime", "20140106153000"); // Format must be(YYYYMMDDHHMISS) 2014 01 06 15 30 00 (2014 Jan 06th 3pm 30 00)
          params.put("mid", "mymsgid01"); // set message id. Server creates automatically if empty
          params.put("gid", "mymsg_group_id01"); // set group id. Server creates automatically if empty
          params.put("subject", "Message Title"); // set msg title for LMS and MMS
          params.put("charset", "euckr"); // For Korean language, set euckr or utf-8
          params.put("app_version", "Purplebook 4.1") // 어플리케이션 버전
   */        
         
          
          try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
          } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
          }
          
          
      }
      JsonObject jsonobj = new JsonObject();
      
      jsonobj.addProperty("pwdOK", pwdOK);
   
      return gson.toJson(jsonobj);
   }   
   
   // 인증번호 완료 페이지
   @RequestMapping(value = "/newPwd.yo", method = RequestMethod.POST)
   public ModelAndView newPwd(HttpServletRequest request, ModelAndView mv, MemberVO membervo){
	   
	    String email = request.getParameter("email2");
		String cer = request.getParameter("cer");
		HttpSession session =  request.getSession();
		String certificationCode = (String) session.getAttribute("certificationCode");
		String loc = "";
		
		if(certificationCode== null ) {
			certificationCode = "";
		}
		else if ( email==null ) {
			email = "";
		}
		else if (cer==null) {
			cer = "";
		}
		
		System.out.println(certificationCode);
		System.out.println(email);
		System.out.println(cer);
		
		if(certificationCode.equals(cer)) {
			
			mv.addObject("email", email);
			
			   mv.setViewName("login/newPwd.tiles3");
			   session.removeAttribute("certificationCode");
			   return mv;		   
		}
		else {
			 String msg = "발급된 인증코드가 아닙니다. 인증코드를 다시 발급받으세요!!";
			  loc = "javascript:history.back()";
			   
			   mv.addObject("msg", msg);
			   mv.addObject("loc", loc);
			   mv.setViewName("msg");
			   return mv;
		}
   }
   
   // 인증번호 완료 페이지
   @RequestMapping(value = "/newPwdEnd.yo", method = RequestMethod.POST)
   public ModelAndView newPwdEnd(HttpServletRequest request, ModelAndView mv, MemberVO membervo){
	   int result = service.newPwdEnd(membervo);
	      
	      if(result == 0) {
	         mv.addObject("msg","비밀번호수정이 불가합니다.");
	         mv.addObject("loc",request.getContextPath()+"/newPwd.yo");
	      }
	      else {
	         mv.addObject("msg","새 비밀번호수정 성공.");
	         mv.addObject("loc",request.getContextPath()+"/login.yo");
	      }
	      mv.setViewName("msg");
	      
	      return mv;
   }
   
   // === 자유게시판 ===
   @RequestMapping(value="/freePage.yo", method= {RequestMethod.GET})
   public ModelAndView freePage(ModelAndView mv, HttpServletRequest request) {
	   
	   List<BoardVO> boardList = null;
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
			totalCount = service.getTotalCountWithNOSearch();
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
		
		boardList = service.boardListWithPaging(paraMap);
		// 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것)
		
		if(!"".equals(searchWord)) {
			mv.addObject("paraMap", paraMap);
		}
		
		
		// === #119. 페이지바 만들기 === 
		String pagebar = "<ul>";
		
		String url = "list.action";
		int blockSize = 10;
			
		pagebar += MyUtil.makePageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, searchType, searchWord);
		pagebar += "</ul>";
		mv.addObject("pagebar", pagebar);
		
		String gobackURL = MyUtil.getCurrentURL(request);
		// 페이징 처리되어진 후 특정글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.
		
//		System.out.println("~~~~~gobackURL : " + gobackURL);
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
			
			mv.addObject("boardList", boardList);
			mv.setViewName("freePage/freePage.tiles2");
			
			return mv;
		
   }
   
	// === #51. 게시판 글쓰기 폼페이지 요청 ===
	@RequestMapping(value="/writePage.yo", method= {RequestMethod.GET})
	public ModelAndView requireLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
		
		// === #124. 답변글쓰기가 추가된 경우 ===
		String fk_seq = request.getParameter("fk_seq");
		String groupno = request.getParameter("groupno");
		String depthno = request.getParameter("depthno");
		
		mv.addObject("fk_seq", fk_seq);
		mv.addObject("groupno", groupno);
		mv.addObject("depthno", depthno);
	   //////////////////////////////////////////////////
		
		mv.setViewName("freePage/writePage.tiles2");
		
		return mv;
	}
	
	
	// === #53. 게시판 글쓰기 완료 요청 ===
	@RequestMapping(value="/writePageEnd.yo", method= {RequestMethod.POST}) 
//		public ModelAndView addEnd(BoardVO boardvo, ModelAndView mv) {
	/*
    === #133. 파일첨부가 된 글쓰기 이므로
              먼저 위의  public ModelAndView addEnd(BoardVO boardvo, ModelAndView mv) { 을 주석처리한 이후에 아래와 같이 한다.
       MultipartHttpServletRequest mrequest 를 사용하기 위해서는 
              먼저  /Board/src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 파일에서
       multipartResolver 를 bean 으로 등록해주어야 한다.!!!                     	
*/
	public String writePageEnd(BoardVO boardvo, MultipartHttpServletRequest mrequest) {	
	 /*
	      웹페이지에 요청form이 enctype="multipart/form-data" 으로 되어있어서 Multipart 요청(파일처리 요청)이 들어올때 
	      컨트롤러에서는 HttpServletRequest 대신 MultipartHttpServletRequest 인터페이스를 사용해야 한다.
	   MultipartHttpServletRequest 인터페이스는 HttpServletRequest 인터페이스와 MultipartRequest 인터페이스를 상속받고있다.
	      즉, 웹 요청 정보를 얻기 위한 getParameter()와 같은 메소드와 Multipart(파일처리) 관련 메소드를 모두 사용가능하다.
		 
	    ===== 사용자가 쓴 글에 파일이 첨부되어 있는 것인지 아니면 파일첨부가 안된것인지 구분을 지어주어야 한다. =====
		========= !!첨부파일이 있는지 없는지 알아오기 시작!! ========= */ 
		MultipartFile attach = boardvo.getAttach();
		if(!attach.isEmpty()) {
			 // attach 가 비어있지 않다면(즉, 첨부파일이 있는 경우라면)
			
		/*
		   1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다.
		   >>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기 
		   	  우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다.
		*/
			//  WAS의 webapp 의 절대경로를 알아와야 한다.
		 	HttpSession session = mrequest.getSession();
		 	String root = session.getServletContext().getRealPath("/");
		 	String path = root + "resources" + File.separator + "files";
		 	// File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다.
		 	// 운영체제가 Windows 이라면 File.separator 은 "\" 이고,
		    // 운영체제가 UNIX, Linux 이라면 File.separator 은 "/" 이다.
		 	
		 	// path 가 첨부파일을 저장할 WAS(톰캣)의 폴더가 된다.
		 	System.out.println(">>> 확인용 path ==>" + path);
			// >>> 확인용 path ==>C:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files 
		 	
		 // == 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일올리기 ==
		 	String newFileName = "";
		 	// WAS(톰캣)의 디스크에 저장될 파일명
		 	
		 	byte[] bytes = null;
		 	// 첨부파일을 WAS(톰캣)의 디스크에 저장할때 사용되는 용도
		 	
		 	long fileSize = 0;
		 	// 파일크기를 읽어오기 위한 용도 
		 	
		 	try {
				bytes = attach.getBytes();
				// getBytes() 메소드는 첨부된 파일을 바이트단위로 파일을 다 읽어오는 것이다. 
				// 예를 들어, 첨부한 파일이 "강아지.png" 이라면
				// 이파일을 WAS(톰캣) 디스크에 저장시키기 위해 byte[] 타입으로 변경해서 올린다.
				
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				// 이제 파일올리기를 한다.
				// attach.getOriginalFilename() 은 첨부된 파일의 파일명(강아지.png)이다.
				
				System.out.println(">>> 확인용 newFileName ==> " + newFileName); 
				// >>> 확인용 newFileName ==> 201907251244341722885843352000.jpg 
				
				// == 3. BoardVO boardvo 에 fileName 값과 orgFilename 값과  fileSize 값을 넣어주기 
				boardvo.setFileName(newFileName);
				// WAS(톰캣)에 저장된 파일명(201907251244341722885843352000.jpg)
				
				boardvo.setOrgFilename(attach.getOriginalFilename());
				// 게시판 페이지에서 첨부된 파일의 파일명(강아지.png)을 보여줄때 및  
				// 사용자가 파일을 다운로드 할때 사용되어지는 파일명
				
				fileSize = attach.getSize();
				boardvo.setFileSize(String.valueOf(fileSize));
				// 게시판 페이지에서 첨부한 파일의 크기를 보여줄때 String 타입으로 변경해서 저장함.
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	//	========= !!첨부파일이 있는지 없는지 알아오기 끝!! =========
		
		String content = boardvo.getContent();
		
		// *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 *** //
		boardvo.setContent(MyUtil.replaceParameter(content));  	   
		
		content = boardvo.getContent().replaceAll("\r\n", "<br/>");
		boardvo.setContent(content);
		
	//	int n = service.add(boardvo);
	
	//  === 138. 파일첨부가 있는 경우와 없는 경우에 따라서 Service단으로 호출하기 ===
	//		먼저 위의 int n = service.add(boardvo); 을 주석처리 하고서 아래처럼 한다.
		
		int n = 0;
		if(attach.isEmpty()) {
			// 첨부파일이 없는 경우라면
			n = service.add(boardvo);
		}
		else {
			// 첨부파일이 있는 경우라면
			n = service.add_withFile(boardvo);
		}
		
	//  mv.addObject("n", n);
		mrequest.setAttribute("n", n);
	//  mv.setViewName("board/addEnd.tiles1");
	//	return mv;
		
		return "freePage/writePageEnd.tiles2"; 
	}
	
	// === #61. 글1개를 보여주는 페이지 요청 ===
	   @RequestMapping(value="/view.yo", method= {RequestMethod.GET})	
	   public ModelAndView freeView(HttpServletRequest request, ModelAndView mv) {
		   
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
		   
		   BoardVO boardvo = null;
		   
		   // 위의 글목록보기 #68. 에서 session.setAttribute("readCountPermission", "yes"); 해두었다.
		   if("yes".equals(session.getAttribute("readCountPermission")) ) {
			   // 글목록보기를 클릭한 다음 특정글을 조회해온 경우이다.
		   
		      boardvo = service.getView(seq, email);
		      // 글조회수 증가와 함께 글1개 조회를 해주는 것
		      
		      session.removeAttribute("readCountPermission");
		      // 중요함!! session 에 저장된 readCountPermission을 삭제한다.
		   }
		   else {
			  boardvo = service.getViewWithNoAddCount(seq);
			  // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것
		   }
		   
		   // === #92. 댓글쓰기가 있는 게시판 === //
		   List<CommentVO> commentList = service.getCommentList(seq); 
	   	   // 원게시물에 딸린 댓글들을 조회해오는 것
		   mv.addObject("commentList", commentList);
		   ///////////////////////////////////////////
		   
		   mv.addObject("boardvo", boardvo);
		   mv.setViewName("freePage/view.tiles2");
		   
		   return mv;
	   }
		
		
	   // === #70. 글수정 페이지 요청 ===
	   @RequestMapping(value="/edit.yo", method= {RequestMethod.GET})
	   public ModelAndView requireLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
		   
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
			   
			   mv.setViewName("freePage/edit.tiles2");
		   }
		   
		   return mv;
	   }
		

	   // === #71. 글수정 페이지 완료하기 ===
	   @RequestMapping(value="/editEnd.yo", method= {RequestMethod.POST})
	   public ModelAndView requireLogin_editEnd(HttpServletRequest request, HttpServletResponse response, BoardVO boardvo, ModelAndView mv) {
		   
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
		   
		   mv.addObject("loc", request.getContextPath()+"/view.yo?seq="+boardvo.getSeq());
		   mv.setViewName("msg");
		   
		   return mv;
	   }
	   
	   
	   // === #77. 글삭제 페이지 요청 ===
	   @RequestMapping(value="/del.yo", method= {RequestMethod.GET})
	   public ModelAndView requireLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) { 
		
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
			   mv.setViewName("freePage/del.tiles2");
			   // /Board/src/main/webapp/WEB-INF/views/tiles1/board/del.jsp 파일을 생성한다.
		   }
		   
		   return mv;
	   }
	   
	   
	   // === #78. 글삭제 페이지 완료하기 ===
	   @RequestMapping(value="/delEnd.yo", method= {RequestMethod.POST})
	   public ModelAndView delEnd(HttpServletRequest request, ModelAndView mv) {
		 
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
			   
			   mv.addObject("loc", request.getContextPath()+"/freePage.yo");
			   mv.setViewName("msg");
		   
		    } catch (Throwable e) {
				e.printStackTrace();
			}
		   
		    return mv;
	   }
	   
	   
	   // === #85. 댓글쓰기 ===
	   @RequestMapping(value="/addComment.yo", method= {RequestMethod.POST}, produces="text/plain;charset=UTF-8")      
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
	   
	   
	   // === #103. 검색어 입력시 자동글 완성하기 3 === 
	   @RequestMapping(value="/wordSearchShow.yo", method= {RequestMethod.GET}, produces="text/plain;charset=UTF-8") 
	   @ResponseBody
	   public String wordSearchShow(HttpServletRequest request) {
		   
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
	   
	   // ===== #147. 첨부파일 다운로드 받기 =====
		@RequestMapping(value="/download.yo", method={RequestMethod.GET}) 
		public void requireLogin_download(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			
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
			// Desert.jpg 처럼 다운받을 사용자에게 보여줄 파일명.
			
			
			// 첨부파일이 저장되어 있는 
			// WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다. 
			// 이 경로는 우리가 파일첨부를 위해서
			//    /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
			// WAS 의 webapp 의 절대경로를 알아와야 한다. 
			session = request.getSession();
			
			String root = session.getServletContext().getRealPath("/"); 
			String path = root + "resources"+File.separator+"files";
			// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다. 
			
			// **** 다운로드 하기 **** //
			// 다운로드가 실패할 경우 메시지를 띄워주기 위해서
			// boolean 타입 변수 flag 를 선언한다.
			boolean flag = false;
			
			flag = fileManager.doFileDownload(fileName, orgFilename, path, response);
			// 다운로드가 성공이면 true 를 반납해주고,
			// 다운로드가 실패이면 false 를 반납해준다.
			
			if(!flag) {
				// 다운로드가 실패할 경우 메시지를 띄워준다.
				
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter writer = null;
				
				try {
					writer = response.getWriter();
					// 웹브라우저상에 메시지를 쓰기 위한 객체생성.
				} catch (Exception e) {
					
				}
				
				writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가능합니다.!!')</script>");       
				
			}
			 
		} // end of void download(HttpServletRequest req, HttpServletResponse res, HttpSession session)---------
	   
		
	   /*==== #스마트에디터3. 드래그앤드롭을 사용한 다중사진 파일업로드 ====*/
	   @RequestMapping(value="/image/multiplePhotoUpload.yo", method={RequestMethod.POST})
	   public void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {
		    
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
	   

   	@RequestMapping(value="/freePage/multichat.yo", method= {RequestMethod.GET})
   	public String multichat(HttpServletRequest request, HttpServletResponse response) {
   		HttpSession session =  request.getSession();
   		
   		if( session.getAttribute("loginuser") == null ) {
			try {
				String msg = "먼저 로그인 하세요";
				String loc = request.getContextPath()+"/login.yo";
				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);
				
				// >>> 로그인 성공 후 로그인 하기전 페이지로 돌아가는 작업 하기<<< //
				// === 현재 페이지의 주소(URL) 알아내기 ===
				String url = MyUtil.getCurrentURL(request);
				
			//	System.out.println("확인용"+url);
				
				session.setAttribute("gobackURL", url); // 세션에 url 정보를 저장한다.
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
   		
   		return "multichat.notiles";
   	}  
   
}