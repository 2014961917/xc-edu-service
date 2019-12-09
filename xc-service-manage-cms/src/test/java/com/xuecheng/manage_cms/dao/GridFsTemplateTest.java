package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: zhangchao
 * @Date: 2019-07-12 09:59
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTemplateTest {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void testFindPage() throws IOException {
        //定义File
        File file = new File("F:\\xc_workspace\\xcEduService\\test-freemarker\\src\\main\\resources\\templates\\index_banner.ftl");
        //定义FileInputStream
        FileInputStream inputStream = new FileInputStream(file);

        ObjectId objectId = gridFsTemplate.store(inputStream,"index_banner.ftl");
        System.out.println(objectId);
    }

    @Test
    public void queryFile() throws IOException {
        //根据Id查询到文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5d885fce13732b1af0f3af25")));

        //打开一个下载流
        GridFSDownloadStream gridFSDownloadStream;
        if (gridFSFile != null){
            gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //构建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            //从流中获取数据
            String content = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
            System.out.println(content);
        }
    }

    @Test
    public void testDelFile() throws IOException{
        //根据文件Id删除fs.file和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5d4d2d6e1fbfce3de8ca34b2")));
    }
}
