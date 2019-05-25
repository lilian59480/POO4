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

import dao.jpa.JPADaoFactory;

/**
 *
 * @author Corentin
 */
public abstract class DaoFactory {

    public enum PersistenceType {
        JPA;
    };

    public static DaoFactory getDaoFactory(PersistenceType type) throws Exception {
        switch (type) {
            case JPA:
                return new JPADaoFactory();
            default:
                throw new Exception("Invalid type");
        }
    }

    public abstract ClientDao getClientDao();

    public abstract DepotDao getDepotDao();

    public abstract EmplacementDao getEmplacementDao();
    
    public abstract PlanningDao getPlanningDao();

    public abstract RouteDao getRouteDao();

    public abstract VehiculeDao getVehiculeDao();

    public abstract InstanceDao getInstanceDao();

}
