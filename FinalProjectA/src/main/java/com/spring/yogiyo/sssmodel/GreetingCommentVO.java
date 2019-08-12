package com.spring.yogiyo.sssmodel;


// ===== #82. 댓글쓰기용 VO 생성하기
//            먼저 오라클에서 tblComment 테이블을 생성한다.
//            또한 tblBoard 테이블에 commentCount 컬럼을 추가한다. =====

public class GreetingCommentVO {
	
	private String seq;          // 댓글번호
	private String fk_email;    // 사용자ID
	private String name;         // 성명
	private String content;      // 댓글내용
	private String regDate;      // 작성일자
	private String parentSeq;    // 원게시물 글번호
	private String status;       // 글삭제여부
	
	private String groupno;
	private String fk_seq;
	private String depthno;
	private String commentCount;
	public GreetingCommentVO() { }
	
	public GreetingCommentVO(String seq, String fk_email, String name, String content, String regDate, String parentSeq, String status,
							 String groupno, String fk_seq, String depthno, String commentCount) {
		this.seq = seq;
		this.fk_email = fk_email;
		this.name = name;
		this.content = content;
		this.regDate = regDate;
		this.parentSeq = parentSeq;
		this.status = status;
		this.groupno = groupno;
		this.fk_seq = fk_seq;
		this.depthno = depthno;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(String parentSeq) {
		this.parentSeq = parentSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getFk_seq() {
		return fk_seq;
	}

	public void setFk_seq(String fk_seq) {
		this.fk_seq = fk_seq;
	}

	public String getDepthno() {
		return depthno;
	}

	public void setDepthno(String depthno) {
		this.depthno = depthno;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	
}