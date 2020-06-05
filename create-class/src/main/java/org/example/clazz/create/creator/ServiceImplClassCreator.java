package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class ServiceImplClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath,className,"/service/impl/","ServiceImpl.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return getPackage(basePath,"service.impl");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        String p = page.replaceAll("package ","")
                .replaceAll("service\\.impl","")
                .replaceAll(";","");

        if(getThisClassDefinition().isHashDubbo())
            sb.append("\n\nimport com.alibaba.dubbo.config.annotation.Service;\n");

        sb.append("import com.alibaba.fastjson.JSONObject;\n");
        sb.append("import com.github.pagehelper.PageHelper;\n");

        if (getThisClassDefinition().isOpenTransaction())
           sb.append( "import org.springframework.transaction.annotation.Propagation;\n" ).append(
                    "import org.springframework.transaction.annotation.Transactional;\n");

        if(getThisClassDefinition().isHasRabbitMQ())
            sb.append("import org.springframework.amqp.rabbit.core.RabbitTemplate;\n" );

        if (getThisClassDefinition().isHasRedis())
            sb.append("import org.springframework.data.redis.core.RedisTemplate;\n");

        sb.append("import " + p + "dao.I"+className+"Dao;\n" ).append(
                "import " + p + "service.I"+className+"Service;\n" ).append(
                "\n" ).append(
                "import javax.annotation.Resource;\n" ).append(
                "import "+page.replaceAll("package ","")
                        .replaceAll("service.impl;","util")
                        +".GeneratorIDUtil;\n").append(
                "import javax.servlet.http.HttpServletRequest;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Map;\n\n");

        if (getThisClassDefinition().isHashDubbo()) sb.append("@Service\n");

        sb.append("@org.springframework.stereotype.Service(\""+className+"ServiceImpl\")\n");

        if (getThisClassDefinition().isOpenTransaction()) sb.append("@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)\n");

        sb.append("public class "+className+"ServiceImpl implements I"+className+"Service {\n\n");

        sb.append("\t@Resource\n" ).append(
                "\tprivate I"+className+"Dao dao;\n\n" ).append(
                "\n");

        if(getThisClassDefinition().isHasRedis()) {
            sb.append("\t@Resource\n" ).append(
                    "\tprivate RedisTemplate redisTemplate;\n" + "\n\n");
        }

        if(getThisClassDefinition().isHasRabbitMQ()){
         sb.append("\t@Resource\n" ).append(
                "\tprivate RabbitTemplate rabbitTemplate;\n\n");
        }

        sb.append("\t@Override\n" ).append(
                "\tpublic String query"+className+"(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tPageHelper.startPage(param.getInteger(\"current\"), param.getInteger(\"pagesize\"));\n").append(
                "\t\tJSONObject res = new JSONObject();\n" ).append(
                "\t\tres.put(\"result\",dao.query"+className+"(param));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");


        sb.append("\t@Override\n" ).append(
                "\tpublic String update"+className+"ByCondition(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tJSONObject res = new JSONObject();\n" ).append(
                "\t\tparam.put(\"updatetime\", GeneratorIDUtil.getNowDateStr());\n" ).append(
                "\t\tres.put(\"result\",dao.update").append(className+"ByCondition(\""+getThisClassDefinition().getTableName()+"\",(Map)param.get(\"condition\"),(Map)param.get(\"value\")));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");

        sb.append("\t@Override\n" ).append(
                "\tpublic String update"+className+"ByKey(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tJSONObject res = new JSONObject();\n" ).append(
                "\t\tparam.put(\"updatetime\", GeneratorIDUtil.getNowDateStr());\n" ).append(
                "\t\tres.put(\"result\",dao.update").append(className+"ByKey(\""+getThisClassDefinition().getTableName()+"\",(Map)param.get(\"value\")));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");


        sb.append("\t@Override\n" ).append(
                "\tpublic String add"+className+"One(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tJSONObject res = new JSONObject();\n" )
                .append("\t\tparam.put(\"createtime\", GeneratorIDUtil.getNowDateStr());\n")
                .append("\t\tparam.put(\""+getThisClassDefinition().getPrimaryKey()+"\", GeneratorIDUtil.generatorId());\n")
                .append("\t\tres.put(\"result\",dao.add"+className+"One(\""+getThisClassDefinition().getTableName()+"\",param));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");

        sb.append("\t@Override\n" ).append(
                "\tpublic String add"+className+"List(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tJSONObject res = new JSONObject();\n" ).append(
                "\t\tList<Map> list = (List<Map>)param.get(\"insertList\");\n").append(
                "\t\tfor(Map one : list){\n").append(
                "\t\t\tone.put(\""+getThisClassDefinition().getPrimaryKey()+"\", GeneratorIDUtil.generatorId());\n" ).append(
                "\t\t\tone.put(\"createtime\", GeneratorIDUtil.getNowDateStr());\n" ).append(
                "\t\t}\n").append(
                "\t\tres.put(\"result\",dao.add"+className+"List(\""+getThisClassDefinition().getTableName()+"\",list));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");

        sb.append("\t@Override\n" ).append(
                "\tpublic String delete"+className+"(HttpServletRequest request, JSONObject param) {\n" ).append(
                "\t\tJSONObject res = new JSONObject();\n" ).append(
                "\t\tres.put(\"result\",dao.delete"+className+"(param));\n" ).append(
                "\t\treturn res.toJSONString();\n" ).append(
                "\t}\n\n");
        sb.append("}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file,url,sb,className,page);
    }
}
