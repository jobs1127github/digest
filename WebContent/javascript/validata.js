/**
 * 验证是否是手机号码
 * @param mobile
 * @returns {Boolean}
 */
function isMobile(mobile) {
	var filter = /^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$/;
	if (filter.test(mobile))
		return true;
	else {
		alert("请输入正确的手机号码！");
		return false;
	}
}
/*
 * 根据身份证号码的前两位，来判断省份,定义key-value数组
 */
var vcity = {
	11 : "北京",
	12 : "天津",
	13 : "河北",
	14 : "山西",
	15 : "内蒙古",
	21 : "辽宁",
	22 : "吉林",
	23 : "黑龙江",
	31 : "上海",
	32 : "江苏",
	33 : "浙江",
	34 : "安徽",
	35 : "福建",
	36 : "江西",
	37 : "山东",
	41 : "河南",
	42 : "湖北",
	43 : "湖南",
	44 : "广东",
	45 : "广西",
	46 : "海南",
	50 : "重庆",
	51 : "四川",
	52 : "贵州",
	53 : "云南",
	54 : "西藏",
	61 : "陕西",
	62 : "甘肃",
	63 : "青海",
	64 : "宁夏",
	65 : "新疆",
	71 : "台湾",
	81 : "香港",
	82 : "澳门",
	91 : "国外"
};
/**
 * 验证身份证号码是否真实
 */
checkCard = function() {
	//前台页面定义一个input id=card_no就可以用该方法验证了
	var cardNo = 'card_no';
	var card = document.getElementById(cardNo).value;
	// 是否为空
	if (card === '') {
		alert('请输入身份证号，身份证号不能为空');
		//光标定位到输入身份证号码的input
		document.getElementById(cardNo).focus;
		return false;
	}
	// 校验长度，类型
	if (isCardNo(card) === false) {
		alert('您输入的身份证号码不正确，请重新输入，号码位数不对');
		document.getElementById(cardNo).focus;
		return false;
	}
	// 检查省份
	if (checkProvince(card) === false) {
		alert('您输入的身份证号码不正确，请重新输入，请检查前两位');
		document.getElementById(cardNo).focus;
		return false;
	}
	// 校验生日
	if (checkBirthday(card) === false) {
		alert('您输入的身份证号码生日不正确,请重新输入');
		document.getElementById(cardNo).focus();
		return false;
	}
	// 检验位的检测
	if (checkParity(card) === false) {
		alert('您的身份证校验位不正确,请重新输入');
		document.getElementById(cardNo).focus();
		return false;
	}
	return true;
};

//checkCard内部调用 检查号码是否符合规范，包括长度，类型
isCardNo = function(card) {
	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
	var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
	if (reg.test(card) === false) {
		return false;
	}
	return true;
};
//checkCard内部调用 取身份证前两位,校验省份,该方法等于 function checkProvince(card) {}
checkProvince = function(card) {
	var province = card.substr(0, 2);
	if (vcity[province] == undefined) {
		return false;
	}
	return true;
};
//checkCard内部调用 检查生日是否正确
checkBirthday = function(card) {
	var len = card.length;
	// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
	if (len == '15') {
		var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
		var arr_data = card.match(re_fifteen);
		var year = arr_data[2];
		var month = arr_data[3];
		var day = arr_data[4];
		var birthday = new Date('19' + year + '/' + month + '/' + day);
		return verifyBirthday('19' + year, month, day, birthday);
	}
	// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
	if (len == '18') {
		var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
		var arr_data = card.match(re_eighteen);
		var year = arr_data[2];
		var month = arr_data[3];
		var day = arr_data[4];
		var birthday = new Date(year + '/' + month + '/' + day);
		return verifyBirthday(year, month, day, birthday);
	}
	return false;
};
// 校验日期
verifyBirthday = function(year, month, day, birthday) {
	var now = new Date();
	var now_year = now.getFullYear();
	// 年月日是否合理
	if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month
			&& birthday.getDate() == day) {
		// 判断年份的范围（3岁到100岁之间)
		var time = now_year - year;
		if (time >= 3 && time <= 100) {
			return true;
		}
		return false;
	}
	return false;
};

// 校验位的检测
checkParity = function(card) {
	// 15位转18位
	card = changeFivteenToEighteen(card);
	var len = card.length;
	if (len == '18') {
		var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,4, 2);
		var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3','2');
		var cardTemp = 0, i, valnum;
		for (i = 0; i < 17; i++) {
			cardTemp += card.substr(i, 1) * arrInt[i];
		}
		valnum = arrCh[cardTemp % 11];
		if (valnum == card.substr(17, 1)) {
			return true;
		}
		return false;
	}
	return false;
};
// 15位转18位身份证号
changeFivteenToEighteen = function(card) {
	if (card.length == '15') {
		var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
				4, 2);
		var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
				'2');
		var cardTemp = 0, i;
		card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
		for (i = 0; i < 17; i++) {
			cardTemp += card.substr(i, 1) * arrInt[i];
		}
		card += arrCh[cardTemp % 11];
		return card;
	}
	return card;
};


/**
 * 真实姓名匹配
 * 
 * @param mail
 * @returns {Boolean}
 */
function checkstaffName(staffName) {
	var filter = /^[\u4e00-\u9fa5]{2,4}$/;
	// /(?:[\u4E00-\u9FFF]{1,8}·\u4E00-\u9FFF]{1,8})|(?:[\u4E00-\u9FFF]{2,5})/;
	if (filter.test(staffName))
		return true;
	else {
		alert("请输入真实姓名！");
		return false;
	}
}

/*
 * 匹配邮箱
 * 
 * @param mail
 * @returns {Boolean}
 */
function checkMail(mail) {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(mail))
		return true;
	else {
		return false;
	}
}
/**
 * 以下匹配日期格式与规范
 */
function checkDate(date) {
	var filter = /((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))/;
	if (filter.test(date))
		return true;
	else {
		return false;
	}
}
/**
 * test拓保邮箱
 * 
 * @param mail
 * @returns {Boolean}
 */
function checkTBMail(mail) {
	var tobo = /^[a-zA-Z0-9_]+$/;
	if (tobo.test(mail))
		return true;
	else {
		return false;
	}
}
/**
 * 验证客户资料管理
 */
function checkCustomer() {
	// 验证客户方名
	var tbCname = $("#tbCname").val();
	if (tbCname.trim() == "0") {
		alert("请选择该客户名称!");
		$("#tbCname").focus();
		return false;
	} else if (checkMail($("#tbCprojectMail").val().trim()) == false
			&& $("#tbCprojectMail").val().trim() != "") {
		alert("您输入的项目经理邮箱格式不正确！");
		return false;
	} else {
		return true;
	}
}