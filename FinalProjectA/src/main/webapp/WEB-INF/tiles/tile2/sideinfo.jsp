<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<%-- ======= #26. tile2 중 sideinfo 페이지 만들기  ======= --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	
	#noticeboardUL{
		width: 100%;
		border: solid 0px red;
		padding: 0;
	}

	#noticeboardUL li{
		list-style: none;
		border: solid 0px blue;
		text-align: center;
		font-size: 18pt;
		font-weight: bold;
		margin-bottom: 15px;
	}
	#noticeboardUL li:hover {
		cursor: pointer;
		background-color: #ff8040;
		color: white;
	}
	a{ color: black;}
	a:link {text-decoration: none; color: black;}
	a:visited {text-decoration: none; color: black; }
	a:active {text-decoration: none; color: black;}
	a:hover {text-decoration: none; color: black;}

	.stickyy {/* 포지션을 고정시켜서 상단바가 스크롤 내려도 계속 위에 고정되도록! */
 	 	position: fixed;
 	 	top:120px;
	}

</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		// 카테고리바 스크롤해도 같이 따라오게 하기
		var navbarTop2 = $("#noticeboardVar").offset().top;
		//var navbarLeft = $("#navbar").offset().left;
		
		var scrollTop2 = 0;
		$(window).scroll(function(event){
			scrollTop2 = $("#cartegoryVar").offset().top + 100;
			//scrollTop2 = $(this).scrollTop();
		    //alert("scrollTop : "+scrollTop);
		    //alert("navbarTop : "+navbarTop);
		
		  //var scrollBottom = $("body").height() - $(window).height() - scrollTop;

		  //console.log("scrollBottom => " + scrollBottom);
		  
		    if(scrollTop2 >= navbarTop2) {
		    	//alert("스크롤의 위치가 네비게이션위치 및 그 이하로 떨어졌네요.");
				$("#noticeboardVar").addClass("stickyy");
			}
			else {
				$("#noticeboardVar").removeClass("stickyy");
			}
		}); // end of $(window).scroll---------
		
	}); // end of ready---
		
</script>


<div align="center" style="margin-bottom: 30px;"> <h1>== 게시판 == </h1> </div>
<div id="noticeboardVar">
	<ul id="noticeboardUL">
		<a href="<%= ctxPath%>/pshComplimentBoard.yo"><li>==신고 게시판==</li></a>
		<a href="<%= ctxPath%>/oooBoardList.yo"><li>==추천 게시판==</li></a>
		<a href="<%= ctxPath%>/freePage.yo"><li>==자유 게시판==</li></a>
		<a href="<%= ctxPath%>/moonlist.yo"><li>==문의 게시판==</li></a>
		<a href="<%= ctxPath%>/greetingList.yo"><li>==가입인사 게시판==</li></a>
		<a href="<%= ctxPath%>/kkkBoardList.yo"><li>==공지사항 게시판==</li></a>
	</ul>
</div>
