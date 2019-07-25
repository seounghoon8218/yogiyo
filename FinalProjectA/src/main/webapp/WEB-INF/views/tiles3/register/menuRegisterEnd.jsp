<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<script type="text/javascript">
	if (${n==1}) {
		alert("메뉴등록 성공");
		location.href="<%=ctxPath%>/yogiyo";
	} else {
		alert("메뉴등록 실패");
		location.href="<%=ctxPath%>/yogiyo";
	}
	
</script>