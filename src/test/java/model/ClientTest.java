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
package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Client}
 *
 * @author Lilian Petitpas
 */
@DisplayName("Client")
public class ClientTest {

    private Client client;

    @BeforeEach
    void createNewClient() {
        this.client = new Client(200);
    }

    @Nested
    @DisplayName("Setting position")
    class SettingPosition {

        @Test
        @DisplayName("Positive position should work")
        public void changePositivePosition() {
            ClientTest.this.client.setPosition(1);

            assertEquals(1, ClientTest.this.client.getPosition(), "Both must have the same value");
        }

    }

    @Nested
    @DisplayName("Setting emplacement")
    class SettingEmplacement {

        @Test
        @DisplayName("Valid emplacement should work")
        public void addEmplacement() {
            Emplacement e = new Emplacement(10, 20, 30, 40);
            assertTrue(ClientTest.this.client.addEmplacement(e), "addEmplacement must return true here");
        }

        @Test
        @DisplayName("Null emplacement should not work")
        public void addNullEmplacement() {
            Emplacement e = null;
            assertFalse(ClientTest.this.client.addEmplacement(e), "addEmplacement must return false here");
        }

        @Test
        @DisplayName("Emplacement should have Client information")
        public void emplacementSetClient() {
            Emplacement e = new Emplacement(20, 30, 40, 50);
            assertTrue(ClientTest.this.client.addEmplacement(e), "addEmplacement must return true here");
            assertEquals(ClientTest.this.client, e.getClient(), "Emplacement.getClient is different from Client");
        }

        @Test
        @DisplayName("Emplacement list should be updated")
        public void emplacementList() {
            int max = 7;
            for (int i = 0; i < max; i++) {
                Emplacement e = new Emplacement(i, 5 * i + 1, 2 * i, 2 + i);
                assertTrue(ClientTest.this.client.addEmplacement(e), "addEmplacement must return true here for i=" + i);
            }

            assertEquals(max, ClientTest.this.client.getEmplacements().size(), "Emplacement list should contains all emplacements");
        }

    }
}
