<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<% String ctxPath = request.getContextPath(); %>
  <style type="text/css"> 
    textarea {
     resize: none; /* 사용자 임의 변경 불가 */
     resize: both; /* 사용자 변경이 모두 가능 */
     resize: horizontal; /* 좌우만 가능 */
     resize: vertical; /* 상하만 가능 */
  }
  
     input[type=text]{
      height: 50px;
      font-size: 13pt;
   }
   input[type=checkbox]{
      height: 15px;
   }
   a{
      text-decoration: none;
      
   }
   
   table, th, td{
      border: 0px solid black;
   }
   
   th{
         width: 10% !important;
   }
   
   .inputmargin{
      margin-top: 10px;
   }
   #title { margin-left: 25%; width: 50%; border-bottom: red solid 2px;}
}

</style>  
 
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript">

   $(document).ready(function(){
      
      $(".error").hide();
      
      $("#registerBtn").mouseover(function(){
         var addressVal = $("#addr").val().trim();
           
         goAddressToLatlng(addressVal);
      }); // end mouseover
      
   
         
      
      $("#registerBtn").click(function(){
      
         
         
         // 사업자번호 유효성 검사
         var masternoval = $("#masterno").val().trim();
         if(masternoval  == ""){
            alert("사업자번호를 입력하시오.");
            return;
         }
         
         // 매장이름 유효성 검사
         var shopnameval = $("#shopname").val().trim();
         if(shopnameval == ""){
            alert("매장이름을 입력하시오.");
            return;
         }
         
         // 업종 카테고리 유효성 검사
         var shopcategorycodeval = $("#shopcategorycode").val().trim();
         if(shopcategorycodeval  == ""){
            alert("업종카테고리를 입력하시오.");
            return;
         }
         
         // 주소1 유효성 검사
         var addrval = $("#addr").val().trim();
         if(addrval  == ""){
            alert("주소1을 입력하시오.");
            return;
         }
         
         // 주소2 유효성 검사
         var addr2val = $("#addr2").val().trim();
         if(addr2val  == ""){
            alert("주소2을 입력하시오.");
            return;
         }
         
          // 위도 유효성 검사
         var wdoval = $("#wdo").val().trim();
         if(wdoval== ""){
            alert("천천히 버튼을 입력하시오.");
            return;
         }
         
         // 경도 유효성 검사
         var kdoval = $("#kdo").val().trim();
         if(kdoval == ""){
            alert("천천히 버튼을 입력하시오.");
            return;
         } 
         
         // 전화번호 유효성 검사
         var shoptelval = $("#shoptel").val().trim();
         if(shoptelval  == ""){
            alert("전화번호를 입력하시오.");
            return;
         }
         
         // 매장사진 유효성 검사
         var shopimageval = $("#shopimage").val().trim();
         if(shopimageval  == ""){
            alert("매장사진을 입력하시오.");
            return;
         }
         
         // 영업시간 유효성 검사
         var shoptimeval = $("#shoptime").val().trim();
         if(shoptimeval  == ""){
            alert("영업시간을 입력하시오.");
            return;
         }
         
         // 최소주문금액 유효성 검사
         var minpriceval = $("#minprice").val().trim();
         if(minpriceval  == ""){
            alert("최소주문금액을 입력하시오.");
            return;
         }
         
         /* // 결제방법 유효성 검사
         var paymethodval = $("#paymethod").val().trim();
         if(paymethodval  == ""){
            alert("결제방법을 입력하시오.");
            return;
         }
          */
         // 상호명 유효성 검사
         var sanghonameval = $("#sanghoname").val().trim();
         if(sanghonameval  == ""){
            alert("상호명을 입력하시오.");
            return;
         }
         
         // 원산지 정보 유효성 검사
         var wonsanjival = $("#wonsanji").val().trim();
         if(wonsanjival  == ""){
            alert("원산지를 입력하시오.");
            return;
         }
         
         var frm = document.shopregisterFrm;
         
         frm.method= "POST";
         frm.action= "<%=ctxPath %>/shopregisterEnd.yo";
         frm.submit();
         
      })//end registeBtn
      
      $("#zipcodeSearch").click(function(){
            new daum.Postcode({
               oncomplete: function(data) {
                   $("#post1").val(data.postcode1);  // 우편번호의 첫번째 값     예> 151
                   $("#post2").val(data.postcode2);  // 우편번호의 두번째 값     예> 019
                   $("#addr").val(data.address);    // 큰주소                        예> 서울특별시 종로구 인사로 17 
                   $("#addr2").focus();
               }
            }).open();
            
         });// end zipcodeSearch click
      
       
         
   })// end document ready

   
     function goAddressToLatlng(addressVal) {
         var address = encodeURIComponent(addressVal);
         
         $.ajax({
             url: "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDDQx9Q_JsWUjWyssoeEaeBGSbhvGcTyrA&sensor=false&address="+address,
             type: "GET",
             dataType: "JSON",
                success: function(json){
                      if(json.status == 'OK') {
                          var wdo = json.results[0].geometry.location.lat;
                             var kdo = json.results[0].geometry.location.lng;
                   
                    
                             $("#wdo").val(wdo);
                          $("#kdo").val(kdo);
                           
                      } else if(json.status == 'ZERO_RESULTS') {
                          alert("지오코딩이 성공했지만 반환된 결과가 없음을 나타냅니다.\n\n이는 지오코딩이 존재하지 않는 address 또는 원격 지역의 latlng을 전달받는 경우 발생할 수 있습니다.")
                      } else if(json.status == 'OVER_QUERY_LIMIT') {
                          alert("할당량이 초과되었습니다.");
                      } else if(json.status == 'REQUEST_DENIED') {
                          alert("요청이 거부되었습니다.\n\n대부분의 경우 sensor 매개변수가 없기 때문입니다.");
                      } else if(json.status == 'INVALID_REQUEST') {
                          alert("일반적으로 쿼리(address 또는 latlng)가 누락되었음을 나타냅니다.");
                      }
              }
         });
     } // end goAddressToLatlng
     
