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
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Point model representation.
 *
 * @author Corentin
 */
@Entity
@Table(
        name = "POINT"
//        uniqueConstraints = {
//            @UniqueConstraint(
//                    columnNames = {
//                        "X",
//                        "Y"
//                    }
//            )
//        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "POINTTYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    /**
     * X coordiante.
     */
    private double x;

    /**
     * Y coordinate.
     */
    private double y;

    /**
     * Map of route to a specific Point.
     */
    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    @MapKey(name = "to")
    private Map<Point, Route> routeTo;

    /**
     * Point constructor, to the origin.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Point constructor.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.routeTo = new HashMap<>();
    }

    /**
     * Get id.
     *
     * @return The id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set id.
     *
     * @param id The new id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the route to a specified point.
     *
     * @param p Point.
     * @return A road to this point or null if there is no route.
     */
    public Route getRouteTo(Point p) {
        return this.routeTo.get(p);
    }

    /**
     * Get a list of roads.
     *
     * @return A map or Point-Road
     */
    public Map<Point, Route> getRoutesTo() {
        return this.routeTo;
    }

    /**
     * Get X coordinate.
     *
     * @return X coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get Y coordinate.
     *
     * @return Y coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get distance between 2 points.
     *
     * @param p The destination point.
     * @return A cost, or +Inf if there is no road.
     */
    public double getDistanceTo(Point p) {
        Route r = this.routeTo.get(p);
        if (r == null) {
            return Double.POSITIVE_INFINITY;
        }
        return r.getCout();
    }

    /**
     * Get duration between 2 points.
     *
     * @param p The destination point.
     * @return A duration or 2147483647 if there is no road.
     */
    public int getTempsTo(Point p) {
        Route r = this.routeTo.get(p);
        if (r == null) {
            return Integer.MAX_VALUE;
        }
        return r.getTemps();
    }

    /**
     * Add a new route.
     *
     * @param r The route to add.
     * @return True if the route is valid.
     */
    public boolean addRouteTo(Route r) {
        if (r.getFrom() != this || r.getTo() == null) {
            return false;
        }
        this.routeTo.put(r.getTo(), r);
        return true;
    }

    @Override
    public String toString() {
        return "Point{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }

}
