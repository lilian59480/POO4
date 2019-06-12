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
package algo.iterative;

import java.util.Comparator;
import java.util.List;
import model.Client;
import model.Instance;
import model.Vehicule;

/**
 * Implementation of a Naive solver.
 *
 * This solver is using the client list on the same way as it is inserted.
 *
 * @author Corentin
 */
public class CvCostCapacitySortedSolver extends IterativeSolver {

    /**
     * Client sorted by demand from larger to smaller modifier.
     */
    private static final Modifier<Client> CM = new Modifier<Client>() {
        @Override
        public void modifyList(List<Client> list) {
            list.sort(new Comparator<Client>() {
                @Override
                public int compare(Client c1, Client c2) {
                    return c1.getDemande() - c2.getDemande();
                }
            });
        }
    };

    /**
     * Vehicule sorted by remaining capacity from larger to smaller modifier.
     */
    private static final Modifier<Vehicule> VM = new Modifier<Vehicule>() {
        @Override
        public void modifyList(List<Vehicule> list) {
            list.sort(new Comparator<Vehicule>() {
                @Override
                public int compare(Vehicule v1, Vehicule v2) {
                    return v1.getCapaciteRestante() - v2.getCapaciteRestante();
                }
            });
        }
    };

    /**
     * Solver constructor, without an Instance.
     *
     * You should set your instance later.
     *
     * This constructor is recommended as you can solve multiples instances by
     * using the instance setter.
     */
    public CvCostCapacitySortedSolver() {
        this(null);
    }

    /**
     * Solver constructor, with an Instance.
     *
     * @param i Instance to solve
     */
    public CvCostCapacitySortedSolver(Instance i) {
        super(i, CM, VM);
    }

    @Override
    public String toString() {
        return "CvCostCapacitySortedSolver";
    }

}
