package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.CommenAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class CommentAnswerDaoImpl extends ReadWriteDaoImpl<CommentAnswer, Long> implements CommentAnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommenAnswerDto toDto(CommentAnswer commentAnswer, Long userId) {
        return entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.CommenAnswerDto
                                (ca.id, ca.answer.id, ca.comment.lastUpdateDateTime, ca.comment.persistDateTime, 
                                ca.comment.text, ca.comment.user.id, ca.comment.user.imageLink, SUM(r.count))
                        FROM CommentAnswer ca JOIN Reputation r ON r.author.id = ca.comment.user.id
                        WHERE r.author.id = :userId AND ca.id = :commentAnswerId
                        GROUP BY ca.id, ca.answer.id, ca.comment.lastUpdateDateTime, ca.comment.persistDateTime, 
                        ca.comment.text, ca.comment.user.id, ca.comment.user.imageLink
                        """, CommenAnswerDto.class)
                .setParameter("userId", userId)
                .setParameter("commentAnswerId", commentAnswer.getId())
                .getSingleResult();
    }
}
