package com.yun.oj.question.questionmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class QuestionSubmitMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
	/**
     * 发送消息的方法
     *
     * @param message    消息内容，要发送的具体消息
     */
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(QuestionSubmitMqConstant.QUESTION_SUBMIT_EXCHANGE_NAME, QuestionSubmitMqConstant.QUESTION_SUBMIT_ROUTING_KEY, message);
    }

}
