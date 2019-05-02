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
package algo;

import java.util.ArrayList;
import java.util.List;
import model.Client;
import model.Instance;
import model.Vehicule;

/**
 *
 * @author Corentin
 */
public class DumbSolver implements ISolver {

    private final Instance instance;

    public DumbSolver(Instance i) {
        this.instance = i;
    }

    public Instance getInstance() {
        return instance;
    }

    public boolean resoudre() {
        dumbSolve();
        return true;
    }

    private void dumbSolve()
    {
        this.instance.clear();
        List<Client> clients = this.instance.getClients();
        List<Vehicule> vehicules = this.instance.getVehicules();
        List<Vehicule> vehiculesUtilises = new ArrayList<>();

        for (Client c : clients) {
            boolean affecte = false;
            for (Vehicule v : vehiculesUtilises) {
                if (v.addClient(c)) {
                    affecte = true;
                    break;
                }
            }
            if (!affecte) {
                if (vehicules.isEmpty()) {
                    System.err.println("Erreur : Plus de vehicule dispo pour affecter le client " + c);
                } else {
                    Vehicule v = vehicules.remove(0);
                    this.instance.addVehiculeInPlanning(v);
                    if (!v.addClient(c)) {
                        System.err.println("Erreur : client " + c + " n'a pas pu être affecté au vehicule " + v);
                    }
                    vehiculesUtilises.add(v);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Coucou, je suis un solveur stupide");
    }
}
