package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:59
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class testRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testFindPage(){
        ResponseEntity forEntity =
                restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f",
                        Map.class);
        System.out.println(forEntity);
    }
}
