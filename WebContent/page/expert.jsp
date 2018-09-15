<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>专家@全泰</title>
</head>
<body bgcolor="efeff4">
	<div class="container">
		<div class="page">
			<div class="expinfo">
				<dl>可计提(元)
				</dl>
				<dt class="red">￥<span class="reserved">${userWallet.money_count}</span></dt>
				<dl>累计提现(元)
				</dl>
				<dt >￥0.00</dt>

				<div class="exsubmit js-cash">提现</div>
				<div class="exsubmit unable">提现历史记录</div>
			</div>


			<div class="exfoot">
				<div class="iireinfo">
					<p class="rinfo1">我的打赏用户</p>
					<p class="rinfo2 js-more">更多</p>
				</div>
				<div class="expic">
					<c:forEach var="user" items="${userList}">
						<p class="usermo">
							<image src="${user.head_portrait}" />
							<c:out value="${user.username}" />
						</p>
					</c:forEach>
				</div>
			</div>

		</div>
		<div class="exexpert">
			<div class="exesub">我发表的资讯</div>
		</div>
	</div>
	<script>
	   $(function(){
		   $('.js-cash').on('click',function(){
			    alert('试运行阶段，提现功能暂时未开通哦。');
		   });
		   
		   $('.unable').on('click',function(){
			  // location.href = '../page/cashDetail.jsp';
		   });
		   
		   $('.js-more').on('click',function(){
			   location.href = '${path}/wechat/rewardUser.do';
		   });
		   
		   $('.exesub').on('click',function(){
			   location.href = '${path}/wechat/myAdvice.do';
		   });

	   });
	</script>
</body>
</html>