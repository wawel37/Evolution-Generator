package Visualization;

import Simulation.SimulationEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private final int DELAY = 100;
    private SimulationEngine engine;
    private VisualizatingPanel visualizatingPanel;
    private Timer timer;

    public Frame(SimulationEngine engine){
        super("Evolution Generator");
        this.engine = engine;
        this.visualizatingPanel = new VisualizatingPanel(this.engine);

        this.initializeWindow();

        while(true){
            try {
                Thread.sleep(100);
            }catch(Exception e){
                System.out.println(e);
            }
            this.mainLoop();
        }
//        this.timer = new Timer(this.DELAY, this::actionPerformed);
//        this.timer.start();
    }

    private void initializeWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1280, 720);
        setResizable(false);
        setLocation(200,200);
        add(this.visualizatingPanel);
        pack();
    }

    public void mainLoop(){
//        if(engine.map.getAnimalCounter() == 0){
//            timer.stop();
//            return;
//        }
        this.engine.run();
        this.visualizatingPanel.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        this.mainLoop();
    }
}
