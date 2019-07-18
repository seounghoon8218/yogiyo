<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ======= #25. tile1 중 header 페이지 만들기  ======= --%>
<%
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript" src="<%=ctxPath %>/js/jquery-1.12.4.min.js"></script> 
<%-- jquery 1.x 또는 jquery 2.x 를 사용해야만 한다. 구글맵은 jquery 3.x 을 사용할 수 없다.   --%>

<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDDQx9Q_JsWUjWyssoeEaeBGSbhvGcTyrA"></script>

<script type="text/javascript">

	function goGPS() {
		
		var latitude = "";
		var longitude = "";
		 navigator.geolocation.getCurrentPosition(function(position) {
			 latitude = position.coords.latitude;   //위도
		     longitude = position.coords.longitude; //경도
		 });
		 $xml = simplexml_load_file("http://maps.google.co.kr/maps/api/geocode/xml?latlng=".latitude.",".longitude."&sensor=false");
		 $get_address = $xml->result->formatted_address;
		$.ajax({
			url:"<%= ctxPath%>/gps.yo",
			type:"GET",
			data:{"latitude":latitude , "longitude":longitude},
			dataType:"JSON",
			success:function(json){
				
				latitude = json.latitude;
				longitude = json.longitude;
				
				var html =latitude+" - "+longitude;
				
				$("#main-search-input").val(html);
				
			}, error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
			
		});
	 
	}
	
</script>

<nav class="navbar navbar-inverse" style="background-color: red; border: none; height: 80px; margin-bottom: 0">
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
	        <li><a href="<%=ctxPath%>/login.yo"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
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
<div class="col-sm-12 main-img-con" style="position: relative; margin: 0; padding: 0; height: 250px;">
		<img alt="" src="<%=ctxPath %>/resources/images/메인이미지.jpg" style="width: 100%; height: 100%; opacity: 0.8;">
		<div class="main-img-center"  style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 18px;">
			<h1 style="font-weight: bold; color: white; ">"어디로 <span style="color:#FAED7D;">배달</span>해 드릴까요?"</h2>
			<h4 style="font-weight: bold; color: white; text-align: center;">배달받으실 동 이름으로 검색해주세요.</h5>
			
			 <div id="main-search-div">
				<form name="mainsearchFrm">
				  <a id="gpsbtn" onclick="goGPS();" ><img src="/yogiyo/resources/images/gps.jpg" style="border-radius: 10px; width: 40px; height: 40px;"/></a>
			      <input  id="main-search-input" type="text" placeholder="건물명, 도로명, 지번으로 검색하세요.">
			      <button id="main-search-button">검색</button>
			    </form>
		    </div>
		</div>
</div>