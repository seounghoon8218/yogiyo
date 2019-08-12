<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<script type="text/javascript">
   if(${n==1}) { 
      alert("글쓰기 성공!!");
      location.href="<%= ctxPath%>/moonlist.yo";
   }
   else { 
      alert("글쓰기 실패!!");
      location.href="<%= ctxPath%>/moonadd.yo";
   }   
   
</script> 