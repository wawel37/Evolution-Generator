package Map;
import MapElement.*;
import Math.Vector2d;



import java.util.*;
import java.util.Random;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final int width;
    protected final int height;
    protected final double jungleRatio;
    protected Vector2d leftLowerJungleVector;
    protected Vector2d rightUpperJungleVector;
    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    protected Map<Vector2d, Set<Animal>> animals = new HashMap<>();
    protected Random randomGenerator = new Random();

    //these variables will be injected from Graphical InterFace or simulation engine
    protected int plantEnergy;

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

        Vector2d position = animal.getPosition();
        if (this.animals.get(position) == null){
            this.animals.put(position, new TreeSet<Animal>(new AnimalComparatorByCurrentEnergy()));
        }
        this.animals.get(position).add(animal);
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
        if(this.animals.get(position) != null){
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
        this.animals.get(oldPosition).remove(animal);
        if(this.animals.get(oldPosition).isEmpty()){
            this.animals.remove(oldPosition);
        }
        if (this.animals.get(newPosition) == null){
            this.animals.put(newPosition, new TreeSet<Animal>(new AnimalComparatorByCurrentEnergy()));
        }
        this.animals.get(newPosition).add(animal);
    }

    public Map<Vector2d, Set<Animal>> getAnimals(){
        return this.animals;
    }


    public void moveAllAnimals(){
        List<Animal> toMove = new ArrayList<>();
        for(Map.Entry<Vector2d, Set<Animal>> entry : this.animals.entrySet()){
            Iterator<Animal> iterator = entry.getValue().iterator();
            while(iterator.hasNext()){
                toMove.add(iterator.next());
            }
        }
        Iterator<Animal> iterator = toMove.iterator();
        while(iterator.hasNext()){
            iterator.next().move();
        }
    }

    public void placeTwoGrasses(){
        List<Vector2d> jungle = this.jungleFreeSpaces();
        List<Vector2d> steppe = this.steppeFreeSpace();
        if (jungle.size() != 0){
            this.placeGrass(new Grass(jungle.get(this.randomGenerator.nextInt(jungle.size())), this.plantEnergy));
        }
        if (steppe.size() != 0){
            this.placeGrass(new Grass(steppe.get(this.randomGenerator.nextInt(steppe.size())), this.plantEnergy));
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
        System.out.println("Leftdown: " + this.leftLowerJungleVector + " Rightup: " + this.rightUpperJungleVector);
        System.out.println(Math.ceil((height-jungleHeight)/2));
    }


}
