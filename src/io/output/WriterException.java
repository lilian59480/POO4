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
package io.output;

/**
 * Exception class used by Parsers classes when parsing can't be done properly.
 *
 * @author Lilian Petitpas
 */
public class WriterException extends Exception {

    private static final long serialVersionUID = 20190429110615L;

    private static final String COMPARISON_MSG = "%s\nExpected %s, got %s";

    /**
     * Constructs an instance of {@code WriterException} with a detail message.
     *
     * @param msg the detail message.
     */
    public WriterException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of {@code WriterException} with a cause from a
     * previous {@code Exception}.
     *
     * @param cause the cause.
     */
    public WriterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of {@code WriterException} with a detail message
     * and a comparison between 2 values.
     *
     * @param msg the detail message.
     * @param expected Expected value by the writer
     * @param got Value the writer have retrieved
     */
    public WriterException(String msg, Object expected, Object got) {
        super(String.format(WriterException.COMPARISON_MSG, msg, expected, got));
    }

}
