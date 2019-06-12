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

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Client}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Client")
public class ClientTest {

    private static final int DEFAULT_DEMANDE = 200;

    private Client clientInstance;

    @BeforeEach
    void createNewClient() {
        this.clientInstance = new Client(DEFAULT_DEMANDE);
    }

    /**
     * Test of addEmplacement method, of class Client.
     */
    @Test
    @DisplayName("Add Emplacement")
    public void testAddEmplacement() {
        Emplacement e1 = new Emplacement(0, 10, 0, 0);

        boolean expectedAddValid = true;
        boolean resultAddValid = this.clientInstance.addEmplacement(e1);
        assertEquals(expectedAddValid, resultAddValid, "Adding a valid emplacement should be true");

        boolean expectedAddNull = false;
        boolean resultAddNull = this.clientInstance.addEmplacement(null);
        assertEquals(expectedAddNull, resultAddNull, "Adding a null emplacement should be false");

        boolean expectedAddDuplicate = false;
        boolean resultAddDuplicate = this.clientInstance.addEmplacement(e1);
        assertEquals(expectedAddDuplicate, resultAddDuplicate, "Adding an already added emplacement should be false");
    }

    /**
     * Test of getDemande method, of class Client.
     */
    @Test
    @DisplayName("Get Demande")
    public void testGetDemande() {
        int expected = ClientTest.DEFAULT_DEMANDE;
        int result = this.clientInstance.getDemande();
        assertEquals(expected, result, "Demand set in constructor should be returned");
    }

    /**
     * Test of getPosition, setPosition methods, of class Client.
     */
    @Test
    @DisplayName("Get/Set Position")
    public void testPosition() {
        int result = this.clientInstance.getPosition();
        assertEquals(-1, result, "Default position should be -1");

        int expectedPositionValid = 2;
        this.clientInstance.setPosition(expectedPositionValid);
        int resultPositionValid = this.clientInstance.getPosition();
        assertEquals(expectedPositionValid, resultPositionValid, "A positive position should be valid");

        int expectedPositionNegative = -2;
        this.clientInstance.setPosition(expectedPositionNegative);
        int resultPositionNegative = this.clientInstance.getPosition();
        assertEquals(expectedPositionNegative, resultPositionNegative, "A negative position should be valid");
    }

    /**
     * Test of getEmplacements method, of class Client.
     */
    @Test
    @DisplayName("Get Emplacements")
    public void testGetEmplacements() {
        List<Emplacement> expected = new LinkedList<>();

        // Add a set of examples
        for (int i = 0; i < 7; i++) {
            Emplacement e = new Emplacement(i, i + 1, i, i);
            this.clientInstance.addEmplacement(e);
            expected.add(e);
        }

        List<Emplacement> result = this.clientInstance.getEmplacements();
        assertIterableEquals(expected, result, "List of emplacements should be identical to those added");
    }

    /**
     * Test of getVehicule, setVehicule methods, of class Client.
     */
    @Test
    @DisplayName("Get/Set Vehicule")
    public void testVehicule() {
        Vehicule expectedInit = null;
        Vehicule resultInit = this.clientInstance.getVehicule();
        assertEquals(expectedInit, resultInit, "Default position should be null");

        Vehicule expectedVehiculeValid = new Vehicule();
        this.clientInstance.setVehicule(expectedVehiculeValid);
        Vehicule resultVehiculeValid = this.clientInstance.getVehicule();
        assertEquals(expectedVehiculeValid, resultVehiculeValid, "A valid vehicule should be valid");

        Vehicule expectedVehiculeNull = null;
        this.clientInstance.setVehicule(expectedVehiculeNull);
        Vehicule resultVehiculeNull = this.clientInstance.getVehicule();
        assertEquals(expectedVehiculeNull, resultVehiculeNull, "A null vehicule should be valid");
    }

    /**
     * Test of toString method, of class Client.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.clientInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

    /**
     * Test of clear method, of class Client.
     */
    @Test
    @DisplayName("Clear")
    public void testClear() {
        // Add a set of examples
        for (int i = 0; i < 7; i++) {
            Emplacement e = new Emplacement(i, i + 1, i, i);
            this.clientInstance.addEmplacement(e);
        }
        this.clientInstance.setPosition(10);
        this.clientInstance.setVehicule(new Vehicule());

        this.clientInstance.clear();

        Vehicule expectedVehicule = null;
        Vehicule resultVehicule = this.clientInstance.getVehicule();

        int expectedPosition = -1;
        int resultPosition = this.clientInstance.getPosition();

        assertEquals(expectedVehicule, resultVehicule, "Clearing should set the vehicule to null");
        assertEquals(expectedPosition, resultPosition, "Clearing should set the position to -1");
    }

    /**
     * Test of check method, of class Client.
     */
    @Test
    @DisplayName("Check")
    public void testCheck() {
        boolean resultNoVehicule = this.clientInstance.check();
        assertFalse(resultNoVehicule, "A client without a vehicule is not a valid client");

        this.clientInstance.setVehicule(new Vehicule());

        boolean resultVehicule = this.clientInstance.check();
        assertTrue(resultVehicule, "A client with a vehicule is a valid client");
    }

}
