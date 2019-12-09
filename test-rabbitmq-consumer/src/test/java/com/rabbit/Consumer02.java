package com.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-24 14:10
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
public class Consumer02 {
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

        channel.queueDeclare(Queue,true,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                boolean redeliver = envelope.isRedeliver();
                String exchange = envelope.getExchange();
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("收到消息：" + msg);
            }
        };
        channel.basicConsume(Queue,true,consumer);
    }
}
