package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-24 10:21
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class Producer01 {
    //队列名称
    private static final String QUEUE = "hello world";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”,虚拟机相当于一个独立的mq服务器

        try {
            //创建与RabbitMQ服务的TCP连接
            connection = factory.newConnection();
            //创建与Exchange的通道，每个连接都可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();

            /**
             * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
             * 声明队列，如果Rabbit中没有次队列将自动创建
             * queue：队列名称
             * durable：持久化
             * exclusive：是否独占连接
             * autoDelete：队列不再使用时是否自动删除此队列
             * arguments：队列参数
             */
            channel.queueDeclare(QUEUE,true,false,false,null);
            String message = "hello world,小明。用时：'"+System.currentTimeMillis() +"'";

            /**
             * 消息发布方法
             *  param1:Exchange的名称，如果没有指定，则使用Default Exchange
             *  param2:routingKey,消息路由Key,是用于Exchange（交换机）将消息转发到指定的消息队列
             *  param3:消息包含的属性
             *  param4:消息体
             */
            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("Send Message is:'" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
