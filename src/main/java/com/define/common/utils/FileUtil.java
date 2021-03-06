package com.define.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.UUID;

public class FileUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean generateImage(String imgStr, String path, String fileName) {
        if (imgStr == null) {
            return false;
        }
        try {
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            path = path + fileName;
            Files.write(Paths.get(path), Base64.getDecoder().decode(imgStr), StandardOpenOption.CREATE);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * 通过图片base64流判断图片等于多少字节
     * image 图片流
     */
    public static Long imageSize(String str) {
        Integer equalIndex = str.indexOf("=");//2.找到等号，把等号也去掉
        if (str.indexOf("=") > 0) {
            str = str.substring(0, equalIndex);
        }
        Integer strLength = str.length();//3.原来的字符流大小，单位为字节
        Integer size = strLength - (strLength / 8) * 2;//4.计算后得到的文件流大小，单位为字节
        return Integer.toUnsignedLong(size);
    }
}
