package com.spring.yogiyo.sssmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SssDAO implements InterSssDAO {

	@Autowired
	private SqlSessionTemplate sqlsession;
	
	// 메뉴등록
	@Override
	public int menuRegister(MenuVO menuvo) {
		int n = sqlsession.insert("sss.menuRegister",menuvo);
		return n;
	}

	// 검색조건이 없는 경우의 totalCount불러오기
	@Override
	public int getTotalCountWithOutSearch() {
		int count = sqlsession.selectOne("sss.getTotalCountWithOutSearch");
		return count;
	}

	// 검색조건이 있는 경우의 totalCount불러오기
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paramap) {
		int count = sqlsession.selectOne("sss.getTotalCountWithSearch",paramap);
		return count;
	}

	// 가입인사 등록
	@Override
	public int addGreeting(GreetingBoardVO gbvo) {
		int addGreeting = sqlsession.insert("sss.addGreeting", gbvo);
		return addGreeting;
	}
	
	// 게시물 리스트 불러오기
	@Override
	public List<GreetingBoardVO> greetingList(HashMap<String, String> paramap) {
		List<GreetingBoardVO> greetingList = sqlsession.selectList("sss.greetingList",paramap);
		return greetingList;
	}

	// 글 1개 보여주기 및 readCount증가하기
	@Override
	public GreetingBoardVO getGreetingView(String seq) {
		GreetingBoardVO gbvo = sqlsession.selectOne("sss.getGreetingView",seq);
		return gbvo;
	}
	@Override
	public void addGreetingReadCount(String seq) {
		sqlsession.update("sss.addGreetingReadCount",seq);
		
	}

	// 글 1개 보여주기 
	@Override
	public GreetingBoardVO getGreetingViewNoAddCount(String seq) {
		GreetingBoardVO gbvo = sqlsession.selectOne("sss.getGreetingViewNoAddCount", seq);	
		return gbvo;
	}

	// 댓글 불러오기
	@Override
	public List<GreetingCommentVO> getGreetingCommentList(String seq) {
		List<GreetingCommentVO> greetingCommentList = sqlsession.selectList("sss.getGreetingCommentList",seq);
		return greetingCommentList;
	}

	// 댓글쓰기
	@Override
	public int addGreetingComment(GreetingCommentVO gcommentvo) {
		int n = sqlsession.insert("sss.addGreetingComment", gcommentvo);
		return n;
	}

	// 글 수정하기
	@Override
	public int editGreeting(GreetingBoardVO gbvo) {
		int n = sqlsession.update("sss.editGreeting", gbvo);
		return n;
	}

	// 비밀번호 체크
	@Override
	public boolean checkPW(GreetingBoardVO gbvo) {
		int n = sqlsession.selectOne("sss.checkPW", gbvo);
		if (n == 1) {
			return true;
		} else {
			return false;
		}
		
	}

	// 글 삭제하기
	@Override
	public int destroyGreeting(GreetingBoardVO gbvo) {
		int n = sqlsession.update("sss.destroyGreeting", gbvo);
		return n;
	}

	// 댓글이 있는 글이라면 댓글도 같이 삭제해주어야한다.
	@Override
	public int destroyGreetingComment(String seq) {
		int n = sqlsession.delete("sss.destroyGreetingComment", seq);
		return n;
	}

	// groupno의 max값 불러오
	@Override
	public int getGroupnoMax() {
		int n = sqlsession.selectOne("sss.getGroupnoMax");
		return n;
	}



	
	

}
