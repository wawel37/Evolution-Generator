package Visualization;

public interface IAnimalWatchObserver {

    void addFrame(AnimalWatchFrame frame);

    void deleteFrame(AnimalWatchFrame frame);

    void updateFrames();
}
