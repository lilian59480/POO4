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
    private int nbClients;
    private int nbEmplacements;
    private int nbVehicules;
    private int capaciteVehicule;
    private int coutVehicule;
    private Depot depot;
    private List<Client> clients;
    private List<Vehicule> vehicules;
    private List<Route> routes;
    private List<Planning> plannings;
    
    public Instance()
    {
        this.clients = new LinkedList();
        this.routes = new LinkedList();
        this.vehicules = new LinkedList();
        this.plannings = new LinkedList();
        this.plannings.add(new Planning(this));
    }

    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    public void setNbEmplacements(int nbEmplacements) {
        this.nbEmplacements = nbEmplacements;
    }

    public void setNbVehicules(int nbVehicules) {
        this.nbVehicules = nbVehicules;
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
    
    public Planning getPlanningCurrent() {
        return plannings.get(plannings.size()-1);
    }
}
