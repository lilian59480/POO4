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

import algo.iterative.NaiveSolver;
import io.input.InstanceFileParser;
import io.input.ParserException;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
public class Itineraire extends JFrame { // NOSONAR

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 20190510150842L;

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Itineraire.class.getName());
    private Instance instance;

    /**
     * Creates new form itineraire with a null Instance.
     */
    /**
     * Creates new form itineraire.
     *
     * @param i Instance to display
     */
    public Itineraire(Instance i) {
        this.instance = i;
        this.initComponents();
        this.initialisationFenetre();
        LOGGER.log(Level.INFO, "Instance :", this.instance.toString());
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

        canvas2 = new MyCanvas(this.instance);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        canvas2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                canvas2MouseDragged(evt);
            }
        });
        canvas2.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                canvas2MouseWheelMoved(evt);
            }
        });

        jLabel1.setText("Zoom : ");

        jLabel2.setText("100%");

        jButton1.setText("Zoom in");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Zoom out");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 539, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31)
                        .addComponent(jButton1)
                        .addGap(43, 43, 43)
                        .addComponent(jButton2))
                    .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Action Perfomed.
     *
     * @param evt Action evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * Mo
     *
     * @param evt Mouse event
     */
    private void canvas2MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_canvas2MouseWheelMoved
        // TODO add your handling code here:

        if (evt.getWheelRotation() > 0) {
            //zoom in (amount)
            System.out.println("Zoom In/ Scrolled UP");
            canvas2.zoomIn();
            canvas2.repaint();

        } else {
            //zoom out (amount)
            System.out.println("Zoom out/ Scrolled Down");
            this.canvas2.zoomOut();
            this.canvas2.repaint();

        }

    }//GEN-LAST:event_canvas2MouseWheelMoved
    /**
     * mouse dragged
     *
     * @param evt Dragg event
     */
    private void canvas2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2MouseDragged
        // TODO add your handling code here:
        System.out.println("test :" + evt.getX() + " " + evt.getY());
        this.canvas2.draggedCanvas(evt.getX(), evt.getY());
        this.canvas2.repaint();

    }//GEN-LAST:event_canvas2MouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*
    private java.awt.Canvas canvas2;
    */
    private MyCanvas canvas2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
