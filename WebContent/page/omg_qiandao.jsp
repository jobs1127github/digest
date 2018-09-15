<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>签到抽奖</title>
<link rel="stylesheet" href="dist/style/weui.css" />
<link rel="stylesheet" href="dist/example/example.css" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="bootstrap/js/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="../javascript/jquery.alerts.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>
<script type="text/javascript" src="../javascript/httpService.js"></script>
<script type="text/javascript" src="../javascript/page.js"></script>
<script type="text/javascript" src="../javascript/validata.js"></script>
<script type="text/javascript" src="../javascript/jquery.datepick.js"></script>
<script type="text/javascript"
	src="../javascript/jquery.datepick-zh-CN.js"></script>
	<style type="text/css">
	body{
	background: #FFF;
	text-align: center;
	}
	</style>
</head>
<body >
	<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="panel panel-default">
				<div class="panel-heading" style="text-align: center;height: 88%">
					<img style='width:100%;' src='../img/omg.jpg'></img>
					<%
					String account = request.getParameter("userid");
					%>
					<span style="font-size:2em;color:#FF7F00;background: #fff">领奖凭证：<%=account%></span>
					<img style='width:100%;' src='../img/qtxhzj001.png'></img>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="background:#FF7F00;position: fixed;z-index:99999; bottom:0;left:0px;width: 100%">
<button type="button" style="font-size:5em; background:#FF7F00;color:#fff;width: 100%;height: 190px;" class="weui_btn_dialog default" >全泰消化专家</button></div>
	
	<script src="dist/example/zepto.min.js"></script>
	<script src="dist/example/router.min.js"></script>
	<script src="dist/example/example.js"></script>
</body>
</html>