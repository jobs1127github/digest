<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>打赏用户</title>
<script src="${path}/page/js/iscroll-lite.js"></script>
<script src="${path}/js/common.js"></script>
</head>

<body bgcolor="efeff4">
<div id="wrapper">
    <div id="scroller">
    <div class="rewardinfo" style="margin-top:0">
		<c:forEach var="user" items="${userList}">
			<div class="rewardinfo" style="margin-top:0">
			<div class="mreinfo">
			<dl>
			<dd><img src="${user.head_portrait}" alt="图片"></dd>
			<dd>${user.username}</dd><dd>${user.country}&nbsp;${user.province}&nbsp;${user.city}
				         </dd>
			<a>￥<span class="reserved">${user.gold}</span></a>
			</div>
			</div>
		</c:forEach>
    </div>
   
</div>
<div class="gotop" ></div>
<script type="text/javascript">
	$(function(){
		var myScroll,pageNumeber = 1,pageNumbers = ${pageNumbers};
	    myScroll = new IScroll('#wrapper');
		myScroll.on('scrollEnd',function(){
			if(this.directionY == '1' && pageNumeber <= pageNumbers){
				pageNumeber += 1;
				Common.Ajax({
					url:'${path}/wechat/rewardUserPage.do',
					data:{'page_numeber':pageNumeber},
					success:function(data){
						var sbf = Common.StringBuffer();
						$.each(data.data,function(){
							sbf.append('<div class="rewardinfo" style="margin-top:0">');
							sbf.append('<div class="mreinfo">');
							sbf.append('<dl>');
							sbf.append('<dd><img src="').append(this.head_portrait).append('" alt="图片"></dd>');
							sbf.append('<dd>').append(this.username).append('</dd><dd>').append(this.country).append('&nbsp;').append(this.province).append('&nbsp;').append(this.city);
							sbf.append('</dd>');
							sbf.append('<a>￥<span class="reserved">').append(this.gold.toFixed(2)).append('</span></a>');
							sbf.append('</div>');
							sbf.append('</div>');
						});
						$('#scroller').append(sbf.toString());
						myScroll.refresh();
					}
				});
			}
		});
		
		 //跳到顶部
		 $('div.gotop').on('click',function(){
			 myScroll.scrollTo(0,0);
		 });
	});
</script>
<!-- 
<div class="footer" style="position: static;"> <p class="js-pageBean" data-pageNumber="1">首页</p> <p class="js-pageBean" data-pageNumber="pre"><<上一页</p> <p>${pageNumberNow}/${pageNumbers}</p> <p class="js-pageBean" data-pageNumber="next" >下一页>></p><p class="js-pageBean" data-pageNumber="${pageNumbers}"> 尾页</p></div>
<script type="text/javascript">
     $(function(){
  		   $('.js-pageBean').on('click',function(){
  			   var page_numeber = $(this).attr('data-pageNumber');
  			   collectData(page_numeber);
  	      }); 
  		   
  		   
  		   var collectData = function(page_numeber){
  			   var pageNumbers = ${pageNumbers};
  			   var pageNumberNow = ${pageNumberNow};
  			   if(page_numeber == 'pre'){
  				   if(pageNumberNow != 1){
  					 page_numeber = --pageNumberNow;
  				   }else{
  					 page_numeber = 1;
  				   }
  			   }
	  			if(page_numeber == 'next'){
					   if(pageNumberNow != pageNumbers){
						 page_numeber = ++pageNumberNow;
					   }else{
						 page_numeber = pageNumbers;
					   }
				 }
  			   var sort = $('.js-sort').filter('.aisorted').attr('data-sort');
  			   location.href = '${path}/wechat/rewardUser.do?page_numeber=' + page_numeber;
  		   }
     });
</script>
 -->
</body>
</html>
