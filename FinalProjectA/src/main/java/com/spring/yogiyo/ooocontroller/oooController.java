package com.spring.yogiyo.ooocontroller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.MyUtil;
import com.spring.yogiyo.ooomodel.InteroooDAO;
import com.spring.yogiyo.ooomodel.oooDAO;
import com.spring.yogiyo.ooomodel.oooVO;
import com.spring.yogiyo.oooservice.Interoooservice;

@Controller
public class oooController {

   // === #35. 의존객체 주입하기 ( DI : Dependency Injection ) ====
      @Autowired
      private Interoooservice service;
      
   
   // === #36. 매장 등록 페이지 요청 ====
   @RequestMapping(value="/shopregister.yo", method= {RequestMethod.GET})
   public ModelAndView shopregister(ModelAndView mv, HttpServletRequest request) {
      
      HttpSession session = request.getSession();
      //MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
      
      /*
      if(loginuser ==null ||              (loginuser != null)) {
         
         String msg= "관리자만 접근이 가능합니다";
         String loc = "javascript:history.back()";
         
         mv.addObject("msg", msg);
         mv.addObject("loc", loc);
         
         mv.setViewName("msg");
         
      }
      */
      //else {
         
         InteroooDAO odao = new oooDAO();
         
         int masterno = service.getMasterNo(); // 사업자 번호 가져오기
         List<HashMap<String,String>> shopCategoryList = service.selectShopCategory(); // 업종 카테고리 가져오기
         
         mv.addObject("masterno", masterno);
         mv.addObject("shopCategoryList", shopCategoryList);
         mv.setViewName("register/shopregister.tiles3");
      //}
      return mv;
      
   } // end shopregister
   
   
   @RequestMapping(value="/shopregisterEnd.yo", method= {RequestMethod.POST})
   public ModelAndView shopregisterEnd(oooVO ovo, ModelAndView mv, HttpServletRequest request) {
      
      String wonsanji = ovo.getWonsanji();
      
      wonsanji = MyUtil.replaceParameter(wonsanji);
      wonsanji = ovo.getWonsanji().replaceAll("\r\n", "<br>");      
      ovo.setWonsanji(wonsanji);
      System.out.println(ovo.getWdo());
      System.out.println(ovo.getKdo());
      System.out.println("---------------------------------------------------");
      
      System.out.println(request.getParameter("wdo"));
      System.out.println(request.getParameter("kdo"));
      
      int n = service.addshop(ovo); // 매장 등록 완료
      System.out.println("n===>" + n);
      
      
      mv.addObject("n", n);
      mv.setViewName("register/shopregisterEnd.tiles3");
      
      return mv;
   } // end shopregisterEnd
   
}