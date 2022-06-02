package pr4.vehicles;

import java.util.*;

import pr4.components.*;
import pr4.components.exceptions.InvalidComponentException;

/**
 * Abstract Class Vehicle (implementing the interface IVehicleRace)
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class Vehicle implements IVehicleRace{
	private int id;
	protected double actualPosition = 0.0;
	protected double maxSpeed;
	private boolean canMove = true;
	private List<IComponent> components = new ArrayList<>();
	
	private static int countIDs = 1;
	
	/**
	 * Constructor of vehicle
	 * @param maxSpeed maximum speed
	 */
	public Vehicle(double maxSpeed) {
		this.maxSpeed = maxSpeed;
		this.id = countIDs++;
	}
	
	/**
	 * The name of the type of vehicle
	 * @return name of the type of vehicle
	 */
	protected abstract String getVehicleTypeName();
	
	/**
	 * To string method
	 * @return the string that represents the vehicle
	 */
	@Override
	public String toString() {
		String ret = getName()
				       + ". Speed " + maxSpeed 
				       + ". Actual Position: " 
				       + String.format(Locale.ROOT, "%.1f", actualPosition) +".";
		
		for(IComponent c: this.components) {
			ret += "\n->"+c.toString();
		}
		return ret;
	}
	
	/*IVehicle interface implementation*/
	
	/**
	 * Current position
	 * @return current position
	 */
	@Override
	public double getActualPosition() {
		return actualPosition;
	}
	
	/**
	 * Set the new position
	 * @param  newPosition the new position
	 */
	@Override
	public void setActualPosition(double newPosition) {
		this.actualPosition = newPosition;
	}
	
	/**
	 * Whether this can move
	 * @return true iff this can move
	 */
	@Override
	public boolean canMove() {
		if(!canMove) {
			return false;
		}
		for(IComponent c: components) {
			if(c.isCritical() && c.isDamaged()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Set the parameter "can move"
	 * @param newMovement the new movement
	 */
	@Override
	public void setCanMove(boolean newMovement) {
		this.canMove = newMovement;
	}
	
	/**
	 * Maximum speed of the vehicle
	 * @return the maximum speed
	 */
	@Override
	public double getMaxSpeed() {
		return this.maxSpeed;
	}
	
	/**
	 * Name of the vehicle
	 * @return the name of the vehicle
	 */
	@Override
	public String getName() {
		return this.getVehicleTypeName()+"("+this.id+")";
	}
	
	/**
	 * Add a component to the vehicle if it is possible. 
	 * Will throw InvalidComponentException if it is not.
	 * @param c component to add
	 */
	@Override
	public void addComponent(IComponent c) throws InvalidComponentException{
		this.components.add(c);
	}
	
	/**
	 * The components of the vehicle
	 * @return the components of the vehicle
	 */
	@Override
	public List<IComponent> getComponents() {
		return new ArrayList<>(components);
	}

	/*end of IVehicle implementation*/
	
	/*IVehicleRace implementation:*/
	
	/**
	 * The distance between this and vehicle w. 
	 * @param w the vehicle to calculate the distance to.
	 * @return The distance between this and vehicle w. Greater than 0
	 * if w is ahead.
	 */
	@Override
	public double distance(IVehicle w) {
		return (w.getActualPosition()-this.actualPosition);
	}
		
	/**
	 * Repairs components
	 */
	@Override
	public void repairComponents() {
		for (IComponent c : this.components) {
			c.repair();
		}
	}

	/**
	 * If this can attack
	 * @return true iff this can attack (if it has a non-damaged dispenser)
	 */
	@Override
	public boolean canAttack() {
		if (this.bananaDispenser() == null) {
			return false;
		}
		return !(this.bananaDispenser().isDamaged());
	}

	/**
	 * This vehicle attacks w
	 * @param w attacked vehicle
	 */
	@Override
	public void attack(IVehicle w) {
		String message = this.getName();
		if (w == null) { // w is null if there are no vehicles ahead or they are too far
			message += " can not attack";
		} else if (Math.random() < 0.5) {
			message += " fails attack";
		} else {
			// select a random component
			IComponent c = w.getComponents().get((int)(w.getComponents().size() * Math.random()));
			c.setDamaged(true);
			message += " attacks " + w.getName() + " " + c.getName();
		}
		System.out.println(message);
	}

	
	/**
	 * The banana dispenser of the vehicle or null if it does not have one.
	 * @return The banana dispenser of the vehicle or null if it does not have one.
	 */
	private BananaDispenser bananaDispenser() {
		for (IComponent c : this.components) {
			if (c.getName().equals("Banana dispenser")) {
				return (BananaDispenser) c;
			}
		}
		return null;
	}
}
