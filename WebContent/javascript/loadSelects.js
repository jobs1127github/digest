function loadStaffSelect(){
	//加载员工级别下拉框数据
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"levelId"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbStaffLevel").empty();
	$("#tbStaffLevel").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbStaffLevel").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"skillJobid"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbSkillJobid").empty();
	$("#tbSkillJobid").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbSkillJobid").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"maritalstatus"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbMaritalstatus").empty();
	$("#tbMaritalstatus").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbMaritalstatus").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"educateleve"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbEducatelevelid").empty();
	$("#tbEducatelevelid").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbEducatelevelid").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"workplace"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbWorkplace").empty();
	$("#tbWorkplace").append("<option value='0'>请选择</option>");
	
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbWorkplace").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"levelId"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbPartyrank").empty();
	$("#tbPartyrank").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbPartyrank").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	//var department = tbDepartment
	/**
	 * 加载部门
	 */
	/*sendRequest("../rest/emplinfo/loadDepartment",{},function(jsonData){
	var data1 = jsonData.data;
	$("#tbDepartment").empty();
	$("#tbDepartment").append("<option value='0'>请选择</option>");
	
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbDepartment").append("<option value='"+data1[i].tbOrganizationid+"'>"+data1[i].tbDepartmentName+"</option>");
		}
	},false);*/
	
	/**
	服务客户serviceCustomer
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"serviceCustomer"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbServiceCustomerId").empty();
	$("#tbServiceCustomerId").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbServiceCustomerId").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	
	/**
		所属机构tbAgencyId
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"agencyId"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbAgencyId").empty();
	$("#tbAgencyId").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbAgencyId").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	
	
	/**
		下拉框之性别
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"sex"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbSex").empty();
	//$("#tbSex").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbSex").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	
	/**
		检查有没有孩子tbChildren
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"children"},function(jsonData){
	var data1 = jsonData.data;
		$("#tbChildren").empty();
		$("#tbChildren").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbChildren").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false); 
	/**
		tbStaffFloatId在职状态
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"staffFloat"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbStaffFloatId").empty();
	$("#tbStaffFloatId").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbStaffFloatId").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	/**
		tbMinzu民族
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"minzu"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbMinzu").empty();
	$("#tbMinzu").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbMinzu").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	/**
		tbZzmm政治面貌
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"zzmm"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbZzmm").empty();
	$("#tbZzmm").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbZzmm").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	
	/**
		户口类型tbHkType
	*/
	sendRequest("../rest/emplinfo/loadSelectData",{"param":"hkType"},function(jsonData){
	var data1 = jsonData.data;
	$("#tbHkType").empty();
	$("#tbHkType").append("<option value='0'>请选择</option>");
		for(var i=0;i<data1.length;i++)
		{
			 $("#tbHkType").append("<option value='"+data1[i].dataItem+"'>"+data1[i].dataValue+"</option>");
		}
	},false);
	
	
	
}