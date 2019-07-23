<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>

<style type="text/css">
	input[type=text], input[type=password] , input[type=email]{
		width: 100%;
		height: 50px;
		font-size: 13pt;
	}
	input[type=checkbox]{
		height: 15px;
	}
	a{
		text-decoration: none;
		
	}
</style>
<script src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
<script type="text/javascript">

	$(document).ready(function(){
		 
		 $("#btnRegister").click(function() {
			 func_Register();
		 }); // end of $("#btnRegister").click();-----------------------
		
		 $("#check").click(function(){ 
			 //만약 전체 선택 체크박스가 체크된상태일경우 
			 if($("#check").prop("checked")) { //해당화면에 전체 checkbox들을 체크해준다
				 $("input[type=checkbox]").prop("checked",true);
			 } 
			 else { //해당화면에 모든 checkbox들의 체크를해제시킨다. 
					$("input[type=checkbox]").prop("checked",false); 
				 } 
		 })// end of $("#check").click(function(){--------------
		
			 
			 $(".checksub").click(function(){
				
				 if($("#check2").prop("checked") && $("#check3").prop("checked") && $("#check4").prop("checked")){
					 $("#check").prop("checked",true);
				 }else{
					 $("#check").prop("checked",false);
				 }
			 
			 });
			 
		
		
		$('#pwd2').blur(function(){
			   if($('#pwd').val() != $('#pwd2').val()){
			    	if($('#pwd2').val()!=''){
				    alert("비밀번호가 일치하지 않습니다.");
			    	    $('#pwd2').val('');
			          $('#pwd2').focus();
			       }
			    }
		})// end of $('#pwd2').blur(function(){----------
		
		$(".emailcheck").click(function () {
			
			var query = {email : $("email").val()};
			
			$.ajax({
				url: "/yogiyo/emailcheck.yo",
				type: "POST",
				data: query,
				success : function(data) {
					
					if(data == 1) {
						$(".result .msg").text("사용 불가");
						$(".result .msg").attr("style", "color:#f00");
						
						$("#btnRegister").attr("disabled", "disabled");
					}
					else {
						$(".result .msg").text("사용 가능");
						$(".result .msg").attr("style", "color:#00f");
						
						$("#btnRegister").removeAttr("disabled");
					}
				}
			});//end of ajax---------
		});// end of $(".emailcheck").click(function () {---------

	})// end of $(document).ready(function(){---------
	
	
	function func_Register() {
		var email = $("#email").val(); 
		var pwd = $("#pwd").val();
		var name = $("#name").val();
		
		
   	    if( !$("input:checkbox[id=check2]").is(":checked") ) {
   		    alert("이용약관에 동의하셔야 합니다.");
   		    return;
   	    } 
   	    if( !$("input:checkbox[id=check3]").is(":checked") ) {
   		    alert("개인정보 수집 및 이용에 동의하셔야 합니다.");
   		    return;
   	    } 
   	    if( !$("input:checkbox[id=check4]").is(":checked") ) {
   		    alert("만 14세 이상 이용자에 동의하셔야 합니다.");
   		    return;
   	    } 
		
		var frm = document.registerFrm;
		 
		 frm.action = "<%=ctxPath%>/registerEnd.yo";
		 frm.method = "POST";
		 frm.submit();
	}
</script>
    
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
	<div align="center" style="font-weight: bold; margin-bottom: 35px;">
		<h2><span>회원가입해주셔서 감사합니다!</span></h2>
	</div>
	<form name="registerFrm">
		<table style="width: 70%">
			<tr><th><span>회원정보 입력</span></th></tr>
			<tr>
				<td>
					<input type="email" placeholder="(필수)이메일 주소 입력" name="email" id="email"/>
					<button type="button" class="emailcheck" id="emailcheck">이메일 중복확인</button>
				</td>
			</tr>
			<tr>
				<td class="result">
					<span class="msg">왜안바뀌어</span>
				<td>
			</tr>
			<tr>
				<td><input type="password" placeholder="(필수)비밀번호 입력" name="pwd" id="pwd" required/></td>
			</tr>
			<tr>
				<td><input type="password" placeholder="(필수)비밀번호 재확인" name="pwd2" id="pwd2" required/></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)이름 입력" name="name" id="name" required></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)휴대폰번호 입력" name="tel" id="tel" required></td>
			</tr>
			<tr><td>&nbsp;<td></tr>
			<tr><th><span>약관동의 </span></th></tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check" />
					<label for="check">전체동의</label>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check2" class="checksub" style="margin-left: 10px;" />
					<label for="check2">이용약관동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check3" class="checksub" style="margin-left: 10px;"/>
					<label for="check3">개인정보 수집 및 이용동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check4" class="checksub" style="margin-left: 10px;"/>
					<label for="check4">만 14세 이상 이용자(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr><td>&nbsp;<td></tr>
			<tr>
				<td>
					<button id="btnRegister" type="submit" disabled="disabled" style="width: 100%; color: white; background-color: red;">
						<span style="font-weight: bold; font-size: 15pt;">회원가입 완료</span>
					</button>
				</td> 
			</tr>
		</table>
	</form>
</div>