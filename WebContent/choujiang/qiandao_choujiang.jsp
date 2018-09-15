﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 禁止页面放大缩小 -->
<meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>签到抽奖</title>
<link href="${pageContext.request.contextPath}/choujiang/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/choujiang/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/choujiang/js/awardRotate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/httpService.js"></script>

<!-- jquery-ui插件的样式 -->
<link href="${pageContext.request.contextPath}/thirdparty/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/thirdparty/layer/layer.js"></script>

<script type="text/javascript">
var turnplate={
		restaraunts:[],				//大转盘奖品名称
		colors:[],					//大转盘奖品区块对应背景颜色
		outsideRadius:192,			//大转盘外圆的半径
		textRadius:155,				//大转盘奖品位置距离圆心的距离
		insideRadius:68,			//大转盘内圆的半径
		startAngle:0,				//开始角度
		bRotate:false				//false:停止;ture:旋转
};
//用户的openid
var userid = "${openId}";
//用户的可用抽奖币个数
var count = "${count}";
//count = 5;
//奖品
var prize1 = "新疆核桃";
var prize2 = "新疆葡萄干";

$(document).ready(function(){
	//alert(userid+"--"+count);
	//动态添加大转盘的奖品与奖品区域背景颜色
	turnplate.restaraunts = [prize1, "1抽奖币", "谢谢参与", "1抽奖币", prize2, "谢谢参与", "2抽奖币 ", "谢谢参与", "2抽奖币", "谢谢参与"];
	turnplate.colors = ["#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF","#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF","#FFF4D6", "#FFFFFF"];
	
	var rotateTimeOut = function (){
		$('#wheelcanvas').rotate({
			angle:0,
			animateTo:2160,
			duration:8000,
			callback:function (){
				//alert('网络超时，请检查您的网络设置！');
				layer.alert('网络异常', {
	        	    skin: 'layui-layer-lan'
	        	    ,closeBtn:0
	        	    ,anim: 1 //动画类型
	        	});
			}
		});
	};

	//旋转转盘 item:奖品位置; txt：提示语;
	var rotateFn = function (item, txt){
		var angles = item*(360/turnplate.restaraunts.length)-(360/(turnplate.restaraunts.length*2));
		if(angles<270){
			angles = 270 - angles; 
		}else{
			angles = 360 - angles + 270;
		}
		$('#wheelcanvas').stopRotate();
		$('#wheelcanvas').rotate({
			angle:0,
			animateTo:angles+1800,
			duration:8000,
			callback:function (){
				//中奖后累计
				if(txt.indexOf("1抽奖币")!=-1){
					count=count+1;
				} else if(txt.indexOf("2抽奖币")!=-1){
					count=count+2;
				} else if(txt.indexOf("3抽奖币")!=-1){
					count=count+3;
				} else if(txt.indexOf("4抽奖币")!=-1){
					count=count+4;
				} else if(txt.indexOf("5抽奖币")!=-1){
					count=count+5;
				}
				//alert("count="+count);
				sendRequest(pageContextPath+"/wechat/updateUserchoujiang.do", {"count":count}, 
					function(jsonData){
						if(jsonData.issucessful){
							if(txt.indexOf(prize1)!=-1){
								sendRequest(pageContextPath+"/wechat/savePrize.do", {"prize":prize1}, 
									function(jsonData){
									if(jsonData.isSavePrizeSucessful){
										//alert("恭喜您获得了:"+txt+"，记得联系我们给您发货哦。");
										layer.alert("恭喜您获得了:"+txt+"，记得联系我们给您发货哦。", {
							        	    skin: 'layui-layer-lan'
							        	    ,closeBtn:0
							        	    ,anim: 1 //动画类型
							        	});
									}
								}); 
							}
							if(txt.indexOf(prize2)!=-1){
								sendRequest(pageContextPath+"/wechat/savePrize.do", {"prize":prize2}, 
									function(jsonData){
									if(jsonData.isSavePrizeSucessful){
										//alert("恭喜您获得了:"+txt+"，记得联系我们给您发货哦。");
										layer.alert("恭喜您获得了:"+txt+"，记得联系我们给您发货哦。", {
							        	    skin: 'layui-layer-lan'
							        	    ,closeBtn:0
							        	    ,anim: 1 //动画类型
							        	});
									}
								}); 
							}
							if(txt.indexOf("谢谢参与")!=-1){
								//alert("机会总会有的，再来一把。");
								layer.alert("机会总会有的，再来一把。", {
					        	    skin: 'layui-layer-lan'
					        	    ,closeBtn:0
					        	    ,anim: 1 //动画类型
					        	});
							}
							if(txt.indexOf("抽奖币")!=-1){
								//alert("恭喜您获得了:"+txt+"，还可以继续抽奖哦。");
								layer.alert("恭喜您获得了:"+txt+"，还可以继续抽奖哦。", {
					        	    skin: 'layui-layer-lan'
					        	    ,closeBtn:0
					        	    ,anim: 1 //动画类型
					        	});
							}
						} 
					}); 
				turnplate.bRotate = !turnplate.bRotate;
			}
		});
	};

	
	$('.pointer').click(function (){
		if(count > 0){
			count--;//抽一次就少一次
			if(turnplate.bRotate)return;
			turnplate.bRotate = !turnplate.bRotate;
			//获取随机数(奖品个数范围内)
			var item = rnd(1,turnplate.restaraunts.length);
			//奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
			rotateFn(item, turnplate.restaraunts[item-1]);
			console.log(item+"-"+turnplate.restaraunts[item-1]);
		} else {
			//alert('您的抽奖币不够了，明天再来吧');
			layer.alert('您的抽奖币不够了，明天再来吧', {
        	    skin: 'layui-layer-lan'
        	    ,closeBtn:0
        	    ,anim: 1 //动画类型
        	});
		}
	});
});

