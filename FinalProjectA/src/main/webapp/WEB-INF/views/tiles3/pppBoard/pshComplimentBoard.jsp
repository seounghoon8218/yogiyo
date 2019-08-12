<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	table,th,td{
		border: solid 1px gray;
	}
	#table {
		width: 970px;
		border-collapse: collapse;
	}
	#table th, #table td {
		padding: 5px;
		text-align: center;
	}
	#table th{
		background: #DDD; 
	}
	.subject{
		cursor: pointer;
	}
	.subjectStyle{
		color: navy;
		font-weight: bold;
		
	}
	
	#td:hover {
		background-color: yellow;
	}
}
	
}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$(".subject").bind("mouseover",function(event){
			var $target = $(event.target);
			$target.addClass("subjectStyle");
		});
		
		$(".subject").bind("mouseout",function(event){
			var $target = $(event.target);
			$target.removeClass("subjectStyle");
		});
	
		
		$("#searchWord").keydown(function(event) {
			  if (event.keyCode == '13') {
				  goSearch();
			  }
		});
		
		if(${requestScope.paraMap != null }){
	         $("#searchType").val("${paraMap.searchType}");
	         $("#searchWord").val("${paraMap.searchWord}");
	     }
		
		<!-- === #105. 검색어 입력시 자동글 완성하기2 ===   -->
		$("#displayList").hide();
		
		$("#searchWord").keyup(function(){

			var form_data = {searchType:$("#searchType").val(),
							 searchWord:$("#searchWord").val() };
			
			$.ajax({
				url:"<%=request.getContextPath()%>/pppwordSearchShow.yo",
				type:"GET",
				data:form_data,
				dataType:"JSON",
				success:function(json){
					// #110. 검색어 입력시 자동글 완성하기
						if(json.length > 0) { // 검색된 데이터가 있는 경우임. 만약에 조회된 데이터가 없을 경우 if(json == null) 이 아니고 if(json.length == 0) 이라고 써야 한다. 
						
						var html = "";
					
						$.each(json, function(entryIndex, item){
							var word = item.word;
							var index = word.toLowerCase().indexOf( $("#searchWord").val().toLowerCase() ); 
							//  alert("index : " + index);
							var len = $("#searchWord").val().length;
							var result = "";
							
							result = "<span class='first' style='color:blue;'>" +word.substr(0, index)+ "</span>" + "<span class='second' style='color:red; font-weight:bold;'>" +word.substr(index, len)+ "</span>" + "<span class='third' style='color:blue;'>" +word.substr(index+len, word.length - (index+len) )+ "</span>";  
							
							html += "<span style='cursor:pointer;'>"+ result +"</span><br/>"; 
						});
						
						$("#displayList").html(html);
						$("#displayList").show();
					}
					else {
						// 검색된 데이터가 존재하지 않을 경우
						$("#displayList").hide();
					} // end of if ~ else ----------------
				},
				error: function(request, status, error){
		            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		        }
			
			}); // end of ajax--
			
		}); // end of keyup--
		
		// #111. 검색어 입력시 자동글 완성하기
		$("#displayList").click(function(event){
			var word = "";
			var $target = $(event.target);
			
			if($target.is(".first")) {
				word = $target.text() + $target.next().text() + $target.next().next().text();
			}
			else if($target.is(".second")) {
				word = $target.prev().text() + $target.text() + $target.next().text();
			}
			else if($target.is(".third")) {
				word = $target.prev().prev().text() + $target.prev().text() + $target.text();
			}
			
			$("#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
			
			
			$("#displayList").hide();
			
			goSearch();
			
		}); // end of $("#displayList").click----------
		
	}); // end of ready---
	
	function goView(seq) {
		var frm = document.goViewFrm;
		frm.seq.value = seq;
		frm.method = "GET";
		frm.action = "<%=request.getContextPath()%>/pppview.yo";
		frm.submit();
		
	}
	
	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath()%>/pshComplimentBoard.yo";
		frm.submit();
	}
	
	
</script>

