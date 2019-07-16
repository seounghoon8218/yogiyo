<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
	input[type=text], input[type=password] , input[type=email]{
		width: 100%;
		height: 50px;
		font-size: 13pt;
	}
	input[type=checkbox]{
		height: 15px;
	}
	a{
		text-decoration: none;
		
	}
</style>
    
<div style=" width: 80%; border: 0px solid; margin: 0 auto;">
	<div align="center" style="font-weight: bold; margin-bottom: 35px;">
		<h2><span>회원가입해주셔서 감사합니다!</span></h2>
	</div>
	<form name="registerFrm">
		<table style="width: 100%">
			<tr><th><span>회원정보 입력</span></th></tr>
			<tr>
				<td><input type="email" placeholder="(필수)이메일 주소 입력" id="email" /></td>
			</tr>
			<tr>
				<td><input type="password" placeholder="(필수)비밀번호 입력" id="pwd" /></td>
			</tr>
			<tr>
				<td><input type="password" placeholder="(필수)비밀번호 재확인" id="pwd2" /></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)이름 입력" id="phone"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="(필수)휴대폰번호 입력" id="phone"></td>
			</tr>
			<tr><td>&nbsp;<td></tr>
			<tr><th><span>약관동의 </span></th></tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check" />
					<label for="check">전체동의</label>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check2" style="margin-left: 10px;" />
					<label for="check2">이용약관동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check3" style="margin-left: 10px;"/>
					<label for="check3">개인정보 수집 및 이용동의(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr style="background-color: white;">
				<td>
					<input type="checkbox" value="" id="check4" style="margin-left: 10px;"/>
					<label for="check4">만 14세 이상 이용자(필수)</label>
					<span style="opacity: 0.8; text-align: "><a href="">내용보기 ></a></span>
				</td>
			</tr>
			<tr><td>&nbsp;<td></tr>
			<tr>
				<td>
					<button type="button" style="width: 100%; color: white; background-color: red;"><span style="font-weight: bold; font-size: 15pt;">회원가입 완료</span></button>
				</td>
			</tr>
		</table>
	</form>
</div>