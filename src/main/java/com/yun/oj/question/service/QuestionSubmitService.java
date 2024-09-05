package com.yun.oj.question.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.IService;
import yun.oj.model.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import yun.oj.model.model.entity.QuestionSubmit;
import yun.oj.model.model.vo.QuestionSubmitVO;


import javax.servlet.http.HttpServletRequest;

/**
* @author ylw16
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-08-31 07:10:50
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    void validQuestionSubmit(QuestionSubmit questionSubmit, boolean b);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);

    Wrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
