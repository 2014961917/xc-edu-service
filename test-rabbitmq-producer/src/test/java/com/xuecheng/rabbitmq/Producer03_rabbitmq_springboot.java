package com.xuecheng.rabbitmq;

import com.xuecheng.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: zhangchao
 * @Date: 2019-09-24 13:59
 * @classDesc: 功能描述:(类的作用)
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer03_rabbitmq_springboot {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendByTopics(){
        for (int i = 0; i < 5; i++) {
            String message = "sms mail inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.sms",message);
            System.out.println("Send Message is :'" + message + "'");
        }
    }

    @Test
    public void testSendEmail(){
        String message = "send email message to user";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
        System.out.println("Send Message is :'" + message + "'");
    }
}
