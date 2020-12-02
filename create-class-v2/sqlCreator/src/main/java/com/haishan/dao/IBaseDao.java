package com.haishan.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBaseDao {

	@Update("")	int updateByCondition(@Param("tableName") String tableName,@Param("condition") Map condition,@Param("value") Map value);

	@Update("")	int updateByKey(@Param("tableName") String tableName,@Param("value") Map value);

	@Insert("")	int insert(@Param("tableName") String tableName,@Param("value") Map value);

	@Insert("")	int insertList(@Param("tableName") String tableName,@Param("value") List<Map> value);

	@Delete("")	int deleteByKey(@Param("tableName") String tableName,@Param("key") String key);

	@Delete("")	int deleteByCondition(@Param("tableName") String tableName,@Param("condition") String key);


}