package pr4.components;

import pr4.vehicles.IVehicle;

/**
 * IComponent interface, provided by the professors
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public interface IComponent {
	/**
	 * Whether it is damaged
	 * @return Whether it is damaged
	 */
	public boolean isDamaged();
	
	/**
	 * Set the damage
	 * @param damage new damage
	 */
	public void setDamaged(boolean damage);
	
	/**
	 * The name of the component
	 * @return name of the component
	 */
	public String getName();
	
	/**
	 * Number of turns it costs to repair
	 * @return Number of turns it costs to repair
	 */
	public int costRepair();
	
	/**
	 * Its vehicle
	 * @return its vehicle
	 */
	public IVehicle getVehicle();
	
	/**
	 * Whether it is critical
	 * @return Whether it is critical
	 */
	public boolean isCritical();
	
	/**
	 * Repair component
	 */
	public void repair();
}
