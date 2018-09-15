//删除文件
        function delFile(_this){
	        if(confirm("是否确认需要删除?"))
	        {
	        	var tbResumeAccessoryUrl=$(_this).attr("tbResumeAccessoryUrl");
	        	sendRequest("../rest/tesumeaccessory/delFile",{"tbResumeAccessoryUrl":tbResumeAccessoryUrl},function(jsonData){
	        		alert(jsonData.data);
	        		init();
	        	});
        	}
        }
        //重置按钮
        function chongZi(formName,html){
        	document.getElementById(formName).reset();
        	window.location.href=html;
        }
        
        