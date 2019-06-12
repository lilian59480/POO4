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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javax.swing.UIManager.get;
import javax.swing.table.AbstractTableModel;
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
    private final Map<Vehicule, Boolean> vehicules;

    /**
     * constructeur.
     *
     * @param data list of vehicules
     */
    public VehiculeModelTable(List<Vehicule> data) {
        this.entetes = new String[3];
        this.entetes[0] = "Display / Hide";
        this.entetes[1] = "Color";
        this.entetes[2] = "Vehicule";
        this.data = data;
        
        this.vehicules = new HashMap<>(); 
        for (Vehicule v : data){
            this.vehicules.put(v, false);
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
                return this.vehicules.get(this.data.get(rowIndex)).booleanValue();
            case 1:
                return Color.getHSBColor(this.data.get(rowIndex).getId() * 10 / 360.0f, 1, 0.8f);
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
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.vehicules.put(data.get(rowIndex), (Boolean) aValue);
    }
    
    
    /**
     *  display true vehicules.
     * @param rowIndex value
     * @param columnIndex value.
     * @return vehicules to display
     */
    public List<Vehicule> getDisplayVehicules(int rowIndex, int columnIndex) {
        List<Vehicule> vehicules = new ArrayList<Vehicule>();
        for (int i = 0; i < this.getRowCount(); i++) {
            if (this.vehicules.get(i).booleanValue()){
                vehicules.add(data.get(rowIndex));
            } 
        }
        return vehicules;
    }

}
