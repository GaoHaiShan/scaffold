package com.haishan.controller;

import com.alibaba.fastjson.JSONObject;
import com.haishan.service.ISqlCreatorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@CrossOrigin
public class SqlCreatorController{

	@Resource(name = "SqlCreatorServiceImpl")
	private ISqlCreatorService service;

	@PostMapping("/querySqlCreator")
	@ResponseBody
	public String querySqlCreator(HttpServletRequest request,@RequestBody JSONObject param){
		return service.querySqlCreator(request,param);
	}

	@PostMapping("/updateSqlCreatorByCondition")
	@ResponseBody
	public String updateSqlCreatorByCondition(HttpServletRequest request,@RequestBody JSONObject param){
		return service.updateSqlCreatorByCondition(request,param);
	}

	@PostMapping("/updateSqlCreatorByKey")
	@ResponseBody
	public String updateSqlCreatorByKey(HttpServletRequest request,@RequestBody JSONObject param){
		return service.updateSqlCreatorByKey(request,param);
	}

	@PostMapping("/addSqlCreatorOne")
	@ResponseBody
	public String addSqlCreatorOne(HttpServletRequest request,@RequestBody JSONObject param){
		return service.addSqlCreatorOne(request,param);
	}

	@PostMapping("/addSqlCreatorList")
	@ResponseBody
	public String addSqlCreatorList(HttpServletRequest request,@RequestBody JSONObject param){
		return service.addSqlCreatorList(request,param);
	}
}