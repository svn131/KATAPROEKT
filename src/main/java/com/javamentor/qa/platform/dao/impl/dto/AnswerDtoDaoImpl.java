package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long id, Long userId) {
        return entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(
                            a.id,
                            a.user.id,
                            a.question.id,
                            a.htmlBody,
                            a.persistDateTime,
                            a.isHelpful,
                            a.dateAcceptTime,
                            (SELECT COALESCE(SUM(CASE WHEN v.voteType = 'UP' THEN 1 ELSE -1 END), 0) FROM v),
                            (SELECT COALESCE(COUNT(r.count), 0) FROM r),
                            a.user.imageLink,
                            a.user.nickname,
                            (SELECT COALESCE(COUNT(*), 0) FROM v),
                            (SELECT CASE WHEN COALESCE(SUM(CASE WHEN v.voteType = 'UP' THEN 1 ELSE -1 END), 0)  > 0 THEN 'UP' ELSE 'DOWN' END FROM v))
                        FROM
                            Answer a
                            LEFT JOIN Reputation r ON a.user.id = r.author.id
                            LEFT JOIN VoteAnswer v ON a.id = v.answer.id
                        WHERE
                            a.question.id = :id
                            AND a.isDeleted = false
                            AND a.isDeletedByModerator = false
                            AND a.user.id = :userId
                        GROUP BY
                            a.id,
                            a.user.id,
                            a.question.id,
                            a.htmlBody,
                            a.persistDateTime,
                            a.isHelpful,
                            a.dateAcceptTime,
                            a.user.imageLink,
                            a.user.nickname
                        """, AnswerDto.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
    }
}
