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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Route model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "ROUTE")
public class Route implements Serializable {

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    /**
     * Origin point.
     */
    @ManyToOne
    private Point from;

    /**
     * Destination point.
     */
    @ManyToOne
    private Point to;

    /**
     * Cost of the travel.
     */
    private double cout;

    /**
     * Duration of the travel.
     */
    private int temps;

    /**
     * Route constructor.
     *
     */
    public Route() {
        this(null, null, 0, 0);
    }

    /**
     * Route constructor.
     *
     * @param from Beginning of the road.
     * @param to End of the road.
     * @param cout Cost of travel.
     * @param temps Duration of travel.
     */
    public Route(Point from, Point to, double cout, int temps) {
        this.from = from;
        this.to = to;
        this.cout = cout;
        this.temps = temps;
    }

    /**
     * Get origin Point.
     *
     * @return Origin point.
     */
    public Point getFrom() {
        return this.from;
    }

    /**
     * Get destination Point.
     *
     * @return Destination point.
     */
    public Point getTo() {
        return this.to;
    }

    /**
     * Get cost.
     *
     * @return The cost.
     */
    public double getCout() {
        return this.cout;
    }

    /**
     * Get duration.
     *
     * @return The duration.
     */
    public int getTemps() {
        return this.temps;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.from);
        hash = 79 * hash + Objects.hashCode(this.to);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.cout) ^ (Double.doubleToLongBits(this.cout) >>> 32));
        hash = 79 * hash + this.temps;
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
        final Route other = (Route) obj;
        if (Double.doubleToLongBits(this.cout) != Double.doubleToLongBits(other.cout)) {
            return false;
        }
        if (this.temps != other.temps) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Route{" + "from=" + from + ", to=" + to + ", cout=" + cout + ", temps=" + temps + '}';
    }

}
