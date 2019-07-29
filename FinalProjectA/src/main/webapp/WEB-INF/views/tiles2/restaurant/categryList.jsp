<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
%>

<script type="text/javascript">
	$(document).ready(function(){
		
		shoplist();
		
		$(window).scroll(function(event){
		
		  	var scrollBottom = $("body").height() - $(window).height() - $(window).scrollTop();
			
		  	if(scrollBottom == 0){
		  		
		  		 $.ajax({
		             url:"<%=request.getContextPath()%>/getShopList.yo?shopcategorycode=${shopcategorycode}",
		             type:"GET",
		             data:{},
		             dataType:"JSON",
		             success:function(json){
		            	 var html = "";
		            	 $.each(json, function(index, item){
		            		html += "<a href='<%=ctxPath%>/restaurantView.yo?masterno="+item.masterno+"'>";
		            		html += "<div class='restaurant' style='padding-top: 13px;'>";
		            		html += "<form name='restaurantViewFrm'>";
		 		  			html += "<table class='restaurant-table'>";
		 		  			html += "<tr>";
		 		  			html += "<td class='logo'>";
		 		  			html += "<div><img class='restaurant-img' src='<%=ctxPath %>/resources/images/category-01.png' /></div>";		 		  			
		 		  			html += "</td>";
		 		  			html += "<td>";
		 		  			html += "<div class='restaurants-info'>";
		 		  			html += "<div class='restaurant-name'>"+item.shopname+"</div>";
		 		  			html += "<div class='stars'> ";
		 		  			html += "<span class='ico-star1' >★ 4.0</span>";
		 		  			html += "<span class='review' >리뷰 29</span>";
		 		  			html += "<span class='review_num' >사장님댓글 2</span><br/>";
		 		  			html += "<span class='yogiseo-payment'>요기서결제</span>";
		 		  			html += "<span class='min-price'>"+item.minprice+"원 이상 배달</span><br/>";
		 		  			html += "<span class='delivery-time'>40~50분 </span>";
		 		  			html += "</div>";
		 		  			html += "</div>";
		 		  			html += "</td>";
		 		  			html += "</tr>";
		 		  			html += "</table>";
		 		  			html += "</form>";
		 		  			html += "</div>";
		            		html += "</a>";
						}); // each ---------------
		                    
		            	 $("#scrollvar").append(html);
		                
		             }, error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		             }
		             
		          });
		  	}
		  	
		}); // end of $(window).scroll---------
		
	}); // end of ready-----------
	
	function shoplist(){
		 $.ajax({
             url:"<%=request.getContextPath()%>/getShopList.yo?shopcategorycode=${shopcategorycode}",
             type:"GET",
             data:{},
             dataType:"JSON",
             success:function(json){
            	 var html = "";
            	 $.each(json, function(index, item){
            		html += "<a href='<%=ctxPath%>/restaurantView.yo?masterno="+item.masterno+"'>";
            		html += "<div class='restaurant' style='padding-top: 13px;'>";
            		html += "<form name='restaurantViewFrm'>";
 		  			html += "<table class='restaurant-table'>";
 		  			html += "<tr>";
 		  			html += "<td class='logo'>";
 		  			html += "<div><img class='restaurant-img' src='<%=ctxPath %>/resources/images/category-01.png' /></div>";		 		  			
 		  			html += "</td>";
 		  			html += "<td>";
 		  			html += "<div class='restaurants-info'>";
 		  			html += "<div class='restaurant-name'>"+item.shopname+"</div>";
 		  			html += "<div class='stars'> ";
 		  			html += "<span class='ico-star1' >★ 4.0</span>";
 		  			html += "<span class='review' >리뷰 29</span>";
 		  			html += "<span class='review_num' >사장님댓글 2</span><br/>";
 		  			html += "<span class='yogiseo-payment'>요기서결제</span>";
 		  			html += "<span class='min-price'>"+item.minprice+"원 이상 배달</span><br/>";
 		  			html += "<span class='delivery-time'>40~50분 </span>";
 		  			html += "</div>";
 		  			html += "</div>";
 		  			html += "</td>";
 		  			html += "</tr>";
 		  			html += "</table>";
 		  			html += "</form>";
 		  			html += "</div>";
            		html += "</a>";
				}); // each ---------------
                    
            	 $("#scrollvar").append(html);
                
             }, error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
             
          });
	}
	
</script>


	
<div id="scrollvar">
	
</div>
	