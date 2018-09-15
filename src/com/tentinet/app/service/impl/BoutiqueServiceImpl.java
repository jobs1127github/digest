package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.BoutiqueVo;
import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.service.BoutiqueService;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;

@Service(value="boutiqueService")
public class BoutiqueServiceImpl implements BoutiqueService{
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	
	
	@Override
	public boolean saveBoutique(BoutiqueVo boutique) {
		
		boolean result=false;
		try {
			commonHibernateBaseDao.save(boutique);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateBoutique(BoutiqueVo boutique) {
		boolean result=false;
		try {
			commonHibernateBaseDao.update(boutique);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BoutiqueVo queryBoutiqueById(String informationId) {
		BoutiqueVo vo=commonHibernateBaseDao.getEntityByStringId(BoutiqueVo.class, informationId);
		return vo;
	}

	@Override
	public List<BoutiqueVo> queryBoutique(Map<String, Object> params) {
		List<BoutiqueVo> list= (List<BoutiqueVo>) commonBatiesBaseDao.queryForList("information.boutiqueInfos", params);
		return list;
	}

	@Override
	public Integer queryBoutiqueCount(Map<String, Object> params) {
		return commonBatiesBaseDao.queryForCount("information.boutiqueInfosCount", params);
		
	}

	@Override
	public boolean deleteBoutique(BoutiqueVo boutique) {
		// TODO Auto-generated method stub
		return false;
	}

}
