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

import dao.ClientDao;
import java.util.Collection;
import javax.persistence.Query;
import model.Client;

/**
 *
 * @author Corentin
 */
public class JpaClientDao extends JpaDao<Client> implements ClientDao {

    /**
     * Static Instance of JpaClientDao.
     *
     * Used for singleton instance.
     */
    private static JpaClientDao instance;

    /**
     * Get an instance of JpaClientDao.
     *
     * Reuse existing instance or create a new one.
     *
     * @return A new instance
     */
    protected static JpaClientDao getInstance() {
        if (JpaClientDao.instance == null) {
            JpaClientDao.instance = new JpaClientDao();
        }
        return JpaClientDao.instance;
    }

    /**
     * Constructor.
     */
    private JpaClientDao() {
        super(Client.class);
    }

    @Override
    public Collection<Client> findNotServed() {
        Query query = JpaDao.em.createNamedQuery("Client.findNotServed");
        return query.getResultList();
    }

}
