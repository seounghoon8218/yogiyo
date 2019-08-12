package com.spring.yogiyo.kkkmodel;

import java.util.HashMap;
import java.util.List;

import com.spring.yogiyo.ooomodel.oooVO;

public interface InterKkkDAO {

	// 매장가져오기
	List<oooVO> getShopList(HashMap<String,String> paraMap);

	// 매장하나정보 가지고오기
	oooVO getShopView(String masterno);

	// 메뉴카테고리 가져오기
	List<HashMap<String, String>> getMenucategoryList();
	// 리스트별 메뉴 가져오기
	List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap);

	// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
	int cartSelect(HashMap<String, String> paramap);
	// 장바구니에 insert 해주기
	int cartInsert(HashMap<String, String> paramap);

	// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
	List<HashMap<String, String>> selectCartList(String email);

	// 주문메뉴 삭제
	int delMenu(String orderno);

	// 타음식점 추가 불가 기능
	String getCartMasterno(String email);

	// 지훈 - 매장정보
	   List<HashMap<String, String>> getShopInfo(String masterno);	

	// 총 별점 평균 보여주기
	   double getStarpointAvg(int masterno);
	   
	   // 총 리뷰 갯수 보여주기
	   int getReviewCount(int masterno);
	   
	   // 리뷰 등록
	   List<HashMap<String, String>> getReviewList(String masterno);
	   int addReview(HashMap<String, String> paramap);

	   // 결제테이블에서 Status 체크
	   List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap);

	   // 리뷰갯수 구해오기
	   int getReviewCount1(String masterno);	   
	   
	   // 별점이랑 리뷰갯수 업데이트 해주기
	   int updateStarpAndReviewc(HashMap<String, String> masterStarReviewMap);   
	   
	   
	   /*-------------------------------손혜현 게시판 시작------------------------------*/
	   // 페이징 처리한 글목록 가져오기
	   List<BoardVO> KKKBoardListWithPaging(HashMap<String, String> paraMap);
	   
	   // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	   int getKKKTotalCountWithNOsearch();
	   
	   // 검색조건이 있을 경우의 총 게시물 건수(totalCount)   
	   int getKKKTotalCountWithSearch(HashMap<String, String> paraMap);

	   // === 검색어 입력시 자동 글 완성하기  ===
	   List<String> KKKwordSearchShow(HashMap<String, String> paraMap);

	   // 첨부파일이 없는 경우
	   int KKKadd(BoardVO boardvo);

	   // 첨부파일이 있는 경우
	   int KKKadd_withFile(BoardVO boardvo);

	   // 글조회수 증가와 함께 글1개 조회를 해주는 것
	   BoardVO getKKKView(String seq);
	   // 글 조회수 1 증가
	   void setKKKAddReadCount(String seq);

	   // 글수정 및 글삭제시 암호일치 여부 알아오기 
	   boolean checkPWKKK(BoardVO boardvo);   

	   // 원게시물에 딸린 댓글들을 조회해오는 것
	   List<CommentVO> getKKKCommentList(String parentSeq);

	   // 글 1개를 수정한다.
	   int updateBoardKKK(BoardVO boardvo);
	      
	   // 댓글쓰기
	   int addKKKComment(CommentVO commentvo);

	   // KKKBoard 테이블에서 groupno 컬럼의 최댓값 구하기
	   int getKKKGroupnoMax();

	   // 원게시물에 딸린 댓글삭제
	   int deleteKKKComment(String seq);

	   // 글 1개를 삭제한다.
	   int deleteKKKBoard(BoardVO boardvo);

	   // KKKBoard 테이블에 commentCount 컬럼의 값을 1증가(update) 
	   int updateKKKCommentCount(String parentSeq);

	   
	   
}
