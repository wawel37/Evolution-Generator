package MapElement;
import Map.IPositionChangeObserver;
import Map.IWorldMap;
import Enums.*;
import Math.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Animal extends AbstractMapElement{
    private Random randomGenerator = new Random();

    public final double startEnergy;
    public final double moveEnergy;
    public final Genotype genotype;
    private double currentEnergy;
    private IPositionChangeObserver observer;
    private IWorldMap map;
    private Orientation orientation;


    //Constructor for tests
    public Animal (Vector2d position){
        this.position = position;
        this.startEnergy = 10;
        this.moveEnergy = 10;
        this.genotype = new Genotype();
    }

    public Animal (Vector2d position,
                   double startEnergy,
                   double currentEnergy,
                   double moveEnergy,
                   Genotype genes,
                   IPositionChangeObserver observer,
                   IWorldMap map,
                   Orientation orientation){
        this.position = position;
        this.moveEnergy = moveEnergy;
        this.startEnergy = startEnergy;
        this.observer = observer;
        this.map = map;
        this.orientation = orientation;
        this.currentEnergy = currentEnergy;
        this.genotype = new Genotype();
    }

    public double getCurrentEnergy(){
        return this.currentEnergy;
    }

    public void move(){
        if (this.currentEnergy < this.moveEnergy){
            this.die();
            return;
        }
        this.currentEnergy -= this.moveEnergy;
        Vector2d oldPosition = this.position;
        Animal oldAnimal = this;
        this.rotate();
        this.position = this.map.getPositionAfterMove(this.position.add(this.orientation.toUnitVector()));
        this.observer.positionChanged(oldAnimal, oldPosition, this.position);
    }

    public void gainEnergy(double energy){
        this.currentEnergy += energy;
    }

    public double copulating(){
        double result = this.currentEnergy/4;
        this.currentEnergy -= result;
        return result;
    }

    private void rotate(){
        int myRotation = this.genotype.getRandomGene();
        this.orientation = Orientation.values()[(this.orientation.ordinal()+myRotation)%8];
    }

    private void die(){
        this.observer.deleteAnimal(this, this.position);
    }


    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (this.hashCode() != other.hashCode()) return false;
        Animal otherAnimal = (Animal) other;
        return this.getPosition().equals(((Animal) other).getPosition());
    }

    @Override
    public String toString(){
        return "A";
    }
}
