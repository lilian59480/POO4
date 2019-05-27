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
package algo.iterative;

import algo.ISolver;
import algo.SolverException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Instance;
import model.Vehicule;

/**
 * Implementation of a Naive solver.
 *
 * This solver iterates over all clients and try to assign them a vehicule.
 *
 * @author Corentin
 * @author Lilian Petitpas
 */
public class IterativeSolver implements ISolver {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(IterativeSolver.class.getName());

    /**
     * Current instance.
     */
    private Instance instance;

    /**
     * Client modifier class.
     */
    private Modifier<Client> clientModifier;

    /**
     * Vehicule modifier class.
     */
    private Modifier<Vehicule> vehiculeModifier;

    /**
     * Solver constructor, without an Instance.
     *
     * This constructor is recommended as you can solve multiples instances by
     * using the instance setter.
     *
     * @param cm Client list modifier
     * @param vm Vehicule list modifier
     */
    public IterativeSolver(Modifier<Client> cm, Modifier<Vehicule> vm) {
        this(null, cm, vm);
    }

    /**
     * Solver constructor, with an Instance.
     *
     * @param i Instance to solve
     * @param cm Client list modifier
     * @param vm Vehicule list modifier
     */
    public IterativeSolver(Instance i, Modifier<Client> cm, Modifier<Vehicule> vm) {
        this.instance = i;
        if (cm == null) {
            throw new NullPointerException("Client modifier can't be null");
        }
        this.clientModifier = cm;
        if (vm == null) {
            throw new NullPointerException("Vehicule modifier can't be null");
        }
        this.vehiculeModifier = vm;
    }

    @Override
    public Instance getInstance() {
        return this.instance;
    }

    @Override
    public void setInstance(Instance i) {
        this.instance = i;
    }

    @Override
    public boolean solve() {
        if (this.instance == null) {
            LOGGER.log(Level.WARNING, "No instance to solve");
            return false;
        }
        LOGGER.log(Level.FINE, "Solving a new instance");
        try {
            this.iterativeSolver();
        } catch (SolverException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return false;
        }
        // Check for instance validity
        return this.instance.check();
    }

    /**
     * Solve the current instance using a naive algorithm.
     *
     * It iterates over all clients and try to assign them a vehicule.
     *
     * If it is not possible, ask for an external vehicule.
     *
     * @throws SolverException If there is an internal exception or inconsistant
     * values.
     */
    private void iterativeSolver() throws SolverException {
        this.instance.clear();
        List<Client> clients = this.instance.getClients();

        this.clientModifier.modifyList(clients);
        System.out.println("\n\n\n\n\n\n" + clients + "\n\n\n\n\n\n\n");

        for (Client c : clients) {
            boolean affecte = false;

            List<Vehicule> vehicules = this.instance.getVehicules();

            this.vehiculeModifier.modifyList(vehicules);

            for (Vehicule v : vehicules) {
                if (v.addClient(c)) {
                    affecte = true;
                    break;
                }
            }
            if (!affecte) {
                LOGGER.log(Level.INFO, "Plus de vehicule dispo pour affecter le client {0}", c);
                Vehicule v = this.instance.addVehicule();
                if (v == null) {
                    LOGGER.log(Level.WARNING, "Invalid vehicule !");
                    throw new SolverException("Invalid vehicule");
                }
                LOGGER.log(Level.INFO, "Nouveau vehicule créé");
                if (!v.addClient(c)) {
                    LOGGER.log(Level.WARNING, "Erreur : Impossible d affecter le client {0}", c);
                }
            }
        }
    }

}
