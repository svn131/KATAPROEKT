package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.CommenAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface CommentAnswerService extends ReadWriteService<CommentAnswer, Long> {
    Optional<CommentAnswer> addCommentAnswer(Long answerId, Long questionId, String comment);

    CommenAnswerDto toDto(CommentAnswer commentAnswer);
}

