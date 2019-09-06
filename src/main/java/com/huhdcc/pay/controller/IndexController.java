package com.huhdcc.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.huhdcc.pay.service.intf.IPayService;
import com.huhdcc.pay.util.RequestUtil;
import com.huhdcc.pay.util.XMLBeanUtil;
import com.huhdcc.pay.vo.PayOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/5
 */
@RestController
public class IndexController {

    @Autowired
    private IPayService payService;

    @RequestMapping("/index")
    public String index(){
        return "HELLO PAY";
    }

    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    @ResponseBody
    public Object pay() {
        //为方便以及处理回调都采用该方式
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> params = RequestUtil.convertRequestParamsToMap(request);
        return payService.unifiedOrder(JSONObject.parseObject(JSONObject.toJSONString(params), PayOrderVo.class));
    }

    @RequestMapping(value = "/refund",method = RequestMethod.POST)
    @ResponseBody
    public Object refund() {
        //为方便以及处理回调都采用该方式
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> params = RequestUtil.convertRequestParamsToMap(request);
        return payService.refund(JSONObject.parseObject(JSONObject.toJSONString(params), PayOrderVo.class));
    }

    @RequestMapping(value = "/unifiedNotify",method = RequestMethod.POST)
    @ResponseBody
    public String unifiedNotify(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> params = RequestUtil.convertRequestParamsToMap(request);
        if(params==null || params.size()==0){
            String postXml = RequestUtil.getPostStr(request);
            try {
                params = XMLBeanUtil.doXMLParse(postXml);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return payService.unifiedNotify(params);
    }
}
