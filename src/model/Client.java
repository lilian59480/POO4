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
    private Integer position;
    private Vehicule vehicule;

    public Client() {
        this.emplacements = new LinkedList<>();
        this.position = -1;
    }

    public Client(int demande) {
        this();
        this.demande = demande;
    }
    
    public Client(int demande, List<Emplacement> emplacements) {
        
        this();
        this.emplacements = emplacements;
        this.demande = demande;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<Emplacement> getEmplacements() {
        return emplacements;
    }
    
    @Override
    public String toString() {
        return "Client{" + "demande=" + demande + ", emplacements=" + emplacements + ", position=" + position + ", vehicule=" + vehicule + '}';
    }

    public void clear() {
        this.position = -1;
        this.vehicule = null;
    }

}
