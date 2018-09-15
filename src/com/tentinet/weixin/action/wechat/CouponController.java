package com.tentinet.weixin.action.wechat;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tentinet.app.bean.AreaVo;
import com.tentinet.app.bean.ExpertMoneyVo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.util.BASE64Utils;
/**
 * @Controller控制层
 * @jobs1127
 *
 */
@Controller
@RequestMapping("/wechat")
public class CouponController extends WechatBaseAction {

	private final static Logger log = LoggerFactory
			.getLogger(CouponController.class);
    
	@Autowired
	private WXCMSClientService wXCMSClientService;
	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, ModelMap modelMap,
			String fromopenId) throws Exception {
		String resultStr = "";
		resultStr = "/page/index";
		return resultStr;
	}

	@RequestMapping("/userInfo.do")
	public String userInfo(HttpServletRequest request, ModelMap modelMap) {
		String openId = getCurrentOpenId();//父类里从session里拿取
		String operType = request.getParameter("oper_type");
		String resultPage = "";
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("open_id", openId);
		WXUserDto wxUser = this.wXCMSClientService.getWechatUserInfo(params); 
		String userName = wxUser.getUsername();//用户名保存时是通过编码保存的
		wxUser.setUsername(BASE64Utils.getFromBASE64(userName));//解码
		modelMap.put("user", wxUser);
		if(StringUtils.isBlank(operType) || "1".equals(operType)){
			resultPage = "/page/aOmniscient";
		} else if("2".equals(operType)){ //用户信息修改
			resultPage = "/page/aOEdit";
		}
		return resultPage;
	}
	@RequestMapping("/qiandao.do")
	public String qiandao(HttpServletRequest request, ModelMap modelMap) {
		String openId = getCurrentOpenId();
		String operType = request.getParameter("oper_type"), resultPage = "";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("open_id", openId);
		WXUserDto wxUser = this.wXCMSClientService.getWechatUserInfo(params); 
	    String userName = wxUser.getUsername();//解码
	    wxUser.setUsername(BASE64Utils.getFromBASE64(userName));
		modelMap.put("user", wxUser);
		if(StringUtils.isBlank(operType) || "1".equals(operType)){
			resultPage = "/page/aOmniscient";
		}else if("2".equals(operType)){ //用户信息修改
			resultPage = "/page/aOEdit";
		}
		return resultPage;
	}
	
	@RequestMapping("/updateUserInfo.do")
	public String updateUserInfo(HttpServletRequest request, ModelMap modelMap){
//		 log.info("-----------------用户信息修改");
		 String openId = getCurrentOpenId();
		 String userName = request.getParameter("username");
		 String age = request.getParameter("age");
		 String sex = request.getParameter("sex");
		 String resultPage = "";
		 WXUserVo wxUser = this.wXCMSClientService.getWechatUserInfo(openId);
		 wxUser.setUsername(BASE64Utils.getFromBASE64(wxUser.getUsername()));//解码
		 modelMap.put("user", wxUser);
		 if(StringUtils.isBlank(userName) || StringUtils.isBlank(age) || !NumberUtils.isDigits(age)  || StringUtils.isBlank(sex)){
			 modelMap.put("msg", "参数有误");
			 resultPage = "/page/aOEdit";
		 }else{
			 wxUser.setUsername(BASE64Utils.getBASE64(userName));//加码
			 wxUser.setAge(Integer.valueOf(age));
			 wxUser.setSex(sex);
			// wxUser.setCountry(request.getParameter("country"));
			 wxUser.setProvince(request.getParameter("province"));
			 wxUser.setCity(request.getParameter("city"));
			 boolean result = this.wXCMSClientService.updateWechatUserInfo(wxUser);
			 if(result){
				 Map<String,Object> params = new HashMap<String, Object>();
				 params.put("open_id", openId);
			     WXUserDto user = this.wXCMSClientService.getWechatUserInfo(params);
			     user.setUsername(BASE64Utils.getFromBASE64(user.getUsername()));//解码
				 modelMap.put("user", user);
				 resultPage = "/page/aOmniscient";
			 }else{
				 modelMap.put("msg", "修改失败");
				 resultPage = "/page/aOEdit";
			 }
		 }
		//log.info("-----------------用户信息修改结束");
		return resultPage;
	}
	
	@RequestMapping("/expertsIngress.do")
	public String expertsIngress(HttpServletRequest request, ModelMap modelMap){
//		 log.info("------------专家入口");
		 String openId = getCurrentOpenId();
		 ExpertMoneyVo userWallet = this.wXCMSClientService.getUserMoneyByOpenId(openId);
//		 log.info("----userWallet  exist " + (null == userWallet));
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("openid", openId);
		 params.put("beginnum", 0);
		 List<WXUserVo> wxUserVoList = this.wXCMSClientService.queryTopTenExpenseUser(params);
		 if(null != wxUserVoList){
			 for(int i=0; i<wxUserVoList.size();i++){
				 String userName = wxUserVoList.get(i).getUsername();
				 wxUserVoList.get(i).setUsername(BASE64Utils.getFromBASE64(userName));
				 if(wxUserVoList.get(i).getUsername()!=null){
					 if(wxUserVoList.get(i).getUsername().length()>2){
						 wxUserVoList.get(i).setUsername(wxUserVoList.get(i).getUsername().substring(0,2)+"...");
					 }
				 }
			 }
		 }
		 modelMap.put("userWallet", userWallet);	 
		 modelMap.put("userList", wxUserVoList);
//		 log.info("------------专家");
		 return "/page/expert";
	}
	
	@RequestMapping("/rewardUser.do")
	public String rewardUser(HttpServletRequest request, ModelMap modelMap){
//		log.info("-------------打赏用户列表");
		String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openId);
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		modelMap.put("pageNumberNow",pageNumeber);
		params.put("beginnum", (pageNumeber - 1)*10);
		List<WXUserVo> wxUserVoList = this.wXCMSClientService.getWxUserTexpense(params);
		 if(null != wxUserVoList){
			 for(int i=0; i<wxUserVoList.size();i++){
				 String userName = wxUserVoList.get(i).getUsername();
				 wxUserVoList.get(i).setUsername(BASE64Utils.getFromBASE64(userName));
			 }
		 }
		int informationCount = this.wXCMSClientService.getWxUserTexpenseCount(params),pageNumbers = 0;
		if(informationCount%10 == 0){ //总页数
			if(informationCount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = informationCount/10;
			}
		}else{
			pageNumbers = informationCount/10 + 1;
		}
		if(log.isInfoEnabled()){
//			log.info("打赏用户 is exist " + (null == wxUserVoList));
		}
		modelMap.put("userList", wxUserVoList);
		modelMap.put("pageNumbers", pageNumbers);
		log.info("------------打赏用户查找结束");
		return "/page/myreward";
	}
	
	@RequestMapping("/rewardUserPage.do")
	public void rewardUserPage(HttpServletRequest request,HttpServletResponse response){
//		log.info("-------------打赏用户列表");
		String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openId);
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		params.put("beginnum", (pageNumeber - 1)*10);
		List<WXUserVo> wxUserVoList = this.wXCMSClientService.getWxUserTexpense(params);
		 if(null != wxUserVoList){
			 for(int i=0; i<wxUserVoList.size();i++){
				 String userName = wxUserVoList.get(i).getUsername();
				 wxUserVoList.get(i).setUsername(BASE64Utils.getFromBASE64(userName));
			 }
		 }
		int informationCount = this.wXCMSClientService.getWxUserTexpenseCount(params),pageNumbers = 0;
		if(informationCount%10 == 0){ //总页数
			if(informationCount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = informationCount/10;
			}
		}else{
			pageNumbers = informationCount/10 + 1;
		}
		if(log.isInfoEnabled()){
//			log.info("打赏用户 is exist " + (null == wxUserVoList));
		}
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", wxUserVoList);
		jsonData.put("code", "00");
		//jsonData.put("pageNumbers", pageNumbers);
		super.writeResponseByJson(request, response, jsonData);
