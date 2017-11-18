package com.outstudio.weixin.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by 96428 on 2017/7/16.
 */
public class NetWorkUtil {

    private static Logger logger = Logger.getLogger(NetWorkUtil.class);

    public static JSONObject doGetUri(String uri) {
        logger.info("do get the " + uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        get.setConfig(requestConfig);

        String result = "";

        try {
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");/** 设置utf-8字符集，否则请求微信数据可能会出现中文乱码 */
                logger.info("got response : " + result);
            } else {
                logger.info("something wrong happened");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            logger.info("the response is not JSON");
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(result);
    }

    public static JSONObject doPostUri(String uri, String params) {
        logger.info("do post the " + uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        post.setConfig(requestConfig);

        String result = "";

        try {
            post.setEntity(new StringEntity(params, "UTF-8"));/** 设置utf-8字符集，否则请求微信数据可能会出现中文乱码 */
            CloseableHttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("got response : " + result);
            } else {
                logger.info("something wrong happened,error code is " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            logger.info("the response is not JSON");
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(result);
    }

    /**
     * 执行文件上传
     *
     * @param url           远程接收文件的地址
     * @param localFilePath 本地文件地址
     * @return
     * @throws IOException
     */
    public static JSONObject uploadFile(String url, String localFilePath, String propertyName) {

        // 建立一个sslcontext，这里我们信任任何的证书。
        SSLContext context = null;
        try {
            context = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] arg0, String arg1)
                                throws CertificateException {
                            // 这一句就是信任任何的证书，当然你也可以去验证微信服务器的真实性
                            return true;
                        }
                    }).build();          // 建立socket工厂
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(factory).build();

        // 把文件转换成流对象FileBody
        File localFile = new File(localFilePath);
        FileBody fileBody = new FileBody(localFile);

        // 以浏览器兼容模式运行，防止文件名乱码。
        HttpEntity reqEntity = null;
        try {
            reqEntity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart(propertyName, fileBody)
                    .addBinaryBody("media", localFile, ContentType.APPLICATION_OCTET_STREAM, localFile.getName())
                    .setCharset(CharsetUtils.get("UTF-8"))
                    .build();

            LoggerUtil.fmtDebug(NetWorkUtil.class, "上传文件体：length，%s type，%s charset，%s", reqEntity.getContentLength(), reqEntity.getContentType(), reqEntity.getContentEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // uploadFile对应服务端类的同名属性<File类型>
        // .addPart("uploadFileName", uploadFileName)
        // uploadFileName对应服务端类的同名属性<String类型>

        String result = "";

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept", "*/*");

            httpPost.setEntity(reqEntity);
            httpResponse = httpClient.execute(httpPost);
            Integer statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                logger.info("got response : " + result);
            } else {
                logger.info("something wrong happened,error code is " + statusCode);
            }
        } catch (Exception e) {
            LoggerUtil.error(NetWorkUtil.class, "发送请求失败");
        } finally {

            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpResponse时出现错误", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    LoggerUtil.error(NetWorkUtil.class, "关闭httpClient时出现错误", e);
                }
            }
        }

        return JSON.parseObject(result);
    }


    /**
     * @desc ：微信上传素材的请求方法
     *
     * @param requestUrl  微信上传临时素材的接口url
     * @param file    要上传的文件
     * @return String  上传成功后，微信服务器返回的消息
     */
    public static JSONObject httpRequest(String requestUrl, File file) {
        StringBuffer buffer = new StringBuffer();

        try{
            //1.建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();


            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();


        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return JSON.parseObject(buffer.toString());
    }

}
