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
package gui;

import gui.metier.ClientModelTable;
import gui.metier.VehiculeModelTable;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Point;
import model.Vehicule;

/**
 * Itineraire drawing canvas
 *
 * @author thibaut
 */
public class MyCanvas extends Canvas {

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 20190612230051L;

    /**
     * Instance to draw.
     */
    private final Instance instance;
    /**
     * X origin.
     */
    private int draggedX;
    /**
     * Y origin.
     */
    private int draggedY;
    /**
     * Current Zoom.
     */
    private int zoom = 4;
    /**
     * Vehicule table model.
     */
    private VehiculeModelTable vModel;
    /**
     * Vehicule table model.
     */
    private ClientModelTable cModel;

    /**
     * Map of all the colors
     */
    private Map<Object, Integer> colorCodes;
    /**
     * Last color code used
     */
    private int lastColorCode;
    
    /**
     * Step between each color
     */
    private static final int COLOR_STEP = 26;
    
    /**
     * initial constructor
     *
     * @param i Instance
     * @param vModel vModelTable
     * @param cModel cModelTable
     */
    public MyCanvas(Instance i, VehiculeModelTable vModel, ClientModelTable cModel) {
        super();
        this.instance = i;
        this.vModel = vModel;
        this.cModel = cModel;
        this.colorCodes = new HashMap<>();
        this.setBackground(Color.WHITE);
    }

    /**
     * Paint method
     *
     * @param g graphic
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;
        this.drawInstance(graphics2D, this.instance);
        this.drawBorder(graphics2D);
    }

    /**
     * Draw Canva Border
     *
     * @param g graphic
     */
    public void drawBorder(Graphics2D g) {
        int width = this.getWidth();
        int height = this.getHeight();
        g.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(0, 0, width, 0);
        g.drawLine(width, 0, width, height);
        g.drawLine(width, height, 0, height);
        g.drawLine(0, height, 0, 0);
    }

    /**
     * Draw an Emplacement
     *
     * @param g Graphics2D zone.
     * @param e Emplacement to draw.
     * @param code int
     */
    private void drawEmplacement(Graphics2D g, Emplacement e, int code) {
        if (e == null) {
            return;
        }

        if (e instanceof Depot) {
            g.setColor(Color.RED);
        } else {
            g.setColor(this.getColor(code));
        }
        g.fillRect(this.getDrawedX(e) - 3, this.getDrawedY(e) - 3, 6, 6);

    }

    /**
     * Draw an instance.
     *
     * @param g Graphics2D zone.
     * @param i Instance to draw.
     */
    private void drawInstance(Graphics2D g, Instance i) {
        if (i == null) {
            return;
        }
        this.drawRoutes(g, i);
        this.drawClients(g, i);
        Point origin = new Emplacement();
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g.drawLine(this.getDrawedX(origin), 0, this.getDrawedX(origin), this.getHeight());
        g.drawLine(0, this.getDrawedY(origin), this.getWidth(), this.getDrawedY(origin));

    }

    /**
     * Draw Route.
     *
     * @param g graphic
     * @param i instance
     */
    private void drawRoutes(Graphics2D g, Instance i) {
        //Dessin du depot
        Depot d = i.getDepot();

        List<Vehicule> vehicules = this.vModel.getDisplayVehicules();

        for (Vehicule v : vehicules) {
            Emplacement source = d;
            for (Emplacement destination : v.getEmplacements()) {
                /*
                 * Between 360 -> getHSBColor(X, 1, 0.8)
                 */
                g.setColor(this.getColor(v));
                g.setStroke(new BasicStroke(3));
                g.drawLine(this.getDrawedX(source), this.getDrawedY(source), this.getDrawedX(destination), this.getDrawedY(destination));
                source = destination;

            }
            g.drawLine(this.getDrawedX(source), this.getDrawedY(source), this.getDrawedX(d), this.getDrawedY(d));
        }
        this.drawEmplacement(g, d, 0);
    }

    /**
     *  draw Client.
     * @param g graphic
     * @param i instance
     */
    private void drawClients(Graphics2D g, Instance i) {

        List<Client> clients = this.cModel.getDisplayClients();
        //Dessin du depot
        Depot d = i.getDepot();
        for (Client c : clients) {
            for (Emplacement e : c.getEmplacements()) {
                /*
                 * Between 360 -> getHSBColor(X, 1, 0.8)
                 */
                this.drawEmplacement(g, e, this.getColorCode(c));
            }
        }

    }

    /**
     * zoom In.
     *
     */
    public void zoomIn() {
        this.zoom++;
    }

    /**
     * zoom out.
     *
     */
    public void zoomOut() {
        if (this.zoom != 1) {
            this.zoom--;
        }
    }

    /**
     * Center canvas.
     */
    public void center() {
        int centerX = this.getHeight() / 2;
        int centerY = this.getWidth() / 2;
        this.draggedCanvas(centerX, centerY);
        this.repaint();
    }

    /**
     * Set dragged X-Y to the canvas.
     *
     * @param x abscisse
     * @param y ordonne
     */
    public void draggedCanvas(int x, int y) {
        this.draggedX = x;
        this.draggedY = y;
    }

    /**
     * Get zoom
     *
     * @return zoom.
     */
    public int getZoom() {
        return this.zoom;
    }

    /**
     * Get X coordinate for the Canvas.
     *
     * @param p Point to draw
     * @return The X coordinate with scaling and drag
     */
    private int getDrawedX(Point p) {
        return (int) (p.getX() * this.zoom + this.draggedX);
    }

    /**
     * Get Y coordinate for the Canvas.
     *
     * @param p Point to draw
     * @return The Y coordinate with scaling and drag
     */
    private int getDrawedY(Point p) {
        return (int) (p.getY() * this.zoom + this.draggedY);
    }

    /**
     * Get the color code of the object.
     *
     * @param o Object 
     * @return The color code of the object
     */
    private int getColorCode(Object o) {
        if( this.colorCodes.containsKey(o) ) {
            return this.colorCodes.get(o);
        }
        this.lastColorCode += MyCanvas.COLOR_STEP;
        this.colorCodes.put(o, this.lastColorCode);
        return this.lastColorCode;
    }
    
    /**
     * Get the color of the object.
     *
     * @param o Object 
     * @return The color of the object
     */
    private Color getColor(Object o) {
        int colorCode = this.getColorCode(o);
        return this.getColor(colorCode);
    }
    
    /**
     * Get a color from an int.
     *
     * @param colorCode the color code
     * @return The color from the color code
     */
    private Color getColor(int colorCode) {
        return Color.getHSBColor(colorCode / 360.0f, 1, 0.8f);
    }
}
