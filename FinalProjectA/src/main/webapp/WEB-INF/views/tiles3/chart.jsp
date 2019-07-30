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
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		$("#searchType").bind("change",function(){
			if($(this).val() != ""){
				func_Ajax($(this).val());
			}
		});
	
	}); // end of document.ready-------------
	
	function func_Ajax(searchTypeVal) {
		
		switch (searchTypeVal) {
		case "deptname":
		// 부서별 인원통계
			$.ajax({
				url:"test13deptnameJSON.action",
				type:"GET",
				dataType:"JSON",
				success:function(json){
					$("#chart_container").empty();
					
					var resultArr = [];
					for(var i=0; i<json.length;i++ ){
						var obj = {name: json[i].DEPARTMENT_NAME 
								  ,y : Number(json[i].PERCNT) };
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
					        text: '우리회사 부서별 인원통계'
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
		/* case "gender":
		// 성별 인원통계
			$.ajax({
				url:"test13genderJSON.action",
				type:"GET",
				dataType:"JSON",
				success:function(json){
					$("#chart_container").empty();
					
					var resultArr = [];
					for(var i=0; i<json.length;i++ ){
						var obj = {name: json[i].GENDER 
								  ,y : Number(json[i].PERCNT) };
						resultArr.push(obj); // 배열속에 객체넣기
					}
					
					// ==== 차트 ===== 
					Highcharts.chart('chart_container', {
					    chart: {
					        plotBackgroundColor: null,
					        plotBorderWidth: null,
					        plotShadow: false,
					        type: 'pie'
					    },
					    title: {
					        text: '우리회사 성별 인원통계'
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
					
				}, // end of success------
				error: function(request, status, error){
		            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		         }
			});
			break;
		case "deptnameGender":
		// 부서별 성별 인원통계
			$.getJSON("test13deptnameJSON.action",function(json){
				$("#chart_container").empty();
				
				var deptnameCntArr = [];
				
				$.each(json,function(index,item){
					deptnameCntArr.push( {
											"name": item.DEPARTMENT_NAME,
											"y": Number(item.PERCNT),
											"drilldown": item.DEPARTMENT_NAME
										 } );
				}); // end of .each----------------------
				
				var deptnameGenderCntArr = []; // 가장 큰 배열
				
				$.each(json,function(index1,item1){
					$.getJSON("test13deptnameGenderJSON.action?department_name="+item1.DEPARTMENT_NAME, function(json2){
						var subArr = []; // data : [] 뜻함..!
						
						$.each(json2,function(index2,item2){
							
							subArr.push([item2.GENDER , Number(item2.PERCNT)]); // data:[] 속의 작은 배열들
							
						}); // end of .each(json2)-------------
						
						deptnameGenderCntArr.push({"name" : item1.DEPARTMENT_NAME, // 가장 큰 배열속 객체
													"id" : item1.DEPARTMENT_NAME,
													"data" : subArr});
						
					});
				}); // end of .each----------------------
				
				// ==== 막대 차트 ===== /////////////////////////////////////////////////////////////
				Highcharts.chart('chart_container', {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: '우리회사 부서별 남여 인원 통계'
			    },
			    subtitle: {
			        text: '부서이름을 클릭하시면 해당 부서의 남여별 통계가 보입니다.'
			    },
			    xAxis: {
			        type: 'category'
			    },
			    yAxis: {
			        title: {
			            text: '인원비율(%)'
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
			            name: "부서명",
			            colorByPoint: true,
			            data: deptnameCntArr // *** 위에서 구한 값을 대입시킨다. //
			        }
			    ],
			    drilldown: {
			        series: deptnameGenderCntArr // *** 위에서 구한값을 대입시킴 // 
			    }
			});
				/////////////////////////////////////////////////////////////////////////////////////
				
			});		
			break; 
			*/

		default:
			break;
		}
		
	} // end of func_Ajax()-----------------
	
</script>

</head>
<body>
	<div align="center">
		<h2>우리회사 사원 통계정보( 차 트 )</h2>
		
		<form name="searchFrm" style="margin-bottom: 100px;">
			<select name="searchType" id="searchType">
				<option value="" >통계선택.</option>
				<option value="deptname">부서별 인원통계</option>
				<option value="gender">성별 인원통계</option>
				<option value="deptnameGender">부서별 성별 인원통계</option>
			</select>
		</form>
		
		<%-- 원차트 --%>
		<div id="chart_container" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
		<%-- 막대차트 --%>
	</div>
</body>
</html>