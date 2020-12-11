package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainFrame extends JFrame {
    private SimulationEngine engine;
    private MainPanel mainPanel;

    public MainFrame(){
        super("Evolution Generator");
        this.initializeWindow();
    }

    private void initializeWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.mainPanel = new MainPanel(this);
        add(this.mainPanel);
        setResizable(true);
        setLocation(10,10);
        pack();
    }

    private void test(){
    }

    public void addNewMainPanel(){
        //skip
    }
}
