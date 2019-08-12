show user;

select * from user_tables;

select * from tbl_menu;
desc TBL_MENUSPEC;
select MENUSPECCODE, MENUSPECNAME from TBL_MENUSPEC;

select * from TBL_MEMBER;

select shopcategorycode from TBL_SHOP;

select masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel, shopimage, shoptime, minprice, paymethod, sanghoname, wonsanji
      from tbl_shop
      where shopcategorycode = '2';

select menucode, masterno, menuname, menuprice, filename, pop_menuspeccode
 from TBL_MENUSPEC S left JOIN tbl_menu M
 ON S.MENUSPECCODE = M.FK_MENUSPECCODE
 where MENUSPECCODE = 2;
 
 select menucode, masterno, menuname, menuprice, filename, pop_menuspeccode
       from tbl_menuspec s left join tbl_menu m
          on s.menuspeccode = m.fk_menuspeccode
       where menuspeccode = 2
       and masterno = 167
        
desc tbl_review;
select * from tbl_shop where masterno = 233;
select * from tbl_menu;
        
desc tbl_review;
        
        select * from tbl_review;
        
        select * from tbl_menu where masterno = 233;

select R.reviewno as reviewno, R.fk_masterno as fk_masterno, R.fk_menucode as fk_menucode, R.starpoint as starpoint, R.image as image, R.comments as comments, R.reviewRegDate as reviewRegDate, R.email as email, N.menuname as menuname
from tbl_review R , tbl_menu N
where R.fk_menucode = N.menucode; 
        select * from tbl_review where fk_masterno = 233;
        select sum(starpoint) from tbl_review where fk_masterno = 233;
        select nvl(trunc(sum(starpoint)/count(*),1),0)
      from tbl_review
      where fk_masterno = 233
        
        alter ;
        
desc TBL_shop;


select shopcategorycode
from tbl_shop;


select RNO, masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel, shopimage, shoptime, minprice, paymethod, sanghoname, wonsanji
        , DISTANCE
from
(
    select rownum as RNO, masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel, shopimage, shoptime, minprice, paymethod, sanghoname, wonsanji
                , DISTANCE
    from
    (        
        select masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel, shopimage, shoptime, minprice, paymethod, sanghoname, wonsanji, DISTNACE_WGS84(37.5632533, 126.529182, WDO, KDO) as DISTANCE
        from TBL_shop
        where shopcategorycode = 3
        and (WDO between 37.5632533-0.022 and 37.5632533+0.022)
        and (KDO between 126.9868325-0.022 and 126.9868325+0.022)
    )V
)T
where RNO between 1 and 5;        





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

select * from tbl_payment;
select * from tbl_review;
delete from tbl_review;
update tbl_payment set masterno = 233 where payno = 13;
commit;
desc tbl_review;
delete from tbl_payment where payno between 7 and 12;

alter table tbl_review modify (image varchar2(255));
alter table 
alter table tbl_payment add ( paymenuname varchar2(300) );
alter table tbl_payment add ( masterno number(10));


select R.reviewno as reviewno, R.fk_masterno as fk_masterno, R.fk_menucode as fk_menucode, R.starpoint as starpoint, R.image as image, R.comments as comments, to_char(R.reviewRegDate, 'yyyy-mm-dd') as reviewRegDate
          , R.email as email 
      from tbl_review R 
      where R.fk_masterno = 233 
      order by reviewno desc ; 
        

 ------------------------- 손혜현 게시판 시작 ---------------------------------------------------
 
 select * from user_tables;
 
 select *
 from TBL_MEMBER;
 
 desc tbl_member;
 
 select *
 from TBL_REVIEW;
 
 drop table KKKBoard purge;
 drop table KKKBoardComment purge;
 
 alter table KKKBoard
 drop constraint FK_KKKBoard_fk_userid;
 
 drop sequence KKKBoardSeq;
 
 drop sequence KKKCommentSeq;
 
 truncate table KKKBoardComment;
 
 alter table KKKBoard
 add CONSTRAINT FK_KKKBoard_fk_userid foreign key(fk_userid) references tbl_member(email) on delete cascade;
 
   SELECT * 
   FROM USER_CONSTRAINTS 
   WHERE TABLE_NAME = 'KKKBoard';
 
 DELETE FROM KKKBoard;
 
 ----------- 테이블락 제거 ------------------
 
 select a.sid, a.serial# ,a.status
from v$session a, v$lock b, dba_objects c 
where a.sid=b.sid and 
b.id1=c.object_id and 
b.type='TM' and 
c.object_name='tbl_member';

 
 -----------------------------------------
 
