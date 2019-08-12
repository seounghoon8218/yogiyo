<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String ctxPath = request.getContextPath(); %>
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

.orderbtn{
   text-align:center;   
   float: left;
    width: 50%;
    height: 48px;
    border: none;
    font-size: 16px;
    font-weight: bold;
    padding-top: 15px;
    margin: 0;    
    cursor: pointer;    
}

#le{
   position: absolute;
    right: 95px;
    top: 350px;
    width: 30px;
    height: 30px;
    border: 1px solid #ccc;
    opacity: 1;
    font-size: 13pt;
    line-height: 2;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
}

#mi{
   position: absolute;
    right: 45px;
    top: 350px;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
    width: 50px;
    height: 30px;
    font-weight: bold;
    line-height: 2;
    text-align: center;
}

#rit{
   position: absolute;
    right: 15px;
    top: 350px;
    width: 30px;
    height: 30px;
    border: 1px solid #ccc;
    opacity: 1;
    line-height: 2;
    font-weight: bold;
    text-align: center;
    cursor: pointer;
}

/* 상경지훈 */
.icon1{
   font-weight: bold;
   font-size: 14pt;
   }
   

.info-item i {
    color: #999;
    font-style: normal;
    display: table-cell;
    width: 95px;
    letter-spacing: -1px;
}   
   .infoTitle { display: inline-block; width: 110px; font-style: normal;}
   /* 상경지훈 */

