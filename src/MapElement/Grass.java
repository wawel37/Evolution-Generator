package MapElement;
import Math.Vector2d;

public class Grass extends AbstractMapElement {
    public double plantEnergy;

    public Grass (Vector2d position, double plantEnergy){
        this.plantEnergy = plantEnergy;
        this.position = position;
    }

    public double getPlantEnergy(){
        return this.plantEnergy;
    }

    @Override
    public String toString(){
        return "G";
    }
}
