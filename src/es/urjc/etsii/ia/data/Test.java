package es.urjc.etsii.ia.data;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//Get singleton instance. It will load the data from the given path.
		DataLoader data = DataLoader.getDataLoader("./data/olimpiadas.dat");
		
		System.out.println("-- Test data loader --");
		//Print both matrices
		int[][] d = data.getDistancesMatrix();
		System.out.println("Distances-Matrix:");
	    
		for (int x = 0; x<d.length; x++)
    	{
    		for(int y=0; y<d.length; y++)
    		{
    			System.out.print(d[x][y] + " - ");
    		}
    		System.out.println();
    	}
		
		int[][] f = data.getFlowMatrix();
		System.out.println("Flow-Matrix:");
	    
		for (int x = 0; x<f.length; x++)
    	{
    		for(int y=0; y<f.length; y++)
    		{
    			System.out.print(f[x][y] + " - ");
    		}
    		System.out.println();
    	}
		
   
	}

}
