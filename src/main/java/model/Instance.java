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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Instance model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "INSTANCE")
public class Instance implements Serializable {

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Instance.class.getName());

    /**
     * Database Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Depot depot;

    /**
     * List of clients.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Client> clients;

    /**
     * List of vehicules.
     */
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private List<Vehicule> vehicules;

    /**
     * List of routes.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Route> routes;

    /**
     * List of plannings.
     */
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private List<Planning> plannings;

    /**
     * Name of the instance.
     */
    private String instanceName;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instance other = (Instance) obj;
        if (this.capaciteVehicule != other.getCapaciteVehicule()) {
            return false;
        }
        if (this.coutVehicule != other.getCoutVehicule()) {
            return false;
        }
        if (this.nbVehicules != other.getNbVehicules()) {
            return false;
        }
        if (!Objects.equals(this.depot, other.getDepot())) {
            return false;
        }
        if (!Objects.equals(this.clients, other.getClients())) {
            return false;
        }
        if (!Objects.equals(this.vehicules, other.getVehicules())) {
            return false;
        }
        return Objects.equals(this.routes, other.routes);
    }

    /**
     * Set vehicule capacity.
     *
     * @param capaciteVehicule The CapaciteVehicule.
     */
    public void setCapaciteVehicule(int capaciteVehicule) {
        if (capaciteVehicule < 0) {
            throw new IllegalArgumentException("capcciteVehicule should not be negative");
        }
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
        if (coutVehicule < 0) {
            throw new IllegalArgumentException("coutVehicule should not be negative");
        }
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
        if (clients == null) {
            throw new NullPointerException("List of clients should not be null");
        }
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
        return this.plannings.get(this.plannings.size() - 1);
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
        return this.clients;
    }

    /**
     * Get vehicules.
     *
     * @return A list of vehicules.
     */
    public List<Vehicule> getVehicules() {
        return this.vehicules;
    }

    /**
     * Get depot.
     *
     * @return The depot.
     */
    public Depot getDepot() {
        return this.depot;
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
        return this.nbVehicules;
    }

    /**
     * Set number of vehicules.
     *
     * @param nbVehicule New number of vehicule.
     */
    public void setNbVehicules(int nbVehicule) {
        if (nbVehicule < 0) {
            throw new IllegalArgumentException("nbVehicule should not be negative");
        }
        this.nbVehicules = nbVehicule;
    }

    /**
     * Get Vehicule cost.
     *
     * @return The CoutVehicule.
     */
    public int getCoutVehicule() {
        return this.coutVehicule;
    }

    /**
     * Get Instance name.
     *
     * @return The instance Name.
     */
    public String getInstanceName() {
        return this.instanceName;
    }

    /**
     * Set instance Name.
     *
     * @param instanceName setted instanceName.
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
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
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.capaciteVehicule;
        hash = 29 * hash + this.coutVehicule;
        hash = 29 * hash + this.nbVehicules;
        hash = 29 * hash + Objects.hashCode(this.depot);
        hash = 29 * hash + Objects.hashCode(this.clients);
        hash = 29 * hash + Objects.hashCode(this.vehicules);
        hash = 29 * hash + Objects.hashCode(this.routes);
        return hash;
    }

    @Override
    public String toString() {
        if (this.instanceName != null) {
            return "Instance : " + this.instanceName;
        }
        return "Instance : " + this.id;
    }

}
