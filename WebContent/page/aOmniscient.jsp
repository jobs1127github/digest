<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>我@全泰</title>
</head>
<body bgcolor="efeff4">
	<div class="container">
		<div class="aoinfo">
			<li><img src="${user.head_portrait}" title="图片"/>
				<div class="aotittle">
					<div class="aodl1">${user.username}</div>
					<div class="aodl2">
						<p class="aodd1">
							金币：<a class="reserved">${user.gold}</a>元
						</p>
					</div>
				</div>
			</li>
		</div>
		<div class="aominfo">
			<li>注册时间
				<p>${user.created_time}</p>
			</li>
			<li>地区
				<p>${user.province}&nbsp;${user.city}</p>
			</li>
			<li>性别
				<p class="js-sex"></p>
			</li>
			<li>年龄
				<p class="uncheck js-age"></p>
			</li>
		</div>
		<div class="aosubmit">编辑资料</div>
		<c:if test="${user.role == '2'}">
			<div class="aoexpert" style="position: static">
				<div class="aoeinfo">您是全泰消化专家认证专家用户，可以点击下面的按钮进入[专家@全泰]查看您的全泰钱包，以及发表资讯内容</div>
				<div class="aoesub js-pass">专家@全泰</div>
			</div>
		</c:if>
		<c:if test="${user.role == '0'}">
			<div class="aoexpert" style="position: static">
				<div class="aoeinfo">您是全泰消化专家待认证专家用户</div>
				<div class="aoesub auditing">专家@全泰</div>
			</div>
		</c:if>
	</div>
	<script type="text/javascript">
		$(function() {
			// 某个class样式点击一下，调用function()
			$('.aosubmit').click(function() {
				location.href = '${path}/wechat/userInfo.do?oper_type=2';
			});
			
			$('div.js-pass').on('click', function() {
				location.href = '${path}/wechat/expertsIngress.do';
			});
			
			
			var sex = '${user.sex}';
			if (sex == '1') {
				sex = '男';
			} else if (sex == '2') {
				sex = '女';
			} else {
				sex = '';
			}
			//jquery根据class 来拿到对应的元素，并赋值
			$('.js-sex').text(sex);

			var age = '${user.age}';
			if (age == '') {
				age = '未填写年龄';
			}
			$('.js-age').text(age);
		});
	</script>
</body>
</html>