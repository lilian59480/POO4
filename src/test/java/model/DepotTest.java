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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests for {@link Depot}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Depot")
public class DepotTest {

    @Test
    @DisplayName("Creating from Emplacement should work")
    public void instanciateFromEmplacement() {
        Emplacement e = new Emplacement(10, 20, 30.20, 40.24);

        Depot d = new Depot(e);

        assertEquals(10, d.getHeureDebut(), "Emplacement's heureDebut field must be propagated");
        assertEquals(20, d.getHeureFin(), "Emplacement's heureFin field must be propagated");
        assertEquals(30.20, d.getX(), "Emplacement's X field must be propagated");
        assertEquals(40.24, d.getY(), "Emplacement's Y field must be propagated");

    }

    @Test
    @DisplayName("Creating from null should not work")
    public void instanciateFromNullEmplacement() {
        Emplacement e = null;

        Exception ex = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Depot d = new Depot(e);
            }
        }, "NullPointerException must be raised");

        assertNotNull(ex, "Exception must not be null");
    }

}
