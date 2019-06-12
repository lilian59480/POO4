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
package template.metier;

import java.awt.Color;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Client;

/**
 *
 * @author thibaut
 */
public class ClientModelTable extends AbstractTableModel {

    /**
     * Représente les entêtes des colonnes du jTable
     */
    private final String[] entetes;

    /**
     * Représente les données du jTable
     */
    private final List<Client> data;

    /**
     * Constructor.
     *
     * @param data Client List.
     */
    public ClientModelTable(List<Client> data) {
        this.entetes = new String[3];
        this.entetes[0] = "Display / Hide";
        this.entetes[1] = "Color";
        this.entetes[2] = "Client";
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
        System.out.println("test ID : " + this.data.get(rowIndex).getId());
        switch (columnIndex) {
            case 0:
                return true;
            case 1:
                return  Color.getHSBColor(this.data.get(rowIndex).getId() * 10 / 360.0f, 1, 0.8f);
            case 2:
                return this.data.get(rowIndex).getId();
            default:
                throw new IllegalArgumentException();
        }
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
            case 1:
                return Color.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }
  /**
   * Allow to set a value to a 
   * @param aValue
   * @param rowIndex
   * @param columnIndex 
   */
  @Override
   public void setValueAt(Object aValue, int rowIndex, int columnIndex)
   {/*
       Client row = data.get(rowIndex);

        if(0 == columnIndex) {
         //  row.set(aValue);
       }
     */
   }
}
