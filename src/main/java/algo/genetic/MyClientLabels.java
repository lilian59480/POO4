/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Emplacement;

/**
 *
 * @author Thomas
 */
public class MyClientLabels {
    private int clientNb;
    private Map<Emplacement, List<Label>> em2Labels;

    public MyClientLabels(int cnb) {
        clientNb = cnb;
        em2Labels = new HashMap<>();
    }

    public Map<Emplacement, List<Label>> getEm2Labels() {
        return em2Labels;
    }
    
    public List<Label> getLabels(Emplacement e) {
        return em2Labels.get(e);
    }
    
    private void createEmplacementEntry(Emplacement e) {
        if(!this.em2Labels.containsKey(e)) {
            this.em2Labels.put(e, new ArrayList<Label>());
        }
    }

    public void addLabelsToEmplacement(Emplacement e, List<Label> labels) {
        if(!this.em2Labels.containsKey(e)) {
            createEmplacementEntry(e);
        }
        for(Label label: labels) {
            this.em2Labels.get(e).add(label);
        }
    }
    
    public void addLabelToEmplacement(Emplacement e, Label label) {
        if(!this.em2Labels.containsKey(e)) {
            createEmplacementEntry(e);
        }
        this.em2Labels.get(e).add(label);
    }

    @Override
    public String toString() {
        return "MyClientLabels{" + "client nb=" + clientNb + ", em2Labels=" + em2Labels + '}';
    }

    
    
}
