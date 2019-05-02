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
import java.util.Set;

/**
 *
 * @author Corentin
 */
public class Vehicule {

    private Depot depot;
    private List<Emplacement> emplacements;
    private Planning planning;
    private Instance instance;
    private double cout;
    private int capacite;
    private int capaciteUtilisee;
    private int time;

    public Vehicule() {
        this.emplacements = new ArrayList<>();
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

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public double getCout() {
        return cout;
    }

    public void clear() {
        this.cout = 0.0;
        this.capaciteUtilisee = 0;
        this.emplacements.clear();
    }

    public int getCapaciteRestante() {
        return this.capacite - this.capaciteUtilisee;
    }
    
    public boolean addClient(Client c) {
        if (c == null) {
            return false;
        }

        if (this.getCapaciteRestante() < c.getDemande()) {
            return false;
        }

        Emplacement lastEmplacement = this.emplacements.get(this.emplacements.size() - 1);
        if( lastEmplacement == null)
            lastEmplacement = this.depot;
        
        for( Emplacement e : c.getEmplacements() ) {
            if( time + lastEmplacement.getRouteTo(e).getTemps() > lastEmplacement.getHeureFin() )
            {
                continue;
            }
            //TODO add emplacement to client
            //return true;
        }
        
        return false;
        
        /*
        // Get position
        if (clients.isEmpty()) {
            c.setPosition(0);
        } else {
            lastClient = clients.get(clients.size() - 1);
            int value = 1;
            if (lastClient.getPosition() != null) {
                value = lastClient.getPosition();
            }
            c.setPosition(value + 1);
        }

        boolean added = clients.add(c);
        if (!added) {
            return false;
        }

        // Il faut ajouter la distance client -> depot et depot -> client
        // Ne pas oublier d'enlever la distance pour le dernier client
        if (this.clientList.size() == 1) {
            double depotToClient = this.ndepot.getDistanceTo(c);
            double clientToDepot = c.getDistanceTo(ndepot);
            this.cout = depotToClient + clientToDepot;
        } else {
            if (lastClient == null) {
                throw new RuntimeException("Impossible if well made");
            }
            double diffCout = -lastClient.getDistanceTo(ndepot);
            diffCout += lastClient.getDistanceTo(c);
            diffCout += c.getDistanceTo(ndepot);

            this.cout += diffCout;
        }

        this.capaciteutilisee += c.getDemande();
        c.setVehicule(this);
        nplanning.recalculerCoutTotal();
        return true;*/
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
