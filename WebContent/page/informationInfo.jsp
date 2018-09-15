<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>全部资讯</title>

<script type="text/javascript">
	//infoopenid是字符串变量，故要用''或""包起来，否则会有异常
	var infoopenid='${information.openid }';
	var informationid='${information.information_id }';
	
	//gold、price小数类型或数字类型，不能用''或""包起来，否则会有异常
	var gold=${gold};
	var price=0.0;
	//打赏
	function doreward(_this){
		//转换成float类型
		price= parseFloat(_this.attributes.price.value);
		if(gold>=price){
			paygold(price);//用户金币充足的情况
		}else if(gold <price && gold>1){//金币不足，但有1元金币
			show2();
		}else{
			show3();//无金币
		}
	}
	//用金币打赏
	function paygold(price){
		sendRequest("${path}/wechat/updatePrice.do", 
			{"price":price,"informationid":informationid,"infoopenid":infoopenid},
			function(jsonData){
				if(jsonData){
					gold=gold-price;
					deshow();		
					show1();
					queryRewarders();
				}
			});	
	}
	//查询打赏记录
	function queryRewarders(){
		sendRequest("${path}/wechat/queryRewarders.do", 
				{"informationid":informationid},
				function(jsonData){
					var rewarders=jsonData.rewarders;
					var rewardercount=jsonData.rewarderscount;
					$("#iipic").html('');
					var str="";
					if(rewarders!=null){
						for(var i=0;i<rewarders.length;i++){
							str+="<p><img src='"+rewarders[i].head_portrait+"'/></p>";
						}
					}
					$("#iipic").html(str);
					$("#rinfo2").html("总打赏人数："+rewardercount+"人");
				});	
	}
	//分享成功后的提示信息
	function sharesucdo(){
		$("#frist_show").html('');
		var frist_show="分享成功，每天前五次分享可以获得金币哦！";
		frist_show+="<div class='iifrsubmit' onclick='deshow2()'>知道了</div>";
		$("#frist_show").html(frist_show);
		$(".frist_show").show();
	}

	function show1(){
		//div 模板
		$(".brokerage_contant").show();
		$("#payover1").show();
	}
	function show2(){
		//div 模板
		$(".brokerage_contant").show();
		$("#payover2").show();
	}
	function show3(){
		$(".brokerage_contant").show();
		$("#payover3").show();
	}

	function payAll(){
		if($("#payAll1").hasClass("checked")){
			paygold(gold);
		}else{
			payByWX();
		}
	}
	
	//微信支付
	function payByWX(){
		//交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
		var payed= Math.round((price-gold)*100);//price打赏的价格，gold是用户剩余的钱/元。
		WXPay(payed);
	}
	//打赏成功后隐藏
	function deshow(){
		$(".brokerage_contant").hide();
		//通过class来拿取元素
		$(".iipayover").hide();
	}
	function deshow2(){
		$(".frist_show").hide();
	}
	/**
	 * 微信支付统-下单
	 */
	 function WXPay(payed) {
		sendRequest(pageContextPath+"/sysGold/wxPayMoney.do", {"payed":payed}, function(jsonData) {
			var returndata=jsonData.data;
			//统一下单成功后，调起微信支付页面
			if(returndata.result_code=="SUCCESS" && returndata.return_code=="SUCCESS"){
				onBridgeReady(returndata);
			} else{
				alert("统一下单失败");
			}
		});
	}

	 /**
		 微信支付页面 用户点击确认支付，
		 输入密码 JS API支付接口（getBrandWCPayRequest） 
		 参考http://www.cnblogs.com/txw1958/p/weixin-jsapi-pay.html
		**/
	 function onBridgeReady(data){
	   	WeixinJSBridge.invoke(
	       	'getBrandWCPayRequest', //支付url
	       	{
	           "appId" : data.appId,     //公众号名称，由商户传入     
	           "timeStamp" : data.timeStamp, //时间戳，自1970年以来的秒数     
	           "nonceStr" : data.nonceStr, //随机串     
	           "package" : data.prepay_id,     
	           "signType" : data.signType, //微信签名方式：     
	           "paySign" : data.paySign //微信签名 
	       },//data 参数
	       function(res){     
	           if(res.err_msg == 'get_brand_wcpay_request:cancel' || res.err_msg == 'get_brand_wcpay_request:fail') {
		      	  //支付取消或失败，什么也不做
		       } else{
		    	   //已经微信支付成功，下面开始做页面相关的内容展示以及自己服务器端相关操作
	        	   gold=price;
	        	   paygold(price);
		       }
			// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	       } //回调方法
	   ); 
	}
</script>
</head>

<body bgcolor="efeff4">
	<div class="brokerage_contant" style="display:none"></div>
	<div class="frist_showto"></div>
	<!-- 右上角的提示 -->
	<div class="frist_show" style="display:none" id="frist_show">
		点击分享，获取分享奖励和流量奖励！赶快来参加哦！
		<div class="iifrsubmit" onclick="deshow2()">知道了</div>
	</div>

	<div class="wpage">
		<div class="iiinfo">
			<!-- 标题 ${information.title}并没有用''包起来，因为这里不是定一个变量var-->
			<div class="iitittle">${information.title}</div>
			<!-- 副标题  时间浏览量等-->
			<div class="iiwriter">
				<p class="aidd1">${information.created_time}</p>
				<p class="aidd2">${information.expert}</p>
				<p class="aidd2">${information.information_name}</p>
				<p class="aidd4">${award_money}</p>
				<p class="aidd3">${information.browse_count}</p>
			</div>
			<!-- 音频文件显示 -->
			<div class="iiplayer">
				<!-- information.audio_url不为空执行 -->
				<c:if test="${not empty information.audio_url}">
					<audio controls="controls" preload="auto"
						src="${information.audio_url}" style="width: 100%;">
					</audio>
				</c:if>
			</div>
			<!-- 正文 -->
			<div class="iiarticle">${information.content}</div>
			<!-- 标签 -->
			<div class="iilabel">
				<c:forEach var="marks" items="${marks}">
					<p>${marks.mark_name}</p>
				</c:forEach>
			</div>
			<!-- 打赏 -->
			<div class="iireward">
				<p>
					<img src="${path}/page/images/reward.png" />
				</p>
				<p>打&nbsp;赏</p>
			</div>
			
			<!-- 金币充足的提示 -->
			<div class="iipayover" id="payover1" style="display:none">
				<div class="iipaysuc">打赏成功！</div>
				<div class="iisubmit" onclick="deshow()">确认</div>
			</div>

			<!-- 金币不充足的提示 -->
			<div class="iipayover" id="payover2" style="display:none">
				<div class="iipaym">您的金币余额不足</div>
				<div class="iidinfo">
					<!-- 用div css来模拟radio -->
					<div class="iipcheck">
						<div class="iiradio checked" id="payAll1"></div>
						<p>全部身家都给你</p>
					</div>
					<div class="iipcheck">
						<div class="iiradio" id="payAll2"></div>
						<p>用微信支付</p>
					</div>
				</div>
				<div class="iisubmit1" onclick="payAll()">确认</div>
				<div class="iisubmit2" onclick="deshow()">取消</div>
			</div>

			<!-- 无金币的提示 -->
			<div class="iipayover" id="payover3" style="display:none">
				<div class="iipaym2">您的金币不足，是否微信支付？</div>
				<div class="iisubmit1" onclick="payByWX()">确认</div>
				<div class="iisubmit2" onclick="deshow()">取消</div>
			</div>
			
			
			
			<div class="iipay">
				<div class="iipay1" onclick="doreward(this)" price="0.88">0.88元</div>
				<div class="iipay2" onclick="doreward(this)" price="1.66">1.66元</div>
				<div class="iipay3" onclick="doreward(this)" price="6.66">6.66元</div>
			</div>
		</div>

		<div class="iifoot">
			<div class="iireinfo">
				<p class="rinfo1">打赏记录</p>
				<p class="rinfo2" id="rinfo2">总打赏人数：${rewarderscount}人</p>
			</div>
			<div class="iipic" id="iipic">
				<c:forEach var="user" items="${rewarders}">
					<p>
						<img src="${user.head_portrait}" />
					</p>
				</c:forEach>
			</div>
		</div>
	</div>
	
<!-- 引入JS文件 微信jsapi  js-sdk -->
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
		/**
		JSSDK使用步骤
		步骤一：绑定域名 先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
		步骤二：引入JS文件 http://res.wx.qq.com/open/js/jweixin-1.0.0.js
		步骤三：通过config接口注入权限验证配置 所有需要使用JS-SDK的页面必须先注入配置信息，否则将无法调用
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '', // 必填，公众号的唯一标识
		    timestamp: , // 必填，生成签名的时间戳
		    nonceStr: '', // 必填，生成签名的随机串
		    signature: '',// 必填，签名，见附录1
		    jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		步骤四：通过ready接口处理成功验证
		wx.ready(function(){
	    	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
	    	// config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
	    	// 则须把相关接口放在ready函数中调用来确保正确执行。
	    	// 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		});
		步骤五：通过error接口处理失败验证
		wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
		**/
	$(function(){
		//alert(gold);
		/***
			jquery的get请求 $.get(url,params,function(data){},'json');
		***/
		$.get('${path}/wechat/jssdk/sign.do', '', function(data){
			if(data.code != 0){
				return ;
			} 
			var d = data.data;
			//wx对象是在引入的JS文件里定义微信端生成的。 
			wx.config({
			    appId: d.appId, 
			    timestamp: d.timestamp,
			    nonceStr: d.nonceStr,
			    signature: d.signature,
			    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage']
			});
			//modelMap.put("shareConfig", share);
			wx.ready(function(){
				wx.onMenuShareTimeline({//分享到朋友圈
					title: '${shareConfig.title}', 
					link: '${shareConfig.link}', 
					desc: '${shareConfig.content}',
					imgUrl: '${shareConfig.imgUrl}',
				    success: function () { 
				    	//分享成功后调用方法
				    	shareCallback(1);
				    },
				    cancel: function () { 
				    	//取消分享后调用方法
				    }
				});
				wx.onMenuShareAppMessage({//分享给朋友
					title: '${shareConfig.title}', 
					link: '${shareConfig.link}', 
					desc: '${shareConfig.content}', 
					imgUrl: '${shareConfig.imgUrl}', 
				    success: function () {
				    	//分享成功后调用方法
				    	shareCallback(2);
				    },
				    cancel: function () { 
				    	//取消分享后调用方法
				    }
				});
		   });
		},'json');
			
		
		
		var openId = '${openId}';
		var information_id='${information_id}';
		function shareCallback(type){
			sharesucdo();
			$.get('${path}/wechat/share/callback.do', {'openId':openId,'information_id':information_id,'type':type}, function(data){
				if(data.code == 0){
					return ;
				}
			},'json');
		}
		
		$(".iiradio").click(function(){
			$this=$(this);
			$(".iiradio").removeClass('checked');
			$this.addClass('checked');
		});
		
		var fristshow=${fristshow };
		if(fristshow==0){
			$(".frist_show").show();
		} else {//之前是关注后，第一次就显示，现在每次都显示
			$(".frist_show").show();
		}
	});
</script>
<%@ include file="footer.jsp"%>