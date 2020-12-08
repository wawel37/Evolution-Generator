package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;

public class Frame extends JFrame{
    private SimulationEngine engine;


    public Frame(SimulationEngine engine){
        super("Evolution Generator");
        this.engine = engine;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1280, 720);
        setResizable(false);
        setLocation(200,200);


        add(new VisualizatingPanel(this.engine));
        pack();
    }
}
