package MapElement;
import Map.IPositionChangeObserver;
import Map.IWorldMap;
import Enums.*;
import Math.Vector2d;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Animal extends AbstractMapElement{
    private Random randomGenerator = new Random();

    public final int startEnergy;
    public final int moveEnergy;
    private int currentEnergy;
    private IPositionChangeObserver observer;
    private IWorldMap map;
    private Orientation orientation;
    private List<Integer> gens = new ArrayList<>();

    //Constructor for tests
    public Animal (Vector2d position){
        this.position = position;
        this.startEnergy = 10;
        this.moveEnergy = 10;
        this.setGens();
    }

    public Animal (Vector2d position,
                   int startEnergy,
                   int moveEnergy,
                   IPositionChangeObserver observer,
                   IWorldMap map,
                   Orientation orientation){
        this.position = position;
        this.moveEnergy = moveEnergy;
        this.startEnergy = startEnergy;
        this.observer = observer;
        this.map = map;
        this.orientation = orientation;
        this.currentEnergy = this.startEnergy;
        this.setGens();
    }

    public int getCurrentEnergy(){
        return this.currentEnergy;
    }

    public void move(){
        if (this.currentEnergy < this.moveEnergy){
            this.die();
            return;
        }
        Vector2d oldPosition = this.position;
        Animal oldAnimal = this;
        this.rotate();
        this.position = this.map.getPositionAfterMove(this.position.add(this.orientation.toUnitVector()));
        this.observer.positionChanged(oldAnimal, oldPosition, this.position);
    }







    private void rotate(){
        int myRotation = this.gens.get(this.randomGenerator.nextInt(32));
        this.orientation = Orientation.values()[(this.orientation.ordinal()+myRotation)%8];
    }

    private void die(){
        //PASS
    }

    private void setGens(){
        for(int i = 0; i < 32; i++){
            this.gens.add(this.randomGenerator.nextInt(8));
        }
        Collections.sort(this.gens);
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
