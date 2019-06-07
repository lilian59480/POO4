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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests for {@link Emplacement}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Emplacement")
public class EmplacementTest {

    private static final int DEFAULT_HEURE_DEBUT = 0;
    private static final int DEFAULT_HEURE_FIN = 10;
    private static final double DEFAULT_X = -2;
    private static final double DEFAULT_Y = 7;

    private Emplacement emplacementInstance;

    @BeforeEach
    void createNewEmplacement() {
        this.emplacementInstance = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Check if HeureDebut is less than HeureFin.
     */
    @Test
    @DisplayName("HeureDebut should be less than HeureFin")
    public void checkHeure() {

        Exception ex = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Emplacement e = new Emplacement(DEFAULT_HEURE_FIN, DEFAULT_HEURE_DEBUT, DEFAULT_X, DEFAULT_Y);
            }
        });

        assertNotNull(ex, "Exception must not be null");

    }

    /**
     * Test of getHeureDebut method, of class Emplacement.
     */
    @Test
    public void testGetHeureDebut() {
        int expectedHeureDebut = DEFAULT_HEURE_DEBUT;
        int resultHeureDebut = this.emplacementInstance.getHeureDebut();
        assertEquals(expectedHeureDebut, resultHeureDebut, "HeureDebut set in constructor should be returned");
    }

    /**
     * Test of getHeureFin method, of class Emplacement.
     */
    @Test
    public void testGetHeureFin() {
        int expectedHeureFin = DEFAULT_HEURE_FIN;
        int resultHeureFin = this.emplacementInstance.getHeureFin();
        assertEquals(expectedHeureFin, resultHeureFin, "HeureFin set in constructor should be returned");
    }

    /**
     * Test of getClient, setClient method, of class Emplacement.
     */
    @Test
    public void testClient() {
        Client expectedInit = null;
        Client resultInit = this.emplacementInstance.getClient();
        assertEquals(expectedInit, resultInit, "Default position should be null");

        Client expectedClientValid = new Client();
        this.emplacementInstance.setClient(expectedClientValid);
        Client resultClientValid = this.emplacementInstance.getClient();
        assertEquals(expectedClientValid, resultClientValid, "A valid client should be valid");

        Exception ex = assertThrows(NullPointerException.class, new Executable() {

            private final Emplacement emplacementInstance = EmplacementTest.this.emplacementInstance;

            @Override
            public void execute() throws Throwable {
                Client expecteClientNull = null;
                this.emplacementInstance.setClient(expecteClientNull);
                Client resultClientNull = this.emplacementInstance.getClient();
            }
        });

        assertNotNull(ex, "Exception must not be null");
    }

    /**
     * Test of toString method, of class Emplacement.
     */
    @Test
    public void testToString() {
        String result = this.emplacementInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
