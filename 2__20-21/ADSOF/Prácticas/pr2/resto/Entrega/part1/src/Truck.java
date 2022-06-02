package prac2.traffic;

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
	 * Constructor of motorcycle
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param axles is the number of axles
	 */
	public Truck(String mod, int a, String pl, int axles) {
		super(mod, a, pl);
		this.numAxles = axles;
	}
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return (2*this.numAxles); }

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
	 * get number of axles
	 * @return number of axles
	 */
	public int getNumAxles() { return (this.numAxles); }

	/**
	 * set number of axles
	 * @param b number of axles
	 */
	public void setNumAxles(int b) { this.numAxles = b; }
}
