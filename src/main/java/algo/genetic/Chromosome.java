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

import algo.SolverException;

import model.Client;
import model.Emplacement;
import model.Instance;
import model.Planning;
import model.Vehicule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Chromosome class
 *
 * @author Thomas
 */
public class Chromosome {

    /**
     * Clients of the chromosome
     */
    private List<Client> clients;

    /**
     * Tournee of this chromosome
     */
    private Tournee tournee;

    private Instance instance;

    /**
     * Chromosome constructor
     */
    public Chromosome() {
        this.clients = new ArrayList<>();
    }

    /**
     * Chromosome constructor that converts an Instance
     *
     * @param i The instance to convert
     */
    public Chromosome(Instance i) {
        this.instance = i;
        if (i.getPlanningCurrent().getVehicules().size() > 0) {
            this.clients = new ArrayList<>();
            Planning p = i.getPlanningCurrent();
            for (Vehicule vehicule : p.getVehicules()) {
                List<Emplacement> ems = vehicule.getEmplacements();
                for (Emplacement e : ems) {
                    this.clients.add(e.getClient());
                }
            }
        } else { // No planning to optimise
            this.clients = new ArrayList<>(i.getClients());
        }
    }

    /**
     * Chromosome constructor that copies a Chromosome
     *
     * @param chromosome the chromosome to copy
     * @throws SolverException If there is an internal exception or inconsistant
     *                         values.
     */
    public Chromosome(Chromosome chromosome) throws SolverException {
        this.instance = chromosome.getInstance();
        this.clients = new ArrayList<>(chromosome.getClients());
        this.tournee = new Tournee(chromosome.getTournee());
    }

    /**
     * Chromosome constructor using a list of clients
     *
     * @param i       The instance to convert
     * @param clients The list of client
     */
    Chromosome(Instance i, List<Client> clients) {
        this.instance = i;
        this.clients = new ArrayList<>(clients);
    }

    /**
     * Get the clients of the chromosome
     *
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Get the tournee of the chromosome
     * and generate it if it doesn't exist
     *
     * @return the tournee
     * @throws SolverException If there is an internal exception or inconsistant
     *                         values.
     */
    public Tournee getTournee() throws SolverException {
        if (this.tournee == null) {
            this.generateTournee();
        }
        return tournee;
    }

    /**
     * Get the instance of the chromosome
     *
     * @return the instance
     */
    public Instance getInstance() {
        return instance;
    }

    /**
     * Functions that generate the chromosome's tournee using the shortest path algo
     *
     * @throws SolverException If there is an internal exception or inconsistant
     *                         values.
     */
    public void generateTournee() throws SolverException {
        ShortestPathClients sp = new ShortestPathClients(this.instance, this);
        this.tournee = sp.findBestTournee();
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "clients=" + clients +
                ", tournee=" + tournee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return clients.equals(that.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clients);
    }
}
