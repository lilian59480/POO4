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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
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
                instance.setInstanceName(iterator.getFilename());
                this.dlm.addElement(instance);
            } catch (ParserException ex) {
                LOGGER.log(Level.SEVERE, "Exception while reading Instances!", ex);
            }
        }

        this.initComponents();
        this.initialisationFenetre();
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
        jSelectectLabel = new javax.swing.JLabel();
        jSelectedInstance = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(204, 204, 255));

        jListInstance.setModel(dlm);
        jListInstance.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListInstanceValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListInstance);

        jButtonSolveInstance.setText("Touver une solution");
        jButtonSolveInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSolveInstanceActionPerformed(evt);
            }
        });

        jButtonDisplayInstance.setText("Afficher une solution");
        jButtonDisplayInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDisplayInstanceActionPerformed(evt);
            }
        });

        jTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTitle.setText("Deliver2I");

        jSelectectLabel.setText("File selected :");

        uploadButton.setText("Select a file");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Solve an external instance");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(228, 228, 228))
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(67, 67, 67)
                        .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSelectectLabel)
                        .addGap(78, 78, 78)
                        .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonDisplayInstance)
                        .addGap(108, 108, 108)
                        .addComponent(jButtonSolveInstance)))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadButton)
                    .addComponent(jLabel1))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSelectectLabel)
                    .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDisplayInstance)
                    .addComponent(jButtonSolveInstance))
                .addGap(109, 109, 109))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @todo Write Code and Javadoc
     * @param evt Event
     */
    private void jButtonSolveInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSolveInstanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSolveInstanceActionPerformed

    /**
     * @todo Write Code and Javadoc
     * @param evt Event
     */
    private void jListInstanceValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListInstanceValueChanged
        // TODO add your handling code here:
        String selected = this.jListInstance.getSelectedValue().toString();
        this.jSelectedInstance.setText(selected);

    }//GEN-LAST:event_jListInstanceValueChanged

    /**
     * @todo Write Code and Javadoc
     * @param evt Event
     */
    private void jButtonDisplayInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDisplayInstanceActionPerformed
        // TODO add your handling code here:
    //    if (this.jListInstance.getSelectedValue() instanceOf Instance){
        Instance i = this.jListInstance.getSelectedValue();
    //    }

        Itineraire itineraire = new Itineraire(i);
    }//GEN-LAST:event_jButtonDisplayInstanceActionPerformed

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Instance", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            this.jSelectedInstance.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_uploadButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDisplayInstance;
    private javax.swing.JButton jButtonSolveInstance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<Instance> jListInstance;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jSelectectLabel;
    private javax.swing.JLabel jSelectedInstance;
    private javax.swing.JLabel jTitle;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
