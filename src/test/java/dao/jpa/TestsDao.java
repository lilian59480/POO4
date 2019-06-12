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
package dao.jpa;

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
import java.util.ArrayList;
import java.util.List;
import main.PopulateDatabase;
import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Planning;
import model.Route;
import model.Vehicule;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * Tests for {@link Dao}
 *
 * @author Corentin
 */
@DisplayName("Dao")
@TestMethodOrder(OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "ENV", matches = "DB")
public class TestsDao {

    /**
     * Check if DAO is working
     */
    @Test
    @DisplayName("Is Dao Working")
    @Order(1)
    public void isDaoWorking() {
        DaoFactory daoFactory;

        try {
            daoFactory = DaoFactory.getDaoFactory(DaoFactory.PersistenceType.JPA);
        } catch (DaoException ex) {
            fail(ex);
            return;
        }

        RouteDao routeManager = daoFactory.getRouteDao();
        assertTrue(routeManager.deleteAll(), "Delete all should work for routes");

        ClientDao clientManager = daoFactory.getClientDao();
        assertTrue(clientManager.deleteAll(), "Delete all should work for clients");

        VehiculeDao vehiculeManager = daoFactory.getVehiculeDao();
        assertTrue(vehiculeManager.deleteAll(), "Delete all should work for vehicules");

        DepotDao depotManager = daoFactory.getDepotDao();
        assertTrue(depotManager.deleteAll(), "Delete all should work for depots");

        PlanningDao planningManager = daoFactory.getPlanningDao();
        assertTrue(planningManager.deleteAll(), "Delete all should work for plannings");

        Depot d = new Depot(0, 700, 0, 0);
        depotManager.create(d);

        Emplacement e1 = new Emplacement(0, 700, 10, 10);
        Client c1 = new Client(10);
        c1.addEmplacement(e1);
        Emplacement e2 = new Emplacement(0, 700, -10, 10);
        Client c2 = new Client(5);
        c2.addEmplacement(e2);
        Emplacement e3 = new Emplacement(0, 700, 10, -10);
        Client c3 = new Client(10);
        c3.addEmplacement(e3);

        clientManager.create(c1);
        clientManager.create(c2);
        clientManager.create(c3);

        Route r = new Route(d, e1, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(d, e2, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(d, e3, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(e1, d, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(e1, e2, 20, 0);
        d.addRouteTo(r);
        r = new Route(e1, e3, 20, 0);
        d.addRouteTo(r);
        r = new Route(e2, d, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(e2, e1, 20, 0);
        d.addRouteTo(r);
        r = new Route(e2, e3, 20, 0);
        d.addRouteTo(r);
        r = new Route(e3, d, 14.1, 0);
        d.addRouteTo(r);
        r = new Route(e3, e1, 20, 0);
        d.addRouteTo(r);
        r = new Route(e3, e2, 20, 0);
        d.addRouteTo(r);

        depotManager.update(d);
        clientManager.update(c1);
        clientManager.update(c2);
        clientManager.update(c3);

        Vehicule v1 = new Vehicule(d, 15);
        Vehicule v2 = new Vehicule(d, 15);

        vehiculeManager.create(v1);
        vehiculeManager.create(v2);

        Planning p = new Planning();
        p.addVehicule(v1);
        p.addVehicule(v2);

        planningManager.create(p);

        if (!v1.addClient(c1)) {
            v2.addClient(c1);
        }
        if (!v1.addClient(c2)) {
            v2.addClient(c2);
        }
        if (!v1.addClient(c3)) {
            v2.addClient(c3);
        }

        // p.updatePositionClients();
        clientManager.update(c1);
        clientManager.update(c2);
        clientManager.update(c3);

        planningManager.update(p);

        assertNotNull(p, "Planning must exist");
    }

    /**
     * Check if Instances are identical
     */
    @Test
    @DisplayName("Is Instances equals")
    @Order(2)
    public void isInstancesEquals() {
        int numberOfInstances = 15;
        PopulateDatabase pd;
        DaoFactory daoFactory;
        JarInstanceResourceReader reader = new JarInstanceResourceReader();
        InstanceFileParser ifp;

        try {
            pd = new PopulateDatabase();
            daoFactory = DaoFactory.getDaoFactory(DaoFactory.PersistenceType.JPA);
            ifp = new InstanceFileParser();
        } catch (DaoException | ParserException ex) {
            fail(ex);
            return;
        }

        pd.clean();
        pd.populate(numberOfInstances);

        InstanceDao instanceManager = daoFactory.getInstanceDao();
        List<Instance> instancesDb = new ArrayList<>(instanceManager.findAll());
        List<Instance> instancesFile = new ArrayList<>();

        int i = 0;
        for (FilenameIterator<InputStream> iterator = reader.iterator(); iterator.hasNext() && i < numberOfInstances; i++) {

            try (InputStream is = iterator.next()) {
                Instance instance = ifp.parse(is);
                instancesFile.add(instance);
            } catch (IOException | ParserException ex) {
                fail(ex);
            }

        }

        assertIterableEquals(instancesFile, instancesDb, "Instances must be equals");

    }

}
