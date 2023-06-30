package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {

        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.QuestionCommentDto
                (cq.id, q.id, c.lastUpdateDateTime, c.persistDateTime, c.text, u.id, u.imageLink, r.count)
                FROM Question q JOIN CommentQuestion cq ON q.id = cq.question.id
                JOIN Comment c ON cq.comment.id = c.id
                JOIN User u ON u.id = c.user.id
                LEFT JOIN Reputation r ON u.id = r.author.id
                WHERE q.id = :questionId""", QuestionCommentDto.class)
                .setParameter("questionId", questionId).getResultList();
    }
}

