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
import model.Vehicule;

/**
 *
 * @author thibaut
 */
public class VehiculeModelTable extends GenericModelTable<Vehicule> {

    /**
     * entetes
     */
    static final String[] entetes = {"Display / Hide", "Vehicule"};

    /**
     * constructeur.
     *
     * @param data list of vehicules
     */
    public VehiculeModelTable(List<Vehicule> data) {
        super(VehiculeModelTable.entetes, data);

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

}
