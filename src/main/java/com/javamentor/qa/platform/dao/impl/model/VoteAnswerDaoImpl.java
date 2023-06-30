package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VoteAnswer> getVoteAnswerDao(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("""
                        FROM VoteAnswer va WHERE va.answer.id =: answerId
                         AND va.user.id =: userId AND va.voteType = 'UP'
                        """, VoteAnswer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId));
    }

    @Override
    public Long getAllCurrentUserVotesOfAnswer(Long answerId, Long userId) {
        return entityManager.createQuery("""
                        SELECT COUNT(*) FROM VoteAnswer va WHERE va.answer.id =: answerId
                         AND va.user.id =: userId
                        """, VoteAnswer.class)
                .setParameter("answerId", answerId)
                .setParameter("userId", userId).getResultStream().count();
    }
}

