import java.util.Arrays;

public class GADriver{
    public static void main(String[] args) {
        Population population = new Population(GeneticAlgorithm.POPULATION_SIZE).initializePopulation();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        System.out.println("--------------------------------------------------");
        System.out.println("Generation # 0" + " | Fittest Chromosome fitness: " + population.getChromosomes()[0].getFitness());
        printPopulation(population, "Target Chromosome: " + Arrays.toString(GeneticAlgorithm.TARGET_CHROMOSOME));
        int generationNumber = 0;
        while (population.getChromosomes()[0].getFitness() < GeneticAlgorithm.TARGET_CHROMOSOME.length){
            generationNumber++;
            System.out.println("--------------------------------------------------");
            population = geneticAlgorithm.evolvePopulation(population);
            population.sortChromosomeByFitness();
            System.out.println("Generation # " + generationNumber + " | Fittest Chromosome fitness: " + population.getChromosomes()[0].getFitness());
            printPopulation(population, "Target Chromosome: " + Arrays.toString(GeneticAlgorithm.TARGET_CHROMOSOME));
            if (population.getChromosomes()[0].getFitness() < GeneticAlgorithm.TARGET_CHROMOSOME.length){
                
                population = population.swapOutUnfitChromosomes();
                System.out.println("After swapping Out Unfit Chromosomes");
                printPopulation(population, "Target Chromosome: " + Arrays.toString(GeneticAlgorithm.TARGET_CHROMOSOME));
            }
            
        }
        geneticAlgorithm.printStatistics();
    }
    public static void printPopulation(Population population, String heading){
        System.out.println(heading);
        System.out.println("--------------------------------------------------");
        for (int x = 0; x < population.getChromosomes().length; x++) {
            System.out.println("Chromosome #" + x + " : " + Arrays.toString(population.getChromosomes()[x].getGenes()) + " | Fitness: " + population.getChromosomes()[x].getFitness() + " | Diversity: " + population.getDiversity(population.getChromosomes()[x]));
        }
    }
}
