package Visualization;
import Simulation.SimulationEngine;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import Math.Vector2d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VisualizatingPanel extends JPanel{
    public int PANEL_WIDTH = 900;
    public int PANEL_HEIGHT = 900;
    public SimulationEngine engine;
    private int widthRatio;
    private int heightRatio;
    private InformationPanel infoPanel;

    public VisualizatingPanel(SimulationEngine engine, InformationPanel infoPanel){
        this.engine = engine;
        this.infoPanel = infoPanel;
        this.widthRatio = (int)Math.floor(this.PANEL_WIDTH/this.engine.WIDTH);
        this.heightRatio = (int)Math.floor(this.PANEL_HEIGHT/this.engine.HEIGHT);
        this.PANEL_WIDTH = this.widthRatio*this.engine.WIDTH;
        this.PANEL_HEIGHT = this.heightRatio*this.engine.HEIGHT;

        Timer timer = new Timer(100, new GameLoop(this));
        timer.start();
        setPreferredSize(new Dimension(this.PANEL_WIDTH, this.PANEL_HEIGHT));
        System.out.println(this.PANEL_HEIGHT + " " + this.PANEL_WIDTH);
    }


    @Override
    public void paint(Graphics g){
        super.paintComponent(g);

        this.drawSteppe(g);
        this.drawJungle(g);
        this.drawGrasses(g);
        this.drawAnimals(g);


    }

    private void drawSteppe(Graphics g){
        g.setColor(new Color(0,160,7));
        g.fillRect(0,0,this.PANEL_WIDTH, this.PANEL_HEIGHT);
    }

    private void drawJungle(Graphics g){
        g.setColor(new Color(0,100,0));
        int x = this.engine.leftJungleVector.x*this.widthRatio;
        int y = this.PANEL_HEIGHT-((this.engine.rightJungleVector.y+1)*this.heightRatio);
        int width = (this.engine.rightJungleVector.x-this.engine.leftJungleVector.x+1)*this.widthRatio;
        int height = (this.engine.rightJungleVector.y-this.engine.leftJungleVector.y+1)*this.heightRatio;
        g.fillRect(x, y, width, height);
    }

    private void drawGrasses(Graphics g){
        List<Vector2d> grassesPositions = this.engine.getGrassesPositions();
        g.setColor(new Color(236,179,72));
        Iterator<Vector2d> iterator = grassesPositions.iterator();
        while(iterator.hasNext()){
            Vector2d myVector = iterator.next();
            g.fillRect(myVector.x*this.widthRatio, this.PANEL_HEIGHT-((myVector.y+1)*this.heightRatio), this.widthRatio, this.heightRatio);
        }

    }

    private void drawAnimals(Graphics g){
        List<Vector2d> animalsPositions = this.engine.getAnimalsPositions();
        g.setColor(new Color(0, 0, 0));
        Iterator<Vector2d> iterator = animalsPositions.iterator();
        while(iterator.hasNext()){
            Vector2d myVector = iterator.next();
            g.fillRect(myVector.x*this.widthRatio, this.PANEL_HEIGHT-((myVector.y+1)*this.heightRatio), this.widthRatio, this.heightRatio);
        }
    }

    public void loop(){
        this.engine.run();
        repaint();
        this.infoPanel.updateValues();
    }


}
