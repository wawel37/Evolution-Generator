package MapElement;

import java.util.Comparator;

public class AnimalComparatorByCurrentEnergy implements Comparator<Animal> {

    @Override
    public int compare(Animal o, Animal t1){
        if(o.getCurrentEnergy() > t1.getCurrentEnergy()) return -1;
        else if (o.equals(t1)) return 0;
        else return 1;
    }
}
