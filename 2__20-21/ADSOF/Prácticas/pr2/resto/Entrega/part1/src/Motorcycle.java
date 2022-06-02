package prac2.traffic;

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
	 */
	public Motorcycle(String mod, int a, String pl, boolean el) {
		super(mod, a, pl);
		this.electric = el;
	}
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return 2; }

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
	
	/**
	 * returns whether the motorcycle is electric
	 * @return true or false
	 */
	public boolean getElectric() { return this.electric; }
	
	/**
	 * set whether it is electric
	 * @param b : electric or not (boolean)
	 */
	public void setElectric(boolean b) { this.electric = b; }
}
