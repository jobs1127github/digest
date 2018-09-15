package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.dto.InformationDto;

public interface InformationService{
	
   /**
    *  保存资讯
    * @param information
    * @return
    */
   public boolean saveInformation(InformationVo information);
   /**
    * 修改资讯
    * @param information
    * @return
    */
   public boolean updateInformation(InformationVo information);
   /**
    *  查询单个资讯
    * @param informationId
    * @return
    */
   public InformationVo queryInformationById(String informationId);
    /**
     * 查询资讯列表信息
     * @param params
     * @return
     */
   public List<InformationDto> queryInformationInfos(Map <String,Object> params);
 
   /**
    *   查询资讯的数量
    * @param params
    * @return
    */
   public Integer queryInformationCount(Map<String,Object>params);

   /**
    * 删除资讯
    * @param information
    * @return
    */
   public boolean deleteInformation(InformationVo information);
  
}
