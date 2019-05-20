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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Vehicule model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "VEHICULE")
public class Vehicule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Depot.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    private Depot depot;

    /**
     * A list of destinations.
     */
    @OneToMany
    private List<Emplacement> destinations;

    /**
     * Current planning.
     */
    @ManyToOne
    private Planning planning;

    /**
     * Instance.
     */
    @ManyToOne
    private Instance instance;

    /**
     * Cost.
     */
    private double cout;

    /**
     * Capacity.
     */
    private int capacite;

    /**
     * Used capacity.
     */
    private int capaciteUtilisee;

    /**
     * Current time.
     */
    @Column(name = "VEHICLETIME")
    private int time;

    /**
     * Vehicule constructor with empty capacity.
     */
    public Vehicule() {
        this(0);
    }

    /**
     * Vehicule constructor with no depot.
     *
     * @param capacite Vehicule capacity.
     */
    public Vehicule(int capacite) {
        this(null, 0);
    }

    /**
     * Vehicule constructor.
     *
     * @param depot Depot.
     * @param capacite Vehicule capacity.
     */
    public Vehicule(Depot depot, int capacite) {
        this.depot = depot;
        this.destinations = new ArrayList<>();
        this.cout = 0;
        this.capaciteUtilisee = 0;
        this.capacite = capacite;
    }

    /**
     * Check if this Vehicule is consistant.
     *
     * @return True if this vehicule is valid.
     */
    public boolean check() {
        boolean valid = true;
        valid &= (this.getCapaciteRestante() >= 0);

        Emplacement last = this.getDepot();
        int currentTime = 0;
        // TODO Check time
        for (Emplacement destination : this.destinations) {
            // Check that we can go to next destination
            valid = false;
        }
        return valid;
    }

    /**
     * Get a list of emplacements.
     *
     * @return Emplacement list.
     */
    public List<Emplacement> getEmplacements() {
        return this.destinations;
    }

    /**
     * Set a new planning.
     *
     * @param planning New planning.
     */
    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    /**
     * Set a new Instance.
     *
     * @param instance New instance.
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
        this.planning = instance.getPlanningCurrent();
    }

    /**
     * Get cost.
     *
     * @return The cout.
     */
    public double getCout() {
        return cout;
    }

    /**
     * Clear this vehicule.
     */
    public void clear() {
        this.cout = 0.0;
        this.capaciteUtilisee = 0;
        this.time = 0;
        this.destinations.clear();
    }

    /**
     * Get free capacity.
     *
     * @return Capacity - Used capacity.
     */
    public int getCapaciteRestante() {
        return this.capacite - this.capaciteUtilisee;
    }

    /**
     * Add new client.
     *
     * @param c New client.
     * @return True if we can add it.
     */
    public boolean addClient(Client c) {
        if (c == null) {
            return false;
        }

        //Test if enough remaining capacity
        if (this.getCapaciteRestante() < c.getDemande()) {
            return false;
        }

        int position = this.destinations.size();

        Emplacement lastEmplacement
                = position != 0 ? this.destinations.get(position - 1)
                        : depot;

        for (Emplacement e : c.getEmplacements()) {
            // Test if enough remaining time
            int timeToDestination = lastEmplacement.getTempsTo(e);
            int timeAtDestination = time + timeToDestination;
            timeAtDestination = timeAtDestination < e.getHeureDebut() ? e.getHeureDebut()
                    : this.time + timeToDestination;
            int timeAtDepot = timeAtDestination + e.getTempsTo(depot);
            if (timeAtDestination > e.getHeureFin() || timeAtDepot > depot.getHeureFin()) {
                continue;
            }

            c.setPosition(position + 1);

            if (!destinations.add(e)) {
                return false;
            }

            // Il faut ajouter la distance client -> depot et depot -> client
            // Ne pas oublier d'enlever la distance pour le dernier client
            if (position == 0) {
                double depotToClient = this.depot.getDistanceTo(e);
                double clientToDepot = e.getDistanceTo(depot);
                this.cout = depotToClient + clientToDepot;
            } else {
                double diffCout = -lastEmplacement.getDistanceTo(depot);
                diffCout += lastEmplacement.getDistanceTo(e);
                diffCout += e.getDistanceTo(depot);

                this.cout += diffCout;
            }

            this.capaciteUtilisee += c.getDemande();
            this.time = timeAtDestination;

            c.setVehicule(this);

            planning.recalculerCoutTotal();

            return true;
        }

        return false;
    }

    /**
     * Get depot.
     *
     * @return The depot.
     */
    public Depot getDepot() {
        return depot;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.instance);
        hash = 23 * hash + this.capacite;
        return hash;
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
        final Vehicule other = (Vehicule) obj;
        if (this.capacite != other.capacite) {
            return false;
        }
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vehicule{" + "depot=" + depot + ", destinations=" + destinations + ", cout=" + cout + ", capacite=" + capacite + ", capaciteUtilisee=" + capaciteUtilisee + '}';
    }

}
