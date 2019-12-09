package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:40
 * @classDesc: cms页面管理
 * @Version: 1.0
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController  implements CmsPageControllerApi {

    @Autowired
    PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size")int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @Override
    @GetMapping("/findByPageId/{pageId}")
    public CmsPageResult findByPageId(@PathVariable("pageId") String pageId) {
        return pageService.getById(pageId);
    }

    @Override
    @PutMapping("/edit/{pageId}")
    public CmsPageResult edit(@PathVariable("pageId") String pageId,@RequestBody CmsPage cmsPage) {
        return pageService.edit(pageId,cmsPage);
    }

    @Override
    @DeleteMapping("/delete/{pageId}")
    public ResponseResult del(@PathVariable("pageId") String pageId) {
        return pageService.del(pageId);
    }
}
