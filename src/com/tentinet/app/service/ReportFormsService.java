package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.dto.DigestGoldDto;

public interface ReportFormsService{
	
     /**
     * 查询金币发放记录列表信息
     * @param params
     * @return
     */
   public List<DigestGoldDto> queryGoldSendInfos(Map <String,Object> params);
   public Integer queryGoldSendCount(Map <String,Object> params);

 

}
