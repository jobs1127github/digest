<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/page/myheader.jsp"%>
<meta name="author" content="http://www.softwhy.com/" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">

<title>全安通讯录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/page/dist/style/weui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/page/dist/example/example.css" />
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("a").click(function() {
			$("a").removeClass("weui_bar_item_on");
			$(this).addClass("weui_bar_item_on");
		});
	});
</script>
<body>
	<div class="weui_tab_bd">
		<iframe name="showmessage"
			style="width: 100%; height: 100%; border: 0px;"
			src="${pageContext.request.contextPath }/the_address_book2/contact_center.jsp"> </iframe>
	</div>
	<div class="weui_tabbar">
		<a href="${pageContext.request.contextPath }/the_address_book2/contact_center.jsp" class="weui_tabbar_item weui_bar_item_on"
			target="showmessage" style="text-decoration: none" >
			<div class="weui_tabbar_icon">
				<img src="${pageContext.request.contextPath }/page/dist/example/images/icon_nav_tab.png">
			</div>
			<p class="weui_tabbar_label">营销中心</p>
		</a> 
		
		<a href="${pageContext.request.contextPath }/the_address_book2/contact_factory.jsp" class="weui_tabbar_item "
			target="showmessage" style="text-decoration: none">
			<div class="weui_tabbar_icon">
				<img src="${pageContext.request.contextPath }/page/dist/example/images/icon_nav_panel.png">
			</div>
			<p class="weui_tabbar_label">新疆厂部</p>
		</a>
	</div>
	<script src="${pageContext.request.contextPath }/page/dist/example/zepto.min.js"></script>
	<script src="${pageContext.request.contextPath }/page/dist/example/router.min.js"></script>
	<script src="${pageContext.request.contextPath }/page/dist/example/example.js"></script>
</body>

</html>