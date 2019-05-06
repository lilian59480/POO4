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

/**
 *
 * @author Corentin
 */
public class Instance {

    private int capaciteVehicule;
    private int coutVehicule;
    private int nbVehicules;
    private Depot depot;
    private List<Client> clients;
    private List<Vehicule> vehicules;
    private List<Route> routes;
    private List<Planning> plannings;

    public Instance() {
        this.clients = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.vehicules = new LinkedList<>();
        this.plannings = new LinkedList<>();
        this.plannings.add(new Planning(this));
    }

    public void setCapaciteVehicule(int capaciteVehicule) {
        this.capaciteVehicule = capaciteVehicule;
    }

    public void setCoutVehicule(int coutVehicule) {
        this.coutVehicule = coutVehicule;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
        for(Vehicule v : this.vehicules)
        {
            this.getPlanningCurrent().addVehicule(v);
        }
    }

    public Planning getPlanningCurrent() {
        return plannings.get(plannings.size() - 1);
    }

    public void setPlanningCurrent(Planning planningCurrent) {
        this.plannings.add(planningCurrent);
    }

    public void addNewPlanning()
    {
        Planning p = new Planning(this);
        for(Vehicule v : this.vehicules){
            p.addVehicule(v);
        }
        this.plannings.add(p);
    }
    
    public void clear() {
        for (Client c : this.clients) {
            c.clear();
        }
        for (Vehicule v : this.vehicules) {
            v.clear();
        }
        this.addNewPlanning();
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }
    
    public Vehicule addVehicule()
    {
        Vehicule v = new Vehicule(this.depot, this.capaciteVehicule);
        v.setInstance(this);
        this.vehicules.add(v);
        this.getPlanningCurrent().addVehicule(v);
        return v;
    }

    public int getNbVehicules() {
        return nbVehicules;
    }

    public void setNbVehicules(int nbVehicule) {
        this.nbVehicules = nbVehicule;
    }

    public int getCoutVehicule() {
        return coutVehicule;
    }
 
    @Override
    public String toString() {
        return "Instance{" + "capaciteVehicule=" + capaciteVehicule + ", coutVehicule=" + coutVehicule + ", depot=" + depot + ", clients=" + clients + ", vehicules=" + vehicules + ", routes=" + routes + ", plannings=" + plannings + '}';
    }

}
