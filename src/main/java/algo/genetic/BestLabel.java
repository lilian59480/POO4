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
 * BestLabel class
 *
 * @author Thomas
 */
public class BestLabel {

    /**
     * Cost of the BestLabel and the previous labels
     */
    private double cost;
    /**
     * Labels of the BestLabel
     */
    private Label label;

    /**
     * List of previous labels
     */
    private List<Label> labelsPre;

    /**
     * BestLabel constructor
     */
    public BestLabel() {
        this.cost = 0.0;
        this.labelsPre = new ArrayList<>();
    }

    /**
     * BestLabel constructor with a Label
     * @param label  the label
     */
    public BestLabel(Label label) {
        this.cost = label.getCost();
        this.label = label;
        this.labelsPre = new ArrayList<>();
    }

    /**
     * BestLabel constructor that extends a BestLabel
     *
     * @param label the Label
     * @param parentTournee the parentTournee to extend
     */
    public BestLabel(Label label, BestLabel parentTournee) {
        this.cost = label.getCost() + parentTournee.getCost();
        this.label = label;
        this.labelsPre = new ArrayList<>(parentTournee.getLabelsPre());
        this.labelsPre.add(parentTournee.getLabel());
    }

    /**
     * Get the cost of the BestLabel
     *
     * @return the cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Get the label of BestLabel
     *
     * @return the label
     */
    public Label getLabel() {
        return this.label;
    }

    /**
     * Get the previous labels of BestLabel
     *
     * @return the list of previous labels
     */
    public List<Label> getLabelsPre() {
        return this.labelsPre;
    }

    @Override
    public String toString() {
        return "Tournee2{" + "cost=" + cost + ", label=" + label + ", labelsPre=" + labelsPre + '}';
    }

}
