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
package dao;

import javax.persistence.Query;
import model.Instance;

/**
 *
 * @author Corentin
 */
public class JPAInstanceDao extends JPADao<Instance> implements InstanceDao {

    private static JPAInstanceDao instance;

    protected static JPAInstanceDao getInstance() {
        if (JPAInstanceDao.instance == null) {
            JPAInstanceDao.instance = new JPAInstanceDao();
        }
        return JPAInstanceDao.instance;
    }

    private JPAInstanceDao() {
        super(Instance.class);
    }

    @Override
    public Instance findByName(String name) {
        Query query = JPADao.em.createNamedQuery("Instance.findByNom");
        query.setParameter("nom", name);
        return (Instance) query.getSingleResult();
    }

}
