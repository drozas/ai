package es.urjc.etsii.ia.genalg;
import java.util.Random;
import java.lang.Comparable;

import es.urjc.etsii.ia.config.Configuration;
import es.urjc.etsii.ia.data.DataLoader;

/**
 * Class Chromosome
 */
public class Chromosome implements Comparable{

	//
	// Fields
	//
	

  public double fitness;
  public int cost;
  public int[] gens;
  private int gensNumber;
  private Random r = new Random();
  private Configuration config;
  private DataLoader data;
  public boolean is_mutant=false;
  public boolean is_elite = false;
  
  public double selection_prob;
  
	//
	// Constructors
	//
	public Chromosome()
	{
		config = Configuration.getConfiguration("./config/params.xml");
		data = DataLoader.getDataLoader(config.getDataPath());
		this.gensNumber = this.data.getDistancesMatrix().length;
		this.gens = new int[this.gensNumber];
		this.cost = Integer.MAX_VALUE;
		
		//Initialize the array with negative numbers == empty position
		for (int i=0; i<this.gensNumber; i++)
			gens[i]=-1;	
	}
	

	public void fillRandomly()
	{
		//We try to fill a random position with a number from 0 til gensNumber. If this position is already filled, then
		//we try to fill a new one with the same number
		for(int i=0; i<this.gensNumber; i++)
		{
			int position=r.nextInt(this.gensNumber);
			if (this.gens[position] < 0) 
				this.gens[position]=i;
			else
				i--;
		}
		this.calculateCost();
		this.calculateFitness();
	}
  
	//
	// Other methods
	//

	/**
	 */
	public void calculateCost() {
		this.cost = 0;
		for (int i = 0; i < this.gensNumber; i++)
			for (int j = 0; j < this.gensNumber; j++)
				this.cost += this.data.getDistancesMatrix()[i][j]
						* this.data.getFlowMatrix()[gens[i]][gens[j]];
	}


  public void calculateFitness(  )
  {
	  if (this.config.getFitnessType()>0)
		  this.fitness = 1e+8 - this.cost;
	  else
		  this.fitness = 1.0/this.cost;
	  
	}


  public boolean mutate()
  {

	  if (r.nextFloat()<= this.config.getMutationProbability())
	  {
		  	int a,b,aux;
		  	
		  	
			do
			{
				a = r.nextInt(this.gensNumber);
				b = r.nextInt(this.gensNumber);
				
			}while(a==b);
			
			aux = this.gens[a];
			this.gens[a] = this.gens[b];
			this.gens[b] = aux;
			
			return true;
			
	  }else{
		  return false;
	  }
	
  }


  public Chromosome Cruce(Chromosome mother)
  {
	  Chromosome son=new Chromosome();
	  
	  //(this.gensNumber-2)+1 this is for being sure that the cut-off is gonna be between 1 and number of genes-1
	  int cut_off = this.r.nextInt(this.gensNumber-2)+1;
	  
	  //Copy all the gens from the father til the cut-off point
	  for(int i=0; i<cut_off;i++)
		  son.gens[i] = this.gens[i];
	  
	  //Fullfill with the mother the rest
	  int position=cut_off;
	  for(int i=0; i<this.gensNumber; i++)
	  {
		  boolean nomatches=true;
		  
		  //We have only to check the fathers part
		  for(int j=0; j<cut_off; j++)
		  {
			  
			  if (son.gens[j]==mother.gens[i])
				  nomatches = false;
		  }
		  
		  if (nomatches)
			  son.gens[position++] = mother.gens[i];
		  
	  }
	  
	  son.calculateCost();
	  son.calculateFitness();
	  son.is_mutant = son.mutate();
	  
	  return son;
	
  }			
  
  public String toString()
  {

	  String res =  "\nGenes ";
	  if (this.is_mutant)
		  res+="(I am a mutant) ";
  
	  for(int i=0; i<this.gensNumber; i++)
	  {
		  res+=this.gens[i] + " | ";
	  }
	  
	  res+= "\nCost: " + this.cost;
	  res+= "\nFitness: " + this.fitness;
	  
	  return res;
	  
  }
  
  public int compareTo(Object anotherChromosome) throws ClassCastException {
		if (!(anotherChromosome instanceof Chromosome))
			throw new ClassCastException("A Chromosome object expected.");

		if (this.fitness>((Chromosome)anotherChromosome).fitness) 
			return -1;
		else if (this.fitness<((Chromosome)anotherChromosome).fitness)
			return 1;
		else
			return 0;

	}



}
