package com.tentinet.weixin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.QianDao;
import com.tentinet.app.bean.UserCharLogVo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.weixin.entity.TemplateData;
import com.tentinet.weixin.entity.TemplateMsgContent;
import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.service.PrecessService;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.DateUtil;
import com.tentinet.weixin.util.wechat.Articles;
import com.tentinet.weixin.util.wechat.EventKeyType;
import com.tentinet.weixin.util.wechat.EventType;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;
import com.tentinet.weixin.util.wechat.Tools;

/**
 * 事件处理消息类
 */
@Service(value = "eventJob")
public class EventJob implements PrecessService {

	private final static Logger log = LoggerFactory.getLogger(EventJob.class);

	@Autowired
	private WechatJssdkService wechatJssdkService;

	@Autowired
	private WXCMSClientService wXCMSClientService;

	@Override
	public OutMessage doJob(InMessage msg) {
		OutMessage oms = null;
		String event = msg.getEvent().toLowerCase();// 得到具体是什么事件
		if (StringUtils.equals(EventType.SUBSCRIBE_EVENT_TYPE, event)) {// 关注
			//用户关注后，发送给用户的消息，1、保存关注者，2、发送图文消息给用户。
			//oms = buildSubscribe(msg);//图文消息
			oms = buildTextContent(msg);//文本消息
			
			//发红包：先发一个模板消息，通知用户红包情况(提示信息)，引导用户点击进入红包页面，然后发红包
			/**
			for (int i = 0; i <= 2; i++) {
				if (i == 2) {
					sendHbTemplateMsg(msg);
				}
			}
			**/
			//System.out.println("msg.getEventKey()="+msg.getEventKey());
			/***带标识的二维码 二维码相关的操作
			if(msg.getEventKey().contains("1127")) {//扫描药盒过来的二维码
				upate_biaoshi2wm("",msg.getFromUserName());
			} else if(msg.getEventKey().contains("qrscene_")) {//客户推广二维码
				String s = msg.getEventKey();
				try {
					int a = new Integer(s.substring("qrscene_".length()));
					upate_biaoshi2wm(a+"",msg.getFromUserName());
				} catch (NumberFormatException e) {
				}
			} else {
				String ek = msg.getEventKey();
				try {
					int a = new Integer(ek);
					if(a <= 50) {
						upate_biaoshi2wm(a+"",msg.getFromUserName());
					}
				} catch (NumberFormatException e) {
				}
			}
			***/
		} else if (StringUtils.equals(EventType.CLICK_EVENT_TYPE, event)) {// 点击事件
			/***
			 * 如果是点击事件，获取事件的key，知道用户点击的是什么菜单项。
			 */
			String eventKey = msg.getEventKey();
			//System.out.println("点击事件eventKey="+eventKey);
			if (StringUtils.equals(EventKeyType.CUSTOMER_SERVICE, eventKey)) {
				oms = buildCustomerServiceMsg(msg);//联系客服消息提示
			}
			if (StringUtils.equals(EventKeyType.qiandao_choujian, eventKey)) {
				oms = buildQiandaoServiceMsg(msg);//签到相关的消息提示
			}
			if (StringUtils.equals(EventKeyType.kaipiao, eventKey)) {
				oms = buildKaipiaoMsg(msg);//开票资料
			}
			if (StringUtils.equals(EventKeyType.product, eventKey)) {
				oms = imageAndTextMsgBuildProduct(new OutMessage());//产品资料
			}
			if (StringUtils.equals(EventKeyType.contact, eventKey)) {
				oms = imageAndTextMsgBuildContact(new OutMessage());//联系方式通讯录
			}
		} else if (StringUtils.equals(EventType.UNSUBSCRIBE_EVENT_TYPE, event)) {
			buildUnSubscribe(msg);// 取消事件
		} else {
			//log.info(DateUtil.format(new Date(), DateUtil.FMT_S_TIMES)+ "|未匹配任何事件");
		}
		return oms;
	}
	
