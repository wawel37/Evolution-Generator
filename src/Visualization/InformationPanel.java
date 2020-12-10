package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class InformationPanel extends JPanel {
    static final int PANEL_WIDTH = 900;
    private SimulationEngine engine;
    private MainPanel parent;
    private ButtonPanel buttonPanel;
    private ConfigurationPanel configPanel;




    private final JLabel animalCounter = new JLabel();
    private final JLabel grassCounter = new JLabel();
    private final JLabel dominatingGenotype = new JLabel();
    private final JLabel averageEnergy = new JLabel();
    private final JLabel averageAge = new JLabel();
    private final JLabel averageDescandants = new JLabel();


    public InformationPanel(SimulationEngine engine, MainPanel parent){
        this.parent = parent;
        setPreferredSize(new Dimension(PANEL_WIDTH, 0));
        this.engine = engine;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.addAllLabels();
        this.addConfigurationPanel();
        this.addButtonPanel();
    }

    private void addConfigurationPanel(){
        this.configPanel = new ConfigurationPanel(this.parent);
        add(this.configPanel);
    }

    private void addButtonPanel(){
        this.buttonPanel = new ButtonPanel(this.parent);
        add(this.buttonPanel);
    }

    private void addAllLabels(){

        this.animalCounter.setText("Number of animals: " + this.engine.map.getAnimalCounter());
        add(this.animalCounter);

        this.grassCounter.setText("Number of grasses: " + this.engine.map.getGrassCounter());
        add(this.grassCounter);

        this.dominatingGenotype.setText("Dominating genotype: " + this.engine.map.getDominatingGenotype());
        add(this.dominatingGenotype);

        this.averageEnergy.setText("Average energy: " + this.engine.map.getAverageEnergy());
        add(this.averageEnergy);

        this.averageAge.setText("Average living spam: " + this.engine.map.getAverageAge());
        add(this.averageAge);

        this.averageDescandants.setText("Average number of descandants: " + this.engine.map.getAverageDescendants());
        add(this.averageDescandants);
    }

    public void updateValues(){
        this.animalCounter.setText("Number of animals: " + this.engine.map.getAnimalCounter());
        this.grassCounter.setText("Number of grasses: " + this.engine.map.getGrassCounter());
        this.dominatingGenotype.setText("Dominating genotype: " + this.engine.map.getDominatingGenotype());
        this.averageEnergy.setText("Average energy: " + this.engine.map.getAverageEnergy());
        this.averageAge.setText("Average living spam: " + this.engine.map.getAverageAge());
        this.averageDescandants.setText("Average number of descandants: " + this.engine.map.getAverageDescendants());
    }

}
