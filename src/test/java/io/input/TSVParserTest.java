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
package io.input;

import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TSVParser}
 *
 * @author Lilian Petitpas
 */
@DisplayName("TSVParser")
public class TSVParserTest {

    /**
     * TSVParser.
     */
    private final TSVParser parser = new TSVParser();

    /**
     * Test string.
     */
    private final String testString = "ID\tValue\n0\t1\n1\t5\n";

    /**
     * Test if TSVParser is working with a valid reader.
     */
    @Test
    @DisplayName("Is working with a valid Reader")
    public void isWorkingWithValidReader() {
        List<String[]> expected = new LinkedList<>();
        String[] e1 = {"ID", "Value"};
        String[] e2 = {"0", "1"};
        String[] e3 = {"1", "5"};

        expected.add(e1);
        expected.add(e2);
        expected.add(e3);

        try {
            List<String[]> actual = this.parser.parse(new StringReader(this.testString));

            assertEquals(expected.size(), actual.size(), "Both must have the same size");

            Iterator<String[]> i1 = expected.iterator();
            Iterator<String[]> i2 = actual.iterator();

            for (; i1.hasNext() && i2.hasNext();) {
                String[] expectedList = i1.next();
                String[] actualList = i2.next();

                assertArrayEquals(expectedList, actualList, "Elements must be identical");
            }

        } catch (ParserException ex) {
            fail("Received an exception", ex);
        }
    }

}
