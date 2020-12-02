package com.haishan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ISqlCreatorDao {

List<Map> querySqlCreator(@Param("param") Map param);}