function rnd(n, m){
	var random = Math.floor(Math.random()*(m-n+1)+n);
	return random;
}


//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
window.onload=function(){
	drawRouletteWheel();
};

function drawRouletteWheel() {    
  var canvas = document.getElementById("wheelcanvas");    
  if (canvas.getContext) {
	  //根据奖品个数计算圆周角度
	  var arc = Math.PI/(turnplate.restaraunts.length/2);
	  var ctx = canvas.getContext("2d");
	  //在给定矩形内清空一个矩形
	  ctx.clearRect(0,0,422,422);
	  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
	  ctx.strokeStyle = "#FFBE04";
	  //font 属性设置或返回画布上文本内容的当前字体属性
	  ctx.font = '16px Microsoft YaHei';      
	  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
		  var angle = turnplate.startAngle + i * arc;
		  ctx.fillStyle = turnplate.colors[i];
		  ctx.beginPath();
		  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
		  ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);    
		  ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
		  ctx.stroke();  
		  ctx.fill();
		  //锁画布(为了保存之前的画布状态)
		  ctx.save();   
		  
		  //----绘制奖品开始----
		  ctx.fillStyle = "#E5302F";
		  var text = turnplate.restaraunts[i];
		  var line_height = 17;
		  //translate方法重新映射画布上的 (0,0) 位置 211
		  ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 
				  211 + Math.sin(angle + arc / 2) * turnplate.textRadius);
		  
		  //rotate方法旋转当前的绘图
		  ctx.rotate(angle + arc/2 + Math.PI/2);
		  
		  /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
		  if(text.indexOf("M")>0){//流量包
			  var texts = text.split("M");
			  for(var j = 0; j<texts.length; j++){
				  ctx.font = j == 0?'bold 20px Microsoft YaHei':'16px Microsoft YaHei';
				  if(j == 0){
					  ctx.fillText(texts[j]+"M", -ctx.measureText(texts[j]+"M").width / 2, j * line_height);
				  }else{
					  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
				  }
			  }
		  }else if(text.indexOf("M") == -1 && text.length>6){//奖品名称长度超过一定范围 
			  text = text.substring(0,6)+"||"+text.substring(6);
			  var texts = text.split("||");
			  for(var j = 0; j<texts.length; j++){
				  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
			  }
		  }else{
			  //在画布上绘制填色的文本。文本的默认颜色是黑色
			  //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
			  ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
		  }
		  
		  //添加对应图标
		  if(text.indexOf("抽奖币")!=-1){
			  var img= document.getElementById("shan-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  }; 
			  ctx.drawImage(img,-15,10);  
		  }else if(text.indexOf("谢谢参与")!=-1){
			  var img= document.getElementById("sorry-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  };  
			  ctx.drawImage(img,-15,10);  
		  }
		  //把当前画布返回（调整）到上一个save()状态之前 
		  ctx.restore();
		  //----绘制奖品结束----
	  }     
  } 
}

</script>
</head>
<body style="background: #e62d2d; overflow-x: hidden;">
	<img src="${pageContext.request.contextPath}/choujiang/images/1.png" id="shan-img" style="display: none;" />
	<img src="${pageContext.request.contextPath}/choujiang/images/2.png" id="sorry-img" style="display: none;" />
	<div class="banner">
		<div class="turnplate"
			style="background-image: url(${pageContext.request.contextPath}/choujiang/images/turnplate-bg.png); background-size: 100% 100%;">
			<canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
			<img class="pointer" src="${pageContext.request.contextPath}/choujiang/images/turnplate-pointer.png" />
		</div>
	</div>
</body>
</html>