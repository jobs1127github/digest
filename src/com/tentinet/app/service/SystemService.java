package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.DataDictionaryVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.QianDao;

/**
 * 系统字典公共接口
 *
 */
public interface SystemService {
	public void updateMark(MarkVo m);
	public QianDao getQianDaoByOpenId(String openid);
	public List<QianDao> getQiandaolist(String andsql);
	public int getCountQiandao(String andsql);
	public void updateQiandao(QianDao qd);
	
	public boolean savaMarkVo(MarkVo data);
	
	public boolean savaMarkVo_test(MarkVo data,MarkVo data2);
	
	public boolean deleteMarkVo(MarkVo data);
	public boolean updateMarkVo(String mark_code,String mark_name);
	public MarkVo getMarkVoInfosById(String datakey);
	/**
	 * 初始化界面要加载的信息
	 * @return
	 */
    public List<DataDictionaryVo> loadinitDatas(Map<String,Object>params);
    
      /**
      * 查询所有的数据字典
      * @param params
      * @return
      */
     public List<DataDictionaryVo> queryDataDictionaryInfos(Map<String,Object>params);
     /**
      * 保存数据字典
      * @param data
      * @return
      */
     public boolean saveDataDictionary(DataDictionaryVo data);
     /**
      * 维护数据字典
      * @param data
      * @return
      */
     public boolean updateDataDictionary(DataDictionaryVo data);
     /**
      * 删除数据字典
      * @param data
      * @return
      */
     public boolean deleteDataDictionary(DataDictionaryVo data);
     /**
      * 查询标签数量
      * @param params
      * @return
      */
     public Integer queryBiaoQianCount(Map<String,Object>params);
     /**
      * 查询数据字典数量
      * @param params
      * @return
      */
     public Integer queryDataDictionaryCount(Map<String,Object>params);
     /**
      * 得到单个数据字典
      * @param datakey
      * @return
      */
     public DataDictionaryVo getDataDictionaryVoInfosById(String datakey);
    
    /**
     * 标签查询
     * @param params
     * @return
     */
     public List<DataDictionaryVo> loadinitMark(Map<String,Object>params);
     /**
      * 专家查询
      * @param params
      * @return
      */
     public List<DataDictionaryVo> loadinitExpert(Map<String,Object>params);
     
     /**
      * 群组查询
      * @param params
      * @return
      */
     public List<DataDictionaryVo> loadinitGroup(Map<String,Object>params);

     
}
