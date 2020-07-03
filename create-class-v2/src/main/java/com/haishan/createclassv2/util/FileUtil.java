package com.haishan.createclassv2.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    /**
     * 上传文件
     *
     * @param file
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, String filepath) throws IOException {
        String filename = file.getOriginalFilename();
        String newname = GeneratorIDUtil.generatorId() + "." + filename.substring(filename.lastIndexOf(".") + 1);
        File newFile = new File(filepath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(newFile + "/" + newname);
        out.write(file.getBytes());
        out.close();
        return newname;
    }
}