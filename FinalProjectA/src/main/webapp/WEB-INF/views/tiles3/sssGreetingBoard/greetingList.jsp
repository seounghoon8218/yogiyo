<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
 	th, td { border-bottom: 1px solid #ff000024; padding: 15px; text-align: center;}
	.titleStyle { font-weight: bold; cursor: pointer; }
	#containArea { margin-left: 26%; margin-right: 25%; width: 50%; display: inline-block; 
   					margin-left: 26%; margin-right: 25%; width: 50%; display: inline-block; height: 750px; }
   	#infoBoard { margin-bottom: 50px; }
   	#searchFrm { text-align: center; margin-top: 25px; }
   	
</style>


<script type="text/javascript">

	
	$(document).ready(function(){
		$(".title").bind("mouseover", function(event){
			var $target = $(event.target);
			$target.addClass("titleStyle");
		})
		
		$(".title").bind("mouseout", function(event){
			var $target = $(event.target);
			$target.removeClass("titleStyle");
		})
		
		
		
		
	}) // end of document.ready
	
	function goGreetingView(seq){
		var frm = document.goGreetingViewFrm;
		frm.seq.value = seq;
		
		frm.method = "GET";
		frm.action = "greetingView.yo";
		frm.submit();
		
	}
	
	function goSearch(){
		
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%=request.getContextPath()%>/greetingList.yo";
		frm.submit();
	}
	
	
</script>

<div id="containArea">
	<div id="infoBoard">
		<h3 style="padding-top: 20px;"> 가입인사방</h3>
		
	</div>
	<table style="width: 100%; border-top: solid 2px red;">
		<thead>
		
			<tr>
				<th style="width: 80px;">말머리</th>
				<th style="text-align: left">제목</th>
				<th style="width: 120px">작성자</th>
				<th style="width: 140px; ">작성일</th>
				<th style="width: 80px">조회수</th>
			</tr>
			
			<c:forEach items="${greetingList}" var="greetingList">
				<tr>
					<td class="title" onclick="goGreetingView('${greetingList.seq}')">${greetingList.seq}</td>
					<%-- 댓글이 없는 게시글인 경우 --%>
					<c:if test="${greetingList.commentCount == 0 }">
						<td style="text-align: left" class="title" onclick="goGreetingView('${greetingList.seq}')">${greetingList.title}</td>
					</c:if>
					
					<%-- 댓글이 있는 게시글인 경우 --%>
					<c:if test="${greetingList.commentCount > 0 }">
						<td style="text-align: left" class="title" onclick="goGreetingView('${greetingList.seq}')">${greetingList.title}<span style="color: red;">[ ${greetingList.commentCount} ]</span></td>
					</c:if>					
					
					
					<td>${greetingList.name}</td>
					<td>${greetingList.regDate}</td>
					<td>${greetingList.readCount}</td>
				</tr>	
			</c:forEach>
		</thead>
	</table>
	<button type="button" style=" margin-right: 20px; float: right; margin-top: 10px;" onclick="javascript:location.href='<%=request.getContextPath()%>/addGreeting.yo'">글쓰기</button>
	
	<form name="goGreetingViewFrm">
		<input type="hidden" name="seq"/>
		<input type="hidden" name="goBackUrl" value="${goBackUrl}"/>
	</form>
	
	<div align="center">
		${pageBar}
	</div>
	
	<form name="searchFrm" id="searchFrm">
		<select name="searchType" id="searchType" style="height: 40px;">
			<option value="title">글제목</option>
			<option value="name">글작성자</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" style="height: 40px;"/>
		<button type="button" onclick="goSearch()" id="btnSearch" style="height: 40px; width: 80px;">검색</button>		
	</form>
</div>
