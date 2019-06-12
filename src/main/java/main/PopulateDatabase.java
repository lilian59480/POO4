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
package main;

import dao.ClientDao;
import dao.DaoException;
import dao.DaoFactory;
import dao.DepotDao;
import dao.InstanceDao;
import dao.PlanningDao;
import dao.RouteDao;
import dao.VehiculeDao;
import io.input.FilenameIterator;
import io.input.InstanceFileParser;
import io.input.JarInstanceResourceReader;
import io.input.ParserException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Instance;
import utils.CliUtils;

/**
 * Populate the database with instances taken from Jar, used as one entry point
 * for this project.
 *
 * @author Lilian Petitpas
 * @author Corentin Apolinario
 */
public class PopulateDatabase {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PopulateDatabase.class.getName());

    /**
     * PrintStream used to print progress to the user.
     */
    private static final PrintStream PS = System.out;

    /**
     * Instance Reader.
     */
    private static final JarInstanceResourceReader JAR_INSTANCE_RR = new JarInstanceResourceReader();

    /**
     * Instance file parser.
     */
    private final InstanceFileParser ifp;

    /**
     * DaoFactory.
     */
    private final DaoFactory daoFactory;

    /**
     * PopulateDatabase constructor.
     *
     * @throws DaoException After a database exception.
     * @throws ParserException If we can't instantiate the parser.
     */
    public PopulateDatabase() throws DaoException, ParserException {
        this.daoFactory = DaoFactory.getDaoFactory(DaoFactory.PersistenceType.JPA);
        this.ifp = new InstanceFileParser();
    }

    /**
     * Cleaning the database.
     */
    public void clean() {
        PopulateDatabase.PS.println("Cleaning the database");

        boolean success = true;

        LOGGER.log(Level.INFO, "Deleting Routes");
        RouteDao routeManager = this.daoFactory.getRouteDao();
        success &= routeManager.deleteAll();
        LOGGER.log(Level.INFO, "Routes deleted");

        LOGGER.log(Level.INFO, "Deleting Clients");
        ClientDao clientManager = this.daoFactory.getClientDao();
        success &= clientManager.deleteAll();
        LOGGER.log(Level.INFO, "Clients deleted");

        LOGGER.log(Level.INFO, "Deleting Vehicules");
        VehiculeDao vehiculeManager = this.daoFactory.getVehiculeDao();
        success &= vehiculeManager.deleteAll();
        LOGGER.log(Level.INFO, "Vehicules deleted");

        LOGGER.log(Level.INFO, "Deleting Depots");
        DepotDao depotManager = this.daoFactory.getDepotDao();
        success &= depotManager.deleteAll();
        LOGGER.log(Level.INFO, "Depots deleted");

        LOGGER.log(Level.INFO, "Deleting Plannings");
        PlanningDao planningManager = this.daoFactory.getPlanningDao();
        success &= planningManager.deleteAll();
        LOGGER.log(Level.INFO, "Plannings deleted");

        LOGGER.log(Level.INFO, "Deleting Instances");
        InstanceDao instanceManager = this.daoFactory.getInstanceDao();
        success &= instanceManager.deleteAll();
        LOGGER.log(Level.INFO, "Instances deleted");

        if (!success) {
            LOGGER.log(Level.SEVERE, "Error while cleaning database");
        }
    }

    /**
     * Populate the database.
     */
    public void populate() {
        this.populate(40);
    }

    /**
     * Populate the database.
     *
     * @param max Maximum number of instances to load
     */
    public void populate(int max) {
        PopulateDatabase.PS.println("Populating the database");

        InstanceDao instanceManager = this.daoFactory.getInstanceDao();

        int i = 0;
        for (FilenameIterator<InputStream> iterator = PopulateDatabase.JAR_INSTANCE_RR.iterator(); iterator.hasNext() && i < max; i++) {

            try (InputStream is = iterator.next()) {
                LOGGER.log(Level.INFO, "Parsing new file");
                LOGGER.log(Level.INFO, "Loaded {0}", iterator.getFilename());

                PopulateDatabase.PS.println("Resource " + iterator.getFilename() + " loaded from Jar");

                Instance instance = this.ifp.parse(is);

                LOGGER.log(Level.FINE, "Instance parsed : {0}", is);

                boolean status = instanceManager.create(instance);

                if (!status) {
                    LOGGER.log(Level.SEVERE, "Error while populating database with this instance");
                    PopulateDatabase.PS.println("Resource not added");
                } else {
                    PopulateDatabase.PS.println("Resource added");
                }

            } catch (ParserException | IOException ex) {
                LOGGER.log(Level.INFO, "Error while populating database, cleaning", ex);
                this.clean();
                System.exit(1);
            }

        }

        PopulateDatabase.PS.println("Database populated");

    }

    /**
     * Print help text.
     */
    private void printHelp() {
        PopulateDatabase.PS.println("Usage : populate.jar [option]");

        PopulateDatabase.PS.println("Options :");
        PopulateDatabase.PS.println("\thelp\tPrint help");
        PopulateDatabase.PS.println("\tusage\tPrint help");
    }

    /**
     * Cli Entry point.
     *
     * @param args Arguments, 1st can be "help" or "usage"
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Cleaning and populating database");
        LOGGER.log(Level.FINE, "Arguments : {0}", Arrays.toString(args));

        PopulateDatabase self;
        try {
            self = new PopulateDatabase();
        } catch (DaoException | ParserException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to initialise PopulateDatabase", ex);
            System.exit(-1);
            return;
        }

        CliUtils.printIntro(PopulateDatabase.PS);
        PopulateDatabase.PS.println("******************");
        CliUtils.printLicence(PopulateDatabase.PS);
        PopulateDatabase.PS.println("******************");

        CliUtils.Options ops = CliUtils.parseArguments(args);

        switch (ops) {
            case UNKNOWN:
                PopulateDatabase.PS.println("Unknown arguments");
                self.printHelp();
                break;
            case HELP:
            case USAGE:
                self.printHelp();
                break;
            case NO_ARGS:
            default:
                self.clean();
                self.populate();
        }
    }
}
