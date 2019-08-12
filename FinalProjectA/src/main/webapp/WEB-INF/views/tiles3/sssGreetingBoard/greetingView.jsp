<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
	.previousTitle:hover { cursor: pointer; font-weight: bold; }
	.nextTitle:hover { cursor: pointer; font-weight: bold; }
	#containBoard { margin-left: 26%; width: 50%; display: block; }
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#replyDiv").hide();
		
		if( ${sessionScope.loginuser == null} ) {
			$("#commentContent").attr("disabled",true);
			$("#btnComment").attr("disabled",true);
		}
		else {
			$("#commentContent").attr("disabled",false);
			$("#btnComment").attr("disabled",false);
		}
		
		
		
	
	}); // end of document.ready
	
	function replyComment(){
		
	}
	
	// 댓글쓰기
	function goAddGreetingComment(){
		
		var frm = document.addGreetingComment;
		
		var contentVal = frm.content.value.trim();
		if (contentVal=="") {
			alert("댓글 내용을 입력하세요 !!");
			return;
		}
		
		var form_data = $("form[name=addGreetingComment]").serialize();
		
		$.ajax({
			url:"<%=request.getContextPath()%>/addGreetingComment.yo",
			data:form_data,
			type:"POST",
			dataType:"JSON",			
			success:function(json){
				var html = "";
				$.each(json, function(index, item){
					
					if (item != null) {
						if (item.depthno == 0) {
							html += "<tr>";
							html += "<td>"+item.name+"</td>&nbsp;&nbsp;<td>"+item.content+"</td><td><span style='color: red;'></span><span>"+item.regDate+"</span></td>";
							html += "<td><span id='replyComment' onclick='replyComment();' style='cursor:pointer;'>&nbsp;&nbsp;τ 답글</span><td>";
							html += "</tr>";
						} else if (item.depthno > 0) {
							html += "<tr>";
							html += "<td><span style='color: coral; font-weight: bold; padding-left:"+(item.depthno*20)+"px;'> ㄴ</span>"+item.name+"&nbsp;&nbsp;"+item.content+"<span style='color: red;'></span><span>"+item.regDate+"</span>";
							html += "<span id='replyComment' onclick='replyComment();' style='cursor:pointer;'>&nbsp;&nbsp;τ 답글</span></td>";
							html += "</tr>";				
						}
						
					} 
					
					
				});
				 
				$("#commentDisplay").html(html);
				frm.content.value = ""; 
							 			
			}, error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
		}); // ajax
		
	} // goAddGreetingComment()
	
</script>
	
	
	<div id="containBoard">
		<div>
			<c:if test="${sessionScope.loginuser.email == gbvo.fk_email }"> 			
				<button type="button" onclick="javascript:location.href='<%=request.getContextPath()%>/editGreeting.yo?seq=${gbvo.seq }'">수정</button>
				<button type="button" onclick="javascript:location.href='<%=request.getContextPath()%>/destroyGreeting.yo?seq=${gbvo.seq }'">삭제</button>
			</c:if>
			<button type="button"  onclick="javascript:location.href='<%= request.getContextPath()%>/greetingList.yo'">목록</button>
		</div>	
		<div>
			<div><span style="border-right: 1px solid black;">${gbvo.title }</span><span style="border-right: 1px solid black;">가입인사방</span>${gbvo.regDate}</div>
		</div>
		
		<table>
			<tr>
				<td>${gbvo.name }</td>		
			</tr>
			<tr>
				<td>${gbvo.content}</td>
			</tr>
			
			<div>
				<c:if test="${empty gbvo.commentCount}">
					<span>댓글  0</span>
				</c:if>
				<c:if test="">
					<span>댓글 ${gbvo.commentCount}</span>
				</c:if>		
				<span>조회수${gbvo.readCount}</span>
				<span>좋아요</span>
			</div>
			<tr>
				<td>
				</td>
			</tr>
		</table>
		
		<%-- 댓글보여주기 --%>
		<table id="commentDisplay">
			<tbody>			
				<c:if test="${!empty greetingCommentList }">
					<c:forEach items="${greetingCommentList }" var="gcList">
						<tr>
							<td>${gcList.name }</td>
							<td>${gcList.content }</td>
							<td>${gcList.regDate }</td>					
						</tr>
					</c:forEach>	
				</c:if>				
			</tbody>								
		</table>
		<%-- 댓글 등록 From --%>
		<div style="border: 2px solid coral;" id="addGreetingCommentDiv">
			<form name="addGreetingComment">
				<input type="hidden" name="name" value="${sessionScope.loginuser.name}"/>
				<input type="hidden" name="fk_email" value="${sessionScope.loginuser.email}"/>
				<input type="hidden" name="parentSeq" value="${gbvo.seq}"/>
				<input type="text" name="content" id="commentContent"/>
				<button type="button" id="btnComment" onclick="goAddGreetingComment()">등록</button>		
				
				<input type="hidden" name="groupno" value="${groupno }"/>
				<input type="hidden" name="depthno" value="${depthno }"/>
				<input type="hidden" name="fk_seq" value="${fk_seq }"/>
			</form>
		</div>
		
		<%-- 대댓글 form --%>
		<div style="border: 2px solid coral;" id="replyDiv">
			<form name="addReplyComment">
				<input type="hidden" name="name" value="${sessionScope.loginuser.name}"/>
				<input type="hidden" name="fk_email" value="${sessionScope.loginuser.email}"/>
				<input type="hidden" name="parentSeq" value="${gbvo.seq}"/>
				<span style="color: coral;"> ㄴ </span>
				<input type="text" name="content" id="replyContent"/>
				<button type="button" id="btnReplyComment" onclick="addReplyComment()">등록</button>		
				
				<input type="hidden" name="groupno" value="${groupno }"/>
				<input type="hidden" name="depthno" value="${depthno }"/>
				<input type="hidden" name="fk_seq" value="${fk_seq }"/>
			</form>
		</div>
	
		<%-- 이전글 다음글 --%>
			<div style="margin-bottom: 1%;"> ＜  이전글<span class="move" onClick="javascript:location.href='greetingView.yo?seq=${gbvo.previousseq}'">${gbvo.previoustitle}</span></div>
			<div style="margin-bottom: 1%;"> ＞ 다음글<span class="move" onClick="javascript:location.href='greetingView.yo?seq=${gbvo.nextseq}'">${gbvo.nexttitle}</span></div>	
			<div onclick="javascript:location.href='<%=request.getContextPath() %>/greetingList.yo'">목록</div>

		
<%-- 				<c:if test="${gbvo.nextseq!=null }">				 --%>
	</div>
	
	
	
	