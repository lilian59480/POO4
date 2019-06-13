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
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests for {@link Instance}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Instance")
public class InstanceTest {

    /**
     * Default values for Heure Debut.
     */
    private static final int DEFAULT_HEURE_DEBUT = 0;
    /**
     * Default values for Heure Fin.
     */
    private static final int DEFAULT_HEURE_FIN = 10;
    /**
     * Default values for X.
     */
    private static final double DEFAULT_X = -2;
    /**
     * Default values for Y.
     */
    private static final double DEFAULT_Y = 7;

    /**
     * Instance instance.
     */
    private Instance instanceInstance;

    /**
     * Create new instances after each test.
     */
    @BeforeEach
    void createNewInstance() {
        this.instanceInstance = new Instance();
    }

    /**
     * Test of setCapaciteVehicule, getCapaciteVehicule method, of class
     * Instance.
     */
    @Test
    @DisplayName("Get/Set CapaciteVehicule")
    public void testCapaciteVehicule() {
        int result = this.instanceInstance.getCapaciteVehicule();
        int expected = 0;
        assertEquals(expected, result, "Default capacity should be 0");

        int expectedCapacityValid = 2;
        this.instanceInstance.setCapaciteVehicule(expectedCapacityValid);
        int resultCapacityValid = this.instanceInstance.getCapaciteVehicule();
        assertEquals(expectedCapacityValid, resultCapacityValid, "A positive position should be valid");

        Exception ex = assertThrows(IllegalArgumentException.class, new Executable() {

            private final Instance instanceInstance = InstanceTest.this.instanceInstance;

            @Override
            public void execute() throws Throwable {
                int negativeValue = -5;
                this.instanceInstance.setCapaciteVehicule(negativeValue);
                int resultNull = this.instanceInstance.getCapaciteVehicule();
            }
        });

        assertNotNull(ex, "Exception must not be null");
    }

    /**
     * Test of setCoutVehicule method, of class Instance.
     */
    @Test
    @DisplayName("Get/Set CoutVehicule")
    public void testCoutVehicule() {
        int result = this.instanceInstance.getCoutVehicule();
        int expected = 0;
        assertEquals(expected, result, "Default cost should be 0");

        int expectedCostValid = 2;
        this.instanceInstance.setCoutVehicule(expectedCostValid);
        int resultCostValid = this.instanceInstance.getCoutVehicule();
        assertEquals(expectedCostValid, resultCostValid, "A positive cost should be valid");

        Exception ex = assertThrows(IllegalArgumentException.class, new Executable() {

            private final Instance instanceInstance = InstanceTest.this.instanceInstance;

            @Override
            public void execute() throws Throwable {
                int negativeValue = -5;
                this.instanceInstance.setCoutVehicule(negativeValue);
                int resultNull = this.instanceInstance.getCoutVehicule();
            }
        });

        assertNotNull(ex, "Exception must not be null");
    }

