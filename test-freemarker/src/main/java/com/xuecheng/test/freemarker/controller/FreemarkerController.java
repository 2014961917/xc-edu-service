package com.xuecheng.test.freemarker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-27 17:21
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@RequestMapping("/freemarker")
@Controller//注意使用这个，不能使用RestController
public class FreemarkerController {

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/test1")
    public String freemarker(Map<String, Object> map){
        map.put("name","黑马程序员");
        //返回模板文件名称
        return "test1";
    }

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map){
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "index_banner";
    }
}