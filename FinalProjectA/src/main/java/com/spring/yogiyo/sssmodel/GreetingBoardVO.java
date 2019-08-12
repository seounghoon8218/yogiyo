package com.spring.yogiyo.sssmodel;

public class GreetingBoardVO {
	private String seq;			// 글번호
	private String fk_email; 	// 이메일
	private String name;		// 이름
	private String title; 	// 글제목
	private String content; 	// 글내용
	private String pw;			// 암호
	private String readCount;	// 글 조회수
	private String regDate;		// 등록일자
	private String status;		// 상태

	private String previousseq; // 이전글번호
	private String previoustitle; // 이전글제목
	private String nextseq; 	// 다음글번호
	private String nexttitle; // 다음글제목
	
	private String commentCount; // 댓글갯수

	public GreetingBoardVO() {}

	public GreetingBoardVO(String seq, String fk_email, String name, String title, String content, String pw,
			String readCount, String regDate, String status, String previousseq, String previoustitle, String nextseq,
			String nexttitle, String commentCount) {
		super();
		this.seq = seq;
		this.fk_email = fk_email;
		this.name = name;
		this.title = title;
		this.content = content;
		this.pw = pw;
		this.readCount = readCount;
		this.regDate = regDate;
		this.status = status;
		this.previousseq = previousseq;
		this.previoustitle = previoustitle;
		this.nextseq = nextseq;
		this.nexttitle = nexttitle;
		this.commentCount = commentCount;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getFk_email() {
		return fk_email;
	}

	public void setFk_email(String fk_email) {
		this.fk_email = fk_email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getReadCount() {
		return readCount;
	}

	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPreviousseq() {
		return previousseq;
	}

	public void setPreviousseq(String previousseq) {
		this.previousseq = previousseq;
	}

	public String getPrevioustitle() {
		return previoustitle;
	}

	public void setPrevioustitle(String previoustitle) {
		this.previoustitle = previoustitle;
	}

	public String getNextseq() {
		return nextseq;
	}

	public void setNextseq(String nextseq) {
		this.nextseq = nextseq;
	}

	public String getNexttitle() {
		return nexttitle;
	}

	public void setNexttitle(String nexttitle) {
		this.nexttitle = nexttitle;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	
	
	
	
}
