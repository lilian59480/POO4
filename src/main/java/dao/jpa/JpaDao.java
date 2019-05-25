/*
 * POO4 Project
 * Copyright (C) 2019
 * Lilian Petitpas, Thomas Ternisien, Thibaut Fenain, Corentin Apolinario
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dao.jpa;

import dao.Dao;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Corentin
 * @param <T> Type of object
 */
public abstract class JpaDao<T> implements Dao<T> {

    /**
     * Name of the persistance unit.
     */
    public static final String PERSISTANCE_UNIT = "POO4_ProjetPU";

    /**
     * Entity manager factory.
     */
    protected static EntityManagerFactory emf;

    /**
     * Entity manager.
     */
    protected static EntityManager em;

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(JpaDao.class.getName());

    /**
     * Entity class to manipulate using Dao.
     */
    private final Class<T> entityClass;

    /**
     * Construct a new Dao using JPA.
     *
     * @param entityClass Class to use.
     */
    public JpaDao(Class<T> entityClass) {
        if (entityClass == null) {
            throw new NullPointerException("Entity class should not be null");
        }
        if (JpaDao.emf == null) {
            JpaDao.emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
        }

        if (JpaDao.em == null) {
            JpaDao.em = emf.createEntityManager();
        }

        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JpaDao.em.getTransaction();
        try {
            et.begin();
            JpaDao.em.persist(obj);
            et.commit();
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Exception while creating an object", ex);
            et.rollback();
            return false;
        }
        return true;
    }

    @Override
    public T find(int id) {
        T storedObject = JpaDao.em.find(this.entityClass, id);
        return storedObject;
    }

    @Override
    public Collection<T> findAll() {
        Collection<T> storedObjects;

        final CriteriaBuilder cb = JpaDao.em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(this.entityClass);
        Root<T> request = cq.from(this.entityClass);
        cq.select(request);

        storedObjects = JpaDao.em.createQuery(cq).getResultList();

        return storedObjects;
    }

    @Override
    public boolean update(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JpaDao.em.getTransaction();
        try {
            et.begin();
            JpaDao.em.merge(obj);
            et.commit();
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Exception while updating an object", ex);
            et.rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JpaDao.em.getTransaction();
        try {
            et.begin();
            JpaDao.em.remove(obj);
            et.commit();
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Exception while deleting an object", ex);
            et.rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        final EntityTransaction et = JpaDao.em.getTransaction();
        try {
            et.begin();
            final CriteriaBuilder cb = JpaDao.em.getCriteriaBuilder();
            final CriteriaDelete<T> cq = cb.createCriteriaDelete(this.entityClass);
            int nbDelete = JpaDao.em.createQuery(cq).executeUpdate();
            et.commit();
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Exception while deleting all object", ex);
            et.rollback();
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        if (JpaDao.em != null && JpaDao.em.isOpen()) {
            JpaDao.em.close();
        }
        if (JpaDao.emf != null && JpaDao.emf.isOpen()) {
            JpaDao.emf.close();
        }
    }

}
