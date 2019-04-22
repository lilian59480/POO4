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

import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TSVParser}
 *
 * @author Lilian Petitpas
 */
@DisplayName("TSVParser")
public class InstanceParserTest {

    private final InstanceFileParser parser;

    private final String testString = "<NB_CUSTOMERS>15</NB_CUSTOMERS>\n"
            + "<NB_LOCATIONS>62</NB_LOCATIONS>\n"
            + "<NB_VEHICLES>4</NB_VEHICLES>\n"
            + "<VEHICLE_CAPACITY>750</VEHICLE_CAPACITY>\n"
            + "<EXTERNAL_VEHICLE_COST>4100</EXTERNAL_VEHICLE_COST>\n"
            + "<DEPOT>	ID	DEMAND	NBLOC	LOCS\n"
            + "0	0	1		0\n"
            + "</DEPOT>\n"
            + "<CUSTOMERS>	ID	DEMAND	NBLOC	LOCS\n"
            + "1	75	5			1	2	3	4	5\n"
            + "</CUSTOMERS>\n"
            + "<LOCATIONS>	ID	x	y	TWE	TWL\n"
            + "0	0	0	0	720\n"
            + "</LOCATIONS>\n"
            + "<TRAVEL_COSTS_TIMES>	IDFROM	IDTO	COST	TIME\n"
            + "0	0	0	0\n"
            + "61	61	0	0\n"
            + "</TRAVEL_COSTS_TIMES>\n"
            + "";

    public InstanceParserTest() throws ParserException {
        this.parser = new InstanceFileParser();
    }

    @Test
    @DisplayName("Is working with a valid InputStream")
    @Disabled("Current parser is not returning a valid Instance")
    public void isWorkingWithValidInputStream() {

        try {
            int actual = this.parser.parse(new ByteArrayInputStream(this.testString.getBytes()));
        } catch (ParserException ex) {
            fail("Received an exception", ex);
        }
    }

}
