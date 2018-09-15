package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.BoutiqueVo;
import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.dto.InformationDto;

public interface BoutiqueService{
	
   /**
    *  保存精品资讯
    * @param information
    * @return
    */
   public boolean saveBoutique(BoutiqueVo boutique);
   /**
    * 修改精品资讯
    * @param information
    * @return
    */
   public boolean updateBoutique(BoutiqueVo boutique);
   /**
    *  查询单个精品资讯
    * @param informationId
    * @return
    */
   public BoutiqueVo queryBoutiqueById(String informationId);
    /**
     * 查询精品资讯列表信息
     * @param params
     * @return
     */
   public List<BoutiqueVo> queryBoutique(Map <String,Object> params);
 
   /**
    *   查询精品资讯的数量
    * @param params
    * @return
    */
   public Integer queryBoutiqueCount(Map<String,Object>params);

   /**
    * 删除精品资讯
    * @param information
    * @return
    */
   public boolean deleteBoutique(BoutiqueVo boutique);
  
}
