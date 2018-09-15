package com.tentinet.weixin.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.AreaVo;
import com.tentinet.app.bean.BoutiqueVo;
import com.tentinet.app.bean.DigestGoldVo;
import com.tentinet.app.bean.DigestRedPacketVo;
import com.tentinet.app.bean.ExpertMoneyVo;
import com.tentinet.app.bean.InformationAwardInfoVo;
import com.tentinet.app.bean.InformationBrowseCountVo;
import com.tentinet.app.bean.InformationBrowseInfoVo;
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
import com.tentinet.weixin.entity.WechatUser;

/**
 * 微信调后台接口
 */
public interface WXCMSClientService {
	/**
	 * 获取通讯录
	 * @param openid
	 * @return
	 */
	public List<WxMailList> getWxMailList(Map<String, Object> params);
	/**
	 * 获取流向
	 * @param openid
	 * @return
	 */
	public List<Plan> getPlans(Map<String, Object> params);
	/**
	 * 获取签到信息
	 * @param openid
	 * @return
	 */
	public QianDao getQianDao(String openid);
	/**
	 * 根据openID获取好伙伴
	 * @param openid
	 * @return
	 */
	public WxClient getWxClient(String openid);
	/**
	 * 是否是今天签到
	 * @param openid
	 * @return
	 */
	public boolean isQianDaoToday(String openid);
	/***
	 * 是否存在签到信息
	 * @param openid
	 * @return
	 */
	public boolean isExistQianDao(String openid);
	/**
	 * 更新签到
	 * @param qd
	 * @return
	 */
	public boolean updateQianDao(QianDao qd);
	/***
	 * 保存签到
	 * @param qd
	 * @return
	 */
	public boolean saveQianDao(QianDao qd);
	/***
	 * 保存用户抽奖信息
	 * @param qd
	 * @return
	 */
	public boolean savePrize(WxPrize wp);
	/***
	 * 更新微信用户
	 * @param user
	 * @return
	 */
	public boolean updateWXUserVo(WXUserVo user);
	/**
	 * 确认DB中是否已该微用户 的对象
	 * 
	 * @param openid
	 * @return
	 */
	public boolean isExistWechatUser(String openid);

	/**
	 * 首次关注用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean saveWechatUser(WechatUser user);

	/**
	 * 微用户可能资料信息有变更,要进行对应的维护动作
	 * 
	 * @param wachatUser
	 * @return
	 */
	public boolean updateWechatUser(WechatUser wachatUser);

	/**
	 * 微用户取消关注公众号
	 * 
	 * @param openid
	 * @return
	 */
	public boolean unSubscribeWX(String openid);

	/**
	 * 得到单个微用户的信息
	 * 
	 * @param openid
	 * @return
	 */
	public WXUserVo getWechatUserInfo(String openid);

	/**
	 * #########################################################以下是资讯部份##############################################3
	 */

	/**
	 * 得到所有微信端的资讯内容
	 * 
	 * @param params
	 * @return
	 */
	public List<InformationDto> queryInformationVoInfos(
			Map<String, Object> params);
	/**
	 * 得到所有微信端的资讯内容 根据关键字
	 * 
	 * @param params
	 * @return
	 */
	public List<InformationDto> queryInformationVoInfos_by_key(
			Map<String, Object> params);

	/**
	 * 查询所有微信端的资讯数量
	 * 
	 * @param params
	 * @return
	 */
	public Integer queryInformationVoCount(Map<String, Object> params);
	/**
	 * 查询所有微信端的资讯数量  根据关键字
	 * 
	 * @param params
	 * @return
	 */
	public Integer queryInformationVoCount_by_key(Map<String, Object> params);

	/**
	 * 查询出当前用户的标签
	 * 
	 * @param params
	 * @return
	 */
	public UserMarkVo queryUserMarkVoInfo(String openid);
	
	/**
	 * 查询用户信息
	 * @param params
	 * @return
	 */
	public WXUserDto getWechatUserInfo(Map<String, Object> params) ;
	    
	/**
	 * 修改用户信息
	 * @param wXUserVo
	 * @return
	 */
	public boolean updateWechatUserInfo(WXUserVo wXUserVo);
	
	
	/**
	 * 查询标签信息
	 * 
	 * @param params
	 * @return
	 */
	public List<MarkVo> queryMarkVoInfo(Map<String, Object> params);
	
	/**
	 * 获取用户金币账户
	 * @param openId
	 * @return
	 * 
	 */
	public UserGoldCountVo getUserWalletByOpenId(String openId);
	
	/**
	 * 获取用户钱包
	 * @param openId
	 * @return
	 */
	public ExpertMoneyVo getUserMoneyByOpenId(String openId);

