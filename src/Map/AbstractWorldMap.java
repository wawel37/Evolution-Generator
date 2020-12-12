package Map;
import Enums.Orientation;
import MapElement.*;
import Math.*;



import java.util.*;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final int width;
    protected final int height;
    protected final double jungleRatio;
    protected Vector2d leftLowerJungleVector;
    protected Vector2d rightUpperJungleVector;
    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Random randomGenerator = new Random();

    //these variables will be injected from Graphical InterFace or simulation engine
    protected double plantEnergy;
    protected double startEnergy;
    protected double moveEnergy;

    //Statistic variables

    //Main stats
    protected int animalCounter = 0;
    protected int grassCounter = 0;
    protected int dominatingGenotype = -1;
    protected double averageEnergy = 0;
    protected double averageAge = 0;
    protected double averageDescendants = 0;

    //Helping stats
    protected int ageSum = 0;
    protected int deadAnimalCounter = 0;
    protected int ageCounter = 0;


    public AbstractWorldMap(int width,
                            int height,
                            double jungleRatio,
                            double plantEnergy,
                            double startEnergy,
                            double moveEnergy){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
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
            this.grassCounter++;
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
        this.grassCounter--;
        this.grasses.remove(position);
    }

    @Override
    public void deleteAnimal(Animal animal, Vector2d position){
        this.animalCounter--;
        this.animals.get(position).remove(animal);
        if(this.animals.get(position).isEmpty()){
            this.animals.remove(position);
        }
    }

    //Putting animal on the map without checking if the position is occupied
    public void addAnimal(Animal animal){
        if (this.animals.get(animal.getPosition()) == null){
            this.animals.put(animal.getPosition(), new LinkedList<Animal>());
        }
        this.animals.get(animal.getPosition()).add(animal);
        this.animalCounter++;
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

        //in getStringestAnimalsAtPosition, animals at given position are being sorted by their currentEnergy
        List<Animal> strongestAnimals = this.getStrongestAnimalsAtPosition(position);
        Animal strongestAnimal1 = null;
        Animal strongestAnimal2 = null;

        if (strongestAnimals.size() < 2){
            strongestAnimal1 = this.animals.get(position).get(0);
            strongestAnimal2 = this.animals.get(position).get(1);
        }else {
            int index1 = this.randomGenerator.nextInt(strongestAnimals.size());
            int index2;
            do {
                index2 = this.randomGenerator.nextInt(strongestAnimals.size());
            } while (index1 == index2);

            strongestAnimal1 = strongestAnimals.get(index1);
            strongestAnimal2 = strongestAnimals.get(index2);
        }

        //If the strongest animals dont have enough energy to copulate
        if (strongestAnimal1.getCurrentEnergy() < this.startEnergy/2 || strongestAnimal2.getCurrentEnergy() < this.startEnergy/2){
            return null;
        }

        double energyForNewAnimal = 0;
        energyForNewAnimal += strongestAnimal1.copulating();
        energyForNewAnimal += strongestAnimal2.copulating();

        Vector2d positionForNewAnimal =  this.getPositionForNewAnimal(position);
        Orientation orientationForNewAnimal = Orientation.values()[this.randomGenerator.nextInt(8)];

        Animal newAnimal = new Animal (positionForNewAnimal,
                                       this.startEnergy,
                                       energyForNewAnimal,
                                       this.moveEnergy,
                                       new Genotype(strongestAnimal1, strongestAnimal2),
                               this,
                                  this,
                                       orientationForNewAnimal);

        return newAnimal;
    }

    /*
        Returns a list of strongest animals at the specific position
        REMEMBER TO CHECK IF THE POSITION IS OCCUPIED BY ANY ANIMAL
        RETURNS A NULL IF THE POSITION IS NOT OCCUPIED
    */
    public List<Animal> getStrongestAnimalsAtPosition(Vector2d position){
        List<Animal> myAnimals = this.animals.get(position);
        if (myAnimals == null) return null;
        Collections.sort(myAnimals, new AnimalComparatorByCurrentEnergy());
        List<Animal> strongestAnimalsAtPosition = new ArrayList<>();
        double maxEnergy = myAnimals.get(0).getCurrentEnergy();
        Iterator<Animal> iterator = myAnimals.iterator();

        while(iterator.hasNext()){
            Animal myAnimal = iterator.next();
            if(myAnimal.getCurrentEnergy() == maxEnergy) {
                strongestAnimalsAtPosition.add(myAnimal);
            }
        }
        return strongestAnimalsAtPosition;
    }

    public Vector2d getPositionForNewAnimal(Vector2d position){
        List<Vector2d> freeSpaces = new LinkedList<>();
        for(int i = 0; i < 8; i++){
            Vector2d myVector = this.getPositionAfterMove(position.add(Orientation.values()[i].toUnitVector()));
            if (!this.isOccupiedByAnimal(myVector)){
                freeSpaces.add(myVector);
            }
        }
        if (freeSpaces.size() != 0){
            return freeSpaces.get(this.randomGenerator.nextInt(freeSpaces.size()));
        }else{
            return this.getPositionAfterMove(position.add(Orientation.values()[this.randomGenerator.nextInt(8)].toUnitVector()));
        }
    }


    //Getter for animals
    public Map<Vector2d, List<Animal>> getAnimals(){
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

    public List<Animal> getAnimalsAsList(){
        List<Animal> result = new LinkedList<>();

        for(Map.Entry<Vector2d, List<Animal>> entry : this.animals.entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            while(iterator.hasNext()){
                result.add(iterator.next());
            }
        }
        return result;
    }

    //Moving all the animals at the same time
    public void moveAllAnimals(){
        List<Animal> toMove = this.getAnimalsAsList();
        /*
            We move each one of them, if it doesn't have enough energy to move,
             it simply dies, that's how life goes ¯\_(ツ)_/¯
        */
        for(Animal animal : toMove){
            animal.move();
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
        List<Animal> toAdd = new LinkedList<Animal>();

        //Getting all new animals that were created thought the copulation at very single point where was it possible
        for(Map.Entry<Vector2d, List<Animal>> entry : this.animals.entrySet()){
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
        }

        if (steppe.size() != 0){
            Grass grassToPlace = new Grass(steppe.get(this.randomGenerator.nextInt(steppe.size())), this.plantEnergy);
            this.placeGrass(grassToPlace);
        }
    }

    //We are generating random animals on the map
    public void generateRandomAnimals(int count){
        for(int i = 0; i < count; i++){
            int x, y;
            Vector2d position;
            do{
                x = randomGenerator.nextInt(this.width);
                y = randomGenerator.nextInt(this.height);
                position = new Vector2d(x,y);
            }while(isOccupiedByAnimal(position));
            Orientation newOrientation = Orientation.values()[this.randomGenerator.nextInt(8)];
            this.placeAnimal(new Animal(position,
                                        this.startEnergy,
                                        this.startEnergy,
                                        this.moveEnergy,
                                        new Genotype(),
                                        this,
                                        this,
                                        newOrientation));
        }
    }

    //Here i can if the zones arent full so i can place there a grass
    public List<Vector2d> steppeFreeSpace(){
        List<Vector2d> result = new LinkedList<>();
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
        List<Vector2d> result = new LinkedList<Vector2d>();
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

    @Override
    public String toString(){
        String result = "";
        for (Map.Entry<Vector2d, List<Animal>> entry : this.animals.entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            result += ("Pozycje animalow na pozycji " + entry.getKey() + ":" + "\n");
            while(iterator.hasNext()){
                Animal myAnimal = iterator.next();
                result += (myAnimal.getPosition() + " " + myAnimal.id + " ");
                result +=("Energy: " + myAnimal.getCurrentEnergy() + "\n");
            }
        }

        for (Map.Entry<Vector2d, Grass> entry : this.grasses.entrySet()){
            result += ("Pozycje grassow na pozycji" + entry.getKey() + ":" + " ");
            result += (entry.getValue().getPosition() + "\n");
        }
        result += "dominating genotype: " + this.dominatingGenotype + "\n";
        result += "average Age: " + this.averageAge + "\n";
        result += "average energy: " + this.averageEnergy + "\n";
        result += "average descendants: " + this.averageDescendants + "\n";
        result += "Animal counter: " + this.animalCounter + "\n";
        result += "Grass counter: " + this.grassCounter + "\n";

        return result;
    }

    public Vector2d getLeftLowerJungleVector(){
        return this.leftLowerJungleVector;
    }

    public Vector2d getRightUpperJungleVector(){
        return this.rightUpperJungleVector;
    }

    public void calculateAverageAge(int age){
        this.deadAnimalCounter++;
        this.ageSum += age;
        this.averageAge = (double)this.ageSum/(double)this.deadAnimalCounter;
    }

    private void calculateAverageEnergy(){
        List<Animal> myAnimals = this.getAnimalsAsList();
        Iterator<Animal> iterator = myAnimals.iterator();
        double energySum = 0;
        while(iterator.hasNext()){
            energySum += iterator.next().getCurrentEnergy();
        }
        if (this.animalCounter == 0){
            this.averageEnergy = 0;
        }else {
            this.averageEnergy = energySum / (double) this.animalCounter;
        }
    }

    private void calculateAverageDescendants(){
        List<Animal> myAnimals = this.getAnimalsAsList();
        Iterator<Animal> iterator = myAnimals.iterator();
        int descendantsSum = 0;
        while(iterator.hasNext()){
            descendantsSum += iterator.next().getChildrenCounter();
        }
        if (this.animalCounter == 0){
            this.averageEnergy = 0;
        }else {
            this.averageDescendants = (double) descendantsSum / (double) this.animalCounter;
        }
    }

    private void calculateDominatingGenotype(){
        Integer[] genotypeCounter = new Integer[8];
        for (int i = 0; i < 8; i++){
            genotypeCounter[i] = 0;
        }
        List<Animal> myAnimals = this.getAnimalsAsList();
        Iterator<Animal> iterator = myAnimals.iterator();
        while(iterator.hasNext()){
            Animal myAnimal = iterator.next();
            for(int i = 0; i < 32; i++){
                genotypeCounter[myAnimal.genotype.genes[i]]++;
            }
        }
        int largest = 0;
        int largestvalue = 0;
        for(int i = 0; i < 8; i++){
            if (genotypeCounter[i] > largestvalue){
                largestvalue = genotypeCounter[i];
                largest = i;
            }
        }
        this.dominatingGenotype = largest;
    }

    public void calculateStatistics(){
        this.calculateAverageDescendants();
        this.calculateAverageEnergy();
        this.calculateDominatingGenotype();
    }

    /*
        GETTERS FOR STATISTICS
     */

    public int getAnimalCounter(){ return this.animalCounter; }
    public int getGrassCounter(){ return this.grassCounter; }
    public int getDominatingGenotype(){ return this.dominatingGenotype; }
    public double getAverageEnergy(){ return this.averageEnergy; }
    public double getAverageAge(){ return this.averageAge; }
    public double getAverageDescendants(){ return this.averageDescendants; }
    public int getAgeCounter(){ return this.ageCounter; }

    public void nextAge(){ this.ageCounter++; }


}
