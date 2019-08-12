<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function(){
		
		$("#btnDestroy").click(function(){
			var frm = document.destroyGreetingFrm;
			
			frm.method="GET";
			frm.action="destroyGreetingEnd.yo";
			frm.submit();
		})
			
	}) // end of document.ready
</script>
	<div>
		<h1>글삭제</h1>
		<form name="destroyGreetingFrm">
			<table>
				<tr>
					<th>글암호</th>
					<td>
						<input type="password" name="pw" id="pw"/>
						<%-- 삭제해야할 글번호 보내주기 --%>
						<input type="hidden" name="seq" id="seq" value="${seq}"/>
					</td>
				</tr>
			</table>		
			<div>
				<button type="button" id="btnDestroy">삭제</button>
				<button type="button" onclick="javascript:history.back()">취소</button>
			</div>
		</form>
	</div>