//		log.info("------------打赏用户查找结束");
	}
	
	
	@RequestMapping("/myAdvice.do")
	public String getMyAdvice(HttpServletRequest request, ModelMap modelMap){
//		log.info("-----查找专家文章");
		String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openId);
		
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		modelMap.put("pageNumberNow",pageNumeber);
		String order = (null == request.getParameter("sort")) ? "1" : request.getParameter("sort");
		modelMap.put("order", order);
		if("1".equals(order)){
			order = "created_time";
		}else if("2".equals(order)){
			order = "share_count";
		}else {
			order = "browse_count";
		}
		params.put("beginnum", (pageNumeber - 1)*10);
		params.put("orderby", order + " desc");
		List<InformationDto> informationAll = this.wXCMSClientService.queryInformationVoInfos(params);
		
		int informationCount = this.wXCMSClientService.queryInformationVoCount(params),pageNumbers = 0;
		if(informationCount%10 == 0){ //总页数
			if(informationCount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = informationCount/10;	
			}
		}else{
			pageNumbers = informationCount/10 + 1;
		}
		if(log.isInfoEnabled()){
			//log.info("咨询文章is exist" + !ArrayUtils.isEmpty(informationAll.toArray()));
		}
		modelMap.put("informations", informationAll);
		modelMap.put("pageNumbers", pageNumbers);
		//modelMap.put("count", informationCount);
