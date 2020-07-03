package org.example.clazz.create.updateconfig;

import java.io.*;

public class UpdatePom {

    public static void update(File file) {
        StringBuffer sb = new StringBuffer();
        String base = "";
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            base = sb.toString();
            base = base.replaceAll("null", "")
                    .replaceAll("</project>", "");
            StringBuffer sb1 = new StringBuffer();
            sb1.append("  <dependencies>\n    <dependency>\n").append(
                    "      <groupId>com.alibaba</groupId>\n").append(
                    "      <artifactId>fastjson</artifactId>\n").append(
                    "      <version>1.2.62</version>\n").append(
                    "    </dependency>\n").append(
                    "    <dependency>\n").append(
                    "      <groupId>mysql</groupId>\n").append(
                    "      <artifactId>mysql-connector-java</artifactId>\n").append(
                    "      <version>8.0.20</version>\n").append(
                    "    </dependency>\n").append(
                    "    <dependency>\n").append(
                    "      <groupId>org.mybatis.spring.boot</groupId>\n").append(
                    "      <artifactId>mybatis-spring-boot-starter</artifactId>\n").append(
                    "      <version>2.1.2</version>\n").append(
                    "    </dependency>").append(
                    "        <dependency>\n" +
                            "      <groupId>com.github.pagehelper</groupId>\n" +
                            "      <artifactId>pagehelper</artifactId>\n" +
                            "      <version>5.1.10</version>\n" +
                            "    </dependency>\n\t</dependencies>\n");
            base = base + sb1.toString() + "\n</project>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (
                FileOutputStream outputStream = new FileOutputStream(file);
        ) {
            outputStream.write(base.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
