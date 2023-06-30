package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDtoServiceImpl implements CommentDtoService {

    private final CommentDtoDao commentDtoDao;

    private final QuestionService questionService;

    public CommentDtoServiceImpl(CommentDtoDao commentDtoDao, QuestionService questionService) {
        this.commentDtoDao = commentDtoDao;
        this.questionService = questionService;
    }

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        questionService.getById(questionId).orElseThrow(() -> new ApiRequestException("Question not found"));
        return commentDtoDao.getAllQuestionCommentDtoById(questionId);
    }
}

