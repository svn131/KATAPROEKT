package com.javamentor.qa.platform.dao.impl.dto;


import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatedTagDto> getTopTags() {
        return entityManager.createQuery("""
                            SELECT new com.javamentor.qa.platform.models.dto.RelatedTagDto(t.id, t.name, COUNT(q))
                            FROM Tag t
                            INNER JOIN t.questions q
                            GROUP BY t.id, t.name
                            ORDER BY COUNT(q) DESC
                        """, RelatedTagDto.class)
                .setMaxResults(10).getResultList();
    }
}
