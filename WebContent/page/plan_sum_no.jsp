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
<meta content="收益概况" name="description">
<meta content="jobs1127" name="author">


<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/bootstrap-fileupload.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/style-default.css" rel="stylesheet" id="style_color">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/bootstrap-fullcalendar.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/style-responsive.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/jquery.gritter.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/thirdparty/bootstrap/css/timeline-component.css" rel="stylesheet"  type="text/css" />

<!-- jquery-ui插件的样式 -->
<link href="${pageContext.request.contextPath}/thirdparty/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">

<% String title = "收益概况"; %>
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
<!-- BEGIN PAGE -->
<div id="main-content">
    <!-- BEGIN PAGE CONTAINER-->
  	<div class="container-fluid">
      <!-- BEGIN PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN PAGE TITLE & BREADCRUMB-->
              <h3 class="page-title">收益概况</h3>
              <!-- END PAGE TITLE & BREADCRUMB-->
          </div>
      </div>
      <!-- END PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN ALERTS PORTLET-->
              <div class="widget orange" >
                  <div class="widget-title">
                      <h4><i class="icon-reorder"></i>收益概况</h4>
                      <span class="tools">
						<a id="queryCondition" href="javascript:;" class="icon-chevron-down"></a>
					  </span>
                  </div>
                  <div class="widget-body" style="overflow:auto;">
                      	<!-- BEGIN BASIC PORTLET-->
                       <div class="widget-body">
                           <table class="table table-bordered" style="width:100%">
                               <tbody id="f1">
                               <tr>
                               <td>
                              	您的收益您懂的...
                               </td>
                               </tr>
                               </tbody>
                           </table>
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

<!-- BEGIN JAVASCRIPTS -->
<!-- Load javascripts at bottom, this will reduce page load time -->
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery.nicescroll.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/fullcalendar.min.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery.sparkline.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/Chart.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/jquery.scrollTo.min.js"></script>
<!--common script for all pages-->
<script src="${pageContext.request.contextPath }/thirdparty/bootstrap/js/common-scripts.js"></script>

<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/easy-pie-chart.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/sparkline-chart.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/home-page-calender.js"></script>
<script src="${pageContext.request.contextPath}/thirdparty/bootstrap/js/home-chartjs.js"></script>
<script src="${pageContext.request.contextPath}/javascript/httpService.js"></script>
<script src="${pageContext.request.contextPath}/page/My97DatePicker/WdatePicker.js"></script>
<!-- END JAVASCRIPTS -->

<!-- END BODY -->
</body>
</html>