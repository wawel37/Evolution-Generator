package LiveTesting;
import Enums.Orientation;
import FileManagement.FileHandling;
import MapElement.*;
import Math.*;
import Map.*;
import Simulation.*;
import Visualization.*;

import java.util.*;

public class Main {

    public static void main(String[] args){
        test9();
    }

    public static void test9() {
        new MainFrame();
    }

    public static void test8(){
        Vector2d myPosition1 = new Vector2d(1,1);
        Grass grass1 = new Grass(myPosition1, 11);
        GameMap map = new GameMap(6, 6, 0.5, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 30, 20, 1, new Genotype(), map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition1, 30,10,  1, new Genotype(), map, map, Orientation.NORTH);
        Animal animal3 = new Animal(myPosition1, 30,45,  1, new Genotype(), map, map, Orientation.NORTH);
        Animal animal4 = new Animal(myPosition1, 30,40,  1, new Genotype(), map, map, Orientation.NORTH);
        System.out.println(map.placeGrass(grass1));
        map.addAnimal(animal1);
        map.addAnimal(animal2);
        map.addAnimal(animal3);
        map.addAnimal(animal4);
        System.out.println(map);
        for (int i = 0; i < 5; i++){
            map.eatAllGrasses();
            map.copulateAllAnimals();
            System.out.println(map.getStrongestAnimalsAtPosition(myPosition1));
            System.out.println(map);
        }
    }

    public static void test7(){
        SimulationEngine engine = new SimulationEngine(5, 5, 0.5, 10, 30, 5, 15);
        System.out.println(engine);
        for(int i = 0; i < 6; i++){
            engine.run();
            System.out.println(engine);
        }
    }

    public static void test6(){
        GameMap map = new GameMap(10,10,0.8,10,30,2);
        map.generateRandomAnimals(1);
        for(int i = 0; i < 18; i++){
            System.out.println(map);
            map.moveAllAnimals();
        }
    }

    public static void test5(){
        Vector2d myPosition1 = new Vector2d(1,1);
        Grass grass1 = new Grass(myPosition1, 11);
        GameMap map = new GameMap(101, 101, 0.8, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 30, 20, 1, new Genotype(), map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition1, 30,30,  1, new Genotype(), map, map, Orientation.NORTH);
        System.out.println(map.placeGrass(grass1));
        map.addAnimal(animal1);
        map.addAnimal(animal2);
        for(int i = 0; i < 10; i++) {
            map.copulateAllAnimals();
        }
        printAllAnimals(map);
    }

    public static void test4(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Grass grass1 = new Grass(myPosition1, 11);
        GameMap map = new GameMap(10, 10, 0.8, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 20, 20, 1, new Genotype(), map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition1, 20,20,  1, new Genotype(), map, map, Orientation.NORTH);
        System.out.println(map.placeGrass(grass1));
        map.addAnimal(animal1);
        map.addAnimal(animal2);
        printAllAnimals(map);
        printAllGrasses(map);
        map.eatAllGrasses();
        printAllAnimals(map);
        printAllGrasses(map);
        map.eatAllGrasses();
        printAllAnimals(map);
        printAllGrasses(map);
    }

    public static void test3(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        Vector2d myPosition3 = new Vector2d(3,2);
        Grass grass1 = new Grass(myPosition1, 10);
        Grass grass2 = new Grass(myPosition2, 10);
        Grass grass3 = new Grass(myPosition3, 10);
        Grass grass4 = new Grass(myPosition3, 10);
        GameMap map = new GameMap(10, 10, 0.8, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 10, 10, 0.5, new Genotype(), map, map, Orientation.NORTH);
//        Animal animal2 = new Animal(myPosition2, 10, 0.5, map, map, Orientation.NORTH);
        map.placeAnimal(animal1);
//        map.placeAnimal(animal2);
        System.out.println(map.placeGrass(grass1));
        System.out.println(map.placeGrass(grass2));
        System.out.println(map.placeGrass(grass3));
        System.out.println(map.placeGrass(grass4));
        for(int i = 0; i < 10; i++){
            printAllAnimals(map);
            map.moveAllAnimals();
            System.out.println(i);
        }
    }

    public static void test2(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        GameMap map = new GameMap(10, 10, 0.8, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 10, 10, 10, new Genotype(), map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition2, 10, 10, 10, new Genotype(), map, map, Orientation.NORTH);
    }

    public static void test1(){
        Vector2d myPosition1 = new Vector2d(2,2);
        Vector2d myPosition2 = new Vector2d(3,3);
        GameMap map = new GameMap(10, 10, 0.5, 10, 30, 1);
        Animal animal1 = new Animal(myPosition1, 10, 10, 10, new Genotype(), map, map, Orientation.NORTH);
        Animal animal2 = new Animal(myPosition2, 10, 10, 10, new Genotype(), map, map, Orientation.NORTH);
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
        for (Map.Entry<Vector2d, List<Animal>> entry : map.getAnimals().entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            System.out.println("Pozycje animalow na pozycji " + entry.getKey() + ":");
            while(iterator.hasNext()){
                Animal myAnimal = iterator.next();
                System.out.println(myAnimal.getPosition());
                System.out.println("Energy: " + myAnimal.getCurrentEnergy());
            }
        }
    }

    public static void printAllGrasses(GameMap map){
        for (Map.Entry<Vector2d, Grass> entry : map.getGrasses().entrySet()){
            System.out.println("Pozycje grassow na pozycji" + entry.getKey() + ":");
            System.out.println(entry.getValue().getPosition());
        }
    }

    public static boolean NoGenes(Genotype myGenes){
        for (int i = 0; i < 8; i++){
            int counter = 0;
            for (int j = 0; j < 32; j++){
                if (myGenes.genes[j] == i) counter++;
            }
            if (counter == 0) return false;
        }
        return true;
    }
}
