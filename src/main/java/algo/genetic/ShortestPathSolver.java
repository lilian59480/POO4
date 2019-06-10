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
package algo.genetic;

import algo.ISolver;
import algo.SolverException;
import algo.iterative.NaiveSolver;
import algo.iterative.RandomSolver;
import io.input.InstanceFileParser;
import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ShortestPathSolver class
 *
 * @author Thomas
 */
public class ShortestPathSolver implements ISolver {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ShortestPathSolver.class.getName());

    /**
     * Current instance.
     */
    private Instance instance;

    /**
     * ShortestPathSolver constructor, without an Instance.
     * <p>
     * This constructor is recommended as you can solve multiples instances by
     * using the instance setter.
     */
    public ShortestPathSolver() {
        this(null);
    }

    /**
     * ShortestPathSolver constructor, with an Instance.
     *
     * @param i Instance to solve
     */
    public ShortestPathSolver(Instance i) {
        this.instance = i;
    }

    @Override
    public Instance getInstance() {
        return instance;
    }

    @Override
    public void setInstance(Instance i) {
        this.instance = i;
    }

    @Override
    public boolean solve() {
        LOGGER.log(Level.FINE, "Solving a new instance");

        try {
            Chromosome chromosome = new Chromosome(this.instance);
            chromosome.generateTournee();
            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int nbV = this.instance.getNbVehicules();

            List<Label> tournee = new ArrayList<>(chromosome.getTournee().getTournee());
            Collections.reverse(tournee);
            Vehicule v;
            for (int i = 0; i < tournee.size(); i++) {
                Label label = tournee.get(i);
                if (i >= nbV) {
                    LOGGER.log(Level.INFO, "Ajout d'un extra vehicule");
                    v = this.instance.addVehicule();
                } else {
                    v = vehicules.get(i);
                }
                if (label.getPrecedents().size() > 1) {
                    for (int j = 1; j < label.getPrecedents().size(); j++) {
                        if (!v.addEmplacement(label.getPrecedents().get(j))) {
                            LOGGER.log(Level.WARNING,
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                            throw new SolverException(
                                    "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                        }
                    }
                }
                if (!v.addEmplacement(label.getEmplacement())) {
                    LOGGER.log(Level.WARNING,
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                    throw new SolverException(
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                }
            }

        } catch (SolverException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return false;
        }

        // Check for instance validity
        return this.instance.check();
    }

    /**
     * Main funtion (for dev purposes only)
     *
     * @param args the args
     */
    public static void main(String[] args) {
        Instance i = null;
        for (int j = 0; j < 40; j++) {
            int id = j;
            System.out.println(id);
            try {
                InstanceFileParser ifp = new InstanceFileParser();
                i = ifp.parse(new File("src/main/resources/instances/instance_" + id + "-triangle.txt"));
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
                return;
            }
            NaiveSolver ds = new NaiveSolver(i);
            ds.solve();
            double cns = i.getPlanningCurrent().getCout();
            System.out.println("---Cout ns: " + cns);
            ShortestPathSolver sp = new ShortestPathSolver(i);
            sp.solve();
            double csp = i.getPlanningCurrent().getCout();
            System.out.println("---Cout sp: " + csp + " ( " + (csp - cns) + " )");
            RandomSolver rs = new RandomSolver(i);
            rs.solve();
            double crs = i.getPlanningCurrent().getCout();
            System.out.println("---Cout rs: " + crs);
            sp = new ShortestPathSolver(i);
            sp.solve();
            csp = i.getPlanningCurrent().getCout();
            System.out.println("---Cout sp: " + csp + " ( " + (csp - crs) + " )");
            /*try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }*/
        }
    }
}
