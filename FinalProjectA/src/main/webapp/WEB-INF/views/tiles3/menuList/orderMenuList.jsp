<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	
.le{
    right: 95px;
    width: 30px;
    height: 30px;
    border: 1px solid #ccc;
    font-size: 13pt;
    line-height: 2;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
}

.mi{
    right: 45px;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
    width: 50px;
    height: 30px;
    font-weight: bold;
    line-height: 2;
    text-align: center;
}

.rit{
    right: 15px;
    width: 30px;
    height: 30px;
    border: 1px solid #ccc;
    line-height: 2;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
}

#sty{
	border-right: 1px solid gray;
	
}

#paysty{
	background-color: red;
	cursor: pointer;
}

	
</style>
<script type="text/javascript">
	
	$(document).ready(function(){
		checkboxprice();
		$(".MenucheckList").click(function(){
			
			checkboxprice();
			
		});
	});
	
	
	<%-- 박성훈-------------- --%>
	function checkboxprice() {
		var cnt = 0;
		var totalmoney = 0;
		var totalmenuName = "";
		$(".MenucheckList").each(function(){
			cnt++;
			
			if($(this).is(":checked")){
				totalmoney += parseInt($("#totalprice"+cnt).val());
				totalmenuName += " / " + $("#menunamePlus"+cnt).val();
				$("#totalmenuName").val(totalmenuName);
			}
		});
		
		$("#totalprice").html(totalmoney);
	}

	function disctn(count,menuprice) {
		var num = $("#mi"+count).text();
		if(num != 1){
		num--;				
		}
		$("#mi"+count).text(num);
		var totalprice = menuprice * num;
		$("#menuprice"+count).text(totalprice);
		$("#totalprice"+count).val(totalprice);
		checkboxprice();
	}
	
	function addctn(count,menuprice) {
		var num = $("#mi"+count).text();
		if(num != 10){
		num++;				
		}
		$("#mi"+count).text(num);
		var totalprice = menuprice * num;
		$("#menuprice"+count).text(totalprice);
		$("#totalprice"+count).val(totalprice);
		checkboxprice();
	}
	
	function deleteMenu(orderno){
		
		location.href="<%= request.getContextPath()%>/kkk/orderMenuList.yo?orderno="+orderno;
		
	}
	
	function premyinfowrite() {
		
		var menuname = $("#totalmenuName").val();
		var menucode = 0;
		var masterno = $("#masterno1").val();
		
		myinfowrite(menuname,menucode,masterno)
	}
	
	function myinfowrite(menuname,menucode,masterno) {

    	var totalprice = $("#totalprice").text();
    	
    	var url = "<%=request.getContextPath()%>/myinfowrite.yo?menuname="+menuname+"&menucode="+menucode+"&masterno="+masterno+"&totalprice="+totalprice;
    	    	
    	window.open(url,"myinfowrite",
		"left=350px, top=100px, width=500px, height=420px");
    		
    	
    }
	
	function goOrder(menuname,menucode,masterno,addr2,tel,yocheong) {
		
		var totalprice = $("#totalprice").text();
    	
		// 여기 결제까지 잘왔어 .. addr1 , 2 , tel 정보는 세션스토리지에 넣어뒀으니까 여기서 꺼내서 밑에 url에 추가로 보내줘서 controll단에서 받아서 결제 ㄱ
		
	 	var addr1 = sessionStorage.getItem('addr1');
		
		
		
		
		
    	
    	// 아임포트 결제 팝업창 띄우기!
    	var url = "<%=request.getContextPath()%>/payment.yo?menuname="+menuname+"&menucode="+menucode+"&masterno="+masterno+"&totalprice="+totalprice+"&addr1="+addr1+"&addr2="+addr2+"&tel="+tel+"&yocheong="+yocheong;
    	
    	window.open(url,"payment",
    					"left=350px, top=100px, width=820px, height=600px");
    	
	} // end of 주문
	
	 function goCoinUpdate(menuname,menucode,masterno,totalprice,addr1,addr2,tel,yocheong) { // 실제 결제된 만큼 DB 업데이트 해주기..
			
			var frm = document.paymentUpdateFrm;
			frm.menuname.value = menuname;
			frm.menucode.value = menucode;
			frm.masterno.value = masterno;
			frm.totalprice.value = totalprice;
			frm.addr1.value = addr1;
			frm.addr2.value = addr2;
			frm.tel.value = tel;
			frm.yocheong.value = yocheong;
			frm.method = "POST";
			frm.action = "<%=request.getContextPath()%>/paymentEnd.yo";
			frm.submit();
			
		}// end of function goCoinUpdate(idx, coinmoney)----
	
	<%-- 박성훈-------------- --%>

