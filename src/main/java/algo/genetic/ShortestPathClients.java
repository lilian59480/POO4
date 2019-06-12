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

import algo.SolverException;
import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * ShortestPathClients class
 *
 * @author Thomas
 */
public class ShortestPathClients {

    /**
     * Current instance.
     */

    private Instance instance;
    /**
     * The chromosome (aka list of clients)
     */
    private Chromosome chromosome;

    /**
     * ShortestPathClients constructor, without an Instance.
     * <p>
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
        this.chromosome = new Chromosome(i);
    }

    /**
     * ShortestPathClients constructor, with an Instance and a chromosome
     *
     * @param i          Instance to solve
     * @param chromosome the chromosome
     */
    public ShortestPathClients(Instance i, Chromosome chromosome) {
        this.instance = i;
        this.chromosome = chromosome;
    }

    /**
     * Function to ind the best Tournee using different percent
     *
     * @return the best Tournee
     * @throws SolverException SolverException If there is an internal exception
     *                         or inconsistant values.
     */
    public Tournee findBestTournee() throws SolverException {
        Tournee bestTournee = this.getBestTournee(this.findShortestPath(1), this.findShortestPath(1.5));
        bestTournee = this.getBestTournee(bestTournee, this.findShortestPath(2));
        bestTournee = this.getBestTournee(bestTournee, this.findShortestPath(2.5));

        return this.getBestTournee(bestTournee, this.findShortestPath(10));
    }
    
    /**
     * Find the best tournee of the two input tournee
     * @param tournee1 the first tournee
     * @param tournee2 the second tournee
     * @return the best tournee of the two
     */
    public Tournee getBestTournee(Tournee tournee1, Tournee tournee2) {
        if (tournee1.getCost() < tournee2.getCost()) {
            return tournee1;
        }
        return tournee2;
    }

    /**
     * Funcion that calculates the shortest path in the order of clients given
     * by the chromosome
     *
     * @param percent a number to ajustate the precision
     * @return The tournee of the shortests path
     * @throws SolverException If there is an internal exception or inconsistant
     *                         values.
     */
    private Tournee findShortestPath(double percent) throws SolverException {
        List<BestLabel> listBestLabel = new ArrayList<>();
        for (int i = 0; i < this.chromosome.getClients().size(); i++) {
            Chromosome partialChromosome = new Chromosome();
            for (int j = i; j < this.chromosome.getClients().size(); j++) {
                Client client = this.chromosome.getClients().get(j);
                partialChromosome.getClients().add(client);
                Label label = this.findPartialShortestPath(partialChromosome);
                if (label != null) {
                    if (listBestLabel.size() >= j + 1) {
                        BestLabel newLabel = new BestLabel(label, listBestLabel.get(i - 1));
                        if (this.shouldReplaceCurrentLabel(listBestLabel.get(j), newLabel, percent)) {
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

        return new Tournee(listBestLabel.get(listBestLabel.size() - 1), this.instance.getNbVehicules(), this.instance.getCoutVehicule());
    }

    /**
     * Function that checks whether we should replace the current BestLabel
     *
     * @param currentLabel the current label
     * @param newLabel     the new label
     * @param percent      a number to ajustate the precision
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
        return (newLabel.getCost() * percent) < currentLabel.getCost();
    }

    /**
     * Funcion that tries to find the shortest path going throught every clients
     * if that path exists
     *
     * @param partialChromosome the partial chromosome
     * @return A label if there is path path, null otherwise
     */
    private Label findPartialShortestPath(Chromosome partialChromosome) {
        Depot depot = this.instance.getDepot();
        int closeTime = depot.getHeureFin();
        List<ClientLabels> labelsEC = new ArrayList<>();
        for (int i = 0; i < partialChromosome.getClients().size(); i++) {
            Client client = partialChromosome.getClients().get(i);
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
                    this.extendsPreviousLabels(em, labelsEC.get(i - 1), labelsEC.get(i));
                }
            }
        }
        return this.getBestLabelFromClientLabels(labelsEC.get(labelsEC.size() - 1));
    }

    /**
     * Functions that extends the previous labels
     *
     * @param em                   the emplacement you want to extends labels to
     * @param previousClientLabels the previous ClientLabels (where there are
     *                             the labels to extends)
     * @param clientLabels         the ClientLabels in which you want to put the
     *                             extended labels
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
     * Function that retrieve the best Label from a ClientLabels
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

}