//		log.info("-----查找专家文章结束");
		return "/page/myInformation";
	}
	
	
	@RequestMapping("/myAdvicePage.do")
	public void myAdvicePage(HttpServletRequest request,HttpServletResponse response){
//		log.info("-----查找专家文章");
		String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openId);
		
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		//modelMap.put("pageNumberNow",pageNumeber);
		String order = (null == request.getParameter("sort")) ? "1" : request.getParameter("sort");
		//modelMap.put("order", order);
		if("1".equals(order)){
			order = "created_time";
		}else if("2".equals(order)){
			order = "share_count";
		}else {
			order = "browse_count";
		}
		params.put("beginnum", (pageNumeber - 1)*10);
		params.put("orderby", order + " desc");
		List<InformationDto> informationAll = this.wXCMSClientService.queryInformationVoInfos(params);
		
		int informationCount = this.wXCMSClientService.queryInformationVoCount(params),pageNumbers = 0;
		if(informationCount%10 == 0){ //总页数
			if(informationCount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = informationCount/10;	
			}
		}else{
			pageNumbers = informationCount/10 + 1;
		}
		if(log.isInfoEnabled()){
			//log.info("咨询文章is exist" + !ArrayUtils.isEmpty(informationAll.toArray()));
		}
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("data", informationAll);
		jsonData.put("code", "00");
		super.writeResponseByJson(request, response, jsonData);
		//modelMap.put("count", informationCount);
