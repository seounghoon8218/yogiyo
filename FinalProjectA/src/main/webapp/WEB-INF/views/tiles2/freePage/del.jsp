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
      
      $("#btnDelete").click(function(){
         
         // 글암호 유효성 검사를 하자 
         var pwval = $("#pw").val().trim();
         if(pwval == ""){
            alert("글암호을 입력하세요.");
            return;
         }
         
         // 폼을 submit
         var frm = document.delFrm;
         frm.method = "POST";
         frm.action = "<%=ctxPath%>/delEnd.yo";
         frm.submit();
      });
      
   });
</script>


<div style="padding-left: 10%; margin-bottom: -2%;">
   <h1>글삭제</h1>
   
   <form name="delFrm">
      <table id="table">
         <tr>
            <th>글암호</th>
            <td>
            	<input type="password" name="pw" id="pw" class="short"/>
            	<input type="hidden"   name="seq" value="${seq}" />
            	<!-- 삭제해야할 글번호와 함께 암호를 전송하도록 한다. -->
            </td>
         </tr>
      </table>
      
      <div>
         <button type="button" id="btnDelete">삭제</button>
         <button type="button" onclick="location.href='<%=ctxPath%>'">취소</button>
      </div>
      
   </form>
   
</div>