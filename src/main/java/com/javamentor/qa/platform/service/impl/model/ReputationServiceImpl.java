package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao, ReputationDao reputationDao) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Optional<Reputation> getReputation(Long answerId, Long senderId) {
        return reputationDao.getReputation(answerId, senderId);
    }

    @Override
    @Transactional
    public void addReputation(User user, Answer answer) {
        if (getReputation(answer.getId(), user.getId()).isPresent()) {
            Reputation reputation = getReputation(answer.getId(), user.getId()).orElseThrow();
            reputation.setCount((int) (getReputaionCount(answer.getId()) + 10));
            reputationDao.update(reputation);
        } else {
            Reputation newReputation = new Reputation();
            newReputation.setCount(10);
            newReputation.setPersistDate(LocalDateTime.now());
            newReputation.setType(ReputationType.VoteAnswer);
            newReputation.setAuthor(answer.getUser());
            newReputation.setSender(user);
            newReputation.setQuestion(answer.getQuestion());
            reputationDao.persist(newReputation);
        }
    }

    @Override
    @Transactional
    public void changeReputation(User user, Answer answer) {
        Reputation reputation = getReputation(answer.getId(), user.getId()).orElseThrow();
        reputation.setCount((int) (getReputaionCount(answer.getId()) - 15));
        reputationDao.update(reputation);
    }

    @Override
    public Long getReputaionCount(Long id) {
        return reputationDao.getReputationCount(id);
    }
}
