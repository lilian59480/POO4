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
import algo.iterative.RandomSolver;
import io.input.InstanceFileParser;
import io.output.SolutionWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Planning;
import model.Route;
import model.Vehicule;

/**
 * @author Thomas
 */
public class ShortestPathClients implements ISolver {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ShortestPathClients.class.getName());
    /**
     * Current instance.
     */
    private Instance instance;
    /**
     * The chromosome (aka list of clients)
     */
    private List<Client> chromosome;

    /**
     * ShortestPathClients constructor, without an Instance.
     *
     * This constructor is recommended as you can solve multiples instances by
     * using the instance setter.
     */
    public ShortestPathClients() {
        this(null);
    }

    /**
     * ShortestPathClients constructor, with an Instance.
     *
     * @param i Instance to solve
     */
    public ShortestPathClients(Instance i) {
        this.instance = i;
        this.instanceToChromosome();
    }

    /**
     * ShortestPathClients constructor, with an Instance and a chromosome
     *
     * @param i Instance to solve
     * @param chromosome the chromosome
     */
    public ShortestPathClients(Instance i, List<Client> chromosome) {
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
            Tournee bestTournee = findBestTournee();
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

    /**
     * Function to ind the best Tournee using different percent
     *
     * @return the best Tournee
     * @throws SolverException SolverException If there is an internal exception
     * or inconsistant values.
     */
    private Tournee findBestTournee() throws SolverException {
        Tournee bestTournee = findShortestPath(1);
        Tournee bestTourneeTemp = findShortestPath(1.5);
        if (bestTourneeTemp.getCost() < bestTournee.getCost()) {
            bestTournee = bestTourneeTemp;
        }
        bestTourneeTemp = findShortestPath(2);
        if (bestTourneeTemp.getCost() < bestTournee.getCost()) {
            bestTournee = bestTourneeTemp;
        }
        bestTourneeTemp = findShortestPath(2.5);
        if (bestTourneeTemp.getCost() < bestTournee.getCost()) {
            bestTournee = bestTourneeTemp;
        }
        bestTourneeTemp = findShortestPath(10);
        if (bestTourneeTemp.getCost() < bestTournee.getCost()) {
            bestTournee = bestTourneeTemp;
        }

        return bestTournee;
    }

    /**
     * Funcion that calculates the shortest path in the order of clients given
     * by the chromosome
     *
     * @param percent a number to ajustate the precision
     * @return The tournee of the shortests path
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private Tournee findShortestPath(double percent) throws SolverException {
        List<BestLabel> listBestLabel = new ArrayList<>();
        for (int i = 0; i < this.chromosome.size(); i++) {
            List<Client> partialChromosome = new ArrayList<>();
            for (int j = i; j < this.chromosome.size(); j++) {
                Client client = this.chromosome.get(j);
                partialChromosome.add(client);
                Label label = findPartialShortestPath(partialChromosome);
                if (label != null) {
                    if (listBestLabel.size() >= j + 1) {
                        BestLabel newLabel = new BestLabel(label, listBestLabel.get(i - 1));
                        if (shouldReplaceCurrentLabel(listBestLabel.get(j), newLabel, percent)) {
                            listBestLabel.set(j, newLabel);
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

        return bestLabelToTournee(listBestLabel.get(listBestLabel.size() - 1), this.instance.getNbVehicules(), this.instance.getCoutVehicule());
    }

    /**
     * Function that checks whether we should replace the current BestLabel
     *
     * @param currentLabel the current label
     * @param newLabel the new label
     * @param percent a number to ajustate the precision
     * @return whether we should replace it or not
     */
    private boolean shouldReplaceCurrentLabel(BestLabel currentLabel, BestLabel newLabel, double percent) {
        if (newLabel.getLabelsPre().size() < currentLabel.getLabelsPre().size() && newLabel.getCost() <= (currentLabel.getCost() * percent)) {
            //Should be unreachable
            return true;
        }
        if (newLabel.getLabelsPre().size() == currentLabel.getLabelsPre().size() && newLabel.getCost() < currentLabel.getCost()) {
            return true;
        }
        if ((newLabel.getCost() * percent) < currentLabel.getCost()) {
            return true;
        }
        return false;
    }

    /**
     * Function that convert a BestLabel into a Tournee
     *
     * @param bestLabel the BestLabel to convert
     * @param nbVehicule the number of vehicule of the tournee
     * @param costExtraVehicle the cost for extra vehicules
     * @return the Tournee
     */
    private Tournee bestLabelToTournee(BestLabel bestLabel, int nbVehicule, double costExtraVehicle) {
        Tournee bestTournee = new Tournee(nbVehicule, costExtraVehicle);
        bestTournee.addLabels(bestLabel.getLabelsPre());
        bestTournee.addLabel(bestLabel.getLabel());

        return bestTournee;
    }

    /**
     * Funcion that tries to find the shortest path going throught every clients
     * if that path exists
     *
     * @param partialChromosome the partial chromosome
     * @return A label if there is path path, null otherwise
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private Label findPartialShortestPath(List<Client> partialChromosome) throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        Depot depot = this.instance.getDepot();
        int closeTime = depot.getHeureFin();
        List<ClientLabels> labelsEC = new ArrayList<>();
        for (int i = 0; i < partialChromosome.size(); i++) {
            Client client = partialChromosome.get(i);
            labelsEC.add(new ClientLabels(i));
            for (Emplacement em : client.getEmplacements()) {
                //Add label to for depot
                Route r2dest = em.getRouteTo(depot);
                Route r2dep = depot.getRouteTo(em);
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
                    extendsPreviousLabels(em, labelsEC.get(i - 1), labelsEC.get(i));
                }
            }
        }
        return getBestLabelFromClientLabels(labelsEC.get(labelsEC.size() - 1));
    }

    /**
     * Functions that extends the previous labels
     *
     * @param em the emplacement you want to extends labels to
     * @param previousClientLabels the previous ClientLabels (where there are
     * the labels to extends)
     * @param clientLabels the ClientLabels in which you want to put the
     * extended labels
     */
    private void extendsPreviousLabels(Emplacement em, ClientLabels previousClientLabels, ClientLabels clientLabels) {
        int capaV = this.instance.getCapaciteVehicule();
        Depot depot = this.instance.getDepot();
        int closeTime = depot.getHeureFin();
        for (Label labelPre : previousClientLabels.getLabels()) {
            Emplacement precedantEm = labelPre.getEmplacement();
            Route r0 = precedantEm.getRouteTo(depot);
            Route r1 = precedantEm.getRouteTo(em);
            Route r2 = em.getRouteTo(depot);
            int arrivalTime = Math.max(labelPre.getTime() - r0.getTemps() + r1.getTemps(), em.getHeureDebut());
            if (arrivalTime <= em.getHeureFin()) {
                int newLoad = labelPre.getLoad() + em.getClient().getDemande();
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
                    clientLabels.addLabel(newLabel);
                    labelPre.addSuivant(em);
                }
            }
        }
    }

    /**
     * Function that retreive the best Label from a ClientLabels
     *
     * @param cl the ClientLabels
     * @return the best Label
     */
    private Label getBestLabelFromClientLabels(ClientLabels cl) {
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

    /**
     * Function that convert the instance into a chromosome
     */
    private void instanceToChromosome() {
        if (this.instance.getPlanningCurrent().getVehicules().size() > 0) {
            this.chromosome = new ArrayList<>();
            Planning p = this.instance.getPlanningCurrent();
            for (Vehicule vehicule : p.getVehicules()) {
                List<Emplacement> ems = vehicule.getEmplacements();
                for (Emplacement e : ems) {
                    this.chromosome.add(e.getClient());
                }
            }
        } else { // No planning to optimise
            this.chromosome = new ArrayList<>();
            this.chromosome.addAll(this.instance.getClients());

        }
        System.out.println(this.chromosome);
    }

    /**
     * Main funtion (for dev purposes only)
     *
     * @param args the args
     */
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
            ShortestPathClients sp = new ShortestPathClients(i);
            sp.solve();
            double csp = i.getPlanningCurrent().getCout();
            System.out.println("---Cout sp: " + csp + " ( " + (csp - cns) + " )");
            RandomSolver rs = new RandomSolver(i);
            rs.solve();
            double crs = i.getPlanningCurrent().getCout();
            System.out.println("---Cout rs: " + crs);
            sp = new ShortestPathClients(i);
            sp.solve();
            csp = i.getPlanningCurrent().getCout();
            System.out.println("---Cout sp: " + csp + " ( " + (csp - crs) + " )");
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
