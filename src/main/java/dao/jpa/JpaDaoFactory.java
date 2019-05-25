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

import dao.DaoFactory;

/**
 *
 * @author Corentin
 */
public class JpaDaoFactory extends DaoFactory {

    @Override
    public JpaClientDao getClientDao() {
        return JpaClientDao.getInstance();
    }

    @Override
    public JpaDepotDao getDepotDao() {
        return JpaDepotDao.getInstance();
    }

    @Override
    public JpaEmplacementDao getEmplacementDao()
    {
        return JpaEmplacementDao.getInstance();
    }
    
    @Override
    public JpaPlanningDao getPlanningDao() {
        return JpaPlanningDao.getInstance();
    }

    @Override
    public JpaRouteDao getRouteDao() {
        return JpaRouteDao.getInstance();
    }

    @Override
    public JpaVehiculeDao getVehiculeDao() {
        return JpaVehiculeDao.getInstance();
    }

    @Override
    public JpaInstanceDao getInstanceDao() {
        return JpaInstanceDao.getInstance();
    }

}
