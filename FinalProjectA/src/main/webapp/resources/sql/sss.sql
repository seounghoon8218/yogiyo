-- 테이블 전체조회
SELECT *
FROM user_tables;
/*
        TBL_CLIENT
        TBL_ORDERSUM
        TBL_SHOPCATEGORY
        TBL_SHOP
        TBL_MENUSPEC
        TBL_MENU
        TBL_REVIEW
        TBL_CART
        TBL_PAYMENT
        TBL_ORDERDETAIL
        TBL_MEMBER
*/
-- 시퀀스 전체조회
select * from user_sequences;

-- 제약조건 조회하기
SELECT * 
FROM USER_CONSTRAINTS 
where TABLE_NAME = 'TBL_CART';

-- 테이블의 PK 제약조건 삭제
ALTER TABLE TBL_cart DROP PRIMARY KEY;

-- 테이블의 FK 제약조건 삭제
ALTER TABLE tbl_menu DROP CONSTRAINT FK_tbl_menu_masterno;

-- 테이블의 UK 제약조건 삭제
ALTER TABLE TBL_MENU DROP CONSTRAINT UQ_TBL_MENU_MASTERNO;

-- 테이블의 FK 제약조건 추가
ALTER TABLE tbl_CART
ADD CONSTRAINT fk_tbl_CART FOREIGN KEY(menucode,masterno)
REFERENCES tbl_menu(menucode,masterno) on delete cascade;

-- 제약조건 추가하기
ALTER TABLE TBL_REVIEW
ADD CONTSRAINT PK_TBL_MENU PRIMARY KEY(REVIEWNO);

select * from tbl_menu;
desc tbl_menu;
/* 컬럼 */
-- menucode(메뉴코드)/ masterno(사업자번호)/ menuspeccode(메뉴카테고리코드)
-- menuname(메뉴명)/ menuprice(메뉴가격)/ menucomment(메뉴설명)/ menuimage(메뉴이미지)
/*  TBL_MENU
이름           널?       유형           
------------ -------- ------------ 
MENUCODE     NOT NULL VARCHAR2(30)   > SEQ_TBL_MENU_MENUCODE
MASTERNO     NOT NULL NUMBER(10)   
MENUSPECCODE NOT NULL VARCHAR2(30) 
MENUNAME     NOT NULL VARCHAR2(30) 
MENUPRICE    NOT NULL NUMBER(10)   
MENUCOMMENT  NOT NULL CLOB         
MENUIMAGE    NOT NULL VARCHAR2(20) 
*/

insert into tbl_menu(menucode, masterno, menuspeccode, menuname, menuprice, menucomment, menuimage)
values(seq_tbl_menu_menucode.nextval, 1, '1', '짜장면(test)', 7000, '존나 맛있음' , 'KKKtestImage.gif');
insert into tbl_menu(menucode, masterno, menuspeccode, menuname, menuprice, menucomment, menuimage)
values(seq_tbl_menu_menucode.nextval, 1, '2', '짜장면(test)', 7000, '존나 맛있음' , 'KKKtestImage.gif');
rollback;
select * from tbl_menu;

select * from TBL_SHOP;
select * from tbl_menuspec;

insert into tbl_menuspec(menuspeccode, menuspecname) values(1, '인기메뉴' );
insert into tbl_menuspec(menuspeccode, menuspecname) values(2, '주메뉴' );
insert into tbl_menuspec(menuspeccode, menuspecname) values(3, '사이드메뉴' );
commit;


-- 인기메뉴(1), 주메뉴(2), 사이드(3)

drop table TBL_menu purge;

-- TBL_MENU
create table tbl_menu
(
    menucode VARCHAR2(30) NOT NULL,          /* 메뉴코드 */
	masterno NUMBER(10) NOT NULL,            /* 사업자번호 */
	fk_menuspeccode VARCHAR2(30) NOT NULL,   /* 메뉴스펙코드 */
	menuname VARCHAR2(30) NOT NULL,          /* 메뉴이름 */
	menuprice NUMBER(10) NOT NULL,           /* 메뉴가격 */
	menucomment CLOB NOT NULL,               /* 메뉴설명 */
	menuimage VARCHAR2(20) NOT NULL          /* 메뉴사진 */ 
    , constraint PK_tbl_menu primary key(masterno, menucode)
    , constraint fk_tbl_menu_masterno foreign key(masterno) 
                references tbl_shop(masterno) on delete cascade
    , constraint fk_tbl_menu_menuspeccode foreign key (fk_menuspeccode)
                references tbl_menuspec(menuspeccode) on delete cascade
);

-- TBL_MENUSPEC
CREATE TABLE TBL_MENUSPEC (
	menuspeccode VARCHAR2(30) NOT NULL, /* 메뉴스펙코드 */
	menuspecname VARCHAR2(30) not null  /* 메뉴스펙이름 */
    ,constraint pk_tbl_menuspec_menuspeccode primary key(menuspeccode)
);

-- TBL_REVIEW
CREATE TABLE TBL_REVIEW (
	reviewno NUMBER(10) NOT NULL,            /* 리뷰번호 */
	fk_masterno NUMBER(10) not null,         /* 사업자번호 */
	fk_menucode VARCHAR2(30) not null,       /* 메뉴코드 */
	starpoint NUMBER(1) DEFAULT 1 NOT NULL,  /* 별점 */
	image VARCHAR2(30),                      /* 이미지 */
	comments CLOB NOT NULL,                  /* 코멘트 */
	email VARCHAR2(20) NOT NULL              /* 이메일 */
    ,constraint pk_tbl_review_reviewno primary key(reviewno)
    ,constraint fk_tbl_review foreign key(fk_masterno, fk_menucode)
                references tbl_menu(masterno, menucode) on delete cascade
);