<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctxPath = request.getContextPath();
%>
<style type="text/css">
	a { text-decoration: none; }
	#shopregisterFrm { width: 50%; display: inline-block; margin: 10px 0px 0px 25%; }
	input { height: 34px; }
	select { height: 34px; width: 110px; }
	tr { margin: 0px 0px 10px 0px }
	td { width: 180px; padding: 10px; }
	tr, td { border: 1px solid #E0E0E0; border-left: none; border-right: none; }
	
	.button{ padding: 10px 25% 10px 25%; display: inline-block; width: 100%; border-top: 1px solid #888; margin-top: 10px; }
	.tdTitle { width: 155px; height: 34px; border-right: 1px solid #E0E0E0; background-color: #ce252508;}
	#title { margin-left: 25%; width: 50%; border-bottom: red solid 2px;}
	#firTitle { width: 50%; margin: 30px 0px 0px 25%; border-bottom: solid #888 1px; padding-bottom: 10px; }
	#secTitle { border-top: solid #888 1px; border-bottom: solid #888 1px; padding: 50px 0px 10px 0px; margin: 10px 0px 10px 0px;}
</style>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

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
         frm.action= "<%=ctxPath%>/shopregisterEnd.yo";
		 frm.submit();

		})//end registeBtn

		$("#zipcodeSearch").click(function() {
			new daum.Postcode({
				oncomplete : function(data) {
					$("#post1").val(data.postcode1); // 우편번호의 첫번째 값     예> 151
					$("#post2").val(data.postcode2); // 우편번호의 두번째 값     예> 019
					$("#addr").val(data.address); // 큰주소                        예> 서울특별시 종로구 인사로 17 
					$("#addr2").focus();
				}
			}).open();

		});// end zipcodeSearch click

	})// end document ready

	function goAddressToLatlng(addressVal) {
		var address = encodeURIComponent(addressVal);
	
		$.ajax({
			url : "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDDQx9Q_JsWUjWyssoeEaeBGSbhvGcTyrA&sensor=false&address="
					+ address,
			type : "GET",
			dataType : "JSON",
			success : function(json) {
				if (json.status == 'OK') {
					var wdo = json.results[0].geometry.location.lat;
					var kdo = json.results[0].geometry.location.lng;

					$("#wdo").val(wdo);
					$("#kdo").val(kdo);

				} else if (json.status == 'ZERO_RESULTS') {
					alert("지오코딩이 성공했지만 반환된 결과가 없음을 나타냅니다.\n\n이는 지오코딩이 존재하지 않는 address 또는 원격 지역의 latlng을 전달받는 경우 발생할 수 있습니다.")
				} else if (json.status == 'OVER_QUERY_LIMIT') {
					alert("할당량이 초과되었습니다.");
				} else if (json.status == 'REQUEST_DENIED') {
					alert("요청이 거부되었습니다.\n\n대부분의 경우 sensor 매개변수가 없기 때문입니다.");
				} else if (json.status == 'INVALID_REQUEST') {
					alert("일반적으로 쿼리(address 또는 latlng)가 누락되었음을 나타냅니다.");
				}
			}
		});
	} // end goAddressToLatlng
</script>

	<div id="title">
		<h2>매장 등록</h2>
		<h6 style="color: #888;">운영중인 음식점을 요기요에 등록하세요. 온라인 매장등록중 어려움이 있으시면 고객센터(02-3447-3612)로 연락주세요</h6>
	</div>
	<div id="firTitle">
		<span style="font-size: 15pt; font-weight: bold;">사업자 정보 <span
			style="color: red; font-size: 9pt;"> *</span></span> <span
			style="font-size: 9pt;">필수 입력</span>
	</div>
	<form name="shopregisterFrm" id="shopregisterFrm">
	<table style="width: 100%;">
		<tr>
			<td class="tdTitle" style="width: 63px;">사업자번호<span style="color: red;"> *</span></td>
			<td class="inputTd">
				<input type="text" name="masterno" id="masterno" value="${masterno}" autofocus readonly />
			</td>
		</tr>
	</table>

	<div id="secTitle">
		<span style="font-size: 15pt; font-weight: bold; margin: 0px;">매장 정보</span>
	</div>

	<table style="width: 100%;">
		<tr>
			<td class="tdTitle">매장 이름<span
				style="color: red; font-size: 9pt;"> *</span>
			</td>
			<td>
				<input type="text" id="shopname" name="shopname" class="inputmargin" />
			</td>
		</tr>
		<tr>
			<td class="tdTitle">업종 카테고리<span style="color: red; font-size: 9pt;"> *</span></td>
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
			<td class="tdTitle">우편번호<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" id="post1" name="post1" size="6" maxlength="3" /> &nbsp;-&nbsp; 
				<input type="text" id="post2" name="post2" size="6" maxlength="3" />&nbsp;&nbsp; <!-- 우편번호 찾기 -->
				<img id="zipcodeSearch" src="<%=request.getContextPath()%>/resources/images/b_zipcode.gif" style="vertical-align: middle; cursor: pointer;" /> 
				<span class="error error_post">우편번호 형식이 아닙니다.</span>
			</td>
		</tr>
		<tr>
			<td class="tdTitle">주소<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" id="addr" class="address" name="addr" size="60" maxlength="100" placeholder="동 , 읍  , 면" /><br style="line-height: 200%" /> 
				<input type="text" id="addr2" class="address" name="addr2" size="60" maxlength="100" placeholder="상세주소" /> 
				<span class="error">주소를 입력하세요</span>
			</td>
		</tr>		
		<tr>
			<td class="tdTitle">전화번호<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" placeholder=" - 포함" id="shoptel" name="shoptel" class="inputmargin">
			</td>
		</tr>
		<tr>
			<td class="tdTitle">매장 사진<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" id="shopimage" name="shopimage" class="inputmargin">
			</td>
		</tr>
		<tr>
			<td class="tdTitle">영업시간<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" placeholder="10:00 ~ 21:00" id="shoptime" name="shoptime" class="inputmargin">
			</td>
		</tr>
		<tr>
			<td class="tdTitle">최소주문금액<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" placeholder="최소주문금액" id="minprice" name="minprice" class="inputmargin">
			</td>
		</tr>
		<tr>
			<td class="tdTitle">상호명<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<input type="text" placeholder="상호명" id="sanghoname" name="sanghoname" class="inputmargin">
			</td>
		</tr>
		<tr>
			<td class="tdTitle">원산지 정보<span style="color: red; font-size: 9pt;"> *</span></td>
			<td>
				<textarea rows="5" cols="62.5" placeholder="원산지 정보 입력" id="wonsanji" name="wonsanji" class="inputmargin" style="resize: none;"></textarea>
			</td>
		</tr>
		
	</table>
	
	<div class="button">
		<input type="button" id="registerBtn" style="background-color: red;   border: none; width: 180px; height: 45px; color: white; font-weight: bold;"   value="등록"  />
		<input type="reset"  style="background-color: white; border: 1px black solid; width: 180px; height: 45px; font-weight: bold;"  value="취소"/>
	</div>
	
	<input type="hidden" placeholder="위도" id="wdo" name="wdo">
	<input type="hidden" placeholder="경도" id="kdo" name="kdo">
	<input type="hidden" placeholder="결제방법" id="paymethod" name="paymethod" value="카드" class="inputmargin">
		
	</form>

