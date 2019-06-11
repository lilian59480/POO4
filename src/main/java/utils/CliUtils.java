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
package utils;

import java.io.PrintStream;

/**
 * Cli utilities
 *
 * @author Lilian Petitpas
 */
public class CliUtils {

    /**
     * Possible options.
     */
    public static enum Options {
        /**
         * Help option.
         *
         * Should display the help message.
         */
        HELP,
        /**
         * Usage option.
         *
         * Should display the help message or an usage example.
         */
        USAGE,
        /**
         * Unknown argument option.
         *
         * Should display the help message.
         */
        UNKNOWN,
        /**
         * No arguments option.
         *
         * Should execute the program.
         */
        NO_ARGS;
    }

    /**
     * Utils class, no need for constructor
     */
    private CliUtils() {
        // Utils
    }

    /**
     * Print introduction text.
     *
     * Add details like a title and authors.
     *
     * @param ps Stream to use.
     */
    public static void printIntro(PrintStream ps) {
        ps.println("+------------+");
        ps.println("|POO4 Project|");
        ps.println("+------------+");

        ps.println("Made by");
        ps.println("+ Lilian Petitpas");
        ps.println("+ Thomas Ternisien");
        ps.println("+ Thibaut Fenain");
        ps.println("+ Corentin Apolinario");
    }

    /**
     * Print licence text.
     *
     * Licence explanation.
     *
     * @param ps Stream to use.
     */
    public static void printLicence(PrintStream ps) {
        ps.println("POO4-Project Copyright (C) 2019 "
                + "Lilian Petitpas, "
                + "Thomas Ternisien, "
                + "Thibaut Fenain, "
                + "Corentin Apolinario");
        ps.println("");
        ps.println("This program comes with ABSOLUTELY NO WARRANTY");
        ps.println("This is free software, and you are welcome to "
                + "redistribute under certain conditions");
    }

    /**
     * Parse arguments.
     *
     * @param args Cli Arguments
     * @return An Option from the enum
     */
    public static Options parseArguments(String[] args) {
        int argLength = args.length;

        if (argLength == 0) {
            return Options.NO_ARGS;
        }

        if (argLength == 1) {
            String arg0 = args[0];

            if (arg0 == null || arg0.isEmpty()) {
                return Options.UNKNOWN;
            }

            switch (arg0) {
                case "help":
                    return Options.HELP;
                case "usage":
                    return Options.USAGE;
                default:
                    return Options.UNKNOWN;
            }
        }

        return Options.UNKNOWN;
    }

}
