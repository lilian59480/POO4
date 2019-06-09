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
import java.util.List;

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
    private List<Label> labels;

    /**
     * ClientLabels constructor
     *
     * @param cnb the client's number
     */
    public ClientLabels(int cnb) {
        clientNb = cnb;
        labels = new ArrayList<>();
    }

    /**
     * Get labels.
     *
     * @return The em2Labels.
     */
    public List<Label> getLabels() {
        return labels;
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
     * Add multiple labels
     *
     * @param labels the labels to add
     */
    public void addLabels(List<Label> labels) {
        this.labels.addAll(labels);
    }

    /**
     * Add a label
     *
     * @param label the label to add
     */
    public void addLabel(Label label) {
        this.labels.add(label);
    }

    @Override
    public String toString() {
        return "ClientLabels2{" + "clientNb=" + clientNb + ", labels=" + labels + '}';
    }

}
