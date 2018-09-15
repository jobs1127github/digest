;$(function(){
	function onloadProductInfo(pageNo, params){
		var url=pageContextPath+"/product/rankinglist.do";
		sendRequest(url, "&pageSize=15&pageNo="+pageNo+"&"+params, function(data) {
			    var datas = data.data.list;
			    
			    $("#table2").html("");
			   	var table2th = "";
			   	table2th+="\<tr id='headtr'\>";
		
			   	if (datas==null){
			      	 $("#table2").html("\<th style='width:826px;'\>没有与之相关的产品\</th\>")
			    }
			   	else{
					    table2th+="\<th style='width:50px;' class='td_emplName'\>产品编号\</th\>";
					    
					    if(data.data.rankinglistType != '1'){
					    	 table2th+="\<th style='width:60px;' class='td_emplName'\>销售量\</th\>";
					    }else{
					    	table2th+="\<th style='width:30px;' class='td_emplSex'\>点击量\</th\>";
					    }
					    table2th+="\<th style='width:30px;' class='td_emplSex'\>日期\</th\>";
					    table2th+="\</tr\>";
					    var row = "";
			            for (var i = 0; i < datas.length; i++) {
			             	if (i % 2 == 0) {
			                    		row += "<tr>";
			                		} else {
			                    		row += "<tr style='background-color:#f4f8f9'>";
			                		}
		
			               row += "<td>" + datas[i].product_id + "</td>";
			               
			               if(data.data.rankinglistType != '1'){
			            	   row += "<td>" + datas[i].sales_volume + "</td>";
			               }else{
			            	   row += "<td>" + datas[i].click_volume + "</td>";
			               } 
			               row += "<td>" + datas[i].year + "年"+ datas[i].month+"月" ;
			               	if(datas[i].week!=null){	row +="，第"+datas[i].week + "周";}
			               row +="</td>";
			           	   row +="</tr>";
			     }
			    var footPage = "";
			            footPage+=	"\<tr align='right'\>";
			            footPage+=  "\<td class='pages' style='align:right;border-top:1px solid #bdd0db;' colspan='14'\>";
			            footPage+=  "\<div id='pagediv' class='fy'\>\</div\>";
			            footPage+=  "\</td\>";
			           	footPage+=	"\</tr\>"; 
			    $("#table2").html(table2th+row+footPage);
			   	}
			    $("#pagediv").children().remove();
			    var pageV = new pageView("#pagediv");
			    pageV.totalCount = data.data.count;
			    pageV.index = pageNo;
			    pageV.onclick = function(index) {
			    	onloadProductInfo(index,params);
			    };
			    pageV.render(); 
			    },true);
	}
    
	
	$('#queryBtn').on('click',function(){
		onloadProductInfo(1,$('#myForm').serialize());
	}).click();
	
	$('#restBtn').on('click',function(){
		 $('#myForm')[0].reset();
	});
})