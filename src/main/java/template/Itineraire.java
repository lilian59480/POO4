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
package template;

import algo.NaiveSolver;
import io.input.InstanceFileParser;
import io.input.ParserException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Vehicule;

/**
 * Planning representation.
 *
 * @todo Follow wireframes.
 * @author thibaut
 */
public class Itineraire extends javax.swing.JFrame {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Itineraire.class.getName());

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 20190510150842L;

    /**
     * Creates new form itineraire.
     */
    public Itineraire() {
        this.initComponents();
        this.initialisationFenetre();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        // DESSIN D'une Instance
        NaiveSolver ds = this.createInstance();
        if (ds == null) {
            return;
        }

        Instance i = ds.getInstance();
        if (i == null) {
            return;
        }

        this.drawInstance(g, i);
        g.setColor(c);

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
        g.fillRect((int) e.getX() * 4 + 400 - 3, (int) e.getY() * 4 + 400 - 3, 6, 6);

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

        List<Vehicule> vehicules = i.getVehicules();
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
                g2.drawLine((int) source.getX() * 4 + 400, (int) source.getY() * 4 + 400, (int) destination.getX() * 4 + 400, (int) destination.getY() * 4 + 400);
                source = destination;
                drawEmplacement(g, destination);

            }
            g.drawLine((int) source.getX() * 4 + 400, (int) source.getY() * 4 + 400, (int) d.getX() * 4 + 400, (int) d.getY() * 4 + 400);
            code += 20;
        }
        drawEmplacement(g, d);

    }

    /**
     * ???.
     *
     * @todo What! Please refactor.
     * @return A solver.
     */
    private NaiveSolver createInstance() {
        Instance i;
        try {
            InstanceFileParser ifp = new InstanceFileParser();
            i = ifp.parse(new File("src/main/resources/instances/instance_0-triangle.txt"));
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return null;
        }
        NaiveSolver ds = new NaiveSolver(i);
        ds.solve();
        return ds;
    }

    /**
     * Initialise this window.
     */
    private void initialisationFenetre() {

        this.setVisible(true);
        this.setTitle("Itineraire");
        this.getContentPane().setBackground(Color.white);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(400, 200));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Main
     *
     * @todo Remove this.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Itineraire i = new Itineraire();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
