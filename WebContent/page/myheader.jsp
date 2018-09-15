<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setAttribute("path", request.getContextPath());
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="apple-itunes-app" content="app-id=429849944" />
<meta name="apple-touch-fullscreen" content="no" />
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-capable" content="no" />

<link rel="stylesheet" href="${path}/page/css/public.css">
<link rel="stylesheet" href="${path}/page/css/index.css">
<script src="${path}/page/js/jquery-1.7.2.min.js"></script>
<script src="${path}/javascript/httpService.js"></script>
<script src="${path}/javascript/window.js"></script>
<script src="${path}/gcpages/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="JavaScript" charset="UTF-8">
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e.keyCode == 27) {
			//不做任何动作 ESC
		}
		if (e.keyCode == 113) {
			//不做任何动作 F2
		}
		if (e.keyCode == 13) {//回车
			window.event.returnValue = false;
			setReturnValueFalse();
		}
	};
	function setReturnValueFalse() {
		if (document.all) {
			window.event.returnValue = false;
		} else {
			event.preventDefault();
		}
	}
</script>