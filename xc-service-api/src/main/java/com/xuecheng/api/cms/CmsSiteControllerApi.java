package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-11 16:43
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Api(value = "cms站点管理接口",tags = "cms站点管理接口，提供站点的增、删、改、查")
public interface CmsSiteControllerApi {
    //站点查询
    @ApiOperation("站点列表")
    public QueryResponseResult findAllSite();
}
