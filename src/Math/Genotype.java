package Math;

import java.util.*;

import MapElement.Animal;

public class Genotype {
    public static final int GENOTYPES_LENGTH = 32;
    public static final int NUMBER_OF_GENOTYPES = 8;
    public final int genes[] = new int[GENOTYPES_LENGTH];
    private final Random randomGenerator = new Random();

    //Random genes
    public Genotype(){
        for(int i = 0; i < GENOTYPES_LENGTH; i++){
            this.genes[i] = this.randomGenerator.nextInt(NUMBER_OF_GENOTYPES);
        }
        this.fixGenes();
    }

    //Genes inherited from parents
    public Genotype(Animal animal1, Animal animal2){
        int index1 = this.randomGenerator.nextInt(GENOTYPES_LENGTH - 2) + 1;
        int index2 = this.randomGenerator.nextInt(GENOTYPES_LENGTH - index1 - 1) + index1;
        for (int i = 0; i < index1; i++){
            this.genes[i] = animal1.genotype.genes[i];
        }
        for (int i = index1; i <= index2; i++){
            this.genes[i] = animal2.genotype.genes[i];
        }
        for (int i = index2 + 1; i < GENOTYPES_LENGTH; i++){
            this.genes[i] = animal1.genotype.genes[i];
        }
        this.fixGenes();
    }

    public int getRandomGene(){
        return this.genes[this.randomGenerator.nextInt(GENOTYPES_LENGTH)];
    }

    private void sortGenes(){
        Arrays.sort(this.genes);
    }

    private void fixGenes(){
        int genesCounter[] = this.calculateGenesCounter();
        for(int i = 0; i < NUMBER_OF_GENOTYPES; i++){
            if (genesCounter[i] == 0){
                this.mutation(genesCounter, i);
                genesCounter = this.calculateGenesCounter();
            }
        }
        this.sortGenes();
    }

    private void mutation(int genesCounter[], int missingGene){
        List<Integer> atLeast2 = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_GENOTYPES; i++){
            if (genesCounter[i] >= 2){
                atLeast2.add(i);
            }
        }

        int chosenGenotype = atLeast2.get(this.randomGenerator.nextInt(atLeast2.size()));

        for(int i = 0; i < GENOTYPES_LENGTH; i++){
            if (this.genes[i] == chosenGenotype){
                this.genes[i] = missingGene;
                break;
            }
        }
        this.sortGenes();
    }

    private int[] calculateGenesCounter(){
        int genesCounter[] = new int[NUMBER_OF_GENOTYPES];
        for(int i = 0; i < NUMBER_OF_GENOTYPES; i++){
            genesCounter[i] = 0;
        }
        for(int i = 0; i < GENOTYPES_LENGTH; i++){
            genesCounter[this.genes[i]]++;
        }
        return genesCounter;
    }

    @Override
    public String toString() {
        String result = "";
        for (Integer a : this.genes) {
            result += a;
        }
        return result;
    }

}
