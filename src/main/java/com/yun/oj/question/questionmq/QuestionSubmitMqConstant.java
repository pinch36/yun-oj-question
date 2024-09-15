package com.yun.oj.question.questionmq;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/09/10:03
 * @Description:
 */
public interface QuestionSubmitMqConstant {
    String QUESTION_SUBMIT_EXCHANGE_NAME = "question_submit_exchange";
    String QUESTION_SUBMIT_QUEUE_NAME = "question_submit_queue";
    String QUESTION_SUBMIT_ROUTING_KEY = "question_submit_routingKey";
}
