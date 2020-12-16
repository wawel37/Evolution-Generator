package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;

public class StatisticPanel extends JPanel {

    private final JLabel animalCounter = new JLabel("", SwingConstants.CENTER);
    private final JLabel grassCounter = new JLabel("", SwingConstants.CENTER);
    private final JLabel dominatingGenotype = new JLabel("", SwingConstants.CENTER);
    private final JLabel averageEnergy = new JLabel("", SwingConstants.CENTER);
    private final JLabel averageAge = new JLabel("", SwingConstants.CENTER);
    private final JLabel averageDescandants = new JLabel("", SwingConstants.CENTER);
    private final JLabel ageCounter = new JLabel("", SwingConstants.CENTER);
    private SimulationEngine engine;

    public StatisticPanel(SimulationEngine engine){
        this.engine = engine;
        setPreferredSize(new Dimension(400,150));
        setLayout(new GridLayout(7,1, 0 ,0));
        this.addAllLabels();
    }

    public void changeEngine(SimulationEngine engine){
        this.engine = engine;
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

        this.averageDescandants.setText("Average number of children: " + this.engine.map.getAverageDescendants());
        add(this.averageDescandants);

        this.ageCounter.setText("Age: " + this.engine.map.getAgeCounter());
        add(this.ageCounter);
    }

    public void updateValues(){
        this.animalCounter.setText("Number of animals: " + this.engine.map.getAnimalCounter());
        this.grassCounter.setText("Number of grasses: " + this.engine.map.getGrassCounter());
        this.dominatingGenotype.setText("Dominating genotype: " + this.engine.map.getDominatingGenotype());
        this.averageEnergy.setText("Average energy: " + this.engine.map.getAverageEnergy());
        this.averageAge.setText("Average living spam: " + this.engine.map.getAverageAge());
        this.averageDescandants.setText("Average number of children: " + this.engine.map.getAverageDescendants());
        this.ageCounter.setText("Age: " + this.engine.map.getAgeCounter());
    }
}
