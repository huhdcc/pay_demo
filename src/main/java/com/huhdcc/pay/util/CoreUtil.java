package com.huhdcc.pay.util;

import java.security.MessageDigest;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/6
 */
public class CoreUtil {

    /**
     * 判断并转换字符串为数字
     * @param str
     * @param backDefault
     * @return
     */
    public static double getDOUBtoDF(Object str, double backDefault) {
        try {
            return Double.parseDouble(""+str);
        } catch (NumberFormatException qut) {
            // 返回预设值
            return backDefault;
        } catch (Exception qu) {
            // 返回预设值
            return backDefault;
        }
    }

    /**
     * MD5加密
     * @param s
     * @return 默认32位
     */
    public final static String MD5(String s,String charsetName) {// MD5加密
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes(charsetName);
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * MD5加密c
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        return CoreUtil.MD5(s,"UTF-8");
    }
}
