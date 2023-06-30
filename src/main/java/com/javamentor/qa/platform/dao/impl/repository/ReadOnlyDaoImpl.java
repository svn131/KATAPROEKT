package com.javamentor.qa.platform.dao.impl.repository;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyDaoImpl<E, K> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    public List<E> getAll() {
        return entityManager.createQuery("FROM " + clazz.getName()).getResultList();
    }

    public boolean existsById(K id) {
        long count = (long) entityManager.createQuery("SELECT COUNT(e) FROM " + clazz.getName() + " e WHERE e.id =: id").setParameter("id", id).getSingleResult();
        return count > 0;
    }

    public Optional<E> getById(K id) {
        String hql = "FROM " + clazz.getName() + " WHERE id = :id";
        TypedQuery<E> query = (TypedQuery<E>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        if (ids != null && ids.iterator().hasNext()) {
            return entityManager.createQuery("FROM " + clazz.getName() + " e WHERE e.id IN :ids")
                    .setParameter("ids", ids).getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean existsByAllIds(Collection<K> ids) {
        if (ids != null && ids.size() > 0) {
            Long count = (Long) entityManager.createQuery("SELECT COUNT (*) FROM " + clazz.getName() + " e WHERE e.id IN :ids")
                    .setParameter("ids", ids).getSingleResult();
            return ids.size() == count;
        }
        return false;
    }

    public Optional<User> getByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("""
                        SELECT u
                        FROM User u
                        JOIN FETCH u.role
                        WHERE u.email=:email
                        """, User.class)
                .setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
