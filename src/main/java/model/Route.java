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

import java.util.Objects;

/**
 *
 * @author Corentin
 */
public class Route {

    private Point from;
    private Point to;
    private double cout;
    private int temps;

    public Route(Point from, Point to, double cout, int temps) {
        this.from = from;
        this.to = to;
        this.cout = cout;
        this.temps = temps;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    public double getCout() {
        return cout;
    }

    public int getTemps() {
        return temps;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.from);
        hash = 53 * hash + Objects.hashCode(this.to);
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