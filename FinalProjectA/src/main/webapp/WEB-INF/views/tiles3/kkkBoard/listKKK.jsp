<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">	
    .subjectStyle {font-weight: bold;
                   color: navy;
                   cursor: pointer;} 
            
    .gongji > ul {
	    border: 1px solid #ccc;
	    background-color: #fff;
	    padding: 0;
	    margin: 0;
	}               
    .gongji > ul > li {
	    border-bottom: 1px solid #ccc;
	    list-style: none;
	    padding: 18px 16px 16px 16px;
	}
	.gongji {
	    margin: 0 10px;
	}
	ul {
	    display: block;
	    list-style-type: disc;
	    margin-block-start: 1em;
	    margin-block-end: 1em;
	    margin-inline-start: 0px;
	    margin-inline-end: 0px;
	    padding-inline-start: 40px;
	}	
	
	#barsty > ul > span {
		background-color: white;
	}
	
	#KKKAdd{
	    width: 10%;
	    float: right;
	    height: 30px;
	    font-size: 12pt;
	    position: relative;
	    top: -90px;
	    left: -12px;
	}
	
</style>

<script type="text/javascript">
 $(document).ready(function(){
	
	 $("#KKKAdd").click(function(){
		
		 location.href="<%=request.getContextPath()%>/addKKKBoard.yo";
		 
	 });
	 
	$(".gongji").bind("mouseover", function(event){
		var $target = $(event.target);
		$target.addClass("subjectStyle");
	});
	
	$(".gongji").bind("mouseout", function(event){
		var $target = $(event.target);
		$target.removeClass("subjectStyle");
	});
	
	
	$("#searchWord").keydown(function(event){
		 if(event.keyCode==13){
			 goSearch();
		 }
	})
	
	if(${paraMap != null}) {
		$("#searchType").val("${paraMap.searchType}");
		$("#searchWord").val("${paraMap.searchWord}");
	}
	
	
	<!-- === 검색어 입력시 자동 글 완성하기 === -->
	$("#displayList").hide();
	
	$("#searchWord").keyup(function(){
        
        var form_data = {searchType:$("#searchType").val(),
                     searchWord:$("#searchWord").val() };
        
        $.ajax({
           url:"<%=request.getContextPath()%>/KKKwordSearchShow.yo",
           type:"GET",
           data:form_data,
           dataType:"JSON",
           success:function(json){
        	   
        	   
        	   <!-- === 검색어 입력시 자동 글 완성하기 7 === -->
              if(json.length > 0) { // 검색된 데이터가 있는 경우임. 만약에 조회된 데이터가 없을 경우 if(data == null) 이 아니고 if(data.length == 0) 이라고 써야 한다. 
                
                var html = "";
             
                $.each(json, function(entryIndex, item){
                   var word = item.word;
                   var index = word.toLowerCase().indexOf( $("#searchWord").val().toLowerCase() );
                   				// 대소문자 구분을 없앤다.
                   				
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
        
        }); // end of ajax--
        
     }); // end of keyup--
	
	<!-- === #108. 검색어 입력시 자동 글 완성하기 8 === -->
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
			word = $target.prev().prev().text() + $target.prev().text() + $target.text(); // 앞앞거랑 앞거랑 자신것을 가져온다.
		}
		
		$("#searchWord").val(word); // 텍스트박스에 검색된 결과의 문자열을 입력해준다.
		
		$("#displayList").hide();
		
		goSearch();
   	 
     });
	
 }); // ready ----------------------
 
 
 function goView(seq) {
	
	 var frm = document.goViewFrm;
	 frm.seq.value = seq;
	 
	 frm.method = "GET";
	 frm.action = "viewKKK.yo";
     frm.submit();
 }
 
 function goSearch(){
	 
	 var frm = document.searchFrm
	 frm.method = "GET";
	 frm.action = "<%= request.getContextPath()%>/kkkBoardList.yo";
	 frm.submit();
	
	 
 }


 
 
</script>  

<div style="max-width: 720px; margin: 20px auto 40px auto; ">
	<h3 style="margin-bottom: 30px;">공지사항</h3>
	
	<c:forEach var="gong" items="${boardList}">
		<div class="gongji" onclick="goView('${gong.seq}');">
		    <ul>
		      <li>
		        <div class="">
		        	<span style="font-weight: bold;">${gong.subject}</span><br/>
		        	${gong.regDate} ~ ${endDate}
		        </div>
		      </li>
		    </ul>
		</div>
	</c:forEach>
	<br/>
	
	<form name="goViewFrm">
		<input type="hidden" name="seq"/>
		<input type="hidden" name="goBackURL" value="${goBackURL}"/>
	</form>
	
	<!-- === #120.페이지바 보여주기 === -->
	
	<div id="barsty" align="center">
		${pagebar}
	</div>
	
	
	
	<!-- #96. 글검색 폼 추가하기 : 글제목, 글쓴이로 검색을 하도록 한다. -->	
	<form name="searchFrm" style="text-align: center;">
		<select name="searchType" id="searchType" style="height: 26px;">
			<option value="subject">글제목</option>
			<option value="name">글내용</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" size="40" autocomplete="off" />
		<button type="button" onclick="goSearch()">검색</button>		
	</form>
	<c:if test="${sessionScope.loginuser.email == 'admin@gmail.com' }">
		<button type="button" id="KKKAdd" >글작성</button>
	</c:if>
	
	<!-- === #101. 검색어 입력시 자동 글 완성하기 1 === -->
	<div id="displayList" style="width: 314px; height: 100px; overflow: auto; margin-left: 214px; margin-top:-1px; border-top:0px; border: solid gray 1px;">
	</div>
	
	
</div>
  





