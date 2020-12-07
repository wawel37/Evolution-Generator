package Map;
import Enums.Orientation;
import MapElement.*;
import Math.*;



import java.util.*;
import java.util.Random;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final int width;
    protected final int height;
    protected final double jungleRatio;
    protected Vector2d leftLowerJungleVector;
    protected Vector2d rightUpperJungleVector;
    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    protected Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    protected Random randomGenerator = new Random();

    //these variables will be injected from Graphical InterFace or simulation engine
    protected double plantEnergy = 10;
    protected double startEnergy = 10;
    protected double moveEnergy = 0.5;

    public AbstractWorldMap(int width, int height, double jungleRatio){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.calculateJungle();
    }

    //We cannot place 2 animals at the same position
    @Override
    public boolean placeAnimal(Animal animal){
        if (this.isOccupiedByAnimal(animal.getPosition())) return false;
        this.addAnimal(animal);
        return true;
    }

    @Override
    public boolean placeGrass(Grass grass){
        if (this.objectsAt(grass.getPosition()).size() == 0){
            this.grasses.put(grass.getPosition(), grass);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupiedByAnimal(Vector2d position){
        if (this.animals.get(position) == null){
            return false;
        }
        return true;
    }

    @Override
    public Vector2d getPositionAfterMove(Vector2d position){
        return new Vector2d((position.x + width) % this.width,(position.y + height) % this.height);
    }

    @Override
    public List<Object> objectsAt(Vector2d position){
        List<Object> result = new ArrayList<>();
        if(this.isOccupiedByAnimal(position)){
            Iterator<Animal> iterator = this.animals.get(position).iterator();
            while(iterator.hasNext()){
                result.add(iterator.next());
            }
        }
        if(this.grasses.get(position) != null){
            result.add(this.grasses.get(position));
        }
        return result;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        this.deleteAnimal(animal, oldPosition);
        this.addAnimal(animal);
    }

    @Override
    public void deleteGrass(Vector2d position){
        this.grasses.remove(position);
    }

    @Override
    public void deleteAnimal(Animal animal, Vector2d position){
        this.animals.get(position).remove(animal);
        if(this.animals.get(position).isEmpty()){
            this.animals.remove(position);
        }
    }

    //Putting animal on the map without checking if the position is occupied
    public void addAnimal(Animal animal){
        if (this.animals.get(animal.getPosition()) == null){
            this.animals.put(animal.getPosition(), new TreeSet<Animal>(new AnimalComparatorByCurrentEnergy()));
        }
        this.animals.get(animal.getPosition()).add(animal);
    }

    /*
        Given grass is getting eaten unless there is no animal at that position
        Returns True if grass was eaten
        Returns False if it wasn't
     */
    public boolean eatGrass(Grass grass){
        Vector2d position = grass.getPosition();
        if (!this.isOccupiedByAnimal(position)) return false;

        List<Animal> strongestAnimalsAtPosition = this.getStrongestAnimalsAtPosition(position);
        double energyToGain = grass.getPlantEnergy()/strongestAnimalsAtPosition.size();
        for(Animal animal : strongestAnimalsAtPosition){
            animal.gainEnergy(energyToGain);
        }
        this.deleteGrass(grass.getPosition());

        return true;
    }


    /*
        Creates new Animal by copulating 2 other animals at the given position
        Returns null if there wasn't at least 2 animals at given position
     */
    public Animal getNewAnimalAtPosition(Vector2d position){
        if (!this.isOccupiedByAnimal(position) || this.animals.get(position).size() == 1) return null;

        List<Animal> strongestAnimals = this.getStrongestAnimalsAtPosition(position);
        Animal strongestAnimal1 = null;
        Animal strongestAnimal2 = null;

        if (strongestAnimals.size() < 2){
            strongestAnimal1 = this.animals.get(position).first();
            strongestAnimal2 = this.animals.get(position).higher(strongestAnimal1);
        }else {
            int index1 = this.randomGenerator.nextInt(strongestAnimals.size());
            int index2;
            do {
                index2 = this.randomGenerator.nextInt(strongestAnimals.size());
            } while (index1 == index2);

            strongestAnimal1 = strongestAnimals.get(index1);
            strongestAnimal2 = strongestAnimals.get(index2);
        }

        double energyForNewAnimal = 0;
        energyForNewAnimal += strongestAnimal1.copulating();
        energyForNewAnimal += strongestAnimal2.copulating();

        Vector2d positionForNewAnimal =  this.getPositionAfterMove(position.add(Orientation.values()[randomGenerator.nextInt(8)].toUnitVector()));
        Orientation orientationForNewAnimal = Orientation.values()[this.randomGenerator.nextInt(8)];

        Animal newAnimal = new Animal (positionForNewAnimal, this.startEnergy, energyForNewAnimal, this.moveEnergy, new Genotype(strongestAnimal1, strongestAnimal2), this, this, orientationForNewAnimal);

        return newAnimal;
    }

    /*
        Returns a list of strongest animals at the specific position
        REMEMBER TO CHECK IF THE POSITION IS OCCUPIED BY ANY ANIMAL
    */
    public List<Animal> getStrongestAnimalsAtPosition(Vector2d position){
        TreeSet<Animal> myAnimals = this.animals.get(position);
        List<Animal> strongestAnimalsAtPosition = new ArrayList<>();
        double maxEnergy = myAnimals.first().getCurrentEnergy();
        Iterator<Animal> iterator = myAnimals.iterator();

        while(iterator.hasNext()){
            Animal myAnimal = iterator.next();
            if(myAnimal.getCurrentEnergy() == maxEnergy) {
                strongestAnimalsAtPosition.add(myAnimal);
            }
        }
        return strongestAnimalsAtPosition;
    }


    //Getter for animals
    public Map<Vector2d, TreeSet<Animal>> getAnimals(){
        return this.animals;
    }

    //Getter for grasses
    public Map<Vector2d, Grass> getGrasses() { return this.grasses; }


    //Getter for all existing grasses on the map as a List interface
    public List<Grass> getGrassesAsList(){
        List<Grass> result = new ArrayList<>();
        for(Map.Entry<Vector2d, Grass> entry: this.grasses.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }

    //Moving all the animals at the same time
    public void moveAllAnimals(){
        List<Animal> toMove = new ArrayList<>();

        //First we get all the animals that exist on the map
        for(Map.Entry<Vector2d, TreeSet<Animal>> entry : this.animals.entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            while(iterator.hasNext()){
                toMove.add(iterator.next());
            }
        }

        /*
            Then we move each one of them, if it doesn't have enough energy to move,
             it simply dies, that's how life goes ¯\_(ツ)_/¯
        */
        Iterator<Animal> iterator = toMove.iterator();
        while(iterator.hasNext()){
            iterator.next().move();
        }
    }

    //For every grass on the map, we try to eat it
    public void eatAllGrasses(){
        List<Grass> toEat = this.getGrassesAsList();
        for(Grass grass : toEat){
            this.eatGrass(grass);
        }
    }

    public void copulateAllAnimals(){
        List<Animal> toAdd = new ArrayList<Animal>();

        //Getting all new animals that were created thought the copulation at very single point where was it possible
        for(Map.Entry<Vector2d, TreeSet<Animal>> entry : this.animals.entrySet()){
            Animal recievedAnimal = this.getNewAnimalAtPosition(entry.getKey());
            if (recievedAnimal != null){
                toAdd.add(recievedAnimal);
            }
        }
        //adding all the new animals to map
        for(Animal animal : toAdd){
            this.addAnimal(animal);
        }
    }

    public void placeTwoGrasses(){

        //We are getting all the free spaces in jungle and steppe
        List<Vector2d> jungle = this.jungleFreeSpaces();
        List<Vector2d> steppe = this.steppeFreeSpace();

        //if there is at least one free space in each of the zone, we choose random position out of free spaces
        if (jungle.size() != 0){
            Grass grassToPlace = new Grass(jungle.get(this.randomGenerator.nextInt(jungle.size())), this.plantEnergy);
            this.placeGrass(grassToPlace);
            System.out.println("W jungli: " + grassToPlace.getPosition());
        }

        if (steppe.size() != 0){
            Grass grassToPlace = new Grass(steppe.get(this.randomGenerator.nextInt(steppe.size())), this.plantEnergy);
            this.placeGrass(grassToPlace);
            System.out.println("Na stepie: " + grassToPlace.getPosition());
        }
    }

    //Here i can if the zones arent full so i can place there a grass
    public List<Vector2d> steppeFreeSpace(){
        List<Vector2d> result = new ArrayList<>();
        if (this.jungleRatio == 1) return result;

        //checking bottom part
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.leftLowerJungleVector.y; y++){
                if (this.objectsAt(new Vector2d(x, y)).size() == 0) result.add(new Vector2d(x ,y));
            }
        }

        //chekcing top part
        for(int x = 0; x < this.width; x++){
            for(int y = this.rightUpperJungleVector.y + 1; y < height; y++){
                if (this.objectsAt(new Vector2d(x, y)).size() == 0) result.add(new Vector2d(x ,y));
            }
        }

        //checking left part
        for(int x = 0; x < this.leftLowerJungleVector.x; x++){
            for(int y = this.leftLowerJungleVector.y; y <= this.rightUpperJungleVector.y; y++){
                if (this.objectsAt(new Vector2d(x, y)).size() == 0) result.add(new Vector2d(x ,y));
            }
        }

        //checking right part
        for(int x = this.rightUpperJungleVector.x + 1; x < width; x++){
            for(int y = this.leftLowerJungleVector.y; y <= this.rightUpperJungleVector.y; y++){
                if (this.objectsAt(new Vector2d(x, y)).size() == 0) result.add(new Vector2d(x ,y));
            }
        }
        return result;
    }

    //Getting all the free spaces in the jungle, if there is none, we return NULL
    public List<Vector2d> jungleFreeSpaces(){
        List<Vector2d> result = new ArrayList<Vector2d>();
        if(this.jungleRatio == 0) return result;
        for(int x = this.leftLowerJungleVector.x; x <= this.rightUpperJungleVector.x; x++) {
            for (int y = this.leftLowerJungleVector.y; y <= this.rightUpperJungleVector.y; y++) {
                if (this.objectsAt(new Vector2d(x, y)).size() == 0) result.add(new Vector2d(x ,y));
            }
        }
        return result;
    }

    //Calculates the jungle border
    public void calculateJungle(){
        int jungleWidth = (int)Math.ceil(this.width * this.jungleRatio);
        int jungleHeight = (int)Math.ceil(this.height * this.jungleRatio);
        int leftLowerx = (width-jungleWidth)/2;
        int leftLowery = (height-jungleHeight)/2;
        int rightUpperx = width-((int)Math.ceil(((double)(width-jungleWidth))/2))-1;
        int rightUppery = height-((int)Math.ceil(((double)(height-jungleHeight))/2))-1;
        this.leftLowerJungleVector = new Vector2d(leftLowerx, leftLowery);
        this.rightUpperJungleVector = new Vector2d(rightUpperx, rightUppery);
    }


}
