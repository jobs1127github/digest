// JavaScript Document

/**
 * ��Ⱦ�����б�
 */
function renderNavList() {

    // һ�������б�
    var level1List = NAV_CONFIG;

    if (!level1List || !level1List.length) {
        return;
    }

    var len = level1List.length;
    var navbarList = $('#navbarList'), navHtml = [], navItem = null, left = 0, zIndex = len;

    for (var i = 0; i < len; i++) {
        navItem = level1List[i];
        navHtml.push('<li style="left:' + left + 'px; z-index:' + zIndex + ';" navIndex="' + i + '"><a goto="' + navItem.href + '" href="#">' + navItem.text + '</a></li>');
        left += 87;
        zIndex--;
    }

    navbarList.html(navHtml.join(''));

    navbarList.find('a').click(level1NavHandler);
    navbarList = navItem = level1List = null;
    // 默认触发第一个一级菜单的单击事件
    $('#navbarList').find('li a:first').click();


}

/**
 * ���һ���˵�ʱ����
 */
function level1NavHandler() {

    var navbarList = $('#navbarList'), _this = $(this);
    var li = _this.parent('li');

    var current = navbarList.find('.hover'), index = li.attr('navIndex');
    current.css('z-index', current.attr('navIndex')).removeClass('hover');

    li.addClass('hover').css('z-index', 9999);

    var navInfo = NAV_CONFIG[index];
    var href = navInfo.href;

    /*  if (navInfo && href && href != '#') {
     // һ����������������
     $('#subPage').attr('src', href);
     } else if (navInfo.childs && navInfo.childs.length) {
     // һ���������ӵ�������
     showLevel2Nav(index);
     }
     */

    var chis = $('#childsNav').children().remove();
    if (navInfo && href && href != '#') {
        // һ�������������ӵ�ַ�����
        $('#subPage').attr('src', href);
    }

    if (navInfo.childs && navInfo.childs.length) {
        // ���ض����˵�
        showLevel2Nav(index);
    }
    navbarList = _this = li = current = navInfo = null;
}

/**
 * ��ʾ�����˵�
 * @params {Number} index
 */
function showLevel2Nav(index) {


    var navInfo = NAV_CONFIG[index];
    var childs = navInfo.childs;

    if (childs && childs.length) {
        var subNavItem = null, subNavHtml = [];

        for (var i = 0, len = childs.length; i < len; i++) {
            subNavItem = childs[i];

            subNavHtml.push('<li><a target="subPage" href="' + subNavItem.href + '">' + subNavItem.text + '</a></li>');

        }

        $('#childsNav').html(subNavHtml.join(''));
    }
}

/**
 * ����Ȩ�޼��ز˵�
 */
function loadMenu() {
    sendRequest('rest/menu/getMenuTree', null, function(jsonData) {
        //���ز˵�
        createMenu(jsonData.data);
        //��Ⱦ�����б�
        renderNavList();
    });
}

/**
 * �����˵�
 * @param data �˵���Ϣ
 */
function createMenu(data) {

    for (var i = 0; i < data.length; i++) {
        var href = '#';
        if (null !== data[i].value && '' != data[i].value) {
            href = data[i].value;
        }
        //���ض����˵�
        var childs = null;
        if (null != data[i].children && data[i].children.length != 0) {
            childs = createSubMenu(data[i].children);
        }
        var topMenu = {menuId:data[i].id,text:data[i].label,href:href ,childs : childs};
        NAV_CONFIG[i] = topMenu;
    }
}

/**
 * ���������˵�
 * @param data �����˵���Ϣ
 */
function createSubMenu(data) {
    var childs = [];
    for (var i = 0; i < data.length; i++) {
        var subMenu = {text:data[i].label,href:data[i].value};
        childs[i] = subMenu;
    }
    return childs;
}


$(function() {
    //���ز˵�
    loadMenu();
    //��Ⱦ�����б�
    //  renderNavList();

    $('#navbarList').find('li a:first').click();
    $('#topVisibility').toggle(function() {
        $('#topLeft, #topRightNav, #topRightOut').hide();
        $(this).find('a').addClass('down');
        $('#top, #topRight').height(22);
    }, function() {
        $('#topLeft, #topRightNav, #topRightOut').show();
        $(this).find('a').removeClass('down');
        $('#top, #topRight').height(79);
    })
});
