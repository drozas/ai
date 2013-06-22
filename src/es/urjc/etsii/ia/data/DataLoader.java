package es.urjc.etsii.ia.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;



public class DataLoader 
{
	
	static private DataLoader data = null;
	
	private int[][]distances;
	private int[][]flow;

	/**
	 * 
	 * Process the XML and load all the parameters
	 * 
	 */
    private DataLoader(String path) 
    {
    	try
    	{
    		System.out.println("Loading data from : " + path);
	    	FileReader file = new FileReader(path);
	    	BufferedReader buff = new BufferedReader(file);
	    	
	
	    	// Calculate the size of the matrices
	    	int size = Integer.parseInt(buff.readLine());
	    	System.out.println("The size of the matrices is : " + size);
			this.distances = new int[size][size];
			this.flow = new int[size][size];
			
			int i=0;
			int j=0;
			String line;
			line = buff.readLine(); //First blank line
	    	
			//Read and tokenize distances matrix
	    	while (((line=buff.readLine())!=null)&&(i<size))
	    	{
	    		
	    		StringTokenizer st = new StringTokenizer(line);
	    	    while (st.hasMoreTokens()) 
	    	    {
	    	    	distances[i][j] = Integer.parseInt(st.nextToken());
	    	    	j++;
	    	    }
	    	    j=0;
	    	    i++;
	    	    
	
	    	}
	    	
	    	System.out.println("...distances matrix was loaded successfully.");
	    	
			i=0;
			j=0;

			//Read and tokenize flow matrix    	
	    	while (((line=buff.readLine())!=null)&&(i<size))
	    	{
	    		
	    		StringTokenizer st = new StringTokenizer(line);
	    	    while (st.hasMoreTokens()) 
	    	    {
	    	    	flow[i][j] = Integer.parseInt(st.nextToken());
	    	    	j++;
	    	    }
	    	    j=0;
	    	    i++;
	    	    
	
	    	}
	    	
	    	System.out.println("...flow matrix was loaded successfully.");
	    	
	    	buff.close();
	    	file.close();

    	}catch(IOException e)
    	{
    		System.out.println("It was not possible to open the data file. Please, check the file path.");
    		e.printStackTrace();
    	}
    	
	}
    
    /**
     * 
     * Returns a singleton instance of the data loader.
     * 
     * @return
     */
    static public DataLoader getDataLoader(String path) 
    {

        if (data == null) {
            data = new DataLoader(path);
        }
        return data;
    }
    
    /**
     * 
     * Returns the distances matrix (which has been loaded previously)
     * 
     * @return
     */
    public int[][] getDistancesMatrix()
    {
    	return this.distances;
    }
    
    /**
     * 
     * Returns the flow matrix (which has been loaded previously)
     * 
     * @return
     */
    public int[][] getFlowMatrix()
    {
    	return this.flow;
    }
    

}
