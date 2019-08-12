---- *** FINAL Project DB *** -------
show user;
/*
    == 작성자명 : 박성훈 
    == 작성일자 : 2019.07.12 12:10
*/

select * from user_tables;
select * from user_sequences;

desc TBL_CART;
select * from TBL_CART;
select * from TBL_PAYMENT;

select * from TBL_CART;

delete from TBL_CART where email = 'psh7603zz@naver.com';

select * from TBL_MEMBER where email like '%'||'admin'||'%';

select * from tbl_shop where shopname like '%'||'CU'||'%';


-- 메뉴등록
insert into tbl_menu(menucode, masterno, fk_menuspeccode, menuname, menuprice, menucomment, fileName, orgFilename, fileSize, fk_shopcategory,fk_masterno, pop_menuspeccode)
values(SEQ_TBL_MENU_MENUCODE.nextval, 216, 1, '후라이드', 9000, '바삭바삭', '20180727874648684522468.jpg', '네네종로후라이드.jpg', 232934, 3, 216, 1);


commit;
-- cart
SELECT orderno,V.masterno AS masterno,V.menucode AS menucode,V.menuname AS menuname,V.menuqty AS menuqty,V.menuprice AS menuprice,shopname,shopimage,M.filename AS filename , V.email AS email
FROM
(
SELECT orderno,C.masterno AS masterno,menucode,menuname,menuqty,menuprice,S.shopname AS shopname,S.shopimage AS shopimage, C.email AS email 
FROM tbl_cart C JOIN tbl_shop S
    ON C.masterno = S.masterno
)V JOIN tbl_menu M
ON V.menucode = M.menucode
WHERE email = 'psh7603zz@naver.com';

desc TBL_PAYMENT;
desc TBL_CART;
desc TBL_ORDERDETAIL;
desc TBL_MENU;

desc TBL_SHOPCATEGORY;
select * from TBL_SHOPCATEGORY;

select * from TBL_SHOP;
desc TBL_SHOP;


insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'대게향-무교점',5,'서울특별시 중구 무교로 32','효령빌딩 지하2층 대게향','37.568715','126.9795727','02-755-5522','대게향-무교점.jpg','   09:00 - 22:00','10000','카드','대게향','국내산만을 이용합니다.');

insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'궁-서소문점',5,'서울 중구 세종대로9길 52','서소문동 120-21','37.5619073','126.9731856','02-778-7210','궁-서소문점.jpg','   14:00 - 22:00','10000','카드','궁','돈가스-국내산등심,쌀-국내산,야채류-국내산,김치-중국산,단무지-국내산');

insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'이자와-명동점',5,'서울 중구 명동10길 19-3','명동2가 3-3 지하1층','37.56303279999999','126.9856018','02-777-1529','이자와-명동점.jpg','10:00 - 22:00','20000','카드','이자와','규카츠 정식 - 규카츠(소고기/채끝):뉴질랜드, 쌀:국내산, 양배추:국내산, 생와사비:중국');

insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'안녕숯불 종로점',5,'서울 종로구 삼일대로 446-6','낙원동 13 긍정빌딩 1층','37.57420260000001','126.9877733','02-763-8292','안녕숯불-종로점.jpg','11:00 - 21:00','15000','카드','안녕숯불','쌀국내산<br>돼지고기 국내산<br>돈까스돼지고기 미국산<br>닭고기 브라질산<br>채소 국내산');

insert into TBL_SHOP
values(SEQ_TBL_SHOP_MASTERNO.nextval,'회산물식당',5,'서울특별시 중구 남대문로2가','지훈빌딩1층','37.5638735','126.9820656','02-726-8332','회산물식당.jpg','10:00 - 21:00','17000','카드','회산물식당','국내산만을 사용합니다.');

commit;

select * from TBL_SHOP;
select * from TBL_SHOPCATEGORY;

