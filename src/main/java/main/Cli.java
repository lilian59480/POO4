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
package main;

import algo.ISolver;
import algo.genetic.GeneticSolver;
import algo.iterative.CvCostCapacitySortedSolver;
import algo.iterative.NaiveSolver;
import algo.iterative.RandomSolver;
import io.input.FilenameIterator;
import io.input.InstanceFileParser;
import io.input.JarInstanceResourceReader;
import io.input.ParserException;
import io.output.SolutionWriter;
import io.output.WriterException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Instance;
import model.Planning;
import utils.CliUtils;

/**
 * CLI function, used as an entry point for this project
 *
 * @author Lilian Petitpas
 */
public class Cli {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Cli.class.getName());

    /**
     * List of solvers available.
     *
     * @todo Use a factory?
     */
    private static final List<ISolver> SOLVERS = Arrays.asList(
            new NaiveSolver(),
            new RandomSolver(),
            new CvCostCapacitySortedSolver(),
            new GeneticSolver(2, 5000, 2500, 0.4)
    );

    /**
     * PrintStream used to print progress to the user.
     */
    private static final PrintStream PS = System.out;

    /**
     * Instance Reader.
     */
    private static final JarInstanceResourceReader JAR_INSTANCE_RR = new JarInstanceResourceReader();

    /**
     * Solution writer.
     */
    private static final SolutionWriter SW = new SolutionWriter();

    /**
     * Instance file parser.
     */
    private final InstanceFileParser ifp;

    /**
     * Cli constructor.
     *
     * @throws ParserException If we can't instantiate the parser.
     */
    public Cli() throws ParserException {
        this.ifp = new InstanceFileParser();
    }

    /**
     * Print help text.
     */
    private void printHelp() {
        Cli.PS.println("Usage : cli.jar [option]");

        Cli.PS.println("Options :");
        Cli.PS.println("\thelp\tPrint help");
        Cli.PS.println("\tusage\tPrint help");
    }

    /**
     * Print cost summary, in a beautiful table.
     *
     * @param solver Solver used
     * @param instanceList List of instances solved with the solver in
     * parameter.
     */
    private void printCostSummary(ISolver solver, List<Instance> instanceList) {
        Cli.PS.println("Summary for :");
        Cli.PS.println(solver);

        Cli.PS.println("+----+--------------+--------+---------+");
        Cli.PS.println("| ID |     Cost     | Ext V. | Checker |");
        Cli.PS.println("+----+--------------+--------+---------+");

        int index = 0;
        for (Instance instance : instanceList) {
            Planning p = instance.getPlanningCurrent();
            int id = index;
            double cost = p.getCout();

            int externalVehicules = p.getVehicules().size() - instance.getNbVehicules();

            boolean valid = p.check();
            String unicodeValid = "V";
            if (!valid) {
                unicodeValid = "X";
            }

            Cli.PS.printf("| %2d | %12.2f |   %2d   |    %s    |\n", id, (float) cost, externalVehicules, unicodeValid);
            index++;
        }

        Cli.PS.println("+----+--------------+--------+---------+");

    }

    /**
     * Run all instances on all defined solvers.
     */
    private void runAllInstancesOnAllSolvers() {

        Cli.PS.println("Run All solvers on all files instances stored in Jar");
        LOGGER.log(Level.INFO, "Solvers available {0}", SOLVERS);

        for (ISolver solverInst : SOLVERS) {
            this.runAllInstancesOnOneSolver(solverInst);
            Cli.PS.println("\t********************");
        }
    }

    /**
     * Run all instances on a specific solver.
     *
     * @param solver The solver to use.
     */
    private void runAllInstancesOnOneSolver(ISolver solver) {
        List<Instance> instanceList = new ArrayList<>();
        try {

            Cli.PS.println("\tRun " + solver + " on all files instances stored in Jar");

            for (FilenameIterator<InputStream> iterator = Cli.JAR_INSTANCE_RR.iterator(); iterator.hasNext();) {
                Cli.PS.println("\t\t********************");

                try (InputStream is = iterator.next()) {
                    LOGGER.log(Level.INFO, "Loaded {0}", iterator.getFilename());
                    Cli.PS.println("\t\tResource " + iterator.getFilename() + " loaded from Jar");

                    Instance instance = this.ifp.parse(is);

                    LOGGER.log(Level.FINE, "Instance parsed : {0}", is);

                    this.runOneInstancesOnOneSolver(solver, instance, Cli.defineSolutionFilename(iterator.getFilename()));
                    instanceList.add(instance);
                }

                Cli.PS.println("\t\tInstance OK");
            }

        } catch (ParserException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving Instances!", ex);
        }

        this.printCostSummary(solver, instanceList);

    }

    /**
     * Solve an instance.
     *
     * @param solver The solver to use.
     * @param i The instance to solve.
     * @param filename Filename to use when writing the solution.
     */
    private void runOneInstancesOnOneSolver(ISolver solver, Instance i, String filename) {
        solver.setInstance(i);
        Cli.PS.println("\t\t\tSolving ...");
        boolean status = solver.solve();
        if (!status) {
            LOGGER.log(Level.SEVERE, "Instance unsolvable! Use the official checker to know why");
        }

        try {
            Cli.SW.write(i, filename);
        } catch (WriterException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to write solution file", ex);
        }

    }

    /**
     * Cli Entry point.
     *
     * @param args Arguments, 1st can be "help" or "usage"
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Solving a new instance");
        LOGGER.log(Level.FINE, "Arguments : {0}", Arrays.toString(args));

        Cli self;
        try {
            self = new Cli();
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to initialise Cli", ex);
            System.exit(-1);
            return;
        }
        CliUtils.printIntro(Cli.PS);
        Cli.PS.println("******************");
        CliUtils.printLicence(Cli.PS);
        Cli.PS.println("******************");

        CliUtils.Options ops = CliUtils.parseArguments(args);

        switch (ops) {
            case UNKNOWN:
                Cli.PS.println("Unknown arguments");
                self.printHelp();
                break;
            case HELP:
            case USAGE:
                self.printHelp();
                break;
            case NO_ARGS:
            default:
                self.runAllInstancesOnAllSolvers();
        }

    }

    /**
     * Get the solution filename to be compatible witj specification.
     *
     * @todo Use a better and fool proof algorithm.
     * @param instanceFilename Path of the instance
     * @return Filename of the solution.
     */
    private static String defineSolutionFilename(String instanceFilename) {
        return instanceFilename.substring(11, instanceFilename.length() - 4) + "_sol.txt";
    }

}
