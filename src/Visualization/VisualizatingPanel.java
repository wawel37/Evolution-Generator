package Visualization;
import MapElement.Animal;
import Simulation.SimulationEngine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import javax.swing.*;
import Math.Vector2d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VisualizatingPanel extends JPanel implements MouseListener{
    public int PANEL_WIDTH = 900;
    public int PANEL_HEIGHT = 900;
    public SimulationEngine engine;
    private MainPanel parent;
    private int widthRatio;
    private int heightRatio;

    public VisualizatingPanel(SimulationEngine engine, MainPanel parent){
        this.parent = parent;
        this.changeEngine(engine);
        this.addMouseListener(this);

    }

    public void changeEngine(SimulationEngine engine){
        this.engine = engine;
        this.PANEL_WIDTH = 900;
        this.PANEL_HEIGHT = 900;
        this.widthRatio = (int)Math.floor(this.PANEL_WIDTH/this.engine.WIDTH);
        this.heightRatio = (int)Math.floor(this.PANEL_HEIGHT/this.engine.HEIGHT);
        this.widthRatio = Math.min(this.widthRatio, this.heightRatio);
        this.heightRatio = Math.min(this.widthRatio, this.heightRatio);
        this.PANEL_WIDTH = this.widthRatio*this.engine.WIDTH;
        this.PANEL_HEIGHT = this.heightRatio*this.engine.HEIGHT;
        setPreferredSize(new Dimension(900, 900));
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
        List<Animal> animalsPositions = this.engine.map.getAnimalsAsList();
        double startEnergy = this.engine.getStartEnergy();
        Iterator<Animal> iterator = animalsPositions.iterator();
        while(iterator.hasNext()){
            Animal myAnimal = iterator.next();
            if(this.parent.showDominatingGenotype && myAnimal.getDominatingGenotype() == this.engine.map.getDominatingGenotype()) {
                g.setColor(new Color(0, 255, 255));
            }else {
                int myColor = (int) ((myAnimal.getCurrentEnergy() / startEnergy) * 255);
                if (myColor > 255) myColor = 255;
                g.setColor(new Color(myColor, 0, 0));
            }
            Vector2d myVector = myAnimal.getPosition();
            g.fillRect(myVector.x*this.widthRatio, this.PANEL_HEIGHT-((myVector.y+1)*this.heightRatio), this.widthRatio, this.heightRatio);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        List<Animal> strongestAnimal = this.engine.map.getStrongestAnimalsAtPosition(new Vector2d(x/this.widthRatio, (this.PANEL_HEIGHT - y)/this.heightRatio));
        if (strongestAnimal != null && !this.parent.isTimerStarted()){
            Animal myAnimal = strongestAnimal.get(0);
            new AnimalInfoFrame(myAnimal, this.parent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){
        //PASS
    }

    @Override
    public void mouseExited(MouseEvent e){
        //PASS
    }

    @Override
    public void mousePressed(MouseEvent e){
        //PASS
    }

    @Override
    public void mouseReleased(MouseEvent e){
        //PASS
    }




}
