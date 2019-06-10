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

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Point}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Point")
public class PointTest {

    private static final double DEFAULT_X = -2;
    private static final double DEFAULT_Y = 7;
    private static final double DEFAULT_COST = 4;
    private static final int DEFAULT_TIME = 9;

    private Point pointInstance;
    private Point point2;
    private Route routeInstance;

    @BeforeEach
    void createNewPoint() {
        this.pointInstance = new Point(DEFAULT_X, DEFAULT_Y);
        this.point2 = new Point(DEFAULT_Y, DEFAULT_X);
        this.routeInstance = new Route(pointInstance, point2, DEFAULT_COST, DEFAULT_TIME);
    }

    /**
     * Test of getId method, of class Point.
     */
    @Test
    @DisplayName("Get/Set Id")
    public void testId() {
        int expectedDefaultId = 0;
        int resultDefault = this.pointInstance.getId();
        assertEquals(expectedDefaultId, resultDefault, "Default value should be 0");

        int expectedId = 2;
        this.pointInstance.setId(expectedId);
        int resultValid = this.pointInstance.getId();
        assertEquals(expectedId, resultValid, "Any value should be valid");
    }

    /**
     * Test of getRouteTo method, of class Point.
     */
    @Test
    @DisplayName("Get RouteTo")
    public void testGetRouteTo() {
        this.pointInstance.addRouteTo(this.routeInstance);

        Route expectedNull = null;
        Route resultNull = this.pointInstance.getRouteTo(null);
        assertEquals(expectedNull, resultNull, "Should return null");

        Route expectedNonExistingRoute = null;
        Route resultNonExistingRoute = this.point2.getRouteTo(this.pointInstance);
        assertEquals(expectedNonExistingRoute, resultNonExistingRoute, "Should return null since there is no Route");

        Route expectedExistingRoute = this.routeInstance;
        Route resultExistingRoute = this.pointInstance.getRouteTo(this.point2);
        assertEquals(expectedExistingRoute, resultExistingRoute, "Should return a valid Route");
    }

    /**
     * Test of getX, getY method, of class Point.
     */
    @Test
    @DisplayName("Get X/Y")
    public void testGetPositions() {
        double expectedX = PointTest.DEFAULT_X;
        double resultX = this.pointInstance.getX();
        assertEquals(expectedX, resultX, "X set in constructor should be returned");

        double expectedY = PointTest.DEFAULT_Y;
        double resultY = this.pointInstance.getY();
        assertEquals(expectedY, resultY, "Y set in constructor should be returned");
    }

    /**
     * Test of getDistanceTo method, of class Point.
     */
    @Test
    @DisplayName("Get DistanceTo")
    public void testGetDistanceTo() {
        this.pointInstance.addRouteTo(this.routeInstance);

        double expectedNull = Double.POSITIVE_INFINITY;
        double resultNull = this.pointInstance.getDistanceTo(null);
        assertEquals(expectedNull, resultNull, "Null value should return infinity");

        double expectedNonExistingRoute = Double.POSITIVE_INFINITY;
        double resultNonExistingRoute = this.pointInstance.getDistanceTo(this.pointInstance);
        assertEquals(expectedNonExistingRoute, resultNonExistingRoute, "Should return infinity since there is no Route");

        double expectedExistingRoute = PointTest.DEFAULT_COST;
        double resultExistingRoute = this.pointInstance.getDistanceTo(this.point2);
        assertEquals(expectedExistingRoute, resultExistingRoute, "Should return a valid double");
    }

    /**
     * Test of getTempsTo method, of class Point.
     */
    @Test
    @DisplayName("Get TempsTo")
    public void testGetTempsTo() {
        this.pointInstance.addRouteTo(this.routeInstance);

        int expectedNull = Integer.MAX_VALUE;
        int resultNull = this.pointInstance.getTempsTo(null);
        assertEquals(expectedNull, resultNull, "Null value should return max");

        int expectedNonExistingRoute = Integer.MAX_VALUE;
        int resultNonExistingRoute = this.point2.getTempsTo(this.pointInstance);
        assertEquals(expectedNonExistingRoute, resultNonExistingRoute, "Should return max since there is no Route");

        int expectedExistingRoute = PointTest.DEFAULT_TIME;
        int resultExistingRoute = this.pointInstance.getTempsTo(this.point2);
        assertEquals(expectedExistingRoute, resultExistingRoute, "Should return a valid integer");
    }

    /**
     * Test of addRouteTo, getRoutesTo method, of class Point.
     */
    @Test
    @DisplayName("Get/Add routeTo")
    public void testAddRouteTo() {
        Map<Point, Route> expectedNoRoute = new HashMap<>();
        Map<Point, Route> resultNoRoute = this.pointInstance.getRoutesTo();
        assertEquals(expectedNoRoute.entrySet(), resultNoRoute.entrySet(), "Routes map should be empty");

        boolean expectedValidRoute = true;
        boolean resultValidMapRoute = this.pointInstance.addRouteTo(this.routeInstance);
        assertEquals(expectedValidRoute, resultValidMapRoute, "Should add valid Route");

        Map<Point, Route> expectedExistingRoute = new HashMap<>();
        expectedExistingRoute.put(this.point2, this.routeInstance);
        Map<Point, Route> resultExistingRoute = this.pointInstance.getRoutesTo();

        assertIterableEquals(expectedExistingRoute.entrySet(), resultExistingRoute.entrySet(), "Routes map should have all Route");

        boolean expectedInvalidRoute = false;
        boolean resultInvalidMapRoute = this.pointInstance.addRouteTo(new Route(this.point2, this.pointInstance, DEFAULT_COST, DEFAULT_TIME));
        assertEquals(expectedInvalidRoute, resultInvalidMapRoute, "Should not add invalid Route");

        Map<Point, Route> expectedNonExistingRoute = new HashMap<>();
        expectedNonExistingRoute.put(this.point2, this.routeInstance);
        Map<Point, Route> resultNonExistingRoute = this.pointInstance.getRoutesTo();
        assertIterableEquals(expectedNonExistingRoute.entrySet(), resultNonExistingRoute.entrySet(), "Routes map should have all valid Routes only");
    }

    /**
     * Test of toString method, of class Point.
     */
    @Test
    @DisplayName("toString")
    public void testToString() {
        String result = this.pointInstance.toString();
        assertNotNull(result, "toString must be defined and return a value");
        assertFalse(result.isEmpty(), "toString must be defined and return a value");
    }

}
