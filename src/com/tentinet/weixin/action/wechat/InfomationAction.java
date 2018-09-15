package com.tentinet.weixin.action.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.ExpertMoneyVo;
import com.tentinet.app.bean.InformationAwardInfoVo;
import com.tentinet.app.bean.InformationBrowseCountVo;
import com.tentinet.app.bean.InformationBrowseInfoVo;
import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.Plan;
import com.tentinet.app.bean.QianDao;
import com.tentinet.app.bean.UserGoldCountVo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.WxClient;
import com.tentinet.app.bean.WxMailList;
import com.tentinet.app.bean.WxPrize;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.bean.dto.PlanDTO;
import com.tentinet.app.bean.dto.QueryVO;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.weixin.entity.ShareInfo;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.DateUtil;

import net.sf.json.JSONObject;
/**
 * @Controller控制层，接收前台的请求/参数通过@Service服务层进行数据库等操作，将数据库的信息返回到前端，根据需要进行控制、跳转等操作
 * @author jobs1127
 * 咨询文章相关的前台控制操作（精选资讯、全部资讯）
 */
@Controller
@RequestMapping("/wechat")
@SuppressWarnings("all")
public class InfomationAction extends WechatBaseAction {

	@Autowired
	private WXCMSClientService wXCMSClientService;
	
	
	
	
	/****************好伙伴相关开始***************************************/
	
	
	/**
	 * 费用查询,获取数据库数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/selectPlansF1.do") 
	public String selectPlansF1(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,QueryVO vo) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("client",sessionClient.getName());//微信公众平台绑定成功后获得 
		params.put("year",vo.getYear());
		//处理客户同名问题
		if(sessionClient.getProvince() != null && StringUtils.isNotBlank(sessionClient.getProvince())) {
			String[] ps = sessionClient.getProvince().split(",");
			params.put("provinces", ps);
		}
		if(sessionClient.getCity() != null && StringUtils.isNotBlank(sessionClient.getCity())) {
			String[] cs = sessionClient.getCity().split(",");
			params.put("citys", cs);
		}
		if(sessionClient.getCountry() != null && StringUtils.isNotBlank(sessionClient.getCountry())) {
			String[] cs = sessionClient.getCountry().split(",");
			params.put("countys", cs);
		}
		if(sessionClient.getTerminalCollection() != null && StringUtils.isNotBlank(sessionClient.getTerminalCollection())) {
			String[] cs = sessionClient.getTerminalCollection().split(",");
			params.put("terminals", cs);
		}
		List<PlanDTO> plans = wXCMSClientService.getPlansF1(params);
		
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		fillPlanList(list,plans);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 填充流向列表
	 * @param target 待填充的目标
	 * @param source 数据源
	 */
	private void fillPlanList(List<PlanDTO> target,List<PlanDTO> source) {
		Float sum = 0f;
		Float y_sum = 0f;//押求和
		Float d_sum = 0f;//待求和
		Float b_sum = 0f;//毕求和
		for (PlanDTO p : source) {
			float s = p.getMoney()==null?0:p.getMoney();
			sum+=s;
			if(p.getStatus().contains("押")) {
				y_sum+=s;
			}
			if(p.getStatus().contains("待")) {
				d_sum+=s;
			}
			if(p.getStatus().contains("毕")) {
				b_sum+=s;
			}
		}
		PlanDTO py = new PlanDTO();
		PlanDTO pd = new PlanDTO();
		PlanDTO pb = new PlanDTO();
		PlanDTO ph = new PlanDTO();
		
		py.setStatus("状态【押】");
		py.setMoney(y_sum);
		pd.setStatus("状态【待】");
		pd.setMoney(d_sum);
		pb.setStatus("状态【毕】");
		pb.setMoney(b_sum);
		ph.setStatus("合计");
		ph.setMoney(sum);
		target.add(py);
		target.add(pd);
		target.add(pb);
		target.add(ph);
	}
	/**
	 * 费用查询,获取数据库数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/selectPlansF3.do") 
	public String selectPlansF3(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,QueryVO vo) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("client",sessionClient.getName());//微信公众平台绑定成功后获得 
		params.put("year",vo.getYear());
		List<PlanDTO> plans = wXCMSClientService.getPlansF3(params);
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		fillPlanList(list,plans);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 费用查询,获取数据库数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/selectPlansF4.do") 
	public String selectPlansF4(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,QueryVO vo) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
				
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("client",sessionClient.getName());//微信公众平台绑定成功后获得  
		params.put("year",vo.getYear());
		List<PlanDTO> plans = wXCMSClientService.getPlansF4(params);
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		fillPlanList(list,plans);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 费用查询,获取数据库数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/selectPlansF5.do") 
	public String selectPlansF5(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,QueryVO vo) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
					
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("client",sessionClient.getName());//微信公众平台绑定成功后获得 
		params.put("year",vo.getYear());
		List<PlanDTO> plans = wXCMSClientService.getPlansF5(params);
		List<PlanDTO> list = new ArrayList<PlanDTO>();
		fillPlanList(list,plans);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 流向查询 明细,获取数据库数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/selectPlans.do") 
	public String selectPlans(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,QueryVO vo) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(sessionClient != null) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("stime", vo.getStime());
			params.put("etime", vo.getEtime());
			if("1".equals(sessionClient.getType())) {//客户
				params.put("client",sessionClient.getName());//微信公众平台绑定成功后获得 
				params.put("daibiao",null);
				//处理客户同名问题
				if(sessionClient.getProvince() != null && StringUtils.isNotBlank(sessionClient.getProvince())) {
					String[] ps = sessionClient.getProvince().split(",");
					params.put("provinces", ps);
				}
				if(sessionClient.getCity() != null && StringUtils.isNotBlank(sessionClient.getCity())) {
					String[] cs = sessionClient.getCity().split(",");
					params.put("citys", cs);
				}
				if(sessionClient.getCountry() != null && StringUtils.isNotBlank(sessionClient.getCountry())) {
					String[] cs = sessionClient.getCountry().split(",");
					params.put("countys", cs);
				}
				if(sessionClient.getTerminalCollection() != null && StringUtils.isNotBlank(sessionClient.getTerminalCollection())) {
					String[] cs = sessionClient.getTerminalCollection().split(",");
					params.put("terminals", cs);
				}
			} else if("2".equals(sessionClient.getType())) {//代表
				params.put("daibiao",sessionClient.getName());//微信公众平台绑定成功后获得 
				params.put("client",null);
				//处理代表同名问题
				if(sessionClient.getProvince() != null && StringUtils.isNotBlank(sessionClient.getProvince())) {
					String[] ps = sessionClient.getProvince().split(",");
					params.put("provinces", ps);
				}
				if(sessionClient.getCity() != null && StringUtils.isNotBlank(sessionClient.getCity())) {
					String[] cs = sessionClient.getCity().split(",");
					params.put("citys", cs);
				}
				if(sessionClient.getCountry() != null && StringUtils.isNotBlank(sessionClient.getCountry())) {
					String[] cs = sessionClient.getCountry().split(",");
					params.put("countys", cs);
				}
				if(sessionClient.getTerminalCollection() != null && StringUtils.isNotBlank(sessionClient.getTerminalCollection())) {
					String[] cs = sessionClient.getTerminalCollection().split(",");
					params.put("terminals", cs);
				}
			} else {
				params.put("client","jobs1127");//必须保证client参数有值
				params.put("daibiao",null);
			}
			params.put("terminal",vo.getTerminal());
			params.put("products",vo.getProducts());
			
			List<Plan> plans = wXCMSClientService.getPlans(params);
			JSONObject oj = new JSONObject();
			oj.put("list", plans);
			writeResponseByJson(request, response, oj);
		}
		return null;
	}
	/**
	 * 流向查询 UI
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/planQuery.do") 
	public String planQuery(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		//判断是已经绑定 全安好伙伴
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		WxClient wxClient = wXCMSClientService.getWxClient(openId);
		if(wxClient == null) {//未绑定，前往绑定验证
			modelMap.put("model","plan");
			return "/page/client_banding";
		}
		//在session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(sessionClient == null) {
			 request.getSession().setAttribute("wxClient", wxClient);
		}
		return "/page/plan_query";
	}
	/**
	 * 流向查询 收益概况 UI
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/plan_sum.do") 
	public String plan_sum(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(sessionClient != null && "1".equals(sessionClient.getType())) {
			return "/page/plan_sum";
		} 
		//处理客户外，其他人无权限访问
		return "/page/plan_sum_no";
	}
	/**
	 * 流向查询 流向明细 UI
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/plan_list.do") 
	public String plan_list(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		return "/page/plan_list";
	}
	
	
	/**
	 * 营销中心通讯录 UI
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/contact_center_UI.do") 
	public String contactUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		//判断是否已经绑定 全安好伙伴
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		
		WxClient wxClient = wXCMSClientService.getWxClient(openId);
		if(wxClient == null) {//未绑定，前往绑定验证
			modelMap.put("model","contact_center");
			return "/page/client_banding";
		}
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(sessionClient == null) {
			//将通过认证的好伙伴，放入session
			request.getSession().setAttribute("wxClient", wxClient);
		}
		return "/the_address_book2/contact_center";
	}
	/**
	 * 新疆厂部通讯录 UI
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/contact_factory_UI.do") 
	public String contact_factory_UI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
		//判断是否已经绑定 全安好伙伴
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		
		WxClient wxClient = wXCMSClientService.getWxClient(openId);
		if(wxClient == null) {//未绑定，前往绑定验证
			modelMap.put("model","contact_factory");
			return "/page/client_banding";
		}
		//获取session中存放当前访问的好伙伴
		WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(sessionClient == null) {
			//将通过认证的好伙伴，放入session
			request.getSession().setAttribute("wxClient", wxClient);
		}
		return "/the_address_book2/contact_factory";
	}
	
	/**
	 * 通讯录 营销中心，异步请求获取数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/query_contact_center.do") 
	public String query_contact_center(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,String keyname) throws Exception {
		//查询出营销中心通讯录
		WxClient wxClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(wxClient != null) {
			//内部人员权限
			Map<String,Object> params = new HashMap<String,Object>();
			List<WxMailList> list = wXCMSClientService.getWxMailList(params);
			List<WxMailList> list2 = new ArrayList<WxMailList>();
			WxMailList wx = new WxMailList();
			wx.setRegion("新疆厂部");
			list2.add(wx);
			list.removeAll(list2);
			//客户、代表不能给其领导的电话
			if(!"3".equals(wxClient.getType())) {
				for(WxMailList w:list) {
					if(w.getName().contains("彭一峰")||w.getName().contains("邹朝东")
							||w.getName().contains("吴世龙")||w.getName().contains("袁周斌")
							||w.getName().contains("潘骐")||w.getName().contains("任晓霞")
							||w.getName().contains("谭晓蕾")||w.getName().contains("冀祖恩")
							||w.getName().contains("杨应梅")||w.getName().contains("王海峰")
							) {
						w.setMobilePhone("暂时未公开");
					}
				}
			} 
			
			//自拼json
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			int index = list.size();
			for (WxMailList w : list) {
				index--;
				sb.append("{");
				if("男".equals(w.getSex())) {
					sb.append("\"img\": \"man.jpg\",");
					sb.append("\"tel\":\""+w.getMobilePhone()+"\",");
					sb.append("\"name\": \""+w.getName()+" "+w.getPosition()+"\"");//
				} else {
					sb.append("\"img\": \"woman.jpg\",");
					sb.append("\"tel\":\""+w.getMobilePhone()+"\",");
					sb.append("\"name\": \""+w.getName()+" "+w.getPosition()+"\"");
				}
				sb.append("}");
				if(index > 0) {
					sb.append(",");
				}
			}
			sb.append("]");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 通讯录 新疆厂部，异步请求获取数据
	 * @param request
	 * @param modelMap
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/query_contact_factory.do") 
	public String query_contact_factory(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,String keyname) throws Exception {
		//查询出新疆厂部通讯录
		WxClient wxClient = (WxClient) request.getSession().getAttribute("wxClient");
		if(wxClient != null) {
			//内部人员权限
			Map<String,Object> params = new HashMap<String,Object>();
			List<WxMailList> list = wXCMSClientService.getWxMailList(params);
			List<WxMailList> list2 = new ArrayList<WxMailList>();
			WxMailList wx = new WxMailList();
			wx.setRegion("新疆厂部");
			list2.add(wx);
			list.retainAll(list2);//取交集
			//客户、代表不能给其领导的电话
			if(!"3".equals(wxClient.getType())) {
				for(WxMailList w:list) {
					if(w.getName().contains("彭一峰")||w.getName().contains("邹朝东")
							||w.getName().contains("吴世龙")||w.getName().contains("袁周斌")
							||w.getName().contains("潘骐")||w.getName().contains("任晓霞")
							||w.getName().contains("谭晓蕾")||w.getName().contains("冀祖恩")
							||w.getName().contains("杨应梅")||w.getName().contains("王海峰")
							) {
						w.setMobilePhone("暂时未公开");
					}
				}
				
			} 
			
			//自拼json
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			int index = list.size();
			for (WxMailList w : list) {
				index--;
				sb.append("{");
				if("男".equals(w.getSex())) {
					sb.append("\"img\": \"man.jpg\",");
					sb.append("\"tel\": \""+w.getMobilePhone()+"\",");
					sb.append("\"name\": \""+w.getName()+" "+w.getPosition()+"\"");
				} else {
					sb.append("\"img\": \"woman.jpg\",");
					sb.append("\"tel\": \""+w.getMobilePhone()+"\",");
					sb.append("\"name\": \""+w.getName()+" "+w.getPosition()+"\"");
				}
				sb.append("}");
				if(index > 0) {
					sb.append(",");
				}
			}
			sb.append("]");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			try {
				response.getWriter().write(sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 保存中奖纪录，流水账记录
	 * @param request
	 * @param modelMap
	 * @param fromopenId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/savePrize.do") 
	public String savePrize(HttpServletRequest request,HttpServletResponse response,String prize) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		//System.out.println("savePrize.do的openid="+openId);
		JSONObject oj = new JSONObject();
		try { 
			if(openId != null) {
				WxClient wxClient = (WxClient) request.getSession().getAttribute("wxClient");
				WxPrize wp = new WxPrize();
				wp.setOpenid(openId);
				wp.setDeliveryStatus("0");//0表示未发货
				wp.setPrizeName(prize);
				wp.setDeliveryTime(DateUtil.dateToStr(new Date()));
				if(wxClient != null) {//收货人
					wp.setConsignee(wxClient.getName());
				}
				wXCMSClientService.savePrize(wp);
				oj.put("isSavePrizeSucessful", true);
			}
		} catch (Exception e) {
			oj.put("isSavePrizeSucessful", false);
		}
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 更新签到用户抽奖币
	 * @param request
	 * @param modelMap
	 * @param fromopenId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/updateUserchoujiang.do") 
	public String updateUserchoujiang(HttpServletRequest request,HttpServletResponse response,Integer count) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		//System.out.println("updateUserchoujiang.do的openid="+openId);
		JSONObject oj = new JSONObject();
		try { 
			if(openId != null) {
				QianDao qd = wXCMSClientService.getQianDao(openId);
				if(qd != null) {
					WxClient wxClient = (WxClient) request.getSession().getAttribute("wxClient");
					qd.setCount(count);
					if(wxClient != null) {
						qd.setUsername(wxClient.getName());
					}
					wXCMSClientService.updateQianDao(qd);
					oj.put("issucessful", true);
				}
			}
		} catch (Exception e) {
			oj.put("issucessful", false);
		}
		writeResponseByJson(request, response, oj);
		return null;
	}
	/**
	 * 签到抽奖UI，必须是已经通过备案的好伙伴才可以抽奖
	 * 进入签到抽奖UI是会进行权限认证，认证是否为全安好伙伴，认证通过可前往抽奖
	 * @param request
	 * @param modelMap
	 * @param fromopenId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/qiandaoChouJiang.do") 
	public String qiandaoChouJiang(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		//log.info("qiandaoChouJiang.do:openId="+openId);
		if(openId != null) {
			//判断是已经绑定 全安好伙伴
			WxClient wxClient = wXCMSClientService.getWxClient(openId);
			if(wxClient == null) {//未绑定，前往绑定验证
				return "/page/client_banding";
			}
			//获取session中存放当前访问的好伙伴
			WxClient sessionClient = (WxClient) request.getSession().getAttribute("wxClient");
			if(sessionClient == null) {
				//将通过认证的好伙伴，放入session
				request.getSession().setAttribute("wxClient", wxClient);
			}
			//前往抽奖页面
			QianDao qd = wXCMSClientService.getQianDao(openId);
			if(qd != null) {
				modelMap.put("openId", openId);
				modelMap.put("count", qd.getCount()==null?0:qd.getCount());
				return "/choujiang/qiandao_choujiang";
			}
			return "";
		} else {
			return "";
		}
	}
	/**
	 * 认证方法，好伙伴，公众平台提交验证申请，处理验证，并跳转抽奖界面
	 * @param request
	 * @param modelMap
	 * @param fromopenId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/authentication.do") 
	public String authentication(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,QueryVO vo) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//openId = "oKcSIjpIpoJ4k0MVNDIXo_pum9Ls";
		//log.info("qiandaoChouJiang.do:openId="+openId);
		JSONObject oj = new JSONObject();
		if(openId != null) {
			//先认证是否为公司内部人员，查询通讯录，若能找到则认为是公司内部人员，绑定到好伙伴类，类型为内部人员
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("mobile_phone", vo.getTelephone());
			List<WxMailList> wxMailList = wXCMSClientService.getWxMailList(params);
			if(wxMailList != null && !wxMailList.isEmpty()) {//内部人员
				try {
					WxClient wxClient = wXCMSClientService.getWxClientByTelephone(vo.getTelephone());
					if(wxClient == null) {
						wxClient = new WxClient();
						wxClient.setCtime(new Date());
					}
					wxClient.setOpenid(openId);
					wxClient.setType("3");
					wxClient.setTelephone(vo.getTelephone());
					wxClient.setName("内部人员待更新");
					wxClient.setMtime(new Date());
					wXCMSClientService.saveOrUpdateWxClient(wxClient);
					oj.put("msg", "更新成功");
				} catch (Exception e) {
					e.printStackTrace();
					oj.put("msg", "验证失败");
					writeResponseByJson(request, response, oj);
					return null;
				}
			} else {
				//通过手机号，获取后台已经添加的全安好伙伴
				WxClient wxClient = wXCMSClientService.getWxClientByTelephone(vo.getTelephone());
				if(wxClient == null) {//后台登记，提醒好伙伴联系经理或助理
					oj.put("msg", "验证失败");//不存在
					writeResponseByJson(request, response, oj);
					return null;
				}
				wxClient.setOpenid(openId);
				try { 
					//更新好伙伴
					wXCMSClientService.saveOrUpdateWxClient(wxClient);
					oj.put("msg", "更新成功");
				} catch (Exception e) {
					e.printStackTrace();
					oj.put("msg", "网络异常");
				}
			}
		} else {
			oj.put("msg", "网络异常");
		}
		writeResponseByJson(request, response, oj);
		return null;
	}
	
	/****************好伙伴相关end***************************************/
	
	
	
	
/*****************************-----------------------------******************************************/
	/**
	 * 全部资讯页
	 * @param request
	 * @param modelMap
	 * @param fromopenId
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/Infomation.do") 
	public String Infomation(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		log.info("jobs1127:openId="+openId);
		return "/gcpages/boutique";
	}
	/**
	 * 首次关注，欢迎首页
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/welcome.do") 
	public String welcome(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		log.info("welcome.do:openId="+openId);
		return "/gcpages/boutique";
	}
	
	
	@RequestMapping("/InfomationC.do") 
	public String InfomationC(HttpServletRequest request, ModelMap modelMap,String type) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//log.info("jobs1127:openId="+openId);
		//System.out.println("type="+type);
		modelMap.put("ctype",type);
		return "/gcpages/allInformation";
	}
	
	@RequestMapping("/myInfomation.do") 
	public String myInfomation(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		return "redirect:../gcpages/paiming.jsp";
		
	}
	/**
	 * 精品推荐
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/Boutique.do")
	public String Boutique(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		return "/page/boutique";
	}

	/**
	 * 查询全部资讯
	 * 
	 * @param request
	 * @param modelMap 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAllInformation.do")
	public void queryAllInformation(String url, HttpServletResponse response,
			HttpServletRequest request, String mark, String information_type,
			String orderby, String beginnum,String title,String best_flag) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(best_flag)) {// 如果不为空
			params.put("best_flag", best_flag);
		}
		if (StringUtils.isNotEmpty(openId)) {// 如果不为空
			params.put("openId", openId);
		}
		if (StringUtils.isNotEmpty(mark)) {// 如果不为空
			params.put("mark", "%" + mark + "%");
		}
		if (StringUtils.isNotEmpty(information_type)) {// 如果不为空
			params.put("information_type", information_type);
		}
		if (StringUtils.isNotEmpty(title)) {// 如果不为空
			params.put("title", "%" + title + "%");
		}
		if (StringUtils.isNotEmpty(orderby)) {// 如果不为空
			if (orderby.equals("1"))
				params.put("orderby", "created_time DESC");
			else if (orderby.equals("2"))
				params.put("orderby", "share_count DESC");
			else if (orderby.equals("3"))
				params.put("orderby", "browse_count desc");
		}
		params.put("beginnum", beginnum);

		List<InformationDto> list = wXCMSClientService.queryInformationVoInfos(params);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getAward_money() == null) {
					list.get(i).setAward_money((float) 0);
				}
				list.get(i).setCreated_time(
						list.get(i).getCreated_time().split(" ")[0]);
			}
		}
		Integer count = wXCMSClientService.queryInformationVoCount(params);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		oj.put("count", count);
		writeResponseByJson(request, response, oj);
	}
	
	/**
	 * 查询精品资讯
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBoutique.do")
	public void queryBoutique(String url, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		//BoutiqueVo tittleBoutiqueVo = wXCMSClientService.queryTittleBoutique();
		List<InformationVo> list = wXCMSClientService.queryBoutique();
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		//oj.put("tittle", tittleBoutiqueVo);
		writeResponseByJson(request, response, oj);
	}
	/**
	 * 查询精品资讯 页面头部，图片切换
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBoutique_change.do")
	public void queryBoutique_change(String url, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		List<InformationVo> list = wXCMSClientService.queryBoutique_change();
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
	}
	/**
	 * 查询精品资讯 6个类别的
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBoutique_c.do")
	public void queryBoutique_c(String url, HttpServletResponse response,
			HttpServletRequest request,String c_form_id) throws Exception {
		List<InformationVo> list = wXCMSClientService.queryBoutique(c_form_id);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 资讯详情页
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/informationInfo.do")
	public String informationInfo(HttpServletRequest request,
			ModelMap modelMap, String information_id) throws Exception {
		String openId = (String) request.getSession().getAttribute("openId");
		//测试的openId
		//openId = "o4Crqwg-B_gbGTQfsLHuCrUO2ZC0";
		
		
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("openid", openId);
		WXUserDto wxUser = this.wXCMSClientService.getWechatUserGold(params1);
		WXUserVo wxuservo = this.wXCMSClientService.getWechatUserInfo(openId);
		// UserGoldCountVo
		// wxUser=wXCMSClientService.getUserGoldCountVoInfo(openId);
		if (wxUser != null) {
			modelMap.put("gold", wxUser.getGold());
		} else {
			modelMap.put("gold", 0);
		}
		if (wxuservo != null) {
			if (wxuservo.getFrist_show() != null) {
				modelMap.put("fristshow", 1);
			} else {
				modelMap.put("fristshow", 0);
				wxuservo.setFrist_show("1");
				wXCMSClientService.updateWechatUserInfo(wxuservo);
			}
		} else {
			modelMap.put("fristshow", 0);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("information_id", information_id);
		InformationDto information = wXCMSClientService.queryInformation(params);
		List<MarkVo> marks = wXCMSClientService.queryInfoMarkVoInfo(params);
		modelMap.put("information", information);
		modelMap.put("award_money", Math.round(information.getAward_money()));
		modelMap.put("marks", marks);
		
		
		// ########################以下是设置分享内容　start
		ShareInfo share = new ShareInfo();
		share.setTitle(information.getTitle());
		share.setContent(ConfigUtils.getValue("share_content"));
		String sharelink = ConfigUtils.getValue("share_link");
		String newLink = String.format(sharelink, openId, openId,information_id);
		share.setLink(newLink);
		share.setImgUrl(information.getTile_img_url());
		modelMap.put("shareConfig", share);
		modelMap.put("openid", openId);
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
			browse.setBrowser_openid(openId);
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
		List<WXUserVo> rewarders = wXCMSClientService.queryInfoRewarders(params);
		Integer rewardercount = wXCMSClientService.queryRewardercounts(params);
		modelMap.put("rewarders", rewarders);
		modelMap.put("rewarderscount", rewardercount);

		return "/page/informationInfo";
	}

	/**
	 * 查询打赏人
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryRewarders.do")
	public void queryRewarders(String url, HttpServletResponse response,
			HttpServletRequest request, String informationid)
			throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("information_id", informationid);
		List<WXUserVo> rewarders = wXCMSClientService
				.queryInfoRewarders(params);
		Integer rewardercount = wXCMSClientService.queryRewardercounts(params);
		JSONObject oj = new JSONObject();
		oj.put("rewarders", rewarders);
		oj.put("rewarderscount", rewardercount);

		writeResponseByJson(request, response, oj);
	}

	/**
	 * 查询标签
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querymark.do")
	public void querymark(String url, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openId", openId);
		List<MarkVo> list = wXCMSClientService.queryMarkVoInfo(params);
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 查询全部标签
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryallmarks.do")
	public void queryallmarks(String url, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");
		Map<String, Object> params = null;
		//查询所有标签
		List<MarkVo> list = wXCMSClientService.queryAllMarkVoInfo(params);
		//查询所有分类，然后用标签类来分组
		List<MarkVo> types = wXCMSClientService.queryAllType(params);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getMark_name().length() > 3) {
					list.get(i).setMark_name(
							list.get(i).getMark_name().substring(0, 2) + "…");
				}
			}
		}

		if (types != null) {
			for (int i = 0; i < types.size(); i++) {
				if (types.get(i).getMark_name().length() > 3) {
					types.get(i).setMark_name(
							types.get(i).getMark_name().substring(0, 2) + "…");
				}
			}
		}
		JSONObject oj = new JSONObject();
		oj.put("list", list);
		oj.put("types", types);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 保存标签，用户在领取红包时所选择的，保存用户感兴趣的标签
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/savemarks.do")
	public void savemarks(String url, HttpServletResponse response,
			HttpServletRequest request, String marks) throws IOException {
		// String openId = (String) request.getSession().getAttribute("openId");
		String openId = request.getParameter("re_openid");
		boolean result = wXCMSClientService.saveMarkVoInfo(openId, marks);
		JSONObject oj = new JSONObject();
		oj.put("result", result);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 打赏功能
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePrice.do")
	public void updatePrice(String url, HttpServletResponse response,
			HttpServletRequest request, float price, String informationid,
			String infoopenid) throws IOException {
		String openId = (String) request.getSession().getAttribute("openId");

		UserGoldCountVo user = wXCMSClientService.getUserWalletByOpenId(openId);
		if (user == null) {
			user = new UserGoldCountVo();
			user.setGold_count(0);
			user.setOpenid(openId);
		}
		if (user.getGold_count() <= price) {
			user.setGold_count(0);
		} else {
			user.setGold_count(user.getGold_count() - price);
		}
		//infoopenid 写资讯文章的openid专家
		ExpertMoneyVo infouser = wXCMSClientService.getUserMoneyByOpenId(infoopenid);

		infouser.setMoney_count(infouser.getMoney_count() + price);
		infouser.setMoney_usable(infouser.getMoney_usable() + price);

		InformationAwardInfoVo award = new InformationAwardInfoVo();
		UUID uuid = UUID.randomUUID();
		award.setTid(uuid.toString());
		//openId 是当前阅读的文章的openid
		award.setAward_openid(openId);
		award.setInformation_id(informationid);
		award.setGold(price);
		award.setCreated_time(dateToStr(new Date()));
		//更新当前用户、和专家
		boolean result = wXCMSClientService.updatePrice(user, infouser);
		//新增打赏记录
		result = wXCMSClientService.addAward(award);
		//更新资讯的打赏
		result = wXCMSClientService.updateAwardCount(informationid, price);

		JSONObject oj = new JSONObject();
		oj.put("result", result);
		writeResponseByJson(request, response, oj);
	}
}