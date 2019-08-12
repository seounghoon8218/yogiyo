package com.spring.yogiyo.sssservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.yogiyo.sssmodel.GreetingBoardVO;
import com.spring.yogiyo.sssmodel.GreetingCommentVO;
import com.spring.yogiyo.sssmodel.InterSssDAO;
import com.spring.yogiyo.sssmodel.MenuVO;

@Service
public class SssService implements InterSssService {

	// 의존객체
	@Autowired
	private InterSssDAO dao;
	
	// 메뉴등록
	@Override
	public int menuRegister(MenuVO menuvo) {
		int n = dao.menuRegister(menuvo); 
		return n;
	}

	// 검색조건이 없는 경우의 totalCount불러오기
	@Override
	public int getTotalCountWithOutSearch() {
		int count = dao.getTotalCountWithOutSearch();
		return count;
	}

	// 검색조건이 있는 경우의 totalCount불러오기
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paramap) {
		int count = dao.getTotalCountWithSearch(paramap);
		return count;
	}
	
	// 가입인사 등록
	@Override
	public int addGreeting(GreetingBoardVO gbvo) {
		int addGreeting = dao.addGreeting(gbvo);
		return addGreeting;
	}

	// 게시물 리스트 불러오기
	@Override
	public List<GreetingBoardVO> greetingList(HashMap<String, String> paramap) {
		List<GreetingBoardVO> greetingList = dao.greetingList(paramap);
		return greetingList;
	}

	// 글 1개 보여주기 + readCount증가
	@Override
	public GreetingBoardVO getGreetingView(String seq, String useremail) {
		
		// 글 1개를 보여줌
		GreetingBoardVO gbvo = dao.getGreetingView(seq);
		
		if (useremail != null && !gbvo.getFk_email().equals(useremail)) {
			
			// 로그인이 되어있고 작성한 사람과 로그인한 유저가 다르다면
			// 글 1개를 보여주고 readCount도 올려주어야한다.
			dao.addGreetingReadCount(seq);
			gbvo = dao.getGreetingView(seq);
		}
		
		return gbvo;
	}

	// 글 1개 보여주기 
	@Override
	public GreetingBoardVO getGreetingViewNoAddCount(String seq) {
		GreetingBoardVO gbvo = dao.getGreetingViewNoAddCount(seq);
		return gbvo;
	}

	// 댓글 불러오기
	@Override
	public List<GreetingCommentVO> getGreetingCommentList(String seq) {
		List<GreetingCommentVO> greetingCommentList = dao.getGreetingCommentList(seq);
		return greetingCommentList;
	}

	// 댓글쓰기
	@Override
	public int addGreetingComment(GreetingCommentVO gcommentvo) {
		
		// 기본적으로 groupno 는 null 인 상태 만약 groupno의 최대값을 알아오고 만약 null 이라면 0값을 주고
		// 0 이 아니라면 getGroupnoMax(0)값에 +1 을 해준다.
		if (gcommentvo.getFk_seq() == null || gcommentvo.getFk_seq().trim().isEmpty()) {
			int groupno = dao.getGroupnoMax()+1;
			gcommentvo.setGroupno(String.valueOf(groupno));
		}
		int n = dao.addGreetingComment(gcommentvo);
		return n;
	}

	// 글 수정하기
	@Override
	public int editGreeting(GreetingBoardVO gbvo) {
		
		int n = 0;
		// 비밀번호체크
		boolean bool = dao.checkPW(gbvo);
		
		if (!bool) {
			return 0;
		} else {
			n = dao.editGreeting(gbvo);
		}
		
		return n;
	}

	// 글 삭제하기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int destroyGreeting(GreetingBoardVO gbvo) {
		
		boolean bool = dao.checkPW(gbvo);
		
		if (!bool) {
			return 0;
		} else {
			
			int m = 0;
			
			// 삭제하기전 댓글수가 몇개인지 받아와야한다.
			String strCommentCount = dao.getGreetingView(gbvo.getSeq()).getCommentCount();
			int commentCount = Integer.parseInt(strCommentCount);
		
			if (commentCount > 0) {
				// 댓글이 있는 글이라면 댓글도 같이 삭제해주어야한다.
				m = dao.destroyGreetingComment(gbvo.getSeq());
			}
			
			// 암호가 일치하면 글 1개 삭제
			int n = dao.destroyGreeting(gbvo);
			
			int result = (m==0)? n: n*m;
			
			return result;
		}
		
	}



	
	
	
	
	

}
