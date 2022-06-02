package pr4.vehicles;

import pr4.components.*;
import pr4.components.exceptions.InvalidComponentException;

/**
 * Class Motorcycle (extends vehicle)
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class Motorcycle extends Vehicle {

	/**
	 * Constructor of motorcycle
	 * @param maxSpeed the maximum speed
	 */
	public Motorcycle(double maxSpeed) {
		super(maxSpeed);
	}

	/**
	 * The name of the type of vehicle
	 * @return name of the type of vehicle
	 */
	@Override
	protected String getVehicleTypeName() {
		return "Motorcycle";
	}

	/**
	 * Moves the vehicle to the new position
	 */
	@Override
	public void move() {
		if(this.canMove()) {
			this.actualPosition += this.maxSpeed;
		}
	}
	
	/**
	 * Add a component to the vehicle if it is possible. 
	 * Will throw InvalidComponentException if it is not.
	 * @param c component to add
	 */
	@Override
	public void addComponent(IComponent c) throws InvalidComponentException{
		if(!c.isCritical()) { // Motorcycle can only have critical components
			throw new InvalidComponentException(c, this);
		}
		super.addComponent(c);
	}
	
	/**
	 * Whether the Motorcycle can attack
	 * @return false, because Motorcycle cannot attack
	 */
	@Override
	public boolean canAttack() {
		return false;
	}

}
