package com.xuecheng.manage_cms.dao;

import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-20 17:14
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGetPageHtml {
    @Autowired
    private PageService pageService;

    @Test
    public void testGetPageHtml(){
        String pageHtml = pageService.getPageHtml("5abefd525b05aa293098fca6");
        System.out.println(pageHtml);
    }
}
