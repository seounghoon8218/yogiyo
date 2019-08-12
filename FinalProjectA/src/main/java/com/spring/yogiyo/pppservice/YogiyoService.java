package com.spring.yogiyo.pppservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.member.model.MemberVO;
import com.spring.yogiyo.pppmodel.BoardVO;
import com.spring.yogiyo.pppmodel.CommentVO;
import com.spring.yogiyo.pppmodel.InterYogiyoDAO;

//#31. Service 선언
@Service
public class YogiyoService implements InterYogiyoService {

   // === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
   @Autowired
   private InterYogiyoDAO dao;

   // 카테고리 리스트 불러오기
   @Override
   public List<HashMap<String, String>> getShopCategory() {
      List<HashMap<String, String>> categoryList = dao.getShopCategory();
      return categoryList;
   }

   // 차트 테스트 ( 업종별 매장수 차트 )
   @Override
   public List<HashMap<String, String>> chartList() {
      List<HashMap<String, String>> chartList = dao.chartList();
      return chartList;
   }

   // TBL_PAYMENT 테이블에 추가
   @Override
   public int insertPayment(HashMap<String, String> paraMap) {
      int m = dao.insertPayment(paraMap);
      return m;
   }

   // 결제완료했으면 해당 아이디 장바구니 비우기
   @Override
   public int alldeleteCart(String email) {
      int z = dao.alldeleteCart(email);
      return z;
   }

   // 음식별 매장 판매 랭킹
   @Override
   public List<HashMap<String, String>> categoryRankShop() {
      List<HashMap<String, String>> chartList = dao.categoryRankShop();
      return chartList;
   }
   // 음식별 매장 판매 랭킹
   @Override
   public List<HashMap<String, String>> categoryRankShopEnd(String shopcategoryname) {
      List<HashMap<String, String>> chartList = dao.categoryRankShopEnd(shopcategoryname);
      return chartList;
   }

   // 총 회원수 구하기
   @Override
   public int getTotalMemberCnt(String searchWord) {
      int totalMemberCnt = dao.getTotalMemberCnt(searchWord);
      return totalMemberCnt;
   }

   // 페이징 처리한 데이터 조회 결과물 가져오기
   @Override
   public List<MemberVO> getPageMember(HashMap<String, String> paraMap) {
      List<MemberVO> MemberList = dao.getPageMember(paraMap);
      return MemberList;
   }
   
// 검색조건이 없을경우의 총 게시물 건수(totalCount)
   @Override
   public int getTotalCountWithNoSearch() {
      int totalCount = dao.getTotalCountWithNoSearch();
      return totalCount;
   }

