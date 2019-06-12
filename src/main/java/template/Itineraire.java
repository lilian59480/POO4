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
import dao.DaoException;
import dao.DaoFactory;
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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Vehicule;
import template.metier.ClientModelTable;
import template.metier.VehiculeModelTable;

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
        List<Client> clients = i.getClients();
        List<Vehicule> vehicules  = i.getVehicules();
        this.jTableClient.setModel(new ClientModelTable(clients));
        this.jTableVehicule.setModel(new VehiculeModelTable(vehicules));
        
        this.initialisationFenetre();

        this.costNLabel.setText("" + this.instance.getCoutVehicule());
        this.numberVLabel.setText("" + this.instance.getNbVehicules());
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
        levelZoomLabel = new javax.swing.JLabel();
        fitInButton = new javax.swing.JButton();
        zoomButton = new javax.swing.JButton();
        costLabel = new javax.swing.JLabel();
        costNLabel = new javax.swing.JLabel();
        externalVlabel = new javax.swing.JLabel();
        numberVLabel = new javax.swing.JLabel();
        clientEmplacementLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableVehicule = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableClient = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(186, 186, 186));

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

        levelZoomLabel.setText("100%");

        fitInButton.setText("Fit In Window");
        fitInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fitInButtonActionPerformed(evt);
            }
        });

        zoomButton.setText("Center");

        costLabel.setText("Cost of this instance :");

        costNLabel.setText("000000000");

        externalVlabel.setText("Number of external vehicules:");

        numberVLabel.setText("00");

        clientEmplacementLabel.setText("Clients and Emplacements ");

        jTableVehicule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableVehicule);

        jTableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableClient);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(externalVlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clientEmplacementLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(levelZoomLabel)
                        .addGap(41, 41, 41)
                        .addComponent(zoomButton)
                        .addGap(50, 50, 50)
                        .addComponent(fitInButton)
                        .addGap(277, 277, 277))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(costNLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(numberVLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(714, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(levelZoomLabel)
                    .addComponent(fitInButton)
                    .addComponent(zoomButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costNLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(externalVlabel)
                    .addComponent(numberVLabel))
                .addGap(40, 40, 40)
                .addComponent(clientEmplacementLabel)
                .addGap(200, 200, 200)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(175, 175, 175)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(542, Short.MAX_VALUE)))
        );

        fitInButton.getAccessibleContext().setAccessibleName("fitIn");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Action Perfomed.
     *
     * @param evt Action evt
     */
    private void fitInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fitInButtonActionPerformed
        // TODO add your handling code here:
        canvas2.center();
        canvas2.repaint();
    }//GEN-LAST:event_fitInButtonActionPerformed
    /**
     * Mo
     *
     * @param evt Mouse event
     */
    private void canvas2MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_canvas2MouseWheelMoved
        // TODO add your handling code here:

        if (evt.getWheelRotation() > 0) {
            //zoom in (amount)
            System.out.println("Zoom In/ Scrolled UP" + this.canvas2.getZoom());
            canvas2.zoomIn();
            canvas2.repaint();

        } else {
            //zoom out (amount)
            System.out.println("Zoom out/ Scrolled Down" + this.canvas2.getZoom());
            this.canvas2.zoomOut();
            this.canvas2.repaint();

        }

        this.levelZoomLabel.setText(this.canvas2.getZoom() * 10 + "%");


    }//GEN-LAST:event_canvas2MouseWheelMoved
    /**
     * mouse dragged
     *
     * @param evt Dragg event
     */
    private void canvas2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvas2MouseDragged
        // TODO add your handling code here:
        this.canvas2.draggedCanvas(evt.getX(), evt.getY());
        this.canvas2.repaint();

    }//GEN-LAST:event_canvas2MouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*
    private java.awt.Canvas canvas2;
    */
    private MyCanvas canvas2;
    private javax.swing.JLabel clientEmplacementLabel;
    private javax.swing.JLabel costLabel;
    private javax.swing.JLabel costNLabel;
    private javax.swing.JLabel externalVlabel;
    private javax.swing.JButton fitInButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableClient;
    private javax.swing.JTable jTableVehicule;
    private javax.swing.JLabel levelZoomLabel;
    private javax.swing.JLabel numberVLabel;
    private javax.swing.JButton zoomButton;
    // End of variables declaration//GEN-END:variables
}
