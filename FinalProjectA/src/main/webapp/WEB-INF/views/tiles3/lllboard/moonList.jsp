<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
   table, th, td {border: solid 1px gray;}

    #table {width: 970px; border-collapse: collapse;}
    #table th, #table td {padding: 5px;}
    #table th {background-color: #DDD;}
     
    .subjectStyle {font-weight: bold;
                   color: black;
                   cursor: pointer;} 
</style>

<script type="text/javascript">
 $(document).ready(function(){
   
   
    
   $(".subject").bind("mouseover", function(event){
      var $target = $(event.target);
      $target.addClass("subjectStyle");
   });
   
   $(".subject").bind("mouseout", function(event){
      var $target = $(event.target);
      $target.removeClass("subjectStyle");
   });
   
   
   $("#searchWord").keydown(function(event) {
       if(event.keyCode == 13) {
          // 엔터를 했을 경우
          goSearch();
       }
    });
   
   if(${paraMap != null}) {
      $("#searchType").val("${paraMap.searchType}");
      $("#searchWord").val("${paraMap.searchWord}");
   }
   
   <!-- === #102. 검색어 입력시 자동글 완성하기 2 === -->
   $("#displayList").hide();
   
   $("#searchWord").keyup(function(){
      
      var form_data = { searchType:$("#searchType").val()
                     ,searchWord:$("#searchWord").val() };
      
      $.ajax({
         url:"<%= request.getContextPath()%>/wordSearchShow.yo",
         type:"GET",
         data:form_data,
         dataType:"JSON",
         success:function(json){
         
         <!-- === #107. 검색어 입력시 자동글 완성하기 7 === -->   
            if(json.length > 0) { 
               // 검색된 데이터가 있는 경우임. 만약에 조회된 데이터가 없을 경우 if(json == null) 이 아니고 if(json.length == 0) 이라고 써야 한다. 
               
               var html = "";
            
               $.each(json, function(entryIndex, item){
                  var word = item.word;
                  var index = word.toLowerCase().indexOf( $("#searchWord").val().toLowerCase() ); 
                  //  alert("index : " + index);
                  var len = $("#searchWord").val().length;
                  var result = "";
                  
                  result = "<span class='first' style='color:blue;'>" +word.substr(0, index)+ "</span>" + "<span class='second' style='color:red; font-weight:bold;'>" +word.substr(index, len)+ "</span>" + "<span class='third' style='color:blue;'>" +word.substr(index+len, word.length - (index+len) )+ "</span>";  
                  
                  html += "<span style='cursor:pointer;'>"+ result +"</span><br/>"; 
               });
               
               $("#displayList").html(html);
               $("#displayList").show();
            }
            else {
               // 검색된 데이터가 존재하지 않을 경우
               $("#displayList").hide();
            } // end of if ~ else ----------------            
            
         },
         error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
         }
      });
   });
   
   <!-- === #108. 검색어 입력시 자동글 완성하기 8 === -->
   $("#displayList").click(function(event){
      var word = "";
      var $target = $(event.target);
      
      if($target.is(".first")) {
         word = $target.text() + $target.next().text() + $target.next().next().text();
      }
      else if($target.is(".second")) {
         word = $target.prev().text() + $target.text() + $target.next().text();
      }
      else if($target.is(".third")) {
         word = $target.prev().prev().text() + $target.prev().text() + $target.text();
      }
      
      $("#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
      
      $("#displayList").hide();
      
      goSearch();
   });
   
   
 });// end of $(document).ready()--------------------
 
 
 function goView(seq) {
   
    var frm = document.goViewFrm;
    frm.seq.value = seq;
    
    frm.method = "GET";
    frm.action = "moonview.yo";
     frm.submit();
 }
 
 function goSearch() {
   var frm = document.searchFrm;
   frm.method = "GET";
   frm.action = "<%= request.getContextPath()%>/moonlist.yo";
   frm.submit();
 }
 

 
</script>  

