package Map;

public class GameMap extends AbstractWorldMap{

    public GameMap(int width,
                   int height,
                   double jungleRatio,
                   double plantEnergy,
                   double startEnergy,
                   double moveEnergy){
        super(width, height, jungleRatio, plantEnergy, startEnergy, moveEnergy);
    }
}
