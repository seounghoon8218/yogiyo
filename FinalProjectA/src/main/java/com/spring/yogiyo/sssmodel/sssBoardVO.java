package com.spring.yogiyo.sssmodel;

import org.springframework.web.multipart.MultipartFile;

public class sssBoardVO {
	private String seq;			// 글번호
	private String fk_email; 	// 이메일
	private String name;		// 이름
	private String subject; 	// 글제목
	private String content; 	// 글내용
	private String pw;			// 암호
	private String readCount;	// 글 조회수
	private String regDate;		// 등록일자
	private String status;		// 상태

	private String previousseq; // 이전글번호
	private String previoussubject; // 이전글제목
	private String nextseq; 	// 다음글번호
	private String nextsubject; // 다음글제목
	
	private String commentCount; // 댓글갯수

	private String groupno;
	private String fk_seq;
	private String depthno;

	private String fileName;	// WAS에 저장될 파일명 (2019072509275164615846484648464.png)
	private String orgFilename; // 진짜 파일명 > 사용자가 파일을 업로드 하거나 파일을 다운로드할때 사용되어지는 파일명
	private String fileSize; 	// 파일크기
	
	private MultipartFile attach; 
	
	public sssBoardVO() {}

	public sssBoardVO(String seq, String fk_email, String name, String subject, String content, String pw,
			String readCount, String regDate, String status, String previousseq, String previoussubject, String nextseq,
			String nextsubject, String commentCount, String groupno, String fk_seq, String depthno, String fileName,
			String orgFilename, String fileSize) {
		super();
		this.seq = seq;
		this.fk_email = fk_email;
		this.name = name;
		this.subject = subject;
		this.content = content;
		this.pw = pw;
		this.readCount = readCount;
		this.regDate = regDate;
		this.status = status;
		this.previousseq = previousseq;
		this.previoussubject = previoussubject;
		this.nextseq = nextseq;
		this.nextsubject = nextsubject;
		this.commentCount = commentCount;
		this.groupno = groupno;
		this.fk_seq = fk_seq;
		this.depthno = depthno;
		this.fileName = fileName;
		this.orgFilename = orgFilename;
		this.fileSize = fileSize;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getfk_email() {
		return fk_email;
	}

	public void setfk_email(String fk_email) {
		this.fk_email = fk_email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getPrevioussubject() {
		return previoussubject;
	}

	public void setPrevioussubject(String previoussubject) {
		this.previoussubject = previoussubject;
	}

	public String getNextseq() {
		return nextseq;
	}

	public void setNextseq(String nextseq) {
		this.nextseq = nextseq;
	}

	public String getNextsubject() {
		return nextsubject;
	}

	public void setNextsubject(String nextsubject) {
		this.nextsubject = nextsubject;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOrgFilename() {
		return orgFilename;
	}

	public void setOrgFilename(String orgFilename) {
		this.orgFilename = orgFilename;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public MultipartFile getAttach() {
		return attach;
	}

	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}
	
	
	
	
}
