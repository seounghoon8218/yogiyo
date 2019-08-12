<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctx = request.getContextPath(); %>
<style type="text/css">
	#menuRegFrm { width: 50%; display: inline-block; margin: 10px 0px 0px 25%; }
	input { height: 34px; }
	select { height: 34px; width: 110px; }
	tr { margin: 0px 0px 10px 0px }
	td { width: 180px; padding: 10px; }
	tr, td { border: 1px solid #E0E0E0; border-left: none; border-right: none; }
	.button{ padding: 10px 25% 10px 25%; display: inline-block; width: 100%; border-top: 1px solid #888; margin-top: 10px; }
	.tdTitle { width: 150px; height: 34px; border-right: 1px solid #E0E0E0; background-color: #ce252508;}
	#title { margin-left: 25%; width: 50%; border-bottom: red solid 2px;}
	#firTitle { width: 50%; margin: 30px 0px 0px 25%; border-bottom: solid #888 1px; padding-bottom: 10px; }
	#secTitle { border-top: solid #888 1px; border-bottom: solid #888 1px; padding: 50px 0px 10px 0px; margin: 10px 0px 10px 0px;}
	
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$("#menuRegister").bind("click",function(){
			
			
			var masternoReg = /^[0-9]{1,20}$/;
			var masternoTest = masternoReg.test($("#masterno").val());
			
			
			var menupriceReg = /^[0-9]{1,20}$/;
			var menupriceTest = menupriceReg.test($("#menuprice").val());
			
				
			if($("#masterno").val().trim()=="") {
				alert("사업자번호를 입력하세요.");
				$("#masterno").val("");
				$("#masterno").focus();
				return;
			} else if ($("#masterno").val().trim()!="" && !masternoTest) {
				alert("사업자번호 형식이 맞지않습니다.");
				$("#masterno").val("");
				$("#masterno").focus();
				return;
			} 
			
			if ($("#fk_menuspeccode").val().trim()=="") {
				alert("메뉴카테고리를 선택하세요.");
				$("#fk_menuspeccode").val("");
				$("#fk_menuspeccode").focus();
				return;
			}
			
			if ($("#fk_shopcategorycode").val().trim()=="") {
				alert("메뉴종류를 선택하세요.");
				$("#fk_shopcategorycode").val("");
				$("#fk_shopcategorycode").focus();
				return;
			}
			
			if ($("#menuname").val().trim()=="") {
				alert("메뉴명을 입력하세요.");
				$("#menuname").val("");
				$("#menuname").focus();
				return;
			} 
			
			if ($("#menuprice").val().trim()=="") {
				alert("메뉴가격을 입력하세요.");
				$("#menuprice").val("");
				$("#menuprice").focus();
				return;
			} else if ($("#menuprice").val().trim()!="" && !menupriceTest) {
				alert("잘못된 가격입니다.");
				$("#menuprice").val("");
				$("#menuprice").focus();
				return;
			}
			
			if ($("#menucomment").val().trim()=="") {
				alert("메뉴설명을 입력하세요.");
				$("#menucomment").val("");
				$("#menucomment").focus();
				return;
			}
		
			if ($("#attach").val()=="") {
				alert("메뉴사진을 선택하세요.");
				$("#attach").val("");
				$("#attach").focus();
				return;
			}					
			
			var frm = document.menuRegFrm;
			frm.method = "POST";
			frm.action = "<%=ctx%>/registerMenuEnd.yo";
			frm.submit();			
		}); // #menuRegister
					
	}); // document.ready
</script>
	<div id="title">
		<h2>메뉴 등록</h2>
		<h6 style="color: #888;">운영중인 음식점의 메뉴를 요기요에 등록하세요. 온라인 메뉴등록중 어려움이 있으시면 고객센터(02-3447-3612)로 연락주세요</h6>
	</div>
	<div id="firTitle">
		<span style="font-size: 15pt; font-weight: bold;">사업자 정보
		<span style="color: red; font-size: 9pt;"> *</span></span>
		<span style="font-size: 9pt;">필수 입력</span>
	</div>
	<form name="menuRegFrm" enctype="multipart/form-data" id="menuRegFrm">
		<table style="width: 100%;">			
			<tr>				
				<td class="tdTitle" style="width: 63px;">사업자번호<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<input type="text" name="masterno" id="masterno"/>
				</td>
			</tr>
		</table>			
			
		<div id="secTitle">
			<span style="font-size: 15pt; font-weight: bold; margin: 0px;">메뉴 정보</span>
		</div>
			
		<table style="width: 100%;">
			<tr>
				<td class="tdTitle">메뉴카테고리<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<select name="fk_menuspeccode" id="fk_menuspeccode">
						<option value="">선택하세요</option>
						<option value="2">주메뉴</option>
						<option value="3">사이드메뉴</option>														 
					</select>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">메뉴종류<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<select name="fk_shopcategorycode" id="fk_shopcategorycode">
						<option value="">선택하세요</option>
						<option value="1">치킨</option>
						<option value="2">피자/양식</option>
						<option value="3">중국집</option>
						<option value="4">한식</option>
						<option value="5">일식/돈까스</option>
						<option value="6">족발/보쌈</option>
						<option value="7">분식</option>
						<option value="8">까페/디저트</option>
						<option value="9">편의점</option>												
					</select>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">인기메뉴<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<input type="checkbox" name="pop_menuspeccode" id="pop_menuspeccode" value="1"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">메뉴명<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<input type="text" name="menuname" id="menuname"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">메뉴가격<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<input type="text" name="menuprice" id="menuprice" placeholder="  , 없이 입력"/>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">메뉴설명<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<textarea name="menucomment" id="menucomment" rows="5" cols="60" style="resize: none;"></textarea>
				</td>
			</tr>
			<tr>
				<td class="tdTitle">메뉴사진<span style="color: red;"> *</span></td>
				<td class="inputTd">
					<input type="file" name="attach" id="attach" size="40" style="position: relative; top: 6px;"/>
				</td>
			</tr>			
		</table>
		<div class="button">
			<input type="button" style="background-color: red;   border: none; width: 180px; height: 45px; color: white; font-weight: bold;"   value="등록" id="menuRegister" />
			<input type="reset"  style="background-color: white; border: 1px black solid; width: 180px; height: 45px; font-weight: bold;"  value="취소"/>
		</div>		
	</form>