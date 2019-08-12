<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">

</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		//전역변수
		var obj = [];
		    
		//스마트에디터 프레임생성
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: obj,
			elPlaceHolder: "content",
			sSkinURI: "<%= request.getContextPath() %>/resources/smarteditor/SmartEditor2Skin.html",
			htParams : {
		    // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		    bUseToolbar : true,            
		    // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		    bUseVerticalResizer : true,    
		    // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		    bUseModeChanger : true,
			}
		});
		
		$("#btnWrite").click(function(){
			
			//id가 content인 textarea에 에디터에서 대입
	        obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	        
	      //스마트에디터 사용시 무의미하게 생기는 p태그 제거
	         var contentval = $("#content").val();
		     
		        if(contentval == "" || contentval == "<p>&nbsp;</p>") {
		        	alert("글내용을 입력하세요!!");
		        	return;
		        }
		        
		        // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기
		        contentval = $("#content").val().replace(/<p><br><\/p>/gi, "<br>"); //<p><br></p> -> <br>로 변환
	 
		        contentval = contentval.replace(/<\/p><p>/gi, "<br>"); //</p><p> -> <br>로 변환  
		        contentval = contentval.replace(/(<\/p><br>|<p><br>)/gi, "<br><br>"); //</p><br>, <p><br> -> <br><br>로 변환
		        contentval = contentval.replace(/(<p>|<\/p>)/gi, ""); //<p> 또는 </p> 모두 제거시
		    
		        $("#content").val(contentval);
	         <%-- === 스마트 에디터 구현 끝 === --%>
		
			var pwReg = /^[0-9]{4,8}$/
			var pwBool = pwReg.test($("#pw").val());
			
			if ($("#title").val().trim()=="") {
				alert("제목을 입력하세요.");
				$("#title").focus();
				$("#title").val("");
				return;
			} 
			
			if ($("#content").val().trim()=="") {
				alert("인사말을 입력하세요.");
				$("#content").focus();
				$("#content").val("");
				return;
			}
			
			if ($("#pw").val().trim()=="") {
				alert("비밀번호를 입력하세요.");
				$("#pw").focus();
				$("#pw").val("");
				return;
			} else if($("#pw").val().trim() != "" && pwBool == false){
				alert("비밀번호는 4~8 사이의 숫자만 입력가능합니다.");
				$("#pw").focus();
				$("#pw").val("");
				return;
			}
						
			var frm = document.addGreetingFrm;
			frm.method = "POST";
			frm.action = "<%= request.getContextPath() %>/addGreetingEnd.yo";
			frm.submit();
		}) // btnWrite
		
	});
</script>

<div>
	<form name="addGreetingFrm" enctype="multipart/form-data">
		<div>
			<table>
				<tr>
					<td>성명</td>
					<td>
						<input type="text" name="name" id="name" value="${sessionScope.loginuser.name}" readonly/>
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
					<td style="width: 700px;">
						<textarea name="content" id="content" rows="10" cols="100" style="width:100%; height:412px;"></textarea>	
					</td>
				</tr>
				<tr>
					<td>암호</td>
					<td>
						<input type="password" name="pw" id="pw"/>
					</td>
				</tr>
				
			</table>
			
			<div style="margin: 20px 20px;">
				<button type="button" id="btnWrite">쓰기</button>
				<button type="button" onclick="javascript:history.back();">취소</button>
			</div>
		</div>
	</form>
</div>