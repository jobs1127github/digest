<%@ page contentType="text/html; charset=utf-8"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>新疆厂部通讯录</title>
</head>
<style>
body, ul, li {
	margin: 3px;
	padding: 3px;
}

#geoPage {
	position: absolute;
	z-index: 99;
	left: 1%;
	width: 98%;
	height: 80px;
	margin-top: 0.7%;
}

#markPage {
	position: absolute;
	z-index: 99;
	left: 1%;
	width: 98%;
	height: 500px;
	margin-top: 15%;
}

body {
	font-size: 12px;
	font-family: sumsun, arial;
	background: #FFFFFF;
	position: absolute;
}

.gover_search {
	width: 100%;
	position: relative;
	z-index: 9999999;
	height: 35px;
	padding: 15px 0 0 20px;
	border: 0px solid #b8cfe6;
	border-bottom: 0;
	background: repeat-x 0 0;
}

.gover_search_form {
	height: 10px;
}

.gover_search .search_t {
	float: left;
	width: 112px;
	line-height: 26px;
	color: #666;
}

.gover_search .input_search_key {
	float: left;
	width: 70%;
	height: 37px;
	padding: 5px;
	line-height: 18px;
	overflow: hidden;
	border: 1px solid #ccc;
	text-align: center;
	color: #ff3300;
	background: no-repeat 0 -79px;
	cursor: pointer;
	font-weight: bold;
}

.gover_search .search_btn {
	margin-left: 3px;
	float: left;
	width: 27%;
	height: 37px;
	overflow: hidden;
	border: 0px solid #ccc;
	text-align: center;
	color: #fff;
	background: #0079FE; no-repeat 0 -79px;
	cursor: pointer;
	font-weight: bold;
	border-radius: 6px 6px 6px 6px;
	font-size: 14px;
}

.gover_search .search_btn3 {
	float: left;
	width: 100%;
	height: 37px;
	overflow: hidden;
	border: 0px solid #ccc;
	text-align: center;
	font-size: 18px;
	color: #fff;
	letter-spacing: 0px;
	background: #0079FE; no-repeat 0 -79px;
	cursor: pointer;
	font-weight: bold;
	border-radius: 6px 6px 6px 6px;
	margin: 11px 6px 6px 6px;
}

.gover_search .search_btn2 {
	float: left;
	width: 107.2%;
	height: 82px;
	overflow: hidden;
	border: 0px solid #ccc;
	text-align: left;
	color: #1F92C7;
	background: #1D1D1D;
	margin-top: -5%;
	margin-left: -21px;
	cursor: pointer;
	font-weight: bold;
}

.gover_search .search_suggest {
	position: absolute;
	z-index: 9997111;
	margin-top: 19%;
	width: 100%;
	height: 150px;
	border: 0px solid #ccc;
	border-top: none;
	display: none;
	color: #004080;
	background: #0079FE;
}

.gover_search .search_suggest li {
	height: 24px;
	overflow: hidden;
	padding-left: 3px;
	line-height: 24px;
	background: #F5F5DC;
	cursor: default;
}

.gover_search .search_suggest li.hover {
	background: #ddd;
}

