package Visualization;
import Simulation.SimulationEngine;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import Math.Vector2d;

import java.util.Iterator;
import java.util.List;

public class VisualizatingPanel extends JPanel{
    public int PANEL_WIDTH = 900;
    public int PANEL_HEIGHT = 900;
    public SimulationEngine engine;
    private int widthRatio;
    private int heightRatio;

    public VisualizatingPanel(SimulationEngine engine){
        this.engine = engine;
        this.widthRatio = (int)Math.floor(this.PANEL_WIDTH/this.engine.WIDTH);
        this.heightRatio = (int)Math.floor(this.PANEL_HEIGHT/this.engine.HEIGHT);
        this.PANEL_WIDTH = this.widthRatio*this.engine.WIDTH;
        this.PANEL_HEIGHT = this.heightRatio*this.engine.HEIGHT;

        System.out.println("pixel width: " + this.widthRatio + " " + this.PANEL_WIDTH);
        System.out.println("pixel height: " + this.heightRatio + " " + this.PANEL_HEIGHT);

        setSize(this.PANEL_WIDTH,this.PANEL_HEIGHT);
        setPreferredSize(new Dimension(this.PANEL_WIDTH,this.PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

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
        System.out.println(x);
        System.out.println(y);
        System.out.println(width);
        System.out.println(height);
        System.out.println(this.engine.leftJungleVector);
        System.out.println(this.engine.rightJungleVector);
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
        System.out.println(this.engine);
        System.out.println(animalsPositions);
        Iterator<Vector2d> iterator = animalsPositions.iterator();
        while(iterator.hasNext()){
            Vector2d myVector = iterator.next();
            System.out.println(myVector);
            g.fillRect(myVector.x*this.widthRatio, this.PANEL_HEIGHT-((myVector.y+1)*this.heightRatio), this.widthRatio, this.heightRatio);
        }
    }


}
