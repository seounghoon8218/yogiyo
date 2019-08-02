package com.spring.yogiyo.ooomodel;

import org.springframework.web.multipart.MultipartFile;

public class oooReviewVO {
   
      private int reviewno;  
      private int fk_masterno;  
      private String fk_menucode; 
      private int starpoint;  
      private String image;
      private String comments;
      private String reviewRegDate;
      private String email;
      private MultipartFile attach;
      
      private String fileName;      
      private String orgFilename;   
      private String fileSize;
      
      public oooReviewVO() {}

      public oooReviewVO(int reviewno, int fk_masterno, String fk_menucode, int starpoint, String image, String comments,
         String reviewRegDate, String email, MultipartFile attach, String fileName, String orgFilename,
         String fileSize) {
      super();
      this.reviewno = reviewno;
      this.fk_masterno = fk_masterno;
      this.fk_menucode = fk_menucode;
      this.starpoint = starpoint;
      this.image = image;
      this.comments = comments;
      this.reviewRegDate = reviewRegDate;
      this.email = email;
      this.attach = attach;
      this.fileName = fileName;
      this.orgFilename = orgFilename;
      this.fileSize = fileSize;
   }

   public int getReviewno() {
      return reviewno;
   }

   public void setReviewno(int reviewno) {
      this.reviewno = reviewno;
   }

   public int getFk_masterno() {
      return fk_masterno;
   }

   public void setFk_masterno(int fk_masterno) {
      this.fk_masterno = fk_masterno;
   }

   public String getFk_menucode() {
      return fk_menucode;
   }

   public void setFk_menucode(String fk_menucode) {
      this.fk_menucode = fk_menucode;
   }

   public int getStarpoint() {
      return starpoint;
   }

   public void setStarpoint(int starpoint) {
      this.starpoint = starpoint;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getComments() {
      return comments;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }

   public String getReviewRegDate() {
      return reviewRegDate;
   }

   public void setReviewRegDate(String reviewRegDate) {
      this.reviewRegDate = reviewRegDate;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public MultipartFile getAttach() {
      return attach;
   }

   public void setAttach(MultipartFile attach) {
      this.attach = attach;
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
      
      
      
      
}