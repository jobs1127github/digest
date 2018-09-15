<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>全部资讯</title>
<script type="text/javascript">
var gold = 6.5;
function doreward(_this){
	//转换成float类型
	price= parseFloat(_this.attributes.price.value);
	if(gold>=price){
		paygold(price);//用户金币充足的情况
	} else if(gold <price && gold>1){//金币不足，但有1元金币
		show2();
	} else{
		show3();//无金币
	}
}

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
function sharesucdo(){
	$("#frist_show").html('');
	var frist_show="分享成功，每天前五次分享可以获得金币哦！";
	frist_show+="<div class='iifrsubmit' onclick='deshow2()'>知道了</div>";
	$("#frist_show").html(frist_show);
	$(".frist_show").show();
}
function show1(){
	$(".brokerage_contant").show();
	$("#payover1").show();
}
function show2(){
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

function payByWX(){
	var payed= Math.round((price-gold)*100);
	WXPay(payed);
}
function deshow(){
	$(".brokerage_contant").hide();
	//通过class来拿取元素
	$(".iipayover").hide();
}

function deshow2(){
	$(".frist_show").hide();
}
/*
 * 微信支付统-下单
 */
 function WXPay(payed){
		sendRequest(pageContextPath+"/sysGold/wxPayMoney.do", {"payed":payed}, function(jsonData) {
				var returndata=jsonData.data;
				if(returndata.result_code=="SUCCESS" && returndata.return_code=="SUCCESS"){
						onBridgeReady(returndata);
				} else{
					alert("统一下单失败");
				}
				});
		
		}

 function onBridgeReady(data){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId" : data.appId,     //公众号名称，由商户传入     
	           "timeStamp" : data.timeStamp,         //时间戳，自1970年以来的秒数     
	           "nonceStr" : data.nonceStr, //随机串     
	           "package" : data.prepay_id,     
	           "signType" : data.signType,         //微信签名方式：     
	           "paySign" : data.paySign //微信签名 
	       },
	       function(res){     
	           if(res.err_msg == 'get_brand_wcpay_request:cancel' || res.err_msg == 'get_brand_wcpay_request:fail') {
		           }
	           else{
	        	   gold=price;
	        	   paygold(price);
		          }
	                // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	       }
	   ); 
	}
</script>

</head>

<body bgcolor="efeff4">
aa
	<div class="brokerage_contant" style="display:none"></div>
	<div class="frist_showto"></div>
	<!-- 右上角的提示 -->
	<div class="frist_show" style="display:none" id="frist_show">
		点击分享，获取分享奖励和流量奖励！赶快来参加哦！
		<div class="iifrsubmit" onclick="deshow2()">知道了</div>
	</div>

	<div class="wpage">
		<div class="iiinfo">
			<!-- 标题 -->
			<div class="iitittle">aa</div>
			<!-- 副标题  时间浏览量等-->
			<div class="iiwriter">
				<p class="aidd1">aa</p>
				<p class="aidd2">aa</p>
				<p class="aidd2">aa</p>
				<p class="aidd4">aa</p>
				<p class="aidd3">aa</p>
			</div>
			<!-- 正文 -->
			<div class="iiarticle">hahah</div>
			<!-- 打赏 -->
			<div class="iireward">
				<p>
					<img src="${path}/page/images/reward.png" />
				</p>
				<p>打&nbsp;赏</p>
			</div>

			<div class="iipayover" id="payover1" style="display:none">
				<div class="iipaysuc">打赏成功！</div>
				<div class="iisubmit" onclick="deshow()">确认</div>
			</div>

			<div class="iipayover" id="payover2" style="display:none">
				<div class="iipaym">您的金币余额不足</div>
				<div class="iidinfo">
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
	</div>
	<%@ include file="footer.jsp"%>