select C.shopcategoryname as shopcategoryname
            , count(*) as CNT
              , round(count(*)/(select count(*) from TBL_SHOP)*100,2) as PERCNT
      from TBL_SHOPCATEGORY C left join TBL_SHOP S
      on S.shopcategorycode = C.shopcategorycode
      group by C.shopcategoryname
        order by percnt desc;
--------------------
select V.shopcategoryname
      ,count(*) as CNT
      , round(count(*)/(select count(*) from tbl_payment)*100,2) as PERCNT
from
(
select C.shopcategorycode , C.shopcategoryname , S.masterno ,S.shopname
from TBL_SHOPCATEGORY C left join TBL_SHOP S
      on S.shopcategorycode = C.shopcategorycode
)V right join tbl_payment P
on V.masterno = P.masterno
group by V.shopcategoryname
order by percnt desc;
--====
select rno , shopname , cnt , percnt
from
(
    select rownum as rno , shopname , cnt , percnt
    from 
    (
        select V.shopname, count(*) as cnt
              , round(count(*)/( select count(*)
                                    from (
                                    select C.shopcategorycode , C.shopcategoryname , S.masterno ,S.shopname
                                    from TBL_SHOPCATEGORY C left join TBL_SHOP S
                                            on S.shopcategorycode = C.shopcategorycode
                                    )W right join tbl_payment P
                                    on W.masterno = P.masterno
                                    where shopcategoryname = '분식' 
                                )*100,2) as percnt
                
        from (
        select C.shopcategorycode , C.shopcategoryname , S.masterno ,S.shopname
        from TBL_SHOPCATEGORY C left join TBL_SHOP S
                on S.shopcategorycode = C.shopcategorycode
        )V right join tbl_payment P
        on V.masterno = P.masterno
        where shopcategoryname = '분식'
        group by v.shopname
        order by percnt desc
    ) X
) Y
where rno between 1 and 3;
-------------


select * from tbl_payment;

select * from tbl_member;
desc tbl_member;

select * from user_sequences;        
        
