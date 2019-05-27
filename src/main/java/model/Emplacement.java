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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Emplacement model representation.
 *
 * @author Corentin
 */
@Entity
@Table(name = "EMPLACEMENT")
public class Emplacement extends Point implements Serializable {
    

    /**
     * Client liked to this adress.
     */
    @ManyToOne
    private Client client;

    /**
     * Start time window.
     */
    private int heureDebut;

    /**
     * End time window.
     */
    private int heureFin;

    /**
     * Emplacement constructor.
     *
     */
    public Emplacement() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Emplacement constructor.
     *
     * @param heureDebut Start time window.
     * @param heureFin End time window.
     * @param x X position.
     * @param y Y position.
     */
    public Emplacement(int heureDebut, int heureFin, double x, double y) {
        super(x, y);
        if (heureFin < heureDebut) {
            throw new IllegalArgumentException("heureFin must be greater or equals than heureDebut");
        }
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    /**
     * Get Start time window.
     *
     * @return The HeureDebut.
     */
    public int getHeureDebut() {
        return this.heureDebut;
    }

    /**
     * Get End time window.
     *
     * @return The HeureFin.
     */
    public int getHeureFin() {
        return this.heureFin;
    }

    /**
     * Get client.
     *
     * @return The client.
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Set a new client.
     *
     * @param client The client.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Emplacement{" + "heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", " + super.toString() + '}';
    }

}
