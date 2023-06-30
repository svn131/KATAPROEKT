package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ReputationService extends ReadWriteService<Reputation, Long> {

    Optional<Reputation> getReputation(Long answerId, Long senderId);

    void addReputation(User user, Answer answer);

    @Transactional
    void changeReputation(User user, Answer answer);

    Long getReputaionCount(Long id);
}

