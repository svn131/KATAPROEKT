package com.javamentor.qa.platform.dao.impl.repository;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class ReadWriteDaoImpl<E, K> extends ReadOnlyDaoImpl<E, K> {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public void persist(E e) {
        entityManager.persist(e);
    }

    public void update(E e) {
        entityManager.merge(e);
    }

    public void delete(E e) {
        entityManager.remove(e);
    }


    public void deleteById(K id) {
        Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        String hql = "DELETE " + clazz.getName() + " WHERE id = :id";
        entityManager.createQuery(hql).setParameter("id", id).executeUpdate();
    }

    public void persistAll(E... entities) {
        int i = 0;

        for (E entity : entities) {
            entityManager.persist(entity);

            i++;

            // Flush a batch of inserts and release memory
            if (i % batchSize == 0 && i > 0) {

                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }
        if (i > 0) {
            entityManager.flush();
            entityManager.clear();
        }

    }

    public void persistAll(Collection<E> entities) {
        int i = 0;

        for (E entity : entities) {
            entityManager.persist(entity);

            i++;

            // Flush a batch of inserts and release memory
            if (i % batchSize == 0 && i > 0) {

                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }
        if (i > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }


    public void deleteAll(Collection<E> entities) {
        for (E entity : entities) {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }
    }


    public void updateAll(Iterable<? extends E> entities) {
        int i = 0;

        for (E entity : entities) {
            entityManager.merge(entity);

            i++;

            // Flush a batch of inserts and release memory
            if (i % batchSize == 0 && i > 0) {

                entityManager.flush();
                entityManager.clear();
                i = 0;
            }
        }
        if (i > 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }
}

