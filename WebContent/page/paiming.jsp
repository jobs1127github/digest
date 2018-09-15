<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="myheader.jsp"%>
<meta name="author" content="http://www.softwhy.com/" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">

<title>全泰消化资讯</title>
<link rel="stylesheet" href="dist/style/weui.css" />
<link rel="stylesheet" href="dist/example/example.css" />
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
			src="boutique.jsp"> </iframe>
	</div>
	<div class="weui_tabbar">
		<a href="boutique.jsp" class="weui_tabbar_item weui_bar_item_on"
			target="showmessage" style="text-decoration: none" >
			<div class="weui_tabbar_icon">
				<img src="dist/example/images/icon_nav_tab.png">
			</div>
			<p class="weui_tabbar_label">精选资讯</p>
		</a> 
		
		<a href="allInformation.jsp" class="weui_tabbar_item "
			target="showmessage" style="text-decoration: none">
			<div class="weui_tabbar_icon">
				<img src="dist/example/images/icon_nav_panel.png">
			</div>
			<p class="weui_tabbar_label">全部资讯</p>
		</a>
	</div>
	<script src="dist/example/zepto.min.js"></script>
	<script src="dist/example/router.min.js"></script>
	<script src="dist/example/example.js"></script>
</body>

</body>
</html>