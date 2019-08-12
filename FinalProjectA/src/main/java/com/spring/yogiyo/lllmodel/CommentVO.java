package com.spring.yogiyo.lllmodel;


// ===== #82. 댓글쓰기용 VO 생성하기
//            먼저 오라클에서 tblComment 테이블을 생성한다.
//            또한 tblBoard 테이블에 commentCount 컬럼을 추가한다. =====

public class CommentVO {
   
   private String seq;          // 댓글번호
   private String fk_email;    // 사용자ID
   private String name;         // 성명
   private String content;      // 댓글내용
   private String regDate;      // 작성일자
   private String parentSeq;    // 원게시물 글번호
   private String status;       // 글삭제여부
   
   public CommentVO() { }
   
   public CommentVO(String seq, String fk_email, String name, String content, String regDate, String parentSeq, String status) {
      this.seq = seq;
      this.fk_email = fk_email;
      this.name = name;
      this.content = content;
      this.regDate = regDate;
      this.parentSeq = parentSeq;
      this.status = status;
   }

   public String getSeq() {
      return seq;
   }

   public void setSeq(String seq) {
      this.seq = seq;
   }

   public String getfk_Email() {
      return fk_email;
   }

   public void setfk_Email(String fk_email) {
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
   
   
}