.num_right {
	float: right;
	text-align: right;
	line-height: 24px;
	padding-right: 10px
}
</style>
<body>
	<h2>新疆厂部</h2>
	<div id="qd_div_id" style="width: 100%; height: 32%;">
		<form id="form" name="form" method="post">
			<div class="gover_search">
				<div class="gover_search_form clearfix" id="contact_center_div">
					<input type="text" class="input_search_key" id="gover_search_key"
						name="gover_search_key" placeholder="输入联系人拨号" />

					<button type="button" class="search_btn" onclick="sub();">拨号</button>

					<div class="search_suggest" id="gov_search_suggest">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			<input id="keyname" name="keyname" type="hidden" value="" /> <a
				id="tel" href="" style="display: none;">a</a>
		</form>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/javascript/httpService.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/javascript/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		var text;
		function sub() {
			//alert(getNum(text));
			$('#tel').attr('href', 'tel:' + getNum(text));
			$("#tel").text(getNum(text));
			document.getElementById("tel").click();
		}
		var getNum = function(Str, isFilter) {
			//用来判断是否把连续的0去掉
			isFilter = isFilter || false;
			if (typeof Str === "string") {
				// var arr = Str.match(/(0\d{2,})|([1-9]\d+)/g);
				//"/[1-9]\d{1,}/g",表示匹配1到9,一位数以上的数字(不包括一位数).
				//"/\d{2,}/g",  表示匹配至少二个数字至多无穷位数字
				var arr = Str.match(isFilter ? /[1-9]\d{1,}/g : /\d{2,}/g);
				console.log(arr);
				return arr.map(function(item) {
					//转换为整数，
					//但是提取出来的数字，如果是连续的多个0会被改为一个0，如000---->0，
					//或者0开头的连续非零数字，比如015，会被改为15，这是一个坑
					// return parseInt(item);
					//字符串，连续的多个0也会存在，不会被去掉
					return item;
				});
			} else {
				return [];
			}
		}

		function oSearchSuggest(searchFuc) {
			var input = $('#gover_search_key');
			var suggestWrap = $('#gov_search_suggest');
			var key = "";
			var init = function() {
				input.bind('keyup', sendKeyWord);
				input.bind('blur', function() {
					setTimeout(hideSuggest, 100);
				});
			};
			var hideSuggest = function() {
				//suggestWrap.hide();
			};
			//发送请求，根据关键字到后台查询 
			var sendKeyWord = function(event) {
				//键盘选择下拉项 
				if (suggestWrap.css('display') == 'block'
						&& event.keyCode == 38 || event.keyCode == 40) {
					var current = suggestWrap.find('li.hover');
					if (event.keyCode == 38) {
						if (current.length > 0) {
							var prevLi = current.removeClass('hover').prev();
							if (prevLi.length > 0) {
								prevLi.addClass('hover');
								input.val(prevLi.html());
							}
						} else {
							var last = suggestWrap.find('li:last');
							last.addClass('hover');
							input.val(last.html());
						}
					} else if (event.keyCode == 40) {
						if (current.length > 0) {
							var nextLi = current.removeClass('hover').next();
							if (nextLi.length > 0) {
								nextLi.addClass('hover');
								input.val(nextLi.html());
							}
						} else {
							var first = suggestWrap.find('li:first');
							first.addClass('hover');
							input.val(first.html());
						}
					}
					//输入字符 
				} else {
					var valText = $.trim(input.val());
					if (valText == '' || valText == key) {
						return;
					}
					searchFuc(valText);
					key = valText;
				}
			};
			//请求返回后，执行数据展示 
			this.dataDisplay = function(data) {
				if (data.length <= 0) {
					suggestWrap.hide();
					return;
				}
				//往搜索框下拉建议显示栏中添加条目并显示 
				var li;
				var tmpFrag = document.createDocumentFragment();
				suggestWrap.find('ul').html('');
				for (var i = 0; i < data.length; i++) {
					li = document.createElement('LI');
					li.innerHTML = data[i];
					tmpFrag.appendChild(li);
				}
				suggestWrap.find('ul').append(tmpFrag);
				suggestWrap.show();
				//为下拉选项绑定鼠标事件 
				suggestWrap.find('li').hover(function() {
					suggestWrap.find('li').removeClass('hover');
					$(this).addClass('hover');
				}, function() {
					$(this).removeClass('hover');
				}).bind('click', function() {
					$(this).find("span").remove();
					input.val(this.innerHTML);
					text = this.innerHTML;
					suggestWrap.hide();
				});
			};
			init();
		};
		//实例化输入提示的JS,参数为进行查询操作时要调用的函数名 
		var searchSuggest = new oSearchSuggest(sendKeyWordToBack);
		//这是一个模似函数，实现向后台发送ajax查询请求，并返回一个查询结果数据，传递给前台的JS,再由前台JS来展示数据。本函数由程序员进行修改实现查询的请求 
		//参数为一个字符串，是搜索输入框中当前的内容 
		function sendKeyWordToBack(keyword) {
			var aData = new Array();
			$("#keyname").val(keyword);
			var params = $("#form").serialize();
			sendRequest(pageContextPath + "/wechat/query_contact_center.do",
					params, function(jsonData) {
						var list = jsonData.list;
						for (var i = 0; i < list.length; i++) {
							aData.push(list[i]);
						}
						//将返回的数据传递给实现搜索输入框的输入提示js类 
						searchSuggest.dataDisplay(aData);
					});
		}
	</script>