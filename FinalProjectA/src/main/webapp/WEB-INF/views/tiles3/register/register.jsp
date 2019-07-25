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
<!-- <script src='https://code.jquery.com/jquery-3.3.1.min.js'></script> -->
<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js'></script>
<script type="text/javascript">

   $(document).ready(function(){
      var emailchecknum = 0; // 이메일 중복체크 여부 확인?

            
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
         
      
           // 이메일 정규식 표현
         $("#email").blur(function(){
            
            var email = $(this).val();
            
            var regExp_EMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
            
            var bool = regExp_EMAIL.test(email);
            
            if(!bool) {
               $(this).parent().find(".error").show();
               $("#btnRegister").attr("disabled",true)
               $(this).attr("disabled",false)
               $("#showerror").html("이메일형식이 잘못되었어요.");
               $(this).focus();
            }
            else {
               $(this).parent().find(".error").hide();
               $("#btnRegister").attr("disabled",false)
               $("#showerror").html("");
               console.log($("#email").val());
/*                if("0".equals($("#emailchk").val())){ */
               if(0==$("#emailchk").val()){

                  $("#showerror").html("이메일 중복체크 해주세요.");
                  $(this).focus();
               }
               else{
                  $("#showerror").html("");
               }
               
            }
            
            
         });// end of $("#email").blur()--------------
         
         // 비밀번호 정규식 표현
         $("#pwd").blur(function(){
            var passwd = $(this).val();
            
         
            var regExp_PW = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);  
         
            
            var bool = regExp_PW.test(passwd);
            
            if(!bool) {
               $("#error_passwd").show();
               $("#btnRegister").attr("disabled",true) 
               $(this).attr("disabled",false)
               $("#showerror").html("암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.");
               $(this).focus();
            }
            else {
               $("#error_passwd").hide();
               $("#btnRegister").attr("disabled",false)
               $("#showerror").html("");
               
            } 
         }); // end of $("#pwd").blur()---------------
         
         // 비밀번호 재확인 검사
         $("#pwd2").blur(function(){
            var passwd = $("#pwd").val();
            var passwdCheck = $(this).val();
            
            if(passwd != passwdCheck) {
               $(this).parent().find(".error").show();
               $("#btnRegister").attr("disabled",true)
               $(this).attr("disabled",false)
               $("#pwd").attr("disabled",false)
               $("#showerror").html("비밀번호가 일치하지 않습니다.");
               $("#pwd").focus();
            }
            else {
               $(this).parent().find(".error").hide();
               $("#showerror").html("");
               $("#btnRegister").attr("disabled",false).removeClass("bgcol");
            }
            
         });// end of $("#pwdcheck").blur()--------------    
      
       //아이디 체크여부 확인 (아이디 중복일 경우 = 0 , 중복이 아닐경우 = 1 )
       $("#emailcheck").click(function(){
            fn_userIDCheck();
         });

   })// end of $(document).ready(function(){---------
    function fn_userIDCheck() {
      $("#showerror").html("");
      var email = $("#email").val();
      var userData = {"email": email}
      
      console.log(email);
      
      if(email.length < 1) {
         alert("이메일을 입력해주시기 바랍니다.");
      }
      else {
         $.ajax({
            type : "GET",
            url : "/yogiyo/emailcheck.yo",
            data : userData,
            dataType:"JSON",
            success : function(json) {
               
            
                if (json.n == 0) {
                  alert("사용이 가능한 이메일입니다.");
                  $("#showerror").html("");
                  $("#emailchk").val('1');
                  $("#pwd").focus();
               }
               else if (json.n == 1) {
                  alert("이미 존재하는 이메일입니다. 다른 이메일 사용해주세요");
                  $("#emailchk").val('0');
               }
               else {
                  alert("에러가 발생");
                  $("#emailchk").val('0');
               }
             
            },
            error : function(error) {
               alert("서버가 응답하지 않습니다. 다시 시도");
            }
         });
         
      }
   } 
      
   function func_Register() {
      var email = $("#email").val(); 
      var pwd = $("#pwd").val();
      var name = $("#name").val();
      
      console.log(email);
      console.log(pwd);
      console.log(name);
      
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
<div style="width: 30%; padding-left: 300px;">
    <input type="hidden" id="emailchk" value="0"/>
    </div>
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
   <div align="center" style="font-weight: bold; margin-bottom: 35px;">
      <h2><span>회원가입해주셔서 감사합니다!</span></h2>
   </div>
   <form name="registerFrm">
      <table style="width: 70%">
         <tr><th><span>회원정보 입력</span>&nbsp;&nbsp;<span id="showerror" style="color: red;"></span></th></tr>
         <tr>
            <td>
               <input type="email" placeholder="(필수)이메일 주소 입력" name="email" id="email"/>
               <button type="button" class="emailcheck" id="emailcheck">이메일 중복확인</button>
            </td>
         </tr>
         <tr>
            <td><input type="password" placeholder="(필수)비밀번호 입력" name="pwd" id="pwd" class="requiredInfo" required/>
            </td>
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
               <button id="btnRegister" type="button" style="width: 100%; color: white; background-color: red;">
                  <span style="font-weight: bold; font-size: 15pt;">회원가입 완료</span>
               </button>
            </td> 
         </tr>
      </table>
   </form>
</div>