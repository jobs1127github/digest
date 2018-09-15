function loadSelectData(url, dadaType, showId, asy) {
	if (asy == undefined) {
		asy = true;
	}
	sendRequest(url, {
		"strDataType" : dadaType
	}, function(jsonData) {
		var data = jsonData.data;
		var html = "";
		$("#" + showId).empty();
		$("#" + showId).append("<option value='0'>请选择</option>");
		for ( var i = 0; i < data.length; i++) {
			//alert(data[i].dataItem);
			html += "<option value='" + data[i].dataItem + "'>"
					+ data[i].dataValue + "</option>";
		}
		//alert(showId);
		$("#" + showId).append(html);
	}, asy);
}
function loadSelectDataByJson(url,showId) {
	sendRequest(url, {
		"strDataType" : 'json'
	}, function(jsonData) {
		var data = jsonData.data;
		var html = "";
		$("#" + showId).empty();
		$("#" + showId).append("<option value='0'>请选择</option>");
		for ( var i = 0; i < data.length; i++) {
			//alert(data[i].dataItem);
			html += "<option value='" + data[i].dataItem + "'>"
					+ data[i].dataValue + "</option>";
		}
		//alert(showId);
		$("#" + showId).append(html);
	}, true);
}