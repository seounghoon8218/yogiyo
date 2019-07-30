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