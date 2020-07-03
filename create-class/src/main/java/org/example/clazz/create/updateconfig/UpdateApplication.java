package org.example.clazz.create.updateconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateApplication {

    public static void update(File file){
        StringBuffer sb = new StringBuffer();
        String base = file.getPath().replaceAll("/+","/");
        if (base.charAt(0) == '/')
            base = base.substring(1);
       String p = "";
       String[] p1 =  base.split("\\\\");
        for (int i = p1.length-2; i > 0; i--) {
            if ("java".equals(p1[i])){
                break;
            }
            p = p1[i] + "." + p ;
        }


        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        ) {
            String line;
            boolean pageBool = true,clazzBool = true,methodBool = true;
            boolean add = false;
            Pattern page = Pattern.compile("package.*");
            Pattern clazz = Pattern.compile("public class.*");
            Pattern method = Pattern.compile("public static void main.*");
            while (!(line = bufferedReader.readLine()).equals("}")) {
                if (add){
                   sb.append(line + "\n");
                }else if (pageBool) {
                    sb.append(line + "\n");
                    Matcher pageMatcher = page.matcher(line.trim());
                    if (pageMatcher.find()){
                        sb.append("\r\nimport org.mybatis.spring.annotation.MapperScan;\n" +
                                "import org.springframework.context.ConfigurableApplicationContext;\n");
                        pageBool=false;
                    }

                }else if (clazzBool){

                    Matcher clazzMatcher = clazz.matcher(line.trim());
                    if (clazzMatcher.find()){
                        sb.append("\n@MapperScan(basePackages = \"" + p + "dao\")\n");
                        clazzBool = false;
                    }
                    sb.append(line + "\n");

                }else if (methodBool){

                    Matcher methodMatcher = method.matcher(line.trim());
                    boolean f = methodMatcher.find();
                    if (f){
                        sb.append("\n\tpublic static ConfigurableApplicationContext CONTEXT;\n");
                        methodBool = false;
                        add = true;
                    }
                    sb.append(line + "\n");
                    if (f){
                        sb.append("\t\tCONTEXT = ");
                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        String text = sb.toString()+"\n}";
        try(   FileOutputStream outputStream = new FileOutputStream(file);){
            outputStream.write(text.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