<div style="padding-left: 10%; margin-left: 250px;">
   <h2 style="margin-bottom: 20px;
    padding-bottom: 30px;
    border-bottom: 3px solid red;
    width: 66%;
    display: inline-block" >고객센터 - 문의게시판</h2>
   
   <table id="table">
      <tr style="color: black; font-weight: bold;">
         <th style="width: 70px;  text-align: center;">글번호</th>
         <th style="width: 360px; text-align: center;">제목</th>
         <th style="width: 70px;  text-align: center;">작성자</th>
         <th style="width: 180px; text-align: center;">날짜</th>
         <th style="width: 70px;  text-align: center;">조회수</th>
      </tr>   
      <c:forEach var="moonvo" items="${moonList}" varStatus="status">
         <tr>
            <c:if test="${moonvo.depthno == 0}">
               <td align="center">${moonvo.seq}</td>
            </c:if>
            <c:if test="${moonvo.depthno > 0}">
               <td align="center"></td>
            </c:if>
            <td align="left"> 
               <%-- === 댓글쓰기가 없는 게시판 === 
                    <span class="subject" onclick="goView('${moonvo.seq}');">${moonvo.subject}</span>
               --%> 
               
               <%-- === 댓글쓰기가 있는 게시판 === --%> 
         <%--   <c:if test="${moonvo.commentCount > 0}">   
                    <span class="subject" onclick="goView('${moonvo.seq}');">${moonvo.subject}&nbsp;<span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${moonvo.commentCount}</span>]</span></span>
               </c:if>
               <c:if test="${moonvo.commentCount == 0}">
                    <span class="subject" onclick="goView('${moonvo.seq}');">${moonvo.subject}</span>
               </c:if>
         --%>      
               <%-- === 댓글쓰기 및 답변형 게시판 === 
                                   답변글인 경우 글제목 앞에 공백(여백)과 함께 └Re 
                                   라는 글자를 붙이도록 하겠다. 
               --%> 
               <!-- 답변글이 아닌 원글인 경우 -->
               <c:if test="${moonvo.depthno == 0}">
                  <c:if test="${moonvo.commentCount > 0}">   
                       <class="subject" onclick="goView('${moonvo.seq}');">${moonvo.subject}&nbsp;<span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${moonvo.commentCount}</span>]</span></span>
                  </c:if>
                  <c:if test="${moonvo.commentCount == 0}">
                       <span class="subject" onclick="goView('${moonvo.seq}');">${moonvo.subject}</span>
                  </c:if>
               </c:if>
               
               <!-- 답변글인 경우 -->
               <c:if test="${moonvo.depthno > 0}">
                  <c:if test="${moonvo.commentCount > 0}">   
                       <span class="subject" onclick="goView('${moonvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${moonvo.depthno * 20}px;">└문의&nbsp;${moonvo.subject}&nbsp;</span><span style="vertical-align: super;">[<span style="color: red; font-size: 9pt; font-style: italic; font-weight: bold;">${moonvo.commentCount}</span>]</span></span>
                  </c:if>
                  <c:if test="${moonvo.commentCount == 0}">
                       <span class="subject" onclick="goView('${moonvo.seq}');"><span style="color: red; font-style: italic; padding-left: ${moonvo.depthno * 20}px;">└문의&nbsp;${moonvo.subject}</span></span>
                  </c:if>
               </c:if>
            </td>
            <td align="center">${moonvo.name}</td>
            <td align="center">${moonvo.regDate}</td>
            <td align="center">${moonvo.readCount}</td>
            
          </tr>
      </c:forEach>
   </table>
   <br/>
   
   <form name="goViewFrm">
      <input type="hidden" name="seq"/>
      <input type="hidden" name="gobackURL" value="${gobackURL}"/> 
   </form>
   
   <!-- === #120. 페이지바 보여주기 === --> 
   <div align="center" style="width: 70%; border: 0px solid gray; margin-left: 50px; margin-bottom: 20px;" >
      ${pagebar}
   </div>
   
   <!-- === #96. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. === --> 
   <form name="searchFrm" style="">
      <select name="searchType" id="searchType" style="height: 26px;">
         <option value="subject">글제목</option>
         <option value="name">글쓴이</option>
      </select>
      <input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" /> 
      <button type="button" onclick="goSearch()">검색</button>
      <button type="button" onclick="javascript:location.href='<%= request.getContextPath()%>/moonadd.yo'" style="margin-left: 475px;">글쓰기</button>
   </form>
   
   <!-- === #101. 검색어 입력시 자동글 완성하기 1 === -->
   <div id="displayList" style="width: 314px; height: 100px; overflow: auto; margin-left: 70px; margin-top: -1px; border-top: 0px; border: solid gray 1px;">
   </div>
   
</div>
  




