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
package algo.genetic;

import algo.ISolver;
import algo.SolverException;
import algo.iterative.NaiveSolver;
import io.input.InstanceFileParser;
import model.*;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Thomas
 */
public class ShortestPathClients2 implements ISolver {

    private Instance instance;
    private static final Logger LOGGER = Logger.getLogger(ShortestPathClients2.class.getName());
    private List<Client> chromosome;
    private Depot depot;

    public ShortestPathClients2() {
        this(null);
    }

    public ShortestPathClients2(Instance i) {
        this.instance = i;
        this.instanceToChromosome();
    }

    public ShortestPathClients2(Instance i, List<Client> chromosome) {
        this.instance = i;
        this.chromosome = new ArrayList<>(chromosome);
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
        /*try {
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
        }*/

        // Check for instance validity
        return this.instance.check();
    }

    private List<List<Integer>> findShortestPath() throws SolverException {
        List<List<Map<String, Object>>> V = new LinkedList<>(); // list des couts & temps les + faible pour chaque emplacement de chaque clients
        List<List<Integer>> P = new LinkedList<>(); // list des points precedant, P[i] => point avant i pour chaque emplacement de chaque clients
        List<ClientLabels> labelsEC = this.labelChromosome();


        return P;
    }

    private List<ClientLabels> labelChromosome() throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        int closeTime = this.instance.getDepot().getHeureFin();

        List<ClientLabels> labelsEC = new ArrayList<>();
        System.out.println("");
        System.out.println("");

        for (int i = 0; i < this.chromosome.size(); i++) {
            Client client = this.chromosome.get(i);
            labelsEC.add(new ClientLabels(i));
            for (Emplacement em : client.getEmplacements()) {
                //Add label to for depot
                Route r2dest = em.getRouteTo(this.depot);
                Route r2dep = this.depot.getRouteTo(em);
                if (Math.max(r2dest.getTemps(), em.getHeureDebut()) <= em.getHeureFin()) {
                    int newTime = Math.max(r2dest.getTemps(), em.getHeureDebut()) + r2dep.getTemps();
                    if (newTime <= closeTime) {
                        labelsEC.get(i).addLabelToEmplacement(em, new Label(
                                client.getDemande(),
                                r2dest.getCout() + r2dep.getCout(),
                                newTime,
                                depot
                        ));
                    }
                }

                if (i > 0) { //etend les labels precedents
                    for (Map.Entry<Emplacement, List<Label>> precedantEm : labelsEC.get(i - 1).getEm2Labels().entrySet()) {
                        //System.out.println(i + " " + em.getId()  + " " + precedantEm.getKey().getId());
                        //System.out.println(precedantEm);
                        Route r0 = precedantEm.getKey().getRouteTo(this.depot);
                        Route r1 = precedantEm.getKey().getRouteTo(em);
                        Route r2 = em.getRouteTo(this.depot);
                        for (Label labelPre : precedantEm.getValue()) {
                            int arrivalTime = Math.max((Integer) labelPre.getTime()  - r0.getTemps() + r1.getTemps(), em.getHeureDebut());
                            if (arrivalTime <= em.getHeureFin()) {
                                int newLoad = (Integer) labelPre.getLoad()+ client.getDemande();
                                int newTime = arrivalTime + r2.getTemps();
                                if (newLoad <= capaV && newTime <= closeTime) {
                                    Label newLabel = new Label(
                                            newLoad,
                                            (Double) labelPre.getCost() - r0.getCout() + r1.getCout() + r2.getCout(),
                                            newTime,
                                            labelPre.getPrecedents()
                                    );
                                    newLabel.addPrecedent(precedantEm.getKey());
                                    labelsEC.get(i).addLabelToEmplacement(em, newLabel);
                                }

                            }

                        }

                    }

                }
            }

        }
        System.out.println("");
        System.out.println("");
        System.out.println(labelsEC);

        /*System.out.println(V);
        System.out.println(V.get(V.size() - 1));
        System.out.println(P);
        Set<Integer> uniqueP = new HashSet<>(P);
        // TODO: find a way to include the count of vehicules to use extra vehicule cost
        int nbExtraVRequired = Math.max(uniqueP.size() - this.instance.getNbVehicules(), 0);
        double realCost = V.get(V.size() - 1) + nbExtraVRequired * this.instance.getCoutVehicule();
        System.out.println("Cost: " + realCost + " (" + nbExtraVRequired + " extra vehicules required)");*/
        return labelsEC;
    }

    private void instanceToChromosome() {
        if (this.instance.getPlanningCurrent().getVehicules().size() > 0) {
            this.chromosome = new LinkedList<>();
            Planning p = this.instance.getPlanningCurrent();
            this.depot = this.instance.getDepot();
            for (Vehicule vehicule : p.getVehicules()) {
                List<Emplacement> ems = vehicule.getEmplacements();
                for (Emplacement e : ems) {
                    this.chromosome.add(e.getClient());
                }
            }
        } else { // No planning to optimise
            this.chromosome = new LinkedList<>();
            this.depot = this.instance.getDepot();
            this.chromosome.addAll(this.instance.getClients());

        }
        System.out.println(this.chromosome);
    }

    public static void main(String[] args) {
        Instance i = null;
        //for (int j = 0; j < 40; j++) {
        int id = 1;
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
        ShortestPathClients2 sp = new ShortestPathClients2(i);
        try {
            sp.findShortestPath();
        } catch (SolverException e) {
            e.printStackTrace();
        }
        /*sp.solve();
            System.out.println("---Cout sp: " + i.getPlanningCurrent().getCout());
            try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }*/
        //}
    }

    @Override
    public String toString() {
        return "ShortestPathEmplacements{" + "chromosome=" + chromosome + '}';
    }
}
