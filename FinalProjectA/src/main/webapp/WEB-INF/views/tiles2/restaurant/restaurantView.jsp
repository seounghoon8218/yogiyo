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

/* Slideshow container */
.slideshow-container {
	max-width: 1000px;
	position: relative;
	margin: auto;
}

/* Next & previous buttons */
.prev, .next {
	cursor: pointer;
	position: absolute;
	top: 50%;
	width: auto;
	padding: 16px;
	margin-top: -22px;
	color: white;
	font-weight: bold;
	font-size: 18px;
	transition: 0.6s ease;
	border-radius: 0 3px 3px 0;
	user-select: none;
}

/* Position the "next button" to the right */
.next {
	right: 0;
	border-radius: 3px 0 0 3px;
}

/* On hover, add a black background color with a little bit see-through */
.prev:hover, .next:hover {
	background-color: rgba(0, 0, 0, 0.8);
}

/* Caption text */
.text {
	color: #f2f2f2;
	font-size: 15px;
	padding: 8px 12px;
	position: absolute;
	bottom: 8px;
	width: 100%;
	text-align: center;
}

/* Number text (1/3 etc) */
.numbertext {
	color: #f2f2f2;
	font-size: 12px;
	padding: 8px 12px;
	position: absolute;
	top: 0;
}

/* The dots/bullets/indicators */
.dot {
	cursor: pointer;
	height: 15px;
	width: 15px;
	margin: 0 2px;
	background-color: #bbb;
	border-radius: 50%;
	display: inline-block;
	transition: background-color 0.6s ease;
}

.active, .dot:hover {
	background-color: #717171;
}

/* Fading animation */
.fade {
	-webkit-animation-name: fade;
	-webkit-animation-duration: 1.5s;
	animation-name: fade;
	animation-duration: 1.5s;
}

@
-webkit-keyframes fade {
	from {opacity: .4
}

to {
	opacity: 1
}

}
@
keyframes fade {
	from {opacity: .4
}

to {
	opacity: 1
}

}

/* On smaller screens, decrease text size */
@media only screen and (max-width: 300px) {
	.prev, .next, .text {
		font-size: 11px
	}
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

.panel {
	margin: 0 auto;
	padding: 0 18px;
	display: none;
	background-color: white;
	overflow: hidden;
}
</style>
<script>
	$(document).ready(function() {

		var slideIndex = 1;
		showSlides(slideIndex);

		function plusSlides(n) {
			showSlides(slideIndex += n);
		}

		function currentSlide(n) {
			showSlides(slideIndex = n);
		}

		function showSlides(n) {
			var i;
			var slides = document.getElementsByClassName("mySlides");
			var dots = document.getElementsByClassName("dot");
			if (n > slides.length) {
				slideIndex = 1
			}
			if (n < 1) {
				slideIndex = slides.length
			}
			for (i = 0; i < slides.length; i++) {
				slides[i].style.display = "none";
			}
			for (i = 0; i < dots.length; i++) {
				dots[i].className = dots[i].className.replace(" active", "");
			}
			slides[slideIndex - 1].style.display = "block";
			dots[slideIndex - 1].className += " active";
		}

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
		            		html += '<div class="a" id="pan'+item.menuspeccode+'">';
					            		menuListAjax(item.menuspeccode);
		            		html += '</div>';
						}); // each ---------------
		                    
		            	 $(".menuList").append(html);
		                
		             }, error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		             }
		             
		          });
		
		///////////////////////
		
		
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


		<div class="slideshow-container">

			<div class="mySlides fade">
				<div class="numbertext">1 / 3</div>
				<img src='<%=request.getContextPath()%>/resources/images/미샤.png'
					style="width: 100%">
				<div class="text">Caption Text</div>
			</div>

			<div class="mySlides fade">
				<div class="numbertext">2 / 3</div>
				<img src="<%=request.getContextPath()%>/resources/images/미샤.png"
					style="width: 100%">
				<div class="text">Caption Two</div>
			</div>

			<div class="mySlides fade">
				<div class="numbertext">3 / 3</div>
				<img src="<%=request.getContextPath()%>/resources/images/미샤.png"
					style="width: 100%">
				<div class="text">Caption Three</div>
			</div>

			<a class="prev" onclick="plusSlides(-1)">&#10094;</a> <a class="next"
				onclick="plusSlides(1)">&#10095;</a>

		</div>
		<br>

		<div style="text-align: center">
			<span class="dot" onclick="currentSlide(1)"></span> <span class="dot"
				onclick="currentSlide(2)"></span> <span class="dot"
				onclick="currentSlide(3)"></span>
		</div>

		<div class="menuList">
			<button class="accordion">주메뉴</button>
			<div class="panel" id="">
				<table class='restaurant-table'>
		 		  			<tr>
		 		  				
		 		  			<td style="width: 70%;">
		 		  				<div class='restaurants-info'>
		 		  				<div class='restaurant-name'>고구마</div>
		 		  				<span class='min-price'>13000원</span><br/>		 		  			
		 		  				</div>
		 		  			</td>
		 		  			<td style="width: 30%;">
		 		  				<div><img class='restaurant-img' src='<%=request.getContextPath()%>/resources/images/미샤.png' /></div>
		 		  			</td>
		 		  			</tr>		 		  			
		 		</table>
			</div>
			
		</div>
	</div>
</body>
</html>