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
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instance model representation.
 *
 * @author Corentin
 */
public class Instance {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Instance.class.getName());

    /**
     * Vehicule capacity.
     */
    private int capaciteVehicule;

    /**
     * Vehicule cost.
     */
    private int coutVehicule;

    /**
     * Number of vehicule.
     */
    private int nbVehicules;

    /**
     * Depot.
     */
    private Depot depot;

    /**
     * List of clients.
     */
    private List<Client> clients;

    /**
     * List of vehicules.
     */
    private List<Vehicule> vehicules;

    /**
     * List of routes.
     */
    private List<Route> routes;

    /**
     * List of plannings.
     */
    private List<Planning> plannings;

    /**
     * Instance constructor.
     */
    public Instance() {
        this.clients = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.vehicules = new LinkedList<>();
        this.plannings = new LinkedList<>();
        this.plannings.add(new Planning(this));
    }

    /**
     * Set vehicule capacity.
     *
     * @param capaciteVehicule The CapaciteVehicule.
     */
    public void setCapaciteVehicule(int capaciteVehicule) {
        this.capaciteVehicule = capaciteVehicule;
    }

    /**
     * Get vehicule capacity.
     *
     * @return The vehicule capacity.
     */
    public int getCapaciteVehicule() {
        return this.capaciteVehicule;
    }

    /**
     * Set vehicule cost.
     *
     * @param coutVehicule The CoutVehicule.
     */
    public void setCoutVehicule(int coutVehicule) {
        this.coutVehicule = coutVehicule;
    }

    /**
     * Set depot.
     *
     * @param depot The Depot.
     */
    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    /**
     * Set client list.
     *
     * @param clients A list of clients.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Set routes list.
     *
     * @param routes A list of routes.
     */
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * Set vehicule list.
     *
     * @param vehicules A list of vehicules.
     */
    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
        for (Vehicule v : this.vehicules) {
            this.getPlanningCurrent().addVehicule(v);
        }
    }

    /**
     * Get current planning.
     *
     * @return The current planning.
     */
    public Planning getPlanningCurrent() {
        return plannings.get(plannings.size() - 1);
    }

    /**
     * Set new planning.
     *
     * @param planningCurrent The new planning.
     */
    public void setPlanningCurrent(Planning planningCurrent) {
        this.plannings.add(planningCurrent);
    }

    /**
     * Add new planning.
     */
    public void addNewPlanning() {
        Planning p = new Planning(this);
        for (Vehicule v : this.vehicules) {
            p.addVehicule(v);
        }
        this.plannings.add(p);
    }

    /**
     * Clear this instance.
     */
    public void clear() {
        for (Client c : this.clients) {
            c.clear();
        }
        for (Vehicule v : this.vehicules) {
            v.clear();
        }
        while (this.nbVehicules < this.vehicules.size()) {
            this.vehicules.remove(this.vehicules.size() - 1);
        }
        this.addNewPlanning();
    }

    /**
     * Get clients.
     *
     * @return A list of clients.
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Get vehicules.
     *
     * @return A list of vehicules.
     */
    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    /**
     * Get depot.
     *
     * @return The depot.
     */
    public Depot getDepot() {
        return depot;
    }

    /**
     * Add a new vehicule.
     *
     * @return A new vehicule.
     */
    public Vehicule addVehicule() {
        Vehicule v = new Vehicule(this.depot, this.capaciteVehicule);
        v.setInstance(this);
        this.vehicules.add(v);
        this.getPlanningCurrent().addVehicule(v);
        return v;
    }

    /**
     * Get number of vehicules.
     *
     * @return Number of vehicules allocated.
     */
    public int getNbVehicules() {
        return nbVehicules;
    }

    /**
     * Set number of vehicules.
     *
     * @param nbVehicule New number of vehicule.
     */
    public void setNbVehicules(int nbVehicule) {
        this.nbVehicules = nbVehicule;
    }

    /**
     * Get Vehicule cost.
     *
     * @return The CoutVehicule.
     */
    public int getCoutVehicule() {
        return coutVehicule;
    }

    /**
     * Check the validity of this instance.
     *
     * Check the validity of all clients internally.
     *
     * @return True if the instance is valid, false otherwise
     */
    public boolean check() {
        boolean valid = this.getPlanningCurrent().check();

        for (Client c : this.clients) {
            valid &= c.check();
        }

        if (!valid) {
            LOGGER.log(Level.WARNING, "Invalid instance solution");
        }
        return valid;
    }

    @Override
    public String toString() {
        return "Instance{" + "capaciteVehicule=" + capaciteVehicule + ", coutVehicule=" + coutVehicule + ", depot=" + depot + ", clients=" + clients + ", vehicules=" + vehicules + ", routes=" + routes + ", plannings=" + plannings + '}';
    }

}
