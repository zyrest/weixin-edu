package com.outstudio.weixin.common.utils;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * Created by 96428 on 2017/7/17.
 * This in TestWeixin, samson.back.util
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);


    /**
     * 根据文件类型保存到相应的文件夹中，返回文件绝对路径
     * @param request http请求
     * @param file 待保存文件
     * @return 文件绝对路径
     * @throws Exception 抛出异常
     */
    public static String saveUploadFile(HttpServletRequest request, MultipartFile file) throws Exception {
        //todo 修改路径
        String baseDir = request.getSession().getServletContext().getRealPath("") + "/files/";
        String fileName = file.getOriginalFilename();
        String type = null;
        if (! StringUtil.isBlank(fileName)) {
            type = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        String path = baseDir + type;

        String newName = UUID.randomUUID() + fileName;

        File dir = new File(path);
        File savedFile = new File(dir, newName);

        if(! dir.exists()) {
            dir.mkdirs();
        }

        //MultipartFile自带的解析方法
        file.transferTo(savedFile);
        logger.info("savedFile is : " + savedFile.getPath());
        return savedFile.getPath();
    }

    public static boolean isXmlFile(String filePath) {

        return filePath.trim().toLowerCase().endsWith("xml");
    }

    public static boolean ixExcelFile(String filePath) {

        return filePath.trim().toLowerCase().endsWith("xls") || filePath.trim().toLowerCase().endsWith("xlsx");
    }
}
