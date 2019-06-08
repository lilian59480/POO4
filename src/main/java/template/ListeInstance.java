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
import dao.InstanceDao;
import io.input.FilenameIterator;
import io.input.InstanceFileParser;
import io.input.JarInstanceResourceReader;
import io.input.ParserException;
import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.ListModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Instance;
import template.metier.ModelList;

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
    private DaoFactory daoFactory;
    private final Collection<Instance> instances;

    /**
     * Creates new form List.
     */
    public ListeInstance() {
        this.initConnexion();
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
        InstanceDao instanceDao = this.daoFactory.getInstanceDao();
        instances = instanceDao.findAll();
        ModelList vv = new ModelList(this.instances);
        this.jListInstanceDb.setModel(vv);
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
     * Allow to connect to the DB.
     *
     * @author Thibaut Fenain
     */
    private void initConnexion() {
        try {
            this.daoFactory = DaoFactory.getDaoFactory(DaoFactory.PersistenceType.JPA);
            InstanceDao instanceManager = daoFactory.getInstanceDao();
            Instance i1 = new Instance();
            Instance i2 = new Instance();
            instanceManager.create(i1);
            instanceManager.create(i2);
        } catch (DaoException ex) {
            Logger.getLogger(Itineraire.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane errorPane = new JOptionPane();
            JOptionPane.showMessageDialog(errorPane, "ERROR CONNEXION DATABASE",
                    "Erreur Connection", ERROR_MESSAGE);
            this.dispose();

        }
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jListInstanceDb = new javax.swing.JList<>();

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

        jScrollPane2.setViewportView(jListInstanceDb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSelectectLabel)
                                .addGap(78, 78, 78)
                                .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonDisplayInstance)
                                .addGap(108, 108, 108)
                                .addComponent(jButtonSolveInstance))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(67, 67, 67)
                                .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(469, 469, 469)
                        .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadButton)
                    .addComponent(jLabel1))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSelectectLabel)
                    .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
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
        if (this.jListInstance.getSelectedValue() instanceof Instance) {
            Instance i = this.jListInstance.getSelectedValue();
            Itineraire itineraire = new Itineraire(i);
        } else if (this.jSelectedInstance != null) {

            String fileName = this.jSelectedInstance.getText();

            NaiveSolver ds = createInstance(fileName);
            ds.solve();
            Itineraire itineraire = new Itineraire(ds.getInstance());
        }
    }//GEN-LAST:event_jButtonDisplayInstanceActionPerformed
    /**
     * Upload Instance.
     *
     * @param evt event.
     */
    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Instance", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            InstanceDao instanceManager = daoFactory.getInstanceDao();
            Instance i2 = new Instance();
            instanceManager.create(i2);
            this.jSelectedInstance.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_uploadButtonActionPerformed

    /**
     * Create Instance.
     *
     * @todo What! Please refactor.
     * @param fileName file Name.
     * @return A solver.
     */
    private NaiveSolver createInstance(String fileName) {
        Instance i;
        try {
            InstanceFileParser ifp = new InstanceFileParser();
            i = ifp.parse(new File(fileName));
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return null;
        }
        NaiveSolver ds = new NaiveSolver(i);
        ds.solve();
        return ds;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDisplayInstance;
    private javax.swing.JButton jButtonSolveInstance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<Instance> jListInstance;
    private javax.swing.JList<Instance> jListInstanceDb;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jSelectectLabel;
    private javax.swing.JLabel jSelectedInstance;
    private javax.swing.JLabel jTitle;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
