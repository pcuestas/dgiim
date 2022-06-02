package pr4.powerUps;

import pr4.races.Race;
import pr4.vehicles.*;


/**
 * Attack first power up. The vehicle that uses this attacks 
 * the first one in race.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class AttackFirstPowerUp extends PowerUp {

	/**
	 * Constructor of attack first power up
	 * @param race where the power up belongs to 
	 */
	public AttackFirstPowerUp(Race race) {
		super(race);
	}

	/**
	 * Apply the power up
	 * @param v the vehicle where the power up is applied
	 */
	@Override
	public void applyPowerUp(IVehicle v) {
		this.applyingPowerUpMessage(v);
		IVehicle first = this.race.getFirstVehicle();
		if(v != first) {
			((IVehicleRace)v).attack(first);			
		}
	}

	/**
	 * Name of the power up
	 * @return the name of the power up
	 */
	@Override
	public String namePowerUp() {
		return "AttackFirstPowerUp";
	}

}
