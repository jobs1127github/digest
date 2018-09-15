<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>全部资讯</title>
<script type="text/javascript">
var count=0;
var allpage=0;
$(function(){
queryAllInformation();

sendRequest("${path}/wechat/queryallmarks.do", null, function(jsonData) {
		var data = jsonData.list;
		var type = jsonData.types;
		var str="<tr>";
		for (var j=0;j<type.length;j++){
			str+="<td><li class='uncheck'>"+type[j].mark_name+"</li></td>";
		}
			for (var i=0;i<data.length;i++){
			str+="<td><li id='mark"+data[i].mark_code+"' class='mark'>"+data[i].mark_name+"</li></td>";
			if(i==1 || i==6){
				str+="</tr><tr>";
				}
			}
			str+="</tr>";
			$("#broktable").html(str);
			
		var str2="<tr>";
		for(var j=0;j<type.length;j++){
			str2+="<td><li id='type"+type[j].mark_code+"' onclick='querytype("+type[j].mark_code+")'>"+type[j].mark_name+"</li></td>";
		}
			for(var i=0;i<data.length;i++){
			str2+="<td class='smark' id='smark"+data[i].mark_code+"'><li onclick='querymark("+data[i].mark_code+")''>"+data[i].mark_name+"</li></td>";
			}
			$("#aichecktable").html(str2);
		





			
	$('.mark').click(function(){
	   $this = $(this);
	 if ($this.hasClass('aisorted')) {
		$this.removeClass('aisorted');
		$("#s"+this.id).hide();
	 }else{
		$this.addClass('aisorted');
		$("#s"+this.id).show();
	 }
	 });
	 sendRequest("${path}/wechat/querymark.do", null, function(jsonData) {
			var data = jsonData.list;
			
			$(".brok_main li").removeClass('aisorted2');
			$(".smark").hide();
			for (var i=0;i<data.length;i++){
				$("#mark"+data[i].mark_code).addClass('aisorted');
				$("#smark"+data[i].mark_code).show();
			};
			


			});

});
	allpag=Math.ceil(count/10);
	$('#pager').html(1+'/'+allpag);

	
});

function goinformation(_this){
	location.href = '${path}/wechat/informationInfo.do?information_id='+_this.attributes.information_id.value; 

	
}
function fristpage(){
	var beginnum=parseInt(document.getElementById("beginnum").value);
	if(beginnum==0){
		return;
	}else{
		beginnum=0;
		document.getElementById("beginnum").value=beginnum;
		queryAllInformation();
	}
}

function perpage(){
	var beginnum=parseInt(document.getElementById("beginnum").value);
	if(beginnum==0){
		return;
	}else{
		beginnum-=10;
		document.getElementById("beginnum").value=beginnum;
		queryAllInformation();
	}
}
function lastpage(){
	var beginnum=parseInt(document.getElementById("beginnum").value);
	if(beginnum>count-10){
		return;
	}else{
		beginnum+=(allpag-1)*10;
		document.getElementById("beginnum").value=beginnum;
		queryAllInformation();
	}
}
function nextpage(){
	var beginnum=parseInt(document.getElementById("beginnum").value);
	if(beginnum>count-10){
		return;
	}else{
		beginnum+=10;
		document.getElementById("beginnum").value=beginnum;
		queryAllInformation();
	}
}
function querymark(num){
	$(".aicheck li").removeClass('aisorted2');
	$("#smark"+num+" li").addClass('aisorted2');
	document.getElementById("information_type").value="";
	document.getElementById("mark").value=""+num;
	queryAllInformation();
}
function querytype(num){
	$(".aicheck li").removeClass('aisorted2');
	$("#type"+num).addClass('aisorted2');
	document.getElementById("mark").value="";
	document.getElementById("information_type").value=""+num;
	queryAllInformation();	
}
function queryorder(num){
	document.getElementById("orderby").value=""+num;
	queryAllInformation();	
	$('.aisort li').removeClass('aisorted2');
	$('#ap'+num).addClass('aisorted2');
}

