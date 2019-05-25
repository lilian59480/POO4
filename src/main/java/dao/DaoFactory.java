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

import dao.jpa.JpaDaoFactory;

/**
 *
 * @author Corentin
 */
public abstract class DaoFactory {

    /**
     * Type of persistance to use.
     */
    public enum PersistenceType {
        /**
         * JPA.
         */
        JPA;
    };

    /**
     * Create a new factory.
     *
     * @param type Type of persistance to use.
     * @return A new factory of the persistance type chosen.
     * @throws Exception When using an invalid type.
     */
    public static DaoFactory getDaoFactory(PersistenceType type) throws Exception {
        switch (type) {
            case JPA:
                return new JpaDaoFactory();
            default:
                throw new Exception("Invalid type");
        }
    }

    /**
     * Get a ClientDao.
     *
     * @return A new or existing ClientDao
     */
    public abstract ClientDao getClientDao();

    /**
     * Get a DepotDao.
     *
     * @return A new or existing DepotDao
     */
    public abstract DepotDao getDepotDao();

    /**
     * Get a EmplacementDao.
     *
     * @return A new or existing EmplacementDao
     */
    public abstract EmplacementDao getEmplacementDao();

    /**
     * Get a PlanningDao.
     *
     * @return A new or existing PlanningDao
     */
    public abstract PlanningDao getPlanningDao();

    /**
     * Get a RouteDao.
     *
     * @return A new or existing RouteDao
     */
    public abstract RouteDao getRouteDao();

    /**
     * Get a VehiculeDao.
     *
     * @return A new or existing VehiculeDao
     */
    public abstract VehiculeDao getVehiculeDao();

    /**
     * Get a InstanceDao.
     *
     * @return A new or existing InstanceDao
     */
    public abstract InstanceDao getInstanceDao();

}
