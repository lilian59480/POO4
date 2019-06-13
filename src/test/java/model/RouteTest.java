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
 * Tests for {@link Route}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Route")
public class RouteTest {

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
     * Default cost.
     */
    private static final double DEFAULT_COST = 4;
    /**
     * Default time.
     */
    private static final int DEFAULT_TIME = 9;

    /**
     * Primary point required as a Route is between two points.
     */
    private Point pointInstance;
    /**
     * Secondary point required as a Route is between two points.
     */
    private Point point2;
    /**
     * Route instance.
     */
    private Route routeInstance;

    /**
     * Create new instances after each test.
     */
    @BeforeEach
    void createNewRoute() {
        this.pointInstance = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_X, DEFAULT_Y);
        this.point2 = new Emplacement(DEFAULT_HEURE_DEBUT, DEFAULT_HEURE_FIN, DEFAULT_Y, DEFAULT_X);
        this.routeInstance = new Route(this.pointInstance, this.point2, DEFAULT_COST, DEFAULT_TIME);
        this.pointInstance.addRouteTo(this.routeInstance);
    }

    /**
     * Test of getFrom method, of class Route.
     */
    @Test
    @DisplayName("Get From")
    public void testGetFrom() {
        Point expectedPoint = this.pointInstance;
        Point resultPoint = this.routeInstance.getFrom();
        assertEquals(expectedPoint, resultPoint, "From set in constructor should be returned");
    }

    /**
     * Test of getTo method, of class Route.
     */
    @Test
    @DisplayName("Get To")
    public void testGetTo() {
        Point expectedPoint = this.point2;
        Point resultPoint = this.routeInstance.getTo();
        assertEquals(expectedPoint, resultPoint, "To set in constructor should be returned");
    }

    /**
     * Test of getCout method, of class Route.
     */
    @Test
    @DisplayName("Get Cost")
    public void testGetCout() {
        double expectedCost = RouteTest.DEFAULT_COST;
        double resultCost = this.routeInstance.getCout();
        assertEquals(expectedCost, resultCost, "Cost set in constructor should be returned");
    }

    /**
     * Test of getTemps method, of class Route.
     */
    @Test
    @DisplayName("Get Temps")
    public void testGetTemps() {
        int expectedTime = RouteTest.DEFAULT_TIME;
        int resultTime = this.routeInstance.getTemps();
        assertEquals(expectedTime, resultTime, "Time set in constructor should be returned");
    }

    /**
     * Test of toString method, of class Route.
     */
    @Test
    public void testToString() {
        String result = this.routeInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
