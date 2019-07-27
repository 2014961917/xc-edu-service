package com.xuecheng.manage_cms.exception;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.Getter;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-25 17:20
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class CustomException extends RuntimeException {
    @Getter
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        //异常信息为错误代码+异常信息
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode = resultCode;
    }
}
