package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.exception.ExceptionCast;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 11:13
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Service
public class PageService {
    private final
    CmsPageRepository cmsPageRepository;
    private final
    RestTemplate restTemplate;
    private final
    CmsTemplateRepository cmsTemplateRepository;
    private final
    GridFsTemplate gridFsTemplate;
    private final
    GridFSBucket gridFSBucket;

    @Autowired
    public PageService(CmsPageRepository cmsPageRepository, RestTemplate restTemplate, CmsTemplateRepository cmsTemplateRepository, GridFsTemplate gridFsTemplate, GridFSBucket gridFSBucket) {
        this.cmsPageRepository = cmsPageRepository;
        this.restTemplate = restTemplate;
        this.cmsTemplateRepository = cmsTemplateRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
    }

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
    public CmsPageResult getById(String pageId){
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        //查询不到返回空
        return optionalCmsPage.map(cmsPage -> new CmsPageResult(CommonCode.SUCCESS, cmsPage)).orElseGet(() -> new CmsPageResult(CommonCode.FAIL, null));
    }

    //更新页面信息
    public CmsPageResult edit(String pageId, CmsPage cmsPage) {
        //根据Id从数据库中查询页面信息
        CmsPageResult cmsPageResult = this.getById(pageId);
        CmsPage page = null;
        if (cmsPageResult.isSuccess()){
            page = cmsPageResult.getCmsPage();
        }
        if (page != null){
            //准备更新数据
            //设置要更改的数据
            //更新模板id
            page.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            page.setPageAliase(cmsPage.getPageAliase());
            //更新页面别名
            page.setSiteId(cmsPage.getSiteId());
            //更新数据Url
            page.setDataUrl(cmsPage.getDataUrl());
            //更新物理路径
            page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新页面名称
            page.setPageName(cmsPage.getPageName());
            //更新访问路径
            page.setPageWebPath(cmsPage.getPageWebPath());

            //提交修改
            CmsPage save = cmsPageRepository.save(page);
            //成功
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult del(String pageId) {
        CmsPageResult cmsPageResult = this.getById(pageId);
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

    //页面静态化
    public String getPageHtml(String pageId){
        //获取页面模型数据
        Map model = this.getModelByPageId(pageId);
        if (model == null){
            //获取页面模型数据为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String templateContent = this.getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(templateContent)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //执行页面静态化
        String html = generateHtml(templateContent,model);
        if (StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    private String generateHtml(String template, Map model) {
        try{
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template",template);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template1,model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTemplateByPageId(String pageId) {
        //查询页面信息
        CmsPageResult cmsPageResult = this.getById(pageId);
        CmsPage cmsPage = null;
        if (cmsPageResult.isSuccess()){
            cmsPage = cmsPageResult.getCmsPage();
        }
        if (cmsPage == null){
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_PAGENOTEXISTS);
        }
        //页面模板
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)){
            //页面模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //模板文件Id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //取出模板文件内容
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            if (gridFSFile == null){
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFSResource
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            try {
                return IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取页面模型数据
    private Map getModelByPageId(String pageId){
        //查询页面信息
        CmsPageResult cmsPageResult = this.getById(pageId);
        CmsPage cmsPage = null;
        if (cmsPageResult.isSuccess()){
            cmsPage = cmsPageResult.getCmsPage();
        }
        if (cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_PAGENOTEXISTS);
        }
        //取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        return entity.getBody();
    }
}
