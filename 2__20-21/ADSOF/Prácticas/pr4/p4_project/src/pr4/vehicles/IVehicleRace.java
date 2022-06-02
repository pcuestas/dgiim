package pr4.vehicles;

/**
 * IVehicleRace interface, extends the one provided by the professors
 * and is used by the race (it keeps a list of IVehicleRace)
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public interface IVehicleRace extends IVehicle {
	/**
	 * Move the vehicle to a new position (one single turn)
	 */
	public void move();
	
	/**
	 * The distance between this and vehicle w. 
	 * @param w the vehicle to calculate the distance to.
	 * @return The distance between this and vehicle w. Greater than 0
	 * if w is ahead.
	 */
	public double distance(IVehicle w);
	
	/**
	 * If this can attack
	 * @return true iff this can attack (if it has a non-damaged dispenser)
	 */
	public boolean canAttack();
	
	/**
	 * This vehicle attacks w
	 * @param w attacked vehicle
	 */
	public void attack(IVehicle w);
	
	/**
	 * Repairs components of the vehicle
	 */
	public void repairComponents();
}
