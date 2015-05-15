package scri;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadInput {
	String filename;
	ArrayList<SensorValues> listValues; 
	
	public ReadInput(String filename){
		this.filename = filename;
		listValues = new ArrayList<SensorValues>();
		readFile(filename);
		
		
	}
	
	public ArrayList<SensorValues> getValues(){
		return listValues;
	}
	
	public void readFile(String filename)
	{
	  String[] aux;
	  SensorValues sensorValues;
	  
	  double sensor1,sensor2;
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      //System.out.println(line);
	      aux=line.split(" ");
	      sensorValues = new SensorValues();
	      try{
	    	  sensorValues.setSensor1(Double.parseDouble(aux[0]));  
	      }
	      catch(Exception e){
	    	  
	      }
	      
	      try{
	    	  sensorValues.setSensor2(Double.parseDouble(aux[1]));  
	      }
	      catch(Exception e){
	    	  
	      }
	      listValues.add(sensorValues);      
	      
	    }
	    reader.close();
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	  }
	}

}
