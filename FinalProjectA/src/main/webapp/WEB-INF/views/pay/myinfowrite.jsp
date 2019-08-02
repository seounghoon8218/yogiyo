<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style type="text/css">

	ul, li {
		list-style: none;
		width: 90%;
	}	
	
	input {
		float: right;
		width: 60%;
		height: 25px;
	}

	span {
		float: right;
		width: 60%;
		font-size: 8pt;
		color: red;
	}
	
	#li2 {
		   margin-top: 40px;
	}
	
	#okbtn{
		background-color: red;
		color: white;
		font-weight: bold;
		cursor: pointer;
	}
	#okbtn:hover {
		border-bottom: 4px solid yellow;
	}
	
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
        var addr1 = sessionStorage.getItem('addr1');
        
        $("#addr1").val(addr1);
        
        $(".error1").show();
        $(".error2").show();
        
        $("#addr2").blur(function(){
	        var addr2 = $("#addr2").val().trim();
	        
	        if( addr2 != "" ){
	        	$(".error1").hide();
	        }else{
	        	$(".error1").show();	        	
	        }
        });
        $("#tel").keydown(function(){
	        var tel = $("#tel").val().trim();
	        
	        if( tel != "" ){
	        	$(".error2").hide();
	        }else{
	        	$(".error2").show();	        	
	        }
        });
        
	});
	
	function goSessionStorage() {
		var addr2 = $("#addr2").val().trim();
		var tel = $("#tel").val().trim();
		var yocheong = $("#yocheong").val().trim();
	
		if(yocheong == null ){
			yocheong = "";
		}
		
		if( addr2 == "" ){
        	$(".error1").show();
        }else if( tel == "" ){
        	$(".error2").show();	        	
        }
        else{
        	
        	var menuname = '${menuname}';
        	var menucode = '${menucode}';
        	var masterno = '${masterno}';
        	opener.parent.goOrder(menuname,menucode,masterno,addr2,tel,yocheong);
        	
        	window.close();
        }
	}
	
</script>
</head>
<body>
	<h2 style="border-bottom: 1px solid gray;">배달정보</h2>
	<div>
		<ul>
			<li><label>주소</label> <input id="addr1" name="addr1" type="text"  readonly="readonly" disabled="true" /></li>
			<li><label>&nbsp;</label><input id="addr2" name="addr2" type="text" placeholder="(필수) 상세주소 입력"/><br/><span class="error1" >상세한 주소를 입력해주세요</span><br/></li>
			<li id="li2" ><label>휴대전화번호</label><input id="tel" name="tel" type="tel" placeholder="(필수) 휴대전화 번호 입력"/><br/><span class="error2">전화번호를 입력해주세요. <br/>(050 등 안심번호 사용 불가)</span></li>
		</ul>
	</div><br/>
	<div>
		<ul>
			<li><label>주문시 요청사항</label></li>
			<li><textarea id="yocheong" name="yocheong" rows="3" cols="50" style="font-size: 11pt;" placeholder="주문시 요청 사항이 있으시면 남겨주세요."></textarea></li>
		</ul>
		
	</div>
	<div id="okbtn" align="center" style="margin-top: 40px; font-size: 20pt;" onclick="goSessionStorage()" >확인</div>
</body>
</html>