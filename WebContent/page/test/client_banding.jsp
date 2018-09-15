<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("path", request.getContextPath());
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
%> 
<%@ page import="java.util.*"%>
<% 
    Calendar calendar=Calendar.getInstance(); 
    int year=calendar.get(Calendar.YEAR); 
    int pyear = year-1;
 %> 
<!DOCTYPE html>
<!-- saved from url=(0051)index.html -->
<html lang="en" style="overflow: hidden;">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta name="apple-itunes-app" content="app-id=429849944" />
	<meta name="apple-touch-fullscreen" content="no" />
	<meta content="telephone=no" name="format-detection" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<link href="<%=request.getContextPath()%>/favicon.ico" rel="shortcut icon" />
<!--视口的作用：在移动浏览器中，当页面宽度超出设备，浏览器内部虚拟的一个页面容器，将页面容器缩放到设备这么大，然后展示
	目前大多数手机浏览器的视口（承载页面的容器）宽度都是980；
	视口的宽度可以通过meta标签设置
	此属性为移动端页面视口设置，当前值表示在移动端页面的宽度为设备的宽度，并且不缩放（缩放级别为1）
	width:视口的宽度
	initial-scale：初始化缩放
	user-scalable:是否允许用户自行缩放（值：yes/no; 1/0）
 	-->
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<!-- 此属性为文档兼容模式声明，表示如果在IE浏览器下则使用最新的标准渲染当前文档 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="成为全安好伙伴" name="description">
<meta content="jobs1127" name="author">


<!-- jquery-ui插件的样式 -->
<link href="${pageContext.request.contextPath}/thirdparty/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/thirdparty/jquery-ui-1.12.1/external/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/jquery-ui-1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/javascript/jquery-1.11.0.js"></script> 
<script src="${pageContext.request.contextPath}/javascript/httpService.js"></script>

<!-- 绑定成为全安好伙伴-->
<% String title = "好伙伴验证"; %>
<title><%=title %>
</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta name="apple-itunes-app" content="app-id=429849944" />
<meta name="apple-touch-fullscreen" content="no" />
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-capable" content="no" />
</head>

<!-- BEGIN BODY -->
<body class="fixed-top" style="overflow:auto;margin:0px;padding:0px !important;">
<div id="dialog_msg" title="Dialog Title" class="ui-dialog-content ui-widget-content">
</div>
<!-- BEGIN PAGE -->
<div id="main-content">
    <!-- BEGIN PAGE CONTAINER-->
  	<div class="container-fluid">
      <!-- BEGIN PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN PAGE TITLE & BREADCRUMB-->
              <h3 class="page-title">好伙伴验证</h3>
              <!-- END PAGE TITLE & BREADCRUMB-->
          </div>
      </div>
      <!-- END PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN ALERTS PORTLET-->
              <div class="widget orange" >
                  <div class="widget-title">
                      <h4><i class="icon-reorder"></i>好伙伴验证</h4>
                      <span class="tools">
						<a id="queryCondition" href="javascript:;" class="icon-chevron-down"></a>
					  </span>
                  </div>
                  <div class="widget-body" style="overflow:auto;">
                      	<!-- BEGIN BASIC PORTLET-->
                       <div class="widget-body">
                       	 <form method="post" id="form" class="form-horizontal">
                           <table class="table table-bordered" style="width:100%">
                               <tbody>
                               <tr>
                               <td style="width:100%">好伙伴在全安备案的手机</td>
                               </tr>
                               <tr>
                               <td style="width:100%">
                               <input type="text" style="width:94%;" name="telephone" class="input-small"/>
                               </td>
                               </tr>
                               </tbody>
                           </table>
                           
                           <div style="text-align: center;">
	                         <button type="button" class="btn blue" style="margin-top:10px;" onclick="authentication();">
	                         <i class="icon-ok"></i>验证
	                         </button>
	                       </div>
                          </form>
                        </div>
                        <!-- END BASIC PORTLET-->
                  </div>
              </div>
              <!-- END ALERTS PORTLET-->
          </div>
      </div>
    </div>
</div>

<!-- BEGIN FOOTER
<div id="footer">2018 © 全安药业股份</div>
END FOOTER -->

<!-- END JAVASCRIPTS -->
<!-- 自定义的script方法 -->
<script type="text/javascript">
function authentication() {
    var params = $("#form").serialize();
    //查询流向
    sendRequest(pageContextPath + "/wechat/authentication.do", params, function (jsonData) {
        var msg = jsonData.msg;
        if(msg == "网络异常"){
        	alert("网络异常");
        }
        if(msg == "验证失败"){
        	alert("验证失败，所填信息与您在全安的备案不符，请联系相关经理或助理！");
        }
        if(msg == "更新成功"){//前往签到
        	 $("#dialog_msg").html("");
             var html = '验证通过，恭喜！';
             $("#dialog_msg").html(html);
             $("#dialog_msg").attr("class","ui-dialog-content ui-widget-content");
             $("#dialog_msg").dialog( "open" );
             
        	 //alert("验证通过，恭喜！");
        	 location.href=pageContextPath+"/wechat/qiandaoChouJiang.do";
        }
    });
}
</script>
<!-- END BODY -->
</body>
</html>