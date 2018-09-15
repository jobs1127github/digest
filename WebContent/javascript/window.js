//定义一个全局变量，项目的根路径
//获取当前网址，如：
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录如：/Tmall/index.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如：//localhost:8080
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/Tmall
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
var pageContextPath=projectName;


function $a(a) {
	return document.getElementById(a);
}

function viewPop(src, title) {
	loadWinDiv(src, 600, 500, title);
	$a("fpanel").style.display = '';
	// resizePos();
	resizeGrayBg();
	fixSelect();
}
function resizePos() {
	$a('fpbg').style.left = (document.documentElement.clientWidth - 548) / 2
			+ 'px';
	$a('fpbg').style.top = (document.documentElement.clientHeight - 464) / 2
			+ 'px';
	$a('addPanel').style.left = (document.documentElement.clientWidth - 542)
			/ 2 + 'px';
	$a('addPanel').style.top = (document.documentElement.clientHeight - 458)
			/ 2 + 'px';
}
function resizeGrayBg() {
	var viewWidth = document.documentElement.clientWidth;
	// var viewHeight = document.documentElement.clientHeight;
	var viewHeight = document.body.scrollHeight;
	if ($a('grayBg')) {
		var grayBg = $a('grayBg');
	} else {
		var grayBg = document.createElement('div');
		grayBg.setAttribute('id', 'grayBg');
	}
	grayBg.style.position = 'absolute';
	grayBg.style.width = viewWidth + 'px';
	grayBg.style.height = viewHeight + 'px';
	grayBg.style.background = 'gray';
	// grayBg.style.zIndex = 9998;
	grayBg.style.opacity = 0.6;
	grayBg.style.filter = 'alpha(opacity:60)';
	if (!$a('grayBg')) {
		document.body.insertBefore(grayBg, document.body.firstChild);
	}
}
function fixSelect() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (/msie\s6.0/.test(ua)) {
		var frameObj = document.createElement('iframe');
		frameObj.setAttribute('src', 'javascript:false;');
		frameObj.style.width = '100%';
		frameObj.style.height = '100%';
		frameObj.style.filter = 'alpha(opacity:0)';
		frameObj.style.zIndex = 9997;
		$a('grayBg').insertBefore(frameObj);
	}
}
function closePop() {
	if ($a("fpanel").style.display == '') {
		$a("fpanel").style.display = 'none';
		document.body.removeChild($a('grayBg'));
	}
}
function loadWinDiv(src, width, height, title) {
	var fpenl = $("#fpanel");
	if (fpenl != null && fpenl != undefined) {
		$("#fpanel").remove();
	}
	var marginLeft = width / 2;
	var margintop = height / 2;
	var winDiv = '<div id="fpanel" style="display:none;">';
	// winDiv += '<div class="fpbg" id="fpbg" style="width:'+width+'px;
	// height:'+height+'px; margin-left:-'+marginLeft+'px;
	// margin-top:-'+margintop+'px;"></div>';
	// winDiv += '<div class="addPanel" id="addPanel" style="width:'+width+'px;
	// height:'+height+'px; margin-left:-'+marginLeft+'px;
	// margin-top:-'+margintop+'px;">';
	winDiv += '<div class="fpbg" id="fpbg" style="width:542px; height:406px;"></div>';
	winDiv += '<div class="addPanel" id="addPanel" style="width:542px; height:400px ;">';
	winDiv += '<div class="fh"><span>'
			+ title
			+ '</span><a class="close" href="javascript:closePop();" title="关闭"></a></div>';
	winDiv += '<div class="fBox">';
	winDiv += '<iframe src="'
			+ src
			+ '" frameborder="0" width="100%" height="100%" style="margin:0; padding:0; border:0"></iframe>';
	winDiv += '	</div>';
	winDiv += '	</div>';
	winDiv += '</div>';
	var div2 = document.createElement("div");
	div2.innerHTML = winDiv;
	document.body.appendChild(div2);
}

// 转换性别
function getSexName(sex) {
	var sexName = '';
	if (sex == '1') {
		sexName = '男';
	} else if (sex == '2') {
		sexName = '女';
	}
	return sexName;
}

