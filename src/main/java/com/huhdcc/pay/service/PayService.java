package com.huhdcc.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.huhdcc.pay.BaseValue;
import com.huhdcc.pay.constant.PayConstant;
import com.huhdcc.pay.service.intf.IPayService;
import com.huhdcc.pay.util.*;
import com.huhdcc.pay.vo.PayBackVo;
import com.huhdcc.pay.vo.PayOrderVo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.*;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/5
 */
@Service
public class PayService implements IPayService {

    private static Logger logger = LoggerFactory.getLogger(PayService.class);

    @Override
    public Map unifiedOrder(PayOrderVo payOrderVo) {
        //支付宝支付
        if(payOrderVo.getPayType().equals(PayConstant.PayType.ALI_PAY)){
            //扫码支付
            if (payOrderVo.getTradeType().equals(PayConstant.TradeType.QR)) {
                return this.aliQrPay(payOrderVo);
            }
            //app支付
            if (payOrderVo.getTradeType().equals(PayConstant.TradeType.APP)) {
                return this.aliAppPay(payOrderVo);
            }
        }
        //微信支付
        if(payOrderVo.getPayType().equals(PayConstant.PayType.WX_PAY)){
            //扫码支付
            if (payOrderVo.getTradeType().equals(PayConstant.TradeType.QR)) {
                return this.wxQrPay(payOrderVo);
            }
            //app支付
            if (payOrderVo.getTradeType().equals(PayConstant.TradeType.APP)) {
                return this.wxAppPay(payOrderVo);
            }
        }
        //微信小程序支付
        if(payOrderVo.getPayType().equals(PayConstant.PayType.WX_APPLET_PAY)){
            return this.wxAppletPay(payOrderVo);
        }
        return null;
    }

    @Override
    public Map refund(PayOrderVo payOrderVo) {
        //统一生成支付退款操作
        //支付宝退款
        if(payOrderVo.getPayType().equals(PayConstant.PayType.ALI_PAY)){
              return this.aliPayRefund(payOrderVo);
        }
        //微信退款
        if(payOrderVo.getPayType().equals(PayConstant.PayType.WX_APPLET_PAY)
                || payOrderVo.getPayType().equals(PayConstant.PayType.WX_PAY)){
            return this.wxPayRefund(payOrderVo);
        }
        return null;
    }

    @Override
    public Map aliQrPay(PayOrderVo payOrderVo) {
        //创建订单
        AlipayClient alipayClient = new DefaultAlipayClient(BaseValue.ALI_GATEWAY,BaseValue.ALI_PAY_APP_ID,BaseValue.ALI_PAY_PRIVATE_KEY,BaseValue.ALI_FORMAT,BaseValue.ALI_CHARSET,BaseValue.ALI_PAY_PUBLIC_KEY,BaseValue.ALI_SIGN_TYPE);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //构建公共参数 设置回调参数
        request.setNotifyUrl(BaseValue.SYSTEM_NOTIFY_URL);
        //构建业务参数
        HashMap<String,String> map = new HashMap<String,String>(){{
            //out_trade_no 作为整个系统的支付凭证 需要全平台唯一 demo此处使用UUID
            put("out_trade_no",CoreUtil.MD5(payOrderVo.getOrderId()));//之前是使用UUID 测试支付回调修改为orderId
            put("total_amount",payOrderVo.getAmount()+"");
            put("subject",payOrderVo.getSubject());
            put("body",payOrderVo.getBody()!=null?payOrderVo.getBody():payOrderVo.getSubject());
            //store_id 如果是多商户架构 建议为商户ID demo此处使用UUID
            put("store_id",UUID.randomUUID().toString());
            put("terminal_id",payOrderVo.getClientIp());
            //二维码失效时间根据需求自定义
            put("qr_code_timeout_express","90m");
        }};
        request.setBizContent(JSONObject.toJSONString(map));
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (Exception e) {
            logger.info("【支付宝获取二维码失败 {}】",e.getMessage());
        }
        if(response!=null && response.isSuccess()){
            return JSONObject.parseObject(JSONObject.toJSONString(response), Map.class);
        } else {
            logger.info("【支付宝获取二维码失败 {}】",response.toString());
        }
        return null;
    }

