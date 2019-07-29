<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%>
<style>
	
	.category-list .row {
		  padding: 5px;
		  max-width: 1020px;
		  margin: 0 auto;
		}
	.category-list ul {
	  list-style: none;
	  margin: 0;
	  padding: 0;
	}
	.category-list .col-xs-6,
	.category-list .col-sm-4,
	.category-list .col-md-3,
	.category-list .col-lg-3 {
	  padding-left: 5px;
	  padding-right: 5px;
	}
	.category-list {
	  background-color: #fafafa;
	  padding-bottom: 15px;
	}
	.thumbnail {
	  display: block;
	  padding: 4px;
	  margin-bottom: 20px;
	  line-height: 1.42857143;
	  background-color: #fff;
	  border: 1px solid #ddd;
	  border-radius: 4px;
	  -webkit-transition: border .2s ease-in-out;
	       -o-transition: border .2s ease-in-out;
	          transition: border .2s ease-in-out;
	}
	.thumbnail > img,
	.thumbnail a > img {
	  margin-right: auto;
	  margin-left: auto;
	}
	a.thumbnail:hover,
	a.thumbnail:focus,
	a.thumbnail.active {
	  border-color: #337ab7;
	}
	.thumbnail .caption {
	  padding: 9px;
	  color: #333;
	}
	.thumbnail {
	  position: relative;
	  border: none;
	  padding: 0;
	  margin: 5px 0;
	  border: 1px solid #d9d9d9 !important;
	  border-radius: 0px;
	  overflow: hidden;
	}
	.thumbnail img {
	  float: right;
	}
	
	.img-responsive,
	.thumbnail > img,
	.thumbnail a > img,
	.carousel-inner > .item > img,
	.carousel-inner > .item > a > img {
	  display: block;
	  max-width: 100%;
	  height: auto;
	}
	.category-title {
    	position: absolute;
	    top: 7%;
	    left: 7%;
	    color: #333;
	    font-size: 110%;
	    font-weight: bold;
	}
</style>	
	
	<div class="category-list" ng-hide="$location.path() != &quot;/&quot;">
		<div class="row">
			<!-- ngRepeat: banner in banner_list -->
			
			<!-- end ngRepeat: banner in banner_list -->
			<div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=0" class="thumbnail" ng-click="select_home_category('all')">
					<div class="category-title">전체보기</div>
					<img src="<%=ctxPath %>/resources/images/category-01.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=1" class="thumbnail" ng-click="select_home_category('치킨')">
					<div class="category-title">치킨</div>
					<img src="<%=ctxPath %>/resources/images/category-02.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=2" class="thumbnail" ng-click="select_home_category('피자양식')">
					<div class="category-title">피자/양식</div>
					<img src="<%=ctxPath %>/resources/images/category-03.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=3" class="thumbnail" ng-click="select_home_category('중식')">
					<div class="category-title">중국집</div>
					<img src="<%=ctxPath %>/resources/images/category-04.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=4" class="thumbnail" ng-click="select_home_category('한식')">
					<div class="category-title">한식</div>
					<img src="<%=ctxPath %>/resources/images/category-05.png">
				</a>
			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=5" class="thumbnail" ng-click="select_home_category('일식돈까스')">
					<div class="category-title">일식/돈까스</div>
					<img src="<%=ctxPath %>/resources/images/category-06.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=6" class="thumbnail" ng-click="select_home_category('족발보쌈')">
					<div class="category-title">족발/보쌈</div>
					<img src="<%=ctxPath %>/resources/images/category-07.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=7" class="thumbnail" ng-click="select_home_category('분식')">
					<div class="category-title">분식</div>
					<img src="<%=ctxPath %>/resources/images/category-09.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=8" class="thumbnail" ng-click="select_home_category('카페디저트')">
					<div class="category-title">카페/디저트</div>
					<img src="<%=ctxPath %>/resources/images/category-11.png">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="<%=ctxPath %>/categryList.yo?shopcategorycode=9" class="thumbnail" ng-click="select_home_category('편의점')">
					<div class="category-title">편의점</div>
					<img src="<%=ctxPath %>/resources/images/category-convenience-store.png">
				</a>			
			</div>
		</div>
	</div>
	
