package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;

public interface TagService extends ReadWriteService<Tag, Long> {
    List<Tag> pushTags(List<Tag> tags);
}
