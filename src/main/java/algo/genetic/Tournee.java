package algo.genetic;

import java.util.ArrayList;
import java.util.List;

public class Tournee {
    private double cost;
    private List<Label> tournee;

    public Tournee() {
        this.cost = 0.0;
        this.tournee = new ArrayList<>();
    }

    public Tournee(Label label) {
        this.cost = label.getCost();
        this.tournee = new ArrayList<>();
        this.tournee.add(label);
    }

    public Tournee(Label label, Tournee parentTournee) {
        this.cost = label.getCost() + parentTournee.getCost();
        this.tournee = new ArrayList<>(parentTournee.getTournee());
        this.tournee.add(label);
    }

    public double getCost() {
        return cost;
    }

    public List<Label> getTournee() {
        return tournee;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "cost=" + cost +
                ", tournee=" + tournee +
                '}';
    }
}
