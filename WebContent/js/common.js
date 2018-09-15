/**
 * 公共区域
 */
;+function(win){
	'use strict';
	//常用配置项
	var _Config = {
	   	errorTitle:'错误提示',
	   	infoTitle:'信息提示',
	   	questionTitle:'信息提示',
	   	warningTitle:'警告提示',
	   	projectName: 'digest'
	};
	
	var _StringBuffer = function(){
		this.array = [];
	};
	
	_StringBuffer.prototype = {
			append:function(str){
				this.array.push(str);
				return this;
			},
			toString:function(){
				return this.array.join('');
			},
			length:function(){
				return this.array.length;
			},
			clear:function(){
				this.array.length = 0;
				return this;
			}
	};
	
	var _Ajax = function(param){
		var _default = {
			error:function(){
				Alert('请求失败！请稍后重新请求或联系系统管理员！');		
			},
			async:true,
			beforeSend:function(){
			     
			},
			dataType:'json',
			method:'post'
		};
		_default = $.extend({}, _default, param);
		$.ajax({
			url: _default.url,
			async:_default.async,
			method: _default.method,
			dataType:_default.dataType,
			data:_default.data,
			success:function(data){
		          if(data.code == '00'){
		        	   _default.success(data);  
		          }else if(data.code == '01'){
		        	   //
		          }else if(data.code == '02'){
		        	  //
		          }else{
		        	  
		          }
		          //处理请求结束---
			}
			
		});
	};
	
	
	var _Dialog = function(param){
		var _default = {
			title : _Config.infoTitle,
			type : 0
		};
		_default = $.extend({}, _default, param);
		layer.open({
		    title: _default.title,
		    content: _default.content,
		    type: _default.type
		}); 	
	};
	
	var _Common = {
		StringBuffer:function(){
			return new _StringBuffer();
		},
		Config:_Config,
		Ajax:_Ajax,
		Dialog: _Dialog,
		Alert:function(str){
			_Dialog({'content':str});
		},
		Confirm:function(str, callBack){
			layer.confirm(str, function(index){
				 callBack();
				 layer.close(index);
			});  
		}
	};
	
	win.Common = _Common;
}(window);