</script>
   
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
  
    <div id="title"  align="left" style="font-weight: bold; margin-bottom: 35px;">
		<h2>메뉴 등록</h2>
		<h6 style="color: #888;">운영중인 음식점의 매장을 요기요에 등록하세요. 온라인 메뉴등록중 어려움이 있으시면 고객센터(02-3447-3612)로 연락주세요</h6>
   </div>
   <form name="shopregisterFrm">
      <span>매장정보 입력</span>
      <table style="width: 100%; border: 1px solid black;">
         
         <tr>
            <th>사업자 번호:</th><td><input type="text" value="${masterno}" id="masterno" name="masterno"  autofocus readonly class="inputmargin" /></td>
         </tr>
         <tr>
            <th>매장 이름:</th><td><input type="text" placeholder="(필수)매장 이름" id="shopname" name="shopname"  class="inputmargin" /></td>
         </tr>
         <tr>
            <th>업종 카테고리:</th>
            <td>
               <select name="shopcategorycode" id="shopcategorycode">
                  <option value="">::업종 선택::</option>
                  <c:forEach var="category" items="${shopCategoryList}">
                     <option value="${category.shopcategorycode}">${category.shopcategoryname}</option>
                  </c:forEach>
               </select>
               
            
            </td>
         </tr>
         <tr>
            <th style="width: 20%; font-weight: bold;">우편번호</th>
            <td style="width: 80%; text-align: left;">
            <input type="text" id="post1" name="post1" size="6" maxlength="3" />
            &nbsp;-&nbsp;
            <input type="text" id="post2" name="post2" size="6" maxlength="3" />&nbsp;&nbsp;
            <!-- 우편번호 찾기 -->
            <img id="zipcodeSearch" src="<%=request.getContextPath()%>/resources/images/b_zipcode.gif" style="vertical-align: middle; cursor: pointer;" />
            <span class="error error_post">우편번호 형식이 아닙니다.</span>
            </td>
            </tr>
            <tr>
              <th style="width: 20%; font-weight: bold;">주소</th>
            <td style="width: 80%; text-align: left;">
              <input type="text" id="addr" class="address" name="addr" size="60" maxlength="100" /><br style="line-height: 200%"/>
              <input type="text" id="addr2" class="address" name="addr2" size="60" maxlength="100" />
              <span class="error">주소를 입력하세요</span>
            </td>
            </tr>
         
         <tr>
            <td><input type="hidden" placeholder="(필수)위도" id="wdo" name="wdo"></td>
         </tr>
         <tr>
            <td><input type="hidden" placeholder="(필수)경도" id="kdo" name="kdo"></td>
            
         </tr>
         <tr>
            <th>전화번호:</th><td><input type="text" placeholder="(필수)전화번호" id="shoptel" name="shoptel"  class="inputmargin"></td>
         </tr>
         <tr>
            <th>매장 사진:</th><td> <input type="text" placeholder="(필수)매장사진" id="shopimage" name="shopimage"  class="inputmargin"></td>
         </tr>
         <tr>
            <th>영업시간:</th><td><input type="text" placeholder="예) 오전 10시 ~ 오후 9시" id="shoptime" name="shoptime"  class="inputmargin"></td>
         </tr>
         <tr>
            <th>최소주문금액:</th><td><input type="text" placeholder="(필수)최소주문금액" id="minprice" name="minprice"  class="inputmargin"></td>
         </tr>
         <tr>
            <td><input type="hidden" placeholder="(필수)결제방법" id="paymethod" name="paymethod" value="카드"  class="inputmargin"></td>
         </tr>
         <tr>
            <th>상호명:</th><td><input type="text" placeholder="(필수)상호명" id="sanghoname" name="sanghoname"  class="inputmargin"></td>
         </tr>
         <tr>
            <th>원산지 정보</th><td><textarea rows="8" cols="30" placeholder="(필수)원산지 정보 입력" id="wonsanji" name="wonsanji"  class="inputmargin"></textarea> </td>
         </tr>
      </table>
      <button type="button" id="registerBtn" style="width: 100%; color: white; background-color: red; margin: 10px 0px;" ><span style="font-weight: bold; font-size: 20pt;">매장 등록</span></button>
   </form>
</div>  
   
   