</style>
<script>
   $(document).ready(function() {
      
      <%-- 오명훈------------------- --%>
      reviewlistfunc();
      <%-- 오명훈------------------- --%>
      

      <%-- 박성훈 모달창------------------- --%>
      var modal = document.getElementById('id01');
      
      // When the user clicks anywhere outside of the modal, close it
      window.onclick = function(event) {
          if (event.target == modal) {
              modal.style.display = "block";
          }
      }
      <%-- 박성훈------------------- --%>
      
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
      /* 오명훈 리뷰등록  시작*/
         $("#btnCommentRegister").click(function(){
               
            var frm = document.reviewFrm;
            var commentsVal = frm.comments.value.trim();
            
            if(commentsVal ==""){
               alert("내용을 입력하세요!");
               return;
            }
         
            reviewlistfunc();   
                
         
            frm.method = "POST";
            frm.action = "<%=ctxPath%>/kkk/addReview.yo?masterno="+${masterno}+"";
            frm.submit();
         });
         /* 오명훈 리뷰등록 끝*/
         
      
   }); // ready ------------------------------------------------
   
   /* 오명훈 리뷰 보여주기*/
      
      function reviewlistfunc() {
         var commentString = $("form[name=reviewFrm]").serialize();
         $.ajax({
            url: "<%=request.getContextPath()%>/kkk/showReview.yo?masterno="+${masterno}+"",
            data: commentString,
            type: "GET",
            dataType:"JSON",
            success:function(json){
               var html="";
               console.log(json);
               
               if(json.length > 0) {
              
                  $.each(json, function(index,item){
                     html += "<div style='border: 1px solid #D5D5D5; padding:5px;' >";
                     html += "<span style='font-weight: bold; font-size: 11pt; padding-left: 15px;'>"+item.email+"</span>";
                     html += "<span style='float: right; font-size: 10pt; padding-right: 5px;'>"+item.reviewRegDate+"</span><br>";
                     if(item.starpoint==1){
                        html += "<span style='color: #ffa400; padding-left: 15px;'>★</span><span style='color: #e0e0e0;'>★★★★</span><br>";
                     }
                     
                     if(item.starpoint==2){
                        html += "<span style='color: #ffa400; padding-left: 15px;'>★★</span><span style='color: #e0e0e0;'>★★★</span><br>";
                     }
                     
                     if(item.starpoint==3){
                        html += "<span style='color: #ffa400; padding-left: 15px;'>★★★</span><span style='color: #e0e0e0;'>★★</span><br>";
                     }
                     
                     if(item.starpoint==4){
                        html += "<span style='color: #ffa400; padding-left: 15px;'>★★★★</span><span style='color: #e0e0e0;'>★</span><br>";
                     }
                     
                     if(item.starpoint==5){
                        html += "<span style='color: #ffa400; padding-left: 15px;'>★★★★★</span><span style='color: #e0e0e0;'></span><br>";
                     }
                     if(item.image != null){
                        html += "<img src='<%=ctxPath%>/resources/images/"+item.image+"' style='width: 500px; height: 300px;'/><br>";
                     }
                     /* html += "<span style='color:#E5D85C; padding-left: 15px;'>"+855416846568+"</span><br><br>"; */
                     html += "<span style='padding-left: 15px; font-size: 12pt;'>"+item.comments+"</span>";
                     html += "</div>";
                  }); // end each
               
               }
               
               
               $("#reviewShow").html(html);
               
               
            },error: function(request, status, error){
                   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
               }
      
         }); // end ajax
      }
   <%-- 오명훈-------------- --%>
   
   <%-- 박성훈-------------- --%>
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
                   
                   html += '<tr onclick= \'modaltest( "'+item.menuname +'","'+ item.menucode +'","'+ item.masterno +'","'+ item.menuprice +'","'+ item.filename+'")\' data-toggle="modal" data-target="#myModal" >';
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
   } // end of menuListAjax---------
   <%-- 박성훈-------------- --%>
   
   <%-- 박성훈-------------- --%>
   function modaltest(menuname,menucode,masterno,menuprice,filename) {
      
      var html = "";
      html += "<div><img  style='width:100%; height:200px; margin: 0 auto;' src='<%=request.getContextPath()%>/resources/images/"+filename+"' /></div>";
      html += "<div style='padding:15px 0; font-size: 30px; text-align:center; border-bottom: 1px solid gray;'><span >"+menuname+"</span></div>";
      html += "<div style='padding:15px 0; font-size: 16px; border-bottom: 1px solid gray;'><strong style='margin-right:280px;'>가격</strong><span>"+menuprice+" 원</span></div>";
      html += "<div style='padding:15px 0; font-size: 16px; border-bottom: 1px solid gray;'><strong style='margin-right:280px;'>수량</strong>";
      html += "<div id='le' onclick='disctn()' >-</div>";
      html += "<div id='mi'>1</div>";
      html += "<div id='rit' onclick='addctn()'>+</div>";
      html += "</div>";
      html += "<div style='padding:15px 0; font-size: 16px; '><strong style='margin-right:250px;'>총 주문금액</strong><span id='totalprice'>"+menuprice+"</span> 원</div><input id='toinput' type='hidden' value='"+menuprice+"' />";
      
      $(".modal-body").html(html);
      
      html = "";
      html += '<a class="orderbtn" style="background-color: black; color: white;" onclick=\'orderAdd("'+menuname+'","'+menucode+'","'+masterno+'","'+menuprice+'","'+filename+'");\' >주문표추가</a>'  
        html += '<a class="orderbtn" style="background-color: red; color: white;" onClick=\'myinfowrite("'+menuname +'","'+ menucode +'","'+ masterno +'");\' >주문하기</a>'
      
        $(".modal-footer").html(html);
   } 
   <%-- 박성훈-------------- --%>

   <%-- 박성훈-------------- --%>
      function disctn() {
         var num = $("#mi").text();
         if(num != 1){
         num--;            
         }
         $("#menuqty").val(num);
         $("#mi").text(num);
         var totalprice = $("#toinput").val();
         totalprice = totalprice * num;
         $("#totalprice").text(totalprice);
      }
      
      function addctn() {
         var num = $("#mi").text();
         if(num != 10){
         num++;            
         }
         $("#menuqty").val(num);
         $("#mi").text(num);
         var totalprice = $("#toinput").val();
         totalprice = totalprice * num;
         $("#totalprice").text(totalprice);
      }
      
      // 장바구니에 추가하기
      function orderAdd(menuname,menucode,masterno,menuprice,filename) {
         var frm = document.orderAddFrm;
         frm.menuname.value = menuname;
         frm.menucode.value = menucode;
         frm.masterno.value = masterno;
         frm.menuprice.value = menuprice;
         frm.filename.value = filename;
         
          var form_data = $("form[name=orderAddFrm]").serialize();
          $.ajax({
                url:"<%=request.getContextPath()%>/kkk/orderAdd.yo",
                type:"GET",
                data:form_data,
                dataType:"JSON",
                success:function(json){
                    if(json.n == 1){
                       alert("주문표에 추가되었습니다.");
                    }else if(json.n == 2){
                       alert("이미 주문표에 추가되었습니다. ( 주문표를 확인해주세요. )");
                    } else if (json.n == 3){
                       alert("먼저 로그인하세요.");                       
                    } else if (json.n == 4){
                       alert("이미 다른 음식점의 메뉴가 주문표에 존재합니다.");                       
                    }
                    
                }, error: function(request, status, error){
                   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }
                
             });
         
      }
   <%-- 박성훈-------------- --%>

   <%-- 박성훈-------------- --%>
    function myinfowrite(menuname,menucode,masterno) {

       var totalprice = $("#totalprice").text();
       
       var url = "<%=request.getContextPath()%>/myinfowrite.yo?menuname="+menuname+"&menucode="+menucode+"&masterno="+masterno+"&totalprice="+totalprice;
              
       window.open(url,"myinfowrite",
      "left=350px, top=100px, width=500px, height=420px");
          
       
    }
    
   // 바로주문하기!!!!!!!--------------------------------------------------------------------------------------------------
    function goOrder(menuname,menucode,masterno,addr2,tel,yocheong) {
      
      var totalprice = $("#totalprice").text();
       
      // 여기 결제까지 잘왔어 .. addr1 , 2 , tel 정보는 세션스토리지에 넣어뒀으니까 여기서 꺼내서 밑에 url에 추가로 보내줘서 controll단에서 받아서 결제 ㄱ
      
       var addr1 = sessionStorage.getItem('addr1');
      
      
      
      
      
       
       // 아임포트 결제 팝업창 띄우기!
       var url = "<%=request.getContextPath()%>/payment.yo?menuname="+menuname+"&menucode="+menucode+"&masterno="+masterno+"&totalprice="+totalprice+"&addr1="+addr1+"&addr2="+addr2+"&tel="+tel+"&yocheong="+yocheong;
       
       window.open(url,"payment",
                   "left=350px, top=100px, width=820px, height=600px");
       
   } // end of 주문
   
    function goCoinUpdate(menuname,menucode,masterno,totalprice,addr1,addr2,tel,yocheong) { // 실제 결제된 만큼 DB 업데이트 해주기..
      
      var frm = document.paymentUpdateFrm;
      frm.menuname.value = menuname;
      frm.menucode.value = menucode;
      frm.masterno.value = masterno;
      frm.totalprice.value = totalprice;
      frm.addr1.value = addr1;
      frm.addr2.value = addr2;
      frm.tel.value = tel;
      frm.yocheong.value = yocheong;
      frm.method = "POST";
      frm.action = "<%=request.getContextPath()%>/paymentEnd.yo";
      frm.submit();
      
   }// end of function goCoinUpdate(idx, coinmoney)----
   <%-- 박성훈-------------- --%>
   
   
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
                        src='<%=request.getContextPath()%>/resources/images/${shop.shopimage}' />
                  </div>
               </td>
               <td>
                  <div>
                     <div style="font-size: 110%; text-align: left;">
                        <span>★ ${starpointAvg}</span> <br><span>리뷰 ${reviewCount }</span><br />
                        <span>요기서결제 | </span> <span>${shop.minprice} 원 이상 배달</span><br />
                        
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
      
      <!-- 리뷰  -->
      <div class="menu-bar2" style="border: 1px solid #D5D5D5; margin-top: 20px;">
         <!-- 총 별점 평균 시작-->
         <div style="border: 0px solid #D5D5D5;" align="center"> 
               
               <span style="font-weight: bold; font-size: 30pt;">${starpointAvg}</span>
                           
               <div style="font-size: 20pt;">
                  <c:if test="${fn:containsIgnoreCase(starpointAvg,'1.') == true }">
                     <span style="color: #ffa400;">★</span><span style="color: #e0e0e0;">★★★★</span>
                  </c:if>
                  <c:if test="${fn:containsIgnoreCase(starpointAvg,'2.') == true }">
                     <span style="color: #ffa400;">★★</span><span style="color: #e0e0e0;">★★★</span>
                  </c:if>
                  <c:if test="${fn:containsIgnoreCase(starpointAvg,'3.') == true }">
                     <span style="color: #ffa400;">★★★</span><span style="color: #e0e0e0;">★★</span>
                  </c:if>
                  <c:if test="${fn:containsIgnoreCase(starpointAvg,'4.') == true }">
                     <span style="color: #ffa400;">★★★★</span><span style="color: #e0e0e0;">★</span>
                  </c:if>
                  <c:if test="${fn:containsIgnoreCase(starpointAvg,'5.') == true }">
                     <span style="color: #ffa400;">★★★★★</span>
                  </c:if>
               </div>
         </div>
         <!-- 총 별점 평균 끝 -->
         
         <!--  총 리뷰 갯수 구하기 시작 -->
         <div style="border: 1px solid #D5D5D5; padding: 10px;">
             리뷰&nbsp;<span style="text-decoration: underline;"><span style="font-weight: bold;">${reviewCount}</span>개</span>
         </div>
         
         <!--  총 리뷰 갯수 구하기 끝 -->
         <!--  리뷰 등록 시작 -->
         <div id="reviewRegister" style="border: 1px solid #D5D5D5; padding: 5px 0px 5px 20px;">
            <form name="reviewFrm"  enctype="multipart/form-data">
               <select name="starpoint" style="color: #ffa400;">
                  <option value="5" style="color: #ffa400;">★★★★★ </option>
                  <option value="4" style="color: #ffa400;">★★★★ </option>
                  <option value="3" style="color: #ffa400;">★★★ </option>
                  <option value="2" style="color: #ffa400;">★★ </option>
                  <option value="1" style="color: #ffa400;">★ </option>
               </select>
               <input type="file" name="attach" />
               <textarea  cols="87" rows="5" name="comments" id="comments"></textarea>
               <button type="button" id="btnCommentRegister">리뷰 등록</button>
               
            <c:if test="${reviewList != null}">
               <c:forEach items="${reviewList}" var="review">
                  <input type="hidden" name="masterno" value="${review.fk_masterno}"/>
                  <input type="hidden" name="email" value="${review.email}"/>
                  <input type="hidden" name="menuname" value="${review.menuname}"/>               
               </c:forEach>
            </c:if>
            </form>
         </div>
         <!--  리뷰 등록 끝  -->
         
         
         <!--  리뷰 보여주기  -->
         <div id="reviewShow" style="border: 1px solid #D5D5D5;">
         
         </div>
         <!--  리뷰 보여주기  -->
   </div>
      
      <%-- 매장정보 시작       상경,지훈 --%>
      <div class="menu-bar3" style="margin-top: 20px;">      
      <c:forEach items="${shopInfo }" var="shopInfoMap">
      
      
         <div class="icon1"><img src='<%=request.getContextPath()%>/resources/images/나의안락한집.PNG'/>업체정보</div>
            <p style="border-top: 1px solid gray; margin-bottom:30px;">
               <i class="infoTitle">영업시간</i>&nbsp;   <span>${shopInfoMap.shoptime}</span>
            </p>
         
              <div class="icon1"><img src='<%=request.getContextPath()%>/resources/images/카드.PNG'/>결제정보</div>
              <p style="border-top: 1px solid gray;">
                 <i class="infoTitle"">최소주문금액</i>&nbsp; <span>${shopInfoMap.minprice}</span>
              </p>
              <p style="margin-bottom: 30px;">
                 <i class="infoTitle"">결제수단</i>&nbsp; <span>${shopInfoMap.paymethod}</span>
              </p>
                  
              <div class="icon1"><img src='<%=request.getContextPath()%>/resources/images/사업자.PNG'/>사업자정보</div>
              <p style="border-top: 1px solid gray;">
                 <i class="infoTitle"">상호명</i>&nbsp; <span>${shopInfoMap.sanghoname}</span>
              </p>
              <p style="margin-bottom: 30px;">
                 <i class="infoTitle"">사업자등록 번호</i>&nbsp;<span>${shopInfoMap.masterno}</span>
              </p>
              
              <div class="icon1"><img src='<%=request.getContextPath()%>/resources/images/원산지.PNG'/>원산지정보</div>
              <p style="border-top: 1px solid gray;">
                 <span>${shopInfoMap.wonsanji}</span>
              </p>
              
              </c:forEach>
      </div>
         <%-- 매장정보 시작    상경,지훈 --%>

