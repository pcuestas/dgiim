package pr4.powerUps;

import pr4.races.Race;
import pr4.vehicles.*;

/**
 * Attack all power up. The vehicle that uses this attacks 
 * all of the others in the race.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class AttackAllPowerUp extends PowerUp{
	
	/**
	 * Constructor of attack all power up
	 * @param race where the power up belongs to 
	 */
	public AttackAllPowerUp(Race race) {
		super(race);
	}

	/**
	 * Apply the power up
	 * @param v the vehicle where the power up is applied
	 */
	@Override
	public void applyPowerUp(IVehicle v) {
		this.applyingPowerUpMessage(v);
		
		for(IVehicle w:race.getVehicles()) {
			if(w != v) {// does not attack itself
				((IVehicleRace)v).attack(w);
			}
		}
	}

	/**
	 * Name of the power up
	 * @return the name of the power up
	 */
	@Override
	public String namePowerUp() {
		return "AttackAllPowerUp";
	}

}
