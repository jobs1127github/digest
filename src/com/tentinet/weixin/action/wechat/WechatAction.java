package com.tentinet.weixin.action.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tentinet.weixin.service.EventService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.wechat.Articles;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;
import com.tentinet.weixin.util.wechat.Tools;
import com.tentinet.weixin.util.wechat.XStreamFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/***
 * 
 * 微信接口调用action
 * 
 * @author jobs1127
 *
 */
@Controller
public class WechatAction {
	private final static Logger log = LoggerFactory.getLogger(WechatAction.class);

	// 注入eventService事件业务接口
	@Autowired
	private EventService eventService;

	/**
	 * 接入微信接口：自己服务器的应用启动，然后配置微信成为开发者，把服务器应用的一个公开的URL配置微信
	 * 这样就实现了把请求交由微信接口，通过微信服务器处理后，在把请求转发到URL（自己的服务器地址）来。
	 * 
	 * 第一步：填写服务器配置 登录微信公众平台官网后，在公众平台官网的开发-基本设置页面，勾选协议成为开发者，
	 * 点击“修改配置”按钮，填写服务器地址（URL）、Token和EncodingAESKey， 其中URL是开发者用来接收微信消息和事件的接口URL。
	 * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）。
	 * EncodingAESKey由开发者手动填写或随机生成，将用作消息体加解密密钥。 第二步：验证消息的确来自微信服务器
	 * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：
	 * 微信认证后,给微信返回对应String,证明自己是可用的。
	 */
	@RequestMapping(value = "/wechat.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String wechat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 之前的域名是： http://www.51pharm.cn，项目名：digest,下面为全泰消化专家公众号的URL配置 请求微信服务器，URL：
		 * http://www.51pharm.cn/digest/wechat.do 带4个参数
		 */
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		Boolean isGet = request.getMethod().equals("GET");
		// log.info("jobs1127:"+DateUtil.format(new Date(), DateUtil.FMT_S_TIMES)+
		// "|signature:" + signature + " ,timestamp:" + timestamp+ " ,nonce:" + nonce +
		// " ,echostr:" + echostr);
		if (isGet) {// 如果是Get的方式，是接入时通过get方式请求微信服务器，用于校验请求是否来自于微信服务器
			/** 校验请求是否来自于微信，认证通过后，即成为开发者 */
			if (Tools.checkSignature(ConfigUtils.getValue("weixin.token"), signature, timestamp, nonce)) {
				// 微信接口约定：若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，接入生效，成为开发者成功
				response.getWriter().write(echostr);// 请求来自微信服务器，所以回应response给微信服务器。
				return null;
			} else {
				// log.info("jobs1127:"+DateUtil.format(new Date(), DateUtil.FMT_S_TIMES)+
				// "|微信请求非法签名, 请求被拒绝");
				return null;
			}
		} else {
			// post请求为其他的请求
			doPost(request, response);
		}
		return null;
	}

