<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
   
<style type="text/css">
	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table, #table2 {border-collapse: collapse;
	 		         width: 900px;
	 		        }
	#table th, #table td{padding: 5px;}
	#table th{width: 120px; background-color: #DDDDDD;}
	#table td{width: 750px;}
	.long {width: 470px;}
	.short {width: 120px;}
	
	.move {cursor: pointer;}
	.moveColor {color: #660029; font-weight: bold;}
</style>

<script type="text/javascript">
    
	$(document).ready(function(){
	
		$(".move").hover(function(){
						   $(this).addClass("moveColor");	
						 },
				         function(){
						   $(this).removeClass("moveColor");
		});
		
	// === 댓글쓰기는 로그인을 했을 경우에만 text type 및 버튼 활성화 시키기 === //
		if( ${sessionScope.loginuser == null} ) {
			$("#commentContent").attr("disabled",true);
			$("#btnComment").attr("disabled",true);
		}
		else {
			$("#commentContent").attr("disabled",false);
			$("#btnComment").attr("disabled",false);
		}
				
	});// end of $(document).ready()----------------------
    
	
	// === 댓글쓰기 === //
	function goAddWrite() {
		var frm = document.addWriteFrm;
		var contentVal = frm.content.value.trim();
		if(contentVal=="") {
			alert("댓글 내용을 입력하세요!!");
			return;
		}
		
		var form_data = $("form[name=addWriteFrm]").serialize();
		
		$.ajax({
			url:"<%= request.getContextPath()%>/oooBoardAddComment.yo",
			data:form_data,
			type:"POST",
			dataType:"JSON",
			success:function(json){
				var html = "";
				$.each(json, function(index, item){
					html += "<tr>";
					html += "<td style='text-align: center;'>"+item.name+"</td>";
					html += "<td>"+item.content+"</td>";
					html += "<td style='text-align: center;'>"+item.regDate+"</td>";
					html += "</tr>";
				});
				
				$("#commentDisplay").html(html);
				frm.content.value = "";
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		
	}
	
</script>

<div style="padding-left: 10%; border: solid 0px red;">
	<h1>글내용보기</h1>
	
	<table id="table" style="word-wrap: break-word; table-layout: fixed;">
		<tr>
			<th>글번호</th>
			<td>${oooboardvo.seq}</td>
		</tr>
		<tr>
			<th>성명</th>
			<td>${oooboardvo.name}</td>
		</tr>
		<tr>
           	<th>제목</th>
           	<td>${oooboardvo.subject}</td>
        </tr>
		<tr>
			<th>내용</th>
			<td>${oooboardvo.content}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${oooboardvo.readCount}</td>
		</tr>
		<tr>
			<th>날짜</th>
			<td>${oooboardvo.regDate}</td>
		</tr>
		<!-- === #146. 첨부파일 이름 및 파일 크기를 보여주고 첨부파일 다운로드 되도록 만들기 ===  -->
		<tr>
			<th>첨부파일</th>
			<td>
			<c:if test="${sessionScope.loginuser != null}">
				<a href="<%=request.getContextPath() %>/oooBoardDownload.yo?seq=${oooboardvo.seq}">
					${oooboardvo.orgFilename}
				</a>
			</c:if>
			<c:if test="${sessionScope.loginuser == null}">
				${oooboardvo.orgFilename}
			</c:if>
			</td>
			
		</tr>
		<tr>
			<th>파일크기(bytes)</th>
			<td>${oooboardvo.fileSize}</td>
		</tr>
		
		
	</table>
	
	<br/>
	
	<div style="margin-bottom: 1%;">이전글 : <span class="move" onClick="javascript:location.href='oooBoardView.yo?seq=${oooboardvo.previousseq}'">${oooboardvo.previoussubject}</span></div>
	<div style="margin-bottom: 1%;">다음글 : <span class="move" onClick="javascript:location.href='oooBoardView.yo?seq=${oooboardvo.nextseq}'">${oooboardvo.nextsubject}</span></div>
	
	<br/>
	
	<button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/oooBoardList.yo'">목록보기1</button> 
	<button type="button" onClick="javascript:location.href='${gobackURL}'">목록보기2</button>
	<button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/oooBoardEdit.yo?seq=${oooboardvo.seq}'">수정</button>
	<button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/oooBoardDel.yo?seq=${oooboardvo.seq}'">삭제</button>
	
	<!-- === #123. 답변글쓰기 버튼 추가하기(현재 보고 있는 글이 작성하려는 답변글의 원글(부모글)이 된다.) === --> 
	<button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/oooBoardAdd.yo?fk_seq=${oooboardvo.seq}&groupno=${oooboardvo.groupno}&depthno=${oooboardvo.depthno}'">답변글쓰기</button> 
	
	
	<!-- === #84. 댓글쓰기 폼 추가 === --> 
	<h3 style="margin-top: 50px;">댓글쓰기 및 보기</h3>
	<form name="addWriteFrm" style="margin-top: 20px;">
		      <input type="hidden" name="fk_userid" value="${sessionScope.loginuser.email}" />
		성명 : <input type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />  
		&nbsp;&nbsp;
		댓글내용 : <input id="commentContent" type="text" name="content" class="long" /> 
		
		<!-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) -->
		<input type="hidden" name="parentSeq" value="${oooboardvo.seq}" /> 
		
		<button id="btnComment" type="button" onclick="goAddWrite();">댓글쓰기완료</button> 
	</form>
	
	<!-- ===== #91. 댓글 내용 보여주기 ===== -->
	<table id="table2" style="margin-top: 2%; margin-bottom: 3%;">
		<thead>
		<tr>
			<th style="width: 15%; text-align: center;">댓글작성자</th>
			<th style="width: 67%; text-align: center;">내용</th>
			<th style="text-align: center;">작성일자</th>
		</tr>
		</thead>
		<tbody id="commentDisplay">
			<c:if test="${!empty commentList}">
				<c:forEach var="commentvo" items="${commentList}">
					<tr>
						<td style="text-align: center;">${commentvo.name}</td>
						<td>${commentvo.content}</td>
						<td style="text-align: center;">${commentvo.regDate}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>