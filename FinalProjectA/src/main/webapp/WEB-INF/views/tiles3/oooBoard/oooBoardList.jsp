<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	table, th, td {border: solid 1px gray;}

    #table {width: 970px; border-collapse: collapse;}
    #table th, #table td {padding: 5px;}
    #table th {background-color: #DDD;}
     
    .subjectStyle {font-weight: bold;
                   color: navy;
                   cursor: pointer;} 
</style>

<script type="text/javascript">
 $(document).ready(function(){
	
	$(".subject").bind("mouseover", function(event){
		var $target = $(event.target);
		$target.addClass("subjectStyle");
	});
	
	$(".subject").bind("mouseout", function(event){
		var $target = $(event.target);
		$target.removeClass("subjectStyle");
	});
	
	
	$("#searchWord").keydown(function(event) {
		 if(event.keyCode == 13) {
			 // 엔터를 했을 경우
			 goSearch();
		 }
	 });
	
	if(${paraMap != null}) {
		$("#searchType").val("${paraMap.searchType}");
		$("#searchWord").val("${paraMap.searchWord}");
	}
	
	<!-- === #102. 검색어 입력시 자동글 완성하기 2 === -->
	$("#displayList").hide();
	
	$("#searchWord").keyup(function(){
		
		var form_data = { searchType:$("#searchType").val()
				         ,searchWord:$("#searchWord").val() };
		
		$.ajax({
			url:"<%= request.getContextPath()%>/oooBoardwordSearchShow.yo",
			type:"GET",
			data:form_data,
			dataType:"JSON",
			success:function(json){
			
			<!-- === #107. 검색어 입력시 자동글 완성하기 7 === -->	
				if(json.length > 0) { 
					// 검색된 데이터가 있는 경우임. 만약에 조회된 데이터가 없을 경우 if(json == null) 이 아니고 if(json.length == 0) 이라고 써야 한다. 
					
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
		});
	});
	
	<!-- === #108. 검색어 입력시 자동글 완성하기 8 === -->
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
	});
	
	
 });// end of $(document).ready()--------------------
 
 
 function goView(seq) {
	
	 var frm = document.goViewFrm;
	 frm.seq.value = seq;
	 
	 frm.method = "GET";
	 frm.action = "oooBoardView.yo";
     frm.submit();
 }
 
 function goSearch() {
	var frm = document.searchFrm;
	frm.method = "GET";
	frm.action = "<%= request.getContextPath()%>/oooBoardList.yo";
	frm.submit();
 }
 function goAdd(){
	 var frm = document.searchFrm;
	 frm.method = "GET";
	 frm.action = "<%=request.getContextPath()%>/oooBoardAdd.yo";
	 frm.submit();
 }
 
</script>  

<div style="padding-left: 10%;">
	<h2 style="margin-bottom: 30px;">추천 게시판</h2>
	
	<table id="table">
		<tr>
			<th style="width: 70px;  text-align: center;">글번호</th>
			<th style="width: 360px; text-align: center;">제목</th>
			<th style="width: 70px;  text-align: center;">성명</th>
			<th style="width: 180px; text-align: center;">날짜</th>
			<th style="width: 70px;  text-align: center;">조회수</th>
			<%-- === #143. 파일과 크기를 보여주도록 수정 === --%>
			<th style="width: 70px;  text-align: center;">파일</th>
			<th style="width: 70px;  text-align: center;">크기(bytes)</th>
			
			
		</tr>	
		<c:forEach var="oooboardvo" items="${boardList}" varStatus="status">
			<tr>
				<td align="center">${oooboardvo.seq}</td>
				<td align="left"> 
			
				   <c:if test="${oooboardvo.depthno == 0}">
					   <c:if test="${oooboardvo.commentCount > 0}">	
					   	  <span class="subject" onclick="goView('${oooboardvo.seq}');">${oooboardvo.subject}&nbsp;<span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${oooboardvo.commentCount}</span>]</span></span>
					   </c:if>
					   <c:if test="${oooboardvo.commentCount == 0}">
					   	  <span class="subject" onclick="goView('${oooboardvo.seq}');">${oooboardvo.subject}</span>
					   </c:if>
				   </c:if>
				   
				   <!-- 답변글인 경우 -->
				   <c:if test="${oooboardvo.depthno > 0}">
					   <c:if test="${oooboardvo.commentCount > 0}">	
					   	  <span class="subject" onclick="goView('${oooboardvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${oooboardvo.depthno * 20}px;">└Re&nbsp;</span>${oooboardvo.subject}&nbsp;<span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${oooboardvo.commentCount}</span>]</span></span>
					   </c:if>
					   <c:if test="${oooboardvo.commentCount == 0}">
					   	  <span class="subject" onclick="goView('${oooboardvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${oooboardvo.depthno * 20}px;">└Re&nbsp;</span>${oooboardvo.subject}</span>
					   </c:if>
				   </c:if>
				</td>
				<td align="center">${oooboardvo.name}</td>
				<td align="center">${oooboardvo.regDate}</td>
				<td align="center">${oooboardvo.readCount}</td>
				
				<!-- === #144. 파일과 크기를 보여주도록 수정 === >
				/Board/src/main/webapp/resources/images/disk.gif 이미지 파일을 사용하여 첨부파일의 유무를 보여주도록 한다.
			
				-->
				<td align="center">
					<c:if test="${not empty oooboardvo.fileName}" >
						<img alt="" src="<%=request.getContextPath()%>/resources/images/disk.gif"  />
					</c:if>
				</td>
				<td align="center">
				<c:if test="${not empty oooboardvo.fileSize}" >
					${oooboardvo.fileSize} 
				</c:if>
				</td>
			
		    </tr>
		</c:forEach>
	</table>
	<br/>
	
	<form name="goViewFrm">
		<input type="hidden" name="seq"/>
		<input type="hidden" name="gobackURL" value="${gobackURL}"/> 
	</form>
	
	<!-- === #120. 페이지바 보여주기 === --> 
	<div align="center" style="width: 70%; border: 0px solid gray; margin-left: 50px; margin-bottom: 20px;" >
		${pagebar}
	</div>
	
	<!-- === #96. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --> 
	<form name="searchFrm" style="">
		<select name="searchType" id="searchType" style="height: 26px;">
			<option value="subject">글제목</option>
			<option value="name">글쓴이</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
		<button type="button" onclick="goSearch()">검색</button>
		<button type="button" onclick="goAdd()">글쓰기</button>
		
	</form>
	
	
	<!-- === #101. 검색어 입력시 자동글 완성하기 1 === -->
	<div id="displayList" style="width: 314px; height: 100px; overflow: auto; margin-left: 70px; margin-top: -1px; border-top: 0px; border: solid gray 1px;">
	</div>
	
	
</div>
  