create table KKKBoard
(seq           number                 not null -- 글번호
,fk_userid     varchar2(20)           not null -- 사용자ID
,name          varchar2(30)           not null -- 글쓴이     -- 역정규화(=비정규화: 정규화에 위배된 것) 제3 정규화에 위배 
,subject       Nvarchar2(200)         not null -- 글제목     -- Nvarchar는 글자 수이다.(varchar는 byte)  영문이던 한글이던 똑같이 한글자로 본다.
,content       clob        not null -- 글내용     -- varchar2는 4000이 최대이고 그 이상일 경우 clob를 사용한다.(4GB까지 허용) 
,pw            varchar2(20)           not null -- 글암호
,readCount     number default 0       not null -- 글조회수
,regDate       date default sysdate   not null -- 글쓴시간
,status        number(1) default 1    not null -- 글삭제여부     1: 사용가능한 글, 0: 삭제된 글   -- delete도 가능하지만 백업을 계속 해주어야하기 때문에 업데이트로 해주려고 넣는다.
,commentCount  number default 0       not null -- 댓글의 갯수
,groupno       number                 not null -- 답변글쓰기에 있어서 그룹번호
                                               -- 원글(부모글)과 답변글은 동일한 groupno 를 가진다.
                                               -- 답변글이 아닌 원글(부모글)인 경우 groupno 의 값은 groupno 컬럼의 최대값(max)+1 을 가진다.
                                               
,fk_seq        number default 0       not null -- fk_seq 컬럼은 절대로 foreign key 가 아니다.!!!!!
                                               -- fk_seq 컬럼은 자시으니 글(답변글)에 있어서
                                               -- 원글(부모글)이 누구인지에 대한 정보값이다.
                                               -- 답변글쓰기에 있어서 답변글이라면 fk_seq 컬럼의 값은
                                               -- 원글(부모글)의 seq 컬럼의 값을 가지게 되며,
                                               -- 답변글이 아닌 원글일 경우 0을 가지도록 한다.
                                               
,depthno       number default 0       not null -- 답변글쓰기에 있어서 답변글 이라면
                                               -- 원글(부모글)의 depthno +1 을 가지게 되며,
                                               -- 답변글이 아닌 원글일 경우 0을 가지도록 한다.
                                               
,fileName       varchar2(255)                  -- WAS(톰캣)에 저장 될 파일명( )
,orgFilename    varchar2(255)                  -- 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할 때 사용되어지는 파일명
,fileSize       number                         -- 파일크기

,constraint PK_KKKBoard_seq primary key(seq)
,constraint FK_KKKBoard_fk_userid foreign key(fk_userid) references tbl_member(email) on delete cascade
,constraint CK_KKKBoard_status check( status in(0,1) )
);


create sequence KKKBoardSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;



create table KKKBoardComment
(seq           number               not null   -- 댓글번호
,fk_userid     varchar2(20)         not null   -- 사용자ID
,name          varchar2(20)         not null   -- 성명
,content       clob       not null   -- 댓글내용
,regDate       date default sysdate not null   -- 작성일자
,parentSeq     number               not null   -- 원게시물 글번호
,status        number(1) default 1  not null   -- 글삭제여부
                                               -- 1 : 사용가능한 글,  0 : 삭제된 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_KKKBoardComment_seq primary key(seq)
,constraint FK_KKKBoardComment_userid foreign key(fk_userid)
                                    references tbl_member(email) on delete cascade
,constraint FK_KKKBoardComment_parentSeq foreign key(parentSeq) 
                                      references KKKBoard(seq) on delete cascade
,constraint CK_KKKBoardComment_status check( status in(1,0) ) 
);


create sequence KKKCommentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


select *
from KKKBoard
order by seq asc;

desc KKKBoard;

select seq, fk_userid, name, subject, content,
       readCount, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss') as regDate
from KKKBoard
where status = 1
order by seq desc;

select RNO,
              seq, fk_userid, name, subject, readCount, regDate, commentCount, 
              groupno, fk_seq, depthno, 
              fileName, orgFilename, fileSize
      from
      (
         select rownum as RNO,
                 seq, fk_userid, name, subject, readCount, regDate, commentCount, 
                 groupno, fk_seq, depthno, 
                 fileName, orgFilename, fileSize
         from
         (
               select rownum, seq, fk_userid, name, subject, readCount, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss') AS regDate,
                     commentCount,
                     groupno, fk_seq, depthno,
                     fileName, orgFilename, fileSize
            from KKKBoard
            where status = 1          
              start with fk_seq = 0
              connect by prior seq = fk_seq              
             order siblings by groupno desc, seq asc
             
           )V
      )T
      where RNO between 1 and 5;
        
delete from KKKBoard;

commit;

 select previousseq, previoussubject, seq, fk_userid, name, subject, content, readCount, regDate, nextseq, nextsubject , commentCount
             , groupno, fk_seq, depthno, fileName, orgFilename, fileSize 
      from 
      (
          select RNO as seq
               , lag(RNO, 1) over(order by RNO desc ) as previousseq
               , lag(subject, 1) over(order by subject desc ) as previoussubject
               , RNO, fk_userid, name, subject, content, readCount , to_char(regDate, 'yyyy-mm-dd') as regDate
               , lead(RNO, 1) over(order by RNO desc ) as nextseq
               , lead(subject, 1) over(order by subject desc) as nextsubject
               , commentCount
               , groupno
               , fk_seq
               , depthno
            , fileName, orgFilename, fileSize
          from 
          (
             select  subject
                  , rownum as RNO               
                  , commentCount
                  , groupno
                  , fk_seq
                  , depthno
               , fileName, orgFilename, fileSize, regDate, readCount, content, name, fk_userid  
             from KKKBoard
             where status = 1
          )T
      )V;
      
      
      
      
      select previousseq, previoussubject, seq, fk_userid, name, subject, content, readCount, regDate, nextseq, nextsubject , commentCount
             , groupno, fk_seq, depthno, fileName, orgFilename, fileSize 
      from 
      (
          select lag(seq, 1) over(order by seq desc ) as previousseq
               , lag(subject, 1) over(order by subject desc ) as previoussubject
               , seq, fk_userid, name, subject, content, readCount , to_char(regDate, 'yyyy-mm-dd hh24:mi:ss') as regDate
               , lead(seq, 1) over(order by seq desc ) as nextseq
               , lead(subject, 1) over(order by subject desc) as nextsubject
               , commentCount
               , groupno
               , fk_seq
               , depthno
            , fileName, orgFilename, fileSize             
          from KKKBoard
          where status = 1
      )V;
      where V.seq = 2;

------------------------------ 손혜현 게시판 끝 ------------------------------------------        