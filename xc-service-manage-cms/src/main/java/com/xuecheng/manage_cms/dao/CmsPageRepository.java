package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:56
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */

//MongoRepository<CmsPage,String> CmsPage模型类，String主键
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据页面名称，站点Id,页面webPath去查询集合
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
