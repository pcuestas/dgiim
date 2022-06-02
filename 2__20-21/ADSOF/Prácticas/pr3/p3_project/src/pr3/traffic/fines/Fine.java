package pr3.traffic.fines;

/**
 * Fine class
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class Fine {
	private String plate; // plate of the vehicle
	private String description; // description of the type of fine
	private int points; // points to be subtracted from the driving license

	/**
	 * Constructor of the class Fine
	 * 
	 * @param pl    plate
	 * @param descr description
	 * @param p     points
	 */
	public Fine(String pl, String descr, int p) {
		this.plate = pl;
		this.description = descr;
		this.points = p;
	}

	/**
	 * toString method to print the information of the fine
	 */
	@Override public String toString() {
		return "Fine [plate=" + this.plate 
				+ ", Fine type=" + this.description 
				+ ", points=" + this.points + "]";
	}
	
	/**
	 * Getter for the plate of the fined vehicle
	 * 
	 * @return the plate of the fined vehicle
	 */
	public String getPlate() {
		return this.plate;
	}

	/**
	 * Getter for the number of points of the fine
	 * 
	 * @return the points of the fine
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * String with the format: NUMBER_PLATE;FINE_TYPE;POINTS
	 * 
	 * @return formatted string
	 */
	public String fileFormat() {
		return plate + ";" + description + ";" + points;
	}
	
	
}
