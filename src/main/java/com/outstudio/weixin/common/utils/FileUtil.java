package com.outstudio.weixin.common.utils;

import com.outstudio.weixin.core.config.WebConfig;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 96428 on 2017/7/17.
 * This in TestWeixin, samson.back.util
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);
    private static String fileSavedPath = WebConfig.getFilePath();

    private static String getFilePath() {
        return fileSavedPath;
    }

    /**
     * 根据文件类型保存到相应的文件夹中，返回文件绝对路径
     *
     * @param request http请求
     * @param file    待保存文件
     * @return 文件绝对路径
     * @throws IOException 抛出异常
     */
    public static String saveUploadFile(HttpServletRequest request, MultipartFile file) throws IOException {
        String baseDir = request.getSession().getServletContext().getRealPath("") + "/files/";
        String fileName = file.getOriginalFilename();
        String type = null;
        if (!StringUtil.isBlank(fileName)) {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        String path = baseDir + type;

        String newName = UUID.randomUUID() + fileName;

        File dir = new File(path);
        File savedFile = new File(dir, newName);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        //MultipartFile自带的解析方法
        file.transferTo(savedFile);
        logger.info("savedFile is : " + savedFile.getPath());
        return savedFile.getPath();
    }

    /**
     * 保存上传的文件，返回文件所在的url地址
     *
     * @param request
     * @param file
     * @return 文件保存的url地址
     * @throws IOException
     */
    public static String saveUploadFileAsUrlPath(HttpServletRequest request, MultipartFile file) throws IOException {
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        String fileName = file.getOriginalFilename();
        String type = "";
        if (!StringUtil.isBlank(fileName)) {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        String filePath = getFilePath();
        filePath = filePath.substring(filePath.indexOf(":") + 1);
        filePath += type;
        String newName = UUID.randomUUID() + fileName;

        File dir = new File(filePath);
        File savedFile = new File(dir, newName);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        //MultipartFile自带的解析方法
        file.transferTo(savedFile);
        logger.info("savedFile is : " + savedFile.getPath());

        String url = baseURL + type + "/" + newName;
        logger.info("savedFileURL is: " + url);
        return url;
    }

    /**
     * 获取给定文件的类型
     *
     * @return
     */
    public static String getType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String type = null;
        if (!StringUtil.isBlank(fileName)) {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return type;
    }

    public static boolean isXmlFile(String filePath) {

        return filePath.trim().toLowerCase().endsWith("xml");
    }

    public static boolean ixExcelFile(String filePath) {

        return filePath.trim().toLowerCase().endsWith("xls") || filePath.trim().toLowerCase().endsWith("xlsx");
    }

    /**
     * 根据给定的文件所在的url删除
     *
     * @param url
     */
    public static boolean deleteFileByUrlPath(String url) {
        if (StringUtil.isBlank(url)) {
            return false;
        }
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        url = url.substring(0, url.lastIndexOf("/"));
        String type = url.substring(url.lastIndexOf("/") + 1);

        String filePath = getFilePath();
        filePath += type + "/";
        filePath += fileName;

        logger.info("获取文件所在本地路径：" + filePath);
        File file = new File(filePath);
        return file.delete();
    }
}