    @Override
    public Map aliAppPay(PayOrderVo payOrderVo) {
        /**
         * 扫码支付和APP使用的参数构建不同 请自行选择
         */
        AlipayClient alipayClient = new DefaultAlipayClient(BaseValue.ALI_GATEWAY,BaseValue.ALI_PAY_APP_ID,BaseValue.ALI_PAY_PRIVATE_KEY,BaseValue.ALI_FORMAT,BaseValue.ALI_CHARSET,BaseValue.ALI_PAY_PUBLIC_KEY,BaseValue.ALI_SIGN_TYPE);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(BaseValue.SYSTEM_NOTIFY_URL);
        //构建业务参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payOrderVo.getBody()!=null?payOrderVo.getBody():payOrderVo.getSubject());
        model.setSubject(payOrderVo.getSubject());
        //请保证OutTradeNo值每次保证唯一 demo使用UUID 自行实现
        model.setOutTradeNo(UUID.randomUUID().toString());
        //store_id 如果是多商户架构 建议为商户ID demo此处使用UUID
        model.setStoreId(UUID.randomUUID().toString());
        model.setTotalAmount(payOrderVo.getAmount()+"");
        //支付渠道码
        model.setProductCode(BaseValue.QUICK_MSECURITY_PAY);
        request.setBizModel(model);
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            if(response!=null && response.isSuccess()){
                //ios 安卓只需要 response.getBody() 即可 直接返回 也可自行封装
                String body = response.getBody();
                return new HashMap<String, String>() {{
                    put("data", body);
                }};
            }else{
                logger.info("【支付宝获取APP支付参数失败 {}】",response.toString());
            }
        } catch (AlipayApiException e) {
            logger.info("【支付宝获取APP支付参数失败 {}】",e.getMessage());
        }
        return null;
    }

    @Override
    public Map wxQrPay(PayOrderVo payOrderVo) {
        //金额转换 微信如果是0.01 需要转换为 1
        int prices = (int)(CoreUtil.getDOUBtoDF(payOrderVo.getAmount(),0)*100);
        Map<String,String> map = new HashMap<String,String>(){{
            put("appid",BaseValue.WX_APP_ID);
            put("mch_id",BaseValue.WX_MCH_ID);
            //交易类型trade_type 以下参数详见微信开发文档
            //https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
            put("trade_type",PayConstant.TradeType.NATIVE);
            put("nonce_str",CoreUtil.MD5(BaseValue.WX_MCH_ID.trim()));
            //如果多商户架构 建议使用商户ID
            put("device_info",BaseValue.WX_MCH_ID.trim());
            put("body",payOrderVo.getSubject());
            put("detail",payOrderVo.getBody());
            //支付订单号 全局唯一 用于通信 demo版使用UUID MD5
            put("out_trade_no",CoreUtil.MD5(UUID.randomUUID().toString()));
            //总金额
            put("total_fee",""+prices);
            put("spbill_create_ip",payOrderVo.getClientIp());
            put("product_id",payOrderVo.getOrderId());
            put("notify_url", BaseValue.SYSTEM_NOTIFY_URL);
            put("sign_type",BaseValue.WX_SIGN_TYPE);
        }};
        try {
            map.put("sign", WxPayUtil.generateSignature(map, BaseValue.WX_MCH_KEY));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String xmlString = XMLBeanUtil.map2XmlString(map);
        //发送http请求
        String post = null;
        try {
            post = OkHttpClientUtil.doPost(BaseValue.WX_ORDER_URL, xmlString);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //返回参数转化
        try {
            return XMLBeanUtil.doXMLParse(post);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    public Map wxAppPay(PayOrderVo payOrderVo) {
        try {
            String totalFee = String.valueOf(new BigDecimal((payOrderVo.getAmount())).multiply(new BigDecimal(100)).intValue());
            Map<String, String> map = new HashMap<String, String>() {{
                put("appid",BaseValue.WX_APP_ID);
                put("mch_id",BaseValue.WX_MCH_ID);
                put("nonce_str", CoreUtil.MD5(payOrderVo.getOrderId()));
                //交易类型trade_type 以下参数详见微信开发文档
                //https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
                put("trade_type", PayConstant.TradeType.APP);
                //如果多商户架构 建议使用商户ID
                put("device_info", BaseValue.WX_MCH_ID);
                put("body", payOrderVo.getSubject());
                put("detail", payOrderVo.getBody());
                //支付订单号 全局唯一 用于通信 demo版使用UUID MD5
                put("out_trade_no",CoreUtil.MD5(UUID.randomUUID().toString()));
                //总金额
                put("total_fee",totalFee);
                put("spbill_create_ip", payOrderVo.getClientIp());
                put("notify_url", BaseValue.SYSTEM_NOTIFY_URL);
            }};
            String prestr = XMLBeanUtil.createLinkString(map);
            String mysign = WxPayUtil.sign(prestr,BaseValue.WX_MCH_KEY, "utf-8").toUpperCase();
            map.put("sign", mysign);
            String xmlString = XMLBeanUtil.map2XmlString(map);
            String post = OkHttpClientUtil.doPost(BaseValue.WX_ORDER_URL, xmlString);
            return XMLBeanUtil.doXMLParse(post);
        } catch (Exception e) {
            logger.info("【微信APP支付请求失败 {}】",e.getMessage());
        }
        return null;
    }

    @Override
    public Map wxAppletPay(PayOrderVo payOrderVo) {
        String totalFee = String.valueOf(new BigDecimal((payOrderVo.getAmount())).multiply(new BigDecimal(100)).intValue());
        Map<String, String> map = new HashMap<String, String>() {{
            put("appid",BaseValue.WX_APPLET_APP_ID);
            put("mch_id",BaseValue.WX_APPLET_MCH_ID);
            put("nonce_str", CoreUtil.MD5(payOrderVo.getOrderId()));
            //如果多商户架构 建议使用商户ID
            put("device_info", BaseValue.WX_APPLET_APP_ID);
            put("body", payOrderVo.getSubject());
            put("detail", payOrderVo.getBody());
            put("out_trade_no",CoreUtil.MD5(UUID.randomUUID().toString()));
            //交易金额
            put("total_fee",totalFee);
            put("spbill_create_ip", payOrderVo.getClientIp());
            //交易类型trade_type 以下参数详见微信开发文档
            //https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_2
            put("trade_type", PayConstant.TradeType.JSAPI);
            //该openid为小程序下微信用户的openid 注意公众号和小程序不同
            put("openid", payOrderVo.getOpenId());
            put("notify_url", BaseValue.SYSTEM_NOTIFY_URL);
        }};
        String prestr = XMLBeanUtil.createLinkString(map);
        //微信支付签名
        String mysign = WxPayUtil.sign(prestr, BaseValue.WX_APPLET_MCH_KEY, "utf-8").toUpperCase();
        map.put("sign", mysign);
        logger.info("【微信小程序支付请求参数 map={}】",map);
        String xmlString = XMLBeanUtil.map2XmlString(map);
        String post = null;
        try {
            post = OkHttpClientUtil.doPost(BaseValue.WX_ORDER_URL, xmlString);
        } catch (Exception e) {
            logger.info("【微信小程序支付请求失败 {}】",e.getMessage());
        }
        try {
            return XMLBeanUtil.doXMLParse(post);
        } catch (Exception e) {
            logger.info("【微信小程序支付请求失败 {}】",e.getMessage());
        }
        return null;
    }

    @Override
    public String unifiedNotify(Map params) {
        PayBackVo payBackVo = MyBeanUtil.createBean(params, PayBackVo.class);
        logger.info("【统一支付回调 payBackVo = {}】",payBackVo);
        //支付宝扫码回调
        if (!StringUtils.isEmpty(payBackVo.getOut_trade_no())) {
            //这个可以通过out_trade_no 将之前请求的时候的数据保存到数据同时保存支付方式
            //再次通过out_trade_no获取数据进行不同的业务 这里没有使用到数据库 就写死了支付方式
            //具体业务自行实现
            if(payBackVo.getOut_trade_no().equals(CoreUtil.MD5("1234567891"))) {
                logger.info("【支付宝回调信息 payBackVo={}】", payBackVo);
                return this.aliPayCallback(params);
            }
            if(payBackVo.getOut_trade_no().equals(CoreUtil.MD5("987654321"))) {
                logger.info("【微信回调信息 payBackVo={}】", payBackVo);
                return this.wxPayCallback(params);
            }
        }
        return null;
    }

    @Override
    public String aliPayCallback(Map params) {
        boolean signVerif = false;
        try {
            signVerif = AlipaySignature.rsaCheckV1(params, BaseValue.ALI_PAY_PUBLIC_KEY, BaseValue.ALI_CHARSET, BaseValue.ALI_SIGN_TYPE);
        } catch (AlipayApiException e) {
            logger.info("【支付宝签名验证失败】");
        }
        if (signVerif) {
            //再次验证支付
            /**
              1.支付APPID
              2.支付金额
              3.支付订单号
              ....具体可根据业务检验
             */

            //开启新的线程处理业务
            ThreadManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //业务逻辑
                }
            });
            //验证成功后返回支付宝SUCCESS标识 否则支付宝会多次回调
            return "SUCCESS";
        } else {
            logger.info("【支付宝参数验证失败】");
        }
        return "FALSE";
    }

    @Override
    public String wxPayCallback(Map params) {
        boolean signVerif = false;
        try {
            //签名验证
            signVerif = WxPayUtil.verify(params, BaseValue.WX_MCH_KEY, "UTF-8".toUpperCase());
        } catch (Exception e) {
            logger.info("【微信签名验证失败】");
        }
        if(signVerif){
            //再次验证支付
            /**
             1.支付APPID
             2.支付金额
             3.支付订单号
             ....具体可根据业务检验
             */
            ThreadManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    //业务逻辑
                }
            });
            //验证成功后返回微信SUCCESS标识 否则微信会多次回调
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("return_code", "SUCCESS");
            returnMap.put("return_msg", "OK");
            return XMLBeanUtil.map2XmlString(returnMap);
        }else{
            logger.info("【微信参数验证失败】");
        }
        return "FALSE";
    }


    @Override
    public Map aliPayRefund(PayOrderVo payOrderVo) {
        try{
            Map<String,String> params = new HashMap<String,String>();
            //商户订单号 这个为支付请求我们生成的ID
            params.put("out_trade_no", payOrderVo.getOrderId());
            //支付宝在支付成功后返回的支付凭证ID 由支付宝生成
            params.put("trade_no", payOrderVo.getPayId());
            //退款预生成的流水号 由系统保证唯一
            params.put("out_request_no", CoreUtil.MD5(UUID.randomUUID().toString()));
            //退款金额 demo使用订单金额
            params.put("refund_amount", payOrderVo.getAmount()+"");
            AlipayClient alipayClient = new DefaultAlipayClient(BaseValue.ALI_GATEWAY,BaseValue.ALI_PAY_APP_ID,BaseValue.ALI_PAY_PRIVATE_KEY,BaseValue.ALI_FORMAT,BaseValue.ALI_CHARSET,BaseValue.ALI_PAY_PUBLIC_KEY,BaseValue.ALI_SIGN_TYPE);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent(JSONObject.toJSONString(params));
            AlipayTradeRefundResponse back = alipayClient.execute(request);
            logger.info("【支付宝退款信息 back = {} 】", back);
            String body = back.toString();
            if(back == null || !back.isSuccess()){
                logger.info("【支付宝退款失败  {} 】", back.isSuccess());
            }
            if(back.getCode().equals("10000")){
                logger.info("【支付订单 退款 {} 成功 】",payOrderVo.getAmount());
            }
            if(back.getSubCode()!=null && back.getSubCode().equals("ACQ.SYSTEM_ERROR")){
                logger.info("【支付订单  退款 {} 超时 】",payOrderVo.getAmount());
            }
        }catch(Exception e){
            logger.info("【支付宝退款失败  {} 】", e.getMessage());
        }
        return null;
    }

    @Override
    public Map wxPayRefund(PayOrderVo payOrderVo) {
        try{
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("appid",BaseValue.WX_APPLET_APP_ID);
            dataMap.put("mch_id",BaseValue.WX_APPLET_MCH_ID);
            //自行实现该随机串
            dataMap.put("nonce_str", CoreUtil.MD5(payOrderVo.getOrderId()));
            //由系统生成我们保证唯一值 下单时的参数
            dataMap.put("out_trade_no",CoreUtil.MD5(payOrderVo.getOrderId()));
            //退款单号 由我们保证唯一
            dataMap.put("out_refund_no", CoreUtil.MD5(UUID.randomUUID().toString()));
            //总金额
            dataMap.put("total_fee", String.valueOf(new BigDecimal((payOrderVo.getAmount())).multiply(new BigDecimal(100)).intValue()));
            //退款金额 demo这里使用总金额
            dataMap.put("refund_fee", String.valueOf(new BigDecimal((payOrderVo.getAmount())).multiply(new BigDecimal(100)).intValue()));
            dataMap.put("refund_desc", payOrderVo.getRefundAmount() + "");
            //生成签名
            String sign = WxPayUtil.generateSignature(dataMap, BaseValue.WX_MCH_KEY);
            dataMap.put("sign", sign);
            //map数据转xml
            String xmlString = XMLBeanUtil.map2XmlString(dataMap);
            /**
             * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
             */
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //这里自行实现我是使用数据库配置将证书上传到了服务器可以使用 FileInputStream读取本地文件
            ByteArrayInputStream inputStream = FileUtil.getInputStream(BaseValue.WX_CRET_PATH);
            try {
                //这里写密码..默认是你的MCHID
                keyStore.load(inputStream, BaseValue.WX_MCH_KEY.toCharArray());
            } finally {
                inputStream.close();
            }
            SSLContext sslcontext = SSLContexts.custom()
                    //这里也是写密码的
                    .loadKeyMaterial(keyStore, BaseValue.WX_MCH_ID.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            try {
                HttpPost httpost = new HttpPost(BaseValue.WX_REFUND_URL);
                httpost.setEntity(new StringEntity(xmlString, "UTF-8"));
                CloseableHttpResponse response = httpclient.execute(httpost);
                try {
                    HttpEntity entity = response.getEntity();
                    //接受到返回信息
                    String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                    EntityUtils.consume(entity);
                    logger.info("【微信退款操作返回信息 jsonStr={} 】", jsonStr);
                    return XMLBeanUtil.doXMLParse(jsonStr);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
