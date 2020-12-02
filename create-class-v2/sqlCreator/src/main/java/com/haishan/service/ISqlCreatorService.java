package com.haishan.service;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;

public interface ISqlCreatorService{

	String querySqlCreator(HttpServletRequest request, JSONObject param);

	String updateSqlCreatorByCondition(HttpServletRequest request, JSONObject param);

	String updateSqlCreatorByKey(HttpServletRequest request, JSONObject param);

	String addSqlCreatorOne(HttpServletRequest request, JSONObject param);

	String addSqlCreatorList(HttpServletRequest request, JSONObject param);

}