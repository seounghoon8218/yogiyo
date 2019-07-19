package com.spring.yogiyo.wwwservice;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.common.AES256;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.wwwmodel.InterWwwDAO;

//=== #31. Service 선언 ===
@Service
public class WwwService implements InterWwwService {
	
	//=== #34. 의존객체 주입하기(DI: Dependency Injection) ===
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private InterWwwDAO dao;
	
	//=== #45. 양방향 암호화 알고리즘인 AES256을 사용하여 암호화/복호화 하기 ===
	@Autowired 
	AES256 aes;

	// === #37. 메인 페이지용 이미지 파일을 가져오기===
	@Override
	public List<String> getImgfilenameList() {
		List<String> imgfilenameList = dao.getImgfilenameList();
		return imgfilenameList;
	}

	// === #42. 로그인 처리하기 ===
	@Override
	public MemberVO getLoginMember(HashMap<String, String> paraMap) {
		MemberVO loginuser = dao.getLoginMember(paraMap); // 셀렉트(멤버vo를 불러오고있음)
		
		// === #48. aes 의존객체를 사용하여 로그인 되어진 사용자(loginuser)의 이메일 값을 복호화 하도록 한다. ===
		if(loginuser != null) {
			
			if( loginuser.getLastlogindategap() >= 12) {
				// 마지막으로 로그인한 날짜가 1년지났으면 휴면처리
				
				loginuser.setIdleStatus(true);
				
				/////////////아래는 로그인 한지 1년이 지났지만 정상적으로 로그인처리를 해주는 것/////////////////////////////////////////////////
				//// 정상적으로 로그인 처리를 허락치 않으려면 아래를 주석처리하면 된다.
				dao.setLastLoginDate(paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기 (DML이 하나라 트랜잭션처리(@Transactional)를 안해도됨)
				
				try {
					loginuser.setEmail(aes.decrypt(loginuser.getEmail()) );
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					e.printStackTrace();
				}
				///////////////////////////////////////////////////////////////
			}
			else {
				
				if(loginuser.getPwdchangegap() >= 4) {
					// 마지막으로 암호를 변경한 날짜가 현재시각으로부터 3개월이 지났으면 true로 변경한다.
					loginuser.setRequirePwdChange(true);
				}
				
				dao.setLastLoginDate(paraMap); // 마지막으로 로그인 한 날짜시간 변경(기록)하기 (DML이 하나라 트랜잭션처리(@Transactional)를 안해도됨)
				
				/*try {
					loginuser.setEmail(aes.decrypt(loginuser.getEmail()) );
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					e.printStackTrace();
				}*/
			}// end of else---------
		}// end of if(loginuser != null)--------
		
		return loginuser;
	}
}
