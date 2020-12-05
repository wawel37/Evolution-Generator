package Map;
import MapElement.*;
import Math.Vector2d;

import java.util.List;

public interface IWorldMap {

    boolean placeAnimal(Animal animal);

    boolean placeGrass(Grass grass);

    boolean isOccupiedByAnimal(Vector2d position);


    List<Object> objectsAt(Vector2d position);

    //gives u a correct position considering map borders (width and height)
    Vector2d getPositionAfterMove(Vector2d position);
}
