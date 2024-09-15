package com.yun.oj.question.manager;

import com.yun.oj.question.questionmq.QuestionSubmitMqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/09/10:20
 * @Description:
 */
@Component
public class JudgeManager {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void judge(String questionSubmitIdStr){
        rabbitTemplate.convertAndSend(QuestionSubmitMqConstant.QUESTION_SUBMIT_EXCHANGE_NAME,QuestionSubmitMqConstant.QUESTION_SUBMIT_ROUTING_KEY,questionSubmitIdStr);
    }
}
