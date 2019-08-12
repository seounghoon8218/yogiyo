<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            
       $("#btnDel").click(function() {
    	   func_Del();
       }); // end of $("#btnEdit").click();-----------------------
       
         
      
         // 비밀번호 정규식 표현
         $("#pwd").blur(function(){
            var passwd = $(this).val();
            
         
            var regExp_PW = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);  
         
            
            var bool = regExp_PW.test(passwd);
            
            if(!bool) {
               $("#error_passwd").show();
               $("#btnEdit").attr("disabled",true) 
               $(this).attr("disabled",false)
               $("#showerror").html("암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.");
               $(this).focus();
            }
            else {
               $("#error_passwd").hide();
               $("#btnEdit").attr("disabled",false)
               $("#showerror").html("");
               
            } 
         }); // end of $("#pwd").blur()---------------
         
         // 비밀번호 재확인 검사
         $("#pwd2").blur(function(){
            var passwd = $("#pwd").val();
            var passwdCheck = $(this).val();
            
            if(passwd != passwdCheck) {
               $(this).parent().find(".error").show();
               $("#btnEdit").attr("disabled",true)
               $(this).attr("disabled",false)
               $("#pwd").attr("disabled",false)
               $("#showerror").html("비밀번호가 일치하지 않습니다.");
               $("#pwd").focus();
            }
            else {
               $(this).parent().find(".error").hide();
               $("#showerror").html("");
               $("#btnEdit").attr("disabled",false).removeClass("bgcol");
            }
            
         });// end of $("#pwdcheck").blur()--------------    
      
       

   })// end of $(document).ready(function(){---------
      
    function func_Del() {
      
      var frm = document.delFrm;
       
       frm.action = "<%=ctxPath%>/memberDelEnd.yo";
       frm.method = "POST";
       frm.submit();
   } 
   
   
       
</script>

<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
   <div align="center" style="font-weight: bold; margin-bottom: 35px;">
      <h2><span>회원탈퇴</span></h2>
   </div>
   <form name="delFrm">
      <table style="width: 70%">
         <tr><th><span>회원탈퇴</span>&nbsp;&nbsp;<span id="showerror" style="color: red;"></span></th></tr>
	         <tr>
	            <td>
	               <input type="text" placeholder="(필수)이메일 주소 입력" value="${sessionScope.loginuser.email}" name="email" id="email" readonly="readonly"/>
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
            <td>
               <button id="btnDel" type="submit" style="width: 100%; color: white; background-color: red;">
                  <span style="font-weight: bold; font-size: 15pt;">회원탈퇴 완료</span>
               </button>
            </td> 
         </tr>
      </table>
   </form>
</div>