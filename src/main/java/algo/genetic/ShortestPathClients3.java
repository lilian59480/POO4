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
public class ShortestPathClients3 implements ISolver {

    private Instance instance;
    private static final Logger LOGGER = Logger.getLogger(ShortestPathClients3.class.getName());
    private List<Client> chromosome;
    private Depot depot;

    public ShortestPathClients3() {
        this(null);
    }

    public ShortestPathClients3(Instance i) {
        this.instance = i;
        this.instanceToChromosome();
    }

    public ShortestPathClients3(Instance i, List<Client> chromosome) {
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
            //List<ClientLabels2> labelsEC = this.labelChromosome();
            System.out.println("");
            System.out.println("");
            findShortestPath();
            Tournee bestTournee = findShortestPath();

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

    private Tournee findShortestPath() throws SolverException {
        List<BestLabel> listBestLabel = new ArrayList<>();

        for (int i = 0; i < this.chromosome.size(); i++) {
            List<Client> partialChromosome = new ArrayList<>();
            for (int j = i; j < this.chromosome.size(); j++) {
                Client client = this.chromosome.get(j);
                partialChromosome.add(client);
                Label label = findPartialShortestPath(partialChromosome);
                if (label != null) {
                    if (listBestLabel.size() >= j + 1) {
                        BestLabel newTournee = new BestLabel(label, listBestLabel.get(i - 1));
                        if (newTournee.getCost() < listBestLabel.get(i - 1).getCost()) {
                            listBestLabel.set(j, newTournee);
                        } else if (newTournee.getCost() == listBestLabel.get(i - 1).getCost() && newTournee.getLabelsPre().size() < listBestLabel.get(i - 1).getLabelsPre().size()) {
                            listBestLabel.set(j, newTournee);
                        }

                    } else {
                        if (i == 0) {
                            listBestLabel.add(new BestLabel(label));
                        } else {
                            listBestLabel.add(new BestLabel(label, listBestLabel.get(i - 1)));
                        }
                    }
                } else {
                    if (i == j) {
                        throw new SolverException(
                                "Error cannot find route between " + client.toString() + " and depot");
                    } else {
                        break;
                    }
                }
            }

        }

        BestLabel bestLabel = listBestLabel.get(listBestLabel.size() - 1);
        Tournee bestTournee = new Tournee();
        for (Label label : bestLabel.getLabelsPre()) {
            bestTournee.addLabel(label);
        }
        bestTournee.addLabel(bestLabel.getLabel());

        return bestTournee;
    }

    private Label findPartialShortestPath(List<Client> partialChromosome) throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        int closeTime = this.instance.getDepot().getHeureFin();
        List<ClientLabels2> labelsEC = new ArrayList<>();

        for (int i = 0; i < partialChromosome.size(); i++) {
            Client client = partialChromosome.get(i);
            labelsEC.add(new ClientLabels2(i));
            for (Emplacement em : client.getEmplacements()) {
                //Add label to for depot
                Route r2dest = em.getRouteTo(this.depot);
                Route r2dep = this.depot.getRouteTo(em);
                if (i == 0) {
                    if (Math.max(r2dest.getTemps(), em.getHeureDebut()) <= em.getHeureFin()) {
                        int newTime = Math.max(r2dest.getTemps(), em.getHeureDebut()) + r2dep.getTemps();
                        if (newTime <= closeTime) {
                            labelsEC.get(i).addLabel(new Label(
                                    client.getDemande(),
                                    r2dest.getCout() + r2dep.getCout(),
                                    newTime,
                                    em,
                                    depot
                            ));
                        }
                    }
                } else if (i > 0) { //etend les labels precedents
                    for (Label labelPre : labelsEC.get(i - 1).getLabels()) {
                        Emplacement precedantEm = labelPre.getEmplacement();
                        Route r0 = precedantEm.getRouteTo(this.depot);
                        Route r1 = precedantEm.getRouteTo(em);
                        Route r2 = em.getRouteTo(this.depot);
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
                                newLabel.addPrecedent(precedantEm);
                                labelsEC.get(i).addLabel(newLabel);
                                labelPre.addSuivant(em);
                            }

                        }

                    }

                }
            }

        }
        ClientLabels2 cl = labelsEC.get(labelsEC.size() - 1);
        //System.out.println("");
        //System.out.println("");
        //System.out.println(cl);

        if (cl.getLabels().size() == 0) {
            return null;
        }
        Label bestLabel = cl.getLabels().get(0);
        for (int i = 1; i < cl.getLabels().size(); i++) {
            Label tempLabel = cl.getLabels().get(i);
            if (bestLabel.getCost() > tempLabel.getCost()) {
                bestLabel = tempLabel;
            } else if (bestLabel.getCost() == tempLabel.getCost() && bestLabel.getTime() > tempLabel.getTime()) {
                bestLabel = tempLabel;
            }
        }
        return bestLabel;
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
            System.out.println(id);
            try {
                InstanceFileParser ifp = new InstanceFileParser();
                i = ifp.parse(new File("src/main/resources/instances/instance_" + id + "-triangle.txt"));
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
                return;
            }
            NaiveSolver ds = new NaiveSolver(i);
            ds.solve();
            double cns = i.getPlanningCurrent().getCout();
            System.out.println("---Cout ns: " + cns);
            ShortestPathClients3 sp = new ShortestPathClients3(i);
            /*try {
                System.out.println("");
                System.out.println("");
                sp.findShortestPath();
            } catch (SolverException e) {
                e.printStackTrace();
            }*/
            sp.solve();
            double csp = i.getPlanningCurrent().getCout();
            System.out.println("---Cout sp: " + csp + " ( " + (csp - cns) + " )");
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