function queryAllInformation(){

	var mark=document.getElementById("mark").value;
	var information_type=document.getElementById("information_type").value;
	var orderby=document.getElementById("orderby").value;
	var beginnum=document.getElementById("beginnum").value;
	if(beginnum==null){
		beginnum='0';
	}
	sendRequest("${path}/wechat/queryAllInformation.do", {"mark":mark,"information_type":information_type,"orderby":orderby,"beginnum":beginnum},function(jsonData){
		var data = jsonData.list;
		$("#aiinfo").html('');
		var page="";
		count=jsonData.count;
		allpag=Math.ceil(count/10);
		var pagenow=Math.ceil(beginnum/10)+1;
		$('#pager').html(pagenow+'/'+allpag);
		for(var i=0;i<data.length;i++){
		page+="<li onclick='goinformation(this)' information_id='"+data[i].information_id+"'><div class='infoimg'><img src='"+data[i].tile_img_url+"'></div><div class='aitittle'>";
		page+="<div class='aidl1'>"+data[i].title+"</div>";
		page+="<div class='aidl2'>";
		page+="<p class='aidd1'>"+data[i].created_time+"</p>";
		page+="<p class='aidd2'>"+data[i].expert+"</p>";
		page+="<p class='aidd4'>"+data[i].share_count+"</p>";
		page+="<p class='aidd3'>"+data[i].browse_count+"</p></div></div></li>";
		}
		$("#aiinfo").html(page);
		});
}
function showdown(){
$(".brokerage_contant").show();
$(".brok_main").show();
}

function savemarks(){
	var arr=document.getElementsByClassName('aisorted');
	var marks="";
	for(var i=0;i<arr.length;i++){
		marks+=arr[i].id.substr(4,1)+",";
	}
	sendRequest("${path}/wechat/savemarks.do", {"marks":marks}, function(jsonData){});
	deshow();
}

function deshow(){
	$(".brokerage_contant").hide();
	$(".brok_main").hide();
}
</script>
</head>

<from id="queryfrom">
<div>
	<input type="hidden" id="beginnum" name="beginnum" value="0">
</div>
<div>
	<input type="hidden" id="mark" name="mark">
</div>
<div>
	<input type="hidden" id="information_type" name="information_type">
</div>
<div>
	<input type="hidden" id="orderby" name="orderby" value="1">
</div>
</from>
<body bgcolor="efeff4" style="position:relative;">
	<div class="brokerage_contant" style="display:none"></div>
	<div class="container">
		<div class="page">
			<div class="brok_main" style="display:none">
				<dl>
					点击标签可切换添加
					<a onClick="savemarks()">确定</a>
				</dl>
				<dt>
					<table width="100%" id="broktable">
					</table>
				</dt>
			</div>
			<div class="aContainer">
				<div class="aicheckContainer">
					<div class="aicheck">
						<table id="aichecktable">
						</table>
					</div>
				</div>
				<div class="aicmore" onClick="showdown()">
					<img src="${path}/page/images/aicmore.png" width="50%">
				</div>
			</div>
			<div class="aisort">
				<p>智能排序:</p>
				<li id="ap1" class="p1 aisorted2" onclick="queryorder(1)">时间</li>
				<li id="ap2" class="p2" onclick="queryorder(2)">打赏金额</li>
				<li id="ap3" class="p3" onclick="queryorder(3)">浏览量</li>
			</div>
			<div class="aiinfo" id="aiinfo"></div>
		</div>
		<div class="footer">
			<p onclick="fristpage()">首页</p>
			<p onclick="perpage()">
				<< 上一页
			</p>
			<p id="pager"></p>
			<p onclick="nextpage()">下一页 >></p>
			<p onclick="lastpage()">尾页</p>
		</div>
	</div>
	<%@ include file="footer.jsp"%>