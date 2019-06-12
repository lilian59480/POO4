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
package template.metier;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Route;

/**
 *
 * @author thibaut
 */
public class RouteModelTable extends AbstractTableModel {

    /**
     * Représente les entêtes des colonnes du jTable
     */
    private final String[] entetes;

    /**
     * Représente les données du jTable.
     */
    private final List<Route> data;
    /**
     * Construsctor.
     * @param data 
     */
    public RouteModelTable(List<Route> data) {
        this.entetes = new String[3];
        this.entetes[0] = "Display / Hide";
        this.entetes[1] = "Color";
        this.entetes[2] = "Road";
        this.data = data;
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
                return true;
            case 1:
                return this.data.get(rowIndex).toString();
            case 2:
                return this.data.get(rowIndex).toString();
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Peremt de retourner le type de chaque colonne.
     *
     * @param columnIndex index of the column
     * @return
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return int.class;
            case 2:
                return int.class;
            default:
                return Object.class;
        }
    }

}
