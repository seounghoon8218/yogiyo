<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">
	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table {border-collapse: collapse;
	 		width: 900px;
	 		}
	#table th, #table td{padding: 5px;}
	#table th{width: 120px; background-color: #DDDDDD;}
	#table td{width: 860px;}
	.long {width: 470px;}
	.short {width: 120px;} 		
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		
		/* === 스마트 에디터 구현 시작 === */
		
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
		
		/* === 스마트 에디터 구현 끝 === */
		
		// 쓰기버튼
		$("#btnWrite").click(function(){
			
			/* === 스마트 에디터 구현 시작 === */
			
			//id가 content인 textarea에 에디터에서 대입
		    obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
		        
			
			/* === 스마트 에디터 구현 끝 === */
			
			
			// 글제목 유효성 검사
			var subjectval = $("#subject").val().trim();
			if(subjectval == "") {
				alert("글제목을 입력하세요!!");
				return;
			}
			
			
			
			//스마트에디터 사용시 무의미하게 생기는 p태그 제거
	           var contentval = $("#content").val();
		        
		        // === 확인용 ===
		        // alert(contentval); // content에 내용을 아무것도 입력치 않고 쓰기할 경우 알아보는것.
		        // "<p>&nbsp;</p>" 이라고 나온다.
		        
		        // 스마트에디터 사용시 무의미하게 생기는 p태그 제거하기전에 먼저 유효성 검사를 하도록 한다.
		        // 글내용 유효성 검사 
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
		     // alert(contentval);
			
			
			
			// 글내용 유효성 검사
			var contentval = $("#content").val().trim();
			if(contentval == "") {
				alert("글내용을 입력하세요!!");
				return;
			}
			
			// 글암호 유효성 검사
			var pwval = $("#pw").val().trim();
			if(pwval == "") {
				alert("글암호를 입력하세요!!");
				return;
			}
			
			
			// 폼을 submit
			var frm = document.addFrm;
			frm.method = "POST"; // 파일을 첨부 할 경우라면 반드시 POST 이어야만 가능하다. GET 이라면 파일첨부가 안되어진다.
			frm.action = "<%= ctxPath%>/addKKKEnd.yo";
			frm.submit();
		});
		
	});
</script>

<div style="padding-left: 10%; margin-bottom: -2%; border: solid 0px red;">
	<h1>글쓰기</h1>
	
	<%-- <form name="addFrm"> --%>
	
	<!-- === #131.파일첨부하기 === 
		먼저 위의 문장을 주석처리 한 후 아래와 같이 해야 한다.	
		enctype="multipart/form-data" 를 해주어야만 파일첨부가 되어진다 -->		
	<form name="addFrm" enctype="multipart/form-data">
		<table id="table">
			<tr>
				<th>성명</th>
				<td>	
				    <input type="hidden" name="fk_userid" value="${sessionScope.loginuser.email}" readonly />
					<input type="text" name="name" value="${sessionScope.loginuser.name}" class="short" readonly />          
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" id="subject" class="long" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
				<textarea name="content" id="content" rows="10" cols="100" style="width: 95%; height: 412px;"></textarea>	
				</td>
			</tr>
			<tr>
				<th>파일첨부</th>
				<td><input type="file" name="attach" /></td>
			</tr>
			<tr>
				<th>글암호</th>
				<td><input type="password" name="pw" id="pw" class="short" /></td>
			</tr>
		</table>
		
		<!-- === #125. 답변글쓰기인 경우 ===
  			  	 	      부모글의 seq 값인 fk_seq 값과
			                      부모글의 groupno 값과
			                      부모글의 depthno 값을 hidden 타입으로 보내준다.  -->
		<input type="hidden" name="fk_seq" value="${fk_seq}" />
		<input type="hidden" name="groupno" value="${groupno}" />
		<input type="hidden" name="depthno" value="${depthno}" />
		
		
		<div style="margin: 20px;">
			<button type="button" id="btnWrite">쓰기</button>
			<button type="button" onclick="javascript:history.back();">취소</button>
		</div>
		
	</form>
</div>






    