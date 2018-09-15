package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.dto.DigestGoldDto;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.ReportFormsService;
@Service(value="reportFormsService")
public class ReportFormsServiceImpl implements ReportFormsService {

	
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	
    /**
     * 查询金币发放记录列表信息
     * @param params
     * @return
     */

	@Override
	public List<DigestGoldDto> queryGoldSendInfos(Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<DigestGoldDto> dto= (List<DigestGoldDto>) commonBatiesBaseDao.queryForList("report.queryGoldSendInfos", params);
		return dto;
	}

	@Override
	public Integer queryGoldSendCount(Map<String, Object> params) {
		return commonBatiesBaseDao.queryForCount("report.queryGoldSendCount", params);
	}

}
