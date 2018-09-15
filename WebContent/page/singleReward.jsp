<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>文章被打赏用户</title>
<script src="${path}/page/js/iscroll-lite.js"></script>
<script src="${path}/js/common.js"></script>
</head>

<body bgcolor="efeff4">
<div class="rewardtittle">
<dl>《${articleName}》</dl>
<dd>合计打赏：<a>￥${rewardsSum}</a></dd>
</div>


<div class="rewardinfo">

<c:forEach var="user" items="${userList}">
		<div class="reinfo">
		<dl>
		<dd>${user.username}</dd><dd>${user.province}&nbsp;${user.city}&nbsp;${user.country}</dd>
		</dl>
		<dl>
		<dd>${user.created_time}</dd>
		</dl>
		<a>￥${user.gold}</a>
		</div>
</c:forEach>

</div>
<div class="gotop" onclick="self.scroll(0,0)"></div>
</body>
<script type="text/javascript">
     $(function(){
      	 var myScroll,pageNumeber = 1,pageNumbers = ${pageNumbers};
      	 var articleId= '${articleId}';
      	  myScroll = new IScroll('#wrapper');
      	  myScroll.on('scrollEnd',function(){
    			if(this.directionY == '1' && pageNumeber <= pageNumbers){
    				pageNumeber += 1;
    				Common.Ajax({
    					url:'${path}/wechat/singleRewardPage.do',
    					data:{'page_numeber':pageNumeber,'article_id':articleId},
    					success:function(data){
    						var sbf = Common.StringBuffer();
    						$.each(data.data,function(){
    							sbf.append('<div class="rewardinfo">');
    						    sbf.append('<div class="mreinfo">');
    							sbf.append('<dl>');
    							sbf.append('<dd>').append(this.username).append('</dd><dd>').append(this.province).append('&nbsp;').append(this.city).append('&nbsp;').append(this.country).append('</dd></dl>');
    							sbf.append('<a>￥<span class="reserved">').append(this.gold).append('</span></a>');
    						    sbf.append('<span style="margin-left:-139px;line-height: 64px">').append(this.created_time).append('</span>');
    							sbf.append('</div>');
    						    sbf.append('</div>');
    						});
    						$('#scroller').append(sbf.toString());
    						myScroll.refresh();
    					}
    				});
    			}
    		});
     });
</script>
</body>
</html>
