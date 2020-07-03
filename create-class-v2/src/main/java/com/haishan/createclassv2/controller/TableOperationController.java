package com.haishan.createclassv2.controller;

import com.alibaba.fastjson.JSONObject;
import com.haishan.createclassv2.service.ITableOperationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/class/create")
@CrossOrigin
public class TableOperationController{

	@Resource(name = "TableOperationServiceImpl")
	private ITableOperationService service;

	@PostMapping("/queryTableAndField")
	@ResponseBody
	public String queryTableOperation(HttpServletRequest request,@RequestBody JSONObject param){
		return service.queryTableOperation(request,param);
	}

	@RequestMapping("/index")
	public String queryTableOperation(){
		return "index";
	}
}