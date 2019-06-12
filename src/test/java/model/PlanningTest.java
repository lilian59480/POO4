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

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Planning}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Planning")
public class PlanningTest {

    private Planning planningInstance;

    @BeforeEach
    void createNewInstance() {
        this.planningInstance = new Planning();
    }

    /**
     * Test of getVehicules, addVehicule method, of class Planning.
     */
    @Test
    @DisplayName("Get/Add Vehicules")
    public void testVehicules() {
        List<Vehicule> expectedInit = null;
        List<Vehicule> resultInit = this.planningInstance.getVehicules();
        assertNotEquals(expectedInit, resultInit, "Default vehicule list should not be null");

        Vehicule v = new Vehicule();
        boolean expectAddValid = true;
        boolean actualAddValid = this.planningInstance.addVehicule(v);

        assertEquals(expectAddValid, actualAddValid, "A valid vehicule should be added");

        Vehicule v2 = null;
        boolean expectAddNull = false;
        boolean actualAddNull = this.planningInstance.addVehicule(v2);
        assertEquals(expectAddNull, actualAddNull, "A null vehicule should not be added");
    }

    /**
     * Test of toString method, of class Planning.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.planningInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