    /**
     * Test of setDepot, getDepot method, of class Instance.
     */
    @Test
    @DisplayName("Get/Set Depot")
    public void testDepot() {
        Depot expectedInit = null;
        Depot resultInit = this.instanceInstance.getDepot();
        assertEquals(expectedInit, resultInit, "Default position should be null");

        Depot expectedVehiculeValid = new Depot(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
        this.instanceInstance.setDepot(expectedVehiculeValid);
        Depot resultVehiculeValid = this.instanceInstance.getDepot();
        assertEquals(expectedVehiculeValid, resultVehiculeValid, "A valid depot should be valid");

        Depot expectedVehiculeNull = null;
        this.instanceInstance.setDepot(expectedVehiculeNull);
        Depot resultVehiculeNull = this.instanceInstance.getDepot();
        assertEquals(expectedVehiculeNull, resultVehiculeNull, "A null depot should be valid");
    }

    /**
     * Test of setClients, getClients method, of class Instance.
     */
    @Test
    @DisplayName("Get/Set Clients")
    public void testClients() {
        List<Client> expectedInit = new ArrayList<>();
        List<Client> resultInit = this.instanceInstance.getClients();
        assertIterableEquals(expectedInit, resultInit, "Default position should be null");

        List<Client> expectedListValid = Arrays.asList(
                new Client(1),
                new Client(2)
        );
        this.instanceInstance.setClients(expectedListValid);
        List<Client> resultListValid = this.instanceInstance.getClients();
        assertIterableEquals(expectedListValid, resultListValid, "A valid depot should be valid");

        Exception ex = assertThrows(NullPointerException.class, new Executable() {

            private final Instance instanceInstance = InstanceTest.this.instanceInstance;

            @Override
            public void execute() throws Throwable {
                List<Client> expectedListNull = null;
                this.instanceInstance.setClients(expectedListNull);
                List<Client> resultListNull = this.instanceInstance.getClients();
            }
        });

        assertNotNull(ex, "Exception must not be null");
    }

    /**
     * Test of getPlanningCurrent, setPlanningCurrent method, of class Instance.
     */
    @Test
    @DisplayName("Get/Set PlanningCurrent")
    public void testPlanningCurrent() {
        Planning expectedInit = null;
        Planning resultInit = this.instanceInstance.getPlanningCurrent();
        assertNotEquals(expectedInit, resultInit, "Default planning should not be null");

        Planning expectedPlanningValid = new Planning();
        this.instanceInstance.setPlanningCurrent(expectedPlanningValid);
        Planning resultPlanningValid = this.instanceInstance.getPlanningCurrent();
        assertEquals(expectedPlanningValid, resultPlanningValid, "A valid planning should be valid");

        Planning expectedPlanningNull = null;
        this.instanceInstance.setPlanningCurrent(expectedPlanningNull);
        Planning resultNull = this.instanceInstance.getPlanningCurrent();
        assertEquals(expectedPlanningNull, resultNull, "A null planning should be valid");
    }

    /**
     * Test of getNbVehicules, setNbVehicules method, of class Instance.
     */
    @Test
    @DisplayName("Get/Set nbVehicules")
    public void testNbVehicules() {
        int result = this.instanceInstance.getNbVehicules();
        int expected = 0;
        assertEquals(expected, result, "Default number of vehicules should be 0");

        int expectedNbVehiculeValid = 2;
        this.instanceInstance.setNbVehicules(expectedNbVehiculeValid);
        int resultNbVehiculeValid = this.instanceInstance.getNbVehicules();
        assertEquals(expectedNbVehiculeValid, resultNbVehiculeValid, "A positive number of vehicules should be valid");

        Exception ex = assertThrows(IllegalArgumentException.class, new Executable() {

            private final Instance instanceInstance = InstanceTest.this.instanceInstance;

            @Override
            public void execute() throws Throwable {
                int negativeValue = -5;
                this.instanceInstance.setNbVehicules(negativeValue);
                int resultNull = this.instanceInstance.getNbVehicules();
            }
        });

        assertNotNull(ex, "Exception must not be null");
    }

    /**
     * Test of addNewPlanning method, of class Instance.
     */
    @Test
    @DisplayName("Add Planning")
    public void testAddNewPlanning() {
        Vehicule v = new Vehicule();
        this.instanceInstance.setVehicules(Arrays.asList(v));

        Planning expectedPlanning = new Planning();
        expectedPlanning.addVehicule(v);

        this.instanceInstance.addNewPlanning();
        Planning resultPlanning = this.instanceInstance.getPlanningCurrent();
        assertIterableEquals(expectedPlanning.getVehicules(), resultPlanning.getVehicules(), "New planning should have vehicules");
    }

    /**
     * Test of addVehicule method, of class Instance.
     */
    @Test
    @DisplayName("Add vehicule")
    public void testAddVehicule() {
        int expectedVehiculeLength = 1;

        this.instanceInstance.addVehicule();

        int actualVehiculeLength = this.instanceInstance.getVehicules().size();

        assertEquals(expectedVehiculeLength, actualVehiculeLength, "Vehicule should be added");
    }

    /**
     * Test of toString method, of class Instance.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.instanceInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
