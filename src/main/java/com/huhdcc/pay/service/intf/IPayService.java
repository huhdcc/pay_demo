package com.huhdcc.pay.service.intf;

import com.huhdcc.pay.vo.PayOrderVo;

import java.util.Map;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/5
 */
public interface IPayService {

    /**
     * 统一支付接口
     * @param payOrderVo
     * @return
     */
    Map unifiedOrder(PayOrderVo payOrderVo);

    /**
     * 统一退款接口
     * @param payOrderVo
     * @return
     */
    Map refund(PayOrderVo payOrderVo);

    /**
     * 统一回调接口
     * @param param
     * @return
     */
    String unifiedNotify(Map param);

    /**
     * 支付宝二维码支付
     * @param payOrderVo
     * @return
     */
    Map aliQrPay(PayOrderVo payOrderVo);

    /**
     * 支付宝APP支付
     * @param payOrderVo
     * @return
     */
    Map aliAppPay(PayOrderVo payOrderVo);

    /**
     * 微信二维码支付
     * @param payOrderVo
     * @return
     */
    Map wxQrPay(PayOrderVo payOrderVo);

    /**
     * 微信APP支付
     * @param payOrderVo
     * @return
     */
    Map wxAppPay(PayOrderVo payOrderVo);

    /**
     * 微信小程序支付
     * @param payOrderVo
     * @return
     */
    Map wxAppletPay(PayOrderVo payOrderVo);

    /**
     * 微信支付回调
     * @param params
     * @return
     */
    String wxPayCallback(Map params);

    /**
     * 支付宝支付回调
     * @param params
     * @return
     */
    String aliPayCallback(Map params);

    /**
     * 支付宝退款
     * @param payOrderVo
     * @return
     */
    Map aliPayRefund(PayOrderVo payOrderVo);

    /**
     * 微信退款
     * @param payOrderVo
     * @return
     */
    Map wxPayRefund(PayOrderVo payOrderVo);

}
