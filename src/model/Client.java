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

    public Client() {
        this.emplacements = new LinkedList<>();
    }

    public Client(int demande) {
        this();
        this.demande = demande;
    }

    public boolean addEmplacement(Emplacement e) {
        if (e == null) {
            return false;
        }
        return this.emplacements.add(e);
    }

    @Override
    public String toString() {
        return "Client{" + "emplacements=" + emplacements + '}';
    }
    
}
