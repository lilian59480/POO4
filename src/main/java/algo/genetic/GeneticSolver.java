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
import algo.iterative.CVCostCapacitySortedSolver;
import algo.iterative.NaiveSolver;
import io.input.InstanceFileParser;

import model.Instance;
import model.Vehicule;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;

/**
 * ShortestPathSolver class
 *
 * @author Thomas
 */
public class GeneticSolver implements ISolver {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(GeneticSolver.class.getName());

    /**
     * Current instance.
     */
    private Instance instance;

    private List<Chromosome> chromosomePool;

    private int minCostSpace;

    private int maxIterations;

    private int maxIterationsWithoutImproving;

    /**
     * ShortestPathSolver constructor, without an Instance.
     * <p>
     * This constructor is recommended as you can solve multiples instances by
     * using the instance setter.
     *
     * @param minCostSpace the minimum delta for the cost to consider two
     * chromosome equal
     * @param maxIterations the maximum of iterations
     * @param maxIterationsWithoutImproving the maximum of iterations without
     * improvement of the best solution
     */
    public GeneticSolver(int minCostSpace, int maxIterations, int maxIterationsWithoutImproving) {
        this(null, minCostSpace, maxIterations, maxIterationsWithoutImproving);
    }

    /**
     * ShortestPathSolver constructor with an Instance and a minCostSpace
     *
     * @param i Instance to solve
     * @param minCostSpace the minimum delta for the cost to consider two
     * chromosome equal
     * @param maxIterations the maximum of iterations
     * @param maxIterationsWithoutImproving the maximum of iterations without
     * improvement of the best solution
     */
    public GeneticSolver(Instance i, int minCostSpace, int maxIterations, int maxIterationsWithoutImproving) {
        this.instance = i;
        this.minCostSpace = minCostSpace;
        this.maxIterations = maxIterations;
        this.maxIterationsWithoutImproving = maxIterationsWithoutImproving;
    }

    /**
     * ShortestPathSolver constructor, with an Instance.
     *
     * @param i Instance to solve
     * @param maxIterations the maximum of iterations
     * @param maxIterationsWithoutImproving the maximum of iterations without
     * improvement of the best solution
     */
    public GeneticSolver(Instance i, int maxIterations, int maxIterationsWithoutImproving) {
        this(i, 1, maxIterations, maxIterationsWithoutImproving);
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
            Chromosome chromosome = new Chromosome(this.instance);
            chromosome.generateTournee();
            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int nbV = this.instance.getNbVehicules();

            List<Label> tournee = new ArrayList<>(chromosome.getTournee().getTournee());
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

    private void geneticSolve() throws SolverException {
        int iterations = 0;
        int interationsWithoutImprovement = 0;
        double mutationRate = 0.1;
        this.generateChromosomePool();
        while (iterations <= this.maxIterations && interationsWithoutImprovement <= this.maxIterationsWithoutImproving) {
            iterations++;
            //Do crossover

            if (Math.random() <= mutationRate) {
                //Do mutation

            }
            //If the new chromosome has a cost that isn't a duplicate and has a cost in the range of the pool
            //then interationsWithoutImprovement=0 & put the chromosome in the pool

            //Else interationsWithoutImprovement++
        }
    }

    private void generateChromosomePool() throws SolverException {
        this.chromosomePool = new ArrayList<>();
        //Chromosome in the "normal" order
        Chromosome c = new Chromosome(this.instance, this.instance.getClients());
        if (!this.isChromosomePoolDuplicates(c)) {
            this.chromosomePool.add(c);
        }
        //Chromosome in the "reverse" order
        List<Client> reversedChromosome = new ArrayList<>(this.instance.getClients());
        Collections.reverse(reversedChromosome);
        c = new Chromosome(this.instance, reversedChromosome);
        if (!this.isChromosomePoolDuplicates(c)) {
            this.chromosomePool.add(c);
        }
        //Chromosome in the NaiveSolver order
        NaiveSolver ds = new NaiveSolver(this.instance);
        ds.solve();
        c = new Chromosome(this.instance);
        if (!this.isChromosomePoolDuplicates(c)) {
            this.chromosomePool.add(c);
        }
        //Chromosome in the CVCostCapacitySortedSolver order
        CVCostCapacitySortedSolver ccss = new CVCostCapacitySortedSolver(this.instance);
        ccss.solve();
        c = new Chromosome(this.instance);
        if (!this.isChromosomePoolDuplicates(c)) {
            this.chromosomePool.add(c);
        }
        //A bunch of chromosome in a random order
        for (int i = 0; i < 15; i++) {
            Collections.shuffle(reversedChromosome);
            c = new Chromosome(this.instance, reversedChromosome);
            if (!this.isChromosomePoolDuplicates(c)) {
                this.chromosomePool.add(c);
            }
        }
        //Sort the chromosomePool by cost
        this.sortChromosomePool();

        /*for (Chromosome c : this.chromosomePool) {
            try {
                System.out.println("Chromosome: " + c.getTournee().getCost());
            } catch (SolverException ex) {
                Logger.getLogger(GeneticSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

    private void sortChromosomePool() {
        Collections.sort(this.chromosomePool, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                try {
                    return Double.compare(o1.getTournee().getCost(), o2.getTournee().getCost());
                } catch (SolverException ex) {
                    Logger.getLogger(GeneticSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        });
    }

    private boolean isChromosomePoolDuplicates(Chromosome c) throws SolverException {
        for (Chromosome ch : this.chromosomePool) {
            if (Math.abs(c.getTournee().getCost() - ch.getTournee().getCost()) < this.minCostSpace) {
                return true;
            }
        }
        return false;
    }

    /**
     * Main funtion (for dev purposes only)
     *
     * @param args the args
     */
    public static void main(String[] args) {
        Instance i = null;
        //for (int j = 0; j < 40; j++) {
        int id = 1;
        System.out.println(id);
        try {
            InstanceFileParser ifp = new InstanceFileParser();
            i = ifp.parse(new File("src/main/resources/instances/instance_" + id + "-triangle.txt"));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return;
        }
        GeneticSolver gs = new GeneticSolver(i, 500, 100);

        try {
            gs.generateChromosomePool();
            
            /*try {
            SolutionWriter sw = new SolutionWriter();
            sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }*/
            //}
        } catch (SolverException ex) {
            Logger.getLogger(GeneticSolver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
