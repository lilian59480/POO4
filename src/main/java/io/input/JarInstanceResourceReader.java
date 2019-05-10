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

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allow us to iterate over Instance files stored inside the Jar
 * archive.
 *
 * @author Lilian Petitpas <lilian.petitpas@outlook.com>
 */
public class JarInstanceResourceReader implements Iterable<InputStream> {

    private static final Logger LOGGER = Logger.getLogger(JarInstanceResourceReader.class.getName());

    /**
     * Maximum value for iterator index.
     */
    private static final int MAX_INDEX = 40;

    /**
     * Class loader used for Jar resources gathering.
     */
    private final Class<JarInstanceResourceReader> loader;

    /**
     * Constructor.
     */
    public JarInstanceResourceReader() {
        this.loader = JarInstanceResourceReader.class;
    }

    /**
     * Create a new iterator to iterate over Instance files.
     *
     * @return A FilenameIterator iterator of InputStream
     */
    @Override
    public FilenameIterator<InputStream> iterator() {
        FilenameIterator<InputStream> it = new FilenameIterator<InputStream>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < MAX_INDEX;
            }

            @Override
            public InputStream next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("Iterator does not have next element");
                }
                int i = currentIndex;
                currentIndex++;
                LOGGER.log(Level.FINEST, "File jar:///instances/instance_{0}-triangle.txt retrieved", i);
                return loader.getResourceAsStream("/instances/instance_" + i + "-triangle.txt");
            }

            @Override
            public String getFilename() {
                return "/instances/instance_" + currentIndex + "-triangle.txt";
            }

        };
        return it;
    }

}
