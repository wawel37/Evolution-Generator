package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AdditionalVisualization extends JFrame implements WindowListener{
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private MainPanel parent;

    public AdditionalVisualization(SimulationEngine engine, MainPanel parent){
        this.engine = engine;
        this.parent = parent;

        this.initializeWindow();
    }

    private void initializeWindow(){
        this.addWindowListener(this);
        setVisible(true);
        setLocation(300,100);
        this.visualizatingPanel = new VisualizatingPanel(this.engine, this.parent);
        add(this.visualizatingPanel);
        pack();
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
