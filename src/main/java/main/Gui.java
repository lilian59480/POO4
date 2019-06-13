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
package main;

import gui.ListeInstance;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gui entry point for this project
 *
 * @author Lilian Petitpas
 */
public class Gui {

    /**
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Gui.class.getName());

    /**
     * Cli Entry point.
     *
     * @param args Arguments, 1st can be "help" or "usage"
     */
    public static void main(String[] args) {
        Gui self = new Gui();
        self.show();
    }

    /**
     * Display the window.
     */
    public void show() {
        LOGGER.log(Level.INFO, "Starting application");
        ListeInstance li = new ListeInstance();
        li.setVisible(true);
    }
}
