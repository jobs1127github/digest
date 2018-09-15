package com.tentinet.weixin.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.AreaVo;
import com.tentinet.app.bean.BoutiqueVo;
import com.tentinet.app.bean.DigestGoldVo;
import com.tentinet.app.bean.DigestRedPacketVo;
import com.tentinet.app.bean.ExpertMoneyVo;
import com.tentinet.app.bean.InformationAwardCountVo;
import com.tentinet.app.bean.InformationAwardInfoVo;
import com.tentinet.app.bean.InformationBrowseCountVo;
import com.tentinet.app.bean.InformationBrowseInfoVo;
import com.tentinet.app.bean.InformationShareCountVo;
import com.tentinet.app.bean.InformationShareInfoVo;
import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.Plan;
import com.tentinet.app.bean.QianDao;
import com.tentinet.app.bean.UserCharLogVo;
import com.tentinet.app.bean.UserGoldCountVo;
import com.tentinet.app.bean.UserMarkVo;
import com.tentinet.app.bean.UserWalletExpenseInfo;
import com.tentinet.app.bean.UserWalletIncomeInfo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.WxClient;
import com.tentinet.app.bean.WxMailList;
import com.tentinet.app.bean.WxPrize;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.bean.dto.PlanDTO;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.DateUtil;
import com.tentinet.weixin.util.UUIDUtils;
import com.tentinet.weixin.util.wechat.Tools;

/**
 * 微信端主要与ＤＢ交付的实现
 * 
 * @author jobs1127
 * @Service服务层，自动注入与DB打交道的Dao
 */
@SuppressWarnings("all")
@Service(value = "wXCMSClientService")
public class WXCMSClientServiceImpl implements WXCMSClientService {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;

	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;

	/**
	 * 当前用户是否存在
	 */
	@Override
	public boolean isExistWechatUser(String openid) {
		boolean isResult = false;
		WXUserVo user = commonHibernateBaseDao.getEntityByStringId(
				WXUserVo.class, openid);
		if (user != null) {
			isResult = true;
		}
		return isResult;
	}

