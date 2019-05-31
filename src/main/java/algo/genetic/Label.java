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
public class Label {
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
     * Points that will be served before
     */
    private List<Emplacement> precedents;

    /**
     * Label constructor
     *
     * @param load
     * @param cost
     * @param time
     */
    public Label(int load, double cost, int time) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.precedents = new ArrayList<>();
    }

    /**
     * Label constructor with an emplacement
     *
     * @param load
     * @param cost
     * @param time
     * @param firstEmplacement
     */
    public Label(int load, double cost, int time, Emplacement firstEmplacement) {
        this(load, cost, time);
        precedents.add(firstEmplacement);
    }

    /**
     * Label constructor with a list of emplacements
     * @param load
     * @param cost
     * @param time
     * @param precedents
     */
    public Label(int load, double cost, int time, List<Emplacement> precedents) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.precedents = new ArrayList<>(precedents);
    }

    /**
     * Get the load
     * @return the load
     */
    public int getLoad() {
        return load;
    }

    /**
     * Get the cost of the travel
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Get the time of the travel
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * Get the precedent emplacements
     * @return the precedent emplacements
     */
    public List<Emplacement> getPrecedents() {
        return precedents;
    }

    /**
     * Add an emplacement to the precedent emplacements
     * @param precedent the emplacement to add
     */
    public void addPrecedent(Emplacement precedent) {
        this.precedents.add(precedent);
    }

    @Override
    public String toString() {
        return "Label{" + "load=" + load + ", cost=" + cost + ", time=" + time + ", precedents=" + precedents + '}';
    }
    
}
