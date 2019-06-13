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

import algo.genetic.GeneticSolver;
import algo.iterative.NaiveSolver;
import dao.DaoException;
import dao.DaoFactory;
import dao.InstanceDao;
import gui.metier.InstanceModelList;
import io.input.InstanceFileParser;
import io.input.ParserException;
import java.awt.Color;
import java.io.File;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
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
    private DaoFactory daoFactory;

    /**
     * List of instances.
     */
    private final Collection<Instance> instances;

    /**
     * Instance Model.
     */
    private final InstanceModelList model;

    /**
     * Creates new form List.
     */
    public ListeInstance() {
        this.initConnexion();

        InstanceDao instanceDao = this.daoFactory.getInstanceDao();
        instances = instanceDao.findAll();
        this.model = new InstanceModelList(this.instances);

        this.initComponents();
        this.initialisationFenetre();
    }

    /**
     * Initialise this window.
     */
    private void initialisationFenetre() {
        this.setVisible(true);
        this.setTitle("Gestion des clients");
        this.getContentPane().setBackground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Allow to connect to the DB.
     */
    private void initConnexion() {
        try {
            this.daoFactory = DaoFactory.getDaoFactory(DaoFactory.PersistenceType.JPA);
        } catch (DaoException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to load database", ex);
            JOptionPane errorPane = new JOptionPane();
            JOptionPane.showMessageDialog(errorPane, "ERROR CONNEXION DATABASE",
                    "Erreur Connection", ERROR_MESSAGE);
            this.dispose();
        }
    }

    //CHECKSTYLE:OFF
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListInstanceDb = new javax.swing.JList<>();
        jLabelSolveExternal = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();
        jSelectectLabel = new javax.swing.JLabel();
        jSelectedInstance = new javax.swing.JLabel();
        jButtonGeneticSolver = new javax.swing.JButton();
        jButtonNaiveSolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 720));

        jTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitle.setText("Deliver2I");

        jListInstanceDb.setModel(this.model);
        jScrollPane2.setViewportView(jListInstanceDb);

        jLabelSolveExternal.setText("Solve an external instance");

        uploadButton.setText("Select a file");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        jSelectectLabel.setText("File selected :");

        jButtonGeneticSolver.setText("Use GeneticSolver (Δ=2, α=5000, β=2500)");
        jButtonGeneticSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGeneticSolverActionPerformed(evt);
            }
        });

        jButtonNaiveSolver.setText("Use NativeSolver");
        jButtonNaiveSolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNaiveSolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelSolveExternal)
                                    .addComponent(jSelectectLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNaiveSolver)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonGeneticSolver)))
                        .addGap(0, 773, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(uploadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelSolveExternal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSelectectLabel)
                    .addComponent(jSelectedInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNaiveSolver)
                    .addComponent(jButtonGeneticSolver))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //CHECKSTYLE:ON

    /**
     * @todo Write Code and Javadoc
     * @param evt Event
     */
    private void jButtonGeneticSolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGeneticSolverActionPerformed
        Instance i = this.jListInstanceDb.getSelectedValue();

        GeneticSolver geneticSolver = new GeneticSolver(i, 2, 5000, 2500, 4.0);

        if (!geneticSolver.solve()) {
            LOGGER.log(Level.WARNING, "Impossible to solve this instance");
        }

        Itineraire itineraire = new Itineraire(geneticSolver.getInstance());
        itineraire.setVisible(true);
    }//GEN-LAST:event_jButtonGeneticSolverActionPerformed

    /**
     * @todo Write Code and Javadoc
     * @param evt Event
     */
    private void jButtonNaiveSolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNaiveSolverActionPerformed
        Instance i = this.jListInstanceDb.getSelectedValue();

        NaiveSolver naiveSolver = new NaiveSolver(i);

        if (!naiveSolver.solve()) {
            LOGGER.log(Level.WARNING, "Impossible to solve this instance");
        }

        Itineraire itineraire = new Itineraire(naiveSolver.getInstance());
        itineraire.setVisible(true);
    }//GEN-LAST:event_jButtonNaiveSolverActionPerformed

    /**
     * Upload Instance.
     *
     * @param evt event.
     */
    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Instance", "txt");
        chooser.setFileFilter(filter);

        InstanceFileParser ifp;

        try {
            ifp = new InstanceFileParser();
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, "Error while loading", ex);
            return;
        }

        int returnVal = chooser.showOpenDialog(this.getParent());

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            LOGGER.log(Level.INFO, "Cancelled");
            return;
        }

        File instanceFile = chooser.getSelectedFile();

        if (instanceFile == null) {
            LOGGER.log(Level.INFO, "File null");
            return;
        }

        try {
            Instance instance = ifp.parse(instanceFile);
            InstanceDao instanceManager = daoFactory.getInstanceDao();
            instanceManager.create(instance);
            JOptionPane.showMessageDialog(this, "Instance added");

            InstanceDao instanceDao = this.daoFactory.getInstanceDao();
            Collection<Instance> newInstances = instanceDao.findAll();
            this.jListInstanceDb.setModel(new InstanceModelList(newInstances));
        } catch (ParserException ex) {
            LOGGER.log(Level.SEVERE, "Error while loading", ex);
        }

    }//GEN-LAST:event_uploadButtonActionPerformed

    //CHECKSTYLE:OFF
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGeneticSolver;
    private javax.swing.JButton jButtonNaiveSolver;
    private javax.swing.JLabel jLabelSolveExternal;
    private javax.swing.JList<Instance> jListInstanceDb;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jSelectectLabel;
    private javax.swing.JLabel jSelectedInstance;
    private javax.swing.JLabel jTitle;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
    //CHECKSTYLE:ON
}