</script>
<body>
	
	<div style='margin-left:100px; width: 80%; border: 1px solid gray; background-color: #fff; border-right: none;'>
		<table style="width: 100%; text-align: center;" >				
			<tr style="border-bottom: 1px solid gray; font-size: 20px; font-weight: bold;" >
				<td id="sty" >선택</td><td id="sty" >매장</td><td id="sty" >메뉴</td><td id="sty" >가격</td><td id="sty" >수량</td><td id="sty" >삭제</td>
			</tr>
			
			<c:if test="${cartList.size() == 0}">
				<tr><td colspan="6" style="font-weight: bold; font-size: 20pt; padding: 30px 0;">주문표가 비어있습니다.</td></tr>
			</c:if>
			
			<c:if test="${cartList.size() != 0}">
				<c:forEach var="showMenu" items="${cartList}" varStatus="status">
					<tr style="border-bottom: 1px solid gray;" >
						<td id="sty" align="center" >
							<input type="checkbox" class="MenucheckList" />
						</td>
						<td id="sty" >
							<img style="height: 80px; width: 80px; vertical-align: middle; margin: 10px; margin-bottom: 0px;" src='<%=request.getContextPath()%>/resources/images/${showMenu.shopimage}' /><br/><span>${showMenu.shopname }</span>
						</td>
						<td id="sty" style='margin-right:160px;' >
							<img style="height: 80px; width: 80px; vertical-align: middle; margin: 10px;" src='<%=request.getContextPath()%>/resources/images/${showMenu.filename}' />
							<span>${showMenu.menuname}</span>
						</td>
						<td id="sty" style='margin-right:160px;'>
							<strong>가격 </strong><span id="menuprice${status.count}">${showMenu.menuprice} </span>원
							<input type="hidden" id="totalprice${status.count}" value="${showMenu.menuprice}" />
							<input type="hidden" id="menunamePlus${status.count}" value="${showMenu.menuname}" />
							<input type="hidden" id="masterno${status.count}" value="${showMenu.masterno}" />
						</td>
						<td style='display: inline-flex; margin-top: 40px;' colspan="1">					
							<div class="le" id='le${status.count}' onclick='disctn(${status.count},${showMenu.menuprice})' >-</div>
							<div class="mi" id='mi${status.count}'>1</div>
							<div class="rit" id='rit${status.count}' onclick='addctn(${status.count},${showMenu.menuprice})'>+</div>					
						</td>
						<td style="border-left: 1px solid gray; border-right: 1px solid gray;">
							<strong onclick="deleteMenu(${showMenu.orderno});" style="cursor: pointer; text-decoration: underline;">삭제</strong>
						</td>
					</tr>
				</c:forEach>
				
				
				<tfoot style="border-top: 1px solid gray; border-right: 1px solid gray;">
					<tr>
						<td colspan="4">
							<div style='padding:15px 0; font-size: 16px; '><strong style='margin-right:250px;'>총 주문금액</strong><span id='totalprice'></span> 원</div>
							<input type="hidden" id="totalmenuName" value="" />
						</td>
						<td colspan="2" id="paysty" >
							<a id="payend" style="color: white; font-weight: bold; font-size: 16pt; " onclick="premyinfowrite()" >결제하기</a>
						</td>
					</tr>
				</tfoot>	
			</c:if>
			
			
		</table>
			
	</div>

	<!-- 결제테이블업데이트 박성훈 -->	
<form name="paymentUpdateFrm">
	<input type="hidden" id="menuname" name="menuname" />
	<input type="hidden" id="menucode" name="menucode" />
	<input type="hidden" id="masterno" name="masterno" />
	<input type="hidden" id="totalprice" name="totalprice" />
	<input type="hidden" id="addr1" name="addr1" />
	<input type="hidden" id="addr2" name="addr2" />
	<input type="hidden" id="tel" name="tel" />
	<input type="hidden" id="yocheong" name="yocheong" />
</form>
<!-- 결제테이블업데이트 박성훈 -->
	
</body>
</html>