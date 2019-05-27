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

import java.util.Collection;

/**
 *
 * @author Corentin
 * @param <T> Type of object
 */
public interface Dao<T> {

    /**
     * Add new object to db.
     *
     * @param obj Object to add
     * @return True if successful
     */
    public boolean create(T obj);

    /**
     * Find an object by its id.
     *
     * @param id Id to retrieve
     * @return The object with this id or null
     */
    public T find(int id);

    /**
     * Find all objects.
     *
     * @return A collection of objects
     */
    public Collection<T> findAll();

    /**
     * Update an object by merging it.
     *
     * @param obj Object to update
     * @return True if successful
     */
    public boolean update(T obj);

    /**
     * Delete an object.
     *
     * @param obj Object to delete
     * @return True if successful
     */
    public boolean delete(T obj);

    /**
     * Delete all objects.
     *
     * @return True if successful
     */
    public boolean deleteAll();

    /**
     * Close any open connections.
     */
    public void close();

}