   // 검색조건이 있을경우의 총 게시물 건수(totalCount)
   @Override
   public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
      int totalCount = dao.getTotalCountWithSearch(paraMap);
      return totalCount;
   }

   // 페이징 처리한 글목록 가져오기 ( 검색이 있든지, 검색이 없든지 다 포함한 것 )
   @Override
   public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
      List<BoardVO> boardList = dao.boardListWithPaging(paraMap);
      return boardList;
   }

   // 글쓰기(파일첨부가 없는 글쓰기) ===
   @Override
   public int add(BoardVO boardvo) {

      // === #126. 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지를 구분하여
      // tblBoard 테이블에 insert 를 해주어야 한다.
      // 원글쓰기 이라면 tblBoard 테이블의 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해서 insert 해야
      // 하고,
      // 답변글쓰기 이라면 넘겨받은 값을 그대로 insert 해주어야 한다.

      // == 원글쓰기인지, 답변글쓰기인지 구분하기 ==
      if (boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
         // 원글쓰기인 경우
         int groupno = dao.getGroupnoMax() + 1;
         boardvo.setGroupno(String.valueOf(groupno));
      }
      //////////////////////////////////////////////

      int n = dao.add(boardvo);
      return n;
   }

   // 글쓰기(파일첨부가 있는 글쓰기) ===
   @Override
   public int add_withFile(BoardVO boardvo) {
      // === 글쓰기가 원글쓰기인지 아니면 답변글쓰기인지를 구분하여
      // tblBoard 테이블에 insert 를 해주어야 한다.
      // 원글쓰기 이라면 tblBoard 테이블의 groupno 컬럼의 값은 groupno 컬럼의 최대값(max)+1 로 해서 insert 해야
      // 하고,
      // 답변글쓰기 이라면 넘겨받은 값을 그대로 insert 해주어야 한다.

      // == 원글쓰기인지, 답변글쓰기인지 구분하기 ==
      if (boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
         // 원글쓰기인 경우
         int groupno = dao.getGroupnoMax() + 1;
         boardvo.setGroupno(String.valueOf(groupno));
      }
      //////////////////////////////////////////////

      int n = dao.add_withFile(boardvo); // 첨부파일이 있는 경우
      return n;
   }

   // ===1개 글 보여주기. ===
   // (먼저, 로그인을 한 상태에서 다른 사람의 글을 조회할 경우에는 글조회수 컬럼 1증가해야 한다.)
   @Override
   public BoardVO getView(String seq, String userid) {
      // userid 는 로그인한 사용자의 userid 이다.
      // 만약에 로그인을 하지 않은 상태이라면 userid 는 null 이다.
      BoardVO boardvo = dao.getView(seq);

      if (userid != null && !boardvo.getFk_email().equals(userid)) {
         // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 해야 한다.
         // 로그인 하지 않은 상태에서는 글조회수 증가는 없다.

         dao.setAddReadCount(seq); // 글조회수 1증가하기
         boardvo = dao.getView(seq);
      }

      return boardvo;
   }

   // === 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것 ===
   @Override
   public BoardVO getViewWithNoAddCount(String seq) {
      BoardVO boardvo = dao.getView(seq);
      return boardvo;
   }

   // 원게시물에 딸린 댓글보여주기 === 
   @Override
   public List<CommentVO> getCommentList(String parentSeq) {
      List<CommentVO> commentList = dao.getCommentList(parentSeq);
      return commentList;
   }
   
   // 1개글 수정하기 ===
   @Override
   public int edit(BoardVO boardvo) {
      
      // 수정하려하는 글에 대한 원래의 암호를 읽어와서 
      // 수정시 입력한 암호와 비교를 한다.
      boolean bool = dao.checkPW(boardvo);
      
      if(!bool) // 암호가 일치 하지 않는 경우
         return 0;
      else {
         // 글 1개를 수정한다.
         int n = dao.updateBoard(boardvo);
         
         return n;
      }
   }
   
   // 1개글 삭제하기(댓글쓰기 게시판인 경우 트랜잭션 처리를 해야 한다.) === 
   @Override
   @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class}) 
   public int del(BoardVO boardvo) throws Throwable {
      
      // 삭제하려는 글에 대한 원래의 암호를 읽어와서 
      // 삭제시 입력한 암호와 비교를 한다.
      boolean bool = dao.checkPW(boardvo);
      
      if(!bool) // 암호가 일치 하지 않는 경우
         return 0;
      else {
         
         //// === 댓글이 있는 게시판인 경우라면  === ////
         // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다.
         String seq = boardvo.getSeq();
                  
         int m = 0;
         String strCommentCount = dao.getView(seq).getCommentCount();
         
         int commentCount = Integer.parseInt(strCommentCount);
         if(commentCount > 0) {
            m = dao.deleteComment(boardvo.getSeq());
         }
         /////////////////////////////////////////////
                  
         // 글 1개를 삭제한다.
         int n = dao.deleteBoard(boardvo);  
         
         int result = (m==0)?n:n*m;
         
         return result;
      }
   }

   //  댓글쓰기 === 
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
         result = dao.updateCommentCount(commentvo.getParentSeq()); // pppBoard 테이블에 commentCount 컬럼의 값을 1증가(update) 
      }
      
      return result;
   }
   
   // 검색어 입력시 자동글 완성하기 4 === 
   @Override
   public List<String> wordSearchShow(HashMap<String, String> paraMap) {
      List<String> wordList = dao.wordSearchShow(paraMap);
      return wordList;
   }
   
}