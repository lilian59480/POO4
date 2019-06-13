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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Depot}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Depot")
public class DepotTest {

    /**
     * Depot instance.
     */
    private Depot depotInstance;

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
     * Create new instances after each test.
     */
    @BeforeEach
    void createNewDepot() {
        this.depotInstance = new Depot(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Check if creating from an Emplacement is working.
     */
    @Test
    @DisplayName("Creating from Emplacement should work")
    public void instanciateFromEmplacement() {
        Emplacement e = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);

        Depot d = new Depot(e);

        int expectedHeureDebut = DEFAULT_HEURE_DEBUT;
        int resultHeureDebut = d.getHeureDebut();
        assertEquals(expectedHeureDebut, resultHeureDebut, "Emplacement's heureDebut field should be propagated");

        int expectedHeureFin = DEFAULT_HEURE_FIN;
        int resultHeureFin = d.getHeureFin();
        assertEquals(expectedHeureFin, resultHeureFin, "Emplacement's heureFin field should be propagated");

        double expectedX = DEFAULT_X;
        double resultX = d.getX();
        assertEquals(expectedX, resultX, "Emplacement's X field should be propagated");

        double expectedY = DEFAULT_Y;
        double resultY = d.getY();
        assertEquals(expectedY, resultY, "Emplacement's Y field should be propagated");
    }

    /**
     * Test of toString method, of class Depot.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.depotInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
