package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final TagService tagService;
    private final QuestionDao questionDao;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, TagService tagService, QuestionDao questionDao) {
        super(readWriteDao);
        this.tagService = tagService;
        this.questionDao = questionDao;
    }

    @Transactional
    @Override
    public void persist(Question e) {
        e.setTags(tagService.pushTags(e.getTags()));
        e.setId(null);
        super.persist(e);
    }

    @Override
    public Long getCountQuestion() {
        return questionDao.getCountQuestion();
    }
}
