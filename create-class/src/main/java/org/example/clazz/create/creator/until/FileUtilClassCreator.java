package org.example.clazz.create.creator.until;

import org.example.clazz.create.AbstartClassCreator;

import java.io.File;
import java.io.IOException;

public class FileUtilClassCreator extends AbstartClassCreator {
    @Override
    protected File createJavaFile(String basePath, String className) {
        File file = null;
        try {
            file = createJavaFile(basePath, "FileUtil", "/util/", ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected String getPackage(String basePath) {
        return super.getPackage(basePath, "util");
    }

    @Override
    protected void deflauteEditCode(File file, String url, StringBuffer sb, String className, String page) {
        sb.append("\n\nimport org.springframework.web.multipart.MultipartFile;\n").append(
                "\n").append(
                "import java.io.File;\n").append(
                "import java.io.FileOutputStream;\n").append(
                "import java.io.IOException;\n").append(
                "\n").append(
                "public class FileUtil {\n").append(
                "    /**\n").append(
                "     * 上传文件\n").append(
                "     *\n").append(
                "     * @param file\n").append(
                "     * @param filepath\n").append(
                "     * @return\n").append(
                "     * @throws IOException\n").append(
                "     */\n").append(
                "    public static String uploadFile(MultipartFile file, String filepath) throws IOException {\n").append(
                "        String filename = file.getOriginalFilename();\n").append(
                "        String newname = GeneratorIDUtil.generatorId() + \".\" + filename.substring(filename.lastIndexOf(\".\") + 1);\n").append(
                "        File newFile = new File(filepath);\n").append(
                "        if (!newFile.exists()) {\n").append(
                "            newFile.mkdirs();\n").append(
                "        }\n").append(
                "        FileOutputStream out = new FileOutputStream(newFile + \"/\" + newname);\n").append(
                "        out.write(file.getBytes());\n").append(
                "        out.close();\n").append(
                "        return newname;\n").append(
                "    }\n").append(
                "}");
    }

    @Override
    protected void restEditCode(File file, String url, StringBuffer sb, String className, String page) {
        deflauteEditCode(file, url, sb, className, page);
    }
}
