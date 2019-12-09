package com.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: zhangchao
 * @Date: 2019-08-09 14:17
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket getGriFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database);
    }
}
