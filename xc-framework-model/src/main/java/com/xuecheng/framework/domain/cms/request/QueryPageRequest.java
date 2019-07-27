package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-11 16:39
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Data
public class QueryPageRequest {
    //接受页面查询条件
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //页面id
    @ApiModelProperty("页面id")
    private String pageId;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //别名
    @ApiModelProperty("别名")
    private String pageAliase;
    //模板id
    @ApiModelProperty("模板id")
    private String templateId;
}
