package pr4.powerUps;

import pr4.vehicles.IVehicle;


/**
 * Provided IPowerUp interface
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public interface IPowerUp {
	/**
	 * Apply the power up
	 * @param v the vehicle where the power up is applied
	 */
	public void applyPowerUp(IVehicle v);
	
	/**
	 * Name of the power up
	 * @return the name of the power up
	 */
	public String namePowerUp();
}
