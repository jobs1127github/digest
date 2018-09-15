//加载选择服务部门选项
    	function changeServiceDept(){
    		var tbCname=$("#tbServiceCustomerId").val();
    		sendRequest("../rest/customer/getCustomerInfo",{"tbCname":tbCname},function(jsonData){
				var data1 = jsonData.data;
				$("#serviceDeptName").empty();
				$("#serviceDeptName").append("<option value='0'>请选择</option>");
					for(var i=0;i<data1.length;i++)
					{
						 $("#serviceDeptName").append("<option value='"+data1[i].tbDeptName+"'>"+data1[i].tbDeptName+"</option>");
					}
					
			},false);
    	
    	}
    	
    	//加载项目经理选项
    	function changeManager(){
    		var tbDeptName=$("#serviceDeptName").val();
    		var cName=$("#tbServiceCustomerId").val();
    		sendRequest("../rest/customer/getManagerInfo",{"tbDeptName":tbDeptName,"cName":cName},function(jsonData){
				var data1 = jsonData.data;
				$("#tbCprojectManager").empty();
				$("#tbCprojectManager").append("<option value='0'>请选择</option>");
					for(var i=0;i<data1.length;i++)
					{
						 $("#tbCprojectManager").append("<option value='"+data1[i].tbCprojectManager+"'>"+data1[i].tbCprojectManager+"</option>");
					}
					
			},false);
    		
    	}
    	/**
		服务客户serviceCustomer
		*/
    	function getCustomer(){
    		
    		sendRequest("../rest/emplinfo/loadSelectData",{"param":"serviceCustomer"},function(jsonData){
    		var data1 = jsonData.data;
    		$("#tbServiceCustomerId").empty();
    		$("#tbServiceCustomerId").append("<option value='0'>请选择</option>");
    			for(var i=0;i<data1.length;i++)
    			{
    				 $("#tbServiceCustomerId").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
    			}
    		},false);
    	}
    	
    	