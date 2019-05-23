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
package io.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that parses an TSV file.
 *
 * @author Lilian Petitpas
 */
public class TsvParser {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TsvParser.class.getName());

    /**
     * Parses a {@link File} and returns a list of values as {@link String}
     *
     * @param f The file to parse
     * @return an Instance
     * @throws ParserException When parsing can't be done properly
     */
    public List<String[]> parse(File f) throws ParserException {
        try (FileInputStream fis = new FileInputStream(f)) {
            return this.parse(fis);
        } catch (IOException ex) {
            throw new ParserException(ex);
        }
    }

    /**
     * Parses an {@link InputStream} and returns a list of values as
     * {@link String}
     *
     * @param is The {@link InputStream} to parse
     * @return an Instance
     * @throws ParserException When parsing can't be done properly
     */
    public List<String[]> parse(InputStream is) throws ParserException {
        try (InputStreamReader isr = new InputStreamReader(is)) {
            return this.parse(isr);
        } catch (IOException ex) {
            throw new ParserException(ex);
        }
    }

    /**
     * Parses a {@link Reader} linked to a document and returns a list of values
     * as {@link String}
     *
     * @param rd The Reader
     * @return an Instance
     * @throws ParserException When parsing can't be done properly
     */
    public List<String[]> parse(Reader rd) throws ParserException {

        List<String[]> lines = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(rd)) {
            String line;

            //Read File Line By Line
            while ((line = br.readLine()) != null) {
                // Print the content on the console
                String[] split = line.split("[\\s\t]+");
                LOGGER.log(Level.FINEST, "Line parsed : {0}", Arrays.toString(split));
                lines.add(split);
            }
        } catch (IOException ex) {
            throw new ParserException(ex);
        }

        return lines;
    }

}
