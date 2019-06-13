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

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Vehicule;

/**
 *
 * @author thibaut
 */
public class GenericModelTable<T> extends AbstractTableModel{

    /**
     * Représente les entêtes des colonnes du jTable
     */
    private final String[] entetes;

    /**
     * Représente les données du jTable.
     */
    private final List<T> data;
    private final List<Boolean> checkBoxValues;
    
    /**
     *  test
     * @param entetes ent
     * @param data data
     * @param checkBoxValues list of checkbox
     */
    public GenericModelTable(String[] entetes, List<T> data, List<Boolean> checkBoxValues) {
        this.entetes = entetes;
        this.data = data;
        this.checkBoxValues = checkBoxValues;
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
                // return this.data.get(rowIndex).getId();
                return 2;
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
    public List<T> getDisplayData() {
        List<T> listes = new ArrayList<T>();
        for (int i = 0; i < this.data.size(); i++) {
            if (this.checkBoxValues.get(i)) {
                listes.add(this.data.get(i));
            }
        }
        return listes;
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
