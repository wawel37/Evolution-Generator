package LiveTesting;
import Enums.Orientation;
import Map.IPositionChangeObserver;
import MapElement.*;
import Math.*;
import Map.*;

import java.util.*;

public class Main {

    public static void main(String[] args){
        test3();
    }

    public static void test3(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        Vector2d myPosition3 = new Vector2d(3,2);
        Grass grass1 = new Grass(myPosition1, 10);
        Grass grass2 = new Grass(myPosition2, 10);
        Grass grass3 = new Grass(myPosition3, 10);
        Grass grass4 = new Grass(myPosition3, 10);
        GameMap map = new GameMap(10, 10, 0.8);
        Animal animal1 = new Animal(myPosition1, 10, 10, map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition2, 10, 10, map, map, Orientation.NORTH);
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        System.out.println(map.placeGrass(grass1));
        System.out.println(map.placeGrass(grass2));
        System.out.println(map.placeGrass(grass3));
        System.out.println(map.placeGrass(grass4));
    }

    public static void test2(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        GameMap map = new GameMap(10, 10, 0.8);
        Animal animal1 = new Animal(myPosition1, 10, 10, map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition2, 10, 10, map, map, Orientation.NORTH);
    }

    public static void test1(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        GameMap map = new GameMap(10, 10, 0.5);
        Animal animal1 = new Animal(myPosition1, 10, 10, map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition2, 10, 10, map, map, Orientation.NORTH);
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        System.out.println("Jałowa pozycja animala1: " + animal1.getPosition());
        System.out.println("Jałowa pozycja animala2: " + animal2.getPosition());
        printAllAnimals(map);
        while(true) {
            animal1.move();
            animal2.move();
            System.out.println("Jałowa pozycja animala1: " + animal1.getPosition());
            System.out.println("Jałowa pozycja animala2: " + animal2.getPosition());
            printAllAnimals(map);
            if(animal1.getPosition().equals(animal2.getPosition())){
                break;
            }
        }
    }

    public static void printAllAnimals(GameMap map){
        for (Map.Entry<Vector2d, Set<Animal>> entry : map.getAnimals().entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            System.out.println("Pozycje animalow na pozycji " + entry.getKey() + ":");
            while(iterator.hasNext()){
                System.out.println(iterator.next().getPosition());
            }
        }
    }
}
