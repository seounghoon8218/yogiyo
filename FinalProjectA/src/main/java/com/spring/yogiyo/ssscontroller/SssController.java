package com.spring.yogiyo.ssscontroller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.FileManager;
import com.spring.common.MyUtil;
import com.spring.member.model.MemberVO;
import com.spring.yogiyo.sssmodel.GreetingBoardVO;
import com.spring.yogiyo.sssmodel.GreetingCommentVO;
import com.spring.yogiyo.sssmodel.MenuVO;
import com.spring.yogiyo.sssservice.InterSssService;

@Controller
public class SssController {

	@Autowired
	private InterSssService service;

	@Autowired
	private FileManager fileManager;

	// 메뉴등록
	@RequestMapping(value = "/registerMenu.yo", method = { RequestMethod.GET })
	public ModelAndView login_registerMenu(HttpServletRequest request, HttpServletResponse response , MenuVO menuvo, ModelAndView mv) {

		mv.setViewName("menu/registerMenu.tiles3");

		return mv;
	}

	// 메뉴등록 완료요청
	@RequestMapping(value = "/registerMenuEnd.yo", method = { RequestMethod.POST })
	public String registerMenuEnd(MenuVO menuvo, MultipartHttpServletRequest mrequest) {

		// 스크립트공격
		String menucomment = menuvo.getMenucomment();
		menucomment = MyUtil.replaceParameter(menucomment);
		menucomment = menuvo.getMenucomment().replaceAll("\r\n", "<br>");
		menuvo.setMenucomment(menucomment);
		
		
		
		// 사진저장
		MultipartFile attach = menuvo.getAttach();
		if (!attach.isEmpty()) {
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + "images";
			String newFileName = "";
			byte[] bytes = null;
			long fileSize = 0;

			try {
				bytes = attach.getBytes();
				newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(), path);
				fileSize = attach.getSize();
				menuvo.setFileName(newFileName);
				menuvo.setOrgFilename(attach.getOriginalFilename());
				menuvo.setFileSize(String.valueOf(fileSize));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // end of if
		
		// 메뉴등록
		int n = 0;
		
		try {
			n = service.menuRegister(menuvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mrequest.setAttribute("n", n);
		
		return "menu/registerMenuEnd.tiles3";
	}
	
	
	// 가입인사게시판등록 페이지
	@RequestMapping(value = "/addGreeting.yo", method = { RequestMethod.GET })
	public ModelAndView login_addGreeting(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

		mv.setViewName("sssGreetingBoard/addGreeting.tiles3");

		return mv;
	}

	// 가입인사 게시판 등록 요청페이지
	@RequestMapping(value="/addGreetingEnd.yo", method = {RequestMethod.POST})
	public ModelAndView addGreetingEnd(ModelAndView mv, GreetingBoardVO gbvo) {
		
		String content = gbvo.getContent();
		String title = gbvo.getTitle();
		
		// 크로스 사이트 공격대비
		gbvo.setTitle(MyUtil.replaceParameter(title));
		content = gbvo.getTitle().replace("/r/n","<br/>");
		gbvo.setTitle(title);		
		
		gbvo.setContent(MyUtil.replaceParameter(content));
		content = gbvo.getContent().replace("/r/n","<br/>");
		gbvo.setContent(content);
		
		int n = service.addGreeting(gbvo);
		
		mv.addObject("n", n);
		mv.setViewName("sssGreetingBoard/addGreetingEnd.tiles3");
			
		return mv;
	}
		
	// 가입인사 등록 요청
	@RequestMapping(value="/greetingList.yo", method = { RequestMethod.GET })
	public ModelAndView greetingList(ModelAndView mv , GreetingBoardVO gvo, HttpServletRequest request) {
		
		List<GreetingBoardVO> greetingList = null;
		
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		int totalCount = 0;			// 총 개시물수
		int sizePerPage = 5;		// 한 페이지당 보여줄 게시물 
		int currentShowPageNo = 0;	// 현제 보여줄 페이지 번호 / 초기페이지는 1로설정
		int totalPage = 0;			// 총 페이지 수
		int startRno = 0;			// 시작 행번호
		int endRno = 0;				// 끝 행번호
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		HashMap<String, String> paramap = new HashMap<String, String>();
		
		// 검색어가 없을경우 > 데이터가 없으어 null 값이 들어가게되면 nullpointexception이 발생하므로 항상 데이터가 있는지 없는지 조건이 필요하다.
		if (searchWord == null || searchWord.trim().isEmpty()) {
			searchWord = "";
		}
		paramap.put("searchType", searchType);
		paramap.put("searchWord", searchWord);
		
		if (!"".equals(searchWord)) {
			mv.addObject("paramap",paramap);
		}
		
		// 검색어가 없는 경우
		if ("".equals(searchWord)) {
			totalCount = service.getTotalCountWithOutSearch();
		} else {
		// 검색어 및 검색타이이 있는 경우
			totalCount = service.getTotalCountWithSearch(paramap);
		}
		
		totalPage = (int) Math.ceil((double)totalCount / sizePerPage);
		// 총게시물수 / 한 페이지장 보여줄 게시물 수 = 페이징 수
		
		if (str_currentShowPageNo == null) {
			//  초기화면일때 > 아무것도 클릭안했을때
			currentShowPageNo = 1;
		} else {
			// 페이징된 번호를 클릭했을떄
			try {
				// 클릭한 곳을 int로 바꿔준후 currentShowPageNo에 저장
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				
				// 유저가 url로 장난쳐서 currentShowPageNo가 1보다 작거나 totalPage보다 클때
				if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					// 에러발생하지 않게 1페이지 (초기페이지)로 이동하게 해줌
					currentShowPageNo = 1;
				}
			} catch (Exception e) {
				// 또한 숫자가아닌 다른 문자를 사용해서 들어오려했을때 발생하는 NumberFormatException 방지
				currentShowPageNo = 1;
			}
			
		}
		
		// 가져올 게시글 범위 구하기
		startRno = ( (currentShowPageNo - 1) * sizePerPage + 1);
		endRno = startRno + sizePerPage - 1;

		paramap.put("startRno", String.valueOf(startRno));
		paramap.put("endRno",String.valueOf(endRno));
		
		// 페이징 처리 완료된 List 불러오기 
		greetingList = service.greetingList(paramap);
		
		// 페이지바 만들기
		String pageBar = "<ul style='margin-top: 25px;'>";
		String url = "greetingList.yo";
		int blockSize = 5;
		
		pageBar += MyUtil.makePageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, searchType, searchWord);
		pageBar += "</ul>";
		
		mv.addObject("pageBar",pageBar);
		
		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		String goBackUrl = MyUtil.getCurrentURL(request);
		mv.addObject("goBackUrl", goBackUrl);
		mv.addObject("greetingList",greetingList);
		
		mv.setViewName("sssGreetingBoard/greetingList.tiles3");
		return mv;
	}
	
	// 글 1개 보기
	@RequestMapping(value = "/greetingView.yo", method = { RequestMethod.GET })
	public ModelAndView greetingView(HttpServletRequest request, ModelAndView mv) {

		try {
			
			String seq = request.getParameter("seq");
			String groupno = request.getParameter("groupno");
			String depthno = request.getParameter("depthno");
			String fk_seq  = request.getParameter("fk_seq");
			
			mv.addObject("groupno", groupno);
			mv.addObject("depthno", depthno);
			mv.addObject("fk_seq", fk_seq);
			
			String useremail = null;
			GreetingBoardVO gbvo = null;
			HttpSession session = request.getSession();
			
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			if (loginuser != null) {
				useremail = loginuser.getEmail();
			}
			
			if ("yes".equals(session.getAttribute("readCountPermission"))) {
				// 글 조회와 동시에 readCount증가하기
				gbvo = service.getGreetingView(seq, useremail);
				
				session.removeAttribute("readCountPermission");
			} else {
				// readCount증가없이 글 조회만 하는 경우
				gbvo = service.getGreetingViewNoAddCount(seq);
				
				// 댓글쓰기가 있는 게시판
				List<GreetingCommentVO> greetingCommentList;
				greetingCommentList = service.getGreetingCommentList(seq);
				
				mv.addObject("greetingCommentList",greetingCommentList);
			}
			String goBackUrl = MyUtil.getCurrentURL(request);
			
			mv.addObject("goBackUrl", goBackUrl);
			mv.addObject("gbvo",gbvo);
			mv.setViewName("sssGreetingBoard/greetingView.tiles3");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	// 글 수정하기
	@RequestMapping(value="/editGreeting.yo", method = {RequestMethod.GET})
	public ModelAndView login_edit(HttpServletRequest request, HttpServletResponse response , ModelAndView mv) {
		
		// 수정해야할 글번호
		String seq = request.getParameter("seq");
		
		// 수정해야할 글 1개 내용 가져오기 증가없는
		GreetingBoardVO gbvo = service.getGreetingViewNoAddCount(seq);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if (!loginuser.getEmail().equals(gbvo.getFk_email())) {
			String msg = "다른 사람의 글은 수정이 불가능합니다.";
			String loc = "javascript:history.back();";
			
			mv.addObject("msg",msg);
			mv.addObject("loc",loc);
			mv.setViewName("msg");
					
		} else {
			// 자신의 글인경우
			mv.addObject("gbvo",gbvo);
			mv.setViewName("sssGreetingBoard/editGreeting.tiles3");
		}
		
		return mv;
	}
	
	// 글 수정 완료 요청하기
	@RequestMapping(value="/editGreetingEnd.yo", method = {RequestMethod.GET})
	public ModelAndView editEnd(HttpServletRequest request, HttpServletResponse response  , ModelAndView mv, GreetingBoardVO gbvo) {
		
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		
		// 크로스 사이트 공격대비
		title = MyUtil.replaceParameter(title);
		title = title.replaceAll("\r\n", "<br/>");
		
		content = MyUtil.replaceParameter(content);
		content = content.replaceAll("\r\n", "<br/>");
		
		gbvo.setContent(content);
		
		// 글 수정시 암호가 전에 작성할때 입력한 암호와 같아야한다.
		int n = service.editGreeting(gbvo);
		
		if (n == 0) {
			mv.addObject("msg", "암호가 일치하지 않습니다.");
		} else {
			mv.addObject("msg", "글수정 완료.");
		}
		
		mv.addObject("loc", request.getContextPath()+"/greetingView.yo?seq="+gbvo.getSeq());
		mv.setViewName("msg");
		
		return mv;
	}
	
	// 글 삭제하기
	@RequestMapping(value="/destroyGreeting.yo", method= {RequestMethod.GET})
	public ModelAndView login_destroyGreeting(HttpServletRequest request,HttpServletResponse response, ModelAndView mv) {
		
		// 삭제할 글번호 가져오기
		String seq = request.getParameter("seq");
		
		// 삭제해야할 글 1개 내용 가져오기 
		GreetingBoardVO gbvo = service.getGreetingViewNoAddCount(seq);
		
		// 글작성자와 삭제하려는사람의 정보가 같아야함
		HttpSession session = request.getSession();
		MemberVO loginuser =(MemberVO) session.getAttribute("loginuser");
		String email = loginuser.getEmail();
		
		if (!email.equals(gbvo.getFk_email())) {
			// 남의 글 이라면
			String msg = "다른 사용자의 글은 삭제 불가능 합니다.";
			String loc = "javascript:history.back()";
			
			mv.addObject("msg", msg);
			mv.addObject("loc", loc);
			
			mv.setViewName("msg");
		} else {
			mv.addObject("seq",seq);
			mv.setViewName("sssGreetingBoard/destroyGreeting.tiles3");
		}
		
		
		return mv;
	}
	
	// 글 삭제 페이지 요청
	@RequestMapping(value="/destroyGreetingEnd.yo", method= {RequestMethod.GET})
	public ModelAndView destroyGreetingEnd(ModelAndView mv , HttpServletRequest request, GreetingBoardVO gbvo){
		
		try {
			String seq = request.getParameter("seq");
			String pw = request.getParameter("pw");
			
			gbvo.setSeq(seq);
			gbvo.setPw(pw);
			
			int n = service.destroyGreeting(gbvo);
			
			if (n==0) {
				mv.addObject("msg","비밀번호가 일치하지 않습니다.");
			} else {
				mv.addObject("msg","삭제완료");
			}
			mv.addObject("loc", request.getContextPath()+"/greetingList.yo");
			mv.setViewName("msg");
		} catch (Exception e) {
			e.printStackTrace();
		} 			
		return mv;
	}
	
	// 댓글 쓰기
	@RequestMapping(value = "/addGreetingComment.yo", method = { RequestMethod.POST }, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String login_addGreetingComment(HttpServletRequest request, HttpServletResponse response, GreetingCommentVO gcommentvo) {
		
		String jsonStr = null;
		// 댓글쓰기 
		int n;
		
		try {
			String groupno = request.getParameter("groupno");
			String depthno = request.getParameter("depthno");
			String fk_seq  = request.getParameter("fk_seq");

			gcommentvo.setGroupno(groupno);
			gcommentvo.setDepthno(depthno);
			gcommentvo.setFk_seq(fk_seq);
			
			n = service.addGreetingComment(gcommentvo);
			
			if (n == 1) {
				
				List<GreetingCommentVO> greetingCommentList = service.getGreetingCommentList(gcommentvo.getParentSeq());
				request.setAttribute("greetingCommentList", greetingCommentList);
				System.out.println("GreetingCommentList : "+greetingCommentList);
				
				JSONArray jsonArr = new JSONArray();
				
				for(GreetingCommentVO gcvo : greetingCommentList) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("name", gcvo.getName());
					jsonobj.put("content", gcvo.getContent());
					jsonobj.put("regDate", gcvo.getRegDate());
					jsonobj.put("depthno", gcvo.getDepthno());
					jsonobj.put("commentCount", gcvo.getCommentCount());
					
					jsonArr.put(jsonobj);					
				}
				jsonStr = jsonArr.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonStr;
		// 뷰단없이 바로 보여주기 위해서 json 사용하기 
	}
}































