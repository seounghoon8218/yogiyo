package com.spring.yogiyo.wwwcontroller;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spring.common.MyUtil;
import com.spring.common.SHA256;
import com.spring.member.model.MemberVO;
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
   @RequestMapping(value="/edit.yo" , method= {RequestMethod.GET})
   public ModelAndView edit(ModelAndView mv) {
      mv.setViewName("login/edit.tiles3");
      return mv;
   }
   
   // === 내정보수정 처리하기 ===
   @RequestMapping(value="/edit.yo", method= {RequestMethod.POST})
   public ModelAndView editEnd(ModelAndView mv, MemberVO membervo, HttpServletRequest request) {
<<<<<<< HEAD
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
         
         mv.setViewName("login/edit.tiles3");
      }
      return mv;
   }
   // === 수정 페이지 완료하기 ===
   @RequestMapping(value="/editEnd.yo", method= {RequestMethod.POST})
   public ModelAndView editEnd(MemberVO membervo, HttpServletRequest request, ModelAndView mv) {

      int result = service.edit(membervo);
      
      if(result == 0) {
         mv.addObject("msg","회원수정이 불가합니다.");
      }
      else {
         mv.addObject("msg","내 정보수정 성공.");
      }
      mv.addObject("loc",request.getContextPath()+"/edit.yo");
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
      
         String api_key = "NCSPJBGFPUEM7XDK";
          String api_secret = "DH98ZNMAF5UNHILJSFRU0JB7LJUWR4XT";
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
          params.put("from", "01030038665"); // 발신번호
          params.put("type", "SMS"); // Message type ( SMS, LMS, MMS, ATA )
          params.put("text", "인증번호 : " + certificationCode); // 문자내용    
          params.put("app_version", "JAVA SDK v2.2"); // application name and version
   
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
=======
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
			
			mv.setViewName("login/edit.tiles3");
		}
      return mv;
   }
   // === 수정 페이지 완료하기 ===
	@RequestMapping(value="/editEnd.yo", method= {RequestMethod.POST})
	public ModelAndView editEnd(MemberVO membervo, HttpServletRequest request, ModelAndView mv) {

		int result = service.edit(membervo);
		
		if(result == 0) {
			mv.addObject("msg","회원수정이 불가합니다.");
		}
		else {
			mv.addObject("msg","내 정보수정 성공.");
		}
		mv.addObject("loc",request.getContextPath()+"/edit.yo");
		mv.setViewName("msg");
		
		return mv;
	}
   
>>>>>>> branch 'master' of https://github.com/seounghoon8218/yogiyo.git
}