<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>我@全泰修改信息</title>
</head>
<title>我的资讯</title>
<script src="${path}/page/js/iscroll-lite.js"></script>
<script src="${path}/js/common.js"></script>
</head>

<body bgcolor="efeff4" >

<div class="aisort">
<p>智能排序:</p>
<li data-sort="1" class="p1  js-sort">时间</li>
<li data-sort="2" class="p2 js-sort">打赏金额</li>
<li data-sort="3" class="p3 js-sort">浏览量</li>
</div>
<div class="aiinfo">
     <div id="wrapper" style="top:66px;background-color: #fff">
	    <div id="scroller">
				<c:forEach var="information"  items="${informations}">
				<li class="js-article" data-artile-id="${information.information_id}" data-artile-name="${information.title}">
				<div class='infoimg'><img src="${information.tile_img_url}"></div>
				<div class="aitittle">
				<div class="aidl1">《${information.title}》</div>
				<div class="aidl2">
				<p class="aidd1">${information.created_time}</p>
				<p class="aidd4 ">${information.share_count}</p>
				<p class="aidd3 ">${information.browse_count}</p>
				</div>
				</div>
				</li>
				</c:forEach>
		</div>
    </div>
</div>

<script type="text/javascript">
     $(function(){
    	 var orderIndex = ${order};
    	 $('.js-sort').on('click',function(){
    		  $this = $(this);
  		      $this.toggleClass('aisorted').siblings('li').removeClass('aisorted');
  		      location.href = '${path}/wechat/myAdvice.do?sort=' + $this.attr('data-sort');
    	 }).eq(--orderIndex).addClass('aisorted');
  		   
    	 var myScroll,pageNumeber = 1,pageNumbers = ${pageNumbers};
    	  myScroll = new IScroll('#wrapper',{preventDefault:false});
    	  myScroll.on('scrollEnd',function(){
  			if(this.directionY == '1' && pageNumeber <= pageNumbers){
  				pageNumeber += 1;
  				var sort = $('.js-sort').filter('.aisorted').attr('data-sort');
  				Common.Ajax({
  					url:'${path}/wechat/myAdvicePage.do',
  					data:{'page_numeber':pageNumeber,'sort':sort},
  					success:function(data){
  						var sbf = Common.StringBuffer();
  						$.each(data.data,function(){
  							sbf.append('<li class="js-article" data-artile-id="').append(this.information_id).append(' data-artile-name="').append(this.title).append('">');
  							sbf.append('<div class="infoimg"><img src="').append(this.tile_img_url).append('"></div>');
  							sbf.append('<div class="aitittle">');
  							sbf.append('<div class="aidl1">《').append(this.title).append('》</div>');
  							sbf.append('<div class="aidl2">');
  							sbf.append('<p class="aidd1">').append(this.created_time).append('</p>');
  							sbf.append('<p class="aidd4 ">').append(this.share_count).append('</p>');
  							sbf.append('<p class="aidd3 ">').append(this.browse_count).append('</p>');
  							sbf.append('</div>');
  							sbf.append('</div>');
  							sbf.append('</li>');
  						});
  						$('#scroller').append(sbf.toString());
  						myScroll.refresh();
  					}
  				});
  			}
  		});
    	  
    	  $('.js-article').on('click',function(){
 			   $this = $(this);
 			  location.href='${path}/wechat/singleReward.do?article_id=' + $this.attr('data-artile-id') + '&article_name=' + $this.attr('data-artile-name');
 		   });
     });
</script>
</body>
</html>
