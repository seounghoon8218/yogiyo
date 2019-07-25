<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctx = request.getContextPath(); %>
<style type="text/css">

</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$("#menuRegister").bind("click",function(){
			
			
			var masternoReg = /^[0-9]+$/;
			var bool = masternoReg.test($("#masterno").val())
	
			if (!bool) {
				alert("아니 시벌");
				$("#masterno").val("");
				$("#masterno").focus();
				return;
			}
			
			var frm = document.menuRegFrm;
			frm.method = "POST";
			frm.action = "<%=ctx%>/menuRegisterEnd.yo";
			frm.submit();			
		}); // #menuRegister
		
		
		
	}); // document.ready
</script>

<div>
	<form name="menuRegFrm">
		<table>
			<tr>
				<%-- 현제 로그인되어진 사용자에 한에서 세션에 정보저장후 불러오기 --%>
				<td>사업자번호</td>
				<td>
					<input type="text" name="masterno" id="masterno"/>
				</td>
			</tr>
			<tr>
				<td>메뉴카테고리</td>
				<td>
					<select name="fk_menuspeccode" id="fk_menuspeccode">
						<option value="">선택하세요</option>
<%-- 						<c:forEach items="${menuspecList}" var="menuspecmap"> --%>
<%-- 							<option value="${menuspecmap.menuspeccode}">${menuspecmap.menuspeccode}</option> --%>
<%-- 						</c:forEach>													 --%>
					</select>
				</td>
			</tr>
			<tr>
				<td>메뉴명</td>
				<td>
					<input type="text" name="menuname" id="menuname"/>
				</td>
			</tr>
			<tr>
				<td>메뉴가격</td>
				<td>
					<input type="text" name="menuprice" id="menuprice"/>
				</td>
			</tr>
			<tr>
				<td>메뉴설명</td>
				<td>
					<textarea name="menucomment" id="menucomment" rows="5" cols="60" style="resize: none;"></textarea>
				</td>
			</tr>
			<%-- 이미지 첨부파일 --%>
			<tr>
				<td>메뉴사진</td>
				<td>
					<input type="file" name="menuimage" id="menuimage" size="40"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="등록" id="menuRegister" />
					<input type="reset" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</div>