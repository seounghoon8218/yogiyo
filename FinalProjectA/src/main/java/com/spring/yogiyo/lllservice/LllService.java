package com.spring.yogiyo.lllservice;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.common.AES256;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.lllmodel.InterLllDAO;

public class LllService implements InterLllService {
	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
		@Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
		private InterLllDAO dao;
		
		//===== #45. 양방향 암호화 알고리즘인 AES256 를 사용하여 암호화/복호화 하기위한 클래스 의존객체 주입하기(DI: Dependency Injection) =====
		@Autowired
		private AES256 aes;
	

	//=== #42. 로그인 처리하기 ===
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		
		MemberVO loginuser = dao.getLoginMember(paraMap);
		
		// === #48. aes 의존객체를 사용하여 로그인 되어진 사용자(loginuser)의 이메일 값을 복호화 하도록 한다. ===
		if(loginuser != null) {
			
			if(loginuser.getLastlogindategap() >= 12) {
				// 마지막으로 로그인 한 날짜가 현재일로 부터 1년(12개월)이 지났으면 해당 로그인 계정을 비활성화(휴면)시킨다. 
				
				loginuser.setIdleStatus(true);
			
				/// 아래는 로그인 한지 1년이 지났지만 정상적으로 로그인 처리를 해주는 것  ///
				/// 정상적으로 로그인 처리를 허락치 않으려면 아래부분은 삭제해야 한다. 
				dao.setLastLoginDate(paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기
				
				try {
					loginuser.setEmail(aes.decrypt(loginuser.getEmail()) );
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					e.printStackTrace();
				}  
				///////////////////////////////////////////////////////////
			}
			else {
				
				if(loginuser.getPwdchangegap() > 3) {
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true 로 변경한다.
					loginuser.setRequirePwdChange(true);
				}
				
				dao.setLastLoginDate(paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기
				
				try {
					loginuser.setEmail(aes.decrypt(loginuser.getEmail()) );
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					e.printStackTrace();
				}  
			}
			
		}
		
		return loginuser;
	}
}
