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
 * Tests for {@link Point}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Point")
public class PointTest {

    @Test
    @DisplayName("Cost should be valid between 2 points")
    /**
     * @todo Create a proper test.
     */
    public void costBetween2Points() {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 5);

        Route r = new Route(p1, p2, 100, 10);
        p1.addRouteTo(r);

        assertEquals(100, p1.getDistanceTo(p2), "Cost between (0,0) and (5,5) is not valid");
    }

}
