package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.CommenAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;

public interface CommentAnswerDao extends ReadWriteDao<CommentAnswer, Long> {
    CommenAnswerDto toDto(CommentAnswer commentAnswer, Long userId);
}

