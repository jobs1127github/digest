<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
.mybutton{
border:1px solid #fff ;font-size:18px; font-weight:bold;color:#fff;border-radius:15px 15px 15px 15px;width:88%;height:48px;background:#ffbb00;
}
</style>
<style type="text/css">
  body,div,ul,li,a,img{margin: 0;padding: 0;}
  ul,li{list-style: none;}
  a{text-decoration: none;}
  #wrapper1{position: relative;margin:3px auto;width: 300px;height: 200px;}
  #banner1{position:relative;width: 300px;height: 200px;overflow: hidden;}
  .imgList1{position:relative;width:2000px;height:200px;z-index: 10;overflow: hidden;}
  .imgList1 li{float:left;display: inline;}
  
  #wrapper{position: relative;margin:0px auto;width:400px ;height: 200px;}
  #banner{position:relative;width:400px;height: 200px;overflow: hidden;}
  .imgList{position:relative;width:2000px;height:200px;z-index: 10;overflow: hidden;}
  .imgList li{float:left;display: inline;}
  #prev,
  #next{position: absolute;top:80px;z-index: 20;cursor: pointer;opacity: 0.2;filter:alpha(opacity=20);}
  #prev{left: 100px;}
  #next{right: 100px;}
  #prev:hover,
  #next:hover{opacity: 0.5;filter:alpha(opacity=50);}
  .bg{position: absolute;bottom: 0;width:400px;height: 40px;z-index:20;opacity: 0.4;filter:alpha(opacity=40);background: black;}
  .infoList{position: absolute;left: 10px;bottom: 10px;z-index: 30;}
  .infoList li{display: none;}
  .infoList .infoOn{display: inline;color: white;}
  .indexList{position: absolute;right: 10px;bottom: 5px;z-index: 30;}
  .indexList li{float: left;margin-right: 5px;padding: 2px 4px;border: 2px solid black;background: grey;cursor: pointer;}
  .indexList .indexOn{background: red;font-weight: bold;color: white;}
  
  
  .bg1{position: absolute;bottom: 0;width: 300px;height: 40px;z-index:20;opacity: 0.4;filter:alpha(opacity=40);background: black;}
  .infoList1{position: absolute;left: 10px;bottom: 10px;z-index: 30;}
  .infoList1 li{display: none;}
  .infoList1 .infoOn1{display: inline;color: white;}
  .indexList1{position: absolute;right: 10px;bottom: 5px;z-index: 30;}
  .indexList1 li{float: left;margin-right: 5px;padding: 2px 4px;border: 2px solid black;background: grey;cursor: pointer;}
  .indexList1 .indexOn1{background: red;font-weight: bold;color: white;}
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

