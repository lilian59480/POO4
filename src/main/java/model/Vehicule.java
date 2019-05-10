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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Corentin
 */
public class Vehicule {

    private Depot depot;
    private List<Emplacement> destinations;
    private Planning planning;
    private Instance instance;
    private double cout;
    private int capacite;
    private int capaciteUtilisee;
    private int time;

    public Vehicule() {
        this.destinations = new ArrayList<>();
        this.cout = 0;
        this.capaciteUtilisee = 0;
        this.capacite = 0;
    }

    public Vehicule(int capacite) {
        this.capacite = capacite;
    }

    public Vehicule(Depot depot, int capacite) {
        this();
        this.depot = depot;
        this.capacite = capacite;
    }

    public List<Emplacement> getEmplacements() {
        return this.destinations;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
        this.planning = instance.getPlanningCurrent();
    }

    public double getCout() {
        return cout;
    }

    public void clear() {
        this.cout = 0.0;
        this.capaciteUtilisee = 0;
        this.destinations.clear();
    }

    public int getCapaciteRestante() {
        return this.capacite - this.capaciteUtilisee;
    }

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

    public Depot getDepot() {
        return depot;
    }

    public List<Emplacement> getDestinations() {
        return destinations;
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

//    public static void main(String[] args) {
//        Depot depot = new Depot(0, 700, 0, 0);
//        Vehicule v = new Vehicule(depot, 200);
//
//        Emplacement e1 = new Emplacement(0, 200, 100, 0);
//        Route r = new Route(depot, e1, 10, 100);
//        Route r2 = new Route(e1, depot, 10, 100);
//        depot.addRouteTo(r);
//        e1.addRouteTo(r2);
//
//        Client c = new Client(100);
//        c.addEmplacement(e1);
//
//        v.addClient(c);
//        System.out.println(c.toString());
//        System.out.println(v.toString());
//    }
}
