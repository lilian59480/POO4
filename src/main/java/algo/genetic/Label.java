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
     * Emplacement associated to this label
     */
    private Emplacement emplacement;

    /**
     * Emplacements that will be served before
     */
    private List<Emplacement> precedents;

    /**
     * Emplacements that can be served after
     */
    private List<Emplacement> suivants;

    /**
     * Label constructor
     *
     * @param load the load of the vehicule
     * @param cost the cost of the travel
     * @param time the time of the travel
     * @param emplacement the emplacement associated to this label
     */
    public Label(int load, double cost, int time, Emplacement emplacement) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.emplacement = emplacement;
        this.precedents = new ArrayList<>();
        this.suivants = new ArrayList<>();
    }

    /**
     * Label constructor with an emplacement
     *
     * @param load the load of the vehicule
     * @param cost the cost of the travel
     * @param time the time of the travel
     * @param emplacement the emplacement associated to this label
     * @param firstEmplacement the first emplacement to add
     */
    public Label(int load, double cost, int time, Emplacement emplacement, Emplacement firstEmplacement) {
        this(load, cost, time, emplacement);
        precedents.add(firstEmplacement);
    }

    /**
     * Label constructor with a list of emplacements
     * @param load the load of the vehicule
     * @param cost the cost of the travel
     * @param time the time of the travel
     * @param emplacement the emplacement associated to this label
     * @param precedents the list of precedent emplacements to add
     */
    public Label(int load, double cost, int time, Emplacement emplacement, List<Emplacement> precedents) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.emplacement = emplacement;
        this.precedents = new ArrayList<>(precedents);
        this.suivants = new ArrayList<>();
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
     * Get the emplacement of the label
     * @return the emplacement
     */
    public Emplacement getEmplacement() {
        return emplacement;
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

    /**
     * Add an emplacement to the suivant emplacements
     * @param suivant the emplacement to add
     */
    public void addSuivant(Emplacement suivant) {
        this.suivants.add(suivant);
    }

    @Override
    public String toString() {
        return "Label{" +
                "load=" + load +
                ", cost=" + cost +
                ", time=" + time +
                ", emplacement=" + emplacement +
                ", precedents=" + precedents +
                ", suivants=" + suivants +
                '}';
    }
}