	/**
	 * 构建文本返回的内容
	 */
	private OutMessage buildTextContent(InMessage msg){
		// 保存首次关注的用户
		getWechatUserInfo(msg.getFromUserName());
		//响应文本消息
		OutMessage oms=new OutMessage();
		oms.setContent("您好，欢迎成为【全安药业】好伙伴，我们将竭诚为您服务……");
		return oms;
	}
	/**
	 * 得到微用户的信息
	 * @param openid
	 * @return
	 */
	private WechatUser getWechatUserInfo(String openid) {
		System.out.println("getWechatUserInfo.openid="+openid);
		WechatUser wechatUser = wechatJssdkService.getWechatUserInfo(openid);
		return wechatUser;
	}
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	public void upate_biaoshi2wm(String a,String openid) {
		WXUserVo wx = wXCMSClientService.getWechatUserInfo(openid);
		wx.setPublicno("药盒二维码"+a);
		wx.setStatus("Y");
		wx.setUpdated_time(format.format(new Date()));
		wXCMSClientService.updateWXUserVo(wx);
		System.out.println("upate_biaoshi2wm已关注的用户，再次扫二维码过来啦……");
	}
	/**
	 * 构建首次关注,保存或更新用户的信息，并发一个图文消息欢迎页面
	 * @return
	 */
	public OutMessage buildSubscribe(InMessage msg) {
		OutMessage oms = null;
		// 得到连接的token
		//log.info(DateUtil.format(new Date(), DateUtil.FMT_S_TIMES) + "|执行关注事件");
		//String token = wechatJssdkService.getToken();// 得到token
		//log.info("accessToken:" + token);
		// 保存首次关注的用户
		getWechatUserInfo(msg.getFromUserName());
		// 组装首页关注的页面的信息
		oms = buildHomePage();
		return oms;
	}
	
