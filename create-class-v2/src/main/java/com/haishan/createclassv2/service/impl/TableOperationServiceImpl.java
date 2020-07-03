package com.haishan.createclassv2.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.haishan.createclassv2.db.JDBC;
import com.haishan.createclassv2.service.ITableOperationService;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("TableOperationServiceImpl")
public class TableOperationServiceImpl implements ITableOperationService {

	@Override
	public String queryTableOperation(HttpServletRequest request, JSONObject param) {
		Map<String,String> datasource = new HashMap<>();
		datasource.put("database",param.getString("database"));
		datasource.put("driverclass",param.getString("driverclass"));
		datasource.put("url",param.getString("url"));
		datasource.put("username",param.getString("username"));
		datasource.put("password",param.getString("password"));
		List<Map<String,String>> tableInfo =  JDBC.getTable(datasource);

		List<String> tableName = tableInfo.stream().map(one->one.get("table_name").toString())
				.distinct()
				.collect(Collectors.toList());
		List<Map> r = new LinkedList<>();
		for (String one : tableName) {
			List<Map> field = tableInfo.stream()
					.filter(fieldOne->fieldOne.get("table_name").toString().equals(one))
					.collect(Collectors.toList());
			List<Map> key = field.stream()
					.filter(keyOne->keyOne.containsKey("key"))
					.collect(Collectors.toList());
			Map resOne = new HashMap();
			if (key.size()>0) {
				Map keyOne = key.get(0);
				resOne.put("key",keyOne.get("key"));
			}
			Map first = field.get(0);
			resOne.put("table_name",first.get("table_name"));
			resOne.put("TABLE_COMMENT",first.get("TABLE_COMMENT"));
			resOne.put("field",field);

			r.add(resOne);
		}
		JSONObject res = new JSONObject();
		res.put("result",r);
		return res.toJSONString();
	}

}