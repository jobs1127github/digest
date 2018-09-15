<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>精品资讯</title>

<script type="text/javascript">
$(function(){
	queryBoutique();	
});

function queryBoutique(){
	sendRequest("${path}/wechat/queryBoutique.do", null, function(jsonData) {
		var titleif=jsonData.tittle;
		$("#bttittle").html('');
		var tittle="";
		tittle+="<a href='"+titleif.content_url+"'>";
		tittle+="<div class='bttittle'>";
		tittle+="<img width='100%' src='"+titleif.title_img_url+"' style='max-height:20%;'>";
		tittle+="<div class='tittleinfo'>"+titleif.title+"</div>";
		tittle+="</a>";
		$("#bttittle").html(tittle);

		var list=jsonData.list;
		$("#btinfo").html('');
		var btlist="";
		for(var i=0;i<list.length;i++){
			btlist+="<a href='"+list[i].content_url+"'>";
			btlist+="<li>";
			btlist+=list[i].title;
			btlist+="<img src='"+list[i].title_img_url+"'>";
			btlist+="</li>";
			btlist+="</a>";
		}
		$("#btinfo").html(btlist);
	});
}
</script>
</head>

<body bgcolor="efeff4">


<div class="container">
<div>
<div id="bttittle">
</div>
<div class="btinfo" id="btinfo">
</div>


</div>
</div>
</body>
<%@ include file="footer.jsp"%>
