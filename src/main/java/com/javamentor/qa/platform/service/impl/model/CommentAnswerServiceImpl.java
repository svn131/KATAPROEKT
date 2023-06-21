package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.CommenAnswerDto;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    private final AnswerService answerService;
    private final UserService userService;
    private final CommentAnswerDao commentAnswerDao;

    public CommentAnswerServiceImpl(ReadWriteDao<CommentAnswer, Long> readWriteDao, AnswerService answerService, UserService userService, CommentAnswerDao commentAnswerDao) {
        super(readWriteDao);
        this.answerService = answerService;
        this.userService = userService;
        this.commentAnswerDao = commentAnswerDao;
    }

    @Override
    public Optional<CommentAnswer> addCommentAnswer(Long answerId, Long questionId, String comment) {

        // Получаем ответ и проверяем его
        Answer answer = answerService.getById(answerId)
                .filter(a -> a.getQuestion().getId().equals(questionId))
                .orElse(null);
        if (answer == null) {
            return Optional.empty();
        }
        //Получаем пользователя
        User user = userService.getCurrentUser();
        //Создаём комментарий к ответу
        CommentAnswer commentAnswer = new CommentAnswer(comment, user);
        commentAnswer.getComment().setCommentType(CommentType.ANSWER);
        commentAnswer.setAnswer(answer);
        // Сохраняем комментарий к ответу в базе данных
        commentAnswerDao.persist(commentAnswer);
        return Optional.of(commentAnswer);
    }

    @Override
    public CommenAnswerDto toDto(CommentAnswer commentAnswer) {
        //Получаем пользователя
        User user = userService.getCurrentUser();
        //Возвращаем наше DTO
        return commentAnswerDao.toDto(commentAnswer, user.getId());
    }
}