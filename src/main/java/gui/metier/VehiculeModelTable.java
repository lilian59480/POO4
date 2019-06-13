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
/**
 * Contains all classes related to the TableModel.
 *
 * @author Lilian Petitpas
 * @author Thomas Ternisien
 * @author Thibaut Fenain
 * @author Corentin Apolinario
 */
package gui.metier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javax.swing.UIManager.get;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import model.Client;
import model.Route;
import model.Vehicule;

/**
 *
 * @author thibaut
 */
public class VehiculeModelTable extends AbstractTableModel {

    /**
     * Représente les entêtes des colonnes du jTable
     */
    private final String[] entetes;

    /**
     * Représente les données du jTable.
     */
    private final List<Vehicule> data;
    private final List<Boolean> checkBoxValues;

    /**
     * constructeur.
     *
     * @param data list of mapDisplayVehicules
     */
    public VehiculeModelTable(List<Vehicule> data) {
        this.entetes = new String[2];
        this.entetes[0] = "Display / Hide";
        this.entetes[1] = "Vehicule";
        this.data = data;
        this.checkBoxValues = new ArrayList<>();

        for (Vehicule v : data) {

            this.checkBoxValues.add(Boolean.TRUE);
        }

    }

    /**
     * Get Row Count.
     *
     * @return int
     */
    @Override
    public int getRowCount() {
        return this.data.size();
    }

    /**
     * Get column count.
     *
     * @return int
     */
    @Override
    public int getColumnCount() {
        return this.entetes.length;
    }

    /**
     * Allow to return the value of a cellule.
     *
     * @param rowIndex
     * @param columnIndex
     * @return value of a cellule
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.checkBoxValues.get(rowIndex);
            case 1:
                return this.data.get(rowIndex).getId();
            /*  default:
                throw new IllegalArgumentException();
             */
        }
        return null;
    }

    /**
     * return the name of a column
     *
     * @param columnIndex
     * @return string.
     */
    @Override
    public String getColumnName(int columnIndex) {
        return this.entetes[columnIndex];
    }

    /**
     * Return the type of each column.
     *
     * @param columnIndex index of the column
     * @return type
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    /**
     * Allow to set a value to a
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("on click cellule :" + aValue.toString());
        if (aValue instanceof Boolean) {
            if (columnIndex == 0) {
                this.checkBoxValues.set(rowIndex, (Boolean) aValue);
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);// notify listeners
    }

    /**
     * Allow Editable cellule.
     *
     * @param rowIndex
     * @param columnIndex
     * @return boolean
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return columnIndex == 0;
    }

    /**
     * display true vehicules.
     *
     * @return vehicules to display
     */
    public List<Vehicule> getDisplayVehicules() {
        List<Vehicule> vehicules = new ArrayList<Vehicule>();
        for (int i = 0; i < this.data.size(); i++) {
            if (this.checkBoxValues.get(i)) {
                vehicules.add(this.data.get(i));
            }
        }
        return vehicules;
    }

    /**
     * set All False or True
     *
     * @param b boolean
     */
    public void setAll(Boolean b) {
        for (int i = 0; i < this.data.size(); i++) {

            this.checkBoxValues.set(i, b);
            fireTableCellUpdated(i, 0);// notify listeners

        }

    }

}