	/**
	 * 构建红包的信息
	 * @param msg
	 * @return
	 */
	public boolean sendHbTemplateMsg(InMessage msg) {
		boolean isResult = false;
		try {
			// 给用户首次发关注所得金币
			// 给用户发首次关注红包
			boolean isdb = wXCMSClientService.isAttentionRedPacket(msg.getFromUserName());
			if (!isdb) {//判断当前访问的用户，是否已经给其发过红包，如果没有则给其发红包。
				/**
				 * 根据微信的模板消息，生成模板消息
				 */
				TemplateMsgContent tmsg = builsHBMsgData(msg);
				//发送模板消息
				wechatJssdkService.sendTemplateMsg(tmsg);
				isResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}
	
	
	/**
	 * 微信公众平台登录，菜单 功能：模板消息，点击进入，去微信已经固定好的模板里选择符合自己需求的模板，
	 * 微信审核通过后，便可以生成自己的模板消息 对应的模板。
	 * 微信公众平台，申请的模板消息的模板：详细内容
		{{first.DATA}}
		充值金额：{{keyword1.DATA}}
		充值门店：{{keyword2.DATA}}
		当前余额：{{keyword3.DATA}}
		{{remark.DATA}}
		
		
		在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
		第一个参数：first
		第二个参数：keyword1，还可以设置参数的颜色
	 * @param msg
	 * @return
	 * 这是个模板消息，必须先申请模板，然后把模板id配置在weixin-config-properties里
	 */
	public TemplateMsgContent builsHBMsgData(InMessage msg) {
		TemplateMsgContent tMsg = new TemplateMsgContent();
		tMsg.setTemplate_id(ConfigUtils.getValue("template_id"));
		tMsg.setTopcolor(ConfigUtils.getValue("topcolor"));
		tMsg.setTouser(msg.getFromUserName());
		String hb_template_url = ConfigUtils.getValue("hb_template_url");
		/***
		 * 字符串占位符的使用：
		 * hb_template_url=http://www.51pharm.cn/digest/page/label.jsp?re_openid=%s
		 * %s为字符串占位符，表示那个位置将来会传递一个参数过去。
		 * 可以通过String.format()方法把%s初始化，并结合成新的字符串。
		 */
		String new_link_url = String.format(hb_template_url,msg.getFromUserName());
		tMsg.setUrl(new_link_url);
		//模板消息里的内容
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#333366");
		first.setValue("全知消化专家--现金红包");
		data.put("first", first);

		TemplateData keyword1 = new TemplateData();
		keyword1.setColor("#333366");
		keyword1.setValue("现金红包");
		data.put("keyword1", keyword1);

		TemplateData keyword2 = new TemplateData();
		keyword2.setColor("#333366");
		keyword2.setValue("活动-打赏消化");
		data.put("keyword2", keyword2);

		TemplateData keyword3 = new TemplateData();
		keyword3.setColor("#333366");
		keyword3.setValue("永久");
		data.put("keyword3", keyword3);

		TemplateData remark = new TemplateData();
		remark.setColor("#333366");
		remark.setValue("微信扫一扫，关注即可领红包和金币。关注“全泰消化专家”，乐享健康资讯，微信红包大礼等你拿！金币在我＠全泰中可以查询."
				+ "您还有一个微信红包未领取！");
		data.put("remark", remark);
		tMsg.setData(data);
		return tMsg;
	}

	
	/**
	 * 构建微信首页信息,这个就是图文消息
	 * 参考网页：https://www.cnblogs.com/txw1958/p/weixin-97-news.html?_t_t_t=0.7894438626244664
	 * 在微信公众平台消息中，发送被动响应消息中的图文消息的XML结构如下所示。
	 * <xml>
		    <ToUserName><![CDATA[toUser]]></ToUserName>
		    <FromUserName><![CDATA[fromUser]]></FromUserName>
		    <CreateTime>12345678</CreateTime>
		    <MsgType><![CDATA[news]]></MsgType>
		    <ArticleCount>2</ArticleCount>
		    <Articles>
		        <item>
		            <Title><![CDATA[title1]]></Title> 
		            <Description><![CDATA[description1]]></Description>
		            <PicUrl><![CDATA[picurl]]></PicUrl>
		            <Url><![CDATA[url]]></Url>
		        </item>
		        <item>
		            <Title><![CDATA[title]]></Title>
		            <Description><![CDATA[description]]></Description>
		            <PicUrl><![CDATA[picurl]]></PicUrl>
		            <Url><![CDATA[url]]></Url>
		        </item>
		    </Articles>
		</xml>
	 */
	private OutMessage buildHomePage() {
		OutMessage oms = new OutMessage();
		oms.setArticleCount(1);//控制图文数量，大于则为多图文消息
		oms.setMsgType("news");
		List<Articles> list = new ArrayList<Articles>();
		Articles entity = new Articles(ConfigUtils.getValue("welcome.wtitle"),
				ConfigUtils.getValue("welcome.wcontent"),
				ConfigUtils.getValue("welcome.wimgUrl"),
				ConfigUtils.getValue("welcome.url"));
		list.add(entity);
		oms.setArticles(list);
		return oms;
	}

	/**
	 * 微用户取消对本微信号的关注
	 */
	public void buildUnSubscribe(InMessage msg) {
		log.info(DateUtil.format(new Date(), DateUtil.FMT_S_TIMES)+ "|执行取消订阅事件");
		boolean isResult = wXCMSClientService.unSubscribeWX(msg.getFromUserName());
		if (isResult) {
			log.info(msg.getFromUserName() + "取消关注公众号成功.");
		} else {
			log.error(msg.getFromUserName() + "取消关注公众号失败.");
		}
	}

	/**
	 * 电话联系人
	 * 
	 * @param msg
	 * @param wechatUser
	 * @return
	 */
	public OutMessage dialingEvent(InMessage msg) {
		OutMessage out = new OutMessage();
		out.setContent("联系电话:13686164585");
		return out;
	}
	/**
	 * 开票资料
	 * @param msg
	 * @return
	 */
	public OutMessage buildKaipiaoMsg(InMessage msg) {
		OutMessage out = new OutMessage();
		out.setContent("三证合一开票资料:\n公司名称:\n新疆全安药业股份有限公司\n税号:\n91652801679272984W\n地址:\n新疆巴州库尔勒市塔什店镇矿山路北侧\n电话:0996-2187061\n开户行:\n交通银行巴音郭楞分行营业部\n账号：\n658100301018010053921");
		return out;
	}

	public OutMessage buildCustomerServiceMsg(InMessage msg) {
		OutMessage out = new OutMessage();
		out.setContent("HI~您好，我是全泰消化专家，有什么可以帮到您？目前在线客户较多，如果我没有及时回复您，您可以拨打我们的健康资讯热线15818557397，工作时间：09:00~17:30");
		return out;
	}
	/***
	 * 全安好伙伴签到抽奖
	 * @param msg
	 * @return
	 */
	public OutMessage buildQiandaoServiceMsg(InMessage msg) {
		OutMessage out = new OutMessage();
		boolean exist = wXCMSClientService.isExistQianDao(msg.getFromUserName());
		QianDao qd = null;
		if(!exist) {
			qd = new QianDao();
			//保存签到信息
			saveOrUpdateQianDao(qd,msg);
			//构建图文消息
			imageAndTextMsgBuild(out,1,qd.getOpenid());
		} else {
			qd = wXCMSClientService.getQianDao(msg.getFromUserName());
			boolean todayok = wXCMSClientService.isQianDaoToday(qd.getOpenid());
			if(!todayok) {
				//更新签到信息
				saveOrUpdateQianDao(qd,msg);
				//构建图文消息
				imageAndTextMsgBuild(out,qd.getCount()==null?0:qd.getCount(),qd.getOpenid());
			} else {
				String notes = "今天"+DateUtil.DatetoString(new Date(),"yyyy-MM-dd")
					+"已签到成功，您当前可用抽奖币："+(qd.getCount()==null?0:qd.getCount())+"个，赶紧去抽奖吧。";
				out.setContent(notes);
			}
		}
		return out;
	}
	
	/***
	 * 保存或更新签到信息
	 * qd要求不能为null，要么new对象，要么传进来一个已经存在的qd对象
	 * msg要求不能为null
	 */
	private void saveOrUpdateQianDao(QianDao qd,InMessage msg) {
		WXUserVo wx = wXCMSClientService.getWechatUserInfo(msg.getFromUserName());
		System.out.println("wx="+wx+"---msg.username="+msg.getFromUserName());
		if(wx != null) {
			qd.setCity(wx.getCity());
			qd.setCountry(wx.getCountry());
			qd.setDate(Tools.getYMD(new Date()));
			qd.setNickname(wx.getNickname());
			qd.setProvince(wx.getProvince());
			qd.setPublicno(wx.getPublicno());
			qd.setTime(Tools.getHHmm(new Date()));
			qd.setUsername(wx.getUsername());
			if(qd.getOpenid() == null) {
				System.out.println("save..");
				qd.setOpenid(wx.getOpenid());
				qd.setCount(1);
				wXCMSClientService.saveQianDao(qd);
			} else {
				System.out.println("update..");
				qd.setCount(qd.getCount()==null?1:qd.getCount()+1);
				wXCMSClientService.updateQianDao(qd);
			}
		}
	}
	/***
	 * 构建图文消息
	 * @param out 需要响应给用户的消息
	 * @param count 签到的总次数
	 * @param userid 用户的id
	 * @return
	 */
	private OutMessage imageAndTextMsgBuild(OutMessage out,Integer count,String userid) {
		if(out == null) {
			return null;
		}
		out.setArticleCount(1);
		out.setMsgType("news");
		//String url = "http://www.51pharm.cn/digest/choujiang/qiandao_choujiang.jsp?userid="+userid+"&count="+count;
		String url = "http://www.51pharm.cn/digest/wechat/youhui.do?type=5";
		String imgurl = "http://www.51pharm.cn/digest/img/choujiang.png";
		List<Articles> list = new ArrayList<Articles>();
		Articles entity = new Articles("签到成功，获得了1个抽奖币哦，您当前可用抽奖币："+count+"个",
				"赶紧去抽奖吧",
				imgurl,url);
		list.add(entity);
		out.setArticles(list);
		return out;
	}
	
	/***
	 * 构建产品知识图文消息
	 * @param out 响应给用户的信息
	 * @param title 标题
	 * @param description 描述
	 * @param url 跳转链接
	 * @param imgurl 图片
	 * @return
	 */
	private OutMessage imageAndTextMsgBuildProduct(OutMessage out) {
		if(out == null) {
			return null;
		}
		out.setArticleCount(3);
		out.setMsgType("news");
		List<Articles> list = new ArrayList<Articles>();
		String title="全安®安胃疡胶囊";
		String description="唯一治疗消化性溃疡的国基植物药——全安®安胃疡胶囊";
		String imgurl="http://www.51pharm.cn/digest/img/product_A.png";
		String url="http://www.51pharm.cn/digest/img/product_A.pptx";
		
		String title2="复方银翘氨敏胶囊";
		String description2="复方银翘氨敏胶囊";
		String imgurl2="http://www.51pharm.cn/digest/img/product_Y.png";
		String url2="http://www.51pharm.cn/digest/img/product_Y.pptx";
		
		String title3="复方甘草浙贝氯化铵片";
		String description3="复方甘草浙贝氯化铵片";
		String imgurl3="http://www.51pharm.cn/digest/img/product_Z.png";
		String url3="http://www.51pharm.cn/digest/img/product_Z.pptx";
		
		Articles entity1 = new Articles(title,description,imgurl,url);
		Articles entity2 = new Articles(title2,description2,imgurl2,url2);
		Articles entity3 = new Articles(title3,description3,imgurl3,url3);
		list.add(entity1);
		list.add(entity2);
		list.add(entity3);
		out.setArticles(list);
		return out;
	}
	/***
	 * 构建通讯录图文消息
	 * @param out 响应给用户的信息
	 * @param title 标题
	 * @param description 描述
	 * @param url 跳转链接
	 * @param imgurl 图片
	 * @return
	 */
	private OutMessage imageAndTextMsgBuildContact(OutMessage out) {
		if(out == null) {
			return null;
		}
		out.setArticleCount(2);
		out.setMsgType("news");
		List<Articles> list = new ArrayList<Articles>();
		String title="营销中心通讯录";
		String description="全安药业通讯录，为您提供深圳营销中心和新疆厂部的通讯服务";
		String imgurl="http://www.51pharm.cn/digest/img/contact.png";
		String url="http://www.51pharm.cn/digest/wechat/youhui.do?type=1";
		
		String title2="新疆厂部通讯录";
		String description2="全安药业通讯录，为您提供深圳营销中心和新疆厂部的通讯服务";
		String imgurl2="http://www.51pharm.cn/digest/img/contact.png";
		String url2="http://www.51pharm.cn/digest/wechat/youhui.do?type=11";
		
		Articles entity = new Articles(title,description,imgurl,url);
		list.add(entity);
		Articles entity2 = new Articles(title2,description2,imgurl2,url2);
		list.add(entity2);
		
		out.setArticles(list);
		return out;
	}
	/***
	 * 之前全泰消化专家，签到活动
	 * @param msg
	 * @return
	 */
	public OutMessage buildQiandaoServiceMsg2(InMessage msg) {
		OutMessage out = new OutMessage();
		boolean exist = wXCMSClientService.isExistQianDao(msg.getFromUserName());
		QianDao qd = null;
		if(!exist) {
			qd = new QianDao();
			WXUserVo wx = wXCMSClientService.getWechatUserInfo(msg.getFromUserName());
			qd.setCity(wx.getCity());
			qd.setCount(1);
			qd.setCountry(wx.getCountry());
			qd.setDate(Tools.getYMD(new Date()));
			qd.setNickname(wx.getNickname());
			qd.setOpenid(wx.getOpenid());
			qd.setProvince(wx.getProvince());
			qd.setPublicno(wx.getPublicno());
			qd.setTime(Tools.getHHmm(new Date()));
			qd.setUsername(wx.getUsername());
			wXCMSClientService.saveQianDao(qd);
			out.setContent("养生签到成功"+qd.getCount()+"天\n坚持养生签到14天\n可获得我迷家品质鸡蛋一盒\n客服手机:15818557397");
		} else {
			qd = wXCMSClientService.getQianDao(msg.getFromUserName());
			boolean todayok = wXCMSClientService.isQianDaoToday(qd.getOpenid());
			if(!todayok) {
				WXUserVo wx = wXCMSClientService.getWechatUserInfo(msg.getFromUserName());
				qd.setCity(wx.getCity());
				qd.setCount(qd.getCount()==null?1:qd.getCount()+1);
				qd.setCountry(wx.getCountry());
				qd.setDate(Tools.getYMD(new Date()));
				qd.setNickname(wx.getNickname());
				qd.setOpenid(wx.getOpenid());
				qd.setProvince(wx.getProvince());
				qd.setPublicno(wx.getPublicno());
				qd.setTime(Tools.getHHmm(new Date()));
				qd.setUsername(wx.getUsername());
				wXCMSClientService.updateQianDao(qd);
				if(qd.getCount() >= 14) {
					out.setArticleCount(1);
					out.setMsgType("news");
					String url = "http://www.51pharm.cn/digest/page/omg_qiandao.jsp?userid="+qd.getOpenid();
					String imgurl = "http://www.51pharm.cn/digest/img/omg.jpg";
					List<Articles> list = new ArrayList<Articles>();
					Articles entity = new Articles("养生签到，领取奖品",
							"养生签到14天，获得我迷家品质鸡蛋一盒",
							imgurl,url);
					list.add(entity);
					out.setArticles(list);
				} else {
					out.setContent("养生签到成功"+qd.getCount()+"天\n坚持养生签到14天\n可获得我迷家品质鸡蛋一盒\n客服手机:15818557397");
				}
			} else {
				if(qd.getCount() >= 14) {
					out.setArticleCount(1);
					out.setMsgType("news");
					String url = "http://www.51pharm.cn/digest/page/omg_qiandao.jsp?userid="+qd.getOpenid();
					String imgurl = "http://www.51pharm.cn/digest/img/omg.jpg";
					List<Articles> list = new ArrayList<Articles>();
					Articles entity = new Articles("养生签到，领取奖品",
							"养生签到14天，获得我迷家品质鸡蛋一盒",
							imgurl,url);
					list.add(entity);
					out.setArticles(list);
				} else {
					out.setContent("今天养生签到已成功，明天再来吧\n您已经成功签到"+qd.getCount()+"天\n坚持养生签到14天\n可获得我迷家品质鸡蛋一盒\n客服手机:15818557397");
				}
			}
		}
		return out;
	}
}