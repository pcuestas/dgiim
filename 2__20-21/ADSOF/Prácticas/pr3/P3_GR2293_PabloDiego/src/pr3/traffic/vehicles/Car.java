package pr3.traffic.vehicles;
import pr3.traffic.drivers.*;
import pr3.traffic.licenses.PermitKind;


/**
 * This class is for the type of vehicle Car
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 *
 */
public class Car extends Vehicle {
	private boolean diesel;// true iff car is diesel
	
	/**
	 * Constructor of Car
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param diesel whether it is diesel
	 * @param o owner
	 */
	public Car(String mod, int a, String pl, boolean diesel, Owner o) {
		super(mod, a, pl, o);
		this.diesel = diesel;
	}
	/**
	 * Constructor of Car
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param diesel whether it is diesel	 
	 */
	public Car(String mod, int a, String pl, boolean diesel) {
		this(mod, a, pl, diesel, null);
	}
    
    /**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return 4; }
	
	/**
	 * The necessary permit kind to drive this car
	 * @return the necessary permit kind
	 */
	@Override public PermitKind necessaryPermit() {
		return PermitKind.B;
	}

	/**
	 * Converts content of the object to string
	 */
	@Override public String toString() {
		return "Car "+(this.diesel ? "diesel" : "gasoline") + ", "+ super.toString();
	}
	
	/**
	 * Returns the pollution index of the car
	 */
	@Override public PollutionIndex getPollutionIndex() {
		if (this.diesel) {return PollutionIndex.C;}
		return super.getPollutionIndex();
	}
	
}
