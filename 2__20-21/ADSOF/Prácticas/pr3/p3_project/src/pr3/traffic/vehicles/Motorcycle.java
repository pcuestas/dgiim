package pr3.traffic.vehicles;
import pr3.traffic.drivers.*;
import pr3.traffic.licenses.PermitKind;


/**
 * This class is for the type of vehicle Motorcycle
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 *
 */
public class Motorcycle extends Vehicle {
	/**
	 * electric is true iff the motorcycle is electric
	 */
	private boolean electric;
	
	/**
	 * Constructor of Motorcycle
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param el true iff it is electric
	 * @param o owner
	 */
	public Motorcycle(String mod, int a, String pl, boolean el, Owner o) {
		super(mod, a, pl,o);
		this.electric = el;
	}
	/**
	 * Constructor of Motorcycle
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param el true iff it is electric
	 */
	public Motorcycle(String mod, int a, String pl, boolean el) {
		this(mod, a, pl, el, null);
	}
	
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return 2; }
	
	/**
	 * The necessary permit kind to drive a motorcycle
	 * @return the necessary permit kind
	 */
	@Override public PermitKind necessaryPermit() {
		return PermitKind.A;
	}

	/**
	 * Converts content of the object to string
	 */
	@Override public String toString() {
		return "Motorcycle"+(this.electric ? " electric" : "") + ", "+ super.toString();
	}
	
	/**
	 * Returns the pollution index of the object motorcycle
	 */
	@Override public PollutionIndex getPollutionIndex() {
		if (this.electric) {return PollutionIndex.A;}
		return super.getPollutionIndex();
	}
}