declare 
    v_email VARCHAR2(300) := 'psh7603zz';
    v_name VARCHAR2(30) := '박성훈';
 begin
     for i in 1..150 loop
        insert into tbl_member(IDX, EMAIL, PWD,NAME,TEL,LASTLOGINDATE,REGISTERDAY,LASTPWDCHANGDATE,STATUS) 
        values(SEQ_TBL_MEMBER.nextval , v_email || i || '@naver.com', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382' ,v_name||i , '01012345678' , default , default , default , default);
     end loop;
 end;        
        

----
--1
CREATE OR REPLACE FUNCTION RADIANS(nDegrees IN NUMBER) 
RETURN NUMBER DETERMINISTIC 
IS
BEGIN
  /*
  -- radians = degrees / (180 / pi)
  -- RETURN nDegrees / (180 / ACOS(-1)); but 180/pi is a constant, so...
  */
  RETURN nDegrees / 57.29577951308232087679815481410517033235;
END RADIANS;

--2
create or replace function DISTNACE_WGS84( H_LAT in number, H_LNG in number, T_LAT in number, T_LNG in number)
return number deterministic
is
begin
  return ( 6371.0 * acos(  
          cos( radians( H_LAT ) )*cos( radians( T_LAT /* 위도 */ ) )
          *cos( radians( T_LNG /* 경도 */ )-radians( H_LNG ) )
          +
          sin( radians( H_LAT ) )*sin( radians( T_LAT /* 위도 */ ) )        
         ));
end DISTNACE_WGS84;
 
select DISTNACE_WGS84(33.504274, 126.529182, 33.524383, 126.544333) from dual;
/* 결과 2.64059773979495999846417249534463003211 */


----- 내위치에서 2km 범위의 가게 불러오기
select * 
from 
(
    select masterno, shopname, WDO, KDO
        , DISTNACE_WGS84(37.5632533, 126.529182, WDO, KDO) as DISTANCE
    from tbl_shop
    where (WDO between 37.5632533-0.019 and 37.5632533+0.019)
        and (KDO between 126.9868325-0.022 and 126.9868325+0.022)
    order by DISTANCE
)TMP 
where rownum < 10;

-----

select * from tbl_shop;
 
insert into tbl_payment(payno,addr1,addr2,tel,yocheong,totalprice,paymethod,email)
      values(seq_tbl_payment_payno.nextval,'서울','11번지','010-3333-3333','맛있게','35000','card','psh@psh' );
select * from tbl_payment;


--------

select * from TBL_MEMBER;

select name, email, tel,status
from
(
    select rownum as rno , name, email, tel,status
    from
    (
        select name,email,tel ,status
        from tbl_member
        where email like '%'||''||'%'
        order by idx asc
    ) V
)T
where rno between 1 and 5;

-------------------------------------------------------

-- * 댓글 및 답변형 파일첨부가 있는 게시판

create table pppBoard
(seq            number                not null   -- 글번호
,fk_email      varchar2(20)          not null   -- 사용자ID
,name           Nvarchar2(20)         not null   -- 글쓴이
,subject        Nvarchar2(200)        not null   -- 글제목
,content        Nvarchar2(2000)       not null   -- 글내용    -- clob
,pw             varchar2(20)          not null   -- 글암호
,readCount      number default 0      not null   -- 글조회수
,regDate        date default sysdate  not null   -- 글쓴시간
,status         number(1) default 1   not null   -- 글삭제여부  1:사용가능한글,  0:삭제된글 
,commentCount   number default 0      not null   -- 댓글의 갯수
,groupno        number                not null   -- 답변글쓰기에 있어서 그룹번호
                                                 -- 원글(부모글)과 답변글은 동일한 groupno 를 가진다.
                                                 -- 답변글이 아닌 원글(부모글)인 경우 groupno 의 값은 groupno 컬럼의 최대값(max)+1 로 한다.

,fk_seq         number default 0      not null   -- fk_seq 컬럼은 절대로 foreign key가 아니다.
                                                 -- fk_seq 컬럼은 자신의 글(답변글)에 있어서 
                                                 -- 원글(부모글)이 누구인지에 대한 정보값이다.
                                                 -- 답변글쓰기에 있어서 답변글이라면 fk_seq 컬럼의 값은
                                                 -- 원글(부모글)의 seq 컬럼의 값을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,depthno        number default 0      not null   -- 답변글쓰기에 있어서 답변글 이라면
                                                 -- 원글(부모글)의 depthno+1 을 가지게 되며
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,fileName       varchar2(225)                    -- WAS(톰켓)에 저장될 파일명(2019072509271512312.png)
,orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png) // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명
,fileSize       number                           -- 파일크기
,constraint  PK_pppBoard_seq primary key(seq)
,constraint  FK_pppBoard_userid foreign key(fk_email) references TBL_MEMBER(email)
,constraint  CK_pppBoard_status check( status in(0,1) )
);

create sequence pppboardSeq
start with 1
increment by 1
nomaxvalue 
nominvalue
nocycle
nocache;      
            
select * from pppBoard;         
            
create table pppComment
(seq           number               not null   -- 댓글번호
,fk_email     varchar2(20)         not null   -- 사용자ID
,name          varchar2(20)         not null   -- 성명
,content       varchar2(1000)       not null   -- 댓글내용
,regDate       date default sysdate not null   -- 작성일자
,parentSeq     number               not null   -- 원게시물 글번호
,status        number(1) default 1  not null   -- 글삭제여부
                                               -- 1 : 사용가능한 글,  0 : 삭제된 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_tblComment_seq primary key(seq)
,constraint FK_tblComment_userid foreign key(fk_email)
                                    references TBL_MEMBER(email)
,constraint FK_tblComment_parentSeq foreign key(parentSeq) 
                                      references pppBoard(seq) on delete cascade
,constraint CK_tblComment_status check( status in(1,0) ) 
);

create sequence pppcommentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;