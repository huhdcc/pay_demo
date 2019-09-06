package com.huhdcc.pay.vo;

import java.io.Serializable;

/**
 * @description:支付订单
 * @author: hhdong
 * @createDate: 2019/7/30
 */
public class PayOrderVo implements Serializable {

    /**
     * 内部订单ID
     */
    private  String orderId;

    /**
     * 第三方支付ID（用于后续操作）
     */
    private  String payId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 支付渠道(微信 支付宝 小程序)
     */
    private String payType;

    /**
     * 支付方式(APP 扫码 JSAPI)
     */
    private String tradeType;

    /**
     * 交易类型(消费,充值...)
     */
    private String trxType;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单详情
     */
    private String body;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 服务端IP
     */
    private String serverIp;

    /**
     * 订单金额
     */
    private double amount;

    /**
     * 退款金额
     */
    private double refundAmount;

    /**
     * 备注
     */
    private String remark;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
