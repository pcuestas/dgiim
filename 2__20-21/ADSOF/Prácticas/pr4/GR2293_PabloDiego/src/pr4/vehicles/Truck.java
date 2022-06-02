package pr4.vehicles;

/**
 * Truck class 
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class Truck extends Vehicle {

	/**
	 * constructor of truck
	 * @param maxSpeed maximum speed of the truck
	 */
	public Truck(double maxSpeed) {
		super(maxSpeed);
	}

	/**
	 * The name of the type of vehicle
	 * @return name of the type of vehicle
	 */
	@Override
	protected String getVehicleTypeName() {
		return "Truck";
	}

	/**
	 * Moves the vehicle to the new position
	 */
	@Override
	public void move() {
		if(this.canMove()) {
			double speedPercentage = 1.0;
			if(Math.random() > 0.9) {
				speedPercentage = 0.8;
			}
			this.actualPosition += speedPercentage*this.maxSpeed;
		}
	}
}
