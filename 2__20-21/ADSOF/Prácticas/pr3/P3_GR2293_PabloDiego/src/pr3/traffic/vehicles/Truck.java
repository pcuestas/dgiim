package pr3.traffic.vehicles;
import java.time.LocalDate;
import java.util.List;
import pr3.traffic.itv.*;
import pr3.traffic.drivers.*;
import pr3.traffic.licenses.PermitKind;

/**
 * This class is for the type of vehicle Truck
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 *
 */
public class Truck extends Vehicle {
	/**
	 * numAxles is the number of axles of the truck
	 */
	private int numAxles;

	/**
	 * Array in the form: {{a1,b1},{a2,b2},{a3,b3}, ..., {an,bn}}. 
     * Where a1 < a2 < a3 < ... < an.
	 * Used to calculate the remaining years until the next ITV. 
	 * Meaning: if the age (in years) of the vehicle is lower than a1, 
	 *              it has to pass no inspection,
	 *          if the age of the vehicle is between 
	 * 				a1 and a2, then it has to pass the ITV every b1 months,
	 *          if the age of the vehicle is between 
	 * 				a2 and a3, then it has to pass the ITV every b2 months,
	 *          if the age of the vehicle is between 
	 * 				a3 and a4, then it has to pass the ITV every b3 months, ...
	 *  		... if the age is higher than an, it has to pass the ITV every bn months
	 **/
	private static final long[][] itvTimeArrayTruck = new long[][]{{2, 2*12}, {6, 1*12}, {10, 6}}; 
	
	/**
	 * Constructor of truck
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param axles is the number of axles
	 * @param o owner
	 */
	public Truck(String mod, int a, String pl, int axles, Owner o) {
		super(mod, a, pl, o);
		this.numAxles = axles;
	}
	/**
	 * Constructor of truck
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param axles is the number of axles
	 */
	public Truck(String mod, int a, String pl, int axles) {
		this(mod, a, pl, axles, null);
	}
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return (2*this.numAxles); }
	
	/**
	 * The necessary permit kind to drive a truck
	 * @return the necessary permit kind
	 */
	@Override public PermitKind necessaryPermit() {
		return PermitKind.C1;
	}

	/**
	 * Converts content of the object to string
	 */
	@Override public String toString() {
		return "Truck with "+ this.numAxles +" axles, "+ super.toString();
	}
	
	/**
	 * Returns the pollution index of the truck
	 */
	@Override public PollutionIndex getPollutionIndex() {
		if (this.numAxles > 2) {return PollutionIndex.C;}
		return super.getPollutionIndex();
	}
	
	/**
	 * Returns the years until the next itv 
	 * @return an integer that corresponds to the years remaining until 
	 * 	ITV has to be passed, if the number is negative, the ITV is expired 
	 */
	@Override
	public long daysUntilITV() {
		return this.daysUntilITVAux(itvTimeArrayTruck);
	}
}
