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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Route}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Route")
public class RouteTest {

    @Test
    @DisplayName("2 Roads are equals even if costs are different")
    /**
     * @todo Create a proper test.
     */
    public void roadEquality() {

        Emplacement e1 = new Emplacement(0, 0, 0, 0);
        Emplacement e2 = new Emplacement(0, 0, 5, 5);

        Route r1 = new Route(e1, e2, 100, 10);
        Route r2 = new Route(e1, e2, 10, 1);

        assertEquals(r1, r2, "Strangely, 2 roads are equals even if costs are differents");
    }

}
