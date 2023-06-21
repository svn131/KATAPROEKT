package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {

    void addNewVoteOnAnswer(User user, Answer answer);

    Optional<VoteAnswer> getVoteAnswerDao(Long answerId, Long userId);

    Long getAllCurrentUserVotesOfAnswer(Long answerId, Long userId);
}
