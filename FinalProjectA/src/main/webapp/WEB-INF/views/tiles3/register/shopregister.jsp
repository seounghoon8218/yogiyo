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
	
}

</style>  
<script type="text/javascript">
	
	$("#registerBtn").click(function(){
		
		var frm = document.shopregisterFrm;
		frm.method="POST";
		frm.action = "<%=ctxPath%>/shopregisterEnd.yo";
		frm.submit();
		
	}); // end registerBtn
	
</script>
   
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
	<div align="center" style="font-weight: bold; margin-bottom: 35px;">
		<h2><span>매장 등록</span></h2>
	</div>
	<form name="shopregisterFrm">
		<table style="width: 100%">
			<tr><th><span>매장정보 입력</span></th></tr>
			<tr>
				<td><input type="text" placeholder="(필수)사업자번호 입력" id="masterno" name="masterno"  autofocus /></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)매장 이름" id="shopname" name="shopname"  /></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)업종카테고리" id="shopcategorycode" name="shopcategorycode"  /></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)주소1" id="addr" name="addr"  ></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)주소2" id="addr2" name="addr2"  ></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)위도" id="wdo" name="wdo"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)경도" id="kdo" name="kdo"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)전화번호" id="shoptel" name="shoptel"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)매장사진" id="shopimage" name="shopimage"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)영업시간" id="shoptime" name="shoptime"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)최소주문금액" id="minprice" name="minprice"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)결제방법" id="paymethod" name="paymethod"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)상호명" id="sanghoname" name="sanghoname"></td>
			</tr>
			<tr>
				<td><textarea rows="8" cols="30" placeholder="(필수)원산지 정보 입력" id="wonsanji" name="wonsanji" class></textarea> </td>
			</tr>
			
			<tr><td>&nbsp;<td></tr>
			<tr><th><span>약관동의 </span></th></tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check" />
					<label for="check">전체동의</label>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check2" style="margin-left: 10px;" />
					<label for="check2">이용약관동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check3" style="margin-left: 10px;"/>
					<label for="check3">개인정보 수집 및 이용동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check4" style="margin-left: 10px;"/>
					<label for="check4">만 14세 이상 이용자(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr><td>&nbsp;<td></tr>
			<tr>
				<td>
					<button type="button" id="registerBtn" style="width: 100%; color: white; background-color: red;"><span style="font-weight: bold; font-size: 15pt;">매장 등록</span></button>
				</td>
			</tr>
		</table>
	</form>
</div>  
   
   