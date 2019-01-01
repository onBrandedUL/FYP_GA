import java.util.Arrays;

public class GeneticAlgorithm{
    public static final int POPULATION_SIZE = 128;
    public static final int[] TARGET_CHROMOSOME = {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0};
    private double MUTATION_RATE = 0.05;
    private double MUTATION_TYPE = 0.5;
    public static final int NUMB_OF_ELITE_CHROMOSOMES = 4;
    public static final int TOURNAMENT_SELECTION_SIZE = 16;
    public int bitShiftSuccessCounter = 0,bitShiftFailureCounter = 0, bitFlipSuccessCounter = 0,bitFlipFailureCounter=0, bFMCounter=0, bSMCounter=0;
    
    public Population evolvePopulation(Population population){
        return mutatePopulation(crossoverPopulation(population));
    }
    
    private Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getChromosomes().length);
        for (int x = 0; x < NUMB_OF_ELITE_CHROMOSOMES; x++){
            crossoverPopulation.getChromosomes()[x] = population.getChromosomes()[x];
        }
        for (int x = NUMB_OF_ELITE_CHROMOSOMES; x < population.getChromosomes().length ; x++){
            Chromosome chromosome1 = selectTournamentPopulation(population).getChromosomes()[0];
            Chromosome chromosome2 = selectTournamentPopulation(population).getChromosomes()[0];
            crossoverPopulation.getChromosomes()[x] = crossoverChromosome(chromosome1,chromosome2);
        }
        return crossoverPopulation;
    }
    
    private Population mutatePopulation(Population population) {
        Population mutatePopulation = new Population(population.getChromosomes().length);
        for (int x = 0; x < NUMB_OF_ELITE_CHROMOSOMES; x++){
            mutatePopulation.getChromosomes()[x] = population.getChromosomes()[x];
        }
        for (int x = NUMB_OF_ELITE_CHROMOSOMES; x < population.getChromosomes().length ; x++){
            mutatePopulation.getChromosomes()[x] = mutateChromosome(population.getChromosomes()[x]);
        }
        
        return mutatePopulation;
    }
    
    private Chromosome crossoverChromosome(Chromosome chromosome1, Chromosome chromosome2){
        int crossoverPoint = (int)(Math.random()*TARGET_CHROMOSOME.length);
        //System.out.println("Crossover Point: " + crossoverPoint);
        Chromosome crossoverChromosome = new Chromosome(TARGET_CHROMOSOME.length);
        for ( int x = 0; x < chromosome1.getGenes().length; x++)
        {
            if (x < crossoverPoint)
            {
                crossoverChromosome.getGenes()[x] = chromosome1.getGenes()[x];
            }
            else
            {
                crossoverChromosome.getGenes()[x] = chromosome2.getGenes()[x];
            }
        }
        return crossoverChromosome;
        
    }
    
    private Chromosome mutateChromosome(Chromosome chromosome)
    {
        Chromosome mutateChromosome = chromosome;
        float parentFitness = chromosome.getFitness();
        float offspringFitness;
        int bitShiftMutationHolderO,bitShiftMutationHolderM;
        int swapPos = chromosome.getGenes().length + 1;
        boolean mutateOccured = false, bitFlipMutate = false, bitShiftMutate = false;
        //System.out.println("Original Chromosome: " + Arrays.toString(chromosome.getGenes()));
        for(int x = 0; x < chromosome.getGenes().length; x++)
        {
            if(Math.random() < MUTATION_RATE)
            {
                mutateOccured = true;
                if(Math.random() < MUTATION_TYPE)
                {
                    bitFlipMutate = true;
                    //System.out.print("BitFlip Mutation Occured: ");
                    if (chromosome.getGenes()[x] == 1)
                    {
                        mutateChromosome.getGenes()[x] = 0;
                        //System.out.print("Mutated Gene: " + mutateChromosome.getGenes()[x]);
                        //System.out.print(" Original Gene: " + chromosome.getGenes()[x]);
                    }
                    else if (chromosome.getGenes()[x] == 0)
                    {
                        mutateChromosome.getGenes()[x] = 1;
                        //System.out.print("Mutated Gene: " + mutateChromosome.getGenes()[x]);
                        //System.out.print(" Original Gene: " + chromosome.getGenes()[x]);
                    }
                    
                }
                else
                {
                    bitShiftMutate = true;
                    //System.out.print("BitShift Mutation Occured:");
                    swapPos = (int)(Math.random()*chromosome.getGenes().length);
                    
                    while (chromosome.getGenes()[swapPos] == chromosome.getGenes()[x])
                    {
                        //System.out.print(" Swap Position: " + swapPos);
                        swapPos = (int)(Math.random()*chromosome.getGenes().length);
                    }
                    
                    
                    bitShiftMutationHolderM = chromosome.getGenes()[swapPos];
                    bitShiftMutationHolderO = chromosome.getGenes()[x];
                    //System.out.print(" Original Gene: " + chromosome.getGenes()[swapPos] + " Position: " + swapPos);
                    //System.out.print(" Mutated Gene: " + chromosome.getGenes()[x] + " Position: " + x);
                    
                    mutateChromosome.getGenes()[x] = bitShiftMutationHolderM;
                    mutateChromosome.getGenes()[swapPos] = bitShiftMutationHolderO;
                    
                }
                System.out.println("");
                
            }
            
            else
            {
                if (x != swapPos)
                {
                mutateChromosome.getGenes()[x] = chromosome.getGenes()[x];
                }
                else
                {
                    //System.out.println("Didn't allow gene to be copied from original Chromsome at position " + x);
                }
            }
            offspringFitness = mutateChromosome.getFitness();
            augementMutationRate(mutateOccured,bitFlipMutate,bitShiftMutate,parentFitness,offspringFitness);
            mutateOccured = false;
            bitFlipMutate = false;
            bitShiftMutate = false;
            }
        //System.out.println("Parent Fitness: " + parentFitness + " Offspring Fitness: " + offspringFitness);
        
        //System.out.println("Mutated Chromosome: " + Arrays.toString(mutateChromosome.getGenes()));
        //System.out.println("------------------------------------------------------");
        return mutateChromosome;
        
    }
    
    private void augementMutationRate(boolean mO, boolean bFM, boolean bSM, float pFit, float oFit)
    {
        if(mO == true)
        {
            if (bFM == true)
            {
                bFMCounter++;
                if (pFit - oFit < 0.0)
                {
                    MUTATION_TYPE += 0.01;
                    bitFlipSuccessCounter++;
                }
                else if (pFit - oFit > 0.0)
                {
                    MUTATION_TYPE -= 0.01;
                    bitFlipFailureCounter++;
                }
                else
                {
                    MUTATION_TYPE = MUTATION_TYPE;
                }
                
            }
            else if (bSM ==  true)
            {
                bSMCounter++;
                if (pFit - oFit < 0.0)
                {
                    MUTATION_TYPE -= 0.01;
                    bitShiftSuccessCounter++;
                }
                else if (pFit - oFit > 0.0)
                {
                    MUTATION_TYPE += 0.01;
                    bitShiftFailureCounter++;
                }
                else
                {
                    MUTATION_TYPE = MUTATION_TYPE;
                }
            }
            //System.out.println("Probability of BitFlip Mutation is: " + MUTATION_TYPE + "Probability of BitShift Mutation is: " + (1 - MUTATION_TYPE));
        }
    }
    
    
    
    private Population selectTournamentPopulation(Population population)
    {
        Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE);
        for (int x = 0; x < TOURNAMENT_SELECTION_SIZE; x++)
        {
            tournamentPopulation.getChromosomes()[x] = population.getChromosomes()[(int)Math.random()*population.getChromosomes().length];
        }
        tournamentPopulation.sortChromosomeByFitness();
        return tournamentPopulation;
    }
    
    
    public void printStatistics()
    {
        System.out.println("--------------------------------------------------");
        System.out.println("Mutation statistics");
        System.out.println("Probability of BitFlip Mutation is: " + MUTATION_TYPE + "Probability of BitShift Mutation is: " + (1 - MUTATION_TYPE));
        System.out.println("Bit Shift Mutation | Total Occurances: " + bSMCounter + " Successes: " + bitShiftSuccessCounter + " Failures: " + bitShiftFailureCounter);
        System.out.println("Bit Flip Mutation  | Total Occurances: " + bFMCounter + " Successes: " + bitFlipSuccessCounter +  " Failures: " + bitFlipFailureCounter);
        System.out.println("--------------------------------------------------");
    }
    
}
