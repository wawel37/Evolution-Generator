package Visualization;

import MapElement.Animal;
import Simulation.SimulationEngine;
import FileManagement.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainPanel extends JPanel implements ActionListener, IAnimalWatchObserver{
    private SimulationEngine engine;
    private SimulationEngine secondEngine;
    private VisualizatingPanel visualizatingPanel;
    private AdditionalVisualization secondVisualization;
    private InformationPanel infoPanel;
    private MainFrame parent;
    public boolean showDominatingGenotype;
    public Timer timer;
    private long start = 0;
    private long end = 0;
    private Set<AnimalWatchFrame> framesObserved;



    public MainPanel(MainFrame parent){
        this.parent = parent;
        if(!this.setEngineFromJSON()){
            this.engine = new SimulationEngine(10, 10, 0.7, 20, 1000, 1, 2);
        }
        this.infoPanel = new InformationPanel(this.engine, this);
        this.visualizatingPanel = new VisualizatingPanel(this.engine, this);
        this.framesObserved = new HashSet<>();
        //this.infoPanel.setMinimumSize(new Dimension(InformationPanel.PANEL_WIDTH, 0));
        this.showDominatingGenotype = false;

        this.initializeTimer();
        this.initializeWindow();
    }

    private void initializeTimer(){
        this.timer = new Timer(10, this::actionPerformed);
        this.timer.stop();
        this.timer.setDelay(1000);
    }

    public void changeTimerGap(int gap){
        this.timer.stop();
        this.timer.setDelay(gap);
        System.out.println("gap: " + gap);
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
        if (this.secondVisualization != null){
            this.secondVisualization.mainLoop();
        }
        this.infoPanel.updateValues();
        this.updateFrames();
    }

    public void setEngineValues(int width,
                                int height,
                                double jungleRatio,
                                double plantEnergy,
                                double startEnergy,
                                double moveEnergy,
                                int initialAnimalCounter){
        Animal.resetAnimalCounter();
        this.engine = new SimulationEngine(width, height, jungleRatio, plantEnergy, startEnergy, moveEnergy, initialAnimalCounter);
        this.timer.stop();
        this.infoPanel.buttonPanel.endGame();
        this.changePanelsEngine();
        this.visualizatingPanel.repaint();
        this.updateFrames();
    }

    private void changePanelsEngine(){
        if (this.visualizatingPanel == null){
            this.visualizatingPanel = new VisualizatingPanel(this.engine, this);
            add(this.visualizatingPanel);
        }else{
            this.visualizatingPanel.changeEngine(this.engine);
        }
        this.infoPanel.changeEngine(this.engine);
        if (this.secondVisualization != null) {
            this.startSecondVisualization();
        }

    }

    public boolean isTimerStarted(){
        return this.infoPanel.buttonPanel.isStarted;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        this.end = System.currentTimeMillis();
        System.out.println(this.end - this.start);
        this.mainLoop();
        this.updateFrames();
        this.start = System.currentTimeMillis();
    }

    @Override
    public void addFrame(AnimalWatchFrame frame) {
        this.framesObserved.add(frame);
    }

    @Override
    public void deleteFrame(AnimalWatchFrame frame) {
        this.framesObserved.remove(frame);
    }

    @Override
    public void updateFrames() {
        Iterator<AnimalWatchFrame> iterator = this.framesObserved.iterator();
        while(iterator.hasNext()){
            iterator.next().updateLabels();
        }
    }

    public void startSecondVisualization(){
        if(this.secondVisualization != null){
            this.secondVisualization.dispatchEvent(new WindowEvent(this.secondVisualization, WindowEvent.WINDOW_CLOSING));
        }
        this.secondEngine = this.engine.copy();
        this.secondVisualization = new AdditionalVisualization(this.secondEngine, this);
    }

    public void stopSecondVisualization(){
        this.secondVisualization.dispatchEvent(new WindowEvent(this.secondVisualization, WindowEvent.WINDOW_CLOSING));
        this.secondVisualization = null;
    }

    public void secondVisualizationClosed(){
        this.secondVisualization = null;
        this.infoPanel.buttonPanel.resetCheckBox();
    }

    public void showDominatingGenotype(){
        this.showDominatingGenotype = true;
        this.visualizatingPanel.repaint();
        if(this.secondVisualization != null){
            this.secondVisualization.repaint();
        }
    }

    public void hideDominatingGenotype(){
        this.showDominatingGenotype = false;
        this.visualizatingPanel.repaint();
        if(this.secondVisualization != null){
            this.secondVisualization.repaint();
        }
    }

    public void saveToFile(){
        if(this.infoPanel.buttonPanel.isStarted){ return; }
        File file = FileHandling.selectFile();
        if (file != null) {
            FileHandling.appendToFile(file, this.engine.toString());
            if (this.secondVisualization != null) {
                FileHandling.appendToFile(file, this.secondEngine.toString());
            }
        }else{
            System.out.println("Choosing output file failed!");
        }
    }

    private boolean setEngineFromJSON(){
        String temp;
        int width, height, initialAnimalCounter;
        double jungleRatio, plantEnergy, startEnergy, moveEnergy;
        temp = FileHandling.getStringFromJSON("width");
        if (temp == null) { return false; }
        width = Integer.parseInt(temp);

        temp = FileHandling.getStringFromJSON("height");
        if (temp == null) { return false; }
        height = Integer.parseInt(temp);

        temp = FileHandling.getStringFromJSON("jungleRatio");
        if (temp == null) { return false; }
        jungleRatio = Double.parseDouble(temp);

        temp = FileHandling.getStringFromJSON("plantEnergy");
        if (temp == null) { return false; }
        plantEnergy = Double.parseDouble(temp);

        temp = FileHandling.getStringFromJSON("startEnergy");
        if (temp == null) { return false; }
        startEnergy = Double.parseDouble(temp);

        temp = FileHandling.getStringFromJSON("moveEnergy");
        if (temp == null) { return false; }
        moveEnergy = Double.parseDouble(temp);

        temp = FileHandling.getStringFromJSON("initialAnimalCounter");
        if (temp == null) { return false; }
        initialAnimalCounter = Integer.parseInt(temp);

        this.engine = new SimulationEngine(width, height, jungleRatio, plantEnergy, startEnergy, moveEnergy, initialAnimalCounter);
        return true;
    }



}
