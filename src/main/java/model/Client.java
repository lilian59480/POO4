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
public class Client {

    private int demande;
    private List<Emplacement> emplacements;
    private int position;
    private Vehicule vehicule;

    public Client() {
        this(0);
    }

    public Client(int demande) {
        this.position = -1;
        this.emplacements = new LinkedList<>();
        this.demande = demande;
        this.vehicule = null;
    }

    public boolean addEmplacement(Emplacement e) {
        if (e == null) {
            return false;
        }
        e.setClient(this);
        return this.emplacements.add(e);
    }

    public int getDemande() {
        return demande;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Emplacement> getEmplacements() {
        return emplacements;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Client{" + "demande=" + demande + ", emplacements=" + emplacements + ", position=" + position + ", vehicule=" + vehicule + '}';
    }

    public void clear() {
        this.position = -1;
        this.vehicule = null;
    }

    /**
     * Check if a client is valid.
     *
     * Check if a vehicule is set to this client.
     *
     * @return True if this client is valid, false otherwise
     */
    public boolean check() {
        return this.vehicule != null;
    }

}
