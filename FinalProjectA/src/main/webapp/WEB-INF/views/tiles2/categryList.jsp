<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
%>

<script type="text/javascript">
	$(document).ready(function(){
		
		$(window).scroll(function(event){
		
		  	var scrollBottom = $("body").height() - $(window).height() - $(window).scrollTop();
			
		  	if(scrollBottom == 0){
		  		
		  		 $.ajax({
		             url:"<%=request.getContextPath()%>/getShopList.yo",
		             type:"GET",
		             data:{},
		             dataType:"JSON",
		             success:function(json){
		            	 var html = "";
		            	 $.each(json, function(index, item){
		            		html += "<div class='restaurant' style='padding-top: 13px;'>";
		 		  			html += "<table>";
		 		  			html += "<tr>";
		 		  			html += "<td>";
		 		  			html += "<div class='logo'><img class='restaurant-img' src='<%=ctxPath %>/resources/images/category-01.png' /></div>";
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
		 		  			html += "</div>";
						}); // each ---------------
		                    
		            	 $("#scrollvar").append(html);
		                
		             }, error: function(request, status, error){
		                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		             }
		             
		          });
		  		
		  		<%--var html = "";
		  		 for(var i=0; i<10; i++){
		  			html += "<div class='restaurant' style='padding-top: 13px;'>";
		  			html += "<table>";
		  			html += "<tr>";
		  			html += "<td>";
		  			html += "<div class='logo'><img class='restaurant-img' src='<%=ctxPath %>/resources/images/category-01.png' /></div>";
		  			html += "</td>";
		  			html += "<td>";
		  			html += "<div class='restaurants-info'>";
		  			html += "<div class='restaurant-name' title='고릴라김밥'>고릴라김밥</div>";
		  			html += "<div class='stars'> ";
		  			html += "<span class='ico-star1' >★ 4.0</span>";
		  			html += "<span class='review' >리뷰 29</span>";
		  			html += "<span class='review_num' >사장님댓글 2</span><br/>";
		  			html += "<span class='yogiseo-payment'>요기서결제</span>";
		  			html += "<span class='min-price'>16,000원 이상 배달</span><br/>";
		  			html += "<span class='delivery-time'>40~50분 </span>";
		  			html += "</div>";
		  			html += "</div>";
		  			html += "</td>";
		  			html += "</tr>";
		  			html += "</table>";
		  			html += "</div>";
		  		} --%>
		  		$("#scrollvar").append(html);
		  	}
		  	
		}); // end of $(window).scroll---------
		
		
	}); // end of ready-----------
</script>

<%-- <div class="restaurant" style="padding-top: 13px;">
	<table>
		<tr>
            <td>
              <div class="logo"><img class="restaurant-img" src="<%=ctxPath %>/resources/images/category-01.png" /></div>
            </td>
            <td>
              <div class="restaurants-info">                
                <div class="restaurant-name " title="고릴라김밥">고릴라김밥</div>
                <div class="stars">                  
                  <span class="ico-star1 " >★ 4.0</span>                  
                  <span class="review " >리뷰 29</span>
                  <span class="review_num " >사장님댓글 2</span><br/>               
                  <span class="yogiseo-payment">요기서결제</span>
                  <span class="min-price">16,000원 이상 배달</span><br/>
                  <span class="delivery-time">40~50분 </span>
                </div>
              </div>
            </td>
          </tr>		
	</table>
</div> --%>



	
<div id="scrollvar">
	
</div>
	