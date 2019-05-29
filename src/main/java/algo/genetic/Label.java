/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.genetic;

import java.util.ArrayList;
import java.util.List;
import model.Emplacement;

/**
 *
 * @author Thomas
 */
public class Label {
    private int load;
    private double cost;
    private int time;
    private List<Emplacement> precedents;

    public Label(int load, double cost, int time) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.precedents = new ArrayList<>();
    }
    
    public Label(int load, double cost, int time, Emplacement firstEmplacement) {
        this(load, cost, time);
        precedents.add(firstEmplacement);
    }
    
    public Label(int load, double cost, int time, List<Emplacement> precedents) {
        this.load = load;
        this.cost = cost;
        this.time = time;
        this.precedents = new ArrayList<>(precedents);
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Emplacement> getPrecedents() {
        return precedents;
    }

    public void setPrecedents(List<Emplacement> precedents) {
        this.precedents = precedents;
    }
    
    public void addPrecedent(Emplacement precedent) {
        this.precedents.add(precedent);
    }

    @Override
    public String toString() {
        return "Label{" + "load=" + load + ", cost=" + cost + ", time=" + time + ", precedents=" + precedents + '}';
    }
    
}
