SELECT * FROM USER_TABLES;

select * from user_constraint

SELECT * FROM USER_TABLES;

select * from tbl_shop;

select * from tbl_shopcategory;

select * from user_sequences;

desc tbl_shop;

select * from tbl_member;

select SEQ_TBL_SHOP_MASTERNO.nextval from dual;

alter table tbl_shop modify column paymethod varchar2(20) default '카드' NOT NULL;
ALTER TABLE tbl_shop MODIFY(shopimage varchar2(100));
drop table tbl_shop cascade;

drop table tbl_shop;
desc tbl_shop;
CREATE TABLE TBL_SHOP (
   masterno NUMBER(10) NOT NULL, /* 사업자번호 */
   shopname VARCHAR2(100) NOT NULL, /* 매장이름 */
   shopcategorycode VARCHAR2(30), /* 업종카테고리코드 */
   addr VARCHAR2(300) NOT NULL, /* 주소 */
   addr2 VARCHAR2(300) NOT NULL, /* 상세주소 */
   wdo VARCHAR2(50) NOT NULL, /* 위도 */
   kdo VARCHAR2(50) NOT NULL, /* 경도 */
   shoptel VARCHAR2(30) NOT NULL, /* 전화번호 */
   shopimage VARCHAR2(50) NOT NULL, /* 매장사진 */
   shoptime VARCHAR2(50) NOT NULL, /* 영업시간 */
   minprice VARCHAR2(50) NOT NULL, /* 최소주문금액 */
   paymethod VARCHAR2(50) default '카드' NOT NULL, /* 결제수단 */
   sanghoname VARCHAR2(100) NOT NULL, /* 상호명 */
   wonsanji CLOB NOT NULL /* 원산지정보 */
   ,constraint pk_tbl_shop_masterno primary key (masterno)
   ,CONSTRAINT FK_TBL_shop_shopcategorycode FOREIGN KEY ( shopcategorycode )
      REFERENCES TBL_SHOPCATEGORY ( shopcategorycode ) on delete cascade
);

select * from tbl_shop;

insert into tbl_shop(masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel, 


