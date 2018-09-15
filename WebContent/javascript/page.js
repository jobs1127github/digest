/*******************************************************************************
 * js 分页组件，js层面抽象了一个类
 */
var pageView = function(id) {
	var self = this;
	this.id = id;
	this.totalPage = 0; // 总页数
	this.totalCount = 0; // 总记录数
	this.pageSize = 15; // 每页显示的记录数
	this.startPage = 1; // 起始页
	this.endPage = 1; // 结束页
	this.index = 1; // 被点击的页码
	this.showPageCode = 12; // 要显示的页码
	/**
	 * 用户点击分页时的处理函数
	 * 
	 * @param index
	 */
	this.onclick = function(index) {
		return true;
	};
	/**
	 * 初始化时计算各种页码变量的值
	 */
	this.calculate = function() {
		// 计算总页数
		if (self.totalCount % self.pageSize == 0) {
			self.totalPage = self.totalCount / self.pageSize;
		} else {
			self.totalPage = parseInt(self.totalCount / self.pageSize + 1);
		}
		// 计算index的页码;
		if (self.index > self.totalPage) {
			self.index = self.totalPage;
		}
		if (self.index < 1) {
			self.index = 1;
		}
		// 计算起始页和结束页
		self.startPage = Math.max(1,self.index-parseInt(self.showPageCode/2));
		self.endPage = Math.min(self.totalPage,self.startPage+self.showPageCode-1);
		//self.startPage = Math.max(1,self.endPage-self.showPageCode+1);
	};

	/**
	 * 内部方法.
	 */
	this._onclick = function(index) {
		var old = self.index;
		self.index = index;
		if (self.onclick(index) !== false) {
			self.render();
		} else {
			self.index = old;
		}
	};
	
	
	// 渲染到页面( 即将此组件加到html中)
	this.render = function() {
		self.calculate();
		var str = "";
		// 增加上一页和首页按钮(如果当前的点击页为第一页那么就将上一页和首页显示成文本反之的是一个<a>)
		if (self.index != 1) {
			str += "<a href='#' pCode='1' class=\"wid\">首页</a>";
			str += "<a href='#' pCode='" + (self.index - 1)
					+ "' class=\"wid\">上一页</a>";
		} else {
			str += "<a class=\"wid\">首页</a>";
			str += "<a class=\"wid\">上一页</a>";
		}
		// 增加显示的页码
		for ( var i = self.startPage; i <= self.endPage; i++) {
			// 如果i为当前选中的页数则为文本
			if (i == self.index) {
				str += "<a class=\"current\">" + i + "</a>";
			} else {
				str += "<a href='#' pCode='" + i + "' >" + i + "</a>";
			}
		}
		// 赠加下一页和尾页
		if (self.index != self.totalPage) {
			str += "<a href='#' pCode='" + (self.index + 1)
					+ "' class=\"wid\">下一页</a>";
			str += "<a href='#' pCode='" + self.totalPage
					+ "' class=\"wid\">尾页</a>";
		} else {
			str += "<a class=\"wid\">下一页</a>";
			str += "<a class=\"wid\">尾页</a>";
		}
		// 显示总页数和总记录数
		// str += "&nbsp;共" + self.totalPage + "页/" + self.totalCount + "条记录";
		str += "&nbsp;&nbsp;共&nbsp;<span>" + self.totalPage
				+ "</span>&nbsp;页&nbsp;<span>" + self.totalCount
				+ "</span>&nbsp;条记录";
		$(this.id).html(str);
		
		// 为每一个<a>链接增加点击事件
		var a_list = $(this.id).find("a");
		for ( var i = 0; i < a_list.length; i++) {
			a_list[i].onclick = function() {
				var tmpIndex = $(this).attr("pCode");
				if (tmpIndex != undefined && tmpIndex != '' && null != tmpIndex) {
					tmpIndex = parseInt(tmpIndex);
					self._onclick(tmpIndex);
				}
				return false;
			};
		}
	};
};