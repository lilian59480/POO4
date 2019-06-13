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
     * Number of vehicle in the Tournee
     */
    private int nbVehicule;

    /**
     * Cost for extra vehicles
     */
    private double costExtraVehicule;

    /**
     * Labels of the tournee
     */
    private List<Trip> trips;

    /**
     * Tournee constructor
     *
     * @param nbVehicle         the number of vehicule of the tournee
     * @param costExtraVehicule the cost for extra vehicle
     */
    public Tournee(int nbVehicle, double costExtraVehicule) {
        this.nbVehicule = nbVehicle;
        this.costExtraVehicule = costExtraVehicule;
        this.cost = 0.0;
        this.trips = new ArrayList<>();
    }

    /**
     * Tournee constructor that extends a Tournee
     *
     * @param label         the Label
     * @param parentTournee the parentTournee to extend
     */
    public Tournee(Label label, Tournee parentTournee) {
        this.nbVehicule = parentTournee.getNbVehicule();
        this.costExtraVehicule = parentTournee.getCostExtraVehicule();
        this.trips = new ArrayList<>(parentTournee.getTrips());
        this.addLabel(label);
    }

    /**
     * Tournee constructor that copies a Tournee
     *
     * @param tournee the parentTournee to extend
     */
    public Tournee(Tournee tournee) {
        this.nbVehicule = tournee.getNbVehicule();
        this.costExtraVehicule = tournee.getCostExtraVehicule();
        this.cost = tournee.getCost();
        this.trips = new ArrayList<>(tournee.getTrips());
    }

    /**
     * Tournee constructor that convert a BestLabel
     *
     * @param bestLabel         the BestLabel to convert
     * @param nbVehicule        the number of vehicule of the tournee
     * @param costExtraVehicule the cost for extra vehicle
     */
    public Tournee(BestLabel bestLabel, int nbVehicule, double costExtraVehicule) {
        this.nbVehicule = nbVehicule;
        this.costExtraVehicule = costExtraVehicule;
        this.cost = 0.0;
        this.trips = new ArrayList<>();
        this.addLabels(bestLabel.getLabelsPre());
        this.addLabel(bestLabel.getLabel());
    }

    /**
     * Function that updates the cost of the Tournee
     */
    private void updateCost() {
        int nbExtraVRequired = Math.max(this.trips.size() - this.nbVehicule, 0);
        double tempCost = 0.0;
        for (Trip trip : this.trips) {
            tempCost += trip.getCost();
        }
        this.cost = tempCost + nbExtraVRequired * this.costExtraVehicule;
    }

    /**
     * Get the cost of the Tournee
     *
     * @return the cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Get the trips of the Tournee
     *
     * @return the cost
     */
    public List<Trip> getTrips() {
        return this.trips;
    }

    /**
     * Add a label to the tournee
     *
     * @param label the label to add
     */
    public void addLabel(Label label) {
        this.trips.add(new Trip(label));
        this.updateCost();
    }

    /**
     * Add a labels to the tournee
     *
     * @param labels labels to add
     */
    public void addLabels(List<Label> labels) {
        for(Label label: labels) {
            this.addLabel(label);
        }
        this.updateCost();
    }

    /**
     * Get the number of vehicule of the tournee
     *
     * @return the number of vehicule
     */
    public int getNbVehicule() {
        return this.nbVehicule;
    }

    /**
     * Get the cost for extra vehicule
     *
     * @return the cost for extra vehicule
     */
    public double getCostExtraVehicule() {
        return this.costExtraVehicule;
    }

    @Override
    public String toString() {
        return "Tournee{" + "cost=" + cost + ", nbVehicule=" + nbVehicule + ", costExtraVehicule=" + costExtraVehicule + ", trips=" + trips + '}';
    }

}
