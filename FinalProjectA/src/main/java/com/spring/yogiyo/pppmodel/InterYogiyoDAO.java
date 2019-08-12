package com.spring.yogiyo.pppmodel;

import java.util.HashMap;
import java.util.List;

import com.spring.member.model.MemberVO;

public interface InterYogiyoDAO {

   // 카테고리 리스트 불러오기
   List<HashMap<String, String>> getShopCategory();

   // 차트 테스트 ( 업종별 매장수 차트 )
   List<HashMap<String, String>> chartList();

   // TBL_PAYMENT 테이블에 추가
   int insertPayment(HashMap<String, String> paraMap);

   // 결제완료했으면 해당 아이디 장바구니 비우기
   int alldeleteCart(String email);

   // 음식별 매장 판매 랭킹
   List<HashMap<String, String>> categoryRankShop();
   // 음식별 매장 판매 랭킹
   List<HashMap<String, String>> categoryRankShopEnd(String shopcategoryname);

   // 총 회원수 구하기
   int getTotalMemberCnt(String searchWord);

   // 페이징 처리한 데이터 조회 결과물 가져오기
   List<MemberVO> getPageMember(HashMap<String, String> paraMap);

   
// 검색조건이 없을경우의 총 게시물 건수(totalCount)
   int getTotalCountWithNoSearch();
   // 검색조건이 있을경우의 총 게시물 건수(totalCount)
   int getTotalCountWithSearch(HashMap<String, String> paraMap);

   // 페이징 처리한 글목록 가져오기 ( 검색이 있든지, 검색이 없든지 다 포함한 것 )
   List<BoardVO> boardListWithPaging(HashMap<String, String> paraMap);

   // 첨부파일이 없는 경우라면
   int add(BoardVO boardvo);
   // 첨부파일이 있는 경우라면
   int add_withFile(BoardVO boardvo);
   // tblBoard 테이블에서 groupno 컬럼의 최대값 구하기 
   int getGroupnoMax();

   // 1개글 보여주기
   BoardVO getView(String seq);
   // 글 조회수 1 증가하기
   void setAddReadCount(String seq);

   // 원 게시물에 딸린 댓글 보여주기
   List<CommentVO> getCommentList(String parentSeq);
   
   // 수정하려는 글 암호
   boolean checkPW(BoardVO boardvo);
   // 1개 글 수정
   int updateBoard(BoardVO boardvo);

   // 원 게시물에 딸린 모든 댓글 삭제
   int deleteComment(String seq);
   // 글 1개 삭제
   int deleteBoard(BoardVO boardvo);
   
   // 댓글쓰기 insert
   int addComment(CommentVO commentvo);

   // pppboard 테이블에 commentCount 값 1증가(update)
   int updateCommentCount(String parentSeq);

   // 검색어 입력시 자동글 완성
   List<String> wordSearchShow(HashMap<String, String> paraMap);
}