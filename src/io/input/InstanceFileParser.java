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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Instance;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class that parses an instance file.
 *
 * @author Lilian Petitpas
 */
public class InstanceFileParser {

    /**
     * Factory for the XML Document builder.
     */
    private static DocumentBuilderFactory dbf;

    /**
     * XML Document builder, used to get a {@link org.w3c.dom.Document} from a
     * file.
     */
    private static DocumentBuilder db;

    /**
     * TSV Parser, used for some data inside XML Nodes.
     */
    private static TSVParser tsvParser;

    /**
     * Constructor.
     *
     * @throws ParserException
     * @todo We may have to discuss if we keep this API or not
     */
    public InstanceFileParser() throws ParserException {
        if (InstanceFileParser.dbf == null) {
            InstanceFileParser.dbf = DocumentBuilderFactory.newInstance();
        }

        if (InstanceFileParser.db == null) {
            try {
                InstanceFileParser.db = InstanceFileParser.dbf.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new ParserException(ex);
            }
        }

        if (InstanceFileParser.tsvParser == null) {
            InstanceFileParser.tsvParser = new TSVParser();
        }
    }

    /**
     * Parses a {@link File} and returns an Instance.
     *
     * @param f The file to parse
     * @return an Instance
     * @throws ParserException
     */
    public Instance parse(File f) throws ParserException {
        try (FileInputStream fis = new FileInputStream(f)) {
            return this.parse(fis);
        } catch (IOException ex) {
            throw new ParserException(ex);
        }
    }

    /**
     * Parses an {@link InputStream} and returns an Instance.
     *
     * @param is The {@link InputStream} to parse
     * @return an Instance
     * @throws ParserException
     */
    public Instance parse(InputStream is) throws ParserException {
        InputStream validInputStream = InstanceFileParser.createSuitableXMLRoot(is);
        Document document;

        try {
            document = InstanceFileParser.db.parse(validInputStream);
        } catch (SAXException | IOException ex) {
            throw new ParserException(ex);
        }

        int nbCustomers = this.getNbCustomers(document);
        System.out.println("nbCustomers : " + nbCustomers);

        int nbLocations = this.getNbLocations(document);
        System.out.println("nbLocations : " + nbLocations);

        int nbVehicles = this.getNbVehicles(document);
        System.out.println("nbVehicules : " + nbVehicles);

        int vehicleCapacity = this.getVehicleCapacity(document);
        System.out.println("vehiculeCapacity : " + vehicleCapacity);

        int externalVehicleCost = this.getExternalVehicleCost(document);
        System.out.println("externalVehiculeCost : " + externalVehicleCost);

        List<String[]> depotsList = this.getDepotsList(document);
        System.out.println("depotsList : " + depotsList);

        List<String[]> customersList = this.getCustomersList(document);
        System.out.println("customersList : " + customersList);

        List<String[]> locationsList = this.getLocationsList(document);
        System.out.println("locationsList : " + locationsList);

        List<String[]> travelCostsTimesList = this.getTravelCostsTimesList(document);
        System.out.println("travelCostsTimesList : " + travelCostsTimesList);

        return new Instance();
    }

    /**
     * Returns the value inside the node {@code NB_CUSTOMERS}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException
     */
    private int getNbCustomers(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_CUSTOMERS");
    }

    /**
     * Returns the value inside the node {@code NB_LOCATIONS}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException
     */
    private int getNbLocations(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_LOCATIONS");
    }

    /**
     * Returns the value inside the node {@code NB_VEHICLES}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException
     */
    private int getNbVehicles(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_VEHICLES");
    }

    /**
     * Returns the value inside the node {@code VEHICLE_CAPACITY}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException
     */
    private int getVehicleCapacity(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "VEHICLE_CAPACITY");
    }

    /**
     * Returns the value inside the node {@code EXTERNAL_VEHICLE_COST}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException
     */
    private int getExternalVehicleCost(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "EXTERNAL_VEHICLE_COST");
    }

    /**
     * Returns the value inside the node {@code DEPOT}.
     *
     * @param document The document parsed
     * @return The value as a list
     * @throws ParserException
     */
    private List<String[]> getDepotsList(Document document) throws ParserException {
        return this.getSingleNodeTSV(document, "DEPOT");
    }

    /**
     * Returns the value inside the node {@code CUSTOMERS}.
     *
     * @param document The document parsed
     * @return The value as a list
     * @throws ParserException
     */
    private List<String[]> getCustomersList(Document document) throws ParserException {
        return this.getSingleNodeTSV(document, "CUSTOMERS");
    }

    /**
     * Returns the value inside the node {@code LOCATIONS}.
     *
     * @param document The document parsed
     * @return The value as a list
     * @throws ParserException
     */
    private List<String[]> getLocationsList(Document document) throws ParserException {
        return this.getSingleNodeTSV(document, "LOCATIONSS");
    }

    /**
     * Returns the value inside the node {@code TRAVEL_COSTS_TIMES}.
     *
     * @param document The document parsed
     * @return The value as a list
     * @throws ParserException
     */
    private List<String[]> getTravelCostsTimesList(Document document) throws ParserException {
        return this.getSingleNodeTSV(document, "TRAVEL_COSTS_TIMES");
    }

    /**
     * Retrieves a node from his tag name and returns the value inside as a
     * number.
     *
     * @param document The document parsed
     * @param tagName The tag name we need to read
     * @return The value as an int
     * @throws ParserException
     */
    private int getSingleNodeValueInteger(Document document, String tagName) throws ParserException {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() != 1) {
            throw new ParserException("Expected only one tag in the DOM", 1, nodeList.getLength());
        }

        Node singleNode = nodeList.item(0);
        return Integer.parseInt(singleNode.getTextContent(), 10);
    }

    /**
     * Retrieves a node from his tag name and returns the value inside as a list
     * of elements.
     *
     * @param document The document parsed
     * @param tagName The tag name we need to read
     * @return The value as a list
     * @throws ParserException
     */
    private List<String[]> getSingleNodeTSV(Document document, String tagName) throws ParserException {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() != 1) {
            throw new ParserException("Expected only one tag in the DOM", 1, nodeList.getLength());
        }

        Node singleNode = nodeList.item(0);
        String nodeText = singleNode.getTextContent();

        return InstanceFileParser.tsvParser.parse(new StringReader(nodeText.substring(1)));
    }

    /**
     * Add a root node to create a valid XML Document.
     *
     * Since instance files are XML like files, without a root node, we need to
     * create a root node. With that, we can leave the XML parsing part to a
     * dedicated library with a better support than an hand-made one.
     *
     * Thanks to
     * https://stackoverflow.com/questions/6640756/parsing-an-xml-stream-with-no-root-element
     * for the code to sandwich an {@link InputStream}
     *
     * @param inputStream
     * @return
     */
    private static InputStream createSuitableXMLRoot(InputStream inputStream) {
        List<InputStream> streams = new ArrayList<>();
        streams.add(new ByteArrayInputStream("<root>".getBytes()));
        streams.add(inputStream);
        streams.add(new ByteArrayInputStream("</root>".getBytes()));

        return new SequenceInputStream(Collections.enumeration(streams));
    }

//    public static void main(String[] args) {
//        try {
//            InstanceFileParser ifp = new InstanceFileParser();
//            ifp.parse(new File("resources/instances/instance_0-triangle.txt"));
//        } catch (Exception ex) {
//            Logger.getLogger(InstanceFileParser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
