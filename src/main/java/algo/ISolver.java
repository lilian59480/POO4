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
package algo;

import model.Instance;

/**
 * Interface for all solvers.
 *
 * @author Corentin
 */
public interface ISolver {

    /**
     * Get the current instance.
     *
     * @return the instance linked to the solver
     */
    public Instance getInstance();

    /**
     * Set the new instance.
     *
     * @param i the instance to link to the solver
     */
    public void setInstance(Instance i);

    /**
     * Solve the current instance.
     *
     * @return true if the solver found a solution
     */
    public boolean solve();
}
