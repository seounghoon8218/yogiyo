package com.spring.yogiyo.wwwservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.common.AES256;
import com.spring.common.SHA256;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.wwwmodel.BoardVO;
import com.spring.yogiyo.wwwmodel.CommentVO;
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
            /*   
            try {
               loginuser.setEmail(aes.decrypt(loginuser.getEmail()) );
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
               e.printStackTrace();
            }*/
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

   // 회원가입
   @Override
   public void RegisterMember(MemberVO membervo) {
      dao.RegisterMember(membervo);
      
   }

   // 중복검사
   @Override
   public int selectUserID(String email) {
      int n = dao.selectUserID(email);
      return n;
   }
   
   // 회원정보 수정
   @Override
   public int editMember(MemberVO membervo) {
      int n = dao.editMember(membervo);
      return n;
   }
   
   // 회원탈퇴
   @Override
	public void memberDelEnd(MemberVO membervo) {
		dao.memberDelEnd(membervo);
	}
		
   
   // 비밀번호 찾기
   @Override
   public int pwdSearch(HashMap<String, String> map) {
      int pwdOK = dao.pwdSearch(map);
      return pwdOK;
   }
   // 새 비밀번호 수정
   @Override
	public int newPwdEnd(MemberVO membervo) {
		int n = dao.newPwdEnd(membervo);
		return n;
	}


   // === #110. 검색조건이 없을 경우의 총게시물건수(totalCount) ===
	@Override
	public int getTotalCountWithNOSearch() {
		int count = dao.getTotalCountWithNOSearch();
		return count;
	}

	// === #113. 검색조건이 있을 경우의 총게시물건수(totalCount) ===
	@Override
	public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
		int count = dao.getTotalCountWithSearch(paraMap);
		return count;
	}

	// === #116. 페이징처리한 글목록보기(검색이 있던지, 검색이 없던지 다 포함한 것) ===
	@Override
	public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
		List<BoardVO> boardList = dao.boardListWithPaging(paraMap);
		return boardList;
	}
   
	// === #54. 글쓰기(파일첨부가 없는 글쓰기) ===
		@Override
		public int add(BoardVO boardvo) {
			
			// === #126. 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지 구분하여
			// 			 tblBoard 테이블에 insert 를 해주어야 한다.
			//			 원글쓰기 라면 tblBoard 테이블의 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 한다.
			//			 답변글쓰기 라면 넘겨받은 값을 그대로 insert 하여야 한다.
			
			// == 원글쓰기인지, 답변글쓰기인지 구분하기 ==
			if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty() ) {
				// 원글쓰기인 경우
				int groupno = dao.getGroupnoMax()+1;
				boardvo.setGroupno(String.valueOf(groupno));
			}
			/////////////////////////////////////////////////////
			int n = dao.add(boardvo);
			return n;
		}
	
	// === #139. 글쓰기(파일첨부가 있는 글쓰기) ===
	@Override
	public int add_withFile(BoardVO boardvo) {
		// ===  글쓰기가 원글쓰기인지 아니면 답변글쓰기인지 구분하여
		// 			 tblBoard 테이블에 insert 를 해주어야 한다.
		//			 원글쓰기 라면 tblBoard 테이블의 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 한다.
		//			 답변글쓰기 라면 넘겨받은 값을 그대로 insert 하여야 한다.
		
		// == 원글쓰기인지, 답변글쓰기인지 구분하기 ==
		if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty() ) {
			// 원글쓰기인 경우
			int groupno = dao.getGroupnoMax()+1;
			boardvo.setGroupno(String.valueOf(groupno));
		}
		/////////////////////////////////////////////////////
		int n = dao.add_withFile(boardvo); // 첨부파일이 있는경우
		return n;
	}
	
	// === #62. 1개 글 보여주기.(먼저, 로그인을 한 상태에서 다른 사람의 글을 조회할 경우에는 글조회수 컬럼 1증가해야 한다.)
	@Override
	public BoardVO getView(String seq, String email) {
								// userid 는 로그인한 사용자의 userid 이다.
								// 만약에 로그인을 하지 않은 상태이라면 userid 는 null 이다.
		BoardVO boardvo = dao.getView(seq);
		
		if(email != null && !boardvo.getFk_email().equals(email)) {
			// 글조회수 증가는 다른 사람의 글을 읽을 때만 증가하도록 해야 한다.
			// 로그인 하지 않은 상태에서는 글조회수 증가는 없다.
			
			dao.setAddReadCount(seq); // 글조회수 증가하기
			boardvo = dao.getView(seq);
		}
		
		return boardvo;
	}

	// === #69. 글조회수 증가는 없고 글조회만 해주는 것
	@Override
	public BoardVO getViewWithNoAddCount(String seq) {
		BoardVO boardvo = dao.getView(seq);
		return boardvo;
	}

	// === #72. 1개글 수정하기 ===
	@Override
	public int edit(BoardVO boardvo) {
		
		// 수정하려하는 글에 대한 원래의 암호를 읽어와서
		// 수정시 입력한 암호와 비교를 한다.
		boolean bool = dao.checkPW(boardvo);
		if(bool==false) { // 암호가 일치하지 않는경우
			return 0;
		}
		else {
			// 글 수정한다.
			int n = dao.updateBoard(boardvo);
			
			return n;
		}
		
	}

	// === #79. 1개글 삭제하기(댓글쓰기 게시판인 경우 트랜잭션 처리를 해야 한다.) ===
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int del(BoardVO boardvo) throws Throwable {
		
		// 삭제하려하는 글에 대한 원래의 암호를 읽어와서
		// 삭제시 입력한 암호와 비교를 한다.
		boolean bool = dao.checkPW(boardvo);
		
		if(bool==false) { // 암호가 일치하지 않는경우
			return 0;
		}
		else {
			// === 댓글이 있는 게시판인 경우라면 === //
			// 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.
			int m = 0;
			String strCommentCount = dao.getView(boardvo.getSeq()).getCommentCount();
			
			int commentCount = Integer.parseInt(strCommentCount);
			if(commentCount > 0) {
				m = dao.deleteComment(boardvo.getSeq()); 
			}
			///////////////////////////////////////////////
			// 글 1개를 삭제한다.
			int n = dao.deleteBoard(boardvo); 
			
			int result = (m==0)?n:n*m;
			
			return result;
		}
	}

	// === #86. 댓글쓰기 ===
	// tblComment 테이블에 insert 된 다음에 
	// tblBoard 테이블에 commentCount 컬럼이 1증가(update) 하도록 요청한다.
	// 즉, 2개이상의 DML 처리를 해야하므로 Transaction 처리를 해야 한다.
	// >>>>> 트랜잭션처리를 해야할 메소드에 @Transactional 어노테이션을 설정하면 된다. 
	// rollbackFor={Throwable.class} 은 롤백을 해야할 범위를 말하는데 Throwable.class 은 error 및 exception 을 포함한 최상위 루트이다. 즉, 해당 메소드 실행시 발생하는 모든 error 및 exception 에 대해서 롤백을 하겠다는 말이다.
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	public int addComment(CommentVO commentvo) throws Throwable {
		
		int result = 0;
		
		int n = 0;
		
		n = dao.addComment(commentvo); // 댓글쓰기(tblComment 테이블에 insert)
		if(n==1) {
			result = dao.updateCommentCount(commentvo.getParentSeq()); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update)
		}
		
		return result;
	}

	// === #86. 원게시물에 딸린 댓글보여주기 ===
	@Override
	public List<CommentVO> getCommentList(String parentSeq) {
		List<CommentVO> commentList = dao.getCommentList(parentSeq);
		return commentList;
	}

	// 페이징처리를 안한 검색어가 있는 전체 글목록 보여주기
	@Override
	public List<BoardVO> boardListSearch(HashMap<String, String> paraMap) {
		List<BoardVO> boardListSearch = dao.boardListSearch(paraMap);
		return boardListSearch;
	}

	// === #104. 검색어 입력시 자동글 완성하기 4 ===
	@Override
	public List<String> wordSearchShow(HashMap<String, String> paraMap) {
		List<String> wordList = dao.wordSearchShow(paraMap);
		return wordList;
	}

	
	
		
		
		
		
		
		
		
		
		
		
		
}