<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<<<<<<< HEAD
<% String ctxPath = request.getContextPath(); %>
=======
<%
	String ctxPath = request.getContextPath();
%>
>>>>>>> branch 'master' of https://github.com/seounghoon8218/yogiyo.git
<script type="text/javascript" src="<%=ctxPath %>/js/jquery-1.12.4.min.js"></script> 
<%-- jquery 1.x 또는 jquery 2.x 를 사용해야만 한다. 구글맵은 jquery 3.x 을 사용할 수 없다.   --%>

<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDDQx9Q_JsWUjWyssoeEaeBGSbhvGcTyrA"></script>

<script type="text/javascript">

$(document).ready(function () {

	 navigator.geolocation.getCurrentPosition(function(position) {
		 var latitude = position.coords.latitude;   //위도
	     var longitude = position.coords.longitude; //경도
	     
	     $("#latitude").val(latitude);
	     $("#longitude").val(longitude);
	     
	 });
		
 });// end of $(document).ready(function (){}---------------
		 
		 
		 
 
</script>
<form name="gpsFrm">
        <input type="hidden" id="latitude" />
        <input type="hidden" id="longitude" />
</form>    
	    