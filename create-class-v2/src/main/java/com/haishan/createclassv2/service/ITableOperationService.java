package com.haishan.createclassv2.service;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;

public interface ITableOperationService{

	String queryTableOperation(HttpServletRequest request, JSONObject param);


}