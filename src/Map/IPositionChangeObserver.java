package Map;
import Math.Vector2d;
import MapElement.Animal;

public interface IPositionChangeObserver {
    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

    void deleteGrass(Vector2d position);

    void deleteAnimal(Animal animal, Vector2d position);

    void calculateAverageAge(int age);
}
