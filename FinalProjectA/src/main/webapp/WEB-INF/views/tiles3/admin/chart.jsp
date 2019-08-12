<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
   String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 차트보기 </title>
<link rel="stylesheet" href="<%=ctxPath %>/resources/jquery-ui-1.11.4.custom/jquery-ui.min.css">
<script type="text/javascript" src="<%=ctxPath %>/resources/js/jquery-3.3.1.min.js"></script><%-- 웹컨텐트에 해당하는게 webapp이다 생략 --%>
<script type="text/javascript" src="<%=ctxPath %>/resources/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>

<%-- 원 차트!!! --%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<%-- 막대 차트!!! --%>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/drilldown.js"></script>



<style type="text/css">
   select {
      height: 35px;
      font-weight: bold;
      font-size: 14pt;
   }
   select:hover {
      cursor: pointer;
   }
   
   #memTable {
      margin-top: 35px;
      width: 750px;
      border: solid 1px blue;
   }
   
   #memTable th, #memTable td {
      font-size: 15pt;
      border: solid 1px gray;
      padding: 5px 0;
      
   }
   
   #memTable tr:hover {
      background-color: yellow;
      cursor: pointer;
   }
   
</style>

<script type="text/javascript">
   $(document).ready(function(){
      
      $("#searchType").bind("change",function(){
         if($(this).val() != ""){
            func_Ajax($(this).val());
         }
      });
      
      //
      $("#searchBtn").click(function(){
         
         var frm = document.emailsearchFrm;
         frm.method = "GET";
         frm.action = "<%=ctxPath%>/adminChart.yo";
         frm.submit();
         
      });
      
   }); // end of document.ready-------------
   
   
   // 차트 -------------------------------------------------
   function func_Ajax(searchTypeVal) {
      
      switch (searchTypeVal) {
      case "deptname":
      // 음식종류별 매장 수
         $.ajax({
            url:"<%=ctxPath%>/chartTest.yo",
            type:"GET",
            dataType:"JSON",
            success:function(json){
               $("#chart_container").empty();
               
               var resultArr = [];
               for(var i=0; i<json.length;i++ ){
                  var obj = {name: json[i].shopcategoryname 
                          ,y : Number(json[i].percnt) };
                  resultArr.push(obj); // 배열속에 객체넣기
               }
               
               /* ==== 차트 ===== */
               Highcharts.chart('chart_container', {
                   chart: {
                       plotBackgroundColor: null,
                       plotBorderWidth: null,
                       plotShadow: false,
                       type: 'pie'
                   },
                   title: {
                       text: '음식종류별 매장 수'
                   },
                   tooltip: {
                       pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                   },
                   plotOptions: {
                       pie: {
                           allowPointSelect: true,
                           cursor: 'pointer',
                           dataLabels: {
                               enabled: true,
                               format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                           }
                       }
                   },
                   series: [{
                       name: '인원비율',
                       colorByPoint: true,
                       data: resultArr
                   }]
               });
               ///////////////////////////////////////
               
            }, // end of success---
            error: function(request, status, error){
                  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
               }
         });
         break;
       
      case "categoryRankShop":
      // 음식별 매장 판매 랭킹
         $.getJSON("<%=ctxPath%>/categoryRankShop.yo",function(json){
            $("#chart_container").empty();
            
            var shoppaycateArr = [];
            
            $.each(json,function(index,item){
               shoppaycateArr.push( {
                                 "name": item.shopcategoryname,
                                 "y": Number(item.percnt),
                                 "drilldown": item.shopcategoryname
                               } );
            }); // end of .each----------------------
            
            var shopnamePayArr = []; // 가장 큰 배열
            
            $.each(json,function(index1,item1){
               $.getJSON("categoryRankShopEnd.yo?shopcategoryname="+item1.shopcategoryname, function(json2){
                  var subArr = []; // data : [] 뜻함..!
                  
                  $.each(json2,function(index2,item2){
                     
                     subArr.push([item2.shopname , Number(item2.percnt)]); // data:[] 속의 작은 배열들
                     
                  }); // end of .each(json2)-------------
                  
                  shopnamePayArr.push({"name" : item1.shopcategoryname, // 가장 큰 배열속 객체
                                       "id" : item1.shopcategoryname,
                                       "data" : subArr});
                  
               });
            }); // end of .each----------------------
            
            // ==== 막대 차트 ===== /////////////////////////////////////////////////////////////
            Highcharts.chart('chart_container', {
             chart: {
                 type: 'column'
             },
             title: {
                 text: '음식종류별 판매순위.'
             },
             subtitle: {
                 text: '음식종류 이름클릭시 음식별 매장 판매 순위가 나옵니다.'
             },
             xAxis: {
                 type: 'category'
             },
             yAxis: {
                 title: {
                     text: '판매비율(%)'
                 }
         
             },
             legend: {
                 enabled: false
             },
             plotOptions: {
                 series: {
                     borderWidth: 0,
                     dataLabels: {
                         enabled: true,
                         format: '{point.y:.2f}%' // 소수부 2째 자리까지 보이고 반올림 해라
                     }
                 }
             },
         
             tooltip: {
                 headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                 pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
             },
         
             series: [
                 {
                     name: "음식종류",
                     colorByPoint: true,
                     data: shoppaycateArr // *** 위에서 구한 값을 대입시킨다. //
                 }
             ],
             drilldown: {
                 series: shopnamePayArr // *** 위에서 구한값을 대입시킴 // 
             }
         });
            /////////////////////////////////////////////////////////////////////////////////////
            
         });      
         break; 
      default:
         break;
      }
      
   } // end of func_Ajax()-----------------
   
</script>

</head>
<body>
   <div>
      <div align="center" style="border: solid 0px gray; width: 50%; float: left;">
         <h2>관리자 통계</h2>
         
         <form name="searchFrm" style="margin-bottom: 100px;">
            <select name="searchType" id="searchType">
               <option value="" >통계선택.</option>
               <option value="deptname">업종별 매장수</option>
               <option value="categoryRankShop">음식별 매장 판매 랭킹</option>
            </select>
         </form>
         
         <%-- 원차트 --%>
         <div id="chart_container" style="min-width: 310px; min-height: 400px; max-width: 600px; margin: 0 auto"></div>
      </div>
      
      <div align="center" style="border: solid 0px red; width: 50%; float: right;">
         <h2>회원 관리</h2>
         <form name="emailsearchFrm">
            <label for="searchWord">회원검색 : &nbsp;</label><input id="searchWord" name="searchWord" type="text" placeholder="email 입력.." width="45px;" height="15px;" />
            <button type="button" id="searchBtn">검색</button>
         </form>
         <table id="memTable">
            <thead>
               <tr align="center" style="border-bottom: 2px solid black;">
                  <th align="center">이름</th>
                  <th align="center">이메일</th>
                  <th align="center">전화번호</th>
                  <th align="center">회원상태</th>
               </tr>
            </thead>
            <tbody id="memTbody">
               <c:forEach var="member" items="${MemberList}" varStatus="status" >
                  <tr>
                     <td>${member.name }</td>
                     <td>${member.email }</td>
                     <td>${member.tel }</td>
                     <td align="center">
                        <c:if test="${member.status == 1}">
                           정상회원
                        </c:if>
                        <c:if test="${member.status == 0}">
                           탈퇴회원
                        </c:if>
                     </td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
         <div id="pageBar" align="center" style="margin-top: 20px;">
             ${pageBar }
          </div>
      </div>
   </div>
</body>
</html>