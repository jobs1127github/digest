<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>全部资讯</title>
<script type="text/javascript">

//http://www.51pharm.cn/digest/wechat/youhui.do?type=1
function goyouhui(){
	window.location.href='http://www.51pharm.cn/digest/wechat/youhui.do?type=1';
}
function goSQ(){
	//alert('社区还未开通！');
	window.location.href='http://www.51pharm.cn:8000/forum/plugin.php?id=wechat:access';
}
</script>

</head><body bgcolor="efeff4">

<div class="">
<div class="page">

<div class="osshow">
<dl>感谢您提交的信息，我们将为您推送您关注的资讯！同时您可以在我们的公众号首页领取您的红包！</dl>
<dd>如果10分钟内您还没有收到您的红包，请在公众号内与我们的人工客服联系。</dd>
</div>


<div class="lbsubmit ommunity" onclick="goSQ()">去全泰社区</div>
<div class="lbsubmit" onclick="goyouhui();"> 去阅读资讯</div>
</div>
</div>

</body>
</html>
