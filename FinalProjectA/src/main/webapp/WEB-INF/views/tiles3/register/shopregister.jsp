<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
 <style type="text/css"> 
 	textarea {
  resize: none; /* 사용자 임의 변경 불가 */
  resize: both; /* 사용자 변경이 모두 가능 */
  resize: horizontal; /* 좌우만 가능 */
  resize: vertical; /* 상하만 가능 */
  
  
  	input[type=text]{
		width: 100%;
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
		border: 1px solid black;
	}
	
}

</style>  
<script type="text/javascript">

	
	
	$(document).ready(function(){
		
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
				alert("위도를 입력하시오.");
				return;
			}
			
			// 경도 유효성 검사
			var kdoval = $("#kdo").val().trim();
			if(kdoval == ""){
				alert("경도를 입력하시오.");
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
			
			// 결제방법 유효성 검사
			var paymethodval = $("#paymethod").val().trim();
			if(paymethodval  == ""){
				alert("결제방법을 입력하시오.");
				return;
			}
			
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
		
		
	})// end re ady
		
		
		
		
		
	
	
</script>
   
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
	<div align="center" style="font-weight: bold; margin-bottom: 35px;">
		<h2><span>매장 등록</span></h2>
	</div>
	<form name="shopregisterFrm">
		<span>매장정보 입력</span>
		<table style="width: 100%; border: 1px solid black;">
			
			<tr>
				<td>사업자 번호:</td><td><input type="text" placeholder="(필수)사업자번호 입력" id="masterno" name="masterno"  autofocus /></td>
			</tr>
			<tr>
				<td>매장 이름:</td><td><input type="text" placeholder="(필수)매장 이름" id="shopname" name="shopname"  /></td>
			</tr>
			<tr>
				<td>업종 카테고리:</td><td><input type="text" placeholder="(필수)업종카테고리" id="shopcategorycode" name="shopcategorycode"  /></td>
			</tr>
			<tr>
				<td>주소1:</td><td><input type="text" placeholder="(필수)주소1" id="addr" name="addr"  ></td>
			</tr>
			<tr>
				<td>주소2:</td><td><input type="text" placeholder="(필수)주소2" id="addr2" name="addr2"  ></td>
			</tr>
			<tr>
				<td>위도:</td><td><input type="text" placeholder="(필수)위도" id="wdo" name="wdo"></td>
			</tr>
			<tr>
				<td>경도:</td><td><input type="text" placeholder="(필수)경도" id="kdo" name="kdo"></td>
			</tr>
			<tr>
				<td>전화번호:</td><td><input type="text" placeholder="(필수)전화번호" id="shoptel" name="shoptel"></td>
			</tr>
			<tr>
				<td>매장 사진:</td><td> <input type="text" placeholder="(필수)매장사진" id="shopimage" name="shopimage"></td>
			</tr>
			<tr>
				<td>영업시간:</td><td><input type="text" placeholder="(필수)영업시간" id="shoptime" name="shoptime"></td>
			</tr>
			<tr>
				<td>최소주문금액:</td><td><input type="text" placeholder="(필수)최소주문금액" id="minprice" name="minprice"></td>
			</tr>
			<tr>
				<td>결제 방법:</td><td><input type="text" placeholder="(필수)결제방법" id="paymethod" name="paymethod"></td>
			</tr>
			<tr>
				<td>상호명:</td><td><input type="text" placeholder="(필수)상호명" id="sanghoname" name="sanghoname"></td>
			</tr>
			<tr>
				<td>원산지 정보</td><td><textarea rows="8" cols="30" placeholder="(필수)원산지 정보 입력" id="wonsanji" name="wonsanji" class></textarea> </td>
			</tr>
		</table>
		<button type="button" id="registerBtn" style="width: 100%; color: white; background-color: red;" ><span style="font-weight: bold; font-size: 15pt;">매장 등록</span></button>
	</form>
</div>  
   
   