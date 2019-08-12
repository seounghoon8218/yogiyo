package com.spring.yogiyo.pppmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.member.model.MemberVO;

//== #32. DAO 선언 ===
@Repository
public class YogiyoDAO implements InterYogiyoDAO {

   // === #33. 의존객체 주입하기 ( DI : Dependency Injection ) ====
   @Autowired   // Type에 따라 알아서 Bean 주입해줌
   private SqlSessionTemplate sqlsession;

   // 카테고리 리스트 불러오기
   @Override
   public List<HashMap<String, String>> getShopCategory() {
      List<HashMap<String, String>> categoryList = sqlsession.selectList("yogiyo.getShopCategory");
      return categoryList;
   }

   // 차트 테스트 ( 업종별 매장수 차트 )
   @Override
   public List<HashMap<String, String>> chartList() {
      List<HashMap<String, String>> chartList = sqlsession.selectList("yogiyo.chartList");
      return chartList;
   }

   // TBL_PAYMENT 테이블에 추가
   @Override
   public int insertPayment(HashMap<String, String> paraMap) {
      int m = sqlsession.insert("yogiyo.insertPayment",paraMap);
      return m;
   }

   // 결제완료했으면 해당 아이디 장바구니 비우기
   @Override
   public int alldeleteCart(String email) {
      int z = sqlsession.delete("yogiyo.alldeleteCart",email);
      return z;
   }

   // 음식별 매장 판매 랭킹
   @Override
   public List<HashMap<String, String>> categoryRankShop() {
      List<HashMap<String, String>> chartList = sqlsession.selectList("yogiyo.categoryRankShop");
      return chartList;
   }
   // 음식별 매장 판매 랭킹
   @Override
   public List<HashMap<String, String>> categoryRankShopEnd(String shopcategoryname) {
      List<HashMap<String, String>> chartList = sqlsession.selectList("yogiyo.categoryRankShopEnd",shopcategoryname);
      return chartList;
   }

   // 총 회원수 구하기
   @Override
   public int getTotalMemberCnt(String searchWord) {
      int totalMemberCnt = sqlsession.selectOne("yogiyo.getTotalMemberCnt",searchWord);
      return totalMemberCnt;
   }

   // 페이징 처리한 데이터 조회 결과물 가져오기
   @Override
   public List<MemberVO> getPageMember(HashMap<String, String> paraMap) {
      List<MemberVO> MemberList = sqlsession.selectList("yogiyo.getPageMember",paraMap);
      return MemberList;
   }
   
// 검색조건이 없을경우의 총 게시물 건수(totalCount)
   @Override
   public int getTotalCountWithNoSearch() {
      int totalCount = sqlsession.selectOne("yogiyo.getTotalCountWithNoSearch");
      return totalCount;
   }
   // 검색조건이 있을경우의 총 게시물 건수(totalCount)
   @Override
   public int getTotalCountWithSearch(HashMap<String, String> paraMap) {
      int totalCount = sqlsession.selectOne("yogiyo.getTotalCountWithSearch",paraMap);
      return totalCount;
   }

   @Override
   public List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap) {
      List<BoardVO> boardList = sqlsession.selectList("yogiyo.boardListWithPaging",paraMap);
      return boardList;
   }

   // 첨부파일이 없는 경우라면
   @Override
   public int add(BoardVO boardvo) {
      int n = sqlsession.insert("yogiyo.add",boardvo);
      return n;
   }
   // 첨부파일이 있는 경우라면
   @Override
   public int add_withFile(BoardVO boardvo) {
      int n = sqlsession.insert("yogiyo.add_withFile",boardvo);
      return n;
   }
   // tblBoard 테이블에서 groupno 컬럼의 최대값 구하기 
   @Override
   public int getGroupnoMax() {
      int max = sqlsession.selectOne("yogiyo.getGroupnoMax");
      return max;
   }

   // 글1개 보여주기
   @Override
   public BoardVO getView(String seq) {
      BoardVO boardvo = sqlsession.selectOne("yogiyo.getView", seq);
      return boardvo;
   }
   // 글 조회수 1증가하기
   @Override
   public void setAddReadCount(String seq) {
      sqlsession.update("yogiyo.setAddReadCount", seq);
   }

   // === 원게시물에 딸린 댓글보여주기 ===
   @Override
   public List<CommentVO> getCommentList(String parentSeq) {
      List<CommentVO> commentList = sqlsession.selectList("yogiyo.getCommentList", parentSeq);
      return commentList;
   }
   
   // 글수정 및 글삭제시 암호일치 여부 알아오기 === 
   @Override
   public boolean checkPW(BoardVO boardvo) {
      int n = sqlsession.selectOne("yogiyo.checkPW", boardvo); 
      
      if(n==1) 
         return true;
      else
         return false;
   }
   
   // === 글 1개를 수정한다. ===
   @Override
   public int updateBoard(BoardVO boardvo) {
      int n = sqlsession.update("yogiyo.updateBoard", boardvo);
      return n;
   }

   // 원게시물에 딸린 모든 댓글들을 삭제하도록 한다. ===
   @Override
   public int deleteComment(String seq) {
      int n = sqlsession.delete("yogiyo.deleteComment", seq);
      return n;
   }
   
   // === #80. 1개글 삭제하기  ===
   @Override
   public int deleteBoard(BoardVO boardvo) {
      int n = sqlsession.update("yogiyo.deleteBoard", boardvo);
      return n;
   }

   // 댓글쓰기(tblComment 테이블에 insert) === 
   @Override
   public int addComment(CommentVO commentvo) {
      int n = sqlsession.insert("yogiyo.addComment", commentvo);
      return n;
   }
   
   // pppBoard 테이블에 commentCount 컬럼의 값을 1증가(update) === 
   @Override
   public int updateCommentCount(String parentSeq) {
      int n = sqlsession.update("yogiyo.updateCommentCount", parentSeq);
      return n;
   }

   // 검색어 입력시 자동글 완성하기 5 === 
   @Override
   public List<String> wordSearchShow(HashMap<String, String> paraMap) {
      List<String> wordList = sqlsession.selectList("yogiyo.wordSearchShow", paraMap);
      return wordList;
   }
   
}
