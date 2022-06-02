package pr4.races;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pr4.components.*;
import pr4.components.exceptions.*;
import pr4.races.exceptions.*;
import pr4.vehicles.*;

/**
 * Class RaceReader, with one public method to read a 
 * race from a file
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class RaceReader {
	
	/**
	 * Reads the race with the format proposed in the Assignment 4 pdf
	 * @param file file where the race is
	 * @return the race or null if there was an error
	 * @throws RaceException if there are wrong parameters in the file
	 * @throws IOException if something goes wrong when opening files
	 */
	public static Race read(String file) 
			throws RaceException, IOException
	{
		double length;
		List<IVehicleRace> vehicleList = new ArrayList<>();
		double highestSpeed = 0.0;

		BufferedReader buffer = new BufferedReader(
										new InputStreamReader(
												new FileInputStream(file)));
		
		String line = buffer.readLine();
		
		length = Double.parseDouble(line);
		
		while ((line = buffer.readLine()) != null) {
			String[] fields = line.split(" ");
			
			double speed = Double.parseDouble(fields[2]);
			highestSpeed = Math.max(highestSpeed, speed);
			
			List<IVehicleRace> newVehicles = createVehiclesFromFileFields(fields);
			vehicleList.addAll(newVehicles);
		}
		buffer.close();
		        
		int vehiclesNum = vehicleList.size();
		
		if(highestSpeed > length) {
			throw new RaceSmallException();
		}else if(vehiclesNum < 2 || vehiclesNum > 10) {
			throw new NumVehiclesException();
		}
		
		return new Race(length, vehicleList);		
	}

	/**
	 * Creates the list of vehicles read from a single line of the text file.
	 * The vehicles are either cars, motorcycles or trucks
	 * @param fields each word of the line
	 * @return the list of new vehicles
	 * @throws RaceException if the max speed is not valid
	 */
	private static List<IVehicleRace> createVehiclesFromFileFields(String[] fields) 
			throws RaceException
	{
		List<IVehicleRace> vehicleList = new ArrayList<>();

		Double maxSpeed = Double.parseDouble(fields[2]);
		
		if(maxSpeed < 0.0) {
			throw new InvalidSpeedException();
		}
		
		for(int i = 0; i < Integer.parseInt(fields[0]); i++){
			IVehicleRace v;
			
			if(fields[1].equals("Car")) {
				v = new Car(maxSpeed);
			}else if(fields[1].equals("Motorcycle")) {
				v = new Motorcycle(maxSpeed);
			}else if(fields[1].equals("Truck")){
				v = new Truck(maxSpeed);						
			}else {
				//not a vehicle
				continue;
			}
			for(int j = 3; j < fields.length; j++) {
				try {
					v.addComponent(createComponent(fields[j], v));
				} catch (ComponentException e) {
					System.out.println(e);
				}
			}
			vehicleList.add(v);
		}
		return vehicleList;
	}

	/**
	 * Creates and returns a new component 
	 * @param name name of the component
	 * @param v vehicle where it belongs
	 * @return the new component
	 * @throws ComponentException if the name is not valid
	 */
	private static IComponent createComponent(String name, IVehicleRace v) 
			throws ComponentException
	{
		if(name.equals("BananaDispenser")) {
			return new BananaDispenser(v);
		}else if(name.equals("Window")) {
			return new Window(v);
		}else if(name.equals("Engine") || name.equals("Wheels")) {
			return new CriticalComponent(v, name);
		}
		throw new InvalidComponentName(name);
	}
}
