package com.spring.yogiyo.wwwmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.common.SHA256;
import com.spring.member.model.MemberVO;

//=== #32. DAO 선언 ===
@Repository
public class WwwDAO implements InterWwwDAO {

public SHA256 as = null;

   //=== #33. 의존객체 주입하기(DI: Dependency Injection) ===
   @Autowired
   private SqlSessionTemplate sqlsession;

   // === #46. 로그인 처리하기 ===
   @Override
   public MemberVO getLoginMember(HashMap<String, String> paraMap) {
      MemberVO loginuser = sqlsession.selectOne("www.getLoginMember", paraMap); // loginuser 서비스로감
      return loginuser;
   }
   @Override
   public void setLastLoginDate(HashMap<String, String> paraMap) {
      sqlsession.update("www.setLastLoginDate", paraMap);
   }

   // 회원가입
   @Override
   public void RegisterMember(MemberVO membervo) {
         membervo.setPwd(as.encrypt(membervo.getPwd()));
      sqlsession.insert("www.RegisterMember", membervo);
   }

   // 이메일 중복검사
   @Override
   public int selectUserID(String email) {
      int n = sqlsession.selectOne("www.selectUserID", email);
      return n;
   }

   // 내정보수정
   @Override
   public int editMember(MemberVO membervo) {
      membervo.setPwd(as.encrypt(membervo.getPwd()));
      int n = sqlsession.update("www.editMember", membervo);
       return n;
   }
   
   // 회원탈퇴
   @Override
	public void memberDelEnd(MemberVO membervo) {
	   membervo.setPwd(as.encrypt(membervo.getPwd()));
	   sqlsession.delete("www.memberDelEnd", membervo);
		
	}
   
   // 찾기
   @Override
   public int pwdSearch(HashMap<String, String> map) {
     int pwdOK = sqlsession.selectOne("www.pwdSearch", map); 
      return pwdOK;
   }
   // 새 비밀번호 수정
   @Override
	public int newPwdEnd(MemberVO membervo) {
	   membervo.setPwd(as.encrypt(membervo.getPwd()));
	   int n = sqlsession.update("www.newPwdEnd", membervo);
	   return n;
	}
   
   // === #111. 검색조건이 없을 경우의 총게시물건수(totalCount)===
	@Override
	public int getTotalCountWithNOSearch() {
		int count = sqlsession.selectOne("www.getTotalCountWithNOSearch");
		return count;
	}

	// === #114. 검색조건이 있을 경우의 총게시물건수(totalCount)===
	@Override
	public int getTotalCountWithSearch(HashMap<String,String> paraMap) {
		int count = sqlsession.selectOne("www.getTotalCountWithSearch", paraMap);
		return count;
	}

	// === #117. 페이징처리한 글목록보기(검색이 있던지, 검색이 없던지 다 포함한 것)===
	@Override
	public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("www.boardListWithPaging", paraMap);
		return boardList;
	}
	
	// === #55.글쓰기(파일첨부가 없는 글쓰기) ===
	@Override
	public int add(BoardVO boardvo) {
		int n = sqlsession.insert("www.add", boardvo);
		return n;
	}
	
	// === #127. tblBoard 테이블에서 grouppno 컬럼의 최대값 구하기 ===
	@Override
	public int getGroupnoMax() {
		int max = sqlsession.selectOne("www.getGroupnoMax");
		return max;
	}
	
	// === #140. 글쓰기(파일첨부가 있는 글쓰기) === 
	@Override
	public int add_withFile(BoardVO boardvo) {
		int n = sqlsession.insert("www.add_withFile", boardvo);
		return n;
	}

	// === #63. 1개 글 보여주기 === // 
	@Override
	public BoardVO getView(String seq) {
		BoardVO boardvo = sqlsession.selectOne("www.getView", seq);
		return boardvo;
	}

	// === #64. 글조회수 1증가하기 === //
	@Override
	public void setAddReadCount(String seq) {
		sqlsession.update("www.setAddReadCount",seq);
		
	}

	// === #73. 글수정 및 글삭제시 암호일치 여부 알아오기 ===
	@Override
	public boolean checkPW(BoardVO boardvo) {
		int n = sqlsession.selectOne("www.checkPW", boardvo);
		
		if(n==1)
			return true;
		else
			return false;
	}

	// === #75. 글 1개를 수정한다. ===
	@Override
	public int updateBoard(BoardVO boardvo) {
		int n = sqlsession.update("www.updateBoard", boardvo);
		return n;
	}

	// === #80. 글 1개를 삭제한다. ===
	@Override
	public int deleteBoard(BoardVO boardvo) {
		int n = sqlsession.update("www.deleteBoard", boardvo);
		return n;
	}

	// === #87. 댓글쓰기(tblComment 테이블에 insert) ===
	@Override
	public int addComment(CommentVO commentvo) {
		int n = sqlsession.insert("www.addComment", commentvo);
		return n;
	}
	// === tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) ===
	@Override
	public int updateCommentCount(String parentSeq) {
		int n = sqlsession.update("www.updateCommentCount", parentSeq);
		return n;
	}

	// === #88. 원게시물에 딸린 댓글보여주기 ===
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = sqlsession.selectList("www.getCommentList", parentSeq);
		return commentList;
	}

	// === #94. 원게시물에 딸린 모든 댓글들을 삭제하기 === 
	@Override
	public int deleteComment(String seq) {
		int n = sqlsession.delete("www.deleteComment", seq);
		return n;
	}

	// === #99. 페이징처리를 안한 검색어가 있는 전체 글목록 보여주기 ===
	@Override
	public List<BoardVO> boardListSearch(HashMap<String, String> paraMap) {
		List<BoardVO> boardList = sqlsession.selectList("www.boardListSearch", paraMap);
		return boardList;
	}

	// === #104. 검색어 입력시 자동글 완성하기 4 ===
	@Override
	public List<String> wordSearchShow(HashMap<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("www.wordSearchShow", paraMap);
		return wordList;
	}
	

}