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
import java.util.List;
import model.Client;

/**
 *
 * @author thibaut
 */
public class ClientModelTable extends GenericModelTable<Client> {

    /**
     * entetes
     */
    private static final String[] entetes = {"Display / Hide", "Client"};

    /**
     * Constructor.
     *
     * @param data Client List.
     */
    public ClientModelTable(List<Client> data) {

        super(ClientModelTable.entetes, data);

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

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * display true vehicules.
     *
     * @return vehicules to display
     */
    public List<Client> getDisplayClients() {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < this.data.size(); i++) {
            if (this.checkBoxValues.get(i)) {
                clients.add(this.data.get(i));
            }
        }
        return clients;
    }

}
