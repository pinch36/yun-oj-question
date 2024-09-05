package com.yun.oj.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yun.oj.question.mapper.QuestionSubmitMapper;
import com.yun.oj.question.service.QuestionService;
import com.yun.oj.question.service.QuestionSubmitService;
import org.springframework.stereotype.Service;
import yun.oj.model.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import yun.oj.model.model.entity.Question;
import yun.oj.model.model.entity.QuestionSubmit;
import yun.oj.model.model.vo.QuestionSubmitVO;
import yun.oj.model.model.vo.QuestionVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author ylw16
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-08-31 07:10:50
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {
    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;

    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit, boolean b) {

    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        Long questionId = questionSubmitVO.getQuestionId();
        Question question = null;
        if (questionId != null && questionId > 0) {
            question = questionService.getById(questionId);
        }
        QuestionVO questionVO = questionService.getQuestionVO(question,request);
        questionSubmitVO.setUserVO(questionVO.getUserVO());
        questionSubmitVO.setQuestionVO(questionVO);
        return questionSubmitVO;
    }

    @Override
    public Wrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        return null;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        return null;
    }
}




