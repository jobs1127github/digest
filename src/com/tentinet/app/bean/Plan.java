package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tentinet.app.util.DateUtils;

/***
 * 流向实体
 * 流向bean和数据库表一一对应的，舍弃了部分不需要的字段
 * 各个字段取名不友好，是系统历史遗留问题，参考注释使用即可。
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "kdb_nss_log_plans")
public class Plan implements Serializable {
    /****匹配注册库start */
    private String mgr="";//经理 
    private String daqu="";//大区 
    private String space="";//片区 
    private String city="";//地市 
    private String county="";//区县 
    private String zhenghe_qs="";//QS名(是同一个终端但是名字不同为了统一而取的名字，用于销售口数据统计)  
    private String fenbu="";//分部
    private String terminaltype="";//类型渠道
    private String terminallev="";//级别
    private String province="";//省份 /***财务要求 如下不能为空 **/
    /****匹配注册库end */
    
    
    /*** 录入模板 start */
    private String judge="";//行为：流向类型 录入
    private String sdate="";//日期 录入
    private String commercial1="";//出货甲方：比如纯销流向的商业公司 录入
    private String terminal="";//入货乙方：比如：纯销流向的终端，或调拨的商业公司 录入
    private String product="";//品种
    private String pack="";//包装
    private String count="0";//数量
    private String pihao="";//批号
    private String demo="";//流向备注
    private String client="";//客户
    private String mode="";//模式：自营、预付、小包、大包、虚拟小包、虚拟大包、承包-小包、承包-预付
    private String money6="";//晚交流向标识
    private String status6="";//自然流向审核标识
    private String xieyizhuangtai="";//条线：OTC、Rx
    @Id
    @GeneratedValue
    private Integer id;//id 自动生成
    /*** 录入模板 end */
    
    /*** 系统计算  start ***/
    //其他属性
    private String year="";//年 根据sdate计算
    private String mouth="";//月 根据sdate计算
    private String dataer="";//录入人员 登陆者
    private String scount="0";//生产折量
    private String xsScount="0";//销售折量
    private String today = DateUtils.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss");//录入时间
    private String status8="";//用作通过流向Id修改了字段后的标识
    private String focusonFlag = "";//重点终端标识
    private String goodFriendFlag = "";//好伙伴标识
    private Date mtime=new Date();//更新时间
    
    /***协议相关**/
    private String zhongbiaojiamoney="0";//中标金额
    private String peisonglv="0%";//配送率
    private String kaipiaojiamoney="0";//开票金额
    private String money1="0";//1金额
    private String basic1="0";//1服务费
    private String status1="押";//1状态
    private String money3="0";//3金额
    private String piaoju3="0";//3服务费，之前叫发票费用
    private String status3="押";//3状态
    private String money4="0";//4金额
    private String dabiao4="0";//4服务费，之前叫达标奖励
    private String status4="押";//4状态
    private String kaifa5="0";//5开发奖励
    private String money5="0";//5金额
    private String status5="押";//5状态
    private String kaifaMgr="0";//业务员开发奖励
    private String moneyMgr="0";//业务员奖金金额
    private String statusMgr="押";//业务员奖金状态
    
    private String zhangdan1="";//1账单，该账单为某笔流向的结算账单
    private String daibiao="";//代表
    private String shoukuangren="";//收款人
    private String kaihuhang="";//开户行
    private String zhanghao="";//账号
    private String kaipiaofangshi="";//票据方案
    private String totalmoney="0";//总费用
    private String atsum = "0";//协议测算价，通过协议表赋值
    private String yingyumoney = "0";//标准测试价，通过协议表赋值
    private String yinyukaiguan="0";//作开票价
    private String cuxiao_ed_money="0";//作中标价
    private String jsmode="";//结算方式
    private String clientpolicyId="0";//协议id
    private String moneybill1 = "";//流向的提单日期
    private String policystatus = "0";//协议状态
    private String tidanfangshi="";//提单方式
    private String feiyonglv="0";//中标费用率
    private String yhzhanghao="";//银行账号
    
    /****和提成算法相关**/
    private String tichengbelong="";//片区提成人
    private String daquTcMan="";//大区提成人
    private String zgTcMan="";//区域主管
    private String gxTcMan="";//贡献提成人
    private Double danwitichen=0.0;//片区存量来源
    private String shijiticheng = "0";//片区存量提成
    private String spaceZLDanwei = "0";//片区增量来源
    private String spaceZLTc = "0";//片区增量提成
    private String spaceKouJian = "0";//片区晚交流向扣减
    private String spaceKouJianTc = "0";//片区晚交流向提成
    private String gaoJieDanwei = "0";//高阶来源
    private String gaoJieTc = "0";//高阶提成
    private String gxDanwei = "0";//贡献来源
    private String gxTc = "0";//贡献提成
    private String zgDanwei = "0";//主管存量来源
    private String zgTc = "0";//主管存量提成
    private String zgDanwei_zl = "0";//主管增量来源
    private String zgTc_zl = "0";//主管增量提成
    private String jicengbutie7 = "0";//大区存量来源
    private String money7 = "0";//大区存量提成
    private String daquZLDanwei = "0";//大区增量来源
    private String daquZLTc = "0";//大区增量提成
    private String daquKouJian = "0";//大区晚交流向扣减
    private String daquKouJianTc = "0";//大区晚交流向提成
    private String tcstatus = "押";//提成的状态(押，待，毕)
    private String jianli = "";//提成备注
    private String money8="0";//提成相关用作2017年新开发客户20%来源
    private String thenMgr="";//提成相关用作老经理
    private String status7="";//提成相关用作2017年新开发客户标识
    
    
    
    public Plan() {
    }
	public String getMgr() {
		return mgr;
	}
	public void setMgr(String mgr) {
		this.mgr = mgr;
	}
	public String getDaqu() {
		return daqu;
	}
	public void setDaqu(String daqu) {
		this.daqu = daqu;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getZhenghe_qs() {
		return zhenghe_qs;
	}
	public void setZhenghe_qs(String zhenghe_qs) {
		this.zhenghe_qs = zhenghe_qs;
	}
	public String getFenbu() {
		return fenbu;
	}
	public void setFenbu(String fenbu) {
		this.fenbu = fenbu;
	}
	public String getTerminaltype() {
		return terminaltype;
	}
	public void setTerminaltype(String terminaltype) {
		this.terminaltype = terminaltype;
	}
	public String getTerminallev() {
		return terminallev;
	}
	public void setTerminallev(String terminallev) {
		this.terminallev = terminallev;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getCommercial1() {
		return commercial1;
	}
	public void setCommercial1(String commercial1) {
		this.commercial1 = commercial1;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPihao() {
		return pihao;
	}
	public void setPihao(String pihao) {
		this.pihao = pihao;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMoney6() {
		return money6;
	}
	public void setMoney6(String money6) {
		this.money6 = money6;
	}
	public String getStatus6() {
		return status6;
	}
	public void setStatus6(String status6) {
		this.status6 = status6;
	}
	public String getXieyizhuangtai() {
		return xieyizhuangtai;
	}
	public void setXieyizhuangtai(String xieyizhuangtai) {
		this.xieyizhuangtai = xieyizhuangtai;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMouth() {
		return mouth;
	}
	public void setMouth(String mouth) {
		this.mouth = mouth;
	}
	public String getDataer() {
		return dataer;
	}
	public void setDataer(String dataer) {
		this.dataer = dataer;
	}
	public String getScount() {
		return scount;
	}
	public void setScount(String scount) {
		this.scount = scount;
	}
	public String getXsScount() {
		return xsScount;
	}
	public void setXsScount(String xsScount) {
		this.xsScount = xsScount;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getStatus8() {
		return status8;
	}
	public void setStatus8(String status8) {
		this.status8 = status8;
	}
	public String getFocusonFlag() {
		return focusonFlag;
	}
	public void setFocusonFlag(String focusonFlag) {
		this.focusonFlag = focusonFlag;
	}
	public String getGoodFriendFlag() {
		return goodFriendFlag;
	}
	public void setGoodFriendFlag(String goodFriendFlag) {
		this.goodFriendFlag = goodFriendFlag;
	}
	public String getZhongbiaojiamoney() {
		return zhongbiaojiamoney;
	}
	public void setZhongbiaojiamoney(String zhongbiaojiamoney) {
		this.zhongbiaojiamoney = zhongbiaojiamoney;
	}
	public String getPeisonglv() {
		return peisonglv;
	}
	public void setPeisonglv(String peisonglv) {
		this.peisonglv = peisonglv;
	}
	public String getKaipiaojiamoney() {
		return kaipiaojiamoney;
	}
	public void setKaipiaojiamoney(String kaipiaojiamoney) {
		this.kaipiaojiamoney = kaipiaojiamoney;
	}
	public String getMoney1() {
		return money1;
	}
	public void setMoney1(String money1) {
		this.money1 = money1;
	}
	public String getBasic1() {
		return basic1;
	}
	public void setBasic1(String basic1) {
		this.basic1 = basic1;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getMoney3() {
		return money3;
	}
	public void setMoney3(String money3) {
		this.money3 = money3;
	}
	public String getPiaoju3() {
		return piaoju3;
	}
	public void setPiaoju3(String piaoju3) {
		this.piaoju3 = piaoju3;
	}
	public String getStatus3() {
		return status3;
	}
	public void setStatus3(String status3) {
		this.status3 = status3;
	}
	public String getMoney4() {
		return money4;
	}
	public void setMoney4(String money4) {
		this.money4 = money4;
	}
	public String getDabiao4() {
		return dabiao4;
	}
	public void setDabiao4(String dabiao4) {
		this.dabiao4 = dabiao4;
	}
	public String getStatus4() {
		return status4;
	}
	public void setStatus4(String status4) {
		this.status4 = status4;
	}
	public String getZhangdan1() {
		return zhangdan1;
	}
	public void setZhangdan1(String zhangdan1) {
		this.zhangdan1 = zhangdan1;
	}
	public String getDaibiao() {
		return daibiao;
	}
	public void setDaibiao(String daibiao) {
		this.daibiao = daibiao;
	}
	public String getShoukuangren() {
		return shoukuangren;
	}
	public void setShoukuangren(String shoukuangren) {
		this.shoukuangren = shoukuangren;
	}
	public String getKaihuhang() {
		return kaihuhang;
	}
	public void setKaihuhang(String kaihuhang) {
		this.kaihuhang = kaihuhang;
	}
	public String getZhanghao() {
		return zhanghao;
	}
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	public String getKaipiaofangshi() {
		return kaipiaofangshi;
	}
	public void setKaipiaofangshi(String kaipiaofangshi) {
		this.kaipiaofangshi = kaipiaofangshi;
	}
	public String getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(String totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getAtsum() {
		return atsum;
	}
	public void setAtsum(String atsum) {
		this.atsum = atsum;
	}
	public String getYingyumoney() {
		return yingyumoney;
	}
	public void setYingyumoney(String yingyumoney) {
		this.yingyumoney = yingyumoney;
	}
	public String getYinyukaiguan() {
		return yinyukaiguan;
	}
	public void setYinyukaiguan(String yinyukaiguan) {
		this.yinyukaiguan = yinyukaiguan;
	}
	public String getCuxiao_ed_money() {
		return cuxiao_ed_money;
	}
	public void setCuxiao_ed_money(String cuxiao_ed_money) {
		this.cuxiao_ed_money = cuxiao_ed_money;
	}
	public String getJsmode() {
		return jsmode;
	}
	public void setJsmode(String jsmode) {
		this.jsmode = jsmode;
	}
	public String getClientpolicyId() {
		return clientpolicyId;
	}
	public void setClientpolicyId(String clientpolicyId) {
		this.clientpolicyId = clientpolicyId;
	}
	public String getMoneybill1() {
		return moneybill1;
	}
	public void setMoneybill1(String moneybill1) {
		this.moneybill1 = moneybill1;
	}
	public String getPolicystatus() {
		return policystatus;
	}
	public void setPolicystatus(String policystatus) {
		this.policystatus = policystatus;
	}
	public String getTidanfangshi() {
		return tidanfangshi;
	}
	public void setTidanfangshi(String tidanfangshi) {
		this.tidanfangshi = tidanfangshi;
	}
	public String getFeiyonglv() {
		return feiyonglv;
	}
	public void setFeiyonglv(String feiyonglv) {
		this.feiyonglv = feiyonglv;
	}
	public String getTichengbelong() {
		return tichengbelong;
	}
	public void setTichengbelong(String tichengbelong) {
		this.tichengbelong = tichengbelong;
	}
	public String getDaquTcMan() {
		return daquTcMan;
	}
	public void setDaquTcMan(String daquTcMan) {
		this.daquTcMan = daquTcMan;
	}
	public String getZgTcMan() {
		return zgTcMan;
	}
	public void setZgTcMan(String zgTcMan) {
		this.zgTcMan = zgTcMan;
	}
	public String getGxTcMan() {
		return gxTcMan;
	}
	public void setGxTcMan(String gxTcMan) {
		this.gxTcMan = gxTcMan;
	}
	public Double getDanwitichen() {
		return danwitichen;
	}
	public void setDanwitichen(Double danwitichen) {
		this.danwitichen = danwitichen;
	}
	public String getShijiticheng() {
		return shijiticheng;
	}
	public void setShijiticheng(String shijiticheng) {
		this.shijiticheng = shijiticheng;
	}
	public String getSpaceZLDanwei() {
		return spaceZLDanwei;
	}
	public void setSpaceZLDanwei(String spaceZLDanwei) {
		this.spaceZLDanwei = spaceZLDanwei;
	}
	public String getSpaceZLTc() {
		return spaceZLTc;
	}
	public void setSpaceZLTc(String spaceZLTc) {
		this.spaceZLTc = spaceZLTc;
	}
	public String getSpaceKouJian() {
		return spaceKouJian;
	}
	public void setSpaceKouJian(String spaceKouJian) {
		this.spaceKouJian = spaceKouJian;
	}
	public String getSpaceKouJianTc() {
		return spaceKouJianTc;
	}
	public void setSpaceKouJianTc(String spaceKouJianTc) {
		this.spaceKouJianTc = spaceKouJianTc;
	}
	public String getGaoJieDanwei() {
		return gaoJieDanwei;
	}
	public void setGaoJieDanwei(String gaoJieDanwei) {
		this.gaoJieDanwei = gaoJieDanwei;
	}
	public String getGaoJieTc() {
		return gaoJieTc;
	}
	public void setGaoJieTc(String gaoJieTc) {
		this.gaoJieTc = gaoJieTc;
	}
	public String getGxDanwei() {
		return gxDanwei;
	}
	public void setGxDanwei(String gxDanwei) {
		this.gxDanwei = gxDanwei;
	}
	public String getGxTc() {
		return gxTc;
	}
	public void setGxTc(String gxTc) {
		this.gxTc = gxTc;
	}
	public String getZgDanwei() {
		return zgDanwei;
	}
	public void setZgDanwei(String zgDanwei) {
		this.zgDanwei = zgDanwei;
	}
	public String getZgTc() {
		return zgTc;
	}
	public void setZgTc(String zgTc) {
		this.zgTc = zgTc;
	}
	public String getZgDanwei_zl() {
		return zgDanwei_zl;
	}
	public void setZgDanwei_zl(String zgDanwei_zl) {
		this.zgDanwei_zl = zgDanwei_zl;
	}
	public String getZgTc_zl() {
		return zgTc_zl;
	}
	public void setZgTc_zl(String zgTc_zl) {
		this.zgTc_zl = zgTc_zl;
	}
	public String getJicengbutie7() {
		return jicengbutie7;
	}
	public void setJicengbutie7(String jicengbutie7) {
		this.jicengbutie7 = jicengbutie7;
	}
	public String getMoney7() {
		return money7;
	}
	public void setMoney7(String money7) {
		this.money7 = money7;
	}
	public String getDaquZLDanwei() {
		return daquZLDanwei;
	}
	public void setDaquZLDanwei(String daquZLDanwei) {
		this.daquZLDanwei = daquZLDanwei;
	}
	public String getDaquZLTc() {
		return daquZLTc;
	}
	public void setDaquZLTc(String daquZLTc) {
		this.daquZLTc = daquZLTc;
	}
	public String getDaquKouJian() {
		return daquKouJian;
	}
	public void setDaquKouJian(String daquKouJian) {
		this.daquKouJian = daquKouJian;
	}
	public String getDaquKouJianTc() {
		return daquKouJianTc;
	}
	public void setDaquKouJianTc(String daquKouJianTc) {
		this.daquKouJianTc = daquKouJianTc;
	}
	public String getTcstatus() {
		return tcstatus;
	}
	public void setTcstatus(String tcstatus) {
		this.tcstatus = tcstatus;
	}
	public String getJianli() {
		return jianli;
	}
	public void setJianli(String jianli) {
		this.jianli = jianli;
	}
	public String getMoney8() {
		return money8;
	}
	public void setMoney8(String money8) {
		this.money8 = money8;
	}
	public String getThenMgr() {
		return thenMgr;
	}
	public void setThenMgr(String thenMgr) {
		this.thenMgr = thenMgr;
	}
	public String getStatus7() {
		return status7;
	}
	public void setStatus7(String status7) {
		this.status7 = status7;
	}
	public String getYhzhanghao() {
		return yhzhanghao;
	}
	public void setYhzhanghao(String yhzhanghao) {
		this.yhzhanghao = yhzhanghao;
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getKaifa5() {
		return kaifa5;
	}
	public void setKaifa5(String kaifa5) {
		this.kaifa5 = kaifa5;
	}
	public String getMoney5() {
		return money5;
	}
	public void setMoney5(String money5) {
		this.money5 = money5;
	}
	public String getStatus5() {
		return status5;
	}
	public void setStatus5(String status5) {
		this.status5 = status5;
	}
	public String getKaifaMgr() {
		return kaifaMgr;
	}
	public void setKaifaMgr(String kaifaMgr) {
		this.kaifaMgr = kaifaMgr;
	}
	public String getMoneyMgr() {
		return moneyMgr;
	}
	public void setMoneyMgr(String moneyMgr) {
		this.moneyMgr = moneyMgr;
	}
	public String getStatusMgr() {
		return statusMgr;
	}
	public void setStatusMgr(String statusMgr) {
		this.statusMgr = statusMgr;
	}
}
