package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-26 15:45
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Service
public class CmsConfigService {
    @Autowired
    CmsConfigRepository cmsConfigRepository;

    //根据Id查询配置管理信息
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> cmsConfigOptional = cmsConfigRepository.findById(id);
        return cmsConfigOptional.orElse(null);
    }
}
