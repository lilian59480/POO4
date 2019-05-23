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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Client;
import model.Depot;
import model.Emplacement;
import model.Instance;
import model.Point;
import model.Route;
import model.Vehicule;
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
     * Class logger.
     */
    private static final Logger LOGGER = Logger.getLogger(InstanceFileParser.class.getName());

    /**
     * Factory for the XML Document builder.
     */
    private DocumentBuilderFactory dbf;

    /**
     * XML Document builder, used to get a {@link org.w3c.dom.Document} from a
     * file.
     */
    private DocumentBuilder db;

    /**
     * TSV Parser, used for some data inside XML Nodes.
     */
    private TSVParser tsvParser;

    /**
     * Constructor.
     *
     * @throws ParserException When parsing can't be done properly
     * @todo We may have to discuss if we keep this API or not
     */
    public InstanceFileParser() throws ParserException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setExpandEntityReferences(false);

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            this.dbf = documentBuilderFactory;
            this.db = this.dbf.newDocumentBuilder();
            this.tsvParser = new TSVParser();

        } catch (ParserConfigurationException ex) {
            throw new ParserException(ex);
        }
    }

    /**
     * Parses a {@link File} and returns an Instance.
     *
     * @param f The file to parse
     * @return an Instance
     * @throws ParserException When parsing can't be done properly
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
     * @throws ParserException When parsing can't be done properly
     */
    public Instance parse(InputStream is) throws ParserException {
        InputStream validInputStream = InstanceFileParser.createSuitableXMLRoot(is);
        Document document;

        try {
            document = this.db.parse(validInputStream);
            if (document == null) {
                throw new NullPointerException("Document is null");
            }
        } catch (SAXException | IOException | NullPointerException ex) {
            throw new ParserException(ex);
        }

        Instance inst = new Instance();

        int nbCustomers = this.getNbCustomers(document);
        LOGGER.log(Level.FINER, "Number of clients : {0}", nbCustomers);

        int nbLocations = this.getNbLocations(document);
        LOGGER.log(Level.FINER, "Number of locations : {0}", nbLocations);

        int nbVehicles = this.getNbVehicles(document);
        inst.setNbVehicules(nbVehicles);
        LOGGER.log(Level.FINER, "Number of vehicules : {0}", nbVehicles);

        int vehicleCapacity = this.getVehicleCapacity(document);
        inst.setCapaciteVehicule(vehicleCapacity);
        LOGGER.log(Level.FINER, "Vehicule capacity : {0}", vehicleCapacity);

        int externalVehicleCost = this.getExternalVehicleCost(document);
        inst.setCoutVehicule(externalVehicleCost);
        LOGGER.log(Level.FINER, "External vehicule cost : {0}", externalVehicleCost);

        Map<Integer, Emplacement> locationsMap = this.getLocationsList(document);
        LOGGER.log(Level.FINER, "Locations : {0}", locationsMap);

        Map<Integer, Depot> depotsList = this.getDepotsList(document, locationsMap);
        LOGGER.log(Level.FINER, "Depots : {0}", depotsList);

        // We assume there is only one Depot with id = 0
        Depot mainDepot = depotsList.get(0);
        if (mainDepot == null) {
            throw new ParserException(new NullPointerException("mainDepot is null"));
        }
        LOGGER.log(Level.FINER, "Main depot : {0}", mainDepot);

        inst.setDepot(mainDepot);
        locationsMap.put(0, mainDepot);
        LOGGER.log(Level.FINEST, "Replacing location 0 with the depot");

        Map<Integer, Client> customersList = this.getCustomersList(document, locationsMap);
        LOGGER.log(Level.FINER, "Customers : {0}", customersList);

        // Drop IDs as they are not stored now
        inst.setClients(new ArrayList<>(customersList.values()));

        List<Route> travelCostsTimesList = this.getTravelCostsTimesList(document, locationsMap);
        LOGGER.log(Level.FINER, "Travel cost : {0}", travelCostsTimesList);

        inst.setRoutes(travelCostsTimesList);

        // Create vehicules
        List<Vehicule> vehiculeList = new LinkedList<>();
        for (int i = 0; i < nbVehicles; i++) {
            Vehicule v = new Vehicule(mainDepot, vehicleCapacity);
            v.setInstance(inst);
            vehiculeList.add(v);
        }
        inst.setVehicules(vehiculeList);
        LOGGER.log(Level.FINER, "Vehicule created");

        return inst;
    }

    /**
     * Returns the value inside the node {@code NB_CUSTOMERS}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getNbCustomers(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_CUSTOMERS");
    }

    /**
     * Returns the value inside the node {@code NB_LOCATIONS}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getNbLocations(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_LOCATIONS");
    }

    /**
     * Returns the value inside the node {@code NB_VEHICLES}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getNbVehicles(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "NB_VEHICLES");
    }

    /**
     * Returns the value inside the node {@code VEHICLE_CAPACITY}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getVehicleCapacity(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "VEHICLE_CAPACITY");
    }

    /**
     * Returns the value inside the node {@code EXTERNAL_VEHICLE_COST}.
     *
     * @param document The document parsed
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getExternalVehicleCost(Document document) throws ParserException {
        return this.getSingleNodeValueInteger(document, "EXTERNAL_VEHICLE_COST");
    }

    /**
     * Returns the value inside the node {@code DEPOT}.
     *
     * @param document The document parsed
     * @param locations Map of locations
     * @return The value as a list
     * @throws ParserException When parsing can't be done properly
     */
    private Map<Integer, Depot> getDepotsList(Document document, Map<Integer, Emplacement> locations) throws ParserException {
        List<String[]> depoList = this.getSingleNodeTSV(document, "DEPOT");

        Iterator<String[]> depotIter = depoList.iterator();

        Map<Integer, Depot> depotMap = new HashMap<>();

        // Skip first entry, as it is an header
        if (!depotIter.hasNext()) {
            return depotMap;
        }
        depotIter.next();

        while (depotIter.hasNext()) {
            String[] elt = depotIter.next();
            // ID DEMAND NBLOC LOCS
            int id = Integer.parseInt(elt[0]);
            // Demand should be equals to 0 in all cases
            int demand = Integer.parseInt(elt[1]);
            if (demand != 0) {
                LOGGER.log(Level.WARNING, "Demand not null, stange");
            }
            int nbloc = Integer.parseInt(elt[2]);

            int locId = -1;
            // There should be only one location, so we will assume it is the case.
            // Keep only the last locId
            for (int i = 3; i < 3 + nbloc; i++) {
                locId = Integer.parseInt(elt[i]);
            }

            Emplacement emp = locations.get(locId);
            Depot depot = new Depot(emp);
            depotMap.put(id, depot);
        }

        return depotMap;
    }

    /**
     * Returns the value inside the node {@code CUSTOMERS}.
     *
     * @param document The document parsed
     * @param locations Map of locations
     * @return The value as a list
     * @throws ParserException When parsing can't be done properly
     */
    private Map<Integer, Client> getCustomersList(Document document, Map<Integer, Emplacement> locations) throws ParserException {

        List<String[]> customerList = this.getSingleNodeTSV(document, "CUSTOMERS");

        Iterator<String[]> customerIter = customerList.iterator();

        Map<Integer, Client> clientMap = new HashMap<>();

        // Skip first entry, as it is an header
        if (!customerIter.hasNext()) {
            return clientMap;
        }
        customerIter.next();

        while (customerIter.hasNext()) {
            String[] elt = customerIter.next();
            // ID DEMAND NBLOC LOCS
            int id = Integer.parseInt(elt[0]);
            // Demand should be equals to 0 in all cases
            int demand = Integer.parseInt(elt[1]);
            int nbloc = Integer.parseInt(elt[2]);

            Client client = new Client(demand);

            // There should be only one location, so we will assume it is the case.
            // Keep only the last locId
            for (int i = 3; i < 3 + nbloc; i++) {
                int locId = Integer.parseInt(elt[i]);
                Emplacement emp = locations.get(locId);
                client.addEmplacement(emp);
            }

            clientMap.put(id, client);
        }

        return clientMap;
    }

    /**
     * Returns the value inside the node {@code LOCATIONS}.
     *
     * @param document The document parsed
     * @return The value as a list
     * @throws ParserException When parsing can't be done properly
     */
    private Map<Integer, Emplacement> getLocationsList(Document document) throws ParserException {

        List<String[]> locationList = this.getSingleNodeTSV(document, "LOCATIONS");

        Iterator<String[]> locationIter = locationList.iterator();

        Map<Integer, Emplacement> locationMap = new HashMap<>();

        // Skip first entry, as it is an header
        if (!locationIter.hasNext()) {
            return locationMap;
        }
        locationIter.next();

        while (locationIter.hasNext()) {
            String[] elt = locationIter.next();
            // ID x y TWE TWL
            int id = Integer.parseInt(elt[0]);
            double x = Double.parseDouble(elt[1]);
            double y = Double.parseDouble(elt[2]);
            int twe = Integer.parseInt(elt[3]);
            int twl = Integer.parseInt(elt[4]);

            Emplacement emplacement = new Emplacement(twe, twl, x, y);
            emplacement.setId(id);

            locationMap.put(id, emplacement);
        }

        return locationMap;
    }

    /**
     * Returns the value inside the node {@code TRAVEL_COSTS_TIMES}.
     *
     * @param document The document parsed
     * @param locations Map of locations
     * @return The value as a list
     * @throws ParserException When parsing can't be done properly
     */
    private List<Route> getTravelCostsTimesList(Document document, Map<Integer, Emplacement> locations) throws ParserException {
        List<String[]> rawRouteList = this.getSingleNodeTSV(document, "TRAVEL_COSTS_TIMES");

        Iterator<String[]> routeIter = rawRouteList.iterator();

        List<Route> routeList = new ArrayList<>();

        // Skip first entry, as it is an header
        if (!routeIter.hasNext()) {
            return routeList;
        }
        routeIter.next();

        while (routeIter.hasNext()) {
            String[] elt = routeIter.next();
            // IDFROM	IDTO	COST	TIME
            int idFrom = Integer.parseInt(elt[0]);
            int idTo = Integer.parseInt(elt[1]);
            double cost = Double.parseDouble(elt[2]);
            int time = Integer.parseInt(elt[3]);

            Point from = locations.get(idFrom);
            Point to = locations.get(idTo);

            Route r = new Route(from, to, cost, time);

            routeList.add(r);
            from.addRouteTo(r);
        }

        return routeList;
    }

    /**
     * Retrieves a node from his tag name and returns the value inside as a
     * number.
     *
     * @param document The document parsed
     * @param tagName The tag name we need to read
     * @return The value as an int
     * @throws ParserException When parsing can't be done properly
     */
    private int getSingleNodeValueInteger(Document document, String tagName) throws ParserException {
        LOGGER.log(Level.FINEST, "Node to parse : {0}", tagName);

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
     * @throws ParserException When parsing can't be done properly
     */
    private List<String[]> getSingleNodeTSV(Document document, String tagName) throws ParserException {

        LOGGER.log(Level.FINEST, "Node to parse : {0}", tagName);

        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() != 1) {
            throw new ParserException("Expected only one tag in the DOM", 1, nodeList.getLength());
        }

        Node singleNode = nodeList.item(0);
        String nodeText = singleNode.getTextContent();

        return this.tsvParser.parse(new StringReader(nodeText.substring(1)));
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
     * @param inputStream Stream to sandwich
     * @return A valid and well formed document
     */
    private static InputStream createSuitableXMLRoot(InputStream inputStream) {
        List<InputStream> streams = new ArrayList<>();
        streams.add(new ByteArrayInputStream("<root>".getBytes()));
        if (inputStream != null) {
            streams.add(inputStream);
        }
        streams.add(new ByteArrayInputStream("</root>".getBytes()));

        return new SequenceInputStream(Collections.enumeration(streams));
    }

}
