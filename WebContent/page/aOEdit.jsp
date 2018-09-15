<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="header.jsp"%>
<title>我@全泰修改信息</title>
<script src="${path}/page/dev/jquery-1.9.1.js"></script>
<script src="${path}/page/dev/js/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
<script src="${path}/page/dev/js/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
<link href="${path}/page/dev/css/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css" />
<link href="${path}/page/dev/css/mobiscroll.animation-2.5.2.css" rel="stylesheet" type="text/css" />
<script src="${path}/page/dev/js/mobiscroll.android-ics-2.5.2.js" type="text/javascript"></script>
<link href="${path}/page/dev/css/mobiscroll.android-ics-2.5.2.css" rel="stylesheet" type="text/css" />
<script src="${path}/page/dev/js/mobiscroll.select-2.5.1.js" type="text/javascript"></script>
</head>
<body bgcolor="efeff4">
	<div class="container">
		<div class="aedinfo">
			<form method="post" action="${path}/wechat/updateUserInfo.do"
				name="myform">
				<li>
					<p>昵称：</p> <input type="text" value="${user.username}"
					name="username" maxlength="4"></li>
				<li>
					<p>地区：</p> <span class="js-area">${user.province}&nbsp;${user.city}</span>
				</li>
				<li>
					<p>性别：</p> 
					<input type="hidden" name="sex" /> 
					<input type="hidden" name="province" value="${user.province}" /> 
					<input type="hidden" name="city" value="${user.city}" />
					<div class="radio" data-value="1"></div>
					<p>男</p>
					<div class="radio" data-value="2"></div>
					<p>女</p></li>
				<li>
					<p>年龄：</p> <input type="text" class="uncheck" value="${user.age}"
					name="age" /></li>
			</form>
			<div class="placecheck" style="display: none">
				<select id="demo"
					onchange='GetOptgroup(this.options[this.selectedIndex]);'>
					<optgroup label="北京">
						<option value="0"></option>
						<option value="东城区">东城区</option>
						<option value="西城区">西城区</option>
						<option value="3">崇文区</option>
						<option value="4">宣武区</option>
						<option value="5">朝阳区</option>
						<option value="6">丰台区</option>
						<option value="7">石景山区</option>
						<option value="8">海淀区</option>
						<option value="9">门头沟区</option>
						<option value="10">房山区</option>
						<option value="11">通州区/
						<option>
						<option value="12">顺义区</option>
						<option value="13">昌平区</option>
						<option value="14">大兴区</option>
						<option value="15">怀柔区</option>
						<option value="16">平谷区</option>
					</optgroup>
					<optgroup label="上海">
						<option value="黄浦区">黄浦区</option>
						<option value="18">卢湾区</option>
						<option value="19">徐汇区</option>
						<option value="20">长宁区</option>
						<option value="21">静安区</option>
						<option value="22">普陀区</option>
						<option value="23">闸北区</option>
						<option value="24">虹口区</option>
						<option value="25">杨浦区</option>
						<option value="26">闵行区</option>
						<option value="27">宝山区</option>
						<option value="28">嘉定区</option>
						<option value="29">浦东新区</option>
						<option value="30">金山区</option>
						<option value="31">松江区</option>
						<option value="32">青浦区</option>
						<option value="33">南汇区</option>
						<option value="34">奉贤区</option>
					</optgroup>
					<optgroup label="天津">
						<option value="35">和平区</option>
						<option value="36">河东区</option>
						<option value="37">河西区</option>
						<option value="38">南开区</option>
						<option value="39">河北区</option>
						<option value="40">红桥区</option>
						<option value="41">塘沽区</option>
						<option value="42">汉沽区</option>
						<option value="43">大港区</option>
						<option value="44">东丽区</option>
						<option value="45">西青区</option>
						<option value="46">津南区</option>
						<option value="47">北辰区</option>
						<option value="48">武清区</option>
						<option value="49">宝坻区</option>
					</optgroup>
					<optgroup label="重庆">
						<option value="50">江津市</option>
						<option value="51">合川市</option>
						<option value="52">永川市</option>
						<option value="53">南川市</option>
					</optgroup>
					<optgroup label="四川">
						<option value="54">成都市</option>
						<option value="55">自贡市</option>
						<option value="56">攀枝花市</option>
						<option value="57">泸州市</option>
						<option value="58">德阳市</option>
						<option value="59">绵阳市</option>
						<option value="60">广元市</option>
						<option value="61">遂宁市</option>
						<option value="62">内江市</option>
						<option value="63">乐山市</option>
						<option value="64">南充市</option>
						<option value="65">眉山市</option>
						<option value="66">宜宾市</option>
						<option value="67">广安市</option>
						<option value="68">达州市</option>
						<option value="69">雅安市</option>
						<option value="70">巴中市</option>
						<option value="71">资阳市</option>
						<option value="72">阿坝藏族羌族自治州</option>
						<option value="73">甘孜藏族自治州</option>
						<option value="74">凉山彝族自治州</option>
					</optgroup>
					<optgroup label="贵州">
						<option value="75">贵阳市</option>
						<option value="76">六盘水市</option>
						<option value="77">遵义市</option>
						<option value="78">安顺市</option>
						<option value="79">铜仁地区</option>
						<option value="80">黔西南布依族苗族自治州</option>
						<option value="81">毕节地区</option>
						<option value="82">黔东南苗族侗族自治州</option>
						<option value="83">黔南布依族苗族自治州</option>
					</optgroup>
					<optgroup label="云南">
						<option value="84">昆明市</option>
						<option value="85">曲靖市</option>
						<option value="86">玉溪市</option>
						<option value="87">保山市</option>
						<option value="88">昭通市</option>
						<option value="89">丽江市</option>
						<option value="90">思茅市</option>
						<option value="91">临沧市</option>
						<option value="92">楚雄彝族自治州</option>
						<option value="93">红河哈尼族彝族自治州</option>
						<option value="94">文山壮族苗族自治州</option>
						<option value="95">西双版纳傣族自治州</option>
						<option value="96">大理白族自治州</option>
						<option value="97">德宏傣族景颇族自治州</option>
						<option value="98">怒江傈僳族自治州</option>
						<option value="99">迪庆藏族自治州</option>
					</optgroup>
					<optgroup label="西藏">
						<option value="100">拉萨市</option>
						<option value="101">昌都地区</option>
						<option value="102">山南地区</option>
						<option value="103">日喀则地区</option>
						<option value="104">那曲地区</option>
						<option value="105">阿里地区</option>
						<option value="106">林芝地区</option>
					</optgroup>
					<optgroup label="河南">
						<option value="107">郑州市</option>
						<option value="108">开封市</option>
						<option value="109">洛阳市</option>
						<option value="110">平顶山市</option>
						<option value="111">安阳市</option>
						<option value="112">鹤壁市</option>
						<option value="113">新乡市</option>
						<option value="114">焦作市</option>
						<option value="115">濮阳市</option>
						<option value="116">许昌市</option>
						<option value="117">漯河市</option>
						<option value="118">三门峡市</option>
						<option value="119">南阳市</option>
						<option value="120">商丘市</option>
						<option value="121">信阳市</option>
						<option value="122">周口市</option>
						<option value="123">驻马店市</option>
					</optgroup>
					<optgroup label="湖北">
						<option value="124">武汉市</option>
						<option value="125">黄石市</option>
						<option value="126">十堰市</option>
						<option value="127">宜昌市</option>
						<option value="128">襄樊市</option>
						<option value="129">鄂州市</option>
						<option value="130">荆门市</option>
						<option value="131">孝感市</option>
						<option value="132">荆州市</option>
						<option value="133">黄冈市</option>
						<option value="134">咸宁市</option>
						<option value="135">随州市</option>
						<option value="136">恩施土家族苗族自治州</option>
						<option value="137">省直辖行政单位</option>
					</optgroup>
					<optgroup label="湖南">
						<option value="138">长沙市</option>
						<option value="139">株洲市</option>
						<option value="140">湘潭市</option>
						<option value="141">衡阳市</option>
						<option value="142">邵阳市</option>
						<option value="143">岳阳市</option>
						<option value="144">常德市</option>
						<option value="145">张家界市</option>
						<option value="146">益阳市</option>
						<option value="147">郴州市</option>
						<option value="148">永州市</option>
						<option value="149">怀化市</option>
						<option value="150">娄底市</option>
						<option value="151">湘西土家族苗族自治州</option>
					</optgroup>
					<optgroup label="广东">
						<option value="152">广州市</option>
						<option value="153">韶关市</option>
						<option value="154">深圳市</option>
						<option value="155">珠海市</option>
						<option value="156">汕头市</option>
						<option value="157">佛山市</option>
						<option value="158">江门市</option>
						<option value="159">湛江市</option>
						<option value="160">茂名市</option>
						<option value="161">肇庆市</option>
						<option value="162">惠州市</option>
						<option value="163">梅州市</option>
						<option value="164">汕尾市</option>
						<option value="165">河源市</option>
						<option value="166">阳江市</option>
						<option value="167">清远市</option>
						<option value="168">东莞市</option>
						<option value="169">中山市</option>
						<option value="170">潮州市</option>
						<option value="171">揭阳市</option>
						<option value="172">云浮市</option>
					</optgroup>
					<optgroup label="广西">
						<option value="173">南宁市</option>
						<option value="174">柳州市</option>
						<option value="175">桂林市</option>
						<option value="176">梧州市</option>
						<option value="177">北海市</option>
						<option value="178">防城港市</option>
						<option value="179">钦州市</option>
						<option value="180">贵港市</option>
						<option value="181">玉林市</option>
						<option value="182">百色市</option>
						<option value="183">贺州市</option>
						<option value="184">河池市</option>
						<option value="185">来宾市</option>
						<option value="186">崇左市</option>
					</optgroup>
					<optgroup label="陕西">
						<option value="187">西安市</option>
						<option value="188">铜川市</option>
						<option value="189">宝鸡市</option>
						<option value="190">咸阳市</option>
						<option value="191">渭南市</option>
						<option value="192">延安市</option>
						<option value="193">汉中市</option>
						<option value="194">榆林市</option>
						<option value="195">安康市</option>
						<option value="196">商洛市</option>
					</optgroup>
					<optgroup label="甘肃">
						<option value="197">兰州市</option>
						<option value="198">嘉峪关市</option>
						<option value="199">金昌市</option>
						<option value="200">白银市</option>
						<option value="201">天水市</option>
						<option value="202">武威市</option>
						<option value="203">张掖市</option>
						<option value="204">平凉市</option>
						<option value="205">酒泉市</option>
						<option value="206">庆阳市</option>
						<option value="207">定西市</option>
						<option value="208">陇南市</option>
						<option value="209">临夏回族自治州</option>
						<option value="210">甘南藏族自治州</option>
					</optgroup>
					<optgroup label="青海">
						<option value="211">西宁市</option>
						<option value="212">海东地区</option>
						<option value="213">海北藏族自治州</option>
						<option value="214">黄南藏族自治州</option>
						<option value="215">海南藏族自治州</option>
						<option value="216">果洛藏族自治州</option>
						<option value="217">玉树藏族自治州</option>
						<option value="218">海西蒙古族藏族自治州</option>
					</optgroup>
					<optgroup label="宁夏">
						<option value="219">银川市</option>
						<option value="220">石嘴山市</option>
						<option value="221">吴忠市</option>
						<option value="222">固原市</option>
						<option value="223">中卫市</option>
					</optgroup>
					<optgroup label="新疆">
						<option value="224">乌鲁木齐市</option>
						<option value="225">克拉玛依市</option>
						<option value="226">吐鲁番地区</option>
						<option value="227">哈密地区</option>
						<option value="228">昌吉回族自治州</option>
						<option value="229">博尔塔拉蒙古自治州</option>
						<option value="230">巴音郭楞蒙古自治州</option>
						<option value="231">阿克苏地区</option>
						<option value="232">克孜勒苏柯尔克孜自治州</option>
						<option value="233">喀什地区</option>
						<option value="234">和田地区</option>
						<option value="235">伊犁哈萨克自治州</option>
						<option value="236">塔城地区</option>
						<option value="237">阿勒泰地区</option>
						<option value="238">省直辖行政单位</option>
					</optgroup>
					<optgroup label="河北">
						<option value="239">石家庄市</option>
						<option value="240">唐山市</option>
						<option value="241">秦皇岛市</option>
						<option value="242">邯郸市</option>
						<option value="243">邢台市</option>
						<option value="244">保定市</option>
						<option value="245">张家口市</option>
						<option value="246">承德市</option>
						<option value="247">沧州市</option>
						<option value="248">廊坊市</option>
						<option value="249">衡水市</option>
					</optgroup>
					<optgroup label="山西">
						<option value="250">太原市</option>
						<option value="251">大同市</option>
						<option value="252">阳泉市</option>
						<option value="253">长治市</option>
						<option value="254">晋城市</option>
						<option value="255">朔州市</option>
						<option value="256">晋中市</option>
						<option value="257">运城市</option>
						<option value="258">忻州市</option>
						<option value="259">临汾市</option>
						<option value="260">吕梁市</option>
					</optgroup>
					<optgroup label="内蒙古">
						<option value="261">呼和浩特市</option>
						<option value="262">包头市</option>
						<option value="263">乌海市</option>
						<option value="264">赤峰市</option>
						<option value="265">通辽市</option>
						<option value="266">鄂尔多斯市</option>
						<option value="267">呼伦贝尔市</option>
						<option value="268">巴彦淖尔市</option>
						<option value="269">乌兰察布市</option>
						<option value="270">兴安盟</option>
						<option value="271">锡林郭勒盟</option>
						<option value="272">阿拉善盟</option>
					</optgroup>
					<optgroup label="江苏">
						<option value="273">南京市</option>
						<option value="274">无锡市</option>
						<option value="275">徐州市</option>
						<option value="276">常州市</option>
						<option value="277">苏州市</option>
						<option value="278">南通市</option>
						<option value="279">连云港市</option>
						<option value="280">淮安市</option>
						<option value="281">盐城市</option>
						<option value="282">扬州市</option>
						<option value="283">镇江市</option>
						<option value="284">泰州市</option>
						<option value="285">宿迁市</option>
					</optgroup>
					<optgroup label="浙江">
						<option value="286">杭州市</option>
						<option value="287">宁波市</option>
						<option value="288">温州市</option>
						<option value="289">嘉兴市</option>
						<option value="290">湖州市</option>
						<option value="291">绍兴市</option>
						<option value="292">金华市</option>
						<option value="293">衢州市</option>
						<option value="294">舟山市</option>
						<option value="295">台州市</option>
						<option value="296">丽水市</option>
					</optgroup>
					<optgroup label="安徽">
						<option value="297">合肥市</option>
						<option value="298">芜湖市</option>
						<option value="299">蚌埠市</option>
						<option value="300">淮南市</option>
						<option value="301">马鞍山市</option>
						<option value="302">淮北市</option>
						<option value="303">铜陵市</option>
						<option value="304">安庆市</option>
						<option value="305">黄山市</option>
						<option value="306">滁州市</option>
						<option value="307">阜阳市</option>
						<option value="308">宿州市</option>
						<option value="309">巢湖市</option>
						<option value="310">六安市</option>
						<option value="311">亳州市</option>
						<option value="312">池州市</option>
						<option value="313">宣城市</option>
					</optgroup>
					<optgroup label="福建">
						<option value="314">福州市</option>
						<option value="315">厦门市</option>
						<option value="316">莆田市</option>
						<option value="317">三明市</option>
						<option value="318">泉州市</option>
						<option value="319">漳州市</option>
						<option value="320">南平市</option>
						<option value="321">龙岩市</option>
						<option value="322">宁德市</option>
					</optgroup>
					<optgroup label="江西">
						<option value="323">南昌市</option>
						<option value="324">景德镇市</option>
						<option value="325">萍乡市</option>
						<option value="326">九江市</option>
						<option value="327">新余市</option>
						<option value="328">鹰潭市</option>
						<option value="329">赣州市</option>
						<option value="330">吉安市</option>
						<option value="331">宜春市</option>
						<option value="332">抚州市</option>
						<option value="333">上饶市</option>
					</optgroup>
					<optgroup label="山东">
						<option value="334">济南市</option>
						<option value="335">青岛市</option>
						<option value="336">淄博市</option>
						<option value="337">枣庄市</option>
						<option value="338">东营市</option>
						<option value="339">烟台市</option>
						<option value="340">潍坊市</option>
						<option value="341">济宁市</option>
						<option value="342">泰安市</option>
						<option value="343">威海市</option>
						<option value="344">日照市</option>
						<option value="345">莱芜市</option>
						<option value="346">临沂市</option>
						<option value="347">德州市</option>
						<option value="348">聊城市</option>
						<option value="349">滨州市</option>
						<option value="350">荷泽市</option>
					</optgroup>
					<optgroup label="辽宁">
						<option value="351">沈阳市</option>
						<option value="352">大连市</option>
						<option value="353">鞍山市</option>
						<option value="354">抚顺市</option>
						<option value="355">本溪市</option>
						<option value="356">丹东市</option>
						<option value="357">锦州市</option>
						<option value="358">营口市</option>
						<option value="359">阜新市</option>
						<option value="360">辽阳市</option>
						<option value="361">盘锦市</option>
						<option value="362">铁岭市</option>
						<option value="363">朝阳市</option>
						<option value="364">葫芦岛市</option>
					</optgroup>
					<optgroup label="吉林">
						<option value="365">长春市</option>
						<option value="366">吉林市</option>
						<option value="367">四平市</option>
						<option value="368">辽源市</option>
						<option value="369">通化市</option>
						<option value="370">白山市</option>
						<option value="371">松原市</option>
						<option value="372">白城市</option>
						<option value="373">延边朝鲜族自治州</option>
					</optgroup>
					<optgroup label="黑龙江">
						<option value="374">哈尔滨市</option>
						<option value="375">齐齐哈尔市</option>
						<option value="376">鸡西市</option>
						<option value="377">鹤岗市</option>
						<option value="378">双鸭山市</option>
						<option value="379">大庆市</option>
						<option value="380">伊春市</option>
						<option value="381">佳木斯市</option>
						<option value="382">七台河市</option>
						<option value="383">牡丹江市</option>
						<option value="384">黑河市</option>
						<option value="385">绥化市</option>
						<option value="386">大兴安岭地区</option>
					</optgroup>
					<optgroup label="海南">
						<option value="387">海口市</option>
						<option value="388">三亚市</option>
						<option value="389">省直辖县级行政单位</option>
					</optgroup>
					<optgroup label="台湾">
						<option value="390">台湾特别行政区</option>
					</optgroup>
					<optgroup label="香港">
						<option value="391">香港特别行政区</option>
					</optgroup>
					<optgroup label="澳门">
						<option value="392">澳门特别行政区</option>
					</optgroup>
				</select>
			</div>
			<div class="aosubmit">确认</div>
		</div>
		
		<script type="text/javascript">
			var $number;
			$(document).ready(function() {
				$('#demo').mobiscroll().select({
					theme : 'android-ics light',
					lang : 'zh',
					display : 'inline',
					group : true,
					height : 40,
					width : 100,
					conText : "dwdwdwd",
					rows : 9,
					headerText : function(valueText) {
						return "选择您所在的城市";
					},
					formatResult : function(array) {
						$number = array[1].substr(1, array[1].length - 1);
						return " ";
					}
				});

				$('#show').click(function() {
					$('#demo').mobiscroll('show');
					return false;
				});

				$('#clear').click(function() {
					$('#demo').mobiscroll('clear');
					return false;
				});
			});

			function GetOptgroup(obj) {
				var optgroup = obj.parentNode;
				while (optgroup.previousSibling.label) {
					optgroup = optgroup.previousSibling;
				}
				if (optgroup.label) {
					var numberText;
					var number = document.getElementById("demo").value;
					var length = optgroup.getElementsByTagName("option");
					for ( var i = 0; i < length.length; i++) {
						if (number == length[i].value) {
							numberText = length[i].text;
						}
					}
					$('.js-area').text(optgroup.label + ' ' + numberText);
					//document.getElementById("didian").value = optgroup.label + " " + " " + numberText;
					$('input:hidden[name="province"]').val(optgroup.label);
					$('input:hidden[name="city"]').val(numberText);
				}
			}

			$(function() {
				$('.js-area').on('click', function() {
					$('.placecheck').toggle();
				});
				var msg = '${msg}';
				if (undefined != msg && msg != '') {
					alert(msg);
				}
				$('div.radio').click(
						function() {
							$this = $(this);
							$this.addClass('checked').siblings('div.radio').removeClass('checked');
							$('input:hidden[name="sex"]').val($this.attr('data-value'));
						});
				//保存信息
				$('.aosubmit').click(function() {
					var age = $('input:text[name=age]').val();
					if (age != null && age != '') {
						if (0 < parseInt(age) && parseInt(age) <= 199) {
							myform.submit();
						} else {
							alert('年龄在1到199之间');
						}
					} else {
						alert('请输入年龄！');
					}
				});

				var sex = '${user.sex}', index = 0, sexVal = '1';
				if ('2' == sex) {
					index = 1;
					sexVal = '2';
				}
				$('div.radio').eq(index).addClass('checked');
				$('input:hidden[name="sex"]').val(sexVal);
				$('#demo_dummy').remove();
			});
		</script>
	</div>
</body>
</html>