package com.huhdcc.pay.util;

import com.alibaba.fastjson.JSON;

/**
 * @description:
 * @author: hhdong
 * @createDate: 2019/9/6
 */
public class MyBeanUtil {

    public static <T> T createBean(Object origObj, Class<T> destClazz) {
        String jsonStr = JSON.toJSONString(origObj);
        return JSON.parseObject(jsonStr, destClazz);
    }

}
