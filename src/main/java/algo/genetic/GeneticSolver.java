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
import algo.iterative.CvCostCapacitySortedSolver;
import algo.iterative.NaiveSolver;
import io.input.InstanceFileParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Instance;
import model.Vehicule;

/**
 * GeneticSolver class The solver is inspired by this research paper:
 * http://citeseerx.ist.psu.edu/viewdoc/download;jsessionid=9CC8F10669C22FB1E97F168D3B770E30?doi=10.1.1.359.9152&rep=rep1&type=pdf&fbclid=IwAR3XEHdHNzlu8G14-ZEWgYUmZNcEA6mICU8VlqTq5DJD4hu6qiw0Sqhr9Nc
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

    /**
     * The pool of chromosome to do some genetic on
     */
    private List<Chromosome> chromosomePool;

    /**
     * The minimum cost difference to make two chromosomes not equals
     */
    private int minCostSpace;

    /**
     * The maximum number of general iterations
     */
    private int maxIterations;

    /**
     * The maximal number of iterations without improving the best solution
     */
    private int maxIterationsWithoutImproving;

    /**
     * The mutation rate (between 0 and 1) The bigger the more aggressive the
     * genetic algo
     */
    private double mutationRate;

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
     * @param mutationRate the mutation rate
     */
    public GeneticSolver(int minCostSpace, int maxIterations, int maxIterationsWithoutImproving, double mutationRate) {
        this(null, minCostSpace, maxIterations, maxIterationsWithoutImproving, mutationRate);
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
     * @param mutationRate the mutation rate
     */
    public GeneticSolver(Instance i, int minCostSpace, int maxIterations, int maxIterationsWithoutImproving, double mutationRate) {
        this.instance = i;
        this.minCostSpace = minCostSpace;
        this.maxIterations = maxIterations;
        this.maxIterationsWithoutImproving = maxIterationsWithoutImproving;
        this.mutationRate = mutationRate;
    }

    /**
     * ShortestPathSolver constructor, with an Instance.
     *
     * @param i Instance to solve
     * @param maxIterations the maximum of iterations
     * @param maxIterationsWithoutImproving the maximum of iterations without
     * improvement of the best solution
     * @param mutationRate the mutation rate
     */
    public GeneticSolver(Instance i, int maxIterations, int maxIterationsWithoutImproving, double mutationRate) {
        this(i, 1, maxIterations, maxIterationsWithoutImproving, mutationRate);
    }

    @Override
    public Instance getInstance() {
        return this.instance;
    }

    @Override
    public void setInstance(Instance i) {
        this.instance = i;
    }

    @Override
    public boolean solve() {
        LOGGER.log(Level.FINE, "Solving a new instance");

        try {
            this.geneticSolve();
            this.sortChromosomePool();
            Chromosome bestChromosome = this.chromosomePool.get(0);
            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int nbV = this.instance.getNbVehicules();

            List<Trip> tournee = new ArrayList<>(bestChromosome.getTournee().getTrips());
            Collections.reverse(tournee);
            Vehicule v;
            for (int i = 0; i < tournee.size(); i++) {
                Trip trip = tournee.get(i);
                if (i >= nbV) {
                    LOGGER.log(Level.INFO, "Ajout d'un extra vehicule");
                    v = this.instance.addVehicule();
                } else {
                    v = vehicules.get(i);
                }
                for(int j = 1; j < trip.getEmplacements().size(); j++) {
                        if (!v.addEmplacement(trip.getEmplacements().get(j))) {
                            LOGGER.log(Level.WARNING,
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                            throw new SolverException(
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                        }
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
     * Solve the current instance using a genetic algorithm.
     *
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private void geneticSolve() throws SolverException {
        int iterations = 0;
        int iterationsWithoutImprovement = 0;
        this.generateChromosomePool();
        while (iterations < this.maxIterations && iterationsWithoutImprovement < this.maxIterationsWithoutImproving) {
            iterations++;
            Chromosome parent1 = this.getRandomChromosome();
            Chromosome parent2 = this.getRandomChromosome();
            //Ensure that parent2 and parent1 are not the same
            while (parent1.equals(parent2)) {
                parent2 = this.getRandomChromosome();
            }
            Random r = new Random();
            //Do crossover on a random parent order
            Chromosome child;
            if (r.nextBoolean()) {
                child = this.doCrossover(parent1, parent2);
            } else {
                child = this.doCrossover(parent2, parent1);
            }
            //Will the child mutate?
            if (r.nextFloat() <= this.mutationRate) {
                //Do mutation
                System.out.println("Mutation occurs");
            }
            if (!this.isChromosomePoolDuplicates(child)) {
                iterationsWithoutImprovement = 0;
                this.chromosomePool.add(child);
                this.sortChromosomePool();
            } else {
                iterationsWithoutImprovement++;
            }
        }
    }

    /**
     * Function that generates a pool of chromosome using the other solvers
     *
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
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
        CvCostCapacitySortedSolver ccss = new CvCostCapacitySortedSolver(this.instance);
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

    /**
     * Function that sort the chromosome pool by increasing Tournee cost
     */
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

    /**
     * Function that check if a Chromosome exists in the pool
     *
     * @param c the Chromosome to test
     * @return whether the Chromosome exists in the pool
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private boolean isChromosomePoolDuplicates(Chromosome c) throws SolverException {
        for (Chromosome ch : this.chromosomePool) {
            if (Math.abs(c.getTournee().getCost() - ch.getTournee().getCost()) < this.minCostSpace) {
                return true;
            }
        }
        return false;
    }

    /**
     * Functions that does the crossover of two Chromosome
     *
     * @param p1 the first parent Chromosome
     * @param p2 the second parent Chromosome
     * @return the child Chromosome
     */
    private Chromosome doCrossover(Chromosome p1, Chromosome p2) {
        ///Create a copy of the parent chromosomes
        List<Client> p2Cli = new ArrayList<>(p2.getClients());
        ///Left rotate the copy of the parents
        Collections.rotate(p2Cli, -1);
        ///Create a child from the first parents chromosome
        List<Client> childCli = new ArrayList<>(p1.getClients());
        ///Get a random range
        Random r = new Random();
        int i = r.nextInt(childCli.size());
        int j = r.nextInt(childCli.size());
        //System.out.println("Range from " + Math.min(i, j) + " to " + Math.max(i, j));
        //Make of the copy of the randomly selected range
        List<Client> p1Range = p1.getClients().subList(Math.min(i, j), Math.max(i, j) + 1);
        //Fill the chromosome with the remaining clients
        int l = ((Math.max(i, j) + 1) % childCli.size());
        for (int k = ((Math.max(i, j) + 1) % childCli.size()); k != (Math.min(i, j) % childCli.size()); k = (k + 1) % childCli.size()) {
            while (p1Range.contains(p2Cli.get(l))) {
                l = (l + 1) % childCli.size();
            }
            //System.out.println("---> k=" + k + "  l=" + l);
            childCli.set(k, p2Cli.get(l));
            l = (l + 1) % childCli.size();
        }
        /*System.out.println("-----------");
        System.out.println(p1.getClients());
        System.out.println(p2Cli);
        System.out.println(childCli);
        System.out.println("-----------");*/
        return new Chromosome(p1.getInstance(), childCli);
    }

    /**
     * Functions that returns a random Chromosome from the pool
     *
     * @return a random Chromosome
     */
    public Chromosome getRandomChromosome() {
        Random rand = new Random();
        return this.chromosomePool.get(rand.nextInt(this.chromosomePool.size()));
    }

    /**
     * Main funtion (for dev purposes only)
     *
     * @param args the args
     */
    public static void main(String[] args) {
        Instance i = null;
        //for (int j = 0; j < 40; j++) {
        int id = 30;
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
        GeneticSolver gs = new GeneticSolver(i, 2, 5000, 2500, 0.0);
        gs.solve();
        double cgs = i.getPlanningCurrent().getCout();
        System.out.println("---Cout gs: " + cgs + " ( " + (cgs - cns) + " )");
        try {
            System.out.println(gs.chromosomePool.get(gs.chromosomePool.size() - 1).getTournee().getCost());
        } catch (SolverException e) {
            e.printStackTrace();
        }
        System.out.println(gs.chromosomePool.size());
        /*try {
        SolutionWriter sw = new SolutionWriter();
        sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
        } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
        }*/
        //}
    }
}
