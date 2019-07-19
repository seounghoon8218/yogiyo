package com.spring.common;

import javax.servlet.http.HttpServletRequest;

public class MyUtil {

   // *** ? 다음의 데이터까지 포함한 현재 uRL 주소를 알려주는 메소드 *** //
   public static String getCurrentURL(HttpServletRequest request) {
      
      String currentURL = request.getRequestURL().toString();
      // http://localhost:9090/MyMVC/shop/prodView.kh?
      
      String queryString = request.getQueryString();
      // pnum=2
      
      currentURL += "?"+queryString;
      // http://localhost:9090/MyMVC/shop/prodView.kh?pnum=2
      
      String ctxPath = request.getContextPath();
      //   /MyMVC
      
      int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();  // indexOf 어떠한 문자가 시작되는 위치값
      //                     21            +      6   
      
      currentURL = currentURL.substring(beginIndex+1);
      //                  27+1
      
      return currentURL;
      // shop/prodView.kh?pnum=2
      
   } // getCurrentURL ------------------------------------------------
   
   
   
   // === 검색어가 포함된 페이지바 만들기 === //
   public static String makePageBar(String url,int currentShowPageNo,int sizePerPage,int totalPage,int blockSize,String searchType,String searchWord) {
      
      String pageBar = "";
      
      // blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 갯수
      /*
           1  2  3  4  5  6  7  8  9  10   -- 1개 블럭
           11 12 13 14 15 16 17 18 19 20   -- 1개 블럭           
       */
      
      int loop = 1;
      /*
          loop 는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 갯수(지금은 10개(blockSize))까지만 증가하는 용도이다.
       */
      
      int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
      // *** !! 공식 !! *** //
      
      /*
      1   2   3   4   5   6   7   8   9   10   -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다.
      11  12  13  14  15  16  17  18  19   20   -- 두번째 블럭의 페이지 번호 시작값(pageNo)은 11이다.
      21  22  23  24  25  26  27  28  29   30   -- 세번째 블럭의 페이지 번호 시작값(pageNo)은 21이다.
      
      currentShowPageNo          pageNo
      -----------------------------------
                1                  1 = ((1 - 1)/10) * 10 +1;
                2                  1 = ((2 - 1)/10) * 10 +1;
                3                  1 = ((3 - 1)/10) * 10 +1;
                4                  1 = ((4 - 1)/10) * 10 +1;
                5                  1 = ((5 - 1)/10) * 10 +1;
                6                  1 = ((6 - 1)/10) * 10 +1;
                7                  1 = ((7 - 1)/10) * 10 +1;
                8                  1 = ((8 - 1)/10) * 10 +1;
                9                  1 = ((9 - 1)/10) * 10 +1;
               10                  1 = ((10 - 1)/10) * 10 +1;
               
               11                  11 = ((11 - 1)/10) * 10 +1;
               12                  11 = ((12 - 1)/10) * 10 +1;
               13
               14
               15
               16
               17
               18
               19
               20
               
               21                  21 = ((21 - 1)/10) * 10 +1;
               22                  21 = ((22 - 1)/10) * 10 +1;
               23
               ..
               29
               30
       */
      
      if(pageNo != 1) {
         // *** [이전] 만들기 *** //
         pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[이전]</a>&nbsp;";
      }   
      
      while(!(loop>blockSize || pageNo>totalPage)) { // ()안에 while 을 빠져나가는 조건
         
         if(pageNo == currentShowPageNo) {
            pageBar += "&nbsp;<span style='color: red; border: 1px solid gray; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;";
         }
         else {
            pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>" +pageNo+"</a>&nbsp;";
            // ""+1+"&nbsp;"+2+"&nbsp;"+3+"&nbsp;"+...+10+"&nbsp;"
         }
         loop++;
         pageNo++;
      } // while ---------------------(페이지바와 링크)      
            
      if(!(pageNo>totalPage)) {
         // *** [다음] 만들기 *** //
         pageBar += "&nbsp;<a href='"+url+"&currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"&searchType="+searchType+"&searchWord="+searchWord+"'>[다음]</a>&nbsp;";
      }
      
      
      return pageBar;
   } // 페이지바 -------------------------------------------------
   
   
   // *** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성해주는 메소드 생성하기 *** //
   public static String replaceParameter(String param) {      
      String result = param;
      
      if(param != null) {
         result = result.replaceAll("<", "&lt;"); // "<" 가 있으면 태그로 보지말고 "&lt"가 있다면 태그다. 
         result = result.replaceAll(">", "&gt;"); 
         result = result.replaceAll("&", "&amp;"); 
         result = result.replaceAll("\"", "&quot;");
      }
      
      return result;      
   } // 크로스 사이트 보안 메소드 ---------------------------------------
   
   
} // MyUtil --------------------------------------------------------------------------------