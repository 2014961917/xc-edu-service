package com.xuecheng.manage_cms.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-25 17:22
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class ExceptionCast {
    //使用此静态方法去抛出自定义异常
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
