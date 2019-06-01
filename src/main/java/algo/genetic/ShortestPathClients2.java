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
import io.output.SolutionWriter;
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

        try {
            List<ClientLabels> labelsEC = this.labelChromosome();

            Tournee bestTournee = findShortestPath(this.chromosome.size() - 1, labelsEC, new Tournee());

            System.out.println("");
            System.out.println("");
            System.out.println(bestTournee.toString());
            System.out.println("");
            int nbExtraVRequired = Math.max(bestTournee.getTournee().size() - this.instance.getNbVehicules(), 0);
            double realCost = bestTournee.getCost() + nbExtraVRequired * this.instance.getCoutVehicule();
            System.out.println("Cost: " + realCost + " (" + nbExtraVRequired + " extra vehicules required)");

            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int nbV = this.instance.getNbVehicules();

            List<Label> tournee = new ArrayList<>(bestTournee.getTournee());
            Collections.reverse(tournee);
            Vehicule v;
            for (int i = 0; i < tournee.size(); i++) {
                Label label = tournee.get(i);
                if (i >= nbV) {
                    LOGGER.log(Level.INFO, "Ajout d'un extra vehicule");
                    v = this.instance.addVehicule();
                } else {
                    v = vehicules.get(i);
                }
                if (label.getPrecedents().size() > 1) {
                    for (int j = 1; j < label.getPrecedents().size(); j++) {
                        if (!v.addEmplacement(label.getPrecedents().get(j))) {
                            LOGGER.log(Level.WARNING,
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                            throw new SolverException(
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                        }
                    }
                }
                if (!v.addEmplacement(label.getEmplacement())) {
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

    private Tournee findShortestPath(int posCli, List<ClientLabels> labelsEC, Tournee parentTournee) {
        ClientLabels clientLabels = labelsEC.get(posCli);
        List<Tournee> myTournees = new ArrayList<>();
        for (Map.Entry<Emplacement, List<Label>> labels : clientLabels.getEm2Labels().entrySet()) {
            for (Label label : labels.getValue()) {
                Tournee newTournee = new Tournee(label, parentTournee);
                if (posCli - label.getPrecedents().size() >= 0) {
                    int newPos = posCli - label.getPrecedents().size();
                    myTournees.add(findShortestPath(newPos, labelsEC, newTournee));
                } else {
                    myTournees.add(newTournee);
                }
            }
        }

        //Find best tournee of myTournees to be returned
        Tournee bestTournee = null;
        for (Tournee tournee : myTournees) {
            if (bestTournee == null) bestTournee = tournee;
            if (tournee.getCost() < bestTournee.getCost()) bestTournee = tournee;
            if (tournee.getCost() == bestTournee.getCost()
                    && tournee.getTournee().size() < bestTournee.getTournee().size()) {
                bestTournee = tournee;
            }
        }
        return bestTournee;
    }

    private List<ClientLabels> labelChromosome() throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        int closeTime = this.instance.getDepot().getHeureFin();
        List<Emplacement> suivantDepot = new ArrayList<>();
        List<ClientLabels> labelsEC = new ArrayList<>();

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
                                em,
                                depot
                        ));
                        suivantDepot.add(em);
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
                            int arrivalTime = Math.max(labelPre.getTime() - r0.getTemps() + r1.getTemps(), em.getHeureDebut());
                            if (arrivalTime <= em.getHeureFin()) {
                                int newLoad = labelPre.getLoad() + client.getDemande();
                                int newTime = arrivalTime + r2.getTemps();
                                if (newLoad <= capaV && newTime <= closeTime) {
                                    Label newLabel = new Label(
                                            newLoad,
                                            labelPre.getCost() - r0.getCout() + r1.getCout() + r2.getCout(),
                                            newTime,
                                            em,
                                            labelPre.getPrecedents()
                                    );
                                    newLabel.addPrecedent(precedantEm.getKey());
                                    labelsEC.get(i).addLabelToEmplacement(em, newLabel);
                                    labelPre.addSuivant(em);
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
        for (int j = 0; j < 40; j++) {
            int id = j;
            System.out.println(j);
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
        /*try {
            sp.findShortestPath();
        } catch (SolverException e) {
            e.printStackTrace();
        }*/
            sp.solve();
            System.out.println("---Cout sp: " + i.getPlanningCurrent().getCout());
            /*try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }*/
        }
    }

    @Override
    public String toString() {
        return "ShortestPathEmplacements{" + "chromosome=" + chromosome + '}';
    }
}
