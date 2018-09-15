<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	request.setAttribute("path", request.getContextPath());
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",0); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="apple-itunes-app" content="app-id=429849944"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    
    <meta name="apple-touch-fullscreen" content="no" />
    <meta content="telephone=no" name="format-detection" />
    <meta name="apple-mobile-web-app-capable" content="no" />
    
    <link rel="stylesheet" href="${path}/page/css/public.css">
	<link rel="stylesheet" href="${path}/page/css/index.css">
	
    <script src="${path}/page/js/jquery-1.7.2.min.js"></script>
    <script src="${path}/javascript/httpService.js"></script>
    <script src="${path}/javascript/window.js"></script>