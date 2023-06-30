package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public List<Tag> pushTags(List<Tag> tags) {
        List<Tag> unmatchedTags = tags.stream()
                .filter(obj1 -> getAll().stream()
                        .noneMatch(obj2 -> obj2.getName().equals(obj1.getName())))
                .collect(Collectors.toList());
        unmatchedTags.replaceAll(tag -> {
            tag.setDescription(tag.getDescription() != null ? tag.getDescription() : "placeholderString");
            return tag;
        });
        if (unmatchedTags.size() > 0) {
            persistAll(unmatchedTags);
        }
        List<Tag> savedTagList = getAll().stream()
                .filter(obj1 -> tags.stream()
                        .anyMatch(obj2 -> obj2.getName().equals(obj1.getName())))
                .collect(Collectors.toList());
        return savedTagList;
    }
}

