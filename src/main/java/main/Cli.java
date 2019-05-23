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
import algo.NaiveSolver;
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
            new NaiveSolver()
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
     * Instance file parser.
     */
    private final InstanceFileParser ifp;

    /**
     * Solution writer.
     */
    private static final SolutionWriter SW = new SolutionWriter();

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
        self.printIntro();
        self.printLicence();

        if (args.length >= 1) {
            if ("help".equals(args[0]) || "usage".equals(args[0])) {
                self.printHelp();
                return;
            }

            // Unknown argument
            self.printHelp();
            return;

        }

        self.runAllInstancesOnAllSolvers();
    }

    /**
     * Cli constructor.
     *
     * @throws ParserException If we can't instantiate the parser.
     */
    public Cli() throws ParserException {
        this.ifp = new InstanceFileParser();
    }

    /**
     * Print intro text.
     */
    private void printIntro() {
        Cli.PS.println("+------------+");
        Cli.PS.println("|POO4 Project|");
        Cli.PS.println("+------------+");

        Cli.PS.println("Made by");
        Cli.PS.println("+ Lilian Petitpas");
        Cli.PS.println("+ Thomas Ternisien");
        Cli.PS.println("+ Thibaut Fenain");
        Cli.PS.println("+ Corentin Apolinario");
    }

    /**
     * Print licence text.
     */
    private void printLicence() {
        Cli.PS.println("******************");

        Cli.PS.println("POO4-Project Copyright (C) 2019 "
                + "Lilian Petitpas, "
                + "Thomas Ternisien, "
                + "Thibaut Fenain, "
                + "Corentin Apolinario");
        Cli.PS.println("");
        Cli.PS.println("This program comes with ABSOLUTELY NO WARRANTY");
        Cli.PS.println("This is free software, and you are welcome to "
                + "redistribute under certain conditions");

        Cli.PS.println("******************");
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

                    Instance instance = ifp.parse(is);

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
