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

import algo.iterative.NaiveSolver;
import gui.metier.VehiculeModelTable;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Vehicule;

/**
 *
 * @author thibaut
 */
public class MyCanvas extends Canvas {

    private Instance instance;
    private int draggedX = 400;
    private int draggedY = 400;
    private int zoom = 4;
    private VehiculeModelTable vModel;

    /**
     * initial constructor
     *
     * @param i Instance
     * @param vModel vModelTable
     */
    public MyCanvas(Instance i, VehiculeModelTable vModel) {
        this.instance = i;
        this.vModel = vModel;
        this.setBackground(Color.WHITE);
    }

    /**
     * Paint method
     *
     * @param g graphic
     */
    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        // DESSIN D'une Instance
        NaiveSolver ds = new NaiveSolver(this.instance);
        ds.solve();
        if (ds == null) {
            return;
        }

        Instance i = ds.getInstance();
        if (i == null) {
            return;
        }
        this.drawBorder(g);
        this.drawInstance(g, i);
        g.setColor(c);
    }

    /**
     * Draw Canva Border
     *
     * @param g graphic
     */
    public void drawBorder(Graphics g) {
        super.paint(g);
       
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(10, 10, width - 10, 10);
        g2d.drawLine(width - 10, 10, width - 10, height - 10);
        g2d.drawLine(width - 10, height - 10, 10, height - 10);
        g2d.drawLine(10, height - 10, 10, 10);
       
  
    }

    /**
     * Draw an Emplacement
     *
     * @param g Graphics zone.
     * @param e Emplacement to draw.
     */
    private void drawEmplacement(Graphics g, Emplacement e) {
        if (e == null) {
            return;
        }

        if (e instanceof Depot) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        g.fillRect((int) ((int) e.getX() * this.zoom + this.draggedX - 3), (int) e.getY() * this.zoom + this.draggedY - 3, 6, 6);

    }

    /**
     * Draw an instance.
     *
     * @param g Graphics zone.
     * @param i Instance to draw.
     */
    private void drawInstance(Graphics g, Instance i) {
        if (i == null) {
            return;
        }
        //Dessin du depot
        Depot d = i.getDepot();

        List<Vehicule> vehicules = this.vModel.getDisplayVehicules();
        System.out.println("test vehicule Canvas" + vehicules.toString());
        int code = 0;

        for (Vehicule v : vehicules) {
            Emplacement source = d;
            for (Emplacement destination : v.getEmplacements()) {
                /*
                 * Between 360 -> getHSBColor(X, 1, 0.8)
                 */
                g.setColor(Color.getHSBColor(code / 360.0f, 1, 0.8f));
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g2.drawLine((int) source.getX() * this.zoom + this.draggedX, (int) source.getY() * this.zoom + this.draggedY, (int) destination.getX() * this.zoom + this.draggedX, (int) destination.getY() * this.zoom + this.draggedY);
                source = destination;
                this.drawEmplacement(g, destination);

            }
            g.drawLine((int) source.getX() * this.zoom + this.draggedX, (int) source.getY() * this.zoom + this.draggedY, (int) d.getX() * this.zoom + this.draggedX, (int) d.getY() * this.zoom + this.draggedY);
            code += 20;
        }
        this.drawEmplacement(g, d);
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
        this.zoom = 1;
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

}
