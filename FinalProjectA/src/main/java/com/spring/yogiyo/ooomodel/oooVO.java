package com.spring.yogiyo.ooomodel;

public class oooVO {
   
   private int masterno;      // 사업자번호   
   private String shopname;   // 매장이름
   private String shopcategorycode;   //업종카테고리코드
   private String addr;         // 주소1
   private String addr2;         // 주소2
   private String wdo;            //위도
   private String kdo;            //경도
   private String shoptel;         //전화번호
   private String shopimage;      //매장사진
   private String shoptime;      //업무시간
   private String minprice;      //최소주문금액
   private String paymethod;      //결제수단
   private String sanghoname;      //상호명
   private String wonsanji;      //원산지
   
   public oooVO() {}
   
   public oooVO(int masterno, String shopname, String shopcategorycode, String addr, String addr2, String wdo,
         String kdo, String shoptel, String shopimage, String shoptime, String minprice, String paymethod,
         String sanghoname, String wonsanji) {
      super();
      this.masterno = masterno;
      this.shopname = shopname;
      this.shopcategorycode = shopcategorycode;
      this.addr = addr;
      this.addr2 = addr2;
      this.wdo = wdo;
      this.kdo = kdo;
      this.shoptel = shoptel;
      this.shopimage = shopimage;
      this.shoptime = shoptime;
      this.minprice = minprice;
      this.paymethod = paymethod;
      this.sanghoname = sanghoname;
      this.wonsanji = wonsanji;
   }


   
   public int getMasterno() {
      return masterno;
   }


   public void setMasterno(int masterno) {
      this.masterno = masterno;
   }


   public String getShopname() {
      return shopname;
   }


   public void setShopname(String shopname) {
      this.shopname = shopname;
   }


   public String getShopcategorycode() {
      return shopcategorycode;
   }


   public void setShopcategorycode(String shopcategorycode) {
      this.shopcategorycode = shopcategorycode;
   }


   public String getAddr() {
      return addr;
   }


   public void setAddr(String addr) {
      this.addr = addr;
   }


   public String getAddr2() {
      return addr2;
   }


   public void setAddr2(String addr2) {
      this.addr2 = addr2;
   }


   public String getWdo() {
      return wdo;
   }


   public void setWdo(String wdo) {
      this.wdo = wdo;
   }


   public String getKdo() {
      return kdo;
   }


   public void setKdo(String kdo) {
      this.kdo = kdo;
   }


   public String getShoptel() {
      return shoptel;
   }


   public void setShoptel(String shoptel) {
      this.shoptel = shoptel;
   }


   public String getShopimage() {
      return shopimage;
   }


   public void setShopimage(String shopimage) {
      this.shopimage = shopimage;
   }


   public String getShoptime() {
      return shoptime;
   }


   public void setShoptime(String shoptime) {
      this.shoptime = shoptime;
   }


   public String getMinprice() {
      return minprice;
   }


   public void setMinprice(String minprice) {
      this.minprice = minprice;
   }


   public String getPaymethod() {
      return paymethod;
   }


   public void setPaymethod(String paymethod) {
      this.paymethod = paymethod;
   }


   public String getSanghoname() {
      return sanghoname;
   }


   public void setSanghoname(String sanghoname) {
      this.sanghoname = sanghoname;
   }


   public String getWonsanji() {
      return wonsanji;
   }


   public void setWonsanji(String wonsanji) {
      this.wonsanji = wonsanji;
   }
   
   
   
      
}