<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- ======= #24. tiles 내에 content만 있는 레이아웃3 페이지 만들기  ======= --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"  %>    

<tiles:insertAttribute name="content" /> <!-- 타일즈 쓰지 않을꺼라 그냥 내용물만 가져온다. -->


