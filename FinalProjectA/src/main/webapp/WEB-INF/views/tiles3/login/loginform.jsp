<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>

<style type="text/css">
   
   
   
   
</style>

<script type="text/javascript">
 
     $(document).ready(function(){
        
        $("#btnLOGIN").click(function() {
           func_Login();
        }); // end of $("#btnLOGIN").click();-----------------------
        
        $("#pwd").keydown(function(event){
           
           if(event.keyCode == 13) { // 엔터를 했을 경우
              func_Login();
           }
        }); // end of $("#pwd").keydown();-----------------------   
        
        $("#certification").click(function () {
           
           var email2 = $("#email2").val().trim();
            var tel2 = $("#tel2").val().trim();
            
            if(email2=="") {
             alert("이메일을 입력하세요!!");
             return;
          }
            else if(tel2==""){
               alert("핸드폰번호를 입력하세요!!");
             return;
            }
            var form_data = {email2:$("#email2").val().trim(), tel2:$("#tel2").val().trim()};
            console.log(email2,tel2);
            $.ajax({
             url:"<%= request.getContextPath()%>/sendSMS.yo",
             data:form_data,
             type:"POST",
             dataType:"JSON",
             success:function(json){
                
                if (json.pwdOK == 1) {
                         alert("전송되었습니다.");
                      }
                      else if (json.pwdOK == 0) {
                         alert("이메일과 핸드폰번호가 맞지 않습니다.");
                      }
                      else {
                         alert("에러가 발생");
                      }
                
             },
             error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
          });
            
      });
        
        $("#btnOK").click(function () {
           var frm = document.loginFrm;
            
            frm.action = "<%=ctxPath%>/newPwd.yo";
            frm.method = "POST";
            frm.submit();
      });
        
    }); // end of $(document).ready()---------------------------    

    
    function func_Login() {
           
       var email = $("#email").val(); 
       var pwd = $("#pwd").val(); 
      
       if(email.trim()=="") {
           alert("이메일을 입력하세요!!");
          $("#email").val(""); 
          $("#email").focus();
          return;
       }
      
       if(pwd.trim()=="") {
          alert("비밀번호를 입력하세요!!");
          $("#pwd").val(""); 
          $("#pwd").focus();
          return;
       }

       var frm = document.loginFrm;
       
       frm.action = "<%=ctxPath%>/loginEnd.yo";
       frm.method = "POST";
       frm.submit();
       
    } // end of function func_Login(event)-----------------------------
    
    
    function pwdSearch() {
           
       var frm = document.loginFrm;
       
       frm.action = "<%=ctxPath%>/pwdSearch.yo";
       frm.method = "GET";
       frm.submit();
       
    } // end of function func_Login(event)-----------------------------
     
</script>

<div class="container custom-border">

   <div class="row col-md-8 col-md-offset-2 custom-border">
      <h2 class="text-primary" style="color: red;">로그인</h2>&nbsp;
      
      <form name="loginFrm">
      <div class="row custom-loginFrm custom-border">    
          <div class="col-sm-8 col-lg-8 custom-border">
            <div class="form-group custom-input">  <%-- 폼에서는 class form-group 을 사용해야 하며, 그 뜻은 폼에서 사용되어지는 1개 행 즉, 클래스 row 와 같은 용도로 사용되어지는 것이다. --%>
               <div class="col-sm-3 col-lg-3">
                  <label for="email">이메일</label>
               </div>
               <div class="col-sm-9 col-lg-9">
                  <input type="text" class="form-control" name="email" id="email" value="" placeholder="이메일 주소 입력(필수)" required="required"/> <%-- 부트스트랩에서 input 태그에는 항상 class form-control 이 사용되어져야 한다. --%>
               </div>
               <input type="hidden" id="emailhidden" name="emailhidden" />               
            </div>   
               
            <div class="form-group custom-input">
               <div class="col-sm-3 col-lg-3">
                  <label for="pwd">비밀번호</label>
               </div>
               <div class="col-sm-9 col-lg-9">
                  <input type="password" class="form-control" name="pwd" id="pwd" value="" placeholder="비밀번호 입력(필수)" required="required"/> &nbsp;&nbsp;
               </div>
            </div>
         </div>
         
             
      </div>
      <div class="col-sm-4 col-lg-4 custom-login-submit custom-border">
            <button class="btn btn-success" type="button" id="btnLOGIN" style=" background-color: red; border: 1px solid #ff8040; margin-left: -5px; width: 710px;" >로그인</button>&nbsp;
            <div class="">
            <button class="pwdSearch" data-toggle="modal" data-target="#myModal" type="button" id="pwdSearch" style=" background-color: red; border: 1px solid #ff8040; margin-left: -5px; width: 710px; height: 30px; color: white;" >비밀번호 찾기</button>
            </div>
         </div>
         

   <!-- 비밀번호 찾기 모달 -->
   
    <div class="container">
    
   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
     <div class="modal-dialog">
       <div class="modal-content">
         <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
      <h4 class="modal-title" id="myModalLabel">비밀번호 찾기</h4>
         </div>
         <div class="modal-body">
         <table>
            <tr>
               <td><input type="email" placeholder="(필수)본인 이메일 주소 입력" name="email2" id="email2"/></td>
              </tr>    
               <tr>
                  <td>
                  <input type="text" placeholder="(필수)본인 휴대폰번호 입력" name="tel2" id="tel2" required>
                  <button type="button" class="certification" id="certification">인증번호 요청</button>
                  <div id="result"></div>
                  </td>
              </tr>
              <tr>
               <td><input type="text" placeholder="(필수)인증번호" name="text" id="text"/></td>
              </tr>
            
         </table>
         </div>
         <div class="modal-footer">
      <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
      <button type="button" class="btn btn-primary" id="btnOK">확인</button>
         </div>
       </div>
     </div>
   </div>
   
   </div>
   </form>   
         
   </div>
   
</div>

  