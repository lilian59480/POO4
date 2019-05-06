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

import io.input.InstanceFileParser;
import java.io.File;
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

    public boolean solve() {
        dumbSolve();
        return true;
    }

    private void dumbSolve()
    {
        this.instance.clear();
        List<Client> clients = this.instance.getClients();

        for (Client c : clients) {
            boolean affecte = false;
            for (Vehicule v : this.instance.getVehicules()) {
                if (v.addClient(c)) {
                    affecte = true;
                    break;
                }
            }
            if (!affecte) {
                System.out.println("Plus de vehicule dispo pour affecter le client " + c);
                Vehicule v = this.instance.addVehicule();
                if (v != null) {
                    System.out.println("Nouveau vehicule créé");
                }
                if (!v.addClient(c)) {
                    System.err.println("Erreur : Impossible d'affecter le client" + c);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Instance i = null;
        try {
            InstanceFileParser ifp = new InstanceFileParser();
            i = ifp.parse(new File("src/main/resources/instances/instance_0-triangle.txt"));
            System.out.println(i);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        DumbSolver ds = new DumbSolver(i);
        ds.solve();
        System.out.println(ds.getInstance().getPlanningCurrent().toString());
    }
}
