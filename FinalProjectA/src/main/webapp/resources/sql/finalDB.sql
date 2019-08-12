SELECT * FROM USER_TABLES;

select * from tbl_shop;

select * from tbl_shopcategory;

desc tbl_shop;


select RNO, masterno, shopname, shopcategorycode, addr, addr2, wdo,
		kdo, shoptel, shopimage, shoptime, minprice, paymethod, sanghoname,
		wonsanji, DISTANCE
		from
		(
		select rownum as RNO, masterno, shopname, shopcategorycode, addr, addr2,
		wdo, kdo, shoptel, shopimage, shoptime, minprice, paymethod,
		sanghoname, wonsanji, DISTANCE
		from
		(
		select masterno, shopname, shopcategorycode, addr, addr2, wdo, kdo, shoptel,
		shopimage, shoptime, minprice, paymethod, sanghoname, wonsanji
		, DISTNACE_WGS84(latitude, longitude, WDO, KDO) as DISTANCE
		from tbl_shop
		where (WDO between (#{latitude}-0.022) and (#{latitude}+0.022))
		and (KDO between (#{longitude}-0.022) and (#{longitude}+0.022))
		<if test='shopcategorycode != "0"'>
			and shopcategorycode = #{shopcategorycode}
		</if>
		)V
		)T
		where RNO between #{cnt} and (#{cnt}+4)
        
        
        
        
        
        
        
        
        select * from tbl_moonboard;