	/**
	 * 保存当前微用户的信息
	 */
	@Override
	public boolean saveWechatUser(WechatUser user) {
		boolean isResult = false;
		try {
			System.out.println("user="+user);
			System.out.println("user.openid="+user.getOpenId());
			WXUserVo wxuser = new WXUserVo();
			wxuser.setOpenid(user.getOpenId());
			wxuser.setCreated_by("admin");
			wxuser.setCreated_time(format.format(new Date()));
			wxuser.setRole("1");
			wxuser.setStatus("Y");
			wxuser.setUpdated_by("admin");
			wxuser.setUpdated_time(format.format(new Date()));
			wxuser.setUsername(user.getNickname());
			wxuser.setHead_portrait(user.getHeadimgurl());
			wxuser.setCountry(user.getCountry());
			wxuser.setProvince(user.getProvince());
			wxuser.setCity(user.getCity());
			wxuser.setSex(user.getSex());
			wxuser.setNickname(user.getNickname());
			commonHibernateBaseDao.saveOrUpdate(wxuser);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 更改微用户的信息
	 */
	@Override
	public boolean updateWechatUser(WechatUser user) {
		boolean isResult = false;
		WXUserVo wxuser = new WXUserVo();
		wxuser.setOpenid(user.getOpenId());
		wxuser.setCreated_by("admin");
		wxuser.setCreated_time(format.format(new Date()));
		wxuser.setRole("1");
		wxuser.setStatus("Y");
		wxuser.setUpdated_by("admin");
		wxuser.setUpdated_time(format.format(new Date()));
		wxuser.setUsername(user.getNickname());
		wxuser.setHead_portrait(user.getHeadimgurl());
		wxuser.setCountry(user.getCountry());
		wxuser.setProvince(user.getProvince());
		wxuser.setCity(user.getCity());
		wxuser.setSex(user.getSex());
		//wxuser.setNickname(BASE64Utils.getBASE64(user.getNickname()));
		wxuser.setNickname(user.getNickname());
		//ibaties更新
//		try {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("openid", user.getOpenId());
//			params.put("status", "Y");
//			//commonBatiesBaseDao.updateObject("wxService.updateWXUser", params);
//			isResult = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		//hibernate更新
		try {
			commonHibernateBaseDao.saveOrUpdate(wxuser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 取消关注公众号
	 */
	@Override
	public boolean unSubscribeWX(String openid) {
		boolean isResult = false;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openid", openid);
			commonBatiesBaseDao.updateObject("wxService.unSubscribeWX", params);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 得到当前微用户的信息
	 */
	@Override
	public WXUserVo getWechatUserInfo(String openid) {
		WXUserVo user = null;
		try {
			user = commonHibernateBaseDao.getEntityByStringId(WXUserVo.class,openid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InformationDto> queryInformationVoInfos(
			Map<String, Object> params) {
		List<InformationDto> list = null;
		try {
			list = (List<InformationDto>) commonBatiesBaseDao.queryForList(
					"information.queryInformations", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer queryInformationVoCount(Map<String, Object> params) {

		Integer count = null;
		try {
			count = commonBatiesBaseDao.queryForCount(
					"information.queryInformationsCount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Integer queryInformationVoCount_by_key(Map<String, Object> params) {

		Integer count = null;
		try {
			count = commonBatiesBaseDao.queryForCount(
					"information.queryInformationsCount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public UserMarkVo queryUserMarkVoInfo(String openid) {
		UserMarkVo user = null;
		try {
			user = commonHibernateBaseDao.getEntityByStringId(UserMarkVo.class,
					openid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public WXUserDto getWechatUserInfo(Map<String, Object> params) {
		WXUserDto user = null;
		try {
			user = (WXUserDto) this.commonBatiesBaseDao.queryForObject(
					"WXUser.queryUserInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public WXUserDto getWechatUserGold(Map<String, Object> params) {
		WXUserDto user = null;
		try {
			user = (WXUserDto) this.commonBatiesBaseDao.queryForObject(
					"WXUser.querygold", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean updateWechatUserInfo(WXUserVo wXUserVo) {
		boolean result = false;
		try {
			result = this.commonHibernateBaseDao.saveOrUpdate(wXUserVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkVo> queryMarkVoInfo(Map<String, Object> params) {
		List<MarkVo> list = null;
		try {
			list = (List<MarkVo>) this.commonBatiesBaseDao.queryForList(
					"information.queryMarkVoInfo", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkVo> queryInfoMarkVoInfo(Map<String, Object> params) {
		List<MarkVo> list = null;
		try {
			list = (List<MarkVo>) this.commonBatiesBaseDao.queryForList(
					"information.queryInfoMarkVoInfo", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkVo> queryAllMarkVoInfo(Map<String, Object> params) {
		List<MarkVo> list = null;
		try {
			list = (List<MarkVo>) this.commonBatiesBaseDao.queryForList(
					"information.queryAllMarkVoInfo", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public UserGoldCountVo getUserWalletByOpenId(String openId) {
		UserGoldCountVo userWallet = null;
		try {
			userWallet = this.commonHibernateBaseDao.getEntityByStringId(
					UserGoldCountVo.class, openId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userWallet;
	}

	@Override
	public boolean saveMarkVoInfo(String openId, String marks) {
		boolean result = false;
		UserMarkVo mark = new UserMarkVo();
		mark.setOpenid(openId);
		mark.setStr_mark(marks);
		try {
			result = commonHibernateBaseDao.saveOrUpdate(mark);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	@Override
	public InformationDto queryInformation(Map<String, Object> params) {
		InformationDto information = new InformationDto();
		try {
			information = (InformationDto) commonBatiesBaseDao.queryForObject(
					"information.queryInformationDto", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return information;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WXUserVo> getWxUserTexpense(Map<String, Object> params) {
		List<WXUserVo> wxUserList = null;
		try {
			wxUserList = (List<WXUserVo>) this.commonBatiesBaseDao
					.queryForList("WXUser.queryExpenseUser", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return wxUserList;
	}

	@Override
	public boolean updatePrice(UserGoldCountVo user,
			UserWalletExpenseInfo expense, UserWalletIncomeInfo income) {
		boolean result = false;
		try {
			if (user != null) {
				result = commonHibernateBaseDao.saveOrUpdate(user);
			}
			if (expense != null) {
				result = commonHibernateBaseDao.save(expense);
			}
			if (income != null) {
				result = commonHibernateBaseDao.save(income);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updatePrice(UserGoldCountVo user, ExpertMoneyVo infouser) {
		boolean result = false;
		try {
			if (user != null) {
				result = commonHibernateBaseDao.saveOrUpdate(user);
			}
			if (infouser != null) {
				result = commonHibernateBaseDao.update(infouser);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean addAward(InformationAwardInfoVo award) {
		boolean result = false;
		try {
			result = commonHibernateBaseDao.save(award);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public List<AreaVo> getAreaVo(Map<String, Object> params) {
		List<AreaVo> AreaVoAll = null;
		try {
			AreaVoAll = (List<AreaVo>) this.commonBatiesBaseDao.queryForList(
					"Area.queryArea", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return AreaVoAll;
	}

	@Override
	public Integer queryRewardercounts(Map<String, Object> params) {
		Integer count = null;
		try {
			count = this.commonBatiesBaseDao.queryForCount(
					"WXUser.queryRewardercounts", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<WXUserDto> queryRewarders(Map<String, Object> params) {
		List<WXUserDto> rewarders = null;
		try {
			rewarders = (List<WXUserDto>) this.commonBatiesBaseDao
					.queryForList("WXUser.queryRewarders", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rewarders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WXUserVo> queryInfoRewarders(Map<String, Object> params) {
		List<WXUserVo> rewarders = null;
		try {
			rewarders = (List<WXUserVo>) this.commonBatiesBaseDao.queryForList(
					"WXUser.queryInfoRewarders", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rewarders;
	}

	/**
	 * 专家认证的方法
	 */
	@Override
	public boolean saveUserChatLog(UserCharLogVo userCharLogVo) {
		boolean isResult = false;
		try {
			// DB中是否存在
			WXUserVo user = commonHibernateBaseDao.getEntityByStringId(
					WXUserVo.class, userCharLogVo.getOpenid());
			if (user != null) {
				String role = user.getRole();// 得到用户的角色，是专家，还是普通用户，
				if (!StringUtils.equals("2", role)) {// 说明当前用户不是专家，可能是普通用户．也可能是待认证的状态
					UserCharLogVo DBuser = commonHibernateBaseDao
							.getEntityByStringId(UserCharLogVo.class,
									userCharLogVo.getOpenid());
					if (DBuser != null) {// 说明此用户之前已红输入了验证码，可能是验证码不正确，再次输入
						commonHibernateBaseDao.update(userCharLogVo);
					} else {
						commonHibernateBaseDao.save(userCharLogVo);
					}
					updateWXUserRole(userCharLogVo.getOpenid());
				}
			}
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean isAttentionRedPacket(String openid) {
		boolean isResult = false;
		try {
			DigestRedPacketVo entity = commonHibernateBaseDao.getEntityByStringId(DigestRedPacketVo.class, openid);
			if (entity != null) {// 说明之前者用户，已领取了关注红包
				isResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean isAttentionGold(String openid) {
		boolean isResult = false;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openid", openid);
			params.put("send_type", "1");// 1关注,2分享奖励记录，3系统奖励
			DigestGoldVo entity = (DigestGoldVo) commonBatiesBaseDao.queryForObject("wxService.isAttentionGold", params);
			if (entity != null) {// 说明用户已领取了关注金币
				isResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean saveAttentionGold(DigestGoldVo entity) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.save(entity);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean saveAttentionRedPacket(DigestRedPacketVo entity) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.save(entity);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public ExpertMoneyVo getUserMoneyByOpenId(String openId) {
		ExpertMoneyVo expertMoney = null;
		try {
			expertMoney = this.commonHibernateBaseDao.getEntityByStringId(
					ExpertMoneyVo.class, openId);
			if (expertMoney == null) {
				expertMoney = new ExpertMoneyVo();
				expertMoney.setMoney_count(0);
				expertMoney.setMoney_usable(0);
				expertMoney.setOpenid(openId);
				commonHibernateBaseDao.save(expertMoney);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expertMoney;
	}

	@Override
	public int getWxUserTexpenseCount(Map<String, Object> params) {
		int count = 0;
		try {
			count = this.commonBatiesBaseDao.queryForCount(
					"WXUser.queryExpenseUserCount", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<AreaVo> getAreaVoRoot(Map<String, Object> params) {
		List<AreaVo> areaAll = null;
		try {
			areaAll = (List<AreaVo>) this.commonBatiesBaseDao.queryForList(
					"Area.queryAreaRoot", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return areaAll;
	}

	@Override
	public void saveBrowse(InformationBrowseInfoVo browse) {
		try {
			commonHibernateBaseDao.save(browse);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean updateWXUserRole(String openid) {
		boolean isResult = false;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openid", openid);
			commonBatiesBaseDao.updateObject("wxService.updateWXUserRole",
					params);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public Integer findBrowseInfo(Map<String, Object> params) {
		Integer browse = null;
		try {
			browse = this.commonBatiesBaseDao.queryForCount(
					"information.findBrowseInfo", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return browse;
	}

	@Override
	public InformationBrowseCountVo findBrowseCount(String information_id) {
		InformationBrowseCountVo BrowseCount = null;
		try {
			BrowseCount = commonHibernateBaseDao.getEntityByStringId(
					InformationBrowseCountVo.class, information_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BrowseCount;
	}

	@Override
	public void saveBrowseCount(InformationBrowseCountVo browseCount) {
		try {
			commonHibernateBaseDao.saveOrUpdate(browseCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存分享得金币的记录
	 */
	@Override
	public boolean saveShareGold(String openid, String information_id,Integer type) {
		boolean isResult = false;
		Integer config_share_count = Integer.parseInt(ConfigUtils
				.getValue("share_times"));
		try {
			for (int i = 0; i <= 1; i++) {
				if (i == 1) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("openid", openid);
					params.put("send_type", 2);
					params.put("send_time", "%" + format.format(new Date())
							+ "%");
					// Integer
					// count=commonBatiesBaseDao.queryForCount("wxService.queryDisgetGoldCount",
					// params);//查询DB中是否已有今天用户分享的记录
					List<DigestGoldVo> list = (List<DigestGoldVo>) commonBatiesBaseDao
							.queryForList("wxService.queryDisgetGoldList",
									params);
					if (list != null && list.size() > 0) {// 说明今天用户已产生了分享得到金币记录
						DigestGoldVo entity = list.get(0);// 得到DB中存在的值
						// 比较share_count是设定的值的大小
						Integer DBcount = entity.getShare_count();
						if (DBcount >= config_share_count) {// 说是该用户今天的分享已达到设定的次数
						} else {// 说明该用户今天内还可以得到分享的金币
							entity.setGold(entity.getGold()
									+ Float.valueOf(ConfigUtils
											.getValue("share_gold_num")));
							entity.setSend_time(DateUtil.dateToStr(new Date()));
							entity.setUpdated_by("admin");
							entity.setUpdated_time(DateUtil
									.dateToStr(new Date()));
							entity.setShare_count(Integer.valueOf(DBcount) + 1);
							commonHibernateBaseDao.update(entity);
							saveUsergoldCount(openid, Float.valueOf(ConfigUtils
									.getValue("share_gold_num")));
						}
					} else {// y说明今天还没有得到分享金币
						DigestGoldVo entity = new DigestGoldVo();
						entity.setGold(Float.valueOf(ConfigUtils
								.getValue("share_gold_num")));
						entity.setSend_time(DateUtil.dateToStr(new Date()));
						entity.setCreated_by("admin");
						entity.setCreated_time(DateUtil.dateToStr(new Date()));
						entity.setOpenid(openid);

						entity.setSend_type("2");
						entity.setUpdated_by("admin");
						entity.setTid(UUIDUtils.getUUID());
						entity.setUpdated_time(DateUtil.dateToStr(new Date()));
						entity.setShare_count(1);
						commonHibernateBaseDao.save(entity);
						saveUsergoldCount(openid, Float.valueOf(ConfigUtils
								.getValue("share_gold_num")));
					}
				}
			}
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 保存分享的明细
	 */
	@Override
	public boolean saveShareInfo(String openid, String information_id) {
		boolean isResult = false;
		try {
			InformationShareInfoVo entity = new InformationShareInfoVo();
			entity.setCreated_by("admin");
			entity.setCreated_time(DateUtil.dateToStr(new Date()));
			entity.setInformation_id(information_id);
			entity.setShare_openid(openid);
			entity.setTid(UUIDUtils.getUUID());
			entity.setUpdated_by("admin");
			entity.setUpdated_time(DateUtil.dateToStr(new Date()));
			commonHibernateBaseDao.save(entity);
			saveShareCount(information_id, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 汇总用户的金币
	 */
	@Override
	public boolean saveUsergoldCount(String openid, Float gold) {
		boolean isResult = false;
		try {
			// 先确认DB中是否已存在该数据
			UserGoldCountVo DBentity = commonHibernateBaseDao.getEntityByStringId(UserGoldCountVo.class, openid);
			if (DBentity != null) {// 说明ＤＢ中已存在该数据
				DBentity.setUpdated_by("admin");
				DBentity.setUpdated_time(DateUtil.dateToStr(new Date()));
				DBentity.setGold_count(DBentity.getGold_count() + gold);
				commonHibernateBaseDao.update(DBentity);
			} else {// 说是DB中不没有该数据
				UserGoldCountVo entity = new UserGoldCountVo();
				entity.setCreated_by("admin");
				entity.setCreated_time(DateUtil.dateToStr(new Date()));
				entity.setGold_count(gold);
				entity.setOpenid(openid);
				entity.setUpdated_by("admin");
				entity.setUpdated_time(DateUtil.dateToStr(new Date()));
				commonHibernateBaseDao.save(entity);
			}
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	/**
	 * 汇总文章分享
	 */
	@Override
	public boolean saveShareCount(String information_id, Integer share_count) {
		boolean isResult = false;
		try {
			InformationShareCountVo DBentity = commonHibernateBaseDao
					.getEntityByStringId(InformationShareCountVo.class,
							information_id);
			if (DBentity != null) {
				DBentity.setUpdated_by("admin");
				DBentity.setUpdated_time(DateUtil.dateToStr(new Date()));
				DBentity.setShare_count(DBentity.getShare_count() + share_count);
				commonHibernateBaseDao.update(DBentity);
			} else {
				InformationShareCountVo entity = new InformationShareCountVo();
				entity.setCreated_by("admin");
				entity.setCreated_time(DateUtil.dateToStr(new Date()));
				entity.setInformation_id(information_id);
				entity.setShare_count(share_count);
				entity.setUpdated_by("admin");
				entity.setUpdated_time(DateUtil.dateToStr(new Date()));
				commonHibernateBaseDao.save(entity);
			}
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public double findRewarderSum(Map<String, Object> params) {
		double result = 0;

		try {
			WXUserDto wxUser = (WXUserDto) this.commonBatiesBaseDao
					.queryForObject("WXUser.queryRewarderSum", params);
			result = wxUser.getGold();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public BoutiqueVo queryTittleBoutique() {
		BoutiqueVo boutiqueVo = null;
		try {
			boutiqueVo = (BoutiqueVo) this.commonBatiesBaseDao.queryForObject(
					"information.queryTittleBoutique", null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return boutiqueVo;
	}

	@Override
	public List<InformationVo> queryBoutique() {
		List<InformationVo> list = null;
		// try{
		// list = (List<BoutiqueVo>)
		// this.commonBatiesBaseDao.queryForList("information.queryBoutique",
		// null);
		// }catch(Exception ex){
		// ex.printStackTrace();
		// }
		String sql = " from InformationVo t where t.best_flag = 'N'";
		list = commonHibernateBaseDao.getList(sql);
		return list;
	}

	@Override
	public List<InformationVo> queryBoutique_change() {
		List<InformationVo> list = null;
		String sql = " from InformationVo t where t.best_flag = 'Y'";
		list = commonHibernateBaseDao.getList(sql);
		return list;
	}

	@Override
	public List<InformationVo> queryBoutique(String c) {
		String type = "";
		if (c != null && !"".equals(c)) {
			int i = Integer.parseInt(c);
			if (i <= 9) {
				type = "0" + i;
			} else {
				type = i + "";
			}
		}
		List<InformationVo> list = null;
		String sql = " from InformationVo t where t.information_type = '"
				+ type + "'";
		list = commonHibernateBaseDao.getList(sql);
		return list;
	}

	@Override
	public UserGoldCountVo getUserGoldCountVoInfo(String openid) {
		UserGoldCountVo entity = null;
		try {
			entity = commonHibernateBaseDao.getEntityByStringId(
					UserGoldCountVo.class, openid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public boolean updateAwardCount(String informationid, float price) {
		InformationAwardCountVo awardCount = null;
		try {
			awardCount = commonHibernateBaseDao.getEntityByStringId(
					InformationAwardCountVo.class, informationid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (awardCount == null) {
			awardCount = new InformationAwardCountVo();
			awardCount.setInformation_id(informationid);
			awardCount.setGold_count(price);
		} else {
			awardCount.setGold_count(awardCount.getGold_count() + price);
		}
		boolean result = false;
		try {
			result = commonHibernateBaseDao.saveOrUpdate(awardCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WXUserVo> queryTopTenExpenseUser(Map<String, Object> params) {
		List<WXUserVo> wXUserVos = null;
		try {
			wXUserVos = (List<WXUserVo>) this.commonBatiesBaseDao.queryForList(
					"WXUser.queryTopTenExpenseUser", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wXUserVos;
	}

	@Override
	public boolean updateWechatUser(WXUserVo wechatUser) {
		boolean result = false;
		try {
			// result = this.commonHibernateBaseDao.update(wechatUser);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openid", wechatUser.getOpenid());
			params.put("status", wechatUser.getStatus());
			/*
			 * params.put("publicno", wechatUser.getPublicno());
			 * params.put("username",
			 * BASE64Utils.getBASE64(wechatUser.getUsername()));
			 * params.put("head_portrait", wechatUser.getHead_portrait());
			 * params.put("role", wechatUser.getRole()); params.put("expert",
			 * wechatUser.getExpert());
			 * 
			 * params.put("created_by", "admin"); params.put("created_time",
			 * format.format(new Date())); params.put("updated_by", "admin");
			 * params.put("updated_time", format.format(new Date()));
			 * params.put("country", wechatUser.getCountry());
			 * params.put("province", wechatUser.getProvince());
			 * params.put("city", wechatUser.getCity()); params.put("sex",
			 * wechatUser.getSex()); params.put("age", wechatUser.getAge());
			 * params.put("frist_show", wechatUser.getFrist_show());
			 * 
			 * params.put("group_id", wechatUser.getGroup_id());
			 * 
			 * params.put("check_time", wechatUser.getCheck_time());
			 */
			commonBatiesBaseDao.updateObject("wxService.updateWXUser", params);
			result = true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public void saveFristshow(String openId) {
		WXUserVo wXUserVo = null;
		try {
			wXUserVo = this.commonHibernateBaseDao.getEntityByStringId(
					WXUserVo.class, openId);
			wXUserVo.setFrist_show("1");
			this.commonHibernateBaseDao.update(wXUserVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkVo> queryAllType(Map<String, Object> params) {
		List<MarkVo> list = null;
		try {
			list = (List<MarkVo>) commonBatiesBaseDao.queryForList(
					"information.queryAllType", params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateWXUserVo(WXUserVo user) {
		boolean s = false;
		try {
			this.commonHibernateBaseDao.update(user);
			s = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	@Override
	public boolean isExistQianDao(String openid) {
		boolean isResult = false;
		QianDao user = commonHibernateBaseDao.getEntityByStringId(
				QianDao.class, openid);
		if (user != null) {
			isResult = true;
		}
		return isResult;
	}

	@Override
	public boolean updateQianDao(QianDao qd) {
		boolean s = false;
		try {
			this.commonHibernateBaseDao.update(qd);
			s = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	@Override
	public boolean saveQianDao(QianDao qd) {
		boolean s = false;
		try {
			this.commonHibernateBaseDao.save(qd);
			s = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	@Override
	public QianDao getQianDao(String openid) {
		QianDao qd = commonHibernateBaseDao.getEntityByStringId(QianDao.class,
				openid);
		return qd;
	}

	@Override
	public boolean isQianDaoToday(String openid) {
		String date = Tools.getYMD(new Date());
		boolean isResult = false;
		String sql = " from QianDao t where t.date = '" + date
				+ "' and t.openid = '" + openid + "'";
		List<QianDao> list = commonHibernateBaseDao.getList(sql);
		if (list != null && !list.isEmpty()) {
			isResult = true;
		}
		return isResult;
	}

	@Override
	public List<InformationDto> queryInformationVoInfos_by_key(
			Map<String, Object> params) {
		List<InformationDto> list = null;
		try {
			list = (List<InformationDto>) commonBatiesBaseDao.queryForList(
					"information.queryInformations_by_key", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean savePrize(WxPrize wp) {
		boolean result = false;
		try {
			result = commonHibernateBaseDao.save(wp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Plan> getPlans(Map<String, Object> params) {
		List<Plan> list = null;
		try {
			list = (List<Plan>) commonBatiesBaseDao.queryForList(
					"information.queryPlans", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PlanDTO> getPlansF1(Map<String, Object> params) {
		List<PlanDTO> list = null;
		try {
			list = (List<PlanDTO>) commonBatiesBaseDao.queryForList(
					"information.queryPlansF1", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PlanDTO> getPlansF3(Map<String, Object> params) {
		List<PlanDTO> list = null;
		try {
			list = (List<PlanDTO>) commonBatiesBaseDao.queryForList(
					"information.queryPlansF3", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PlanDTO> getPlansF4(Map<String, Object> params) {
		List<PlanDTO> list = null;
		try {
			list = (List<PlanDTO>) commonBatiesBaseDao.queryForList(
					"information.queryPlansF4", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PlanDTO> getPlansF5(Map<String, Object> params) {
		List<PlanDTO> list = null;
		try {
			list = (List<PlanDTO>) commonBatiesBaseDao.queryForList(
					"information.queryPlansF5", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public WxClient getWxClient(String openid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("openid", openid);
		WxClient wc = (WxClient) commonBatiesBaseDao.queryForObject(
				"information.getWxClientByOpenid", params);
		return wc;
	}

	@Override
	public WxClient getWxClientByTelephone(String telephone) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("telephone", telephone);
		WxClient wc = (WxClient) commonBatiesBaseDao.queryForObject(
				"information.getWxClientByTelephone", params);
		return wc;
	}

	@Override
	public void saveOrUpdateWxClient(WxClient wxClient) {
		commonHibernateBaseDao.saveOrUpdate(wxClient);
	}

	@Override
	public List<WxMailList> getWxMailList(Map<String, Object> params) {
		List<WxMailList> list = null;
		try {
			list = (List<WxMailList>) commonBatiesBaseDao.queryForList(
					"information.getWxMailList", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
