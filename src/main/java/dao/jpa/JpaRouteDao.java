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

import dao.RouteDao;
import model.Route;

/**
 *
 * @author Corentin
 */
public class JpaRouteDao extends JpaDao<Route> implements RouteDao {

    /**
     * Static Instance of JpaRouteDao.
     *
     * Used for singleton instance.
     */
    private static JpaRouteDao instance;

    /**
     * Constructor.
     */
    private JpaRouteDao() {
        super(Route.class);
    }

    /**
     * Get an instance of JpaRoutegDao.
     *
     * Reuse existing instance or create a new one.
     *
     * @return A new instance
     */
    protected static JpaRouteDao getInstance() {
        if (JpaRouteDao.instance == null) {
            JpaRouteDao.instance = new JpaRouteDao();
        }
        return JpaRouteDao.instance;
    }

}