<script type="text/javascript">
	$(document).ready(function() {
		$("#wrapper").css("width",screen.width);
		$("#banner").css("width",screen.width);
		$(".bg").css("width",screen.width);
	});
	function wait_div_hide(){
		$("#wait_div").hide();
		$("#wrapper").show();
		$("#tuijian").show();
	}
	function wait_div_show(){
		$("#wrapper").hide();
		$("#tuijian").hide();
		$("#wait_div").show();
	}
	function a(){
		alert("屏幕宽高为："+screen.width+"*"+screen.height);
	}
		//图片切换模块 获取headline 为Y的前6个
		$(document).ready(
			function() {
				wait_div_show();
				sendRequest(pageContextPath + "/wechat/queryBoutique_change.do", null, function(jsonData) {
					var list=jsonData.list;
					var btlist="";
					var btitle="";
					$("#img_change").html('');
					$("#img_change_title").html('');
					var len = list.length>=6?6:list.length;
					for ( var i = 0; i < len; i++) {
						btlist+="<li>";
						btlist+="<a href='"+list[i].content_url+"'>";
						btlist+="<img alt='"+list[i].title+"' src='"+list[i].title_img_url+"' width='"+screen.width+"px' height='200px' />";
						btlist+="</a>";
						btlist+="</li>";
						btitle+="<li>";
						btitle+=list[i].title;
						btitle+="</li>";
					}
					$("#img_change").html(btlist);
					$("#img_change_title").html(btitle);
					wait_div_hide();
				});
			});
			//固定6个图片模块 获取headline 为Y的前6个
		
			$(document).ready(
			function() {
				sendRequest(pageContextPath + "/wechat/queryBoutique_change.do", null, function(jsonData) {
					var list=jsonData.list;
					$("#btinfo").html('');
					var btlistinfo="<br/>";
					btlistinfo += "<table style='width:100%' class='imagetable'>";
					var len = list.length>=6?6:list.length;
					for ( var i = 0; i < len; i+=2) {
						btlistinfo +="<tr>";
						btlistinfo +="<td style='text-align:center;width:50%;height:120px;'>";
						btlistinfo +="<a style='font-size:12px;' href='"+list[i].content_url+"'>";
						btlistinfo += "<img style='border-radius:15px 15px 15px 15px;width:100%;' src='"+list[i].title_img_url+"'/>";
						btlistinfo += list[i].title;
						btlistinfo +="</a>";
						btlistinfo +="</td>";
						btlistinfo +="<td style='text-align:center;width:50%;height:120px;'>";
						btlistinfo+="<a style='font-size:12px;' href='"+list[i+1].content_url+"'>";
						btlistinfo += "<img style='border-radius:15px 15px 15px 15px;width:100%;' src='"+list[i+1].title_img_url+"'/>";
						btlistinfo +=list[i+1].title;
						btlistinfo +="</a>";
						btlistinfo +="</td>";
						btlistinfo+="</tr>";
					}
					btlistinfo += "</table>";
					$("#btinfo").html(btlistinfo);
				});
			});
			
			//正常图片模块 获取headline 为N的前6个
			$(document).ready(
			function() {
				sendRequest(pageContextPath + "/wechat/queryBoutique.do", null, function(jsonData) {
					var list=jsonData.list;
					$("#btinfo_list").html('');
					var btlist="";
					var len = list.length>=6?6:list.length;
					for (var i = 0; i < len; i++) {
						btlist +="<a href='"+list[i].content_url+"'>";
					    btlist += "<table class='imagetable'>";
						btlist += "<tr>";
						btlist += "<td style='text-align:left;width:30%;'>";
						btlist += "<img style='width:100%;' src='"+list[i].title_img_url+"'/>";
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
						btlist+="</a>";
					}
					$("#btinfo_list").html(btlist);
				});
			});
			
	function sousuo(c){
		window.location.href = 'allInformation.jsp?c='+c; 
	} 
</script>


