package com.huhdcc.pay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/6
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取流
     * @param filePath
     * @return
     */
    public static ByteArrayInputStream getInputStream(String filePath) {
        // 输入流对象
        InputStream in = null;
        // 字节数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            URL url = new URL(filePath);
            URLConnection connection = url.openConnection();
            in = connection.getInputStream();

            int len = -1;
            byte[] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        } catch (MalformedURLException me) {
            logger.error("创建链接失败,message={}", me.getMessage());
        } catch (IOException ie) {
            logger.error("IO操作失败,message={}", ie.getMessage());
        } finally {
            if (in != null) {
                try {
                    // 关闭输入流
                    in.close();
                } catch (IOException e) {
                    logger.error("输入流关闭失败,message={}", e.getMessage());
                }
            }
            if (out != null) {
                try {
                    // 关闭输出流
                    out.close();
                } catch (IOException e) {
                    logger.error("输出流关闭失败,message={}", e.getMessage());
                }
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}