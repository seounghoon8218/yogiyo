<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
%>

<script type="text/javascript">
	$(document).ready(function(){
		
		var latitude = sessionStorage.getItem('latitude', latitude);
		var longitude = sessionStorage.getItem('longitude', longitude);
		
		var cnt = 1;
		
		shoplist(cnt,latitude,longitude);
		
		$(window).scroll(function(event){
		
		  	var scrollBottom = $("body").height() - $(window).height() - $(window).scrollTop();
			
		  	if(scrollBottom == 0){
		  		cnt += 4;
		  		shoplist(cnt,latitude,longitude);
		  		
		  	}
		  	
		}); // end of $(window).scroll---------
		
	}); // end of ready-----------
	
	function shoplist(cnt,latitude,longitude){
		
		 $.ajax({
             url:"<%=request.getContextPath()%>/getShopList.yo?shopcategorycode=${shopcategorycode}&cnt="+cnt+"&latitude="+latitude+"&longitude="+longitude,
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
                     html += "<div><img class='restaurant-img' src='<%=ctxPath %>/resources/images/"+item.shopimage+"' /></div>";                        
                     html += "</td>";
                     html += "<td>";
                     html += "<div class='restaurants-info'>";
                     html += "<div class='restaurant-name'>"+item.shopname+"</div>";
                     html += "<div class='stars'> ";
                     html += "<span class='ico-star1' >★ "+item.starpointAvg+"</span><br>";
                     html += "<span class='review' >리뷰 "+item.reviewCount+"</span><br>";
                     html += "<span class='yogiseo-payment'>요기서결제 | </span> ";
                     html += "<span class='min-price'>"+item.minprice+"원 이상 배달</span><br/>";
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
	