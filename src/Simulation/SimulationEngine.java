package Simulation;


import Map.GameMap;

public class SimulationEngine implements ISimulation {
    private final int WIDTH;
    private final int HEIGHT;
    private final double jungleRatio;
    private final double plantEnergy;
    private final double startEnergy;
    private final double moveEnergy;
    private final GameMap map;

    public SimulationEngine(int width,
                            int height,
                            double jungleRatio,
                            double plantEnergy,
                            double startEnergy,
                            double moveEnergy,
                            int initialAnimalCounter){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.jungleRatio = jungleRatio;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.map = new GameMap(this.WIDTH, this.HEIGHT, this.jungleRatio, this.plantEnergy, this.startEnergy, this.moveEnergy);
        this.map.generateRandomAnimals(initialAnimalCounter);
        this.map.placeTwoGrasses();
    }

    public void run(){
        this.map.moveAllAnimals();
        this.map.eatAllGrasses();
        this.map.copulateAllAnimals();
        this.map.placeTwoGrasses();
    }

    @Override
    public String toString(){
        return this.map.toString();
    }
}
