<%@ page contentType="text/html; charset=utf-8"%>
<head>
<title>hello world</title>
</head>
<body>
	<h1>hello world</h1>
	<script type="text/javascript">
		function PreviewImage(imgFile) {
			var filextension = imgFile.value.substring(imgFile.value
					.lastIndexOf("."), imgFile.value.length);
			filextension = filextension.toLowerCase();
			if ((filextension != '.jpg') && (filextension != '.gif')
					&& (filextension != '.jpeg') && (filextension != '.png')
					&& (filextension != '.bmp')) {
				alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 !");
				imgFile.focus();
			} else {
				var path;
				if (document.all)//IE
				{
					imgFile.select();
					path = document.selection.createRange().text;

					document.getElementById("imgPreview").innerHTML = "";
					document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\""
							+ path + "\")";//使用滤镜效果  
				} else//FF
				{
					path = imgFile.files[0].getAsDataURL();
					document.getElementById("imgPreview").innerHTML = "<img id='img1' width='120px' height='100px' src='"+path+"'/>";
					// document.getElementById("img1").src = path;
				}
			}
		}
	</script>
	上传图片:
	<input type="file" name="file" style="width: 200px; height: 20px;"
		onchange="PreviewImage(this)" id="upload" />
	<div id="imgPreview"
		style="width:120px; height:100px;margin-left: 280px;"></div>
</body>
