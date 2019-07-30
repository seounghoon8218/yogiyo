<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<script type="text/javascript">
	if (${n==1}) {
		alert("메뉴등록 성공");
		location.href="<%=ctxPath%>/registerMenu.yo";
		// **추후에 관리자전용페이지로 가지게
	} else {
		alert("메뉴등록 실패");
		location.href="<%=ctxPath%>/registerMenu.yo";
	}
	
</script>