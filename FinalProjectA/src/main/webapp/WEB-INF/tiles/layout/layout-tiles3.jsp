<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%-- === #22. tiles 를 사용하는 레이아웃1 페이지 만들기 === --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%  
	String ctxPath = request.getContextPath();
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요기요2</title>

<!-- 반응형 웹 쓰겠다. (부트스트랩)-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/resources/css/style1.css" />

</head>
<body>
	<div id="mycontainer">
		<div id="myheader">
			<tiles:insertAttribute name="header" /> <!-- tiles-layout.xml 의 name 값과 같은거 불러오는것 -->
		</div>
		
		<div id="mycontent">
			<tiles:insertAttribute name="content" />
		</div>
		
		<div id="myfooter">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>