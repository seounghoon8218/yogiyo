show user;
--USER이(가) "FINAL1"입니다.

select * from user_tables;

select * from tbl_menu;

select * from TBL_MEMBER;

 
 drop table tbl_member purge;
 
 CREATE TABLE TBL_MEMBER (
    idx number(10) not null, /*회원번호*/
   email VARCHAR2(300) NOT NULL, /* 이메일 */
   pwd VARCHAR2(300) NOT NULL, /* 비밀번호 */
   name VARCHAR2(30) NOT NULL, /* 이름 */
   tel VARCHAR2(30) NOT NULL, /* 휴대전화 */
   lastlogindate DATE default sysdate, /* 최근접속일자 */
   registerday DATE default sysdate, /* 가입일자 */
   lastpwdchangdate DATE default sysdate, /* 최근비밀번호변경일 */
   status NUMBER(1) default 1,/* 상태 */
  
    CONSTRAINT PK_TBL_MEMBER_IDX PRIMARY KEY(idx),
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(email)
);



insert into tbl_member (idx,email,pwd,name,tel,lastlogindate,registerday,lastpwdchangdate,status)
values(seq_tbl_member.nextval, 'admin@gmail.com', 'qwer1234$', '관리자', '01012345678', default, default, default, default);
commit;

insert into tbl_member values(seq_tbl_member.nextval, '11@naver.com', 'qwer1234$', '테스트', '01012345678', default, default, default, default);

delete from tbl_member where email = '12@naver.com';
commit;

select *
from tbl_member
where email = 'ss@naver.com';

update tbl_member set pwd = '123', lastpwdchangdate = sysdate
      where email = '12@naver.com';

ALTER TABLE tbl_member rename column pw to pwd; 

SELECT * FROM USER_TABLES;
desc tbl_member;

insert into tbl_member values('admin@admin.com', 'qwer1234$', '관리자', '010-1111-2222', default, default, default, 1);
commit;
delete tbl_member;
desc tbl_shop;
desc tbl_member;
drop table tbl_member purge;
show user;
CREATE TABLE TBL_MEMBER (
    idx number(10) not null, /*회원번호*/
   email VARCHAR2(300) NOT NULL, /* 이메일 */
   pw VARCHAR2(300) NOT NULL, /* 비밀번호 */
   name VARCHAR2(30) NOT NULL, /* 이름 */
   tel VARCHAR2(30) NOT NULL, /* 휴대전화 */
   lastlogindate DATE, /* 최근접속일자 */
   registerday DATE, /* 가입일자 */
   lastpwchangdate DATE, /* 최근비밀번호변경일 */
   status NUMBER(1), /* 상태 */
    CONSTRAINT PK_TBL_MEMBER_IDX PRIMARY KEY(idx),
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(email)
);

create sequence seq_tbl_member
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


create sequence seq_tbl_member
 start with 1   
 increment by 1 -- 1 2 3 4 5  --1부터 증가해서 1씩올린다는 뜻.
 nomaxvalue
 nominvalue
 nocycle
 nocache;
select idx,email,pwd,name,tel
        , trunc( months_between(sysdate, lastLoginDate) ) AS lastlogindategap 
        , trunc( months_between(sysdate, lastpwdchangdate) ) AS pwdchangegap 
from tbl_member
where status = 1 and
      email = 'admin@gmail.com' and
      pwd = 'qwer1234$'
 
SELECT COUNT(*) FROM tbl_member
      WHERE
          email = 'admin@gmail.com'
          

insert into tbl_Member (idx,email, pwd, name, tel, status) values (seq_tbl_member.nextval, '9', PASSWORD(1234), '1', '1', 1) 


SELECT *
FROM tbl_member;

delete from tbl_member where email = 'admin@gmail.com';

update tbl_member set name = '관리자',pwd = 'qwer1234$', lastpwdchangdate = sysdate, tel = '01030031234' 
		where idx = 172

commit;
    
drop table tblComment purge;
drop table tblFreePage purge;    
      
