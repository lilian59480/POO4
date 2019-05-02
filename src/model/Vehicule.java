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
public class Vehicule {
    private Depot depot;
    private List<Emplacement> destinations;
    private Planning planning;
    private Instance instance;
    private double cout;
    private int capacite;
    private int capaciteUtilisee;
    
    public Vehicule()
    {
        this.destinations = new LinkedList();
        this.cout = 0;
        this.capaciteUtilisee = 0;
        this.capacite = 0;
    }
    
    public Vehicule(int capacite)
    {
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
        this.destinations.clear();
    }
}
