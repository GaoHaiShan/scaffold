package com.haishan.service.impl;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.haishan.dao.IBaseDao;
import com.haishan.dao.ISqlCreatorDao;
import com.haishan.service.ISqlCreatorService;

import javax.annotation.Resource;
import com.haishan.util.GeneratorIDUtil;
import com.haishan.util.JsonUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service("SqlCreatorServiceImpl")
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class SqlCreatorServiceImpl implements ISqlCreatorService {

	@Resource
	private IBaseDao baseDao;


	@Resource
	private ISqlCreatorDao dao;


	@Override
	public String querySqlCreator(HttpServletRequest request, JSONObject param) {
		return null;
	}

	@Override
	public String updateSqlCreatorByCondition(HttpServletRequest request, JSONObject param) {
		JSONObject res = new JSONObject();
		res.put("result",baseDao.updateByCondition("test",(Map)param.get("condition"),(Map)param.get("value")));
		return res.toJSONString();
	}

	@Override
	public String updateSqlCreatorByKey(HttpServletRequest request, JSONObject param) {
		JSONObject res = new JSONObject();
		res.put("result",baseDao.updateByKey("test",(Map)param.get("value")));
		return res.toJSONString();
	}

	@Override
	public String addSqlCreatorOne(HttpServletRequest request, JSONObject param) {
		JSONObject res = new JSONObject();
		param.put("test", GeneratorIDUtil.generatorId());
		res.put("result",baseDao.insert("test",param));
		return res.toJSONString();
	}

	@Override
	public String addSqlCreatorList(HttpServletRequest request, JSONObject param) {
		JSONObject res = new JSONObject();
		List<Map> list = (List<Map>)param.get("insertList");
		for(Map one : list){
			one.put("test", GeneratorIDUtil.generatorId());
		}
		res.put("result",baseDao.insertList("test",list));
		return res.toJSONString();
	}

}