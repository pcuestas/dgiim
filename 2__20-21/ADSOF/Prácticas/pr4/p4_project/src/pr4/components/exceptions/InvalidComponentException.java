package pr4.components.exceptions;

import pr4.components.IComponent;
import pr4.vehicles.IVehicle;

/**
 * Invalid comonent exception. When a component is added 
 * to a vehicle that can not have that component.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class InvalidComponentException extends ComponentException {
	private IComponent component;
	private IVehicle vehicle;
	
	/**
	 * Constructor of the exception
	 * @param component invalid component
	 * @param vehicle vehicle where the component is invalid
	 */
	public InvalidComponentException(IComponent component, IVehicle vehicle) {
		this.component = component;
		this.vehicle = vehicle;
	}

	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	@Override
	public String message() {
		return "Component " + component.getName() 
		       + " is not valid for vehicle " + vehicle.getName();
	}
}
