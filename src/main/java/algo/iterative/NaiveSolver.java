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
public class NaiveSolver extends IterativeSolver {

    /**
     * No-op Client modifier.
     */
    private static final Modifier<Client> CM = new Modifier<Client>() {
        @Override
        public void modifyList(List<Client> list) {
            // No-op
        }
    };

    /**
     * No-op Vehicule modifier.
     */
    private static final Modifier<Vehicule> VM = new Modifier<Vehicule>() {
        @Override
        public void modifyList(List<Vehicule> list) {
            // No-op
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
    public NaiveSolver() {
        this(null);
    }

    /**
     * Solver constructor, with an Instance.
     *
     * @param i Instance to solve
     */
    public NaiveSolver(Instance i) {
        super(i, CM, VM);
    }

    @Override
    public String toString() {
        return "NaiveSolver";
    }

}
