package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-24 13:59
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class Producer02 {
    private static final String Queue = "queue";

    public static void main(String[] args) throws Exception {

        Connection connection = null;
        Channel channel = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        connection = factory.newConnection();
        channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(Queue,true,false,false,null);
        String message = "hello world" + System.currentTimeMillis();

        channel.basicPublish("",Queue,null,message.getBytes());
        channel.close();
        connection.close();
    }
}
