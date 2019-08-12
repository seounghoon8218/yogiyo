package com.spring.yogiyo.lllservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.yogiyo.lllmodel.CommentVO;
import com.spring.yogiyo.lllmodel.InterLllDAO;
import com.spring.yogiyo.lllmodel.MoonVO;

@Service
public class LllService implements InterLllService {
   
   // === #34. 의존객체 주입하기(DI: Dependency Injection) ===
      @Autowired   // Type에 따라 알아서 Bean 을 주입해준다.
      private InterLllDAO dao;
   
      
      // === #54. 글쓰기(파일첨부가 없는 글쓰기) ===
      @Override
      public int moonadd(MoonVO moonvo) {

         // === #126. 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지를 구분하여
         //           tblBoard 테이블에 insert 를 해주어야 한다.
         //           원글쓰기 이라면 tblBoard 테이블의 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해서 insert 해야 하고,  
         //           답변글쓰기 이라면 넘겨받은 값을 그대로 insert 해주어야 한다.
         
         // == 원글쓰기인지, 답변글쓰기인지 구분하기 == 
         if(moonvo.getFk_seq() == null || moonvo.getFk_seq().trim().isEmpty() ) {  
            // 원글쓰기인 경우
            int groupno = dao.getGroupnoMax()+1;
            moonvo.setGroupno(String.valueOf(groupno));
         }
         //////////////////////////////////////////////
         
         int n = dao.moonadd(moonvo);
         return n;
      }


      // === #110. 검색조건이 없을 경우의 총 게시물 건수(totalCount) === 
      @Override
      public int getTotalCountWithNOsearch() {
         int count = dao.getTotalCountWithNOsearch();
         return count;
      }


      // === #113. 검색조건이 있을 경우의 총 게시물 건수(totalCount) === 
      @Override
      public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
         int count = dao.getTotalCountWithSearch(paraMap);
         return count;
      }


      // === #116. 페이징 처리한 글목록 가져오기(검색이 있든지 , 검색이 없든지 다 포함한것) === 
      @Override
      public List<MoonVO> boardListWithPaging(HashMap<String, String> paraMap) {
         List<MoonVO> boardList = dao.boardListWithPaging(paraMap); 
         return boardList;
      }


      // === #62. 1개 글 보여주기. ===
      // (먼저, 로그인을 한 상태에서 다른 사람의 글을 조회할 경우에는 글조회수 컬럼 1증가해야 한다.) 
      @Override
      public MoonVO getView(String seq, String email) {
                                 
         MoonVO moonvo = dao.getView(seq);
         
         if( email != null && !moonvo.getFk_email().equals(email) ) {
            // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 해야 한다.
            // 로그인 하지 않은 상태에서는 글조회수 증가는 없다.
            
            dao.setAddReadCount(seq);  // 글조회수 1증가하기
            moonvo = dao.getView(seq);
         }
         
         return moonvo;
      }


      // === #69. 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것 ===
      @Override
      public MoonVO getViewWithNoAddCount(String seq) {
         MoonVO moonvo = dao.getView(seq);
         return moonvo;
      }


      // === #86. 원게시물에 딸린 댓글보여주기 === 
      @Override
      public List<CommentVO> getCommentList(String parentSeq) {
         List<CommentVO> commentList = dao.getCommentList(parentSeq);
         return commentList;
      }


      // === #72. 1개글 수정하기 ===
      @Override
      public int edit(MoonVO moonvo) {
         
         // 수정하려하는 글에 대한 원래의 암호를 읽어와서 
         // 수정시 입력한 암호와 비교를 한다.
         boolean bool = dao.checkPW(moonvo);
         
         if(!bool) // 암호가 일치 하지 않는 경우
            return 0;
         else {
            // 글 1개를 수정한다.
            int n = dao.updateBoard(moonvo);
            
            return n;
         }
      }


      // === #79. 1개글 삭제하기(댓글쓰기 게시판인 경우 트랜잭션 처리를 해야 한다.) === 
      @Override
      @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class}) 
      public int del(MoonVO moonvo) throws Throwable {
         
         // 삭제하려는 글에 대한 원래의 암호를 읽어와서 
         // 삭제시 입력한 암호와 비교를 한다.
         boolean bool = dao.checkPW(moonvo);
         
         if(!bool) // 암호가 일치 하지 않는 경우
            return 0;
         else {
            
            //// === 댓글이 있는 게시판인 경우라면  === ////
            // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.
            String seq = moonvo.getSeq();
                     
            int m = 0;
            String strCommentCount = dao.getView(seq).getCommentCount();
            
            int commentCount = Integer.parseInt(strCommentCount);
            if(commentCount > 0) {
               m = dao.deleteComment(moonvo.getSeq());
            }
            /////////////////////////////////////////////
                     
            // 글 1개를 삭제한다.
            int n = dao.deleteBoard(moonvo);  
            
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


      // === #104. 검색어 입력시 자동글 완성하기 4 === 
      @Override
      public List<String> wordSearchShow(HashMap<String, String> paraMap) {
         List<String> wordList = dao.wordSearchShow(paraMap);
         return wordList;
      }
}