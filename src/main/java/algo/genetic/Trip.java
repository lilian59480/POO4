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

import model.Emplacement;

/**
 * Label class
 *
 * @author Thomas
 */
public class Trip {
    /**
     * Load of the vehicule
     */
    private int load;

    /**
     * Cost of the travel
     */
    private double cost;

    /**
     * Duration of the travel
     */
    private int time;

    /**
     * Emplacements that will be served before
     */
    private List<Emplacement> emplacements;

    /**
     * Label constructor
     *
     * @param load        the load of the vehicule
     * @param cost        the cost of the travel
     * @param time        the time of the travel
     * @param emplacements the emplacements associated to this trip
     */
    public Trip(int load, double cost, int time, List<Emplacement> emplacements) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.emplacements = new ArrayList<>(emplacements);
    }

    /**
     * Label constructor converting a Label
     *
     * @param label        The label to convert
     */
    public Trip(Label label) {
        this.load = label.getLoad();
        this.cost = label.getCost();
        this.time = label.getTime();
        this.emplacements = new ArrayList<>(label.getPrecedents());
        this.emplacements.add(label.getEmplacement());
    }

    /**
     * Get the load
     *
     * @return the load
     */
    public int getLoad() {
        return this.load;
    }

    /**
     * Get the cost of the travel
     *
     * @return the cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Get the time of the travel
     *
     * @return the time
     */
    public int getTime() {
        return this.time;
    }

    /**
     * Get the emplacement of the label
     *
     * @return the emplacement
     */
    public List<Emplacement> getEmplacements() {
        return this.emplacements;
    }

    @Override
    public String toString() {
        return "Label{" +
                "load=" + load +
                ", cost=" + cost +
                ", time=" + time +
                ", emplacements=" + emplacements +
                '}';
    }
}
