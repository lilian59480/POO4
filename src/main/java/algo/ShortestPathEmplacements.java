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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Depot;
import model.Instance;
import model.Emplacement;
import model.Planning;
import model.Route;
import model.Vehicule;

/**
 *
 * @author Thomas
 */
public class ShortestPathEmplacements implements ISolver {

    private Instance instance;
    private static final Logger LOGGER = Logger.getLogger(NaiveSolver.class.getName());
    private List<Emplacement> chromosome;
    private Depot depot;

    public ShortestPathEmplacements() {
        this(null);
    }

    public ShortestPathEmplacements(Instance i) {
        this.instance = i;
        this.instanceToChromosome();
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
            List<Integer> tournee = findShortestPath();
            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int numV = 0;
            int nbV = this.instance.getNbVehicules();
            Vehicule v = vehicules.get(numV);
            for (int i = 0; i < this.chromosome.size(); i++) {
                if (i != 0 && tournee.get(i).compareTo(tournee.get(i - 1)) != 0) {
                    numV++;
                    if (numV < nbV) {
                        v = vehicules.get(numV);
                    } else {
                        LOGGER.log(Level.INFO, "Ajout d'un extra vehicule");
                        v = this.instance.addVehicule();
                    }
                }
                if (!v.addEmplacement(this.chromosome.get(i))) {
                    LOGGER.log(Level.WARNING,
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                    throw new SolverException(
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                }
            }
        } catch (SolverException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return false;
        }

        // Check for instance validity
        return this.instance.check();
    }

    private List<Integer> findShortestPath() throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        int closeTime = this.instance.getDepot().getHeureFin();
        List<Double> V = new LinkedList<>(); // list des couts les + faible pour chaque emplacement
        List<Integer> P = new LinkedList<>(); // list des points precedant, P[i] => point avant i
        for (int i = 0; i < this.chromosome.size(); i++) {
            V.add(Double.MAX_VALUE);
            P.add(0);
        }
        for (int i = 0; i < this.chromosome.size(); i++) {
            int time = 0, load = 0;
            double cost = 0.0;
            for (int j = i; j < this.chromosome.size(); j++) {
                Emplacement e = this.chromosome.get(j);
                load += e.getClient().getDemande();
                if (i == j) {
                    Route r = e.getRouteTo(this.depot);
                    cost = r.getCout() * 2;
                    time = Math.max(r.getTemps(), e.getHeureDebut()) + r.getTemps();
                } else {
                    Emplacement eb = this.chromosome.get(j - 1);
                    Route r0 = eb.getRouteTo(this.depot);
                    Route r1 = e.getRouteTo(eb);
                    Route r2 = e.getRouteTo(this.depot);
                    cost = cost - r0.getCout() + r1.getCout() + r2.getCout();
                    time = Math.max(time - r0.getTemps() + r1.getTemps(), e.getHeureDebut());
                    // Check if the vehicule will arrive before the client's closing time
                    if (time > e.getHeureFin()) {
                        break;
                    }
                    time += r2.getTemps();
                }
                if (load > capaV || time > closeTime) {
                    break;
                }
                if (i == 0) {
                    if (cost < V.get(j)) {
                        V.set(j, cost);
                        P.set(j, this.depot.getId());
                    }
                } else {
                    if (V.get(i - 1) + cost < V.get(j)) {
                        V.set(j, V.get(i - 1) + cost);
                        P.set(j, this.chromosome.get(i - 1).getId());
                    }
                }
            }
        }
        System.out.println(V);
        System.out.println(P);
        Set<Integer> uniqueP = new HashSet<>(P);
        // TODO: find a way to include the count of vehicules to use extra vehicule cost
        int nbExtraVRequired = Math.max(uniqueP.size() - this.instance.getNbVehicules(), 0);
        double realCost = V.get(V.size() - 1) + nbExtraVRequired * this.instance.getCoutVehicule();
        System.out.println("Cost: " + realCost + " (" + nbExtraVRequired + " extra vehicules required)");

        return P;
    }

    private void instanceToChromosome() {
        if (this.instance.getPlanningCurrent().getVehicules().size() > 0) {
            this.chromosome = new LinkedList<>();
            Planning p = this.instance.getPlanningCurrent();
            this.depot = this.instance.getDepot();
            List<Vehicule> vehicules = p.getVehicules();
            for (Vehicule vehicule : vehicules) {
                List<Emplacement> ems = vehicule.getEmplacements();
                this.chromosome.addAll(ems);
            }
        } else { // No planning to optimise
            this.chromosome = new LinkedList<>();
            this.depot = this.instance.getDepot();
            for (Client cli : this.instance.getClients()) {
                this.chromosome.add(cli.getEmplacements().get(0));
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
            System.out.println("---Cout ns: " + i.getPlanningCurrent().getCout());
            ShortestPathEmplacements sp = new ShortestPathEmplacements(i);
            sp.solve();
            System.out.println("---Cout sp: " + i.getPlanningCurrent().getCout());
            try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }
        }
    }

    @Override
    public String toString() {
        return "ShortestPathEmplacements{" + "chromosome=" + chromosome + '}';
    }
}
