package org.example.clazz.create.creator;

import org.example.clazz.create.AbstartClassCreator;

import java.io.*;

public class ControllerClassCreator extends AbstartClassCreator {

    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
           file = createJavaFile(basePath,className,"/controller/","Controller.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return super.getPackage(basePath,"controller");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        String p = page.replaceAll("package ","")
                .replaceAll("controller","")
                .replaceAll(";","");
        sb.append("\n\nimport com.alibaba.fastjson.JSONObject;\n" +
                "import " + p + "service.I" + className+"Service;\n" +
                "import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiImplicitParam;\n" +
                "import io.swagger.annotations.ApiOperation;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "\r\n"+
                "import javax.annotation.Resource;\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "\r\n");
        sb.append("@RestController\n" )
                .append("@RequestMapping(\"" );
        if(url==null||"".equals(url)) sb.append("/api/"+className);
        else sb.append(url);
        sb.append("\")\n")
                .append("@CrossOrigin\n");
        sb.append("@Api(value = \"\")\n");
        sb.append("public class " + className+"Controller{\n" );
        sb.append("\r\n");
        sb.append("\t@Resource(name = \""+className+"ServiceImpl\")\n");
        sb.append("\tprivate I"+ className  +"Service service;\n");
        sb.append("\r\n");

        sb.append("\t@PostMapping(\"/query").append(className).append("\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"\",notes = \"\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"分页参数\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");
        sb.append("\tpublic String query")
                .append(className).append("(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.query"+className+"(request,param);\n");
        sb.append("\t}\n");

        sb.append("\r\n");
        sb.append("\t@PostMapping(\"/update").append(className).append("ByCondition\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"根据参数修改\",notes = \"根据参数修改\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"修改参数和条件\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");;
        sb.append("\tpublic String update")
                .append(className).append("ByCondition(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.update"+className+"ByCondition(request,param);\n");
        sb.append("\t}\n");

        sb.append("\r\n");
        sb.append("\t@PostMapping(\"/update").append(className).append("ByKey\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"根据主键修改\",notes = \"根据主键修改\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"修改参数\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");;
        sb.append("\tpublic String update")
                .append(className).append("ByKey(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.update"+className+"ByKey(request,param);\n");
        sb.append("\t}\n");

        sb.append("\r\n");
        sb.append("\t@PostMapping(\"/add").append(className).append("One\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"添加单条记录\",notes = \"添加单条记录\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"新增参数\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");;
        sb.append("\tpublic String add")
                .append(className).append("One(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.add"+className+"One(request,param);\n");
        sb.append("\t}\n");

        sb.append("\r\n");
        sb.append("\t@PostMapping(\"/add").append(className).append("List\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"添加多条记录\",notes = \"添加多条记录\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"添加参数\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");;
        sb.append("\tpublic String add")
                .append(className).append("List(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.add"+className+"List(request,param);\n");
        sb.append("\t}\n");

        sb.append("\r\n");
        sb.append("\t@PostMapping(\"/delete").append(className).append("\")\n")
                .append("\t@ResponseBody\n")
                .append("\t@ApiOperation(value = \"\",notes = \"\")\n")
                .append("\t@ApiImplicitParam(name = \"param\",value = \"删除条件\",paramType = \"JSONObject\",required = true,dataType = \"JSONObject\")\n");
        ;
        sb.append("\tpublic String delete")
                .append(className).append("(HttpServletRequest request,@RequestBody JSONObject param){\n");
        sb.append("\t\treturn service.delete"+className+"(request,param);\n");
        sb.append("\t}\n\n");

        sb.append("}");

    }


}
