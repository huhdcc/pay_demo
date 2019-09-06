package com.huhdcc.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/5
 */
public class BaseValue {

    /**
     * 支付宝APPID
     */
    public static String ALI_PAY_APP_ID = "********************************";

    /**
     * 支付宝请求私钥
     */
    public static String ALI_PAY_PRIVATE_KEY = "********************************";
    /**
     * 支付宝请求公钥
     */
    public static String ALI_PAY_PUBLIC_KEY = "********************************";
    /**
     * 支付宝FORMAT
     */
    public static String ALI_FORMAT = "json";

    /**
     * 支付宝字符集
     */
    public static String ALI_CHARSET = "UTF-8";

    /**
     * 支付宝签名类型
     */
    public static String ALI_SIGN_TYPE = "RSA";

    /**
     * 支付宝网关中心
     */
    public static String ALI_GATEWAY = "https://openapi.alipay.com/gateway.do";

    /**
     * 系统支付回调地址
     */
    public static String SYSTEM_NOTIFY_URL = "********************************";

    /**
     * 支付宝支付渠道码
     */
    public static final String QUICK_MSECURITY_PAY = "QUICK_MSECURITY_PAY";

    /**
     * 微信支付APPID
     */
    public static String WX_APP_ID = "********************************";

    /**
     * 微信支付私钥ID
     */
    public static String WX_MCH_ID = "********************************";

    /**
     * 微信支付私钥
     */
    public static String WX_MCH_KEY = "********************************";

    /**
     * 微信支付加密方式
     */
    public static String WX_SIGN_TYPE = "MD5";

    /**
     * 微信支付证书地址
     */
    public static String WX_CRET_PATH = "********************************";

    /**
     * 微信统一下单
     */
    public static final String WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信小程序支付APPID
     */
    public static String WX_APPLET_APP_ID = "********************************";

    /**
     * 微信小程序支付私钥ID
     */
    public static String WX_APPLET_MCH_ID = "********************************";

    /**
     * 微信小程序支付私钥
     */
    public static String WX_APPLET_MCH_KEY = "********************************";

    /**
     * 微信小程序支付加密方式
     */
    public static String WX_APPLET_SIGN_TYPE = "MD5";

    /**
     * 微信退款地址
     */
    public static final String WX_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";


}
