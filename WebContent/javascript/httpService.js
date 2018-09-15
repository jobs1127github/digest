//定义一个全局变量，项目的根路径

//获取当前网址，如：
var curWwwPath=window.document.location.href;
//获取主机地址之后的目录如：/Tmall/index.jsp
var pathName=window.document.location.pathname;
var pos=curWwwPath.indexOf(pathName);
//获取主机地址，如：//localhost:8080
var localhostPaht=curWwwPath.substring(0,pos);
//获取带"/"的项目名，如：/Tmall
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 

var pageContextPath=projectName;

/**
 * jquery中的ajax方法详解
 * 1、url: 要求为String类型的参数，（默认为当前页地址）发送请求的地址。
 * 2、type: 要求为String类型的参数，请求方式（post或get）默认为get。
 * 3、timeout:要求为Number类型的参数，设置请求超时时间（毫秒）。此设置将覆盖$.ajaxSetup()方法的全局设置。
 * 4、async:要求为Boolean类型的参数，默认设置为true，所有请求均为异步请求。
 * 如果需要发送同步请求，请将此选项设置为false。注意，同步请求将锁住浏览器，
 * 用户其他操作必须等待请求完成才可以执行。
 * 5、cache：要求为Boolean类型的参数，默认为true（当dataType为script时，默认为false），设置为false将不会从浏览器缓存中加载请求信息。
 * 6、data：要求为Object或String类型的参数，发送到服务器的数据。如果已经不是字符串，将自动转换为字符串格式。
 * 例如：
 * {foo1:"bar1",foo2:"bar2"}转换为&foo1=bar1&foo2=bar2。
 * 如果是数组，JQuery将自动为不同值对应同一个名称。
 * 例如{foo:["bar1","bar2"]}转换为&foo=bar1&foo=bar2。
 * 7、dataType：要求为String类型的参数，预期服务器返回的数据类型。
 * 如果不指定，JQuery将自动根据http包mime信息返回responseXML或responseText，
 * 并作为回调函数参数传递。可用的类型如下：
 * xml：返回XML文档，可用JQuery处理。
 * html：返回纯文本HTML信息；包含的script标签会在插入DOM时执行。
 * script：返回纯文本JavaScript代码。不会自动缓存结果。
 * 除非设置了cache参数。注意在远程请求时（不在同一个域下），所有post请求都将转为get请求。
 * json：返回JSON数据。
 * jsonp：JSONP格式。使用SONP形式调用函数时，例如myurl?callback=?，
 * JQuery将自动替换后一个“?”为正确的函数名，以执行回调函数。
 * text：返回纯文本字符串。
 * 8、beforeSend
 * 要求为Function类型的参数，发送请求前可以修改XMLHttpRequest对象的函数，例如添加自定义HTTP头。
 * 在beforeSend中如果返回false可以取消本次ajax请求。XMLHttpRequest对象是惟一的参数。
            function(XMLHttpRequest){
               this;   //调用本次ajax请求时传递的options参数
            }
 * 
 * 9、complete：要求为Function类型的参数，请求完成后调用的回调函数（请求成功或失败时均调用）。
 * 参数：XMLHttpRequest对象和一个描述成功请求类型的字符串。 
 * function(XMLHttpRequest, textStatus){
             this;    //调用本次ajax请求时传递的options参数
          }
 * 10、success：要求为Function类型的参数，请求成功后调用的回调函数，可以一个参数，也可以有两个参数。
 * 		(1)由服务器返回，并根据dataType参数进行处理后的数据。
        (2)描述状态的字符串。
         function(data, textStatus){
            //data可能是xmlDoc、jsonObj、html、text等等
            this;  //调用本次ajax请求时传递的options参数
         }
 * 11、error：要求为Function类型的参数，请求失败时被调用的函数。
 * 12、contentType：要求为String类型的参数，当发送信息至服务器时，
 * 内容编码类型默认为"application/x-www-form-urlencoded"。该默认值适合大多数应用场合。
 * 13、dataFilter：要求为Function类型的参数，给Ajax返回的原始数据进行预处理的函数。
 * 提供data和type两个参数。data是Ajax返回的原始数据，
 * type是调用jQuery.ajax时提供的dataType参数。函数返回的值将由jQuery进一步处理。
 * function(data, type){
                //返回处理后的数据
                return data;
            }
 * 
 * 14、global：要求为Boolean类型的参数，默认为true。表示是否触发全局ajax事件。设置为false将不会触发全局ajax事件，ajaxStart或ajaxStop可用于控制各种ajax事件。
 * 15、ifModified：要求为Boolean类型的参数，默认为false。仅在服务器数据改变时获取新数据。服务器数据改变判断的依据是Last-Modified头信息。默认值是false，即忽略头信息。
 * 16、jsonp：要求为String类型的参数，在一个jsonp请求中重写回调函数的名字。该值用来替代在"callback=?"这种GET或POST请求中URL参数里的"callback"部分，例如{jsonp:'onJsonPLoad'}会导致将"onJsonPLoad=?"传给服务器。
 * 17、username：要求为String类型的参数，用于响应HTTP访问认证请求的用户名。
 * 18、password：要求为String类型的参数，用于响应HTTP访问认证请求的密码。
 * 19、processData：要求为Boolean类型的参数，默认为true。默认情况下，发送的数据将被转换为对象（从技术角度来讲并非字符串）以配合默认内容类型"application/x-www-form-urlencoded"。如果要发送DOM树信息或者其他不希望转换的信息，请设置为false。
 * 20、scriptCharset：要求为String类型的参数，只有当请求时dataType为"jsonp"或者"script"，并且type是GET时才会用于强制修改字符集(charset)。通常在本地和远程的内容编码不同时使用。
 * 
 * 案例：
$(function(){
    $('#send').click(function(){
         $.ajax({
             type: "GET",
             url: "test.json",
             data: {username:$("#username").val(), content:$("#content").val()},
             dataType: "json",
             success: function(data){
                 $('#resText').empty();   //清空resText里面的所有内容
                 var html = ''; 
                 $.each(data, function(commentIndex, comment){
                       html += '<div class="comment"><h6>' + comment['username']
                                 + ':</h6><p class="para"' + comment['content']
                                 + '</p></div>';
                 });
                 $('#resText').html(html);
             }
         });
    });
});
*/



