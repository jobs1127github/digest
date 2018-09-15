package com.tentinet.weixin.action.wechat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tentinet.app.bean.InformationBrowseCountVo;
import com.tentinet.app.bean.InformationBrowseInfoVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.weixin.entity.ShareInfo;
import com.tentinet.weixin.entity.WXUrl;
import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.JsonResult;
import com.tentinet.weixin.util.UrlUtil;

@Controller
@RequestMapping("/wechat/share")
public class ShareAction extends WechatBaseAction {

	@Autowired
	private WXCMSClientService wXCMSClientService;

	@Autowired
	private WechatJssdkService wechatJssdkService;

	@RequestMapping("/callback.do")
	public void callback(HttpServletRequest request,
			HttpServletResponse response, String openId, String information_id,
			Integer type) throws Exception {
		boolean flag = wXCMSClientService.saveShareGold(openId, information_id,type);// 保存分享记录的今天内应得的金币
		boolean flag1 = wXCMSClientService.saveShareInfo(openId, information_id);// 保存分享的记录．并对享的内容进行汇总
		writeResponseByJson(request, response, JsonResult.success());
	}

	@RequestMapping(value = "/auth.do")
	public ModelAndView auth(HttpServletRequest request, String code,
			ModelMap modelMap, String openId, String fromopenId,
			String information_id) throws Exception {
		Map<String, Object> map = wechatJssdkService.getPageAccessToken(code);
		WechatUser pageWechatUser = wechatJssdkService.getPageWechatUser(map
				.get("access_token").toString(), map.get("openid").toString());
		log.info("wechatId:===========" + pageWechatUser.getOpenId());
		request.getSession().setAttribute("openId", pageWechatUser.getOpenId());
		String sharelink = ConfigUtils.getValue("share_link");
		String newLink = String.format(sharelink, openId, fromopenId,
				information_id);
		return new ModelAndView(new RedirectView(newLink));
	}

	@RequestMapping("/informationShare.do")
	public ModelAndView informationShare(HttpServletRequest request,
			ModelMap modelMap, String openId, String information_id,
			String fromopenId) throws Exception {
		System.out.println("jobs1127.openId==" + openId + ".....fromopenId=="
				+ fromopenId + "......information_id == " + information_id
				+ "...........");
		String sessionOpenid = (String) request.getSession().getAttribute(
				"openId");
		if (sessionOpenid == null) {
			String wechatRedirece = UrlUtil.encode(ConfigUtils
					.getValue("weixin.backHost")
					+ "wechat/share/auth.do?format=json&openId="
					+ openId
					+ "&fromopenId="
					+ fromopenId
					+ "&information_id="
					+ information_id);
			log.info("url:=======wechatRedirece==========="
					+ UrlUtil.decode(wechatRedirece));
			String url = WXUrl.AUTHORIZE_URL
					+ wechatRedirece
					+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
			log.info("url:=======url===========" + UrlUtil.decode(url));
			return new ModelAndView(new RedirectView(url));
		}

		//
		wechatJssdkService.getWechatUserInfo(openId);
		wechatJssdkService.getWechatUserInfo(fromopenId);
		System.out.println(".newOpenId==" + sessionOpenid
				+ ".....newFromopenId==" + openId + "......information_id == "
				+ information_id + "...........");
		// ########################以下是设置分享内容　start
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("openid", sessionOpenid);
		WXUserDto wxUser = this.wXCMSClientService.getWechatUserGold(params1);
		if (wxUser != null) {
			modelMap.put("gold", wxUser.getGold());
		} else {
			modelMap.put("gold", 0);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("information_id", information_id);
		InformationDto information = wXCMSClientService
				.queryInformation(params);
		List<MarkVo> marks = wXCMSClientService.queryInfoMarkVoInfo(params);
		modelMap.put("information", information);
		modelMap.put("marks", marks);
		// ########################以下是设置分享内容　start
		ShareInfo share = new ShareInfo();
		share.setTitle(information.getTitle());
		share.setContent(ConfigUtils.getValue("share_content"));
		String sharelink = ConfigUtils.getValue("share_link");
		String newLink = String.format(sharelink, sessionOpenid, sessionOpenid,
				information_id);
		share.setLink(newLink);
		share.setImgUrl(information.getTile_img_url());
		modelMap.put("shareConfig", share);
		modelMap.put("openid", sessionOpenid);
		modelMap.put("information_id", information_id);
		// ########################设置分享内容　end
		params1.put("browse_time", dateToStr(new Date()));
		params1.put("information_id", information_id);
		Integer browses = wXCMSClientService.findBrowseInfo(params1);
		if (browses == 0) {
			InformationBrowseInfoVo browse = new InformationBrowseInfoVo();
			UUID uuid = UUID.randomUUID();
			browse.setTid(uuid.toString());
			browse.setBrowse_time(dateToStr(new Date()));
			browse.setInformation_id(information_id);
			browse.setBrowser_openid(sessionOpenid);
			wXCMSClientService.saveBrowse(browse);
			InformationBrowseCountVo browseCount = wXCMSClientService
					.findBrowseCount(information_id);
			if (browseCount == null) {
				browseCount = new InformationBrowseCountVo();
				browseCount.setInformation_id(information_id);
				browseCount.setBrowse_count(1);
			} else {
				browseCount.setBrowse_count(browseCount.getBrowse_count() + 1);
			}
			wXCMSClientService.saveBrowseCount(browseCount);
		}
		List<WXUserVo> rewarders = wXCMSClientService
				.queryInfoRewarders(params);
		Integer rewardercount = wXCMSClientService.queryRewardercounts(params);
		modelMap.put("rewarders", rewarders);
		modelMap.put("rewarderscount", rewardercount);

		return new ModelAndView("/page/informationInfo");
	}
}
