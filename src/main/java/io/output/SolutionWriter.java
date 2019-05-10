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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Emplacement;
import model.Instance;
import model.Planning;
import model.Vehicule;

/**
 *
 * @author Lilian Petitpas <lilian.petitpas@outlook.com>
 */
public class SolutionWriter {

    private static final Logger LOGGER = Logger.getLogger(SolutionWriter.class.getName());

    public boolean write(Instance i) throws WriterException {
        return this.write(i, "instance_latest_sol.txt");
    }

    public boolean write(Instance i, String filename) throws WriterException {
        File f = new File(filename);

        LOGGER.log(Level.INFO, "Writing new solution at {0}", filename);

        try (PrintWriter writer = new PrintWriter(f, "UTF-8")) {
            Planning p = i.getPlanningCurrent();
            List<Vehicule> vehicules = p.getVehicules();
            for (Vehicule vehicule : vehicules) {
                List<Emplacement> el = vehicule.getEmplacements();
                List<Integer> al = new ArrayList<>();
                for (Emplacement emplacement : el) {
                    al.add(emplacement.getId());
                }
                this.printTSVln(writer, al);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception while writing solution", ex);
            throw new WriterException(ex);
        }

        return true;
    }

    private void printTSV(PrintWriter w, List objList) {
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

    private void printTSVln(PrintWriter w, List objList) {
        this.printTSV(w, objList);
        w.println();
    }

}
