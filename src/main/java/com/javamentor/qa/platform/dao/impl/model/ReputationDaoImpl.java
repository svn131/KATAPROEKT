package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> getReputation(Long answerId, Long senderId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("""
                        FROM Reputation r WHERE r.answer.id =: answerId
                         AND r.sender.id =: senderId
                        """, Reputation.class)
                .setParameter("answerId", answerId)
                .setParameter("senderId", senderId));
    }

    @Override
    public Long getReputationCount(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("""
                        FROM Reputation r WHERE r.answer.id =: id
                        """, Reputation.class)
                .setParameter("id", id)).orElseThrow().getCount().longValue();
    }

}
