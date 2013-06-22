package es.urjc.etsii.ia.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Configuration 
{
	static private Configuration config = null;

	private Document doc;
	
	private String selectionPolicy;
	private int populationSize;
	private int maxGenerations;
	private float mutationProbability;
	private int fitnessType;
	private String dataPath;
	private String logPath;
	private float temperature;
	private float elitism;
	private int bestValue;
	
	/**
	 * 
	 * Process the XML and load all the parameters
	 * 
	 */
    private Configuration(String path)
    {
    	try
    	{
    		System.out.println("Loading cofiguration file from: " + path);
    		
    		// Process the XML file to get the document from the given path
	    	this.doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new FileInputStream(path)));
	    	//Load all the parameters
	    	this.loadParameters();
	    	
	    	System.out.println("... the configuration file was loaded successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (SAXException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch (ParserConfigurationException e) {
            e.printStackTrace();
         }
    	
    }
    
    /**
     * 
     * Returns a singleton instance of the configuration
     * 
     * @return
     */
    static public Configuration getConfiguration(String path) 
    {

        if (config == null) {
            config = new Configuration(path);
        }
        return config;
    }
    
    /**
     * 
     * Load all the paramaters for the genetical algorithm execution
     * 
     */
    private void loadParameters()
    {
    	try
    	{
    		this.dataPath = this.doc.getElementsByTagName("dataPath").item(0).getTextContent();
    		this.logPath = this.doc.getElementsByTagName("logPath").item(0).getTextContent();
	    	this.selectionPolicy = this.doc.getElementsByTagName("selectionPolicy").item(0).getTextContent();
	    	this.populationSize = Integer.parseInt(this.doc.getElementsByTagName("populationSize").item(0).getTextContent());
	    	this.maxGenerations = Integer.parseInt(this.doc.getElementsByTagName("maxGenerations").item(0).getTextContent());
	    	this.mutationProbability = Float.parseFloat(this.doc.getElementsByTagName("mutationProbability").item(0).getTextContent());
	    	this.fitnessType = Integer.parseInt(this.doc.getElementsByTagName("fitnessType").item(0).getTextContent());
	    	this.temperature = Float.parseFloat(this.doc.getElementsByTagName("temperature").item(0).getTextContent());
	    	this.elitism = Float.parseFloat(this.doc.getElementsByTagName("elitism").item(0).getTextContent());
	    	this.bestValue = Integer.parseInt(this.doc.getElementsByTagName("bestValue").item(0).getTextContent());
	    }
    	catch(NumberFormatException e)
    	{
    		System.out.println("Some of the parameters in the configuration file are not properly configured.");
    		throw e;
    	}
    }
    
    public String getDataPath()
    {
    	return this.dataPath;
    }
    
    public String getLogPath()
    {
    	return this.logPath;
    }
    
    public String getSelectionPolicy()
    {
    	return this.selectionPolicy;
    }
    
    public int getPopulationSize()
    {
    	return this.populationSize;
    }
    
    public int getMaxGenerations()
    {
    	return this.maxGenerations;
    }
    
    public float getMutationProbability()
    {
    	return this.mutationProbability;
    }
    
    public int getFitnessType()
    {
    	return this.fitnessType;
    }
    
    public float getTemperature()
    {
    	return this.temperature;
    }
    
    public float getElitism()
    {
    	return this.elitism;
    }
    
    public int getBestValue()
    {
    	return this.bestValue;
    }
    
    public String toString()
    {
    	String res = "Selection policy: " + this.selectionPolicy + "\n";
    	res+= "Population size : " + this.populationSize + "\n";
    	res+= "Generations : " + this.getMaxGenerations() + "\n";
    	res += "Mutation probability : " + this.mutationProbability + "\n";
    	
    	res += "The fitness function type is: ";
    	if (getFitnessType()>0)
    		res+="K - g(x)\n";
    	else 
    		res+=" 1/g(x)\n";
    	
    	if (this.selectionPolicy.equals("boltzmann"))
    		res+= "Temperature  = " + this.temperature + "\n";
    	
    	res+= "Elitism percentage = " + this.elitism*100 + "%\n";
    	
    	return res;

    }


}
