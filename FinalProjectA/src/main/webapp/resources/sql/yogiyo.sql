show user;
--USER이(가) "FINAL1"입니다.

select * from user_tables;

select * from tbl_menu;

select * from TBL_MEMBER;

 
 drop table tbl_member purge;
 
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

insert into tbl_member (idx,email,pw,name,tel,lastlogindate,registerday,lastpwchangdate,status)
values(seq_tbl_member.nextval, 'admin@gmail.com', 'qwer1234$', '관리자', '01012345678', default, default, default, 1)

SELECT * FROM USER_TABLES;

<<<<<<< HEAD
desc tbl_member;

insert into tbl_member values('admin@admin.com', 'qwer1234$', '관리자', '010-1111-2222', default, default, default, 1);
commit;
delete tbl_member;
desc tbl_shop;

drop table tbl_member purge;

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
=======
create sequence seq_tbl_member
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
 

>>>>>>> branch 'master' of https://github.com/seounghoon8218/yogiyo.git

