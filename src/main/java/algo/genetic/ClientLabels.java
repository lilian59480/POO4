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
package algo.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Emplacement;

/**
 * ClientLabels class
 *
 * @author Thomas
 */
public class ClientLabels {
    /**
     * The client's number
     */
    private int clientNb;

    /**
     * Map that associate an emplacement to a list of Label
     */
    private Map<Emplacement, List<Label>> em2Labels;

    /**
     * ClientLabels constructor
     *
     * @param cnb the client's number
     */
    public ClientLabels(int cnb) {
        clientNb = cnb;
        em2Labels = new HashMap<>();
    }

    /**
     * Get em2Labels.
     *
     * @return The em2Labels.
     */
    public Map<Emplacement, List<Label>> getEm2Labels() {
        return em2Labels;
    }

    /**
     * Get the labels associated to an Emplacement
     *
     * @param e the emplacement you want the labels
     * @return the labels
     */
    public List<Label> getLabels(Emplacement e) {
        return em2Labels.get(e);
    }

    /**
     * Get clientNb
     *
     * @return the clientNb
     */
    public int getClientNb() {
        return clientNb;
    }

    /**
     * Create an entry for an Emplacement
     *
     * @param e the emplacement
     */
    private void createEmplacementEntry(Emplacement e) {
        if (!this.em2Labels.containsKey(e)) {
            this.em2Labels.put(e, new ArrayList<Label>());
        }
    }

    /**
     * Add multiple labels to an Emplacement
     *
     * @param e      the emplacement you want to add a label to
     * @param labels the labels to add
     */
    public void addLabelsToEmplacement(Emplacement e, List<Label> labels) {
        if (!this.em2Labels.containsKey(e)) {
            createEmplacementEntry(e);
        }
        for (Label label : labels) {
            this.em2Labels.get(e).add(label);
        }
    }

    /**
     * Add a label to an Emplacement
     *
     * @param e     the emplacement you want to add a label to
     * @param label the label to add
     */
    public void addLabelToEmplacement(Emplacement e, Label label) {
        if (!this.em2Labels.containsKey(e)) {
            createEmplacementEntry(e);
        }
        this.em2Labels.get(e).add(label);
    }

    /**
     * Add multiple labels to multiple emplacements
     *
     * @param labels the labels to add
     */
    public void addLabels(List<Label> labels) {
        for (Label label : labels) {
            this.addLabelToEmplacement(label.getEmplacement(), label);
        }
    }

    @Override
    public String toString() {
        return "ClientLabels{" + "client nb=" + clientNb + ", em2Labels=" + em2Labels + '}';
    }


}
