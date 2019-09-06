package com.huhdcc.pay.constant;

/**
 * @description:支付常量
 * @author: hhdong
 * @createDate: 2019/9/5
 */
public class PayConstant {

    /**
     * 第三方类型
     */
    public interface PayType{

        /**
         * 支付宝
         */
        String ALI_PAY = "ALI_PAY";

        /**
         * 微信
         */
        String WX_PAY = "WX_PAY";

        /**
         * 微信小程序
         */
        String WX_APPLET_PAY = "WX_APPLET_PAY";

    }

    /**
     * 支付类型
     */
    public interface TradeType{

        String APP = "APP";

        String JSAPI = "JSAPI";

        String NATIVE = "NATIVE";

        String QR = "QR";
    }
}
