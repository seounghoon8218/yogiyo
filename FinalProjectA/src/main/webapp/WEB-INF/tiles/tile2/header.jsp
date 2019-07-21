<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ======= #25. tile1 중 header 페이지 만들기  ======= --%>
<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
	#categoryul {
		list-style:none;
    	margin:0;
    	padding:0;
    	border: solid 0px red;
    	height: 100%;
	}
	
	#categoryul li{
		text-align: center;
		vertical-align: middle;
		width : 9%;
		height : 100%;
		margin: 0 0 0 0;
		padding-top: 15px;
    	border : 0;
    	float: left;
    	border: solid 0px blue;
	}
	#categoryul li:hover{
		background-color: black;
		color: white;
	}
	
	.categorylia{
		color: black !important;
		font-weight: bold;
		height: 100%;
		width: 100%;
	}
	.categorylia:hover {
		cursor: pointer;
	}
	
	#cartegoryVar{
		width:100%;
		background-color: white;
		border-bottom: 3px solid gray;
	}
	
	.sticky {/* 포지션을 고정시켜서 상단바가 스크롤 내려도 계속 위에 고정되도록! */
 	 	position: fixed;
  		top: 0;
  		
  		width: 100%; 
	}
	
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$.ajax({
			url:"<%= ctxPath%>/categoryListAjax.yo",
			type:"GET",
			dataType:"JSON",
			success:function(json){
				
				$("#categoryul").empty();
				var html = "";
					html += "<a class='categorylia'><li>검색(이미지)</li></a>";
					html += "<a class='categorylia'><li>전체보기</li></a>";
				$.each(json, function(index,item){
					html += "<a class='categorylia'><li>"+item.shopcategoryname+"</li></a>";
				});
				
				$("#categoryul").append(html);	
			}, error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		}); // end of ajax---
		
// 카테고리바 스크롤해도 같이 따라오게 하기
		var navbarTop = $("#cartegoryVar").offset().top;
		//var navbarLeft = $("#navbar").offset().left;
		
		var scrollTop = 0;
		$(window).scroll(function(event){
			scrollTop = $(this).scrollTop();
		  //  alert("scrollTop : "+scrollTop);
		  //  alert("navbarTop : "+navbarTop);
		
		  //var scrollBottom = $("body").height() - $(window).height() - scrollTop;

		  //console.log("scrollBottom => " + scrollBottom);
		  
		    if(scrollTop >= navbarTop) {
		    	//alert("스크롤의 위치가 네비게이션위치 및 그 이하로 떨어졌네요.");
				$("#cartegoryVar").addClass("sticky");
			}
			else {
				$("#cartegoryVar").removeClass("sticky");
			}
		}); // end of $(window).scroll---------
//	------------------	
		
	}); // end of ready--
</script>

<nav class="navbar navbar-inverse" style="background-color: red; border: none; margin-bottom: 0">
  <div class="container-fluid" style="width: 70%;font-size: 16pt;" >
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=ctxPath%>/index.yo"><img src="<%=ctxPath %>/resources/images/요기요로고.png" style="width: 100px; height: 50px;" /></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar" style="color: white; margin-top: 12px;">
     <c:if test="${sessionScope.loginuser == null }">
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="<%=ctxPath%>/register.yo"><span class="glyphicon glyphicon-user"></span> 회원가입</a></li>
	      </ul>
     </c:if>
     <c:if test="${sessionScope.loginuser != null }">
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="#"><span class="glyphicon glyphicon-user"></span> 내정보</a></li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="#"><span class="glyphicon glyphicon-list-alt"></span> 주문표</a></li>
	      </ul>
      </c:if>
    </div>
  </div>
</nav>

<div class="main-img-con" style="position: relative; margin: 0; padding: 0; height: 150px;">
		<img alt="" src="<%=ctxPath %>/resources/images/메인이미지.jpg" style="width: 100%; height: 100%; opacity: 0.8;">
		<div class="main-img-center"  style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 18px;">
			 <div id="main-search-div">
				<form name="mainsearchFrm">
			      <input id="main-search-input" type="text" placeholder="건물명, 도로명, 지번으로 검색하세요.">
			      <button id="main-search-button">검색</button>
			    </form>
		    </div>
		</div>
</div>

<div id="cartegoryVar" >
	<div style="height: 50px; background-color: white;  padding: 0; width: 80%; margin: 0 auto;">
		<ul id="categoryul">
		</ul>
	</div>
</div>