package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:59
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSiteRepositoryTest {

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Test
    public void testFindAll(){
        List<CmsSite> all = cmsSiteRepository.findAll();
        System.out.println(all);
    }


    @Test
    public void testFindPage(){
        List<CmsSite> all = cmsSiteRepository.findAll();
        System.out.println(all);
    }
}
