package es.urjc.etsii.ia.data;
import java.io.*;

public class DataSaver {
	private FileOutputStream fout;	
	static private DataSaver data = null;
	
	
    /**
     * 
     * Returns a singleton instance of the data saver.
     * 
     * @return
     */
    static public DataSaver getDataSaver(String path) 
    {

        if (data == null) {
            data = new DataSaver(path);
        }
        return data;
    }
    
    
	private DataSaver(String path)
	{
			
		try
		{
		    // Open an output stream
		    fout = new FileOutputStream (path);
		}
		// Catches any error conditions
		catch (IOException e)
		{
			System.err.println ("Unable to open to file");
			System.exit(-1);
		}
	}
	public void close() {
		try {
			fout.close();
		}
		// Catches any error conditions
		catch (IOException e) {
			System.err.println("Unable to close to file");
			System.exit(-1);
		}
	}
	public void write(String msg)
	{

			// Print a line of text
		    new PrintStream(fout).println (msg);

	}
}
