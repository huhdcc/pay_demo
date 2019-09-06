package com.huhdcc.pay.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/6
 */
public class WxPayUtil {

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k).trim().length() > 0) {
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        return CoreUtil.MD5(sb.toString()).toUpperCase();

    }

    public static String sign(String text, String key, String input_charset) {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 签名验证
     * @param params
     * @param key
     * @param chartset
     * @return
     */
    public static boolean verify(Map params, String key, String chartset) {
        SortedMap<String,String> payInfo = new TreeMap<>(params);
        String sign = payInfo.get("sign");
        String prestr = XMLBeanUtil.createLinkString(WxPayUtil.paraFilter(payInfo));
        String mysign = WxPayUtil.sign(prestr, key, chartset.toUpperCase());
        if(mysign.toLowerCase().equals(sign.toLowerCase())){
            return true;
        }
        return false;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

}
