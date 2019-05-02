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
import java.util.Objects;

/**
 *
 * @author Corentin
 */
public class Planning {
    private List<Vehicule> vehicules;
    private Instance instance;
    private double cout;
    
    public Planning()
    {
        this.vehicules = new LinkedList();
        this.cout = 0.0;
    }
    
    public Planning(Instance instance) {
        this();
        this.instance = instance;
    }
    
    public Planning(double cout, Instance instance, List<Vehicule> vehiculeSet) {
        this.cout = cout;
        this.instance = instance;
        this.vehicules = vehiculeSet;
    }
    
    public boolean addVehicule(Vehicule v) {
        if (v == null) {
            return false;
        }

        v.setPlanning(this);

        return this.vehicules.add(v);

    }
    
    public void recalculerCoutTotal() {
        double cost = 0;
        for (Vehicule vehicule : vehicules) {
            cost += vehicule.getCout();
        }
        this.cout = cost;
    }
    
    public void clear() {
        this.vehicules.clear();
        this.cout = 0.0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.vehicules);
        hash = 41 * hash + Objects.hashCode(this.instance);
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
        final Planning other = (Planning) obj;
        if (!Objects.equals(this.vehicules, other.vehicules)) {
            return false;
        }
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Planning{" + "vehicules=" + vehicules + ", cout=" + cout + '}';
    }
    
}
