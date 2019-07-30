<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">

</style>

<script type="text/javascript">
	$(document).ready(function(){
		
	});
</script>

<div>
	<form name="addBoardFrm" enctype="multipart/form-data">
		<div>
			<table>
				<tr>
					<td>성명</td>
					<td>
						<input type="text" name="name" value="${sessionScope.loginuser.name}" readonly/>
						<input type="hidden" name="fk_email" value="${sessionScope.loginuser.email}" readonly/>
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title" id="title"/>			
					</td>
				</tr>
				<tr>
					<td>글내용</td>
					<td>
						<textarea name="content" id="content" cols="100"  style="width: 95%; height: 412px;"></textarea>
						
					</td>
				</tr>
				<tr>
					<td>첨부파일</td>
					<td>
						<input type="file" name="attach" id="attach"/>						
					</td>
				</tr>
				<tr>
					<td>암호</td>
					<td>
						<input type="password" name="pw" id="pw"/>
					</td>
				</tr>
				
			</table>
			
			<input type="hidden" name="fk_seq" value="${fk_seq}"   />
			<input type="hidden" name="groupno" value="${groupno}" />
			<input type="hidden" name="depthno" value="${depthno}" />
			
			<div style="margin: 20px 20px;">
				<button type="button" id="btnWrite">쓰기</button>
				<button type="button" onclick="javascript:history.back();">취소</button>
			</div>
		</div>
	</form>
</div>