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
package gui.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;
import model.Instance;

/**
 * Classe représentant le model de notre jTable des instances.
 *
 * @author thibaut
 */
public class ModelList extends AbstractListModel {
    

    /**
     * Représente les données du jTable
     */
    private final List<Instance> data;

    /**
     * Constructeurs par données
     *
     * @param instances ensemble instance
     */
    public ModelList(Collection<Instance> instances) {
        this.data = new ArrayList<>(instances);
    }


    /**
     * Get size.
     * @return  int.
     */
    @Override
    public int getSize() {
                return this.data.size();
    }
    /**
     * get Value.
     * @param index index.
     * @return value selected.
     */
    @Override
    public Object getElementAt(int index) {
                        return this.data.get(index);

    }

}