	/**
	 * 请求微信服务器，URL： http://www.51pharm.cn/digest/wechat.do 带4个参数
	 * 当不是get方式请求微信URL时，处理事件请求,结合DB,作出对应的操作 当成为开发者成功后，
	 * 只要不是Get，其他任何对公众平台的访问，都会调用该方法
	 * 
	 * doPost方法、doGet方法，都是请求微信服务器，此时request.getSession()获取到的session对象是微信服务器的会话对象。
	 * @param request
	 * @param response
	 */
	private void doPost(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 当用户发送消息给公众号时（或某些特定的用户操作引发的事件推送时），会产生一个POST请求，
		 * 开发者可以在响应包（Get）中返回特定XML结构，来对该消息进行响应（现支持回复文本、图片、图文、语音、视频、音乐）。
		 */
		/**
		 * 请求了微信接口，在响应微信服务器时，通过xml文件的方式把数据提交给微信服务器，
		 * 微信服务器获取到该xml文件后，会进行解析等（可能包括加密、解密等）操作，然后把相关信息转发到客户端。
		 * 
		 * 用户的请求，会包装成xml格式的文件去请求微信服务器，文本消息的内容格式： 
		 * <xml>
		 * <ToUserName><![CDATA[toUser]]></ToUserName>
		 * <FromUserName><![CDATA[fromUser]]></FromUserName>
		 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType>
		 * <Content><![CDATA[你好]]></Content> 
		 * </xml>
		 */
		/**
		 * 假如服务器无法保证在五秒内处理并回复，必须做出下述回复，
		 * 这样微信服务器才不会对此作任何处理，并且不会发起重试。 
		 * 1、直接回复success。
		 * 2、直接回复空串给用户公众号端（指字节长度为0的空字符串，而不是XML结构体中content字段的内容为空）。
		 */
		//设置响应的编码
		response.setCharacterEncoding("UTF-8");
		//设置响应的内容类型
		response.setContentType("text/xml");
		OutMessage oms = new OutMessage();
		try {
			// request是OpenID用户发起的请求，拿到该请求的输入流
			ServletInputStream in = request.getInputStream();
			/***
			 * 通过XStream对象来转换微信post回来的XML内容。
			 * Xstream使用学习地址：http://wenku.baidu.com/link?url=VAMHa7XCBJY1MEue7toh-ZJ7W0Tl4Qvj_b8du6JoR8Tq5IP3PK6o-AYPmeubBq3qGNmgBBKPg_hAQbz7ij9I63VxR9wlX3MreYKqOpAOaV3
			 */
			XStream xs = new XStream(new DomDriver());// Xstream可以把java对象与XML关联对应起来，互相装换
			// 给InMessage.class字节码对象取别名，方便日后xs.fromXML("xml字符串")获取该字节码对象的对象。
			xs.alias("xml", InMessage.class);// <xml></xml>格式数据转成InMessage.class类型
			// 把InputString转成String
			String xmlMsg = Tools.inputStream2String(in);
			/***
			 * 如果用户的请求为空，或null则可以不作出回应
			 */
			if (StringUtils.isBlank(xmlMsg)) {
				responseWxServerBlank(response);
			} else {
				/**
				 * 请求微信服务器，把请求转换成字符串， 发现原来去请求微信服务器的request对象是以xml格式的方式去请求服务器的。
				 */
				// log.info("jobs1127:"+"xmlMsg=="+xmlMsg);
				/***
				 * XStream反序列化，把String类型的XMl转成类对象。 把xml格式的请求，转换成了java类对象，方便处理。
				 */
				InMessage msg = (InMessage) xs.fromXML(xmlMsg);
				// 取得消息类型
				// String type = msg.getMsgType().toLowerCase();
				// log.info("消息类型，event:"+type);
				// 针对不同类型消息进行处理，上报地理位置事件：Event事件类型:scan,location地理位置,click点击事件
				// log.info("事件类型：" + msg.getEvent() + ":" + msg.getEventKey());
				/**
				 * openID访问公众平台，包括进入、点击、连接、等等动作，具体体现以xml格式的数据请求微信服务器。
				 * doPrecess做具体的事情，根据openID的访问情况来发消息给OpenID。 msg是用户的请求对象。
				 * oms是用户请求后，准备给用户发送消息的实体对象。
				 */
				//msg是用户请求时的消息，oms是微信服务器处理后，响应回去的消息。根据不同的事件响应不同的oms消息。
				oms = eventService.doPrecess(msg);// 对用户的请求，处理后，得到要准备给用户发送的消息对象。
				if (oms != null) {
					// 设置发送信息
					oms.setCreateTime(new Date().getTime());
					oms.setToUserName(msg.getFromUserName());
					oms.setFromUserName(msg.getToUserName());
					// fasle,表示获取默认的XStream对象。不支持CDATA标签。
					xs = XStreamFactory.init(false);
					// 别名xml和OutMessage.class，使得xml格式的文件可以与OutMessage.class字节码对象的对象相互转换。
					xs.alias("xml", OutMessage.class);// OutMessage类相关的数据，转换成xml标签格式的数据。
					// 别名item和 Articles.class，使得item标签格式的文件可以与Articles.class字节码对象的对象相互转换。
					xs.alias("item", Articles.class);// Articles类相关的数据，转换成item标签格式的数据。
					// 通过XStream对象把oms java类对象toXML转换成xml标签格式的文件，并把转换后的xml文件写入到response对象里。
					xs.toXML(oms, response.getWriter());// 序列号化xml，并回复用户OpenID
					log.info("mgsType=" + xs.toXML(oms));
					/**
					 * 把用户请求对象中的openID存放到当前request对象的session空间里。
					 * 注意：此时的用户请求，是请求微信服务器，故该request对象是微信服务器端的，所以session里的对象
					 * 是存放到微信服务器端的，而微信服务器端是不会给你存放的。在自己服务器端拿取该session值是获取不到的。
					 */
					//该用户的openID是用户发消息给微信服务器，微信服务器响应回来的，若想要就得发起请求获取，所以可以想办法把openID存放到自己服务器的session里。
					//request.getSession().setAttribute("openId", msg.getFromUserName());
				} else {
					responseWxServerBlank(response);
				}
			}
		} catch (Exception e) {
			responseWxServerBlank(response);
			e.printStackTrace();
		}
	}
	
	/***
	 * 如果没什么要回复用户，则直接回复空字符，告知微信服务器，
	 * 这样微信服务器才不会对此作任何处理，否则：该公众号暂时无法提供服务，请稍后再试。
	 * @param response
	 */
	private void responseWxServerBlank(HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}