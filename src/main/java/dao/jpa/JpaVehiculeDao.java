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

import dao.VehiculeDao;
import java.util.Collection;
import javax.persistence.Query;
import model.Vehicule;

/**
 *
 * @author Corentin
 */
public class JpaVehiculeDao extends JpaDao<Vehicule> implements VehiculeDao {

    /**
     * Static Instance of JpaVehiculeDao.
     *
     * Used for singleton instance.
     */
    private static JpaVehiculeDao instance;

    /**
     * Get an instance of JpaVehiculeDao.
     *
     * Reuse existing instance or create a new one.
     *
     * @return A new instance
     */
    protected static JpaVehiculeDao getInstance() {
        if (JpaVehiculeDao.instance == null) {
            JpaVehiculeDao.instance = new JpaVehiculeDao();
        }
        return JpaVehiculeDao.instance;
    }

    /**
     * Constructor.
     */
    private JpaVehiculeDao() {
        super(Vehicule.class);
    }

    @Override
    public Collection<Vehicule> findAllNotUsed() {
        Query query = JpaDao.em.createNamedQuery("Vehicule.findAllNotUsed");
        return query.getResultList();
    }

}
