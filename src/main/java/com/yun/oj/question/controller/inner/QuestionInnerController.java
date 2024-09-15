package com.yun.oj.question.controller.inner;

import com.yun.oj.question.service.QuestionService;
import com.yun.oj.question.service.QuestionSubmitService;
import com.yun.oj.service.client.service.QuestionFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yun.oj.model.model.entity.Question;
import yun.oj.model.model.entity.QuestionSubmit;
import javax.annotation.Resource;

/**
 * 问题接口
 *

 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;


    @Override
    @PostMapping("/question_submit/update")
    public boolean updateById(QuestionSubmit questionSubmitUpdate) {
        return questionSubmitService.updateById(questionSubmitUpdate);
    }

    @Override
    @GetMapping("/question/get/id")
    public Question getQuestionById(@RequestParam("questionId")Long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }
}
