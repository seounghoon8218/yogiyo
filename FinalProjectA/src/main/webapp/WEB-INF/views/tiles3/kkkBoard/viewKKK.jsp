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
	.long {width: 400px;}
	.short {width: 120px;}
	
	.move {cursor: pointer;}
	.moveColor {color: #660029; font-weight: bold;}
	a {text-decoration: none;}
	
	
	
	#content {
	    width: 100%;
	    margin: 0;
	    padding: 0;
	}
	.notice-wrap {
	    max-width: 720px;
	    margin: 20px auto 40px auto;
	}
	.notice-list {
	    margin: 0 10px;
	}
	
	.notice-list > ul {
	    border: 1px solid #ccc;
	    background-color: #fff;
	    padding: 0;
	    margin: 0;
	}
	.n-content {
	    border: 1px solid #ccc;
	    border-top: 1px solid #eee;
	    background: #fff;
	    padding: 0;
	    margin-top: -1px;
	}
	
	.notice-list > ul > li {
	    border-bottom: 1px solid #ccc;
	    list-style: none;
	    padding: 18px 16px 16px 16px;
	}
		
		
	.btn-list {
		cursor:pointer;
	    display: inline-block;
	    border: 1px solid #ccc;
	    background-color: white;
	    padding: 7px 21px 7px 21px;
	    margin: 10px 0 0 0;
	}

	#stycon > img{
		width: 100%;
	}
	
	dl > img{
		width: 100%;
	}
	
	#btnComment{
		float: right;
	}		
	
</style>

<script type="text/javascript">
    
	$(document).ready(function(){
	
		$(".move").hover(function(){
						   $(this).addClass("moveColor");	
						 },
				         function(){
						   $(this).removeClass("moveColor");
		});
		
		
		if( ${sessionScope.loginuser == null}){			
			$("#name1").attr("disabled",true);
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
			url:"<%= request.getContextPath()%>/addKKKComment.yo",
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
				history.go(0);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		
	}
	
</script>

<!--/ 공지사항 Start -->
<div class="notice-wrap">
  <h2>공지사항</h2>
  <div class="notice-list">
    <ul>
      <li>
        <div class="n-tit">${boardvo.subject}</div>
        <div class="n-date">${boardvo.regDate} ~ ${endDate}</div>
      	
      		<div style="float: left; font-size: 8pt;" >
				조회수
				${boardvo.readCount}
			</div>
      		<c:if test="${boardvo.orgFilename != null}">
				<div style="float: right; font-size: 8pt;">첨부파일 :
					<c:if test="${sessionScope.loginuser != null}">
						<a href="<%= request.getContextPath()%>/KKKdownload.yo?seq=${boardvo.seq}">
							${boardvo.orgFilename}
						</a>
					</c:if>
					<c:if test="${sessionScope.loginuser == null}">
						${boardvo.orgFilename}
					</c:if>
				</div>
			</c:if>			
			
		</li>
		<li style="height: 50px; background-color: #ffb400; color: white;">
			${boardvo.subject}&nbsp;&nbsp;&nbsp;(${boardvo.regDate} ~ ${endDate})
		</li>
		<li>
			<p id="stycon" style="word-break: break-all;">${boardvo.content}</p>
		</li>
    </ul>


    <div class="n-body">
      <div class="btn-list"  onClick="javascript:location.href='<%= request.getContextPath() %>/kkkBoardList.yo'">목록으로</div>
      <c:if test="${sessionScope.loginuser.email == 'admin@gmail.com' }">
	      <div class="btn-list"  onClick="javascript:location.href='<%= request.getContextPath() %>/editKKK.yo?seq=${boardvo.seq}'">수정</div>
	      <div class="btn-list"  onClick="javascript:location.href='<%= request.getContextPath() %>/delKKK.yo?seq=${boardvo.seq}'">삭제</div>
	      <%-- <div class="btn-list"  onClick="javascript:location.href='<%= request.getContextPath() %>/addKKKBoard.yo?fk_seq=${boardvo.seq}&groupno=${boardvo.groupno}&depthno=${boardvo.depthno}'">글추가</div> --%>
      </c:if>      
    </div>
  </div>

	
	<br/>
	<c:if test="${boardvo.previousseq != null}">
		<div style="margin-bottom: 1%;">이전글 : <span class="move" onClick="javascript:location.href='viewKKK.yo?seq=${boardvo.previousseq}'">${boardvo.previoussubject}</span></div>
	</c:if>	
	
	<c:if test="${boardvo.nextseq != null}">
		<div style="margin-bottom: 1%;">다음글 : <span class="move" onClick="javascript:location.href='viewKKK.yo?seq=${boardvo.nextseq}'">${boardvo.nextsubject}</span></div>
	</c:if> 
	<br/>
		
	<!-- === #84. 댓글쓰기 폼 추가 === --> 
	<h3 style="margin-top: 50px;">댓글쓰기</h3>
	
	<form name="addWriteFrm" style="margin-top: 20px;">
		      <input type="hidden" name="fk_userid" value="${sessionScope.loginuser.email}" />
		성명 : <input id="name1" type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />  
		&nbsp;&nbsp;
		댓글내용 : <input id="commentContent" type="text" name="content" class="long" /> 
		
		<!-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) -->
		<input type="hidden" name="parentSeq" value="${boardvo.seq}" /> 
		
		<button id="btnComment" type="button" onclick="goAddWrite();">댓글등록</button> 
	</form>
	
	
	<!-- ===== #91. 댓글 내용 보여주기 ===== -->
	<table id="table2" style="margin-top: 2%; margin-bottom: 3%; width: 100%">
		<thead>
		<c:if test="${empty commentList}">
			<tr>
				
			</tr>
		</c:if>
		<c:if test="${!empty commentList}">
			<tr>
				<th style="width: 15%; text-align: center;">댓글작성자</th>
				<th style="width: 60%; text-align: center;">내용</th>
				<th style="text-align: center;">작성일자</th>
			</tr>
		</c:if>
		</thead>
		<tbody id="commentDisplay">
			<c:if test="${!empty commentList}">
				<c:forEach var="commentvo" items="${commentList}">
					<tr>
						<td style="text-align: center;">${commentvo.name}</td>
						<td style="text-align: center;">${commentvo.content}</td>
						<td style="text-align: center;">${commentvo.regDate}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

</div>
<!-- 공지사항 End /-->
	









