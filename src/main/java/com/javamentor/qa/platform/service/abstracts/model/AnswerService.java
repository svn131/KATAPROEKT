package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface AnswerService extends ReadWriteService<Answer, Long> {

    void markAsDeleted(Long id);

}
