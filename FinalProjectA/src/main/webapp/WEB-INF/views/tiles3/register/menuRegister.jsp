<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">

</style>

<script type="text/javascript">

</script>

<div>
	<form name="menuRegFrm">
		<table>
			<tr>
				<%-- 현제 로그인되어진 사용자에 한에서 세션에 정보저장후 불러오기 --%>
				<td>사업자번호</td>
				<td>
					<input type="text" name="menuCode" id="menuCode"/>
				</td>
			</tr>
			<tr>
				<td>메뉴카테고리</td>
				<td>
					<select name="menuSpecCode" class="menuSpecCode">
						<option value="">선택하세요</option>
						<option value="">test-1</option>
						<option value="">test-2</option>
						<option value="">test-3</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>메뉴명</td>
				<td>
					<input type="text" name="menuName" id="menuName"/>
				</td>
			</tr>
			<tr>
				<td>메뉴가격</td>
				<td>
					<input type="text" name="menuPrice" id="menuPrice"/>
				</td>
			</tr>
			<tr>
				<td>메뉴설명</td>
				<td>
					<textarea name="menuComment" id="menuComment" rows="5" cols="60" style="resize: none;"></textarea>
				</td>
			</tr>
			<%-- 이미지 첨부파일 --%>
			<tr>
				<td>메뉴사진</td>
				<td>
					<input type="file" name="menuImage" id="menuImage" size="40"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="메뉴등록" id="menuRegister" />
					<input type="reset" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</div>