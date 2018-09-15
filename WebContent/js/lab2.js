$(function(){
	
	$('#b1').click(function(){
		$obj = $('#allTemplateContexts option:selected').clone(true);
		if($obj.size() == 0){
			alert("请至少选择一条!");
		}
		$('#templateContexts').append($obj);
		$('#allTemplateContexts option:selected').remove();
	});
	
	$('#b2').click(function(){
		$('#templateContexts').append($('#allTemplateContexts option'));
	});
	
	$('#b3').click(function(){
		$obj = $('#templateContexts option:selected').clone(true);
		if($obj.size() == 0){
			alert("请至少选择一条!");
		}
		$('#allTemplateContexts').append($obj);
		$('#templateContexts option:selected').remove();
	});
	
	$('#b4').click(function(){
		$('#allTemplateContexts').append($('#templateContexts option'));
	});
	
	$('option').dblclick(function(){
		var flag = $(this).parent().attr('id');
		if(flag == "allTemplateContexts"){
			var $obj = $(this).clone(true);
			$('#templateContexts').append($obj);
			$(this).remove();
		} else {
			var $obj = $(this).clone(true);
			$('#allTemplateContexts').append($obj);
			$(this).remove();
		}
	});
	
});
function previewImage(num)
{
	var file=$("#imageFile"+num)[0];
  var MAXWIDTH  = 260; 
  var MAXHEIGHT = 180;
  var div = document.getElementById('preview');
  if (file.files && file.files[0])
  {
      div.innerHTML ='<img id=imageDisplay1>';
      var img = document.getElementById('imageDisplay1');
      img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, 200, 140);
        img.width  =  rect.width;
        img.height =  rect.height;
//         img.style.marginLeft = rect.left+'px';
        img.style.marginTop = rect.top+'px';
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
  }
  else //兼容IE
  {
    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
    file.select();
    var src = document.selection.createRange().text;
    div.innerHTML = '<img id=imageDisplay1>';
    var img = document.getElementById('imageDisplay1');
    img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
    status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
    div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
  }
}
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
         
        if( rateWidth > rateHeight )
        {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else
        {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
     
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}  