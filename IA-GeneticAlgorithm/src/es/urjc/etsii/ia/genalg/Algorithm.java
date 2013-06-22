package es.urjc.etsii.ia.genalg;
import es.urjc.etsii.ia.config.Configuration;
import es.urjc.etsii.ia.data.DataLoader;
import es.urjc.etsii.ia.genalg.Chromosome;
import es.urjc.etsii.ia.genalg.Graphics;
public class Algorithm {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration config = Configuration.getConfiguration("./config/params.xml");
		int[] data_avg=new int[config.getMaxGenerations()];
		int[] data_min=new int[config.getMaxGenerations()];
		Population population = new Population();
		System.out.print("Working");
		population.initializate();
		
		Chromosome best_chromosome = new Chromosome();
		

		for(int i=0; i<config.getMaxGenerations()&&population.stopCriteria(); i++)
		{
			population = population.get_next_generation();
			if (best_chromosome.cost > population.chromosomes[population.bestChromosome].cost)
				best_chromosome = population.chromosomes[population.bestChromosome];
			double percent=(double)i/(double)config.getMaxGenerations()*100;
			
			if (percent%10==0)
			{
				System.out.print("..."+(int)percent+" % \n");
			}
			
			//Appending data history
			data_min[i]=population.bestCost;
			data_avg[i]=population.avg;
			
			//Append data to log
			population.toLog();
		}
		
		System.out.println("\nThe best individual is " + best_chromosome.toString());
		System.out.println("The parameters used were : ");
		System.out.println(config.toString());
    //now with graphics =) 
		new Graphics("Graphics",800,600,data_min,data_avg,best_chromosome.cost).start();

	}

}
