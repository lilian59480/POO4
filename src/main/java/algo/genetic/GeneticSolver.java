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
import model.Depot;
import model.Emplacement;
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
                for (int j = 1; j < trip.getEmplacements().size(); j++) {
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
                child = mutateChromosome(child);
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
     * Functions that mutates a Chromosome
     *
     * @param c the chromosome to mutate
     * @return the mutated chromosome
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private Chromosome mutateChromosome(Chromosome c) throws SolverException {
        List<List<Object>> trips = chromosomeToTrips(c);
        Random r = new Random();
        //Take a random pair of vertexes (u, v) instead of every possible distinct pair
        int uTrip = r.nextInt(trips.size());
        int uNode = r.nextInt(trips.get(uTrip).size());
        int vTrip = r.nextInt(trips.size());
        int vNode = r.nextInt(trips.get(vTrip).size());
        //System.out.println("C " + c);
        //System.out.println("U " + trips.get(uTrip).get(uNode));
        //System.out.println("V " + trips.get(vTrip).get(vNode));
        Chromosome mutatedC = doMutationMoves(trips, uTrip, uNode, vTrip, vNode);
        if (mutatedC == null) {
            return c;
        }
        return mutatedC;
    }

    /**
     * Do the mutation moves to a list of trips with a given pair of nodes
     *
     * @param trips the list of trips
     * @param uTrip the trip number of u
     * @param uNode the node number of u
     * @param vTrip the trip number of v
     * @param vNode the node number of v
     * @return The mutated chromosome
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private Chromosome doMutationMoves(List<List<Object>> trips, int uTrip, int uNode, int vTrip, int vNode) throws SolverException {
        if (trips.get(uTrip).get(uNode).getClass() == Client.class) {
            //Move 1
            Chromosome bestC = mutationMove1(trips, uTrip, uNode, vTrip, vNode);
            if (trips.get(uTrip).get((uNode + 1) % trips.get(uTrip).size()).getClass() == Client.class) {
                //Move 2
                bestC = getBestChromosome(bestC, mutationMove23(true, trips, uTrip, uNode, vTrip, vNode));
                //Move 3
                bestC = getBestChromosome(bestC, mutationMove23(false, trips, uTrip, uNode, vTrip, vNode));
            }
            if (trips.get(vTrip).get(vNode).getClass() == Client.class) {
                //Move 4  
                bestC = getBestChromosome(bestC, mutationMovePermuteTwoClient(trips, uTrip, uNode, vTrip, vNode));
                if (trips.get(uTrip).get((uNode + 1) % trips.get(uTrip).size()).getClass() == Client.class) {
                    //Move 5
                    bestC = getBestChromosome(bestC, mutationMove5(trips, uTrip, uNode, vTrip, vNode));
                    if (trips.get(vTrip).get((vNode + 1) % trips.get(vTrip).size()).getClass() == Client.class) {
                        //Move 6
                        bestC = getBestChromosome(bestC, mutationMove6(trips, uTrip, uNode, vTrip, vNode));
                    }
                }

            }
            return bestC;
        }
        return null;
    }

    /**
     * Move 1 of the mutation process: if u is a client node, move u after v
     *
     * @param trips the list of trips
     * @param uTrip the trip number of u
     * @param uNode the node number of u
     * @param vTrip the trip number of v
     * @param vNode the node number of v
     * @return the chromosome corresponding to the mutation if it occurred, null
     * otherwise
     */
    private Chromosome mutationMove1(List<List<Object>> trips, int uTrip, int uNode, int vTrip, int vNode) {
        List<List<Object>> tripsTemps = new ArrayList<>();
        for (List<Object> lo : trips) {
            tripsTemps.add(new ArrayList<>(lo));
        }
        if (uTrip != vTrip || uNode > vNode) {
            Client u = (Client) tripsTemps.get(uTrip).remove(uNode);
            tripsTemps.get(vTrip).add((vNode + 1) % tripsTemps.get(vTrip).size(), u);
        } else if (uNode < vNode) {
            Client u = (Client) tripsTemps.get(uTrip).get(uNode);
            tripsTemps.get(vTrip).add((vNode + 1) % tripsTemps.get(vTrip).size(), u);
            tripsTemps.get(uTrip).remove(uNode);
        } else {
            return null;
        }

        return tripsToChromosome(tripsTemps);
    }

    /**
     * Move 2 of the mutation process: if u and x are client nodes, move (u, x)
     * after v Move 3 of the mutation process: if u and x are client nodes, move
     * (x, u) after v
     *
     * @param is2 whether you want to do move 2 or 3 (because move 2 and 3 are
     * similar)
     * @param trips the list of trips
     * @param uTrip the trip number of u
     * @param uNode the node number of u
     * @param vTrip the trip number of v
     * @param vNode the node number of v
     * @return the chromosome corresponding to the mutation if it occurred, null
     * otherwise
     */
    private Chromosome mutationMove23(boolean is2, List<List<Object>> trips, int uTrip, int uNode, int vTrip, int vNode) {
        List<List<Object>> tripsTemps = new ArrayList<>();
        for (List<Object> lo : trips) {
            tripsTemps.add(new ArrayList<>(lo));
        }
        if (uTrip != vTrip || uNode > vNode) {
            List<Client> ux = new ArrayList<>();
            ux.add((Client) tripsTemps.get(uTrip).remove(uNode + 1));
            if (is2) {
                ux.add(0, (Client) tripsTemps.get(uTrip).remove(uNode));
            } else {
                ux.add((Client) tripsTemps.get(uTrip).remove(uNode));
            }
            tripsTemps.get(vTrip).addAll((vNode + 1) % tripsTemps.get(vTrip).size(), ux);
        } else if (uNode < vNode && uNode + 1 != vNode) {
            List<Client> ux = new ArrayList<>();
            ux.add((Client) tripsTemps.get(uTrip).get(uNode + 1));
            if (is2) {
                ux.add(0, (Client) tripsTemps.get(uTrip).get(uNode));
            } else {
                ux.add((Client) tripsTemps.get(uTrip).get(uNode));
            }
            tripsTemps.get(vTrip).addAll((vNode + 1) % tripsTemps.get(vTrip).size(), ux);
            tripsTemps.get(uTrip).remove(uNode + 1);
            tripsTemps.get(uTrip).remove(uNode);
        } else {
            return null;
        }

        return tripsToChromosome(tripsTemps);
    }

    /**
     * Move of the mutation process to permute two clients (used in moves 4, 7,
     * 8, 9). Move 4: if u and v are client nodes, permute u and v. Move 7: if
     * (u, x) and (v, y) are non adjacent in the same trip, replace them by (u,
     * v) and (x, y). Move 8: if (u, x) and (v, y) are in distinct trips,
     * replace them by (u, v) and (x, y). Move 9: if (u, x) and (v, y) are in
     * distinct trips, replace them by (u, y) and (x, v)
     *
     *
     * @param trips the list of trips
     * @param cli1Trip the trip number of cli1
     * @param cli1Node the node number of cli1
     * @param cli2Trip the trip number of cli2
     * @param cli2Node the node number of cli2
     * @return the chromosome corresponding to the mutation if it occurred, null
     * otherwise
     */
    private Chromosome mutationMovePermuteTwoClient(List<List<Object>> trips, int cli1Trip, int cli1Node, int cli2Trip, int cli2Node) {
        List<List<Object>> tripsTemps = new ArrayList<>();
        for (List<Object> lo : trips) {
            tripsTemps.add(new ArrayList<>(lo));
        }
        Client temp = (Client) tripsTemps.get(cli1Trip).get(cli1Node);
        tripsTemps.get(cli1Trip).set(cli1Node, tripsTemps.get(cli2Trip).get(cli2Node));
        tripsTemps.get(cli2Trip).set(cli2Node, temp);

        return tripsToChromosome(tripsTemps);
    }

    /**
     * Move 5 of the mutation process: if u, x and v are clients nodes, permute
     * (u, x) with v
     *
     * @param trips the list of trips
     * @param uTrip the trip number of u
     * @param uNode the node number of u
     * @param vTrip the trip number of v
     * @param vNode the node number of v
     * @return the chromosome corresponding to the mutation if it occurred, null
     * otherwise
     */
    private Chromosome mutationMove5(List<List<Object>> trips, int uTrip, int uNode, int vTrip, int vNode) {
        List<List<Object>> tripsTemps = new ArrayList<>();
        for (List<Object> lo : trips) {
            tripsTemps.add(new ArrayList<>(lo));
        }
        Client u = (Client) tripsTemps.get(uTrip).get(uNode);
        tripsTemps.get(uTrip).set(uNode, tripsTemps.get(vTrip).get(vNode));
        tripsTemps.get(vTrip).set(vNode, u);
        if (uTrip != vTrip || uNode + 1 > vNode) {
            Client x = (Client) tripsTemps.get(uTrip).remove(uNode + 1);
            tripsTemps.get(vTrip).add((vNode + 1) % tripsTemps.get(vTrip).size(), x);
        } else if (uNode + 1 < vNode) {
            Client x = (Client) tripsTemps.get(uTrip).get(uNode + 1);
            tripsTemps.get(vTrip).add((vNode + 1) % tripsTemps.get(vTrip).size(), x);
            tripsTemps.get(uTrip).remove(uNode + 1);
        } else {
            return null;
        }

        return tripsToChromosome(tripsTemps);
    }

    /**
     * Move 6 of the mutation process: if (u, x) and (v, y) are client nodes,
     * permute (u, x) and (v, y)
     *
     * @param trips the list of trips
     * @param uTrip the trip number of u
     * @param uNode the node number of u
     * @param vTrip the trip number of v
     * @param vNode the node number of v
     * @return the chromosome corresponding to the mutation if it occurred, null
     * otherwise
     */
    private Chromosome mutationMove6(List<List<Object>> trips, int uTrip, int uNode, int vTrip, int vNode) {
        List<List<Object>> tripsTemps = new ArrayList<>();
        for (List<Object> lo : trips) {
            tripsTemps.add(new ArrayList<>(lo));
        }
        //Swap u & v
        Client temp = (Client) tripsTemps.get(uTrip).get(uNode);
        tripsTemps.get(uTrip).set(uNode, tripsTemps.get(vTrip).get(vNode));
        tripsTemps.get(vTrip).set(vNode, temp);
        //Swap x & y
        temp = (Client) tripsTemps.get(uTrip).get(uNode+1);
        tripsTemps.get(uTrip).set(uNode+1, tripsTemps.get(vTrip).get(vNode+1));
        tripsTemps.get(vTrip).set(vNode+1, temp);

        return tripsToChromosome(tripsTemps);
    }

    /**
     * Compare two Chromosome and return the best
     *
     * @param c1 the first Chromosome
     * @param c2 the second Chromosome
     * @return the best Chromosome
     * @throws SolverException SolverException If there is an internal exception
     * or inconsistant values.
     */
    private Chromosome getBestChromosome(Chromosome c1, Chromosome c2) throws SolverException {
        if (c2 != null && (c1 == null || c2.getTournee().getCost() < c1.getTournee().getCost())) {
            return c2;
        }
        return c1;
    }

    /**
     * Convert trips to a Chromosome
     *
     * @param trips the trips to convert
     * @return the chromosome
     */
    private Chromosome tripsToChromosome(List<List<Object>> trips) {
        List<Client> clients = new ArrayList<>();
        for (List<Object> lo : trips) {
            for (Object o : lo) {
                if (o.getClass() == Client.class) {
                    clients.add((Client) o);
                }
            }
        }
        Chromosome c = new Chromosome(this.instance, clients);

        return c;
    }

    /**
     * Convert a Chromosome to a trips
     *
     * @param c the Chromosome to convert
     * @return the trips
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private List<List<Object>> chromosomeToTrips(Chromosome c) throws SolverException {
        List<List<Object>> trips = new ArrayList<>();
        for (Trip trip : c.getTournee().getTrips()) {
            List<Object> temp = new ArrayList<>();
            for (Emplacement em : trip.getEmplacements()) {
                if (em.getClass() == Depot.class) {
                    temp.add(em);
                } else {
                    temp.add(em.getClient());
                }
            }
            trips.add(temp);
        }

        return trips;
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
        //Create a copy of the parent chromosomes
        List<Client> p2Cli = new ArrayList<>(p2.getClients());
        //Left rotate the copy of the parents
        Collections.rotate(p2Cli, -1);
        //Create a child from the first parents chromosome
        List<Client> childCli = new ArrayList<>(p1.getClients());
        //Get a random range
        Random r = new Random();
        int i = r.nextInt(childCli.size());
        int j = r.nextInt(childCli.size());
        //System.out.println("Range from " + Math.min(i, j) + " to " + Math.max(i, j));
        //Make of the copy of the randomly selected range
        List<Client> p1Range = p1.getClients().subList(Math.min(i, j), Math.max(i, j) + 1);
        //Fill the chromosome with the remaining clients
        int l = (Math.max(i, j) + 1) % p2Cli.size();
        for (int k = ((Math.max(i, j) + 1) % childCli.size()); k != (Math.min(i, j) % childCli.size()); k = (k + 1) % childCli.size()) {
            while (p1Range.contains(p2Cli.get(l))) {
                l = (l + 1) % p2Cli.size();
            }
            //System.out.println("---> k=" + k + " l=" + l);
            childCli.set(k, p2Cli.get(l));
            l = (l + 1) % p2Cli.size();
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
        /*GeneticSolver gs = new GeneticSolver(i, 2, 5000, 2500, 0.0);
        try {
            gs.generateChromosomePool();
            gs.mutateChromosome(gs.chromosomePool.get(0));

        } catch (SolverException ex) {
            Logger.getLogger(GeneticSolver.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        NaiveSolver ds = new NaiveSolver(i);
        ds.solve();
        double cns = i.getPlanningCurrent().getCout();
        System.out.println("---Cout ns: " + cns);
        GeneticSolver gs = new GeneticSolver(i, 2, 5000, 2500, 0.5);
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
