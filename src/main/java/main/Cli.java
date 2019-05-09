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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import model.Instance;

/**
 * CLI function, used as an entry point for this project
 *
 * @author Lilian Petitpas <lilian.petitpas@outlook.com>
 */
public class Cli {

    private static final List<Class<? extends ISolver>> SOLVERS = Arrays.asList(
            DumbSolver.class
    );

    public static void main(String[] args) {
        PrintStream ps = System.out;
        Cli.printIntro(ps);
        Cli.printLicence(ps);

        if (args.length >= 1) {
            if ("help".equals(args[0]) || "usage".equals(args[0])) {
                Cli.printHelp(ps);
                return;
            }

            // Unknown argument
            ps.println("Unknown argument");
            Cli.printHelp(ps);

        } else {
            Cli.runAllSolversAllJarInstances(ps);
        }
    }

    private static void printIntro(PrintStream ps) {
        ps.println("+------------+");
        ps.println("|POO4 Project|");
        ps.println("+------------+");

        ps.println("Made by");
        ps.println("+ Lilian Petitpas");
        ps.println("+ Thomas Ternisien");
        ps.println("+ Thibaut Fenain");
        ps.println("+ Corentin Apolinario");
    }

    private static void printLicence(PrintStream ps) {
        ps.println("******************");

        ps.println("POO4-Project Copyright (C) 2019 "
                + "Lilian Petitpas, "
                + "Thomas Ternisien, "
                + "Thibaut Fenain, "
                + "Corentin Apolinario");
        ps.println("");
        ps.println("This program comes with ABSOLUTELY NO WARRANTY");
        ps.println("This is free software, and you are welcome to "
                + "redistribute under certain conditions");

        ps.println("******************");
    }

    private static void printHelp(PrintStream ps) {
        ps.println("Usage : cli.jar [option]");

        ps.println("Options :");
        ps.println("\thelp\tPrint help");
        ps.println("\tusage\tPrint help");

    }

    private static void runAllSolversAllJarInstances(PrintStream ps) {

        ps.println("Run All solvers on all files instances stored in Jar");

        for (Class<? extends ISolver> solver : SOLVERS) {
            try {

                ps.println("\t********************");
                ps.println("\tRun " + solver.getCanonicalName() + " on all files instances stored in Jar");

                Constructor<? extends ISolver> cons = solver.getConstructor(Instance.class);

                JarInstanceResourceReader instanceLoader = new JarInstanceResourceReader();

                for (FilenameIterator<InputStream> iterator = instanceLoader.iterator(); iterator.hasNext();) {
                    ps.println("\t\t********************");

                    try (InputStream is = iterator.next()) {
                        ps.println("\t\tResource " + iterator.getFilename() + " loaded from Jar");

                        InstanceFileParser ifp = new InstanceFileParser();
                        Instance instance = ifp.parse(is);

                        ISolver solverInst = cons.newInstance(instance);
                        boolean status = solverInst.solve();

                        if (!status) {
                            System.exit(1);
                        }
                    }

                    ps.println("\t\tInstance OK");
                }

                ps.println("\tParseur OK");

            } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException | ParserException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
