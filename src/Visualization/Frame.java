package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame{
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private InformationPanel infoPanel;

    public Frame(SimulationEngine engine){
        super("Evolution Generator");
        this.engine = engine;
        this.infoPanel = new InformationPanel(this.engine);
        this.visualizatingPanel = new VisualizatingPanel(this.engine, this.infoPanel);


        this.initializeWindow();
    }

    private void initializeWindow(){
        setLayout(new GridLayout(1,2));
        add(this.infoPanel);
        add(this.visualizatingPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1280, 720);
        setResizable(true);
        setLocation(200,200);
        pack();
    }



}
