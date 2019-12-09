package com.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-24 11:03
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class Consumer01 {

    private static final String QUEUE = "hello world";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");
        connection = factory.newConnection();
        channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE,true,false,false,null);
        //定义消费方法
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**
             * 消费者接收消息调用此方法
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包内容，可从中获取消息Id,消息的routingKey,交换机，消息和重传标志（收到消息失败后是否需要重新发送）
             * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //交换机
                String exchange = envelope.getExchange();
                //路由Key
                String routingKey = envelope.getRoutingKey();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("receive message..." +msg);
            }
        };

        /**
         * 监听队列String queue.boolean autoAck,Consumer callback
         * 参数明细
         * 1、队列名称
         * 2、是否自动回复 autoAck，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，
         * 设置为false这需要手都回复
         * 3、消费消息的方法，消费者接收到消息后调用此方法
         */
        channel.basicConsume(QUEUE,true,consumer);
    }
}