<!-- 결제모달창 박성훈 -->   
<div class="container">
  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content" style="width: 460px;">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">메뉴상세</h4>
        </div>
        <div class="modal-body">
        </div>
        <div class="modal-footer" style="bottom: 0;">
                 
        </div>
      </div>
      
    </div>
  </div>  
</div>
<!-- 결제모달창 박성훈 -->   

<!-- 결제테이블업데이트 박성훈 -->   
<form name="paymentUpdateFrm">
   <input type="hidden" id="menuname" name="menuname" />
   <input type="hidden" id="menucode" name="menucode" />
   <input type="hidden" id="masterno" name="masterno" />
   <input type="hidden" id="totalprice" name="totalprice" />
   <input type="hidden" id="addr1" name="addr1" />
   <input type="hidden" id="addr2" name="addr2" />
   <input type="hidden" id="tel" name="tel" />
   <input type="hidden" id="yocheong" name="yocheong" />
</form>
<!-- 결제테이블업데이트 박성훈 -->
   
<!-- 장바구니테이블업데이트 박성훈 -->   
<form name="orderAddFrm">
   <input type="hidden" id="menuname" name="menuname" />
   <input type="hidden" id="menucode" name="menucode" />
   <input type="hidden" id="masterno" name="masterno" />
   <input type="hidden" id="menuprice" name="menuprice" />
   <input type="hidden" id="filename" name="filename" />
   <input type="hidden" id="menuqty" value="1" name="menuqty" />
</form>
<!-- 장바구니테이블업데이트 박성훈 -->   

      
   </div>   
</body>
</html>