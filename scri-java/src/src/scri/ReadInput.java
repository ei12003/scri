package scri;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadInput {
	String filename;
	ArrayList<SensorValues> listValues;
	final static int STUCK_CONSTANT = 5;
	final static int RANDOM_CONSTANT = 50;

	public ReadInput(String filename) {
		this.filename = filename;
		listValues = new ArrayList<SensorValues>();
		readFile(filename);

	}

	public ArrayList<SensorValues> getValues() {
		return listValues;
	}

	public void readFile(String filename) {
		String[] aux;
		SensorValues sensorValues;
		double sensor1, sensor2;
		int ls1c = 0, ls2c = 0;
		double ls1 = -1, ls2 = -1;
		boolean isValid;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);

				aux = line.split(" ");
				sensorValues = new SensorValues();

				try {					
					sensor1 = Double.parseDouble(aux[0]);
					if (sensor1 > 0) {
						if (sensor1 == ls1) {
							ls1c++;
							if (ls1c >= STUCK_CONSTANT)
								isValid = false;
							else
								isValid = true;
						}
						else if(ls1 > 0 && Math.abs((ls1-sensor1))>=RANDOM_CONSTANT)
							isValid = false;
						else{
							ls1 = sensor1;
							ls1c = 1;
							isValid = true;
						}
							
					} 
					else
						isValid = false;
					if (isValid)
						sensorValues.setSensor1(sensor1);
					else
						discarded(sensor1);
					
				} catch (Exception e) {

				}

				try {
					sensor2 = Double.parseDouble(aux[1]);
					if (sensor2 > 0) {
						if (sensor2 == ls2) {
							ls2c++;
							if (ls2c >= STUCK_CONSTANT)
								isValid = false;
							else
								isValid = true;
						} 
						else if(ls2 > 0 && Math.abs((ls2-sensor2))>=RANDOM_CONSTANT)
							isValid = false;
						else{
							ls2 = sensor2;
							ls2c = 1;
							isValid = true;
						}
					} else
						isValid = false;

					if (isValid)
						sensorValues.setSensor2(sensor2);
					else
						discarded(sensor2);
				} catch (Exception e) {

				}
				listValues.add(sensorValues);

			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.",
					filename);
			e.printStackTrace();
		}
	}

	
	public void discarded(double num){
		System.out.println("Discarded: "+ num);
	}
}
