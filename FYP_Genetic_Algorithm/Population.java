import java.util.*;

public class Population{
    private Chromosome[] chromosomes;
    
    public Population(int length){
        chromosomes = new Chromosome[length];
    }
    public Population initializePopulation(){
        for (int x = 0; x < chromosomes.length; x++){
            chromosomes[x] = new Chromosome(GeneticAlgorithm.TARGET_CHROMOSOME.length).initializeChromosome();
        }
        sortChromosomeByFitness();
        return this;
    }
    
    public Population swapOutUnfitChromosomes(){
        boolean isChromoUnique = false;
        int counter = 0;
        Chromosome tempChromosome = new Chromosome(GeneticAlgorithm.TARGET_CHROMOSOME.length);
        for (int x = (chromosomes.length/2) -1; x < chromosomes.length; x++){
            while (isChromoUnique == false)
            {
                //System.out.println("Testing While Loop");
                tempChromosome = new Chromosome(GeneticAlgorithm.TARGET_CHROMOSOME.length).initializeChromosome();
                for (int y = 0; y < chromosomes.length; y++)
                {
                    if (compareChromosomes(chromosomes[y],tempChromosome) == true)
                    {
                       //System.out.println("Checking if Chromosome is Unique");
                        counter++;
                    }
                }
                if (counter >= chromosomes.length-1 )
                {
                    //System.out.println("Chromosome is Unique");
                    isChromoUnique = true;
                    counter = 0;
                }
                counter = 0;
            }
            chromosomes[x] = tempChromosome;
            isChromoUnique = false;
        }
        sortChromosomeByFitness();
        return this;
        
    }
    
    public Chromosome[] getChromosomes(){
        return chromosomes;
    }
    
    
    public int getDiversity(Chromosome testChromosome){
        int chromosomeDiv = 0;
        //System.out.print("This Chromosome: " + chromosome);
        //if(chromosome.getGenes()[4] == chromosomes[0].getGenes()[4]) System.out.println(" That Chromosome: " + chromosomes[index].getGenes()[4]);
        //System.out.print("Chromosome A: " + chromosome);
        for (int x = 0; x < chromosomes.length; x++)
        {
                //System.out.println("This Chromosome: " + chromosomes[x] + "Reference Chromosome: " + chromosomes[index]);
                if (compareChromosomes(chromosomes[x],testChromosome) == true)
                {
                    
                    chromosomeDiv++;
                }
        }
        
        return chromosomeDiv;
    }
    
    
    public boolean compareChromosomes(Chromosome cA, Chromosome cB)
    {
        //System.out.println("This Chromosome: " + cA);
        boolean uNique = false;
        int charsTheSame = 0;
        //System.out.print(" Chromosome B: " + cB);
        for (int x = 0; x < GeneticAlgorithm.TARGET_CHROMOSOME.length; x++)
        {
            if (cA.getGenes()[x] != cB.getGenes()[x])
            {
                charsTheSame++;
            }
        }
        
        if (charsTheSame > 0)
        {
            uNique = true;
        }
        
        //System.out.println("Unique? " + uNique);
        return uNique;
    
    }
    public void sortChromosomeByFitness(){
        Comparator<Chromosome> chromosomeComparator = new Comparator<Chromosome>() {
            public int compare(Chromosome chromosome1, Chromosome chromosome2) {
                int flag = 0;
                
                if (chromosome1.getFitness() + getDiversity(chromosome1) > chromosome2.getFitness() + getDiversity(chromosome2))
                {
                    flag = -1;
                }
                else if (chromosome1.getFitness() + getDiversity(chromosome1) < chromosome2.getFitness() + getDiversity(chromosome2))
                {
                    flag = 1;
                }
                return flag;
                
                
            }};
        Arrays.sort(chromosomes,chromosomeComparator);
    }
    

    
}
