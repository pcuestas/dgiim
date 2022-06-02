package pr4.vehicles;

import java.util.List;

import pr4.components.IComponent;
import pr4.components.exceptions.InvalidComponentException;

/**
 * Provided IVehicle interface
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public interface IVehicle {
	
	/**
	 * Current position
	 * @return current position
	 */
	public double getActualPosition();

	/**
	 * Set the new position
	 * @param  newPosition the new position
	 */
	public void setActualPosition(double newPosition);

	/**
	 * Whether this can move
	 * @return true iff this can move
	 */
	public boolean canMove();

	/**
	 * Set the parameter "can move"
	 * @param newMovement the new movement
	 */
	public void setCanMove(boolean newMovement);
	
	/**
	 * Maximum speed of the vehicle
	 * @return the maximum speed
	 */
	public double getMaxSpeed();
	
	/**
	 * Name of the vehicle
	 * @return the name of the vehicle
	 */
	public String getName();
	
	//For Exercise 3
	
	/**
	 * Add a component to the vehicle if it is possible. 
	 * Will throw InvalidComponentException if it is not.
	 * @param c component to add.
	 * @throws InvalidComponentException when the component is not 
	 * valid for the vehicle.
	 */
	public void addComponent(IComponent c) throws InvalidComponentException;
	
	/**
	 * The components of the vehicle
	 * @return the components of the vehicle
	 */
	public List<IComponent> getComponents();
}