create table tblFreePage
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
                                                 -- 원글(부모님)과 답변글은 동일한 groupno 를 가진다.
                                                 -- 답변글이 아닌 원글(부모글)인 경우 groupno 의 값은 groupno 컬럼의 최대값(max)+1 로 한다.
,fk_seq         number default 0      not null   -- fk_seq 컬럼은 절대로 foreign key가 아니다.!!!!!!!!!
                                                 -- fk_seq 컬럼은 자신의 글(답변글)이 있어서
                                                 -- 원글(부모글)이 누구인지에 대한 정보값이다.
                                                 -- 답변글쓰기에 있어서 답변글이라면 fk_seq 컬럼의 값은
                                                 -- 원글(부모글)의 seq 컬럼의 값을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,depthno        number default 0      not null   -- 답변글쓰기에 있어서 답변글 이라면
                                                 -- 원글(부모글)의 depthno+1 을 가지게 되며, 
                                                 -- 답변글이 아닌 원글일 경우 0을 가지도록 한다.
,fileName       varchar2(255)                    -- WAS(톰캣)에 저장될 파일명(20190725092715)
,orgFilename    varchar2(255)                    -- 진짜 파일명(강아지.png)    // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,fileSize       number                           -- 파일크기                                                 

,constraint  PK_tblFreePage_seq primary key(seq)
,constraint  FK_tblFreePage_email foreign key(fk_email) references tbl_member(email)
,constraint  CK_tblFreePage_status check( status in(0,1) )
);

drop sequence boardSeq;

create sequence freePageSeq
start with 1
increment by 1
nomaxvalue 
nominvalue
nocycle
nocache;

insert into tblBoard(seq, fk_email, name, subject, content, pw, readCount, regDate, status)
values(boardSeq.nextval, 'tkdrud444', '홍길동1', '홍길동1 입니다.', '안녕하세요? 홍길동1 입니다.', '1234', default, default, default);

select *
from tblFreePage;

update tblBoard set status = 1;

commit; 

----- **** 댓글 테이블 생성 **** -----
  
create table tblFreeComment
(seq           number               not null   -- 댓글번호
,fk_email     varchar2(20)         not null   -- 사용자ID
,name          varchar2(20)         not null   -- 성명
,content       varchar2(1000)       not null   -- 댓글내용
,regDate       date default sysdate not null   -- 작성일자
,parentSeq     number               not null   -- 원게시물 글번호
,status        number(1) default 1  not null   -- 글삭제여부
                                               -- 1 : 사용가능한 글,  0 : 삭제된 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_tblFreeComment_seq primary key(seq)
,constraint FK_tblFreeComment_email foreign key(fk_email)
                                    references tbl_member(email)
,constraint FK_tblFreeComment_parentSeq foreign key(parentSeq) 
                                      references tblFreePage(seq) on delete cascade
,constraint CK_tblFreeComment_status check( status in(1,0) ) 
);

drop sequence freecommentSeq;

create sequence commentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;      
      
      
      -------- *** 계층형 쿼리 *** --------
      /*
            이지훈                 1 level
              |
        장건희     김선규           2 level
          |         |
    이영진 박주희   손혜현           3 level
                    |
                우상경 박성훈       4 level
      */

    select *
    from employees;
    
    select level, employee_id, first_name, last_name, manager_id
    from employees
    start with employee_id = 109    -- 108 -- 101 -- 100 -- null
    connect by prior manager_id = employee_id;   -- prior 다음에 나오는 컬럼의 값은 start with 되어진 행의 컬럼값이다.
                -- 108
                -- 101 
                -- 100
                -- null
                
    select level, employee_id, first_name, last_name, manager_id            
    from employees
    start with employee_id = 100 -- 101 -- 108 -- 109 110 111 112
    connect by prior employee_id = manager_id;         
                    -- 100
                    -- 101
                    -- 108
                    -- 109 110 111 112 나오고 다시 101 나오는데 하나의 계층이 끝나서 다른계층을 보는것.
                
select *
from tblFreePage;