package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.Comment;

import com.javamentor.qa.platform.service.abstracts.model.CommentService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ReadWriteServiceImpl<Comment, Long> implements CommentService {

    public CommentServiceImpl(ReadWriteDao<Comment, Long> readWriteDao) {
        super(readWriteDao);
    }

}
