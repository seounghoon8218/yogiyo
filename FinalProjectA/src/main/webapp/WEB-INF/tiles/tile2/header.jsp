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

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>    

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
//   ------------------   
      
      
      $("#main-search-input").click(function(){
          new daum.Postcode({
             oncomplete: function(data) {
                $("#main-search-input").val(data.address);   
             }
          }).open();
      }); // click ---------------------------
      
      
   }); // end of ready--
   
   /////////////////// GPS ///////////////////
   
   function goGPS() {
       
       var latitude = "";
       var longitude = "";
       
       navigator.geolocation.getCurrentPosition(function(position) {
           latitude = position.coords.latitude;   //위도
           longitude = position.coords.longitude; //경도
           
       var latlng = latitude+","+longitude;  // 위도,경도
            goLatlngToAddress(latlng);
       });
              
      function goLatlngToAddress(latlng) {
         
          $.ajax({
             url:"https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDDQx9Q_JsWUjWyssoeEaeBGSbhvGcTyrA&sensor=false&language=ko&latlng="+latlng,
             type:"GET",
             data:{"latitude":latitude , "longitude":longitude},
             dataType:"JSON",
             success:function(json){
                
                if(json.status == 'OK') {
                     var html="";
                     var adlocation = json.results[1].formatted_address;
                     
                     html += adlocation.substring( 5 );
                     
                     
                     $("#main-search-input").val(html);
                  } else if(json.status == 'ZERO_RESULTS') {
                      alert("지오코딩이 성공했지만 반환된 결과가 없음을 나타냅니다.\n\n이는 지오코딩이 존재하지 않는 address 또는 원격 지역의 latlng을 전달받는 경우 발생할 수 있습니다.")
                  } else if(json.status == 'OVER_QUERY_LIMIT') {
                      alert("할당량이 초과되었습니다.");
                  } else if(json.status == 'REQUEST_DENIED') {
                      alert("요청이 거부되었습니다.\n\n대부분의 경우 sensor 매개변수가 없기 때문입니다.");
                  } else if(json.status == 'INVALID_REQUEST') {
                      alert("일반적으로 쿼리(address 또는 latlng)가 누락되었음을 나타냅니다.");
                  }
                
             }, error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
             
          }); // ajax --------------------------------------
          
      } // goLatlngToAddress ------------------------------------      
      
   } // goGPS() ---------------------------------
   
   
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
           <li><a href="<%=ctxPath%>/logout.yo"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
         </ul>
         <c:if test="${sessionScope.loginuser.email != 'admin@gmail.com' }">
            <ul class="nav navbar-nav navbar-right">
              <li><a href="#"><span class="glyphicon glyphicon-list-alt"></span> 주문표</a></li>
            </ul>
         </c:if>
         <c:if test="${sessionScope.loginuser.email == 'admin@gmail.com' }">
            <ul class="nav navbar-nav navbar-right">
              <li><a href="<%=ctxPath%>/shopregister.yo"><span class="glyphicon glyphicon-log-in"></span> 매장등록</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 메뉴등록</a></li>
            </ul>
         </c:if>
      </c:if>
    </div>
  </div>
</nav>

<div class="main-img-con" style="position: relative; margin: 0; padding: 0; height: 150px;">
      <img alt="" src="<%=ctxPath %>/resources/images/메인이미지.jpg" style="width: 100%; height: 100%; opacity: 0.8;">
      <div class="main-img-center"  style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 18px;">
          <div id="main-search-div">
            <form name="mainsearchFrm">
              <a id="gpsbtn" onclick="goGPS();"><img src="/yogiyo/resources/images/gps.jpg" style="border-radius: 10px; width: 40px; height: 40px;"/></a>
               <input id="main-search-input" type="text" placeholder="건물명, 도로명, 지번으로 검색하세요." autocomplete="off">
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


<div class="list-option">
  <div class="list-option-inner">
  <select>
    <option value="rank" >기본 정렬순</option>
    <option value="review_avg" >별점순</option>
    <option value="review_count" >리뷰 많은순</option>
    <option value="min_order_value" >최소 주문 금액순</option>
    <option value="distance" >거리순</option>    
  </select>
  <i class="arr-down"></i>
  </div>
</div>


