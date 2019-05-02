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

/**
 *
 * @author Corentin
 */
public class Point {
    private double x;
    private double y;
    private Map<Point, Route> routeTo;
    private Map<Point, Route> routeFrom;
    
    public Point(){
        this.routeTo = new HashMap();
        this.routeFrom = new HashMap();
    }
    
    public Point(double x, double y){
        this();
        this.x = x;
        this.y = y;
    }
    
    public Route getRouteTo(Point p)
    {
        return this.routeTo.get(p);
    }

    public Route getRouteFrom(Point p)
    {
        return this.routeFrom.get(p);
    }
}
