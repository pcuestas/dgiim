package pr4.powerUps;

import pr4.races.Race;
import pr4.vehicles.IVehicle;


/**
 * Power up abstract class, because all power ups have to keep 
 * the race they belong to.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class PowerUp implements IPowerUp{
	protected Race race;

	
	/**
	 * Constructor of power up
	 * @param race race where the power up belongs
	 */
	protected PowerUp(Race race) {
		this.race = race;
	}
	
	/**
	 * Shows the message saying the power up is being applied on vehicle v.
	 * @param v where the power up is applied.
	 */
	protected void applyingPowerUpMessage(IVehicle v) {
		System.out.println("Vehicle " + v.getName()
				+ " applying power-up: " +this.namePowerUp());
	}
}
