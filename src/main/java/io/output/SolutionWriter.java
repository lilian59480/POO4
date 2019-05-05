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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import model.Instance;

/**
 *
 * @author Lilian Petitpas <lilian.petitpas@outlook.com>
 */
public class SolutionWriter {

    public boolean write(Instance i) throws WriterException {
        return this.write(i, "instance_latest_sol.txt");
    }

    public boolean write(Instance i, String filename) throws WriterException {
        File f = new File(filename);

        try (PrintWriter writer = new PrintWriter(f, Charset.forName("UTF-8"))) {
            // Business code needed
        } catch (IOException ex) {
            throw new WriterException(ex);
        }

        return true;
    }

    private void printTSV(PrintWriter w, List<Object> objList) {
        Iterator<Object> objIter = objList.iterator();

        while (objIter.hasNext()) {
            Object elt = objIter.next();
            w.print(elt);

            // Not the last element
            if (objIter.hasNext()) {
                w.print('\t');
            }

        }

    }

    private void printTSVln(PrintWriter w, List<Object> objList) {
        this.printTSV(w, objList);
        w.println();
    }

    public static void main(String[] args) {
        SolutionWriter sw = new SolutionWriter();
        Instance i = new Instance();
        try {
            sw.write(i, "test.txt");
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
    }
}