function getValidName(validind) {
	var validIndName = '';
	if (validind == 'Y') {
		validIndName = '关注';
	} else if (validind == 'N') {
		validIndName = '未关注';
	}
	return validIndName;
}

function getRole(id) {
	var validIndName = id;
	if (id == '1') {
		validIndName = '用户';
	} else if (id == '2') {
		validIndName = '专家';
	} else if (id == '0') {
		validIndName = '专家待审批';
	}
	return validIndName;
}

function getRoleName(id) {
	var Name = id;
	if (id == '001') {
		Name = '系统管理员';
	} else if (id == '002') {
		Name = '内部总管理员';
	} else if (id == '003') {
		Name = '资讯管理员';
	}
	return Name;
}

function getStatusName(id) {
	var Name = id;
	if (id == '1') {
		Name = '新建';
	} else if (id == '2') {
		Name = '待审核';
	} else if (id == '3') {
		Name = '已发布';
	} else if (id == '4') {
		Name = '已隐藏';
	}
	return Name;
}
/**
 * 
 * 打开窗口 win
 * 
 * @returns
 */
var openWin = function(param) {
	var _default = {
		url : '',
		title : '', // 窗口的名字
		width : '1200px', // 窗口的宽度
		height : '768px', // 窗口的高度
		scrollbars : 'yes', // 是否在窗口中显示横向或者纵向滚动条。默认为yes。
		top : '200px', // 窗口顶部的位置
		left : '200px'// 窗口距左边框的距离
	};
	$.extend(_default, param);
	window.open(pageContextPath + _default.url, _default.title, 'width='
			+ _default.width + ',height=' + _default.height + ',top='
			+ _default.top + ',left=' + _default.left);
};

/**
 * 
 */
function getMarkName(code) {
	var markname = "";
	var Str_mark = code + ",";
	var sourceStrArray = Str_mark.split("/,");
	for ( var i = 0; i < sourceStrArray.length; i++) {
		if (sourceStrArray[i] != "") {
			var markItme = getQueryMark(sourceStrArray[i]);
			if (markItme != "") {
				markname += markItme[0].dataValue + ",";
			}
			;
		}
	}
	return markname.substring(0, markname.length - 1);
}
// 获取标签
function getQueryMark1(mark) {
	var markMap = "";
	sendRequest(pageContextPath + "/system/loadinitMark.do", {
		"mark" : mark
	}, function(jsonData) {
		markMap = jsonData.data;
	}, false);
	return markMap;

}
// 获取标签
function getQueryMark(mark) {
	var markMap = "";
	sendRequest(pageContextPath + "/system/loadinitMark.do", {
		"mark" : mark
	}, function(jsonData) {
		markMap = jsonData.data;
		$("#mark").html("");
		var mark = "";
		for ( var i = 0; i < markMap.length; i++) {
			mark += "<input type='checkbox' name='mark' value="
					+ markMap[i].dataItem + "/>" + markMap[i].dataValue
					+ "&nbsp";
		}
		$("#mark").html(mark);
	}, false);
	return markMap;
}
//获取标签
function getAllQueryMark() {
	var markMap = "";
	sendRequest(pageContextPath + "/system/loadinitMark.do", {
		"mark" : ""
	}, function(jsonData) {
		markMap = jsonData.data;
	}, false);
	return markMap;
}
// 获取群组
function getQueryGroup(group_id) {
	var group = "";
	sendRequest(pageContextPath + "/system/loadinitGroup.do", {
		"group_id" : group_id
	}, function(jsonData) {
		group = jsonData.data;
	}, false);
	return group;

}
// 金币发放类型
function getSendName(id) {
	var Name = id;
	if (id == '3') {
		Name = '系统发放';
	} else if (id == '2') {
		Name = '分享奖励';
	} else if (id == '4') {
		Name = '流量奖励';
	} else if (id == '1') {
		Name = '关注奖励';
	}
	return Name;
}

$(function() {
	// 搜索关于金额保留两位数据(0.00)显示
	$('.reserved').each(function() {
		$this = $(this);
		var money = parseFloat($this.text());
		$this.text(money.toFixed(2));
	});
});
