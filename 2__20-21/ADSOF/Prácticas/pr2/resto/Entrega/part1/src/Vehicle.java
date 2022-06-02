package prac2.traffic;

/**
 * Abstract class Vehicle that stores model name and year
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 * 
 **/
public abstract class Vehicle {
	private String model;
	private int purchaseYear;
	private String plate;
	
	/**
	 * Constructor of Vehicle
	 * @param mod : the model string
	 * @param a : year
	 * @param pl : plate
	 * */
	public Vehicle(String mod, int a, String pl) {		
		this.model = mod;
		this.purchaseYear = a;
		this.plate = pl;
	}
	
	/**
	 * Converts content of the object to string
	 */
	@Override public String toString() {
		return "model "+this.model+", number plate: "+this.plate+", purchase year "+this.purchaseYear+", with "+
				this.numWheels()+" wheels, index:"+this.getPollutionIndex();
	}
	
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	public abstract int numWheels();
	
	/**
	 * Calculates the pollution index of the vehicle
	 * @return the pollution index
	 */
	public PollutionIndex getPollutionIndex() {
		return PollutionIndex.getPollutionIndex(this.purchaseYear);
	}
}
