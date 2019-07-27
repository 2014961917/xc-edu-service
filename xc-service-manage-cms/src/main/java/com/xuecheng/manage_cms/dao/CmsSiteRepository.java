package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:56
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */

//MongoRepository<CmsSite,String> CmsSite，String主键
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
