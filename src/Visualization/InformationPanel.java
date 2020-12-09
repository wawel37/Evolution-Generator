package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {
    private final int PANEL_WIDTH = 900;
    private final int PANEL_HEIGHT = 900;
    private SimulationEngine engine;
    private final JLabel animalCounter = new JLabel();
    private final JLabel grassCounter = new JLabel();
    private final JLabel dominatingGenotype = new JLabel();
    private final JLabel averageEnergy = new JLabel();
    private final JLabel averageAge = new JLabel();
    private final JLabel averageDescandants = new JLabel();


    public InformationPanel(SimulationEngine engine){
        setSize(this.PANEL_WIDTH, this.PANEL_HEIGHT);
        setPreferredSize(new Dimension(this.PANEL_WIDTH, 0));
        setVisible(true);
        this.engine = engine;
        setLayout(new GridLayout(10, 1));

        this.addAllLabels();
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
    }

    public void updateValues(){
        this.animalCounter.setText("Number of animals: " + this.engine.map.getAnimalCounter());
        this.grassCounter.setText("Number of grasses: " + this.engine.map.getGrassCounter());
        this.dominatingGenotype.setText("Dominating genotype: " + this.engine.map.getDominatingGenotype());
        this.averageEnergy.setText("Average energy: " + this.engine.map.getAverageEnergy());
    }

}
