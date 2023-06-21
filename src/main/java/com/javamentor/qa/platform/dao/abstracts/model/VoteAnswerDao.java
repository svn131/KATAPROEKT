package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

import java.util.Optional;

public interface VoteAnswerDao extends ReadWriteDao<VoteAnswer, Long> {

    Optional<VoteAnswer> getVoteAnswerDao(Long answerId, Long userId);

    Long getAllCurrentUserVotesOfAnswer(Long answerId, Long userId);
}
