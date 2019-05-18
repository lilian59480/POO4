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

import io.input.FilenameIterator;
import io.input.InstanceFileParser;
import io.input.JarInstanceResourceReader;
import io.input.ParserException;
import java.awt.Color;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import model.Instance;

/**
 * List instances.
 *
 * @todo Follow wireframes.
 * @author thibaut
 */
public class ListeInstance extends JFrame { // NOSONAR

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ListeInstance.class.getName());

    /**
     * Serial UID, for serialisation.
     */
    private static final long serialVersionUID = 20190511190815L;

    /**
     * List of Instances.
     */
    private final DefaultListModel<Instance> dlm;

    /**
     * Creates new form List.
     */
    public ListeInstance() {
        JarInstanceResourceReader instanceReader = new JarInstanceResourceReader();
        this.dlm = new DefaultListModel<>();
        for (FilenameIterator<InputStream> iterator = instanceReader.iterator(); iterator.hasNext();) {
            try {
                InputStream next = iterator.next();
                InstanceFileParser ifp = new InstanceFileParser();
                Instance instance = ifp.parse(next);
                this.dlm.addElement(instance);
            } catch (ParserException ex) {
                LOGGER.log(Level.SEVERE, "Exception while reading Instances!", ex);
            }
        }

        initComponents();
        initialisationFenetre();
    }

    /**
     * Initialise this window.
     */
    private void initialisationFenetre() {

        this.setVisible(true);
        this.setTitle("Gestion des clients by Thibaut Fenain");
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListInstance = new javax.swing.JList<>();
        jButtonSolveInstance = new javax.swing.JButton();
        jButtonDisplayInstance = new javax.swing.JButton();
        jTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(204, 204, 255));

        jListInstance.setModel(dlm);
        jScrollPane1.setViewportView(jListInstance);

        jButtonSolveInstance.setText("Touver une solution");
        jButtonSolveInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSolveInstanceActionPerformed(evt);
            }
        });

        jButtonDisplayInstance.setText("Afficher une solution");

        jTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTitle.setText("Deliver2I");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonDisplayInstance)
                        .addGap(37, 37, 37)
                        .addComponent(jButtonSolveInstance)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(228, 228, 228))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSolveInstance)
                    .addComponent(jButtonDisplayInstance))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSolveInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSolveInstanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSolveInstanceActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDisplayInstance;
    private javax.swing.JButton jButtonSolveInstance;
    private javax.swing.JList<Instance> jListInstance;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTitle;
    // End of variables declaration//GEN-END:variables
}
