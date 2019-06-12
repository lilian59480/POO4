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
package model;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Vehicule}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Vehicule")
public class VehiculeTest {

    private static final int DEFAULT_HEURE_DEBUT = 0;
    private static final int DEFAULT_HEURE_FIN = 10;
    private static final double DEFAULT_X = -2;
    private static final double DEFAULT_Y = 7;
    private static final int DEFAULT_CAPACITY = 70;

    private Vehicule vehiculeInstance;
    private Depot depot;

    @BeforeEach
    void createNewPoint() {
        this.depot = new Depot(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
        this.vehiculeInstance = new Vehicule(depot, DEFAULT_CAPACITY);
    }

    /**
     * Test of check method, of class Vehicule.
     */
    @Test
    @DisplayName("Check")
    public void testCheck() {
        Emplacement e01 = new Emplacement(0, 1, DEFAULT_X, DEFAULT_Y);
        Emplacement e12 = new Emplacement(1, 2, DEFAULT_X, DEFAULT_Y);
        Emplacement e03 = new Emplacement(0, 3, DEFAULT_X, DEFAULT_Y);
        Emplacement e23 = new Emplacement(2, 3, DEFAULT_X, DEFAULT_Y);

        Client c1 = new Client(10);
        c1.addEmplacement(e01);
        c1.addEmplacement(e23);

        Client c2 = new Client(10);
        c2.addEmplacement(e12);
        c2.addEmplacement(e03);

        Vehicule vehiculeValid = new Vehicule(11);
        vehiculeValid.addClient(c1);
        vehiculeValid.addClient(c2);

        boolean expResult = true;
        boolean result = vehiculeValid.check();
        assertEquals(expResult, result, "Valid vehicule");
    }

    /**
     * Test of clear method, of class Vehicule.
     */
    @Test
    @DisplayName("Clear")
    public void testClear() {
        Client c = new Client(10);
        Emplacement e1 = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
        c.addEmplacement(e1);

        this.vehiculeInstance.addClient(c);
        this.vehiculeInstance.clear();

        List<Emplacement> expected = new ArrayList<>();
        List<Emplacement> result = this.vehiculeInstance.getEmplacements();

        assertEquals(expected, result, "Clear should be empty");
    }

    /**
     * Test of getCapaciteRestante method, of class Vehicule.
     */
    @Test
    @DisplayName("Get CapaciteRestante")
    public void testGetCapaciteRestante() {
        int demand = 10;

        int expectedFullCapacity = VehiculeTest.DEFAULT_CAPACITY;
        int resultFullCapacity = this.vehiculeInstance.getCapaciteRestante();
        assertEquals(expectedFullCapacity, resultFullCapacity, "Capacity should be same as defined in constructor");

        Instance i = new Instance();
        this.vehiculeInstance.setInstance(i);

        Client c = new Client(demand);
        Emplacement e1 = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);

        this.depot.addRouteTo(new Route(this.depot, e1, 1, 1));
        e1.addRouteTo(new Route(e1, this.depot, 1, 1));

        c.addEmplacement(e1);

        boolean expectedAdd = true;
        boolean resultAdd = this.vehiculeInstance.addClient(c);
        assertEquals(expectedAdd, resultAdd, "Capacity should be updated");

        int expectedCapacity = VehiculeTest.DEFAULT_CAPACITY - demand;
        int resultCapacity = this.vehiculeInstance.getCapaciteRestante();
        assertEquals(expectedCapacity, resultCapacity, "Capacity should be updated");
    }

    /**
     * Test of toString method, of class Vehicule.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.vehiculeInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
