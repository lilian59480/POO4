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
package algo;

import io.input.InstanceFileParser;
import io.output.SolutionWriter;
import model.*;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class ShortestPathClients implements ISolver {

    private Instance instance;
    private static final Logger LOGGER = Logger.getLogger(NaiveSolver.class.getName());
    private List<Client> chromosome;
    private Depot depot;

    public ShortestPathClients() {
        this(null);
    }

    public ShortestPathClients(Instance i) {
        this.instance = i;
        this.instanceToChromosome();
    }

    @Override
    public Instance getInstance() {
        return instance;
    }

    @Override
    public void setInstance(Instance i) {
        this.instance = i;
    }

    @Override
    public boolean solve() {
        LOGGER.log(Level.FINE, "Solving a new instance");
        /*try {
            List<Integer> tournee = findShortestPath();
            this.instance.clear();
            List<Vehicule> vehicules = this.instance.getVehicules();
            int numV = 0;
            int nbV = this.instance.getNbVehicules();
            Vehicule v = vehicules.get(numV);
            for (int i = 0; i < this.chromosome.size(); i++) {
                if (i != 0 && tournee.get(i).compareTo(tournee.get(i - 1)) != 0) {
                    numV++;
                    if (numV < nbV) {
                        v = vehicules.get(numV);
                    } else {
                        LOGGER.log(Level.INFO, "Ajout d'un extra vehicule");
                        v = this.instance.addVehicule();
                    }
                }
                if (!v.addEmplacement(this.chromosome.get(i))) {
                    LOGGER.log(Level.WARNING,
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                    throw new SolverException(
                            "Error while adding emplacement to vehicule during ShortestPathEmplacements calculation");
                }
            }
        } catch (SolverException ex) {
            LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
            return false;
        }*/

        // Check for instance validity
        return this.instance.check();
    }

    private List<List<Integer>> findShortestPath() throws SolverException {
        int capaV = this.instance.getCapaciteVehicule();
        int closeTime = this.instance.getDepot().getHeureFin();
        List<List<Map<String, Object>>> V = new LinkedList<>(); // list des couts & temps les + faible pour chaque emplacement de chaque clients
        List<List<Integer>> P = new LinkedList<>(); // list des points precedant, P[i] => point avant i pour chaque emplacement de chaque clients

        //Init V and P
        for(int i = 0; i < this.chromosome.size(); i++) {
            List<Emplacement> ems = this.chromosome.get(i).getEmplacements();
            V.add(new ArrayList<>());
            P.add(new ArrayList<>());
            /*Map<String, Object> defaultV = new HashMap<String, Object>() {{
                put("em", null);
                put("cost", Double.MAX_VALUE);
                put("time", Integer.MAX_VALUE);
            }};
            for (int j = 0; j < ems.size(); j++) {
                V.get(i).add(defaultV);
                P.get(i).add(0);
            }*/
        }

        for (int i = 0; i < this.chromosome.size(); i++) {
            Client client = this.chromosome.get(i);

            //Init labels for each emplacements of client
            Map<Emplacement,List<Map<String, Object>>> labelsMap = new HashMap<>();
            for(Emplacement em: client.getEmplacements()) {
                labelsMap.put(em, new ArrayList<>());
            }
            System.out.println(i);
            for (int j = i; j < this.chromosome.size(); j++) {
                Client c = this.chromosome.get(j);
                int pos = j;
                if (i == j) {
                    for (Map.Entry<Emplacement, List<Map<String, Object>>> labels : labelsMap.entrySet()) {
                        Route r1 = labels.getKey().getRouteTo(this.depot);
                        Route r2 = this.depot.getRouteTo(labels.getKey());
                        labels.getValue().add(new HashMap<String, Object>() {{
                            put("lastEm", depot);
                            put("lastLab", null);
                            put("em", labels.getKey());
                            put("avancement", pos);
                            put("load", c.getDemande());
                            put("cost", r1.getCout() + r2.getCout());
                            put("time", Math.max(r1.getTemps(), labels.getKey().getHeureDebut()) + r2.getTemps());
                        }});
                    }
                } else {
                    for (Map.Entry<Emplacement, List<Map<String, Object>>> labels : labelsMap.entrySet()) {
                        List<Map<String, Object>> newLabel = new ArrayList<>();
                        for (Map<String, Object> label : labels.getValue()) {
                            if(j > (Integer) label.get("avancement")) {
                                Emplacement previous = (Emplacement) label.get("em");
                                for(Emplacement e : c.getEmplacements()) {
                                    if(e.getId() != previous.getId()) {
                                        Route r0 = previous.getRouteTo(this.depot);
                                        Route r1 = e.getRouteTo(previous);
                                        Route r2 = e.getRouteTo(this.depot);
                                        int arrivalTime = Math.max((Integer) label.get("time") - r0.getTemps() + r1.getTemps(), e.getHeureDebut());
                                        //Add only if we arrive there before their closing time
                                        if(arrivalTime <= e.getHeureFin()) {
                                            int time = arrivalTime + r2.getTemps();
                                            int load = (Integer) label.get("load") + c.getDemande();
                                            //Add only if we arrive at depot before their closing time and if vehicule capacity is enough
                                            if(load <= capaV && time <= closeTime) {
                                                newLabel.add(new HashMap<String, Object>() {{
                                                    put("lastEm", previous);
                                                    put("lastLab", label);
                                                    //Probably need to put the last label inside to compare and to add cost and time
                                                    put("em", e);
                                                    put("avancement", pos);
                                                    put("load", load);
                                                    put("cost", (Double) label.get("cost") - r0.getCout() + r1.getCout() + r2.getCout());
                                                    put("time", time);
                                                }});
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //Remove labels from previous iterations
                        labels.getValue().removeIf(el -> pos > (Integer)(((Map<String, Object>) el).get("avancement")));
                        labels.getValue().addAll(newLabel);
                    }
                    boolean flagEmpty = true;
                    for (Map.Entry<Emplacement, List<Map<String, Object>>> labels : labelsMap.entrySet()) {
                        if (labels.getValue().size() != 0) {
                            flagEmpty = false;
                            break;
                        }
                    }
                    labelsMap.entrySet().removeIf(el -> el.getValue().size()==0);
                    if(flagEmpty) break;
                }

                for (Map.Entry<Emplacement, List<Map<String, Object>>> labels : labelsMap.entrySet()) {
                    //Check if there are already solutions that can be removed because worst than the others
                    List<Map<String, Object>> labelsToRemove = new ArrayList<>();
                    for (int l = 0; l < labels.getValue().size()-1; l++) {
                        if(!labelsToRemove.contains(labels.getValue().get(l))) {
                            for (int m = l+1; m < labels.getValue().size(); m++) {
                                if(!labelsToRemove.contains(labels.getValue().get(m))) {
                                    if((Double) labels.getValue().get(m).get("cost") >= (Double) labels.getValue().get(l).get("cost")
                                            && (Integer) labels.getValue().get(m).get("time") >= (Integer) labels.getValue().get(l).get("time")){
                                        labelsToRemove.add(labels.getValue().get(m));
                                    }
                                }
                            }
                        }
                    }
                    //Remove the worst solutions
                    labels.getValue().removeAll(labelsToRemove);

                    //TODO: fix this part (might be the problematic part, the code before ahould be good)

                    //Compare with solutions gotten in the previous iteration  to see if we can deliver one more client with the vehicule
                    for (Map<String, Object> label : labels.getValue()) {
                        if (i == 0) {
                            /*if (cost < V.get(j)) {
                                   V.set(j, cost);
                                   P.set(j, this.depot.getId());
                               }*/
                            V.get(j).add(new HashMap<String, Object>() {{
                                put("em", labels.getKey()); //Probably useless
                                //Probably need to put the last label inside to compare and to add cost and time
                                put("lastLab", label.get("lastLab")); //should be null
                                put("lastEm", label.get("lastEm")); //Probably useless
                                put("cost", label.get("cost"));
                                put("time", label.get("time"));
                            }});
                            //P.get(j).add(((Emplacement) label.get("lastEm")).getId());
                        } else {
                            double cost = (double) label.get("cost");
                            int time = (int) label.get("time");
                            boolean flagAddLabel = true;
                            List<Integer> indexToRemove = new ArrayList<>();
                            for(Map<String, Object> vPrecedant : V.get(i-1)) {
                                for(int k = 0; k<V.get(j).size(); k++){
                                    Map<String, Object> v = V.get(j).get(k);
                                    System.out.println("+++++++++");
                                    System.out.println(v.get("cost"));
                                    System.out.println((Double) vPrecedant.get("cost") + cost);
                                    System.out.println("---------");
                                    if ((Double) v.get("cost") <= (Double) vPrecedant.get("cost") + cost && (Integer) v.get("time") <= (Integer) vPrecedant.get("time") + time) {
                                        flagAddLabel = false;
                                    }
                                    if((Double) v.get("cost") > (Double) vPrecedant.get("cost") + cost && (Integer) v.get("time") > (Integer) vPrecedant.get("time") + time){
                                        if(!indexToRemove.contains(k)) {
                                            indexToRemove.add(k);
                                        }
                                    }
                                }
                                for (int k = indexToRemove.size(); k-- > 0; ) {
                                    V.get(j).remove(indexToRemove.get(k));
                                }
                                if(flagAddLabel) {
                                    System.out.println("add");
                                    V.get(j).add(new HashMap<String, Object>() {{
                                        put("em", labels.getKey());
                                        put("lastEm", vPrecedant.get("em"));
                                        put("lastLab", vPrecedant); //Todo Fix probably
                                        put("cost", (Double) vPrecedant.get("cost") + cost);
                                        put("time", (Integer) vPrecedant.get("time") + time);
                                    }});
                                }
                            }


                            /*int sizeV = V.get(i).size();
                            List<Integer> indexToRemove = new ArrayList<>();
                            boolean flagAddLabel = true;
                            for(int k = 0; k<sizeV; k++){
                                Map<String, Object> v = V.get(i).get(k);
                                System.out.println("+++++++++");
                                System.out.println(v);
                                System.out.println(label);
                                System.out.println("---------");
                                if((Double) label.get("cost") > (Double) v.get("cost") && (Integer) label.get("time") > (Integer) v.get("time")) {
                                    flagAddLabel = false;
                                } else if((Double) label.get("cost") < (Double) v.get("cost") && (Integer) label.get("time") < (Integer) v.get("time")){
                                    indexToRemove.add(k);
                                }
                            }
                            for (int k = indexToRemove.size(); k-- > 0; ) {
                                V.get(i).remove(indexToRemove.get(k));
                            }
                            if(flagAddLabel) {
                                V.get(j).add(new HashMap<String, Object>() {{
                                    put("em", labels.getKey()); //Probably useless
                                    put("lastEm", label.get("lastEm")); //Probably useless
                                    put("lastLab", label.get("lastLab"));
                                    put("cost", label.get("cost"));
                                    put("time", label.get("time"));
                                }});
                            }*/
                        }
                    }
                }


                System.out.println(labelsMap);
            }
        }
        System.out.println(V);
        System.out.println(V.get(V.size()-1));
        /*System.out.println(P);
        Set<Integer> uniqueP = new HashSet<>(P);
        // TODO: find a way to include the count of vehicules to use extra vehicule cost
        int nbExtraVRequired = Math.max(uniqueP.size() - this.instance.getNbVehicules(), 0);
        double realCost = V.get(V.size() - 1) + nbExtraVRequired * this.instance.getCoutVehicule();
        System.out.println("Cost: " + realCost + " (" + nbExtraVRequired + " extra vehicules required)");*/

        return P;
    }

    private void instanceToChromosome() {
        if (this.instance.getPlanningCurrent().getVehicules().size() > 0) {
            this.chromosome = new LinkedList<>();
            Planning p = this.instance.getPlanningCurrent();
            this.depot = this.instance.getDepot();
            for (Vehicule vehicule : p.getVehicules()) {
                List<Emplacement> ems = vehicule.getEmplacements();
                for(Emplacement e: ems) {
                    this.chromosome.add(e.getClient());
                }
            }
        } else { // No planning to optimise
            this.chromosome = new LinkedList<>();
            this.depot = this.instance.getDepot();
            this.chromosome.addAll(this.instance.getClients());

        }
        System.out.println(this.chromosome);
    }

    public static void main(String[] args) {
        Instance i = null;
        //for (int j = 0; j < 40; j++) {
            int id = 1;
            try {
                InstanceFileParser ifp = new InstanceFileParser();
                i = ifp.parse(new File("src/main/resources/instances/instance_" + id + "-triangle.txt"));
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while solving an Instance", ex);
                return;
            }
            NaiveSolver ds = new NaiveSolver(i);
            ds.solve();
            System.out.println("---Cout ns: " + i.getPlanningCurrent().getCout());
            ShortestPathClients sp = new ShortestPathClients(i);
        try {
            sp.findShortestPath();
        } catch (SolverException e) {
            e.printStackTrace();
        }
            /*sp.solve();
            System.out.println("---Cout sp: " + i.getPlanningCurrent().getCout());
            try {
                SolutionWriter sw = new SolutionWriter();
                sw.write(i, "target/instance_" + id + "-triangle_sol_sp.txt");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Exception while writing a solution", ex);
            }*/
        //}
    }

    @Override
    public String toString() {
        return "ShortestPathEmplacements{" + "chromosome=" + chromosome + '}';
    }
}
