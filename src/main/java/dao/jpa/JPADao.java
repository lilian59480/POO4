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

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Corentin
 * @param <T> Type of object
 */
public abstract class JPADao<T> {

    public static final String PERSISTANCE_UNIT = "POO4_ProjetPU";

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    private final Class<T> entityClass;

    public JPADao(Class<T> entityClass) {
        if (entityClass == null) {
            throw new NullPointerException("Entity class should not be null");
        }
        if (JPADao.emf == null) {
            JPADao.emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
        }

        if (JPADao.em == null) {
            JPADao.em = emf.createEntityManager();
        }

        this.entityClass = entityClass;
    }

    /**
     * Add new object to db
     *
     * @param obj Object to add
     * @return Trus if successful`
     */
    public boolean create(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JPADao.em.getTransaction();
        try {
            et.begin();
            JPADao.em.persist(obj);
            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
            return false;
        }
        return true;
    }

    /**
     * Find an object by its id
     *
     * @param id
     * @return
     */
    public T find(Integer id) {
        if (id == null) {
            return null;
        }
        T storedObject = JPADao.em.find(this.entityClass, id);
        return storedObject;
    }

    public Collection<T> findAll() {
        Collection<T> storedObjects;

        final CriteriaBuilder cb = JPADao.em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> request = cq.from(entityClass);
        cq.select(request);

        storedObjects = JPADao.em.createQuery(cq).getResultList();

        return storedObjects;
    }

    /**
     * Update object by merging it
     *
     * @param obj Object to update
     * @return True
     */
    public boolean update(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JPADao.em.getTransaction();
        try {
            et.begin();
            JPADao.em.merge(obj);
            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
            return false;
        }
        return true;
    }

    public boolean delete(T obj) {
        if (obj == null) {
            return false;
        }
        final EntityTransaction et = JPADao.em.getTransaction();
        try {
            et.begin();
            JPADao.em.remove(obj);
            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
            return false;
        }
        return true;
    }

    /**
     * Remove object
     *
     * @return True
     */
    public boolean deleteAll() {
        final EntityTransaction et = JPADao.em.getTransaction();
        try {
            et.begin();
            final CriteriaBuilder cb = JPADao.em.getCriteriaBuilder();
            final CriteriaDelete<T> cq = cb.createCriteriaDelete(entityClass);
            int nbDelete = JPADao.em.createQuery(cq).executeUpdate();
            et.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback();
            return false;
        }
        return true;
    }

    /**
     * Close database connection
     */
    public void close() {
        if (JPADao.em != null && JPADao.em.isOpen()) {
            JPADao.em.close();
        }
        if (JPADao.emf != null && JPADao.emf.isOpen()) {
            JPADao.emf.close();
        }
    }

}
