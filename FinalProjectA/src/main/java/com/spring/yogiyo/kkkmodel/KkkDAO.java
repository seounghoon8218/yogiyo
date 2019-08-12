package com.spring.yogiyo.kkkmodel;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.yogiyo.ooomodel.oooVO;

//== #32. DAO 선언 ===
@Repository
public class KkkDAO implements InterKkkDAO {

	// === #33. 의존객체 주입하기 ( DI : Dependency Injection ) ====
	@Autowired // Type에 따라 알아서 Bean 주입해줌
	private SqlSessionTemplate sqlsession;

	// 매장가져오기
	@Override
	public List<oooVO> getShopList(HashMap<String, String> paraMap) {
		List<oooVO> shopList = sqlsession.selectList("kkk.getShopList", paraMap);
		return shopList;
	}

	// 매장하나정보 가지고오기
	@Override
	public oooVO getShopView(String masterno) {
		oooVO shop = sqlsession.selectOne("kkk.getShopView", masterno);
		return shop;
	}

	// 메뉴카테고리 가져오기
	@Override
	public List<HashMap<String, String>> getMenucategoryList() {
		List<HashMap<String, String>> menucategoryList = sqlsession.selectList("kkk.getMenucategoryList");
		return menucategoryList;
	}

	// 리스트별 메뉴 가져오기
	@Override
	public List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap) {
		List<HashMap<String, String>> menuList = sqlsession.selectList("kkk.getMenuList", paramap);
		return menuList;
	}

	// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
	@Override
	public int cartSelect(HashMap<String, String> paramap) {
		int m = sqlsession.selectOne("kkk.cartSelect", paramap);
		return m;
	}

	// 장바구니에 insert 해주기
	@Override
	public int cartInsert(HashMap<String, String> paramap) {
		int n = sqlsession.insert("kkk.cartInsert", paramap);
		return n;
	}

	// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
	@Override
	public List<HashMap<String, String>> selectCartList(String email) {
		List<HashMap<String, String>> cartList = sqlsession.selectList("kkk.selectCartList", email);
		return cartList;
	}

	// 주문메뉴 삭제
	@Override
	public int delMenu(String orderno) {
		int n = sqlsession.delete("kkk.delMenu", orderno);
		return n;
	}

	// 타음식점 추가 불가 기능
	@Override
	public String getCartMasterno(String email) {
		String msno = sqlsession.selectOne("kkk.getCartMasterno", email);
		return msno;
	}

	// 지훈 - 매장정보
	@Override
	public List<HashMap<String, String>> getShopInfo(String masterno) {
		List<HashMap<String, String>> shopInfo = sqlsession.selectList("kkk.getShopInfo", masterno);
		return shopInfo;
	}

	// 총 별점 평균 가져오기
	@Override
	public double getStarpointAvg(int masterno) {
		double starpointAvg = sqlsession.selectOne("kkk.getStarpointAvg", masterno);
		return starpointAvg;
	}

	// 총 리뷰 갯수 보여주기
	@Override
	public int getReviewCount(int masterno) {
		int reviewCount = sqlsession.selectOne("kkk.getReviewCount", masterno);
		return reviewCount;
	}

	// 리뷰 보여주기
	@Override
	public List<HashMap<String, String>> getReviewList(String masterno) {
		List<HashMap<String, String>> reviewList = sqlsession.selectList("kkk.getReviewList", masterno);

		if (reviewList == null){
			System.out.println("널이군요!");
		}

		return reviewList;
	}

	// 리뷰 등록
	@Override
	public int addReview(HashMap<String, String> paramap) {
		int n = sqlsession.insert("kkk.addReview", paramap);
		return n;
	}

	// 결제테이블에서 Status 체크
	@Override
	public List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap) {
		List<HashMap<String, String>> k = sqlsession.selectList("kkk.checkOrderStatus", paramap);
		return k;
	}

	// 리뷰 갯수 구해오기
	@Override
	public int getReviewCount1(String masterno) {
		int reviewCnt = sqlsession.selectOne("kkk.getReviewCount1", masterno);
		return reviewCnt;
	}

	// 별점이랑 리뷰갯수 업데이트 해주기
	@Override
	public int updateStarpAndReviewc(HashMap<String, String> masterStarReviewMap) {
		int sprc = sqlsession.update("kkk.updateStarpAndReviewc", masterStarReviewMap);
		return sprc;
	}
	
	
	  /*----------------------- 손혜현 게시판 시작 -----------------------*/
	   // 페이징 처리한 글목록 가져오기
	   @Override
	   public List<BoardVO> KKKBoardListWithPaging(HashMap<String, String> paraMap) {
	      List<BoardVO> list = sqlsession.selectList("kkk.KKKBoardListWithPaging", paraMap);
	      return list;
	   }      
	      

	   // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	   @Override
	   public int getKKKTotalCountWithNOsearch() {
	      int n = sqlsession.selectOne("kkk.getKKKTotalCountWithNOsearch");
	      return n;
	   }

	   // 검색조건이 있을 경우의 총 게시물 건수(totalCount)
	   @Override
	   public int getKKKTotalCountWithSearch(HashMap<String, String> paraMap) {
	      int n = sqlsession.selectOne("kkk.getKKKTotalCountWithSearch", paraMap);
	      return n;
	   }

	   // === 검색어 입력시 자동 글 완성하기  ===
	   @Override
	   public List<String> KKKwordSearchShow(HashMap<String, String> paraMap) {
	      List<String> wordList = sqlsession.selectList("kkk.KKKwordSearchShow", paraMap);
	      return wordList;
	   }

	   // 첨부파일이 없는 경우
	   @Override
	   public int KKKadd(BoardVO boardvo) {
	      int n = sqlsession.insert("kkk.KKKadd", boardvo);
	      return n;
	   }

	   // 첨부파일이 있는 경우
	   @Override
	   public int KKKadd_withFile(BoardVO boardvo) {
	      int n = sqlsession.insert("kkk.KKKadd_withFile", boardvo);
	      return n;
	   }

	   // 원게시물에 딸린 댓글들을 조회해오는 것
	   @Override
	   public List<CommentVO> getKKKCommentList(String parentSeq) {
	      List<CommentVO> commentList = sqlsession.selectList("kkk.getKKKCommentList", parentSeq);
	      return commentList;
	   }

	   // 댓글쓰기
	   @Override
	   public int addKKKComment(CommentVO commentvo) {
	      int n = sqlsession.insert("kkk.addKKKComment", commentvo);
	      return n;
	   }

	   // === 1개 글 보여주기 === //
	   @Override
	   public BoardVO getKKKView(String seq) {
	      BoardVO boardvo = sqlsession.selectOne("kkk.getKKKView", seq);
	      return boardvo;
	   }

	   // === 글조회수 1증가하기 === //
	   @Override
	   public void setKKKAddReadCount(String seq) {
	      sqlsession.update("kkk.setKKKAddReadCount", seq);
	      
	   }

	   // === 글수정 및 글삭제시 암호일치 여부 알아오기 === 
	   @Override
	   public boolean checkPWKKK(BoardVO boardvo) {
	      int n = sqlsession.selectOne("kkk.checkPWKKK", boardvo); 
	      
	      if(n==1) 
	         return true;
	      else
	         return false;
	   }

	   // === 글 1개를 수정한다. ===
	   @Override
	   public int updateBoardKKK(BoardVO boardvo) {
	      int n = sqlsession.update("kkk.updateKKKBoard", boardvo);
	      return n;
	   }

	   // === KKKBoard 테이블에서 groupno 컬럼의 최대값 구하기 ===
	   @Override
	   public int getKKKGroupnoMax() {
	      int max = sqlsession.selectOne("kkk.getKKKGroupnoMax");
	      return max;
	   }

	   // === 원게시물에 딸린 댓글삭제 ===
	   @Override
	   public int deleteKKKComment(String seq) {
	      int n = sqlsession.delete("kkk.deleteKKKComment", seq);
	      return n;
	   }


	   @Override
	   public int deleteKKKBoard(BoardVO boardvo) {
	      int n = sqlsession.update("kkk.deleteKKKBoard", boardvo);
	      return n;
	   }


	   @Override
	   public int updateKKKCommentCount(String parentSeq) {
	      int n = sqlsession.update("kkk.updateKKKCommentCount", parentSeq);
	      return n;
	   }

}