//		log.info("-----查找专家文章结束");
	}
	
	
	@RequestMapping("/myAdvicePart.do")
	public void myAdvicePart(HttpServletRequest request, HttpServletResponse response){
	   String pageNumber = request.getParameter("page_number");
//	   log.info("pageNUmber ------" + pageNumber);
	   String openId = getCurrentOpenId();
	   Map<String, Object> params = new HashMap<String, Object>();
	   params.put("openid", openId);
	   List<InformationDto> informationAll = this.wXCMSClientService.queryInformationVoInfos(params);
	   JSONObject jsonObject = new JSONObject();
	   jsonObject.put("data", informationAll);
	   jsonObject.put("code", "00");
	   super.writeResponseByJson(request, response, jsonObject);
	}
	
	@RequestMapping("/getArea.do")
	public void getArea(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String city = request.getParameter("city");
		Map<String,Object> map = new HashMap<String, Object>();
		List<AreaVo> areaAll = null;
		if(StringUtils.isBlank(city)){
			  map.put("parents_id", "0");
			  areaAll =  this.wXCMSClientService.getAreaVoRoot(map);
		}else{
			map.put("city",new String(city.getBytes("ISO8859-1"),"utf-8"));
			areaAll  = this.wXCMSClientService.getAreaVo(map);
		}
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("data", areaAll);
	    jsonObject.put("code", "00");
	    super.writeResponseByJson(request, response, jsonObject);
	}
	
	@RequestMapping("/singleReward.do")
	public String singleReward(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) throws UnsupportedEncodingException{
//		log.info("-------------文章被打赏用户列表");
		//String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		String articleId = request.getParameter("article_id");
		params.put("information_id", articleId);
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		modelMap.put("pageNumberNow",pageNumeber);
		params.put("beginnum", (pageNumeber - 1)*10);
		List<WXUserDto> rewarders=wXCMSClientService.queryRewarders(params);
		 if(null != rewarders){
			 for(int i=0; i<rewarders.size();i++){
				 String userName = rewarders.get(i).getUsername();
				 rewarders.get(i).setUsername(BASE64Utils.getFromBASE64(userName));
			 }
		 }
		Integer rewardercount = wXCMSClientService.queryRewardercounts(params),pageNumbers = 0;
		if(rewardercount%10 == 0){ //总页数
			if(rewardercount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = rewardercount/10;
			}
		}else{
			pageNumbers = rewardercount/10 + 1;
		}
		if(log.isInfoEnabled()){
//			log.info("打赏用户 is exist " + (null == rewarders));
		}
		double rewardsSum = this.wXCMSClientService.findRewarderSum(params);
		String articleName=new String(request.getParameter("article_name").getBytes("ISO8859-1"),"utf-8");
		if(articleName.length()>8){
			articleName=articleName.substring(0, 8)+"……";
		}
		modelMap.put("articleName", articleName);
		modelMap.put("articleId", articleId);
		modelMap.put("userList", rewarders);
		modelMap.put("pageNumbers", pageNumbers);
		modelMap.put("rewardsSum",  (float)(Math.round(rewardsSum*100))/100);
//		log.info("------------文章被打赏用户查找结束");
		return "/page/singleReward";
	}
	
	
	@RequestMapping("/singleRewardPage.do")
	public void singleRewardPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
//		log.info("-------------文章被打赏用户列表");
		//String openId = getCurrentOpenId();
		Map<String, Object> params = new HashMap<String, Object>();
		String articleId = request.getParameter("article_id");
		params.put("information_id", articleId);
		int pageNumeber = (null == request.getParameter("page_numeber")) ? 1 : Integer.valueOf(request.getParameter("page_numeber"));
		params.put("beginnum", (pageNumeber - 1)*10);
		List<WXUserDto> rewarders=wXCMSClientService.queryRewarders(params);
		 if(null != rewarders){
			 for(int i=0; i<rewarders.size();i++){
				 String userName = rewarders.get(i).getUsername();
				 rewarders.get(i).setUsername(BASE64Utils.getFromBASE64(userName));
			 }
		 }
		Integer rewardercount = wXCMSClientService.queryRewardercounts(params),pageNumbers = 0;
		if(rewardercount%10 == 0){ //总页数
			if(rewardercount == 0){
				pageNumbers = 1;
			}else{
				pageNumbers = rewardercount/10;
			}
		}else{
			pageNumbers = rewardercount/10 + 1;
		}
		if(log.isInfoEnabled()){
//			log.info("打赏用户 is exist " + (null == rewarders));
		}
		//double rewardsSum = this.wXCMSClientService.findRewarderSum(params);
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("data", rewarders);
	    jsonObject.put("code", "00");
//		log.info("------------文章被打赏用户查找结束");
		super.writeResponseByJson(request, response, jsonObject);
	}

}
