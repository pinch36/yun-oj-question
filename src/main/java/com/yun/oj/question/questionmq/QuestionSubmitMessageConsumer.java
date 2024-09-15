package com.yun.oj.question.questionmq;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.yun.oj.question.service.QuestionService;
import com.yun.oj.question.service.QuestionSubmitService;
import com.yun.oj.service.client.service.JudgeFeignClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import yun.oj.common.common.ErrorCode;
import yun.oj.common.exception.BusinessException;
import yun.oj.model.model.dto.judge.ExecuteCodeRequest;
import yun.oj.model.model.dto.judge.ExecuteCodeResponse;
import yun.oj.model.model.dto.judge.JudgeContext;
import yun.oj.model.model.dto.judge.JudgeInfo;
import yun.oj.model.model.dto.question.JudgeCase;
import yun.oj.model.model.entity.Question;
import yun.oj.model.model.entity.QuestionSubmit;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class QuestionSubmitMessageConsumer {
    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionService questionService;
    /**
     * 接收消息的方法
     *
     * @param questionSubmitIdStr     接收到的消息内容，是一个字符串类型
     * @param channel     消息所在的通道，可以通过该通道与 RabbitMQ 进行交互，例如手动确认消息、拒绝消息等
     * @param deliveryTag 消息的投递标签，用于唯一标识一条消息
     */
    // 使用@SneakyThrows注解简化异常处理
    @SneakyThrows
    @RabbitListener(queues = {QuestionSubmitMqConstant.QUESTION_SUBMIT_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String questionSubmitIdStr, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveQuestionSubmitId questionSubmitId = {}", questionSubmitIdStr);
        long questionSubmitId = Long.parseLong(questionSubmitIdStr);
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        update(questionSubmitId, 1, null,"更新提交状态为判题中失败");
        try {
            QuestionSubmit questionSubmitUpdate = judgeFeignClient.judge(questionSubmit);
            update(questionSubmitId, 2,JSONUtil.toBean(questionSubmitUpdate.getJudgeInfo(),JudgeInfo.class), "更新提交状态为判题成功失败");
        } catch (Exception e) {
            update(questionSubmitId, 3,null, "判题异常");
            log.error("判题异常:", e);
            throw new RuntimeException(e);
        }
        // 手动确认消息的接收，向RabbitMQ发送确认消息
        channel.basicAck(deliveryTag, false);
    }
    private void update(Long questionSubmitId, Integer status, JudgeInfo judgeInfo, String message) {
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(status);
        if (judgeInfo != null) {
            String judgeInfoJson = JSONUtil.toJsonStr(judgeInfo);
            questionSubmitUpdate.setJudgeInfo(judgeInfoJson);
        }
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            questionSubmitUpdate.setStatus(3);
            update = questionSubmitService.updateById(questionSubmitUpdate);
            if (!update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新提交状态为判题失败失败");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, message);
        }
    }
}
