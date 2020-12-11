package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener{
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private InformationPanel infoPanel;
    private MainFrame parent;
    public Timer timer;

    public MainPanel(MainFrame parent){
        this.parent = parent;
        this.engine = new SimulationEngine(30, 30, 0.4, 20, 1000, 1, 30);
        this.infoPanel = new InformationPanel(this.engine, this);
        this.visualizatingPanel = new VisualizatingPanel(this.engine);
        //this.infoPanel.setMinimumSize(new Dimension(InformationPanel.PANEL_WIDTH, 0));

        this.initializeTimer();
        this.initializeWindow();
    }

    private void initializeTimer(){
        this.timer = new Timer(1000, this::actionPerformed);
        this.timer.stop();
    }

    public void changeTimerGap(int gap){
        this.timer.stop();
        System.out.println("zmieniony gap na :" + gap);
        this.timer.setDelay(gap);
    }



    private void initializeWindow(){
        setLayout(new GridLayout(1,2));
        add(this.infoPanel);
        add(this.visualizatingPanel);
        setPreferredSize(new Dimension(1800,900));
        setVisible(true);
    }

    public void mainLoop(){
        if (this.engine.map.getAnimalCounter() == 0){
            this.timer.stop();
            this.infoPanel.buttonPanel.endGame();
            return;
        }
        this.engine.run();
        this.visualizatingPanel.repaint();
        this.infoPanel.updateValues();
    }

    public void setEngineValues(int width,
                                int height,
                                double jungleRatio,
                                double plantEnergy,
                                double startEnergy,
                                double moveEnergy,
                                int initialAnimalCounter){
        this.engine = new SimulationEngine(width, height, jungleRatio, plantEnergy, startEnergy, moveEnergy, initialAnimalCounter);
        this.timer.stop();
        this.infoPanel.buttonPanel.endGame();
        this.changePanelsEngine();
        this.mainLoop();
    }

    private void changePanelsEngine(){
        if (this.visualizatingPanel == null){
            this.visualizatingPanel = new VisualizatingPanel(this.engine);
            add(this.visualizatingPanel);
        }else{
            this.visualizatingPanel.changeEngine(this.engine);
        }
        this.infoPanel.changeEngine(this.engine);
//        this.setPreferredSize(new Dimension(this.visualizatingPanel.PANEL_WIDTH*2, 900));
//        this.parent.pack();

    }


    @Override
    public void actionPerformed(ActionEvent e){
        this.mainLoop();
    }



}
