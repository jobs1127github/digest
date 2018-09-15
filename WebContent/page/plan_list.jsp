<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	request.setAttribute("path", request.getContextPath());
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
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
<meta content="流向查询" name="description">
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

<% String title = "流向查询"; %>
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
<!-- ui-dialog -->
<div class="modal fade" data-backdrop="static" tabindex="-1" id="myModal">
    <!--窗口声明：-->
    <div class="modal-dialog modal-lg">
        <!-- 内容声明 -->
        <div class="modal-content">
            <br>
            <p style="margin-left:15px;">拼命查询中，请稍等 ……</p>
            <!-- 主体 -->
            <div class="modal-body" style="text-align: center">
               <img alt="拼命查询中，请稍等 " src="${pageContext.request.contextPath}/img/wait.gif">
            </div>
        </div>
    </div>
</div>
<!-- BEGIN PAGE -->
<div id="main-content">
    <!-- BEGIN PAGE CONTAINER-->
  	<div class="container-fluid">
      <!-- BEGIN PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN PAGE TITLE & BREADCRUMB-->
              <h3 class="page-title">流向查询</h3>
              <!-- END PAGE TITLE & BREADCRUMB-->
          </div>
      </div>
      <!-- END PAGE HEADER-->
      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN ALERTS PORTLET-->
              <div class="widget orange" >
                  <div class="widget-title">
                      <h4><i class="icon-reorder"></i>查询条件</h4>
                      <span class="tools">
						<a id="queryCondition" href="javascript:;" class="icon-chevron-down"></a>
					  </span>
                  </div>
                  <div class="widget-body" style="overflow:auto;">
                      <form method="post" id="query_plans_form" class="form-horizontal">
                      	<!-- BEGIN BASIC PORTLET-->
                       <div class="widget-body">
                           <table class="table table-bordered">
                               <tbody>
                               <tr>
                                   <td colspan="2">开始时间</td>
                                   <td>
                                   <input type="text" style="width:94%;"
                                        id="stime" onFocus="WdatePicker({isShowClear:false,readOnly:true})"
                                        name="stime" onclick="WdatePicker()" class="Wdate"/>
                                   </td> 
                               </tr>
                               <tr>
                                   <td colspan="2">结束时间</td>
                                   <td>
									<input type="text" style="width:94%;"
                                        id="etime" onFocus="WdatePicker({isShowClear:false,readOnly:true})"
                                        name="etime" onclick="WdatePicker()" class="Wdate"/>
									</td>
                               </tr>
                               <tr>
                                   <td colspan="2">品种</td>
                                   <td>
                                      <input type="checkbox" value="A" checked="true" name="products" id="product_A"/>
                                      <label style="display:inline;margin-right: 20px;" for="product_A">A</label>
                                      <input type="checkbox" value="Y" checked="true" name="products" id="product_Y"> 
                                      <label style="display:inline;margin-right: 20px;" for="product_Y">Y</label>
                                      <input type="checkbox" value="Z" checked="true" name="products" id="product_Z"/> 
                                     <label style="display:inline;margin-right: 20px;" for="product_Z">Z</label>
									</td>
                               </tr>
                               <tr>
                                   <td colspan="2">终端关键字</td>
                                   <td>
                                    <input type="text" style="width:94%;-webkit-appearance: none;" id="terminal" name="terminal" class="input-small"/>
                                   </td>
                               </tr>
                               </tbody>
                           </table>
                        </div>
                        <!-- END BASIC PORTLET-->
                        <div style="text-align: center;">
                         <button type="button" class="btn blue" style="margin-top:10px;" onclick="query();">
                         <i class="icon-ok"></i> 查询
                         </button>
                         </div>
                      </form>
                  </div>
              </div>
              <!-- END ALERTS PORTLET-->
          </div>
      </div>

      <div class="row-fluid">
          <div class="span12">
              <!-- BEGIN ALERTS PORTLET-->
              <div class="widget blue">
                  <div class="widget-title">
                      <h4><i class="icon-reorder"></i>纯销流向明细</h4>
                      <span class="tools">
			<a href="javascript:;" class="icon-chevron-down"></a>
			</span>
                  </div>
                  <div class="widget-body" style="overflow:auto">
                      <table class="table table-striped" width="100%" style="table-layout:fixed">
                          <thead>
                          <tr>
                              <th width="35%">日期/配送商</th>
                              <th width="35%">入货终端</th>
                              <th width="15%">品规</th>
                              <th width="15%">数量</th>
                          </tr>
                          </thead>
                          <tbody id="selectPlans">
                          </tbody>
                      </table>
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
<!-- 自定义的script方法 -->
<script type="text/javascript">
    function query() {
    	$("#queryCondition").click();
    	$('#myModal').modal('show');
        var params = $("#query_plans_form").serialize();
        //查询流向
        sendRequest(pageContextPath + "/wechat/selectPlans.do", params, function (jsonData) {
            var list = jsonData.list;
            var buf = "";
            $("#selectPlans").html('');
            for (var i = 0; i < list.length; i++) {
                buf += '<tr>';
                buf += '<td>' + list[i].sdate+list[i].commercial1 + '</td>';
                buf += '<td>' + list[i].terminal + '</td>';
                buf += '<td>' + list[i].product + list[i].pack+'</td>';
                buf += '<td>' +list[i].count+ '</td>';
                buf += '</tr>';
            }
            $("#selectPlans").html(buf);
            $('#myModal').modal('hide');//隐藏模板
        });
    }
</script>
<script type="text/javascript">
	$(function(){
		var value = new Date().getFullYear()+'/'+(new Date().getMonth()+1) + '/' + new Date().getDate();
		var pre = new Date().getFullYear()+'/'+(new Date().getMonth()) + '/' + '1';
		$("#stime").val(pre);
		$("#etime").val(value);
	});
</script>
<!-- END BODY -->
</body>
</html>