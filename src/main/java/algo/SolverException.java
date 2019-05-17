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
package algo;

/**
 * Exception class used by Solver classes when solving can't be done properly.
 *
 * @author Lilian Petitpas
 */
public class SolverException extends Exception {

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 20190510192031L;

    /**
     * Constructs an instance of {@code SolverException} with a detail message.
     *
     * @param msg the detail message.
     */
    public SolverException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of {@code SolverException} with a cause from a
     * previous {@code Exception}.
     *
     * @param cause the cause.
     */
    public SolverException(Throwable cause) {
        super(cause);
    }

}