//$(function(){
//	//alert('加载httpService.js后就会立刻执行该方法');
//	//alert('pageContextPath='+pageContextPath);
//});


/**
 * 使用post方式向后台发送请求
 * @param url 请求的URL
 * @param data
 * 请求的参数, 
 * 参数可以通过序列化传输：var params = $("#login").serialize();
 * login为form表单的id。
 * 通过form表单的name标识，控制层通过参数对应的name来接收或对象（属性同名）来接收
 * @param succssFun
 * 加载成功后的函数，回调方法，请求返回时response返回数据时包含了dataType，如：json数据对象
 */
function sendRequest(url, data, succssFun,asy) {
	var async = true;//默认为true,异步请求。
    if(asy==undefined){
    	async = true;
    } 
    if(asy == false) {
    	async = false;//同步请求，必须等该请求执行完，才能在去请求其他的URL。
    }
    $.ajax({
        url:url,
        dataType:'json',
        type:'post',
        data:data,
        async:async,  
        success: function(jsonData) {//jsonData为response数据
        	//alert(jsonData.result);
            /**
             * 将后台响应的数据转换成json数据，function(jsonData)返回时执行的方法，即回调方法，
             * result如果该jsonData对象里没有该result属性，则jsonData.result为undefined
             * !undefined 为true.
             */
            if (!jsonData.result) {//result参考com.tentinet.app.util.OMSSecurityFilter.doFilter()
                //alert("errorCode="+jsonData.errorCode);
            	if (jsonData.errorCode == -2000) {
                    // 没有权限访问时就跳转到登录页面
                	top.location.href=pageContextPath+"/login.html";
                } else if (jsonData.errorCode == 0) {
                	//alert(0);
                    // alert("对不起，系统正忙请您稍后重试!");
                } else {
                	//alert(1);
                    succssFun(jsonData);//调用传递进来的参数函数。
                }
            } else {
            	//alert(2);
                succssFun(jsonData);
            }
        },
        error:function(jsonData) {
            // alert("对不起，系统正忙请您稍后重试!");
        }
    });
}
/**
 *  $.ajax(,async不设置默认为true
 * 使用get方式向后台发送请求
 * @param url 请求的URL
 * @param data 请求的参数
 * @param succssFun 加载成功后的函数
 */
function sendRequestByGet(url, data, succssFun) {
    $.ajax({
        url: url ,
        dataType: 'json',
        type:'get',
        data:data,
        complete:function(jsonData){
        	
        },
        success: function(jsonData) {
            if (!jsonData.result) {
                if (jsonData.errorCode == -2000) {
                    // 没有权限访问时就跳转到登录页面
                	top.location.href=pageContextPath+"/login.html";
                }  else if (jsonData.errorCode == 0) {
                    // alert("对不起，系统正忙请您稍后重试!");
                }else {
                    succssFun(jsonData);
                }
            } else {
                succssFun(jsonData);
            }
        },
        error:function(jsonData) {
           // alert("对不起，系统正忙请您稍后重试!");
        }
    });
}
/**
 * 获取url中的参数值,方法1
 * @param paras为?后的参数名，通过该方法能获取到该参数名对应的参数值
 */
function getURLParams(paras) {
    // var url = location.href;
    //var url = "http://192.168.1.73:9220/paoms/index.html?testParam=123";
    var url = window.location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {};
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}
/**
 * 获取地址栏?后的参数值，方法2
 * @param name传入?后的参数名
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
/**
 * 使用AJAX提交表单
 */
function submitForm(formId, url, successFun, errorFun) {
    $(formId).formSerialize();
    var options = {
        url:url,
        type:'POST',
        dataType:'json',
        success : function(jsonData) {
            if (!jsonData.result) {
                if (jsonData.errorCode == -2000) {
                    // 没有权限访问时就跳转到登录页面
                	top.location.href=pageContextPath+"/login.html";
                } else if (jsonData.errorCode == 0) {
                    //alert("对不起，系统正忙请您稍后重试!");
                } else {
                    errorFun(jsonData);
                }
            } else {
                successFun(jsonData);
            }
        }
    };
    $(formId).ajaxSubmit(options);
    return false;
}
/**
 * trim函数，重写了String的trim(),比如p="  aa  ",p.trim()后可以去掉aa左右前后的空格字符。
 * 空格开头或者空格结尾
 * ^是开始
 * \s是空白
 * *表示0个或多个
 * |是或者
 * $是结尾
 * g表示全局，g:执行全局匹配,而不是找到第一个匹配就停止。
 * 匹配首尾空格的正则表达式：(^\s*)|(\s*$)
*/
String.prototype.trim = function() {  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}; 

