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
   lastpwchangdate DATE default sysdate, /* 최근비밀번호변경일 */
   status NUMBER(1), /* 상태 */
    CONSTRAINT PK_TBL_MEMBER_IDX PRIMARY KEY(idx),
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(email)
);

insert into tbl_member (idx,email,pwd,name,tel,lastlogindate,registerday,lastpwchangdate,status)
values(seq_tbl_member.nextval, 'admin@gmail.com', 'qwer1234$', '관리자', '01012345678', default, default, default, 1)

ALTER TABLE tbl_member rename column pw to pwd; 

create sequence seq_tbl_member
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

select idx,email,pwd,name,tel
        , trunc( months_between(sysdate, lastLoginDate) ) AS lastlogindategap 
        , trunc( months_between(sysdate, lastpwchangdate) ) AS pwdchangegap 
from tbl_member
where status = 1 and
      email = 'admin@gmail.com' and
      pwd = 'qwer1234$'
 


