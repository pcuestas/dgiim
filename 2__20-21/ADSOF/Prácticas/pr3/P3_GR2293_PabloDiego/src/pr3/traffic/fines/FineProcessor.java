package pr3.traffic.fines;

import java.util.*;
import pr3.traffic.vehicles.*;
import pr3.traffic.drivers.*;

/**
 * Fine Processor class
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class FineProcessor {
	private List<Vehicle> vehicles;

	/**
	 * Constructor of FineProcessor
	 * 
	 * @param v the list of vehicles
	 */
	public FineProcessor(List<Vehicle> v) {
		this.vehicles = v;
	}

	/**
	 * Static private method to handle the fine of a vehicle (for simplicity) When
	 * this method is called, the fine has to correspond to the vehicle
	 * 
	 * @param f the fine
	 * @param v the vehicle
	 */
	private static void handleFine(Fine f, Vehicle v) {
		Person p = v.getDriver();
		int extra = 0; // extra points to be removed if ITV is expired

		if (!v.itvStatus()) {// if expired
			extra = 1;
			FineWriter.write(new Fine("" + v.getPlate(), "Expired ITV", 1), "txt/ITV_expired.txt");
			System.out.println("ITV of vehicle with plate " + v.getPlate() + " is expired.");
		}

		if (p == null) {
			System.out.println("Vehicle with plate " + v.getPlate() + " has no driver associated. "
					+ "DGT will initiate legal actions against the owner.");
		} else {
			int remPoints = p.getLicense().subtractPoints(f.getPoints() + extra);

			System.out.println("Driver " + p.getName() + " loses " + (f.getPoints()+extra) + " points");

			if (remPoints == -1) { // the driver already had 0 points
				System.out.println("License suspended for driver " + p.getName());
			} else if (remPoints == 0) { // the driver now has 0 points
				System.out.println("Driver " + p.getName() + " remains with 0 points");
			}
		}
	}

	/**
	 * Processes the fines from the input that correspond to the vehicles in the
	 * FineProcessor object
	 * 
	 * @param fines the list of fines to process
	 */
	public void process(List<Fine> fines) {
		for (Fine f : fines) {
			for (Vehicle v : this.vehicles) {
				if (f.getPlate().equals(v.getPlate())) {
					handleFine(f, v);
					//vehicle for the fine has been found, no need to continue the loop
					break;
				}
			}
		}
	}

}
