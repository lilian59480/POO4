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
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Corentin
 */
@Entity
@Table(name = "DEPOT")
public class Depot extends Emplacement implements Serializable {

    public Depot(int heureDebut, int heureFin, double x, double y) {
        super(heureDebut, heureFin, x, y);
    }

    public Depot(Emplacement e) {
        this(e.getHeureDebut(), e.getHeureFin(), e.getX(), e.getY());
    }

    @Override
    public String toString() {
        return "Depot{" + super.toString() + '}';
    }

}
