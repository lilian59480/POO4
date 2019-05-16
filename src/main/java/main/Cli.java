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

import algo.DumbSolver;
import algo.ISolver;
import io.input.FilenameIterator;
import io.input.InstanceFileParser;
import io.input.JarInstanceResourceReader;
import io.input.ParserException;
import io.output.SolutionWriter;
import io.output.WriterException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Instance;

/**
 * CLI function, used as an entry point for this project
 *
 * @author Lilian Petitpas <lilian.petitpas@outlook.com>
 */
public class Cli {

    private static final Logger LOGGER = Logger.getLogger(Cli.class.getName());

    private static final List<Class<? extends ISolver>> SOLVERS = Arrays.asList(
            DumbSolver.class
    );

    private final PrintStream ps = System.out;

    private final JarInstanceResourceReader instanceLoader = new JarInstanceResourceReader();

    private final InstanceFileParser ifp;

    private final SolutionWriter sw = new SolutionWriter();

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

        } else {
            self.runAllInstancesOnAllSolvers();
        }
    }

    public Cli() throws ParserException {
        this.ifp = new InstanceFileParser();
    }

    private void printIntro() {
        this.ps.println("+------------+");
        this.ps.println("|POO4 Project|");
        this.ps.println("+------------+");

        this.ps.println("Made by");
        this.ps.println("+ Lilian Petitpas");
        this.ps.println("+ Thomas Ternisien");
        this.ps.println("+ Thibaut Fenain");
        this.ps.println("+ Corentin Apolinario");
    }

    private void printLicence() {
        this.ps.println("******************");

        this.ps.println("POO4-Project Copyright (C) 2019 "
                + "Lilian Petitpas, "
                + "Thomas Ternisien, "
                + "Thibaut Fenain, "
                + "Corentin Apolinario");
        this.ps.println("");
        this.ps.println("This program comes with ABSOLUTELY NO WARRANTY");
        this.ps.println("This is free software, and you are welcome to "
                + "redistribute under certain conditions");

        this.ps.println("******************");
    }

    private void printHelp() {
        this.ps.println("Usage : cli.jar [option]");

        this.ps.println("Options :");
        this.ps.println("\thelp\tPrint help");
        this.ps.println("\tusage\tPrint help");

    }

    private void runAllInstancesOnAllSolvers() {

        this.ps.println("Run All solvers on all files instances stored in Jar");
        LOGGER.log(Level.INFO, "Solvers available {0}", SOLVERS);

        for (Class<? extends ISolver> solver : SOLVERS) {

            try {
                Constructor<? extends ISolver> cons = solver.getConstructor();
                ISolver solverInst = cons.newInstance();
                this.runAllInstancesOnOneSolver(solverInst);
            } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
                LOGGER.log(Level.SEVERE, "Reflexion exception", ex);
                return;
            }

            this.ps.println("\t********************");
        }
    }

    private void runAllInstancesOnOneSolver(ISolver solver) {
        try {

            this.ps.println("\tRun " + solver + " on all files instances stored in Jar");

            for (FilenameIterator<InputStream> iterator = instanceLoader.iterator(); iterator.hasNext();) {
                this.ps.println("\t\t********************");

                try (InputStream is = iterator.next()) {
                    LOGGER.log(Level.INFO, "Loaded {0}", iterator.getFilename());
                    this.ps.println("\t\tResource " + iterator.getFilename() + " loaded from Jar");

                    Instance instance = ifp.parse(is);

                    LOGGER.log(Level.FINE, "Instance parsed : {0}", is);

                    this.runOneInstancesOnOneSolver(solver, instance, iterator.getFilename());

                }

                this.ps.println("\t\tInstance OK");
            }

        } catch (ParserException | IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving Instances!", ex);
        }

    }

    private void runOneInstancesOnOneSolver(ISolver solver, Instance i, String filename) {
        solver.setInstance(i);
        this.ps.println("\t\t\tSolving ...");
        boolean status = solver.solve();
        if (!status) {
            LOGGER.log(Level.SEVERE, "Instance unsolvable!");
            return;
        }

        // @TODO : Make something better, with proper check
        // Maybe regex ?
        String baseFilename = filename.substring(11, filename.length() - 4);

        try {
            this.sw.write(i, baseFilename + "_sol.txt");
        } catch (WriterException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to write solution file", ex);
        }

    }

}
