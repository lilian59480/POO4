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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Planning model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "PLANNING")
public class Planning implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /**
     * List of vehicules.
     */
    @OneToMany(mappedBy = "planning")
    private List<Vehicule> vehicules;

    /**
     * An instance.
     */
    @ManyToOne
    private Instance instance;

    /**
     * Final cost of this planning.
     */
    private double cout;

    /**
     * Planning constructor, not linked to a planning.
     */
    public Planning() {
        this(null);
    }

    /**
     * Planning construcor.
     *
     * @param instance A new planning.
     */
    public Planning(Instance instance) {
        this(0.0, instance, new LinkedList<Vehicule>());
    }

    /**
     * Planning constuctor.
     *
     * @param cout Cost of this planning.
     * @param instance Instance linked to this planning.
     * @param vehiculeSet List of vehicules.
     */
    public Planning(double cout, Instance instance, List<Vehicule> vehiculeSet) {
        this.cout = cout;
        this.instance = instance;
        this.vehicules = vehiculeSet;
    }

    /**
     * Get a list of vehicules.
     *
     * @return A list of vehicules.
     */
    public List<Vehicule> getVehicules() {
        return this.vehicules;
    }

    /**
     * Add a new vehicule to the planning.
     *
     * @param v The vehicule to add.
     * @return True if the vehicule can be added.
     */
    public boolean addVehicule(Vehicule v) {
        if (v == null) {
            return false;
        }

        v.setPlanning(this);
        return this.vehicules.add(v);
    }

    /**
     * Recalculate the cost of this planning from the beginning.
     */
    public void recalculerCoutTotal() {
        double cost = 0;
        for (Vehicule vehicule : vehicules) {
            cost += vehicule.getCout();
        }
        int vehiculeSupp = this.vehicules.size() - this.instance.getNbVehicules();
        cost += vehiculeSupp * this.instance.getCoutVehicule();
        this.cout = cost;
    }

    /**
     * Get final cost.
     *
     * @return The cost.
     */
    public double getCout() {
        return this.cout;
    }

    /**
     * Clear this planning.
     */
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
        return Objects.equals(this.instance, other.instance);
    }

    @Override
    public String toString() {
        return "Planning{" + "vehicules=" + vehicules + ", cout=" + cout + '}';
    }

    /**
     * Check if this planning is valid.
     *
     * @return True if valid, false otherwise.
     */
    public boolean check() {
        boolean valid = true;
        for (Vehicule vehicule : this.vehicules) {
            valid &= vehicule.check();
        }
        return valid;
    }

}
