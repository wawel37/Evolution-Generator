package Simulation;

import MapElement.Animal;
import MapElement.Grass;
import Math.*;
import Map.GameMap;
import Visualization.Frame;

import javax.swing.Timer;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SimulationEngine implements ISimulation {
    public final int WIDTH;
    public final int HEIGHT;
    public final double jungleRatio;
    public final double plantEnergy;
    public final double startEnergy;
    public final double moveEnergy;
    public final GameMap map;
    public final Vector2d leftJungleVector;
    public final Vector2d rightJungleVector;

    public SimulationEngine(int width,
                            int height,
                            double jungleRatio,
                            double plantEnergy,
                            double startEnergy,
                            double moveEnergy,
                            int initialAnimalCounter){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.jungleRatio = jungleRatio;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.map = new GameMap(this.WIDTH, this.HEIGHT, this.jungleRatio, this.plantEnergy, this.startEnergy, this.moveEnergy);
        this.map.generateRandomAnimals(initialAnimalCounter);
        this.map.placeTwoGrasses();
        this.leftJungleVector = this.map.getLeftLowerJungleVector();
        this.rightJungleVector = this.map.getRightUpperJungleVector();
    }

    public void run(){
        this.map.moveAllAnimals();
        this.map.eatAllGrasses();
        this.map.copulateAllAnimals();
        this.map.placeTwoGrasses();
        this.map.calculateStatistics();
    }

    public List<Vector2d> getAnimalsPositions(){
        List<Vector2d> result = new LinkedList<>();
        Map<Vector2d, List<Animal>> myAnimals = this.map.getAnimals();
        for (Map.Entry<Vector2d, List<Animal>> entry : myAnimals.entrySet()){
            result.add(entry.getKey());
        }
        return result;
    }

    public List<Vector2d> getGrassesPositions(){
        List<Vector2d> result = new LinkedList<>();
        Map<Vector2d, Grass> myGrasses = this.map.getGrasses();
        for(Map.Entry<Vector2d, Grass> entry : myGrasses.entrySet()){
            result.add(entry.getKey());
        }
        return result;
    }

    public void startAppFrame(){
       Frame frame =  new Frame(this);
    }

    @Override
    public String toString(){
        return this.map.toString();
    }

}
