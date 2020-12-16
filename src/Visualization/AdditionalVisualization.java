package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Flow;

public class AdditionalVisualization extends JFrame implements WindowListener{
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private StatisticPanel statPanel;
    private MainPanel parent;
    private boolean isFinished = false;

    public AdditionalVisualization(SimulationEngine engine, MainPanel parent){
        this.engine = engine;
        this.parent = parent;

        this.initializeWindow();
    }

    private void initializeWindow(){
        this.addWindowListener(this);
        setVisible(true);
        setLocation(300,100);
        setLayout(new FlowLayout());
        this.statPanel = new StatisticPanel(this.engine);
        add(this.statPanel);
        this.visualizatingPanel = new VisualizatingPanel(this.engine, this.parent);
        add(this.visualizatingPanel);
        pack();
    }

    public void mainLoop(){
        if (this.isFinished == true) return;
        if (this.engine.map.getAnimalCounter() == 0){
            this.isFinished = true;
        }
        this.engine.run();
        this.visualizatingPanel.repaint();
        this.statPanel.updateValues();
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.parent.secondVisualizationClosed();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
