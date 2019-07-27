package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 11:13
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Service
public class SiteService {
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findAllSite() {
        //根据查询条件和页面条件进行查询
        List<CmsSite> all = cmsSiteRepository.findAll();
        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setList(all);//数据列表
        queryResult.setTotal(all.size());//数据总条数
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
