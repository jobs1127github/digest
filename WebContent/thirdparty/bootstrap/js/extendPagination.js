(function ($) {
    $.fn.extendPagination = function (options) {
        var defaults = {
            //pageId:'',
            totalCount: '',
            showPage: '10',
            pageNum:1,
            limit: '5',
            callback: function () {
                return false;
            }
        };
        $.extend(defaults, options || {});
        if (defaults.totalCount == '') {
            //alert('总数不能为空!');
            $(this).empty();
            return false;
        } else if (Number(defaults.totalCount) <= 0) {
            //alert('总数要大于0!');
            $(this).empty();
            return false;
        }
        var total = defaults.totalCount;
        if (defaults.showPage == '') {
            defaults.showPage = '10';
        } else if (Number(defaults.showPage) <= 0)defaults.showPage = '10';
        if (defaults.limit == '') {
            defaults.limit = '5';
        } else if (Number(defaults.limit) <= 0)defaults.limit = '5';
        var totalCount = Number(defaults.totalCount), showPage = Number(defaults.showPage),
            pageNum = Number(defaults.pageNum),limit = Number(defaults.limit),
            totalPage = Math.ceil(totalCount / limit);

        if (totalPage > 0) {
            var html = [];
            html.push('<ul style="margin:0px;padding: 0px;" class="pagination">');
            html.push('<li class="other other1"><a style="text-decoration: none;cursor:default;" href="javascript:void(0)">总条数：'+total+' </a> </li>');
            html.push('<li class="previous"><a href="#">&lt;上一页</a></li>');
            html.push('<li class="disabled"><a href="javascript:void(0)">...</a></li>');
            if (totalPage <= showPage) {
                for (var i = 1; i <= totalPage; i++) {
                    if (i == 1) html.push(' <li class="active"><a href="#">' + i + '</a></li>');
                    else html.push(' <li><a href="#">' + i + '</a></li>');
                }
            } else {
                for (var j = 1; j <= showPage; j++) {
                    if (j == 1) html.push(' <li class="active"><a href="#">' + j + '</a></li>');
                    else html.push(' <li><a href="#">' + j + '</a></li>');
                }
            }
            html.push('<li class="disabled hidden"><a href="javascript:void(0)">...</a></li>');
            html.push('<li class="next"><a href="#">下一页&gt;</a></li>');
            html.push('<li class="other other1">' 
            		+ '<a style="text-decoration: none;cursor:default;" href="javascript:void(0)"><p id="pageNumber" style="display: inline;">1</p>/'+totalPage + '&nbsp;&nbsp;页</a>');
			if (totalPage > showPage) {
				//html.push('，</li><li class="other other2">到第');
				//html.push('<input type="text" style="line-height:24px;width: 30px;" id="go-page" class="go-input">页');
				//html.push('<input type="button" class="go btn  btn-primary" style="line-height:22px;margin-bottom: 10px;" value="确定">');
				html.push('</li>');
			} else {
				html.push('</li>');
			}
			html.push('</ul>');
			
            $(this).html(html.join(''));
            if (totalPage > showPage) $(this).find('ul.pagination li.next').prev().removeClass('hidden');

            var pageObj = $(this).find('ul.pagination'), preObj = pageObj.find('li.previous'),
                currentObj = pageObj.find('li').not('.previous,.disabled,.next, .other'),
                nextObj = pageObj.find('li.next'), goObj = pageObj.find('li input.go'), 
                goInputObj = pageObj.find('li input.go-input');

            function loopPageElement(minPage, maxPage) {
                var tempObj = preObj.next();
                for (var i = minPage; i <= maxPage; i++) {
                    if (minPage == 1 && (preObj.next().attr('class').indexOf('hidden')) < 0)
                        //preObj.next().addClass('hidden');
                    	;
                    else if (minPage > 1 && (preObj.next().attr('class').indexOf('hidden')) > 0)
                        preObj.next().removeClass('hidden');
                    if (maxPage == totalPage && (nextObj.prev().attr('class').indexOf('hidden')) < 0)
                        //nextObj.prev().addClass('hidden');
                    	;
                    else if (maxPage < totalPage && (nextObj.prev().attr('class').indexOf('hidden')) > 0)
                        nextObj.prev().removeClass('hidden');
                    var obj = tempObj.next().find('a');
                    if (!isNaN(obj.html()))obj.html(i);
                    tempObj = tempObj.next();
                }
            }

            function callBack(curr) {
                defaults.callback(curr, defaults.limit, totalCount);
            }

            currentObj.click(function (event) {
                event.preventDefault();
                var currPage = Number($(this).find('a').html()), activeObj = pageObj.find('li[class="active"]'),
                    activePage = Number(activeObj.find('a').html());
                if (currPage == activePage) return false;
                if (totalPage > showPage && currPage > 1)  {
                    var maxPage = currPage, minPage = 1;
                    if (($(this).prev().attr('class'))
                        && ($(this).prev().attr('class').indexOf('disabled')) >= 0) {
                        minPage = currPage - 1;
                        maxPage = minPage + showPage - 1;
                        loopPageElement(minPage, maxPage);
                    } else if (($(this).next().attr('class'))
                        && ($(this).next().attr('class').indexOf('disabled')) >= 0) {
                        if (totalPage - currPage >= 1) maxPage = currPage + 1;
                        else  maxPage = totalPage;
                        if (maxPage - showPage > 0) minPage = (maxPage - showPage) + 1;
                        loopPageElement(minPage, maxPage)
                    }                  
                }
                activeObj.removeClass('active');
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == currPage) {
                        $(thiz).addClass('active');
                        callBack(currPage);
                    }
                });
            });
            
            goObj.click(function (event) {
            	event.preventDefault;
            	var goPage = goInputObj.val();
            	if (!goPage || goPage == "") {
            		alert('跳转页数不能为空');
            		return false;
            	}
				
            	if (goPage < 1) {
					alert('跳转页数不能小于 1');
					return false;
				}
            	if (goPage > totalPage) {
					alert('跳转页数不能大于总页数');
					return false;
				}
            	var activeObj = pageObj.find('li[class="active"]'), activePage = Number(activeObj.find('a').html());
            	if (activePage == goPage) {
					return false;
				}
            	activeObj.removeClass('active');
            	pageObj.find('li a').each(function() {
            		var curNum = $(this).html();
            		if (curNum == goPage) {
						$(this).parent().addClass('active');
					}
            	});
            	callBack(goPage);
            });
            
            preObj.click(function (event) {
                event.preventDefault();
                var activeObj = pageObj.find('li[class="active"]'), activePage = Number(activeObj.find('a').html());
                if (activePage <= 1) return false;
                if (totalPage > showPage) {
                    var maxPage = activePage, minPage = 1;                  
                    if ((activeObj.prev().prev().attr('class'))
                        && (activeObj.prev().prev().attr('class').indexOf('disabled')) >= 0) {
                        minPage = activePage - 1;
                        if (minPage > 1) minPage = minPage - 1;
                        maxPage = minPage + showPage - 1;
                        loopPageElement(minPage, maxPage);
                    }
                }
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == (activePage - 1)) {
                        activeObj.removeClass('active');
                        $(thiz).addClass('active');
                        callBack(activePage - 1);
                    }
                });
            });
            nextObj.click(function (event) {
                event.preventDefault();
                var activeObj = pageObj.find('li[class="active"]'), activePage = Number(activeObj.find('a').html());
                if (activePage >= totalPage) return false;
                if (totalPage > showPage) {
                    var maxPage = activePage, minPage = 1;                  
                    if ((activeObj.next().next().attr('class'))
                        && (activeObj.next().next().attr('class').indexOf('disabled')) >= 0) {
                        maxPage = activePage + 2;
                        if (maxPage > totalPage) maxPage = totalPage;
                        minPage = maxPage - showPage + 1;
                        loopPageElement(minPage, maxPage);
                    }
                }
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == (activePage + 1)) {
                        activeObj.removeClass('active');
                        $(thiz).addClass('active');
                        callBack(activePage + 1);
                    }
                });
            });
        }
    };
})(jQuery);