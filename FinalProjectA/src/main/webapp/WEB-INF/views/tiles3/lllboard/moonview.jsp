<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
   
<style type="text/css">
   table, th, td, input, textarea {border: solid gray 1px;}
   
   #table, #table2 {border-collapse: collapse;
                   width: 900px;
                  }
   #table th, #table td{padding: 5px;}
   #table th{width: 120px; background-color: #DDDDDD;}
   #table td{width: 750px;}
   .long {width: 470px;}
   .short {width: 120px;}
   
   .move {cursor: pointer;}
   .moveColor {color: #660029; font-weight: bold;}
   
   a {text-decoration: none !important;}
</style>

<script type="text/javascript">
    
   $(document).ready(function(){
   
      $(".move").hover(function(){
                     $(this).addClass("moveColor");   
                   },
                     function(){
                     $(this).removeClass("moveColor");
      });
      
   // === 댓글쓰기는 로그인을 했을 경우에만 text type 및 버튼 활성화 시키기 === //
      if( ${sessionScope.loginuser == null} ) {
         $("#commentContent").attr("disabled",true);
         $("#btnComment").attr("disabled",true);
      }
      else {
         $("#commentContent").attr("disabled",false);
         $("#btnComment").attr("disabled",false);
      }
            
   });// end of $(document).ready()----------------------
    
   
   // === 댓글쓰기 === //
   function goAddWrite() {
      var frm = document.addWriteFrm;
      var contentVal = frm.content.value.trim();
      if(contentVal=="") {
         alert("댓글 내용을 입력하세요!!");
         return;
      }
      
      var form_data = $("form[name=addWriteFrm]").serialize();
      
      $.ajax({
         url:"<%= request.getContextPath()%>/addComment.yo",
         data:form_data, 
         type:"GET",
         dataType:"JSON",
         success:function(json){
            var html = "";
            $.each(json, function(index, item){
               html += "<tr>";
               html += "<td style='text-align: center;'>"+item.name+"</td>";
               html += "<td>"+item.content+"</td>";
               html += "<td style='text-align: center;'>"+item.regDate+"</td>";
               html += "</tr>";
            });
            
            $("#commentDisplay").html(html);
            frm.content.value = "";
         },
         error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
         }
      });
      
   }
   
</script>

<div style="padding-left: 10%; border: solid 0px red;">
   <h1>문의 내용</h1>
   
   <table id="table" style="word-wrap: break-word; table-layout: fixed;">
      <tr>
         <th>글번호</th>
         <td>${moonvo.seq}</td>
      </tr>
      <tr>
         <th>성명</th>
         <td>${moonvo.name}</td>
      </tr>
      <tr>
              <th>제목</th>
              <td>${moonvo.subject}</td>
        </tr>
      <tr>
         <th style="height: 350px;">내용</th>
         <td>${moonvo.content}</td>
      </tr>
      <tr>
         <th>조회수</th>
         <td>${moonvo.readCount}</td>
      </tr>
      <tr>
         <th>날짜</th>
         <td>${moonvo.regDate}</td>
      </tr>
      
   </table>
   
   <br/>
   
   
   <br/>
   
   <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/moonlist.yo'">목록보기</button> 
   <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/moonedit.yo?seq=${moonvo.seq}'">수정</button>
   <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/del.yo?seq=${moonvo.seq}'">삭제</button>
   
   <!-- === #123. 답변글쓰기 버튼 추가하기(현재 보고 있는 글이 작성하려는 답변글의 원글(부모글)이 된다.) === --> 
   <c:if test='${sessionScope.loginuser.email=="admin@gmail.com" }'>
      <button type="button" onClick="javascript:location.href='<%= request.getContextPath() %>/moonadd.yo?fk_seq=${moonvo.seq}&groupno=${moonvo.groupno}&depthno=${moonvo.depthno}'">답변글쓰기</button> 
   </c:if>
   
   
   
   <!-- ===== #91. 댓글 내용 보여주기 ===== -->
   <table id="table2" style="margin-top: 2%; margin-bottom: 3%;">
      
      <tbody id="commentDisplay">
         <c:if test="${!empty commentList}">
            <c:forEach var="commentvo" items="${commentList}">
               <tr>
                  <td style="text-align: center;">${commentvo.name}</td>
                  <td>${commentvo.content}</td>
                  <td style="text-align: center;">${commentvo.regDate}</td>
               </tr>
            </c:forEach>
         </c:if>
      </tbody>
   </table>
</div>










