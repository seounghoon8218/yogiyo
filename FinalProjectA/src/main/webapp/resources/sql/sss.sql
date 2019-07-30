-- 테이블 전체조회
SELECT *
FROM user_tables;

select * from TBL_CLIENT;    
select * from TBL_ORDERSUM;       
select * from TBL_SHOPCATEGORY;       
select * from TBL_SHOP;        
select * from TBL_MENUSPEC;     
select * from TBL_MENU;       
select * from TBL_REVIEW;     
select * from TBL_CART;   
select * from TBL_PAYMENT;      
select * from TBL_ORDERDETAIL;  
select * from TBL_MEMBER;      

desc TBL_SHOPCATEGORY;

-- 시퀀스 전체조회
select * from user_sequences;

-- 컬럼추가
alter table tbl_menu add(pop_menuspeccode varchar2(2) default 0);
-- 컬럼 삭제
ALTER TABLE tbl_menu DROP COLUMN pop_menuspeccode;
rollback;
DESC TBL_SHOP;
-- 제약조건 조회하기
SELECT * 
FROM USER_CONSTRAINTS 
where TABLE_NAME = 'TBL_MENU';

-- 테이블의 PK 제약조건 삭제
ALTER TABLE TBL_cart DROP PRIMARY KEY;

-- 테이블의 FK 제약조건 삭제
ALTER TABLE tbl_menu DROP CONSTRAINT FK_tbl_menu_masterno;

-- 테이블의 UK 제약조건 삭제
ALTER TABLE TBL_MENU DROP CONSTRAINT UQ_TBL_MENU_MASTERNO;

-- 테이블의 FK 제약조건 추가
ALTER TABLE tbl_menu
ADD CONSTRAINT fk_tbl_menu_masterno FOREIGN KEY(fk_masterno)
REFERENCES tbl_shop(MASTERNO) on delete cascade;

-- 제약조건 추가하기
ALTER TABLE TBL_REVIEW
ADD CONTSRAINT PK_TBL_MENU PRIMARY KEY(REVIEWNO);

-- 컬럼값 수정
update table set shopcategorycode = 3 where masterno = 88;

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

select * from tbl_menu;

select * from TBL_SHOP;
select * from tbl_menuspec;

insert into tbl_menuspec(menuspeccode, menuspecname) values(1, '인기메뉴' );
insert into tbl_menuspec(menuspeccode, menuspecname) values(2, '주메뉴' );
insert into tbl_menuspec(menuspeccode, menuspecname) values(3, '사이드메뉴' );
insert into tbl_menuspec(menuspeccode, menuspecname) values(4, '테스트' );
commit;
rollback;

select *
from tbl_menu;

select *
from TBL_MENUSPEC;


desc tbl_menu;

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

desc tbl_shop
select * from tbl_shop order by masterno desc;
select * from tbl_menu;
select * from TBL_MENUSPEC;

commit;

-- 메뉴등록
insert into tbl_menu(menucode, masterno, fk_menuspeccode, menuname, menuprice, menucomment, fileName, orgFilename, fileSize, fk_shopcategory,fk_masterno, pop_menuspeccode)
values(SEQ_TBL_MENU_MENUCODE.nextval, 88, 1, '짬뽕', 9000, '해산물듬뿍 짬뽕', '20190727874648684522468.jpg', '짬뽕.jpg', 232934, 3, 88, 1);

-- 주메뉴 또는 서브메뉴 이면서 인기메뉴
select *
from tbl_menu
where pop_menuspeccode = 1 and fk_menuspeccode = 1;

-- 주메뉴
select *
from tbl_menu
where fk_menuspeccode = 1;