<body>
	<div id="wrapper">
		<!-- 最外层部分 -->
		<div id="banner">
			<!-- 轮播部分 -->
			<ul id="img_change" class="imgList">
				<!-- 图片部分 -->
				<li><a href="#"><img src="img/load2.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test2.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test3.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test4.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test3.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test3.jpg" width=""
						height="200px" alt="">
				</a>
				</li>
				<li><a href="#"><img src="./img/test3.jpg" width=""
						height="200px" alt="胃出血">
				</a>
				</li>
			</ul>
			<!--  
			<img src="img/l.gif" width="20px" height="40px" style="z-index:1999992; position: fixed;right: 1%;top:20%">
			<img src="img/r.gif" width="20px" height="40px" style="z-index:1999992; position: fixed;lift: 1%;top:20%">
			-->
			<div class="bg"></div>
			<!-- 图片底部背景层部分-->
			<ul id="img_change_title" class="infoList">
				<!-- 图片左下角文字信息部分 -->
				<li class="infoOn">1</li>
				<li>2</li>
				<li>3</li>
				<li>4</li>
				<li>5</li>
				<li>6</li>
			</ul>
		</div>
	</div>
	<div id="img_6" class="weui_panel">
		<div class="weui_panel_bd">
			<div class="container-fluid">
				<div class="row-fluid">
					<form>
					<div id="btinfo" align="center"></div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div id="tuijian" class="weui_panel">
		<div class="weui_panel_bd">
			<div class="container-fluid">
				<div class="row-fluid">
					<form>
						<div id="btinfo_tuijian" align="center" style="background-color:#ffffbb">
						<span style="font-size:24px;font-weight:bold">热门主题推荐</span>
						<div style="height: 8px;"></div>
						<table style="width:100%">
						<tr>
						<td style="text-align:center;width:33%;">
						<a href="allInformation.jsp?c=01"> 
						<button type="button" class="mybutton">食疗食谱</button>
						</a>
						</td>
						<td style="text-align:center;width:33%;">
						<a href="allInformation.jsp?c=05"> 
						<button type="button"  class="mybutton">肠胃疾病</button>
						</a>
						</td>
						<td style="text-align:center;width:34%;">
						<a href="allInformation.jsp?c=04"> 
						<button type="button" class="mybutton">生活常识</button>
						</a>
						</td>
						</tr>
						</table>
						<br/>
						<table style="width:100%">
						<tr>
						<td style="text-align:center;width:33%;"><a href="allInformation.jsp?c=02"><button type="button" class="mybutton" >季节养护</button></a></td>
						<td style="text-align:center;width:33%;"><a href="allInformation.jsp?c=03"><button type="button" class="mybutton" >病友分享</button></a></td>
						<td style="text-align:center;width:34%;"><a href="allInformation.jsp?c=104"><button type="button" class="mybutton">精彩活动</button></a></td>
						</tr> 
						</table>
						<div style="height: 8px;"></div>
						</div>
						<div id="btinfo_list" align="center"></div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div id="wait_div" class="weui_panel" style="position: fix;width:100%; z-index: 999">
		<div class="weui_panel_bd">
			<div class="container-fluid">
				<div class="row-fluid">
					<form>
						<div align="center">
							<img id="fm" style="width:100%;" src="img/load2.jpg"></img>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<script src="dist/example/zepto.min.js"></script>
	<script src="dist/example/router.min.js"></script>
	<script src="dist/example/example.js"></script>

	<script type="text/javascript" src="./js/jquery.min.js"></script>
	<script type="text/javascript">
		var curIndex = 0, //当前index
		imgLen = $(".imgList li").length; //图片总数
		// 定时器自动变换2.5秒每次
		var autoChange = setInterval(function() {
			if (curIndex < imgLen - 1) {
				curIndex++;
			} else {
				curIndex = 0;
			}
			//调用变换处理函数
			changeTo(curIndex);
		}, 2500);
		//左箭头滑入滑出事件处理
		$("#prev").hover(function() {
			//滑入清除定时器
			clearInterval(autoChange);
		}, function() {
			//滑出则重置定时器
			autoChangeAgain();
		});
		//左箭头点击处理
		$("#prev").click(function() {
			//根据curIndex进行上一个图片处理
			curIndex = (curIndex > 0) ? (--curIndex) : (imgLen - 1);
			changeTo(curIndex);
		});
		//右箭头滑入滑出事件处理
		$("#next").hover(function() {
			//滑入清除定时器
			clearInterval(autoChange);
		}, function() {
			//滑出则重置定时器
			autoChangeAgain();
		});
		//右箭头点击处理
		$("#next").click(function() {
			curIndex = (curIndex < imgLen - 1) ? (++curIndex) : 0;
			changeTo(curIndex);
		});
		//对右下角按钮index进行事件绑定处理等
		$(".indexList").find("li").each(function(item) {
			$(this).hover(function() {
				clearInterval(autoChange);
				changeTo(item);
				curIndex = item;
			}, function() {
				autoChangeAgain();
			});
		});
		//清除定时器时候的重置定时器--封装
		function autoChangeAgain() {
			autoChange = setInterval(function() {
				if (curIndex < imgLen - 1) {
					curIndex++;
				} else {
					curIndex = 0;
				}
				//调用变换处理函数
				changeTo(curIndex);
			}, 2500);
		}
		function changeTo(num) {
			var goLeft = num * screen.width;
			$(".imgList").animate({
				left : "-" + goLeft + "px"
			}, 500);
			$(".infoList").find("li").removeClass("infoOn").eq(num).addClass("infoOn");
			$(".indexList").find("li").removeClass("indexOn").eq(num).addClass("indexOn");
		}
	</script>
</body>
</html>