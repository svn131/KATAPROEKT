package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AnswerDto;

import java.util.List;
import java.util.Optional;

public interface AnswerDtoService {
    Optional<List<AnswerDto>> getAllAnswersDtoByQuestionId(Long id, Long userId);
}