<div style="padding-left:10%; ">
	<h2 style="margin-bottom: 30px; font-weight: bold; color: red; ">신고게시판</h2>
	<div style="border: solid 5px red; width: 970px; margin-bottom: 20px;" ></div>
	<div style="margin-left: 910px;">
	<button type="button" onclick="location.href='<%=request.getContextPath()%>/pppadd.yo'" >글쓰기</button>
	</div>
	<table id="table">
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>성명</th>
			<th>날짜</th>
			<th>조회수</th>
		<%-- === #146. 파일과 크기를 보여주도록 수정 === --%>
			<th>파일</th>
			<th>크기(bytes)</th>
		</tr>
			<!-- 댓글쓰기 있는 게시판 -->
			<%-- <c:forEach var="boardvo" items="${boardList }" varStatus="status">
				<tr>
					<td style="width: 70px;">${boardvo.seq }</td>
					<td id="td" style="width: 370px;"><span class="subject" onclick="goView('${boardvo.seq }');">${boardvo.subject }</span>
					<c:if test="${boardvo.commentCount != 0}"> <!-- 댓글이 없는 게시판은 -->
					[<span style="color: red;font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount }</span>]
					</c:if> --%>
			
			<!-- 댓글쓰기 및 답변형 게시판 -->
			<!--  답변글인 경우 글제목 앞에 └Re 라는 글자를 붙이도록 하겠다. -->
			<c:forEach var="boardvo" items="${boardList }" varStatus="status">
				<tr >
					<c:if test="${boardvo.depthno != 0 }">
						<td style="width: 70px;">${boardvo.seq }</td>
						<td id="td" style="width: 370px; text-align: left;"><span class="subject" onclick="goView('${boardvo.seq }');"><span style="color: red; font-style: italic; padding-left:${boardvo.depthno * 20}px;">└Re&nbsp;</span>${boardvo.subject }</span>
						<c:if test="${boardvo.commentCount != 0}"> 
						<!-- 댓글이 없는 게시판은 -->
						[<span style="color: red;font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount }</span>]
						</c:if>	
					</c:if>
					<c:if test="${boardvo.depthno == 0 }">
						<td style="width: 70px;">${boardvo.seq }</td>
						<td id="td" style="width: 370px; text-align: left;"><span class="subject" onclick="goView('${boardvo.seq }');">${boardvo.subject }</span>
						<c:if test="${boardvo.commentCount != 0}"> 
						<!-- 댓글이 없는 게시판은 -->
						[<span style="color: red;font-size: 9pt; font-style: italic; font-weight: bold;">${boardvo.commentCount }</span>]
						</c:if>	
					</c:if>
					
					
					</td>
					<td style="width: 70px;">${boardvo.name }</td>
					<td style="width: 170px;">${boardvo.regDate }</td>
					<td style="width: 70px;">${boardvo.readCount }</td>
					
					<%-- #147. 파일의 크기를 보여주도록 수정 --%>
					<%-- /Board/src/main/webapp/resources/images/disk.gif 이미지 파일을 사용하여 첨부파일의 유무를 보여주도록 한다. --%>
					<td align="center">
						<c:if test="${!empty boardvo.fileName }">
							<img src="<%=request.getContextPath() %>/resources/images/disk.gif" />
						</c:if>
						
					</td>
					<td align="center">
						<c:if test="${!empty boardvo.fileSize}">
							${boardvo.fileSize } 
						</c:if>
					</td>
					
				</tr>
			</c:forEach>			
	</table>
	<form name="goViewFrm">
		<input type="hidden" value="" name="seq"/> 
	</form>
	
	<!-- === #123. 페이지바 보여주기 === -->
	<div align="center" style="width: 55%; border: 0px solid gray; margin-top: 30px;">
		${pagebar}
	</div>
	
	
	<!-- #99. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. -->
	<form name="searchFrm" style="margin-top: 30px;">
		<select name="searchType" id="searchType" style="height: 25px;">
			<option value="subject">글제목</option>
			<option value="name">글쓴이</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" />
		<button type="button" onclick="goSearch()">검색</button>
	</form>
	
	<!-- === #104. 검색어 입력시 자동글 완성하기1 === -->
	<div id="displayList" style="width: 315px; height: 100px; overflow: auto; margin-left: 70px; border-top:0; border: solid 1px gray; "><!-- overflow: auto; == height 넘어가면 자동 스크롤 생겨라 -->
	
	</div>
	
</div>









