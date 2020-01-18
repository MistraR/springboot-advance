package com.advance.mistra.utils.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:29
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class FileUtil {

    /**
     * 文件上传
     *
     * @param files
     */
    public static List<String> upload(String savePath, MultipartFile... files) throws IOException {
        List<String> fileNames = new ArrayList<>(files.length);
        //判断路径是否存在
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile file : files) {
            if (file != null) {
                String fileStr = file.getOriginalFilename();
                String fileName = fileStr.substring(fileStr.lastIndexOf('\\') + 1);
                File saveFile = new File(savePath + fileName);
                file.transferTo(saveFile);
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }


    public static List<String> uploadList(String savePath, List<MultipartFile> fileList) throws IOException {
        MultipartFile[] multipartFiles = new MultipartFile[fileList.size()];
        fileList.toArray(multipartFiles);
        return upload3(savePath, multipartFiles);
    }

    /**
     * 文件上传
     * 文件名會變化
     *
     * @param files
     */
    public static List<String> renameUpload(String savePath, MultipartFile... files) throws IOException {
        List<String> fileNames = new ArrayList<>(files.length);
        //判断路径是否存在
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile file : files) {
            if (file != null) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + suffix;
                File saveFile = new File(savePath + fileName);
                file.transferTo(saveFile);
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    /**
     * 文件上传
     * 文件名會變化
     * 本地文件名1 | 返回原文件名1
     * 本地文件名2 | 返回原文件名2
     *
     * @param files
     */
    public static Map<String, String> upload2(String savePath, MultipartFile... files) throws IOException {
        Map<String, String> resultMap = new HashMap<>(files.length);
        //判断路径是否存在
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile file : files) {
            if (file != null && file.getSize() > 0) {
                String fileStr = file.getOriginalFilename();
                String oldFileName = fileStr.substring(fileStr.lastIndexOf('\\') + 1);
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + suffix;
                File saveFile = new File(savePath + fileName);
                file.transferTo(saveFile);
                resultMap.put(fileName, oldFileName);
            }
        }
        return resultMap;
    }

    public static List<String> upload3(String savePath, MultipartFile... files) throws IOException {
        List<String> fileNames = new ArrayList<>(files.length);
        //判断路径是否存在
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile file : files) {
            if (file != null) {
                String fileStr = file.getOriginalFilename();
                String fileSuffix = fileStr.substring(fileStr.lastIndexOf('.'));
                String fileName = UUID.randomUUID() + fileSuffix;
                File saveFile = new File(savePath + "/" + fileName);
                file.transferTo(saveFile);
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }
}
