package com.haishan.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
    public static String toJSON(Object jsonText) {
        return JSON.toJSONString(jsonText,
                SerializerFeature.WriteDateUseDateFormat);
    }}