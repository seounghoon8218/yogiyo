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
        
        