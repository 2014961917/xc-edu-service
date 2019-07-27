package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-26 15:39
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Api(value = "cms配置管理接口",tags = "cms配置管理接口，提供数据模型的管理，查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据Id查询CMS配置信息")
    public CmsConfig getModel(String id);
}
