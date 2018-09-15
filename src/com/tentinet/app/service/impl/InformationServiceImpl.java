package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.InformationService;

@Service(value="informationService")
public class InformationServiceImpl implements InformationService{
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	
	
	@Override
	public boolean saveInformation(InformationVo information) {
		boolean result=false;
		try {
			commonHibernateBaseDao.save(information);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateInformation(InformationVo information) {
		boolean result=false;
		try {
			commonHibernateBaseDao.update(information);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public InformationVo queryInformationById(String informationId) {
		InformationVo vo=commonHibernateBaseDao.getEntityByStringId(InformationVo.class, informationId);
		return vo;
	}

	@Override
	public List<InformationDto> queryInformationInfos(Map<String, Object> params) {
		@SuppressWarnings("unchecked")
		List<InformationDto> list= (List<InformationDto>) commonBatiesBaseDao.queryForList("information.queryInformationInfos", params);
		return list;
	}

	@Override
	public Integer queryInformationCount(Map<String, Object> params) {
		System.out.println("访问了数据库");
		return commonBatiesBaseDao.queryForCount("information.queryInformationCount", params);
		
	}

	@Override
	public boolean deleteInformation(InformationVo information) {
		// TODO Auto-generated method stub
		return false;
	}
}