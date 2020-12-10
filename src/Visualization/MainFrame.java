package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private InformationPanel infoPanel;

    public MainFrame(){
        super("Evolution Generator");
        this.initializeWindow();
    }

    private void initializeWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(new MainPanel(this));
        setResizable(false);
        setLocation(200,200);
        pack();
    }

    public void addNewMainPanel(){
        //skip
    }



}
