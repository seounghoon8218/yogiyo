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
	img{width: 218px; height: 218px;}
	
</style>	
	<div class="category-list" ng-hide="$location.path() != &quot;/&quot;">
		<div class="row">
			<!-- ngRepeat: banner in banner_list -->
			<div class="col-xs-6 col-sm-4 col-md-3 category-banner ng-scope" ng-repeat="banner in banner_list" style="" on-finish-render="completeBannerListRender()">
			<a href="" class="thumbnail" ng-click="setEvent($index, banner)" ng-style="{'background': banner.background_color}" style="background: rgb(251, 226, 206);">
				<img ng-src="https://d5bfh7nnlp98y.cloudfront.net/banner/201906_srw_promotion/YGY_SRW05_mweb_category_%23fbe2ce%20%282%29.png" alt="201907_srw" src="https://d5bfh7nnlp98y.cloudfront.net/banner/201906_srw_promotion/YGY_SRW05_mweb_category_%23fbe2ce%20%282%29.png">
				<i class="icon-move"></i>
			</a>			
			</div>
			
			<!-- end ngRepeat: banner in banner_list -->
			<div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
				<a href="" class="thumbnail" ng-click="select_home_category('all')">
					<div class="category-title">전체보기</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3 col-lg-3">
				<a href="" class="thumbnail" ng-click="select_home_category('1인분주문')">
					<div class="category-title">1인분 주문</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('프랜차이즈')">
					<div class="category-title">프랜차이즈</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('치킨')">
					<div class="category-title">치킨</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('피자양식')">
					<div class="category-title">피자/양식</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('중식')">
					<div class="category-title">중국집</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('한식')">
					<div class="category-title">한식</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>
			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('일식돈까스')">
					<div class="category-title">일식/돈까스</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('족발보쌈')">
					<div class="category-title">족발/보쌈</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('야식')">
					<div class="category-title">야식</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('분식')">
					<div class="category-title">분식</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('카페디저트')">
					<div class="category-title">카페/디저트</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
			
			<div class="col-xs-6 col-sm-4 col-md-3">
				<a href="" class="thumbnail" ng-click="select_home_category('편의점')">
					<div class="category-title">편의점</div>
					<img src="<%=ctxPath %>/resources/images/로고예시.jpg">
				</a>			
			</div>
		</div>
	</div>
	
