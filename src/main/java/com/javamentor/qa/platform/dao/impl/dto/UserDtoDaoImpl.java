package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;

import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import java.util.Optional;


@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
        SELECT new com.javamentor.qa.platform.models.dto.UserDto(u.id, u.email, u.fullName, u.imageLink, u.city,
                (SELECT COALESCE(SUM(CASE WHEN va.voteType = 'LIKE' THEN 1 ELSE -1 END), 0) 
                 FROM VoteAnswer va 
                 WHERE va.user.id = :id),
                u.persistDateTime, 
                (SELECT COUNT(r) FROM Reputation r WHERE r.sender.id = u.id AND DATE(r.persistDate) = CURRENT_DATE())
        )
        FROM User u
        WHERE u.id = :id""", UserDto.class)
                .setParameter("id", id));
    }
}