	/**
	 * 保存用户标签信息
	 * @param openId
	 * @return
	 */
	public boolean saveMarkVoInfo(String openId, String marks);

	public List<MarkVo> queryAllMarkVoInfo(Map<String, Object> params);

	public InformationDto queryInformation(Map<String, Object> params);

	
	/**
	 * 获取支出的用户
	 * @param params
	 */
	public List<WXUserVo> getWxUserTexpense(Map<String, Object> params);
	
	/**
	 * 获取支出的用户总数
	 * @param params
	 */
	public int getWxUserTexpenseCount(Map<String, Object> params);
	
	/**
	 * 获取地区
	 */
	public List<AreaVo> getAreaVo(Map<String,Object> params);

	public boolean updatePrice(UserGoldCountVo user,UserWalletExpenseInfo expense,UserWalletIncomeInfo income);
	
	public boolean updatePrice(UserGoldCountVo user,ExpertMoneyVo infouser);
	
	public WXUserDto getWechatUserGold(Map<String, Object> params);

	public boolean addAward(InformationAwardInfoVo award);

	public List<WXUserDto> queryRewarders(Map<String, Object> params);

	public Integer queryRewardercounts(Map<String, Object> params);

	
	/**
	 * *****************************************************************以下是首次关注,以及分享,微信支付等的接口*******************************
	 */
	/**
	 * 系统对当前用户是否已发送过红包
	 * @param openid
	 * @return
	 */
	public boolean isAttentionRedPacket(String openid);
	/**
	 * 用户首次关注对就就的增加用户的金币
	 * @param openid
	 * @param gold
	 * @return
	 */
	public boolean  isAttentionGold(String openid);
	/**
	 * 保存发关注金币记录
	 * @return
	 */
	public boolean saveAttentionGold(DigestGoldVo entity);
	/**
	 * 保存发关注红包记录
	 * @return
	 */
	public boolean saveAttentionRedPacket(DigestRedPacketVo entity);
	
	
	/**
	 * ****************************************************专家资格待认证方法
	 */
	public boolean saveUserChatLog(UserCharLogVo userCharLogVo);
	
	public boolean updateWXUserRole(String openid);
	
	/**
	 * 获取地区Root
	 */
	public List<AreaVo> getAreaVoRoot(Map<String,Object> params);

	public void saveBrowse(InformationBrowseInfoVo browse);

	public Integer findBrowseInfo(Map<String, Object> params);

	public InformationBrowseCountVo findBrowseCount(String information_id);

	public void saveBrowseCount(InformationBrowseCountVo browseCount);
	/**
	 * 
	 */
	public boolean saveShareGold(String openid,String information_id,Integer type);
	public boolean saveShareInfo(String openid,String information_id);

	List<WXUserVo> queryInfoRewarders(Map<String, Object> params);

	public List<MarkVo> queryInfoMarkVoInfo(Map<String, Object> params);
	
	/**
	 * 获取单个文章发放总净额
	 * @param params
	 * @return
	 */
	public double findRewarderSum(Map<String,Object> params);
	
	/**
	 * **********************************************以下是汇总信息的倿口***************************
	 */
	/**
	 * 保存系统发送的给用户的金币的信息
	 * @return
	 */
	public boolean saveUsergoldCount(String openid,Float gold);
	
	/**
	 *保存分享的汇总信息
	 */
	public boolean saveShareCount(String information_id,Integer share_count);

	public BoutiqueVo queryTittleBoutique();

	public List<InformationVo> queryBoutique();
	public List<InformationVo> queryBoutique_change();
	public List<InformationVo> queryBoutique(String c);
	
	/**
	 * 
	 */
	public UserGoldCountVo getUserGoldCountVoInfo(String openid);

	public boolean updateAwardCount(String informationid, float price);
	
	/**
	 * 查询前十名用户
	 * @param params
	 * @return
	 */
	public List<WXUserVo> queryTopTenExpenseUser(Map<String,Object> params);
    public boolean updateWechatUser(WXUserVo wechatUser);

	public void saveFristshow(String openId);

	public List<MarkVo> queryAllType(Map<String, Object> params);
	/***
	 * 费用查询
	 * @param params
	 * @return
	 */
	public List<PlanDTO> getPlansF1(Map<String, Object> params);
	public List<PlanDTO> getPlansF3(Map<String, Object> params);
	public List<PlanDTO> getPlansF4(Map<String, Object> params);
	public List<PlanDTO> getPlansF5(Map<String, Object> params);
	public WxClient getWxClientByTelephone(String telephone);
	public void saveOrUpdateWxClient(WxClient wxClient);
}
