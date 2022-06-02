package pr4.races;

import java.util.*;

import pr4.powerUps.*;
import pr4.vehicles.*;

/**
 * Class Race
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class Race {
	private double length;
	private List<IVehicleRace> vehicles;
	private boolean allowAttacks = false;
	private boolean allowPowerUps = false;
	private List<IPowerUp> powerUps = new ArrayList<>();
	
	/**
	 * Constructor of race
	 * @param length length of the race
	 * @param vehicles list of vehicles
	 */
	public Race(double length, List<IVehicleRace> vehicles) {
		this.length = length;
		this.vehicles = vehicles;
		this.powerUps.addAll(
				Arrays.asList(new SwapPowerUp(this), 
							  new AttackAllPowerUp(this),
							  new AttackFirstPowerUp(this)));
	}
	
	/**
	 * Info of the race
	 * @return string with the race info
	 */
	@Override
	public String toString() {
		String raceString = "Race with maximum length: " 
	                        + String.format(Locale.ROOT, "%.1f", this.length);
		
		for (IVehicleRace v: vehicles) {
			raceString += "\n"+vehicleToStringInRace(v);
		}
		
		return raceString;
	}

	/**
	 * The string of the vehicle to print in the race toString
	 * @param v vehicle
	 * @return The string of the vehicle to print in the race toString
	 */
	private String vehicleToStringInRace(IVehicleRace v) {
		String ret = "" + v;
		
		for(IVehicleRace w:this.vehicles) {
			if(w != v) {
				ret += "\n " + v.getName() 
				       + " distance to " + w.getName() + ": "
					   + String.format(Locale.ROOT, "%.1f", Math.abs(v.distance(w)));
			}
		}
			
		return ret;
	}
	
	/**
	 * Set the parameter to allow power attacks
	 * @param b new parameter
	 */
	public void allowAttacks(boolean b) {
		this.allowAttacks = b;
	}
	
	/**
	 * Set the parameter to allow power ups
	 * @param b new parameter
	 */
	public void allowPowerUps(boolean b) {
		this.allowPowerUps = b;
	}
	
	/**
	 * Simulate the race
	 */
	public void simulate() {
		int turn = 1;
		IVehicleRace winner = null;
		
		while(winner == null){
			System.out.println("---------\nStarting Turn: " + turn);

			System.out.println(this + "\n");// current state of the race
			
			if(turn%3 == 0 && this.allowAttacks) {
				this.attackingPhase();
			}else {
				System.out.println("Not attacking turn");
				if(this.allowPowerUps && (turn > 3)) {
					this.powerUpsPhase();
				}
			}
			
			this.repairingPhase();
			
			this.positionUpdating();
			
			System.out.println("Ending Turn: " + turn + "\n---------");
			turn++;
			winner = this.findWinner(); // null if there is no winner
		}
		
		System.out.println(winner+"\n has won the race");
	}

	/**
	 * Simulate a power-up phase
	 */
	private void powerUpsPhase() {
		if(Math.random() > 0.1) {
			System.out.println("Turn with no power ups");
			return;
		}

		System.out.println("Turn with power ups");
		
		for (IVehicleRace v: this.vehicles) {
			// get a random power up
			IPowerUp p = this.powerUps.get((int)(Math.random() * this.powerUps.size()));
			p.applyPowerUp(v);// apply power up in the vehicle
		}
	}

	/**
	 * Simulate the attack phase
	 */
	private void attackingPhase() {
		System.out.println("Starting attack phase");
		for(IVehicleRace v: this.vehicles) {
			if(v.canAttack()) {// if the vehicle cannot attack there is no need to calculate the next vehicle
				IVehicleRace w = this.getNextVehicle(v);
				v.attack(w);
			}else {
				System.out.println(v.getName() + " can not attack");
			}
		}
		System.out.println("End attack phase");
	}

	/**
	 * The vehicle immediately ahead of v if it is closer than 30 units.
	 * @param v vehicle to calculate the next of.
	 * @return The vehicle immediately ahead of v if it is closer than 30 units.
	 */
	public IVehicleRace getNextVehicle(IVehicle v) {
		double minDistance = 30.0;// if further, we do not select it
		IVehicleRace nextVehicle = null;
		for(IVehicleRace w:this.vehicles) {
			if(v == w) {
				continue;
			}
			double distance =  w.getActualPosition() - v.getActualPosition();
			if((distance >= 0) && (distance < minDistance)) {
				nextVehicle = w;
				minDistance = distance;
			}else if((distance == minDistance) && ((Math.random() < 0.5) || nextVehicle == null)) {
				// if they are at the same distance, we select with a 50% chance
				nextVehicle = w;
			}
		}
		return nextVehicle;
	}

	/**
	 * Simulate the repair phase
	 */
	private void repairingPhase() {
		for(IVehicleRace v: this.vehicles) {
			v.repairComponents();
		}
	}

	/**
	 * Simulate the position updating phase
	 */
	private void positionUpdating() {
		for(IVehicleRace v: this.vehicles) {
			v.move();
		}
	}

	/**
	 * Find the winner of the race
	 * @return the winner, if it exists, or null if there is no winner
	 */
	private IVehicleRace findWinner() {
		IVehicleRace first = this.getFirstVehicle();
		
		if(first.getActualPosition() >= this.length) {
			return first;
		}
		
		return null;
	}

	/**
	 * The vehicles of the race
	 * @return the list of vehicles of the race
	 */
	public List<IVehicleRace> getVehicles() {
		return this.vehicles;
	}

	/**
	 * The first vehicle of the race
	 * @return The first vehicle of the race
	 */
	public IVehicleRace getFirstVehicle() {
		double maxPosition = 0.0;
		IVehicleRace first = null;
		
		for(IVehicleRace v: vehicles) {
			if(v.getActualPosition() >= maxPosition) {
				maxPosition = v.getActualPosition();
				first = v;
			}
		}
		return first;
	}
}
