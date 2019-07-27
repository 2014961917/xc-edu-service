package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.exception.ExceptionCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 11:13
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    //查询列表
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("siteId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pageId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("TemplateId", ExampleMatcher.GenericPropertyMatchers.exact());
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //页面Id
        if (StringUtils.isNotEmpty(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }
        //页面名称
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //模板Id
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //分页参数
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        page = page - 1;
        //条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        //页面条件
        Pageable pageable = PageRequest.of(page, size);
        //根据查询条件和页面条件进行查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总条数
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    //新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        //校验数据唯一性
        //根据页面名称，站点Id,页面webPath去查询集合，如果查询不到才可以添加
        CmsPage byPageNameAndSiteIdAndPageWebPath = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(
                cmsPage.getPageName(),
                cmsPage.getSiteId(),
                cmsPage.getPageWebPath()
        );

        if (byPageNameAndSiteIdAndPageWebPath != null) {
            //添加失败
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        }
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);


    }

    //根据Id查询页面
    public CmsPageResult findByPageId(String pageId){
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        //查询不到返回空
        return optionalCmsPage.map(cmsPage -> new CmsPageResult(CommonCode.SUCCESS, cmsPage)).orElseGet(() -> new CmsPageResult(CommonCode.FAIL, null));
    }

    //更新页面信息
    public CmsPageResult edit(String pageId, CmsPage cmsPage) {
        CmsPageResult cmsPageResult = this.findByPageId(pageId);
        CmsPage page = null;
        if (cmsPageResult.isSuccess()){
            page = cmsPageResult.getCmsPage();
        }
        if (page != null){
            page.setPageAliase(cmsPage.getPageAliase());
            page.setSiteId(cmsPage.getSiteId());
            page.setDataUrl(cmsPage.getDataUrl());
            page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            page.setPageName(cmsPage.getPageName());
            page.setPageWebPath(cmsPage.getPageWebPath());
            CmsPage save = cmsPageRepository.save(page);
            //成功
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult del(String pageId) {
        CmsPageResult cmsPageResult = this.findByPageId(pageId);
        CmsPage cmsPage = null;
        if (cmsPageResult.isSuccess()){
            cmsPage = cmsPageResult.getCmsPage();
        }
        if (cmsPage != null){
            cmsPageRepository.delete(cmsPage);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
