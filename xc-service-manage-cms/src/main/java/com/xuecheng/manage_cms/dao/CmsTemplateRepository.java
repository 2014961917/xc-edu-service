package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-20 11:20
 * @classDesc: 功能描述:(CmsTemplateRepository)
 * @Version: 1.0
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
