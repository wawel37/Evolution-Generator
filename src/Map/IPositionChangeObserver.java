package Map;
import Math.Vector2d;
import MapElement.Animal;

public interface IPositionChangeObserver {
    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
