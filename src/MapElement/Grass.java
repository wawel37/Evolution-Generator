package MapElement;
import Math.Vector2d;

public class Grass extends AbstractMapElement {
    public int plantEnergy;

    public Grass (Vector2d position, int plantEnergy){
        this.plantEnergy = plantEnergy;
        this.position = position;
    }

    @Override
    public String toString(){
        return "G";
    }
}
