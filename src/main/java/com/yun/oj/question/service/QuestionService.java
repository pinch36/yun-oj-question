package com.yun.oj.question.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.IService;
import yun.oj.model.model.dto.question.QuestionQueryRequest;
import yun.oj.model.model.entity.Question;
import yun.oj.model.model.vo.QuestionVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author ylw16
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-08-30 21:03:15
*/
public interface QuestionService extends IService<Question> {

    void validQuestion(Question question, boolean b);

    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    Wrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
