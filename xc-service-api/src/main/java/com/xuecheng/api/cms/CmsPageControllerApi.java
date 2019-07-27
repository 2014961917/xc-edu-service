package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-11 16:43
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Api(value = "cms页面管理接口", tags = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页面大小", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //新增页面
    @ApiOperation("新增页面")
    CmsPageResult add(CmsPage cmsPage);

    //根据pageId查询页面
    @ApiOperation("根据pageId查询页面")
    CmsPageResult findByPageId(String pageId);

    //更新页面
    @ApiOperation("更新页面")
    CmsPageResult edit(String pageId, CmsPage cmsPage);

    //删除页面
    @ApiOperation("删除页面")
    ResponseResult del(String pageId);
}
