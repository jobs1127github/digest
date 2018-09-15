package com.tentinet.weixin.action.wechat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.weixin.entity.RedPacket;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.HttpConntionUtil;
import com.tentinet.weixin.util.WXOrderPayDto;
import com.tentinet.weixin.util.WxPayUtil;

@Controller
@RequestMapping("/sysGold")
public class SysGoldAction extends WechatBaseAction {

	@Autowired
	private WXCMSClientService wXCMSClientService;
	@Autowired
	private WechatJssdkService xechatJssdkService;

	/*
	 * 
	 * 微信红包,发送金币
	 */
	@RequestMapping("/sedMoney.do")
	public void sedMoney(HttpServletResponse response,HttpServletRequest request) throws IOException {
		String session_openId = (String) request.getSession().getAttribute("openId");
		System.out.println("session_openId="+session_openId);
		String openId = request.getParameter("re_openid");
		System.out.println("_request_openId=" + openId);
		RedPacket redPacket = new RedPacket();
		// 红包由谁来支付（微信商户平台），构建支付订单
		redPacket.setMch_billno(WxPayUtil.build_mch_billno());
		// 红包发给谁（openId）
		redPacket.setRe_openid(openId);
		xechatJssdkService.sendRedPacket(redPacket);
		String data = "";
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 微信统一下单 
	 */
	@RequestMapping("/wxPayMoney.do")
	public void wxPayMoney(HttpServletResponse response,HttpServletRequest request, int payed) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		WXOrderPayDto orderPay = new WXOrderPayDto();
		orderPay.setBody("全泰支付");
		orderPay.setFee_type("CNY");
		orderPay.setOut_trade_no(WxPayUtil.build_mch_billno());
		orderPay.setProduct_id("201500020002");//商户自行定义，必填：否 
		orderPay.setOpenid(openId);
		String nowTime = com.tentinet.app.util.DateFill.DatetoString(new Date(),"yyyyMMddHHmmss");
		String time_expire = "";
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt;
		//开始时间+2个小时就是过期时间
		try {
			dt = sd.parse(nowTime);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.HOUR, 2);
			Date dt1 = rightNow.getTime();
			time_expire = sd.format(dt1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		orderPay.setTime_start(nowTime);
		orderPay.setTime_expire(time_expire);
		orderPay.setTotal_fee(payed);//这里的单位为分
		//构建微信请求统一下单的报文
		String requestXml = WxPayUtil.buildWXOrderRequestXml(orderPay);
		//请求url，发送xml报文
		HttpConntionUtil.sendWXRequestXml(requestXml);
		//获得微信返回的报文
		String str = HttpConntionUtil.getWXResponseXml();
		//解析返回报文,然后存放到Map中
		Map<String, String> WXReturnMap = WxPayUtil.buildXmlToMap(str);
		//构建网页端调起支付API 参数    确认支付页面
		Map<String, String> data = WxPayUtil.buildSendWXMap(WXReturnMap);
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}
}
