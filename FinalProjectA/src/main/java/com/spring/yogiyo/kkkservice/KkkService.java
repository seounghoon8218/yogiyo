package com.spring.yogiyo.kkkservice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.yogiyo.kkkmodel.BoardVO;
import com.spring.yogiyo.kkkmodel.CommentVO;
import com.spring.yogiyo.kkkmodel.InterKkkDAO;
import com.spring.yogiyo.ooomodel.oooVO;

@Service
public class KkkService implements InterKkkService {

	// === #34. 의존객체 주입하기 ( DI : Dependency Injection ) ====
		@Autowired
		private InterKkkDAO dao;

		// 매장 가져오기
		@Override
		public List<oooVO> getShopList(HashMap<String,String> paraMap) {
			List<oooVO> shopList = dao.getShopList(paraMap);
			return shopList;
		}
		

		// 매장하나정보 가지고오기
		@Override
		public oooVO getShopView(String masterno) {
			oooVO shop = dao.getShopView(masterno);
			return shop;
		}

		// 메뉴카테고리 가져오기
		@Override
		public List<HashMap<String, String>> getMenucategoryList() {
			List<HashMap<String, String>> menucategoryList = dao.getMenucategoryList();
			return menucategoryList;
		}

		// 리스트별 메뉴 가져오기
		@Override
		public List<HashMap<String, String>> getMenuList(HashMap<String, String> paramap) {
			List<HashMap<String, String>> menuList = dao.getMenuList(paramap);
			return menuList;
		}

		// 장바구니에 insert 하기 전 이미 존재하는것인지 체크하기
		@Override
		public int cartSelect(HashMap<String, String> paramap) {
			int m = dao.cartSelect(paramap);
			return m;
		}
		// 장바구니에 insert 해주기
		@Override
		public int cartInsert(HashMap<String, String> paramap) {
			int n = dao.cartInsert(paramap);
			return n;
		}

		// 로그인된 email 에 해당하는 cart테이블정도 select 해오기
		@Override
		public List<HashMap<String, String>> selectCartList(String email) {
			List<HashMap<String, String>> cartList = dao.selectCartList(email);
			return cartList;
		}


		// 주문메뉴 삭제
		@Override
		public int delMenu(String orderno) {
			int n = dao.delMenu(orderno);
			return n;

		}

		// 타음식점 추가 불가 기능
		@Override
		public String getCartMasterno(String email) {
			String msno = dao.getCartMasterno(email);
			return msno;
		}

		// 지훈 - 매장정보
	      @Override
	      public List<HashMap<String, String>> getShopInfo(String masterno) {
	         List<HashMap<String, String>> shopInfo = dao.getShopInfo(masterno);
	         return shopInfo;
	      }

	   // 총 별점 평균 보여주기
	      @Override
	      public double getStarpointAvg(int masterno) {
	         double starpointAvg = dao.getStarpointAvg(masterno);
	         return starpointAvg;
	      }

	      // 총 리뷰 갯수 보여주기
	      @Override
	      public int getReviewCount(int masterno) {
	         int reviewCount = dao.getReviewCount(masterno);
	         return reviewCount;
	      }

	      // 리뷰 보여주기
	      @Override
	      public List<HashMap<String, String>> getReviewList(String masterno) {
	    	  
	    	 // 조건
	    	 int count = dao.getReviewCount1(masterno);
	    	 
	    	 List<HashMap<String, String>> reviewList = null;
	    	 
	    	 if(count > 0) { 
	    		  reviewList = dao.getReviewList(masterno);
	    	 }
	    	 else {
	    	 }
	    		 
	    	 	 
	         return reviewList;
	      }

	      // 리뷰 등록
	      @Override
	      public int addReview(HashMap<String, String> paramap) {
	         int n = dao.addReview(paramap);
	         return n;
	      }

	      // 결제테이블에서 Status 체크
	      @Override
	      public List<HashMap<String, String>> checkOrderStatus(HashMap<String, String> paramap) {
	         List<HashMap<String, String>> k = dao.checkOrderStatus(paramap);
	         return k;
	         
	      }	      
	      
	      // 별점이랑 리뷰갯수 업데이트 해주기
	      @Override
	      public int updateStarpAndReviewc(HashMap<String, String> masterStarReviewMap) {
	         int sprc = dao.updateStarpAndReviewc(masterStarReviewMap);
	         return sprc;
	      }         
	      
	      
	      /*-----------------------------손혜현 게시판 시작----------------------------*/
	      // 페이징 처리한 글목록 가져오기
	      @Override
	      public List<BoardVO> KKKBoardListWithPaging(HashMap<String, String> paraMap) {
	         List<BoardVO> list = dao.KKKBoardListWithPaging(paraMap);
	         return list;
	      }
	         
	      // 검색조건이 없을 경우의 총 게시물 건수(totalCount)
	       @Override
	      public int getKKKTotalCountWithNOsearch() {
	         int n = dao.getKKKTotalCountWithNOsearch();
	         return n;
	      }

