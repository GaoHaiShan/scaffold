package com.haishan.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneratorIDUtil {
    public static volatile int idnum = 1;

    /*
     * 生成常用ID编码
     */
    public synchronized static String generatorId() {
        if (idnum >= 999) {
            idnum = 1;
        }
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyMMddHHmmss");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(4);
        nf.setMinimumIntegerDigits(4);
        String datestr = format.format(new Date());
        return (datestr + nf.format(idnum++));
    }

    public static String getNowDateStr() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }
}