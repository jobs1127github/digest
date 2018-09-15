<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<title>新疆厂部通讯录</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/the_address_book2/css/style.css">
<style type="text/css">
.my{
text-overflow:ellipsis; 
white-space:nowrap; 
overflow:hidden; 
width:50px; 
}
</style>
</head>
<body>
	<div id="listbox">
	<div id="letter" ></div>
	<div id="ulist" class="sort_box">
		
	</div>
	<div class="initials">
		<ul>
			<li><img src="${pageContext.request.contextPath}/the_address_book2/image/068.png"></li>
		</ul>
	</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/httpService.js"></script>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>
	<script>
		$(function () {
			$.ajax({
				type: "GET",
				url: pageContextPath+"/wechat/query_contact_factory.do",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				async:false,
				success: function (res) {
					for(var i=0;i<res.length;i++){
						$("#ulist").append(
								'<div class="sort_list">'
								+'<div class="num_logo">'
								+'<a style="color:#000000" href="tel:'+res[i].tel+'">'
								+'<img src='+pageContextPath+'/the_address_book2/image/'+res[i].img+' alt="">'
								+'</a>'
								+'</div>'
								+'<div class="num_name">'
								+'<a style="color:#000000" class="my" href="tel:'+res[i].tel+'">'
								+res[i].name
								+'</a>'
								+'</div>'
								+'</div>'
						)
					}
				}
			});
		})
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/the_address_book2/js/jquery.charfirst.pinyin.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/the_address_book2/js/sort.js"></script>
</body>
</html>