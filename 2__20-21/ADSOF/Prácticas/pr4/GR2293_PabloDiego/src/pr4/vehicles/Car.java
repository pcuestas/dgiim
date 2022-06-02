package pr4.vehicles;

/**
 * Class car
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class Car extends Vehicle {

	/**
	 * Constructor of car
	 * @param maxSpeed max speed of the car
	 */
	public Car(double maxSpeed) {
		super(maxSpeed);
	}

	/**
	 * The name of the type of vehicle
	 * @return name of the type of vehicle
	 */
	@Override
	protected String getVehicleTypeName() {
		return "Car";
	}

	/**
	 * Moves the vehicle to the new position
	 */
	@Override
	public void move() {
		if(this.canMove()) {
			double speedPercentage = 1.0;
			if(Math.random() > 0.9) {
				speedPercentage = 0.9;
			}
			this.actualPosition += speedPercentage*this.maxSpeed;
		}
	}

}
