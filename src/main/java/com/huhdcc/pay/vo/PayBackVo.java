package com.huhdcc.pay.vo;

import java.io.Serializable;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/6
 */
public class PayBackVo implements Serializable {

    /*------支付宝参数-----*/

    private String gmt_create;

    private String charset;

    private String seller_email;

    private String notify_time;

    private String suject;

    private String body;

    private String buyer_id;

    private String version;

    private String notify_id;

    private String notify_type;

    private String total_amount;

    private String trade_status;

    private String trade_no;

    private String auth_app_id;

    private String buyer_login_id;

    private String app_id;

    private String sign_type;

    private String seller_id;

    /*---------微信支付回调参数-------------*/

    private String transaction_id;

    private String nonce_str;

    private String bank_type;

    private String openid;

    private String fee_type;

    private String mch_id;

    private String cash_fee;

    private String device_info;

    private String appid;

    private String total_fee;

    private String trade_type;

    private String result_code;

    private String is_subscribe;

    private String return_code;

    /*--------------支付宝微信回调公用参数----------------*/

    private String sign;

    private String out_trade_no;

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getSuject() {
        return suject;
    }

    public void setSuject(String suject) {
        this.suject = suject;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getAuth_app_id() {
        return auth_app_id;
    }

    public void setAuth_app_id(String auth_app_id) {
        this.auth_app_id = auth_app_id;
    }

    public String getBuyer_login_id() {
        return buyer_login_id;
    }

    public void setBuyer_login_id(String buyer_login_id) {
        this.buyer_login_id = buyer_login_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    @Override
    public String toString() {
        return "PayBackVo{" +
                "gmt_create='" + gmt_create + '\'' +
                ", charset='" + charset + '\'' +
                ", seller_email='" + seller_email + '\'' +
                ", notify_time='" + notify_time + '\'' +
                ", suject='" + suject + '\'' +
                ", body='" + body + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                ", version='" + version + '\'' +
                ", notify_id='" + notify_id + '\'' +
                ", notify_type='" + notify_type + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", trade_status='" + trade_status + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", auth_app_id='" + auth_app_id + '\'' +
                ", buyer_login_id='" + buyer_login_id + '\'' +
                ", app_id='" + app_id + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", bank_type='" + bank_type + '\'' +
                ", openid='" + openid + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", cash_fee='" + cash_fee + '\'' +
                ", device_info='" + device_info + '\'' +
                ", appid='" + appid + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", result_code='" + result_code + '\'' +
                ", is_subscribe='" + is_subscribe + '\'' +
                ", return_code='" + return_code + '\'' +
                ", sign='" + sign + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                '}';
    }
}
