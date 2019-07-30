<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style='width: 50%; border: 1px soild gray; background-color: #fff;'>
		<div style="padding: 10px; font-size: 110%; border-bottom: 1px solid gray;" >
	        <span>${shop.shopname}</span>
	    </div>
			<table class='restaurant-tableView'>
				<tr>
					<td class='logoView'>
						<div>
							<img class='restaurant-img'
								src='<%=request.getContextPath()%>/resources/images/category-01.png' />
						</div>
					</td>
					<td>
						<div class='restaurants-info'>							
							<div class='stars'>
								<span class='ico-star1'>★ 4.0</span> <span class='review'>리뷰29</span>
								<span class='review_num'>사장님댓글 2</span><br /> 
								<span class='yogiseo-payment'>요기서결제</span>
								<span class='min-price'>${shop.minprice} 원 이상 배달</span><br />
								<span class='delivery-time'>40~50분 </span>
							</div>
						</div>
					</td>
				</tr>
			</table>
	</div>
</body>
</html>