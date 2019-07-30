<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>

<style>
* {
	box-sizing: border-box
}

body {
	font-family: Verdana, sans-serif;
	margin: 0
}

.mySlides {
	display: none
}

img {
	vertical-align: middle;
}



.accordion {
	background-color: #eee;
	color: #444;
	cursor: pointer;
	padding: 18px;
	width: 100%;
	border: none;
	text-align: left;
	outline: none;
	font-size: 15px;
	transition: 0.4s;
}

.active, .accordion:hover {
	background-color: #ccc;
}

.panel{
	margin: 0 auto;
	padding: 0 18px;
	background-color: white;
	
	overflow: hidden; 
}

#selectvar , #selectvar>li {
	display: inline-block;
	margin: 0px;
	
}

#selectvar{
	width: 100%;
	height: 50px;
}
#selectvar>li{
	width: 32%;
	text-align: center;
	font-size: 16pt;
	padding-top: 7px;
	font-weight: bold;
}
#selectvar>li:hover{
	background: red;
	color: white;
	border-bottom: 3px solid yellow;
	cursor: pointer;
}

.addselectclass{
	background: red;
	color: white;
	border-bottom: 3px solid yellow;
}

</style>
<script>
	$(document).ready(function() {
		$(".menu-bar1").show();
		$(".menu-bar2").hide();
		$(".menu-bar3").hide();
		
			var acc = document.getElementsByClassName("accordion");
			var i;
	
			for (i = 0; i < acc.length; i++) {
				acc[i].addEventListener("click", function() {
					this.classList.toggle("active");
					var panel = this.nextElementSibling;
					if (panel.style.display === "block") {
						panel.style.display = "none";
					} else {
						panel.style.display = "block";
					}
				});
			}
			
		/* $(".accordion").click(function(){
			$(".panel").css("display","block");
		}); */
		
		
		////////////////////////////
		
		 $.ajax({
		             url:"<%=request.getContextPath()%>/kkk/menucategoryList.yo?masterno="+${masterno}+"",
		             type:"GET",
		             dataType:"JSON",
		             success:function(json){
		            	 $(".menuList").html("");
		            	 var html = "";
		            	 $.each(json, function(index, item){
		            		html+= '<button class="accordion">'+item.menuspecname+'</button>';
		            		html += '<div class="panel" id="pan'+item.menuspeccode+'" >';
					            		menuListAjax(item.menuspeccode);
		            		html += '</div>';
						}); // each ---------------
		                    
		            	 $(".menuList").append(html);
		                
		             }, error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		             }
		             
		          });
		
		///////////////////////
		
		$("#select-menu").click(function(){
			$(".menu-bar1").show();
			$(".menu-bar2").hide();
			$(".menu-bar3").hide();
			
			$("#select-menu").addClass("addselectclass");
			$("#select-review").removeClass("addselectclass");
			$("#select-info").removeClass("addselectclass");
			
		});
		$("#select-review").click(function(){
			$(".menu-bar1").hide();
			$(".menu-bar2").show();
			$(".menu-bar3").hide();
			
			$("#select-menu").removeClass("addselectclass");
			$("#select-review").addClass("addselectclass");
			$("#select-info").removeClass("addselectclass");
		});
		$("#select-info").click(function(){
			$(".menu-bar1").hide();
			$(".menu-bar2").hide();
			$(".menu-bar3").show();
			
			$("#select-menu").removeClass("addselectclass");
			$("#select-review").removeClass("addselectclass");
			$("#select-info").addClass("addselectclass");
		});
		
	}); // ready ------------------------------------------------
	
	function menuListAjax(code){
		 $.ajax({
             url:"<%=request.getContextPath()%>/kkk/menuList.yo?masterno="+${masterno}+"",
             type:"GET",
             data:{"code":code},
             dataType:"JSON",
             success:function(json){
            	 $("#pan"+code).html("");
            	 var html = "";
            		 html += "<table class='restaurant-table'>";
            	 $.each(json, function(index, item){
	 		  				
            		 html += "<tr>";
            		 html += "<td style='width: 70%;'>";
            		 html += "<div class='restaurants-info'>";
            		 html += "<div class='restaurant-name'>"+item.menuname+"</div>";
            		 html += "<span class='min-price'>"+item.menuprice+" 원 </span><br/>";		 		  			
            		 html += "</div>";
            		 html += "</td>";
            		 html += "<td style='width: 30%;'>";
            		 html += "<div><img class='restaurant-img' src='<%=request.getContextPath()%>/resources/images/"+item.filename+"' /></div>";
            		 html += "</td>";
            		 html += "</tr>";		 		  			
				}); // each ---------------
		 		  	 html += "</table>";
                    
            	 $("#pan"+code).append(html);
                
             }, error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
             
          });
	}
	
</script>
<body>
	<div style='width: 50%; margin-left: 500px;'>
		<div style='border: 1px solid gray; background-color: #fff;'>
			<div
				style="padding: 10px; font-size: 25px; border-bottom: 1px solid gray;">
				<span>${shop.shopname}</span>
			</div>
			<table style="width: 100%; height: 100%;">
				<tr>
					<td style="width: 15%;">
						<div>
							<img
								style="height: 80px; width: 80px; vertical-align: middle; margin: 10px;"
								src='<%=request.getContextPath()%>/resources/images/미샤.png' />
						</div>
					</td>
					<td>
						<div>
							<div style="font-size: 110%; text-align: left;">
								<span>★ 4.0</span> <span>리뷰29</span> <span>사장님댓글 2</span><br />
								<span>요기서결제</span> <span>${shop.minprice} 원 이상 배달</span><br />
								<span>40~50분 </span>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div >
			<ul id="selectvar">
				<li id="select-menu" onclick="">메뉴</li>
				<li id="select-review" onclick="">리뷰</li>
				<li id="select-info" onclick="">정보</li>
			</ul>
		</div>
		
			<!-- 메뉴 -->
		<div class="menu-bar1">
			<div class="menuList">
			
			</div>
		</div>
		
		<div class="menu-bar2">
			dd<br>
			dd<br>
			dd<br>
			dd<br>
		
		</div>
		
		<div class="menu-bar3">		
			ddㅇㅇ<br>
			dd<br>
			dd<br>
			dd<br>
		</div>
	

	
		
		
		
	</div>	
</body>
</html>