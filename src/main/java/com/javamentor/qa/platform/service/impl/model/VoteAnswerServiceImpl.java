package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {
    private final VoteAnswerDao voteAnswerDao;
    private final ReputationService reputationService;

    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, VoteAnswerDao voteAnswerDao, ReputationService reputationService) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
        this.reputationService = reputationService;
    }

    @Override
    @Transactional
    public void addNewVoteOnAnswer(User user, Answer answer) {
        if (getVoteAnswerDao(answer.getId(), user.getId()).isEmpty()) {
            VoteAnswer newVoteAnswer = new VoteAnswer();
            newVoteAnswer.setUser(user);
            newVoteAnswer.setAnswer(answer);
            newVoteAnswer.setPersistDateTime(LocalDateTime.now());
            newVoteAnswer.setVoteType(VoteType.UP);
            voteAnswerDao.persist(newVoteAnswer);
            reputationService.addReputation(user, answer);
        } else if (getVoteAnswerDao(answer.getId(), user.getId()).orElseThrow().getVoteType().equals(VoteType.DOWN)) {
            VoteAnswer currentVoteAnswer = getVoteAnswerDao(answer.getId(), user.getId()).orElseThrow();
            currentVoteAnswer.setVoteType(VoteType.UP);
            currentVoteAnswer.setPersistDateTime(LocalDateTime.now());
            reputationService.changeReputation(user, answer);
        }
    }

    @Override
    public Optional<VoteAnswer> getVoteAnswerDao(Long answerId, Long userId) {
        return voteAnswerDao.getVoteAnswerDao(answerId, userId);
    }

    @Override
    public Long getAllCurrentUserVotesOfAnswer(Long answerId, Long userId) {
        return voteAnswerDao.getAllCurrentUserVotesOfAnswer(answerId, userId);
    }
}
