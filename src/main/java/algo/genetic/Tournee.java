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
package algo.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Tournee class
 *
 * @author Thomas
 */
public class Tournee {
    /**
     * Cost of the tournee
     */
    private double cost;
    /**
     * Labels of the tournee
     */
    private List<Label> tournee;

    /**
     * Tournee constructor
     */
    public Tournee() {
        this.cost = 0.0;
        this.tournee = new ArrayList<>();
    }

    /**
     * Tournee constructor that extend a Tournee
     *
     * @param label         the Label
     * @param parentTournee the parentTournee to extend
     */
    public Tournee(Label label, Tournee parentTournee) {
        this.cost = label.getCost() + parentTournee.getCost();
        this.tournee = new ArrayList<>(parentTournee.getTournee());
        this.tournee.add(label);
    }

    /**
     * Get the cost of the Tournee
     *
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Get the tournee of the Tournee
     *
     * @return the cost
     */
    public List<Label> getTournee() {
        return tournee;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "cost=" + cost +
                ", tournee=" + tournee +
                '}';
    }
}
