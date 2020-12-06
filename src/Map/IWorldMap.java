package Map;
import MapElement.*;
import Math.Vector2d;

import java.util.List;

public interface IWorldMap {

    // Placing animal if the position is not occupied
    boolean placeAnimal(Animal animal);

    //places grass if the position is not occupied by anything
    boolean placeGrass(Grass grass);

    //checks if the position is occupied by animal
    boolean isOccupiedByAnimal(Vector2d position);

    //returns list of objects at given position,
    List<Object> objectsAt(Vector2d position);

    //gives u a correct position considering map borders (width and height)
    Vector2d getPositionAfterMove(Vector2d position);
}
