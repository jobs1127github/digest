<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>全部资讯</title>
<script type="text/javascript">
	//拿取地址栏的参数
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	
	$(function() {
		sendRequest(pageContextPath + "/wechat/queryallmarks.do", null,
				function(jsonData) {
					var data = jsonData.list;
					$("#mark_table").html("");
					var str = "";
					if (data != null) {
						for ( var i = 0; i < data.length; i++) {
							if (i == 0) {
								str += "<tr>";
							}
							/*table 每三个td 换一行*/
							if (i > 0 && i % 3 == 0) {
								str += "</tr>";
								str += "<tr>";
							}
							str += "<td><li id=" + data[i].mark_code
									+ " onclick='selectMark(this)' class='' >"
									+ data[i].mark_name + "</li></td>";
							if (i == data.lenght) {
								str += "</tr>";
							}
						}
					}
					$("#mark_table").html(str);
				});
	});
	
	var marks = '';
	function selectMark(_this) {
		//拿到某个元素的class 如果等于某个颜色就做某事
		if ($(_this).attr("class") == "aisorted") {
			$(_this).attr("class", "");
		} else {
			$(_this).attr("class", "aisorted");
		}
		
		var str_mark = "";
		//alert($("li.aisorted"));
		//li标签的==aisorted的class循环迭代
		$("li.aisorted").each(function(i, own) {
			//console.info($(this).attr('id'));
			str_mark += $(this).attr('id') + ',';
		});
		
		//控制显示提交按钮
		if (str_mark != "") {
			$("#sub_div").attr("class", "lbsubmit");
		} else {
			$("#sub_div").attr("class", "lbsubmit unable");
		}
		marks = str_mark;
	}

	function subUserMark() {
		var re_openid = getQueryString('re_openid');
		//发红包，jQuery ajax发起请求
		$.ajax({
			url : pageContextPath + "/sysGold/sedMoney.do",
			data : {
				"re_openid" : re_openid
			},
			type : "POST",
			success : function(data) {
			}
		});

		//保存用户的标签，记录用户感谢的标签文章
		$.ajax({
			url : pageContextPath + "/wechat/savemarks.do",
			data : {
				"marks" : marks,
				"re_openid" : re_openid
			},
			type : "POST",
			success : function(data) {
				window.location.href = "ObtainSuc.jsp";
			}
		});

	}
</script>
</head>
<body bgcolor="efeff4">
	<div class="">
		<div class="page">
			<div class="lbcheck">距离你的红包约0.01m，只有一步之遥哦</div>
			<div class="lbsort">
				请选择你关注的资讯（支持多选）
				<table width="100%" id="mark_table">
				</table>
			</div>
			<div id="sub_div" class="lbsubmit unable" onclick="subUserMark();">提交</div>
		</div>
	</div>
</body>
</html>