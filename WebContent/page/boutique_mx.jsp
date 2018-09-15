<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="myheader.jsp"%>
<meta name="author" content="http://www.softwhy.com/" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<meta http-equiv="x-ua-compatible" content="ie=11" />
<title>精品推荐</title>
<link rel="stylesheet" href="dist/style/weui.css" />
<link rel="stylesheet" href="dist/example/example.css" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<style type="text/css">
a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:active {
	text-decoration: none;
}

.right {
	margin-left: 0em;
}
</style>

<style type="text/css">
table.imagetable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 0px;
	border-color: #999999;
	border-collapse: collapse;
	BORDER-BOTTOM:1PX #ddd SOLID;
}

table.imagetable th {
	background: #b5cfd2 url('img/cell-blue.jpg');
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}

table.imagetable td {
	border-width: 0px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
	background-repeat: repeat;
	background-size: 100% 100%;
	-moz-background-size: 100% 100%;
	-webkit-background-size: 100% 100%;
	BORDER-BOTTOM:1PX #ddd SOLID;
}
</style>
</head>
<!--  
<script type="text/javascript">
	$(document).ready(
	function() {
		var src = window.location.href;
		var n = src.indexOf("?");
		if(n>0) {   
		  var clenth="c".length;
		  var para=src.substr(n+1+clenth+1);   
		  alert(para);
		  $("#c_form_id").val(para); 
		  var params = $("#c_form").serialize();
		  sendRequest(pageContextPath + "/wechat/queryBoutique_c.do", params, function(jsonData) {
				var list=jsonData.list;
				$("#btinfo_list").html('');
				var btlist="";
				var len = list.length>=6?6:list.length;
				for (var i = 0; i < list.length; i++) {
					//btlist +="<a href='"+list[i].tile_img_url+"'>";
				    btlist += "<table class='imagetable'>";
					btlist += "<tr>";
					btlist += "<td style='text-align:left;width:30%;'>";
					btlist += "<img style='width:100%;' src='"+list[i].title_img_url+"'></img>";
					btlist += "</td>";
					btlist += "<td style='text-align:left;width:70%;'>";
					btlist += "<ul>";
					btlist += "<li style='font-size:12px;'>";
					btlist += list[i].title;
					btlist += "</li>";
					btlist +="<li style='font-size:12px;'>";
					btlist +=list[i].updated_time;
					btlist +="</li>";
					btlist +="</ul>";
					btlist +="</td>";
					btlist +="</tr>";
					btlist +="</table>";
					//btlist+="</a>";
				}
				$("#btinfo_list").html(btlist);
		  });
		}   
	});
</script>
-->

<body>
	<div id="tuijian" class="weui_panel" style="position: fix;width:100%; z-index: 999">
		<div class="weui_panel_bd">
			<div class="container-fluid">
				<div class="row-fluid">
					<form id="c_form" name="c_form">
						<input value="" id="c_form_id" name="c_form_id" type="hidden" /> 
						<div id="btinfo" align="center"></div>
						<div id="btinfo_tuijian" align="center" style="background-color:#ffffbb">
						<span style="font-size:24px;font-weight:bold">热门主题推荐</span>
						<div style="height: 8px;"></div>
						<table style="width:100%">
						<tr>
						<td style="text-align:center;width:33%;"><button type="button" class="btn btn-default"  style="font-size:18px; font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">食疗食谱</button></td>
						<td style="text-align:center;width:33%;"><button type="button" class="btn btn-default"  style="font-size:18px;font-weight:bold;color:#fff; border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">肠胃疾病</button></td>
						<td style="text-align:center;width:34%;"><button type="button" class="btn btn-default"  style="font-size:18px;font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">生活常识</button></td>
						</tr>
						</table>
						<br/>
						<table style="width:100%">
						<tr>
						<td style="text-align:center;width:33%;"><button type="button" class="btn btn-default"  style="font-size:18px;font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">季节养护</button></td>
						<td style="text-align:center;width:33%;"><button type="button" class="btn btn-default"  style="font-size:18px;font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">病友分享</button></td>
						<td style="text-align:center;width:34%;"><button type="button" class="btn btn-default"  style="font-size:18px;font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;" onclick="sousuo()">精彩活动</button></td>
						</tr>
						</table>
						<div style="height: 8px;"></div>
						</div>
						<div id="btinfo_list" align="center">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="dist/example/zepto.min.js"></script>
	<script src="dist/example/router.min.js"></script>
	<script src="dist/example/example.js"></script>

</body>
</html>