rollback;
-- 매장등록
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'북경반점-남대문점',3,'서울특별시 중구 회현동 남대문시장2가길','10-10 1층','37.5590652','126.9770026','02-773-1916','북경반점.jpg','10:30 - 21:00','10,000','카드','신북경반점','* 쌀:국내산 *콩:국내산 *닭:국내산 *돼지고기:국내산 *소고기: 호주산 *김치:중국산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'남경-남대문시장점',3,'서울 중구 남대문시장길 29','2층 남경','37.4874941','126.9302821','02-777-2379','남경.jpg','	07:30 - 06:59','10,000','카드','	남경','쌀 국내산 돼지고기 국내산 배추김치 중국산 단무지 중국산소고기 호주산 닭 브라질산. 밀가루 호주산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'명동관-본점',3,'서울 중구 명동10길 7-9','2층 명동관','37.5635214','126.9857366','02-318-4840','명동관.jpg','	10:00 - 20:00','5,000','카드','명동관','쌀 국내산 돼지고기 국내산 배추김치 중국산 단무지 중국산소고기 호주산 닭 브라질산. 밀가루 호주산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'홍리마라탕-종로점',3,'서울 종로구 종로40가길 19','1층','37.5702272','127.0055713','1661-4533','홍리마라탕.jpg','	11:00 - 00:30','10,000','카드','홍리마라탕','소고기 : (목심, 미국산 / 검정양, 국내산 / 깐양, 호주산) 돼지고기: (등심, 국내산 / 프레스햄, 덴마크산 / 닭고기 돼지고기, 비엔나소세지, 국내산) 양고기: (호주산) 갈치, 오징어, 닭고기: 피쉬볼, (중국산) 두부-콩: (푸주, 건두부, 중국산 연두부, 외국산) 쌀: (국내산),', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'중국관-남대문로점',3,'서울 중구 남대문시장길 25-8','1층','37.5605491','126.9775087','02-755-7116','중국관.jpg','11:00 - 21:00','10,000','카드','중국관','*쌀:국내산,*돼지고기:국내산,*오징어:국내산,*소고기:호주산,*김치:수입산,*닭고기:국내산,*두부:미국산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'남경-중구점',3,'서울 중구 세종대로18길 24-8','문왕빌딩 1층','37.5634304','126.9784257','02-756-2286','남경.jpg','10:40 - 21:30','10,000','카드','남경','쌀:국내산 돼지고기:국내산 닭고기:브라질 김치:중국산 소고기:호주산 단무지:국내산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'이것이진짜짬뽕이다',3,'서울 종로구 율곡로 169','1층','37.576025','126.9976522','02-763-0506','이진짬.jpg','10:30 - 21:00','10,000','카드','진짜루','오징어,낚지(베트남산,태국산) 쇠고기(호주산,미국산) 돼지고기(국내산) 닭고기(국내산,미국산) 쌀(국내산) 두부(국내산) 배추김치(중국산) 고추가루(중국산)', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'흑룡강성-관악구점',3,'서울 관악구 쑥고개로 68-1','1층','37.4793278','126.9439704','02-875-8808','흑룡강성.jpg','10:30 - 20:50','10,000','카드','흑룡강성','돼지고기 : 국내산 닭고기:브라질산 소고기:호주산 쌀:국내산 콩(콩국수):국내산 두부:미국산 꽃게:중국산 오징어:국내산/뉴질랜드산 김치:국내산/중국산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'다래성24시',3,'서울 관악구 은천로 83-1','1층','37.486975','126.9445797','02-871-4025','다래성.jpg','11:00 - 10:59','10,000','카드','다래성',''쌀:국내산 돼지고기:국내산 닭고기:브라질 김치:중국산 소고기:호주산 단무지:국내산'', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'홍푸차이',3,'서울 관악구 남부순환로 1513','1층','37.4828282','126.9186448','02-865-9131','홍푸차이.jpg','10:30 - 20:50','10,000','카드','홍푸차이','쌀(국내산),돼지고기(국내산),닭가슴(국내산),오징어(원양산),소고기(호주산),닭고기(브라질산),단무지(중국산),고추가루(중국산)', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'신대원각-관악점',3,'서울 관악구 봉천로 287-1','1층','37.4873738','126.9331987','02-877-3228','신대원각.jpg','10:30 - 23:00','8,000','카드','신대원각','쌀 : 국내산 돼지고기 : 국내산 소고기 : 미국산,호주산 닭고기 : 미국산 김치 : 중국산 배추 : 국내산 고추가루 : 중국산 오징어 : 국내산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'충칭마라훠궈',3,'서울 관악구 관악로14길 65','1층','37.4784736','126.95614','02-6081-8818','충칭.jpg','11:00 - 00:30','16,000','카드','충칭마라훠궈','쌀:국내산 소고기,양고기:호주산 닭:브라질 배추:국내산 사천고추:중국산 고추가루:국내산 밀가루:국내산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'영화루-관악점',3,'서울 관악구 남부순환로161길 30','1층','37.4842632','126.9205768','02-856-2492','영화루.jpg','09:00 - 20:30','8,000','카드','영화루','쌀:국내산 돼지고기:국내산 고춧가루:중국산 김치:중국산', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'취향저격안심탕수육-신림점',3,'서울 관악구 남부순환로 1561','지하3 1층','37.4837236','126.9239473','02-6205-8228','취향저격안심탕수육.jpg','14:00 - 02:30','14,000','카드','케이푸드(K.food)','쌀(국내산), 김치(국내산), 돼지고기안심(국내산), 청양고추(국내산), 무(국내산)', '','','');
insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'신림동만리장성',3,'서울 관악구 관악로 146','2층','37.478214','126.9525562','02-886-1117','만리장성.jpg','10:00 - 20:40','10,000','카드','	24시만리장성','돼지고기: 국내산 소 고 기: 호주산 닭 고 기: 브라질산 김 치: 중국산 쌀 : 국내산 오 징 어: 국내산, 베트남 쭈 꾸 미: 베트남 해파리채: 중국산', '','','');

delete tbl_shop where masterno = 87;
commit;

select menuspeccode, menuspecname
from tbl_menuspec
order by menuspeccode asc;
        
select shopimage
from tbl_shop;

select *
from tbl_shop
where shopcategorycode = 9;

select *
from tbl_menu;

select * from tbl_shop where shopcategorycode = 3;
select * from tbl_shopcategory;

select *
from tbl_menu;



select * from tbl_shop where shopcategorycode = 4;
commit;
delete from tbl_menu where masterno = 176;
commit;

update tbl_menu set fk_shopcategory = '9' where masterno = 227;