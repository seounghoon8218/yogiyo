<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

<script type="text/javascript">
	if (${n==1}) {
		alert("가입인사가 등록되었습니다.");
		location.href="<%=ctxPath%>/greetingList.yo";
	} else {
		alert("등록 실패");
		location.href="<%=ctxPath%>/addGreeting.yo";
	}
	
</script>