package es.urjc.etsii.ia.config;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Configuration config = Configuration.getConfiguration("./config/params.xml");
		
		System.out.println("-- Test configuration loader --");
		System.out.println("Selection policy : " + config.getSelectionPolicy());
		System.out.println("Population size : " + config.getPopulationSize());
		System.out.println("Max. generations : " + config.getMaxGenerations());		
		System.out.println("Mutation probability : " + config.getMutationProbability());
	}

}
