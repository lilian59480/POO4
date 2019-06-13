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
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import model.Client;
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
    /**
     * Instance to draw.
     */
    private Instance instance;
    /**
     * Model table used for Vehicules.
     */
    private VehiculeModelTable vehiculeModelTable;
    private ClientModelTable clientModelTable;

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
        List<Vehicule> vehicules = i.getVehicules();
        List<Client> clients = i.getClients();
        this.vehiculeModelTable = new VehiculeModelTable(vehicules);
        this.clientModelTable = new ClientModelTable(clients);
        this.initComponents();
        this.ClientTable.setModel(this.clientModelTable);
        this.ClientTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Itineraire.this.canvas2.repaint();
            }
        });
        this.jTableVehicule.setModel(this.vehiculeModelTable);
        this.jTableVehicule.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                Itineraire.this.canvas2.repaint();
            }
        });

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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.canvas2.center();
    }

    //CHECKSTYLE:OFF
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas2 = new MyCanvas(this.instance, this.vehiculeModelTable, this.clientModelTable);
        jLabel1 = new javax.swing.JLabel();
        levelZoomLabel = new javax.swing.JLabel();
        zoomButton = new javax.swing.JButton();
        costLabel = new javax.swing.JLabel();
        costNLabel = new javax.swing.JLabel();
        externalVlabel = new javax.swing.JLabel();
        numberVLabel = new javax.swing.JLabel();
        clientEmplacementLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableVehicule = new javax.swing.JTable();
        vehiculeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ClientTable = new javax.swing.JTable();
        showClientButton = new javax.swing.JButton();
        hideClientButton = new javax.swing.JButton();
        showVehiculeButton = new javax.swing.JButton();
        hideVehiculeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Itineraire");
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

        jLabel1.setText("Zoom level : ");

        levelZoomLabel.setText("4");

        zoomButton.setText("Center");
        zoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomButtonActionPerformed(evt);
            }
        });

        costLabel.setText("Cost of this instance :");

        costNLabel.setText("000000000");

        externalVlabel.setText("Number of vehicules:");

        numberVLabel.setText("00");

        clientEmplacementLabel.setText("Vehicules");

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
        jTableVehicule.setCellSelectionEnabled(true);
        jTableVehicule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVehiculeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableVehicule);

        vehiculeLabel.setText("Clients");

        ClientTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(ClientTable);

        showClientButton.setText("Display All");
        showClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showClientButtonActionPerformed(evt);
            }
        });

        hideClientButton.setText("Hide All");
        hideClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideClientButtonActionPerformed(evt);
            }
        });

        showVehiculeButton.setText("Display All");
        showVehiculeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVehiculeButtonActionPerformed(evt);
            }
        });

        hideVehiculeButton.setText("Hide All");
        hideVehiculeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideVehiculeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(externalVlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(levelZoomLabel)
                                .addGap(41, 41, 41)
                                .addComponent(zoomButton)
                                .addGap(426, 426, 426))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(costNLabel)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(clientEmplacementLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(showVehiculeButton)
                                .addGap(34, 34, 34)
                                .addComponent(hideVehiculeButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(vehiculeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(showClientButton)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hideClientButton)
                                    .addComponent(numberVLabel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                        .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(costLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(costNLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(externalVlabel)
                            .addComponent(numberVLabel))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vehiculeLabel)
                            .addComponent(showClientButton)
                            .addComponent(hideClientButton))
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clientEmplacementLabel)
                            .addComponent(showVehiculeButton)
                            .addComponent(hideVehiculeButton))
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(levelZoomLabel)
                            .addComponent(zoomButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //CHECKSTYLE:ON

    /**
     * Mo
     *
     * @param evt Mouse event
     */
    private void canvas2MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_canvas2MouseWheelMoved
        // TODO add your handling code here:

        if (evt.getWheelRotation() < 0) {
            //zoom in (amount)
            this.canvas2.zoomIn();
            this.canvas2.repaint();

        } else {
            //zoom out (amount)
            this.canvas2.zoomOut();
            this.canvas2.repaint();

        }

        this.levelZoomLabel.setText(Integer.toString(this.canvas2.getZoom()));

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

    /**
     * OnClick Table.
     *
     * @param evt event
     */
    private void jTableVehiculeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVehiculeMouseClicked
    }//GEN-LAST:event_jTableVehiculeMouseClicked

    /**
     * Enable all Client.
     *
     * @param evt test
     */
    private void showClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showClientButtonActionPerformed
        // TODO add your handling code here:

        this.clientModelTable.setAll(Boolean.TRUE);
    }//GEN-LAST:event_showClientButtonActionPerformed

    /**
     * isable Table.
     *
     * @param evt event
     */
    private void hideClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideClientButtonActionPerformed
        // TODO add your handling code here:
        this.clientModelTable.setAll(Boolean.FALSE);
    }//GEN-LAST:event_hideClientButtonActionPerformed

    /**
     * Enable all Client.
     *
     * @param evt test
     */
    private void showVehiculeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVehiculeButtonActionPerformed
        // TODO add your handling code here:
        this.vehiculeModelTable.setAll(Boolean.TRUE);
    }//GEN-LAST:event_showVehiculeButtonActionPerformed

    /**
     * Disable all Client.
     *
     * @param evt test
     */
    private void hideVehiculeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideVehiculeButtonActionPerformed
        // TODO add your handling code here:
        this.vehiculeModelTable.setAll(Boolean.FALSE);

    }//GEN-LAST:event_hideVehiculeButtonActionPerformed

    /**
     * Zoom button event.
     *
     * @param evt Internal event
     */
    private void zoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomButtonActionPerformed
        this.canvas2.center();
    }//GEN-LAST:event_zoomButtonActionPerformed

    //CHECKSTYLE:OFF
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ClientTable;
    /*
    private java.awt.Canvas canvas2;
    */
    private MyCanvas canvas2;
    private javax.swing.JLabel clientEmplacementLabel;
    private javax.swing.JLabel costLabel;
    private javax.swing.JLabel costNLabel;
    private javax.swing.JLabel externalVlabel;
    private javax.swing.JButton hideClientButton;
    private javax.swing.JButton hideVehiculeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableVehicule;
    private javax.swing.JLabel levelZoomLabel;
    private javax.swing.JLabel numberVLabel;
    private javax.swing.JButton showClientButton;
    private javax.swing.JButton showVehiculeButton;
    private javax.swing.JLabel vehiculeLabel;
    private javax.swing.JButton zoomButton;
    // End of variables declaration//GEN-END:variables
    //CHECKSTYLE:ON
}
