package prac2.traffic;

/**
 * This class is for the type of vehicle Car
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 *
 */
public class Car extends Vehicle {
	private boolean diesel;
	
	/**
	 * Constructor of Car
	 * @param mod model
	 * @param a year of purchase
	 * @param pl plate
	 * @param diesel whether it is diesel
	 */
	public Car(String mod, int a, String pl, boolean diesel) {
		super(mod, a, pl);
		this.diesel = diesel;
	}
    
    /**
	 * Number of wheels
	 * @return the number of wheels
	 */
	@Override public int numWheels() { return 4; }

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
	
	/**
	 * get whether it is diesel
	 * @return true or false
	 */
	public boolean getDiesel() { return this.diesel; }
	
	/**
	 * set the parameter diesel (boolean)
	 * @param b true iff the Car is diesel
	 */
	public void setDiesel(boolean b) { this.diesel = b; }
}
