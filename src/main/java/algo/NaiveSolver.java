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
import io.output.SolutionWriter;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Instance;
import model.Vehicule;

/**
 *
 * @author Corentin
 */
public class NaiveSolver implements ISolver {

    private Instance instance;
    private static final Logger LOGGER = Logger.getLogger(NaiveSolver.class.getName());

    public NaiveSolver() {
        this(null);
    }

    public NaiveSolver(Instance i) {
        this.instance = i;
    }

    @Override
    public Instance getInstance() {
        return instance;
    }

    @Override
    public void setInstance(Instance i) {
        this.instance = i;
    }

    @Override
    public boolean solve() {
        LOGGER.log(Level.FINE, "Solving a new instance");
        try {
            dumbSolve();
        } catch (SolverException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return false;
        }
        // Check for instance validity
        return this.instance.check();
    }

    private void dumbSolve() throws SolverException {
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
                LOGGER.log(Level.INFO, "Plus de vehicule dispo pour affecter le client {0}", c);
                Vehicule v = this.instance.addVehicule();
                if (v == null) {
                    LOGGER.log(Level.WARNING, "Invalid vehicule !");
                    throw new SolverException("Invalid vehicule");
                }
                LOGGER.log(Level.INFO, "Nouveau vehicule créé");
                if (!v.addClient(c)) {
                    LOGGER.log(Level.WARNING, "Erreur : Impossible d affecter le client {0}", c);
                }
            }
        }
    }

    public static void main(String[] args) {
        Instance i = null;
        for (int j = 0; j < 40; j++) {
            int id = j;
            try {
                InstanceFileParser ifp = new InstanceFileParser();
                i = ifp.parse(new File("src/main/resources/instances/instance_" + id + "-triangle.txt"));
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
                return;
            }
            NaiveSolver ds = new NaiveSolver(i);
            ds.solve();
            try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }
        }

    }
}
