package pr4.powerUps;

import java.util.Locale;

import pr4.races.Race;
import pr4.vehicles.*;

/**
 * Swap power up. The vehicle that uses this attacks 
 * swaps its position with the next.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class SwapPowerUp extends PowerUp{
	
	/**
	 * Constructor of swap
	 * @param race race it belongs to
	 */
	public SwapPowerUp(Race race) {
		super(race);
	}

	/**
	 * Apply the power up
	 * @param v the vehicle where the power up is applied
	 */
	@Override
	public void applyPowerUp(IVehicle v) {
		this.applyingPowerUpMessage(v);
		
		IVehicleRace w = this.race.getNextVehicle(v);
		if(w == null) {
			return ; // if there is no next vehicle
		}
		
		double wActualPosition = w.getActualPosition();
		w.setActualPosition(v.getActualPosition());
		v.setActualPosition(wActualPosition);
		
		printSwapString(v, w.getActualPosition());
		printSwapString(w, v.getActualPosition());
	}

	/**
	 * Prints the string saying where the vehicle was and where it is now
	 * @param v vehicle
	 * @param oldPosition v's old position
	 */
	private void printSwapString(IVehicle v, double oldPosition) {
		System.out.println(v.getName() + " was on " 
				+ String.format(Locale.ROOT, "%.1f", oldPosition)
				+ " with swap is now on " 
				+ String.format(Locale.ROOT, "%.1f", v.getActualPosition()));
	}

	/**
	 * Name of the power up
	 * @return the name of the power up
	 */
	@Override
	public String namePowerUp() {
		return "SwapPowerUp";
	}

}