	       // 검색조건이 있을 경우의 총 게시물 건수(totalCount)
	      @Override
	      public int getKKKTotalCountWithSearch(HashMap<String, String> paraMap) {
	         int n = dao.getKKKTotalCountWithSearch(paraMap);
	         return n;
	      }

	      // === 검색어 입력시 자동 글 완성하기  ===
	      @Override
	      public List<String> KKKwordSearchShow(HashMap<String, String> paraMap) {
	         List<String> wordList = dao.KKKwordSearchShow(paraMap);
	         return wordList;
	      }

	      // 첨부파일이 없는 경우
	      @Override
	      public int KKKadd(BoardVO boardvo) {
	         if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
	            // 원글쓰기인 경우
	            int groupno = dao.getKKKGroupnoMax()+1;
	            boardvo.setGroupno(String.valueOf(groupno));
	         }
	         
	         //////////////////////////////////////////////////////////////////////////
	         
	         int n = dao.KKKadd(boardvo);
	         return n;
	      }
	      

	      // 첨부파일이 있는 경우
	      @Override
	      public int KKKadd_withFile(BoardVO boardvo) {
	         if(boardvo.getFk_seq() == null || boardvo.getFk_seq().trim().isEmpty()) {
	            // 원글쓰기인 경우
	            int groupno = dao.getKKKGroupnoMax()+1;
	            boardvo.setGroupno(String.valueOf(groupno));
	         }
	         
	         //////////////////////////////////////////////////////////////////////////
	         
	         int n = dao.KKKadd_withFile(boardvo); // 첨부 파일이 있는 경우
	         return n;
	      }

	      // 글조회수 증가와 함께 글1개 조회를 해주는 것
	      @Override
	      public BoardVO getKKKView(String seq, String userid) {
	         BoardVO boardvo = dao.getKKKView(seq);
	         
	         if( userid != null && !boardvo.getFk_userid().equals(userid) ) {
	            // 글조회수 증가는 다른 사람의 글을 읽을때만 증가하도록 해야 한다.
	            // 로그인 하지 않은 상태에서는 글조회수 증가는 없다.
	            
	            dao.setKKKAddReadCount(seq);  // 글조회수 1증가하기
	            boardvo = dao.getKKKView(seq);
	         }
	         
	         return boardvo;
	      }

	      // 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것
	      @Override
	      public BoardVO getKKKViewWithNoAddCount(String seq) {
	         BoardVO boardvo = dao.getKKKView(seq);
	         return boardvo;
	      }

	      // 원게시물에 딸린 댓글들을 조회해오는 것
	      @Override
	      public List<CommentVO> getKKKCommentList(String parentSeq) {
	         List<CommentVO> coList = dao.getKKKCommentList(parentSeq);
	         return coList;
	      }

	      // 글수정 페이지 완료하기
	      @Override
	      public int editKKK(BoardVO boardvo) {
	         boolean bool = dao.checkPWKKK(boardvo);
	         
	         if(!bool) // 암호가 일치 하지 않는 경우
	            return 0;
	         else {
	            // 글 1개를 수정한다.
	            int n = dao.updateBoardKKK(boardvo);
	            
	            return n;
	         }
	      }

	      // 글삭제 페이지 완료하기
	      @Override
	      @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class}) 
	      public int delKKK(BoardVO boardvo) throws Throwable {
	         
	         // 삭제하려는 글에 대한 원래의 암호를 읽어와서 
	         // 삭제시 입력한 암호와 비교를 한다.
	         boolean bool = dao.checkPWKKK(boardvo);
	         
	         if(!bool) // 암호가 일치 하지 않는 경우
	            return 0;
	         else {
	            
	            // === 댓글이 있는 게시판인 경우라면 === //
	            // 원게시물에 담긴 모든 댓글들을 삭제하도록 한다.
	            int m = 0;
	            String strCommentCount = dao.getKKKView(boardvo.getSeq()).getCommentCount();
	            int commentCount = Integer.parseInt(strCommentCount);
	            if(commentCount > 0) {
	               m = dao.deleteKKKComment(boardvo.getSeq());
	            }
	            ///////////////////////////////////////////////////////////
	            
	            // 글 1개를 삭제한다.
	            int n = dao.deleteKKKBoard(boardvo);
	            
	            int result = (m==0)?n:n*m;
	            
	            return result;
	         }
	      }

	      // 댓글쓰기
	      @Override
	      @Transactional(propagation=Propagation.REQUIRED, isolation= Isolation.READ_COMMITTED, rollbackFor={Throwable.class})
	      public int addKKKComment(CommentVO commentvo) {
	         int result = 0;
	         int n = 0;
	         
	         n = dao.addKKKComment(commentvo); // 댓글쓰기(tblComment 테이블에 insert) 
	         if(n==1) {
	            result = dao.updateKKKCommentCount(commentvo.getParentSeq()); // tblBoard 테이블에 commentCount 컬럼의 값을 1증가(update) 
	         }
	         
	         return result;
	      }
	      
	      
	      
}
