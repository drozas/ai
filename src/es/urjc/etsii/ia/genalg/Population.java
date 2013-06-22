package es.urjc.etsii.ia.genalg;

import java.util.Random;
import java.util.Arrays;



import es.urjc.etsii.ia.config.Configuration;
import es.urjc.etsii.ia.data.DataLoader;
import es.urjc.etsii.ia.data.DataSaver;

/**
 * Class Population
 */
public class Population {

	//
	// Fields
	//

  public Chromosome[] chromosomes;
  public int generation = 1;
  public int bestCost = Integer.MAX_VALUE;
  public int bestChromosome = 0;
  private Configuration config; 
  private DataLoader data; 
  private DataSaver log;
  private int populationSize;
  private int gensNumber;
  public int avg = 0;
  private int eliteMembers;
  
  private Random r = new Random();
  
	//
	// Constructors
	//
	public Population () { 
		 config = Configuration.getConfiguration("./config/params.xml");
		 data = DataLoader.getDataLoader(config.getDataPath());
		 log = DataSaver.getDataSaver(config.getLogPath() + "log_" + config.getSelectionPolicy() + "_" + config.getFitnessType() + "_" + config.getPopulationSize() + "_" + config.getMaxGenerations() + ".csv");
		 
		 this.gensNumber = this.data.getDistancesMatrix().length;
		 this.populationSize = this.config.getPopulationSize();
		 this.chromosomes = new Chromosome[this.populationSize];
		 this.eliteMembers = (int)(this.populationSize * config.getElitism());
		 
		
		 
		
	};
  

	/**
	 */
  public void initializate(  )
  {

	  for (int i=0;i<this.populationSize;i++)
		 {
			 this.chromosomes[i] = new Chromosome();
			 this.chromosomes[i].fillRandomly();
		 } 
	  
	  this.caculateAvgMinCost();
	}


	/**
	 */
  public boolean stopCriteria(  )
  {
	  return !(this.chromosomes[this.bestChromosome].cost<=config.getBestValue());

  }



  
  public Population get_next_generation()
  {
	  this.markElits();
	  this.calculate_selection_probability();

	  
	  
	 
	  
	  /*Idea: 	- We take a random number from 0 til populationSize: select a chromosome
	  			- We calculate a random probability
	  			- If selection_probability of this member is bigger or equal than this number, 
	  			then it will be selected for reproduction
	  */
	  
	  
	  Population new_population = new Population();
	  

	  for(int i=0; i<this.populationSize-this.eliteMembers; i++)
	  {
		  int rnd_pos;
		  double rnd_prob;
		  do
		  {
			  rnd_pos = r.nextInt(this.populationSize);
			  rnd_prob = r.nextDouble();
			 
		  }while(this.chromosomes[rnd_pos].selection_prob>=rnd_prob);
		  
		  int parent1 = rnd_pos;
		  
		  do
		  {
			  rnd_pos = r.nextInt(this.populationSize);
			  rnd_prob = r.nextDouble();
			 
		  }while(this.chromosomes[rnd_pos].selection_prob>=rnd_prob);
		  
		  //System.out.println("chromosome " + parent1 + " and "  + rnd_pos + " are gonna have a child");
		  new_population.chromosomes[i] = this.chromosomes[parent1].Cruce(this.chromosomes[rnd_pos]);
		
		 
	  }
	  
	  //We filled the rest of the array with elite members
	  for(int i=this.populationSize-this.eliteMembers; i<this.populationSize; i++)
	  {
		  new_population.chromosomes[i] = this.chromosomes[i-this.populationSize+this.eliteMembers];
		  //and reset mutant and elitism parameters
		  new_population.chromosomes[i].is_elite = false;
		  new_population.chromosomes[i].is_mutant = false;
	  }

	  
	  new_population.caculateAvgMinCost();
	  return new_population;
  }
  
  private void calculate_selection_probability()
  {
	  if (this.config.getSelectionPolicy().equals("roulette"))
	  {
		  double sumatory = 0.0;
		  for(int i = 0; i<this.populationSize; i++)
			  sumatory += this.chromosomes[i].fitness;
		  
		  for(int i = 0; i<this.populationSize; i++)
			  this.chromosomes[i].selection_prob = this.chromosomes[i].fitness / sumatory;
		  
	  }else if (this.config.getSelectionPolicy().equals("boltzmann"))
	  {
		  double sumatory = 0.0;
		  for(int i = 0; i<this.populationSize; i++)
			  sumatory += java.lang.Math.exp(this.chromosomes[i].fitness/config.getTemperature());	  
		  
		  for(int i = 0; i<this.populationSize; i++)
			  this.chromosomes[i].selection_prob = java.lang.Math.exp(this.chromosomes[i].fitness/config.getTemperature()) / sumatory;
		  
	  }
  }
  private void markElits()
  {
	  Arrays.sort(this.chromosomes);
	  for(int i=0; i<this.eliteMembers; i++)
		  this.chromosomes[i].is_elite = true;
	  
		  
  }
  
  private void caculateAvgMinCost()
  {
	  double aux = 0.0;
	  for (int i=0; i<this.populationSize; i++)
	  {
		  aux += this.chromosomes[i].cost;
		  if (this.bestCost>=this.chromosomes[i].cost)
		  {
			  this.bestCost = this.chromosomes[i].cost;
			  this.bestChromosome = i;
		  }
	  }
	  
	  this.avg = (int)(aux/(double) this.populationSize);
  }
  
  public String toString()
  {
	  String res = "Population: ";
	  res += "The best chromosome is " + this.bestChromosome + "and has a cost of: " + this.bestCost + " and an avg cost of " + this.avg;
	  return res;
  }
  
  public void toLog()
  {
	  this.log.write(this.bestCost + "," + this.avg);
  }


}
